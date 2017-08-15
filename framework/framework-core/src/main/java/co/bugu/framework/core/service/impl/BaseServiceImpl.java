package co.bugu.framework.core.service.impl;

import co.bugu.framework.core.dao.BaseDao;
import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.exception.TesJedisException;
import co.bugu.framework.core.service.IBaseService;
import co.bugu.framework.core.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by user on 2017/1/5.
 */
public class BaseServiceImpl<T> implements IBaseService<T> {
    @Autowired
    protected BaseDao<T> baseDao;

    private String nameSpace = "";

    {
        Class clazz = this.getClass();
        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        String simpleName = ((Class)types[0]).getSimpleName();
        nameSpace = getProjectName() + "." + simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + ".";
    }

    /**
     * 子类建议重写该方法，以获取指定的工程名称，用于查找对应的mapper中的命名空间
     * @return
     */
    protected String getProjectName(){
        return "tes";
    }

    @Override
    public int save(T record) throws IOException, TesJedisException {
        JedisUtil.delObject(record);
        int num = baseDao.insert(nameSpace + "insert", record);
        JedisUtil.setObject(record);
        return num;
    }

    @Override
    public int updateById(T record) throws TesJedisException, IOException {
        JedisUtil.delObject(record);
        int num = baseDao.update(nameSpace + "updateById", record);
        JedisUtil.setObject(record);
        return num;
    }

    @Override
    public int delete(T record) throws TesJedisException {
        JedisUtil.delObject(record);
        return baseDao.delete(nameSpace + "deleteById", record);
    }

    @Override
    public T findById(Integer id) throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        String type = parameterizedType.getActualTypeArguments()[0].getTypeName();
        T res = null;
        try {
            res = JedisUtil.getObject(id, (Class<T>) Class.forName(type));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(res == null){
            res = baseDao.selectOne(nameSpace + "selectById", id);
        }
        if(res != null){
            JedisUtil.setObject(res);
        }
        return res;
    }

    @Override
    public List<T> findByObject(T record){
        return baseDao.selectList(nameSpace + "findByObject", record);
    }

    @Override
    public PageInfo findByObject(T record, PageInfo<T> pageInfo) throws Exception {
        return baseDao.listByObject(nameSpace + "findByObject", record, pageInfo);
    }

    @Override
    public PageInfo findByObject(T record, Integer showCount, Integer curPage) throws Exception {
        PageInfo<T> pageInfo = new PageInfo<T>(showCount, curPage);
//        baseDao.listByObject(nameSpace + "findByObject", record, pageInfo);
//        return pageInfo;
        return findByObject(record, pageInfo);
    }

    protected String getKeyFromClassAndId(Integer id, Class<T> tClass){
        return tClass.getName() + "_" + id;
    }
}

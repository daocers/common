package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.SystemParam;
import co.bugu.tes.service.ISystemParamService;
import co.bugu.framework.core.dao.BaseDao;
import co.bugu.framework.core.dao.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemParamServiceImpl extends BaseServiceImpl<SystemParam> implements ISystemParamService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(SystemParam systemparam) {
//        return baseDao.insert("tes.systemparam.insert", systemparam);
//    }
//
//    @Override
//    public int updateById(SystemParam systemparam) {
//        return baseDao.update("tes.systemparam.updateById", systemparam);
//    }
//
//    @Override
//    public int saveOrUpdate(SystemParam systemparam) {
//        if(systemparam.getId() == null){
//            return baseDao.insert("tes.systemparam.insert", systemparam);
//        }else{
//            return baseDao.update("tes.systemparam.updateById", systemparam);
//        }
//    }
//
//    @Override
//    public int delete(SystemParam systemparam) {
//        return baseDao.delete("tes.systemparam.deleteById", systemparam);
//    }
//
//    @Override
//    public SystemParam findById(Integer id) {
//        return baseDao.selectOne("tes.systemparam.selectById", id);
//    }
//
//    @Override
//    public List<SystemParam> findAllByObject(SystemParam systemparam) {
//        return baseDao.selectList("tes.systemparam.listByObject", systemparam);
//    }
//
//    @Override
//    public PageInfo listByObject(SystemParam systemparam, PageInfo<SystemParam> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.systemparam.listByObject", systemparam, pageInfo);
//    }
}

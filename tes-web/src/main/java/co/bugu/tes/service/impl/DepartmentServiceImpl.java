package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.util.JedisUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Department;
import co.bugu.tes.service.IDepartmentService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class
DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {
    @Override
    public void batchAdd(List<Department> departmentList) {

    }

    @Override
    public Map<String, String> getDepartmentNameIdMap() {
        Map<String, String> map = JedisUtil.hgetall(Constant.DEPARTMENT_INFO);
        if(MapUtils.isNotEmpty(map)){
            return map;
        }else{
            map = new HashMap<>();
            List<Department>  departments = baseDao.selectList("tes.department.findByObject", null);
            for(Department department: departments){
                map.put(department.getName(), department.getId() + "");
            }
        }
        return map;
    }

    @Override
    public Map<String, String> getDepartmentIdNameMap() {
        Map<String, String> map = JedisUtil.hgetall(Constant.DEPARTMENT_INFO);
        if(MapUtils.isNotEmpty(map)){
            return map;
        }else{
            map = new HashMap<>();
            List<Department>  departments = baseDao.selectList("tes.department.findByObject", null);
            for(Department department: departments){
                map.put(department.getId() + "", department.getName());
            }
        }
        return map;
    }


//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Department department) {
//        return baseDao.insert("tes.department.insert", department);
//    }
//
//    @Override
//    public int updateById(Department department) {
//        return baseDao.update("tes.department.updateById", department);
//    }
//
//    @Override
//    public int saveOrUpdate(Department department) {
//        if(department.getId() == null){
//            return baseDao.insert("tes.department.insert", department);
//        }else{
//            return baseDao.update("tes.department.updateById", department);
//        }
//    }
//
//    @Override
//    public int delete(Department department) {
//        return baseDao.delete("tes.department.deleteById", department);
//    }
//
//    @Override
//    public Department findById(Integer id) {
//        return baseDao.selectOne("tes.department.selectById", id);
//    }
//
//    @Override
//    public List<Department> findAllByObject(Department department) {
//        return baseDao.selectList("tes.department.listByObject", department);
//    }
//
//    @Override
//    public PageInfo listByObject(Department department, PageInfo<Department> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.department.listByObject", department, pageInfo);
//    }
}

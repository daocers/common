package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Department;

import java.util.List;
import java.util.Map;

public interface IDepartmentService extends IBaseService<Department> {
//    int save(Department department);
//
//    int updateById(Department department);
//
//    int saveOrUpdate(Department department);
//
//    int delete(Department department);
//
//    Department findById(Integer id);
//
//    List<Department> findAllByObject(Department department);
//
//    PageInfo listByObject(Department department, PageInfo<Department> pageInfo) throws Exception;

    void batchAdd(List<Department> departmentList);

    Map<String, String> getDepartmentNameIdMap();
    Map<String, String> getDepartmentIdNameMap();
}

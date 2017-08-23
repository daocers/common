package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.model.Branch;
import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

public interface IBranchService extends IBaseService<Branch> {
//    int save(Branch branch);
//
//    int updateById(Branch branch);
//
//    int saveOrUpdate(Branch branch);
//
//    int delete(Branch branch);
//
    void deleteAll();
//
//    Branch findById(Integer id);
//
//    List<Branch> findAllByObject(Branch branch);
//
//    PageInfo listByObject(Branch branch, PageInfo<Branch> pageInfo) throws Exception;

    void batchAdd(List<List<String>> data) throws TesException;

    Map<String, String> getBranchNameIdMap();
    Map<String, String> getBranchIdNameMap();

    void updateAll(JSONArray array);
}

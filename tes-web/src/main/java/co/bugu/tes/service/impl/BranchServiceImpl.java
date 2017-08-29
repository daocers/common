package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.enums.BranchLevelEnum;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.model.Branch;
import co.bugu.tes.service.IBranchService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchServiceImpl extends BaseServiceImpl<Branch> implements IBranchService {


    @Override
    public void deleteAll() {
        baseDao.delete("tes.branch.deleteAll");
    }

    /**
     * 更新操作，如果更新过上级机构，则级联更新该机构下属的所有机构的level
     * 不建议操作，牵扯太多，容易对已有数据造成灾难性影响。
     *
     * @param data
     * @throws TesException
     */
//    @Override
//    public int updateById(Branch branch){
//        if(branch.getSuperiorId() == null){
//            baseDao.update("tes.branch.updateById", branch);
//        }
//
//        return 0;
//
//    }
    @Override
    public void batchAdd(List<List<String>> data) throws TesException {
        for (int i = 0; i < data.size(); i++) {
            List<String> line = data.get(i);
            String code = line.get(0);
            String name = line.get(1);
            String superCode = line.get(2);
            String address = line.get(3);
            if (StringUtils.isEmpty(code)) {
                throw new TesException("第" + i + "行机构编码不能为空。");
            }
            if (StringUtils.isEmpty(name)) {
                throw new TesException("第" + (i + 1) + "行机构名称不能为空。");
            }
            if (StringUtils.isEmpty(superCode)) {
                throw new TesException("第" + (i + 1) + "行上级机构不能为空，如果该机构为总行，上级机构请填入【0】。");
            }
        }

        //            保存基本信息
        Map<String, Integer> codeIdInfo = new HashMap();
        for (List<String> line : data) {
            String name = line.get(1);
            String code = line.get(0);
            String address = line.get(3);
            Date now = new Date();
            Branch branch = new Branch();
            branch.setAddress(address);
            branch.setCreateTime(now);
            branch.setCode(code);
            branch.setName(name);
            branch.setUpdateTime(now);
            branch.setLevel(BranchLevelEnum.FIRST.getLevel());
            branch.setStatus(CommonStatusEnum.ENABLE.getStatus());
//            branchService.save(branch);
            baseDao.insert("tes.branch.insert", branch);

            codeIdInfo.put(code, branch.getId());
        }

        //根据数据处理level信息
        Map<String, Integer> codeLevelInfo = getCodeLevelInfo(data);

//            保存级联关系
        for (List<String> line : data) {
            String code = line.get(0);
            String superiorCode = line.get(2);
            Branch branch = new Branch();
            branch.setCode(code);
            branch.setId(codeIdInfo.get(code));
            branch.setLevel(codeLevelInfo.get(code));
            if (codeIdInfo.get(superiorCode) == null) {
                Branch record = new Branch();
                record.setCode(superiorCode);
                record = baseDao.selectOne("tes.branch.findByObject", record);
                if (record != null) {
                    codeIdInfo.put(superiorCode, record.getId());
                }
            }
            branch.setSuperiorId(codeIdInfo.get(superiorCode));
//            branchService.updateById(branch);
            baseDao.update("tes.branch.updateById", branch);

        }


    }

    @Override
    public Map<String, String> getBranchNameIdMap() {
        Map<String, String> map = new HashMap<>();
        List<Branch> list = baseDao.selectList("tes.branch.findByObject", null);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Branch branch : list) {
                map.put(branch.getName(), branch.getId() + "");
            }
        }
        return map;
    }

    @Override
    public Map<String, String> getBranchIdNameMap() {
        Map<String, String> map =  new HashMap<>();
        List<Branch> list = baseDao.selectList("tes.branch.findByObject", null);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Branch branch : list) {
                map.put(branch.getId() + "", branch.getName());
            }
        }
        return map;
    }
    @Override
    public void updateAll(JSONArray array) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Integer id = obj.getInteger("id");
            Integer pId = obj.getInteger("pId");
            Integer level = obj.getInteger("level");
            String name = obj.getString("name");
            Branch branch = baseDao.selectOne("tes.branch.selectById", id);
            if(branch == null){
                branch = new Branch();
                branch.setId(id);
                branch.setStatus(CommonStatusEnum.ENABLE.getStatus());
                branch.setSuperiorId(pId);
                branch.setCreateTime(new Date());
                branch.setLevel(level);
                branch.setUpdateTime(branch.getCreateTime());
                branch.setName(name);
                baseDao.insert("tes.branch.insert", branch);
            }else{
                if(branch.getLevel() == level && branch.getName().equals(name) && branch.getSuperiorId() == pId){
                    continue;
                }else{
                    branch.setSuperiorId(pId);
                    branch.setLevel(level);
                    branch.setName(name);
                    baseDao.update("tes.branch.updateById", branch);
                }
            }
        }
    }


    /**
     * 根据解析的excel获取对应的level
     *
     * @param data
     * @return
     */
    private Map<String, Integer> getCodeLevelInfo(List<List<String>> data) throws TesException {
        boolean flag = true;
        Map<String, Integer> codeLevelInfo = new HashMap<>();
        while (flag) {
            flag = false;
            for (List<String> line : data) {
                String code = line.get(0);
                String superiorCode = line.get(2);

//                该code已经处理
                if (codeLevelInfo.containsKey(code)) {
                    continue;
                }

                if (superiorCode.equals("")) {
                    codeLevelInfo.put(code, BranchLevelEnum.ZONGHANG.getLevel());
                    flag = true;
                }

                if (codeLevelInfo.containsKey(superiorCode)) {
                    codeLevelInfo.put(code, codeLevelInfo.get(superiorCode) + 1);
                    flag = true;
                }
                if (!codeLevelInfo.containsKey(superiorCode)) {
                    Branch branch = new Branch();
                    branch.setCode(superiorCode);
                    List<Branch> list = baseDao.selectList("tes.branch.findByObject", branch);
                    if (list != null && list.size() == 1) {
                        codeLevelInfo.put(code, list.get(0).getLevel() + 1);
                        flag = true;
                    } else {
                        throw new TesException("数据异常：没有找到对应的机构信息，机构码：" + superiorCode);
                    }
                }
            }
        }
        return codeLevelInfo;
    }

}

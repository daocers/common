package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.ExcelUtilNew;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.enums.BranchLevel;
import co.bugu.tes.model.Branch;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IBranchService;
import co.bugu.tes.service.IUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.apache.poi.sl.usermodel.PresetColor.Menu;

@Controller("/branchController/v1")
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    IBranchService branchService;
    @Autowired
    IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(BranchController.class);

    /**
     * 列表，分页显示
     *
     * @param branch    查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Branch branch, Integer curPage, Integer showCount, ModelMap model) {
        try {
            PageInfo<Branch> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = branchService.findByObject(branch, pageInfo);
            model.put("pi", pageInfo);
            model.put("branch", branch);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "branch/list";

    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public String toEdit(Integer id, ModelMap model) {
        JSONObject json = new JSONObject();
        try {
            Branch record = new Branch();
            record.setLevel(BranchLevel.ZONGHANG.getLevel());
            List<Branch> branchList = branchService.findByObject(record);
            model.put("branchList", branchList);
            Branch branch = branchService.findById(id);
            if (branch != null) {
                if (branch.getSuperiorId() != null) {
                    Branch superior = branchService.findById(branch.getSuperiorId());
                    if (superior != null) {
                        branch.setSuperiorName(superior.getName());
                    }
                }
            }
            json.put("code", 0);
            json.put("data", branch);
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
            json.put("code", -1);
            json.put("msg", "服务错误");
        }
        return json.toString();
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param branch
     * @param model
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Branch branch, ModelMap model) {
        JSONObject json = new JSONObject();
        try {
            Date now = new Date();
            branch.setUpdateTime(now);
            if (branch.getId() == null) {
                branch.setCreateTime(now);
                branchService.save(branch);
            } else {
                branchService.updateById(branch);
            }
            json.put("code", 0);
            json.put("id", branch.getId());

        } catch (Exception e) {
            logger.error("保存失败", e);
            json.put("code", -2);
            json.put("msg", "服务错误");
        }
        return json.toString();
    }

    /**
     * 异步请求 获取全部
     *
     * @param branch 查询条件
     * @return
     */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Branch branch) {
        try {
            List<Branch> list = branchService.findByObject(branch);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

//    @Menu(value = "批量添加", isView = true)
    @RequestMapping("/batchAdd")
    public String batchAdd(MultipartFile file, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            String fileName = file.getOriginalFilename();
            File tarFile = new File(fileName);
            file.transferTo(tarFile);
            List<List<String>> data = ExcelUtilNew.getData(tarFile);
            data.remove(0);
            logger.error("数据： {}", data);
            tarFile.delete();

//            //            保存基本信息
//            Map<String, Integer> codeIdInfo = new HashMap();
//            for(List<String> line: data){
//                String name = line.get(1);
//                String code = line.get(0);
//                String address = line.get(3);
//                Date now = new Date();
//                Branch branch = new Branch();
//                branch.setAddress(address);
//                branch.setCreateTime(now);
//                branch.setCode(code);
//                branch.setName(name);
//                branch.setUpdateTime(now);
//                branch.setLevel(Constant.BRANCH_LEVEL_TOP);
//                branch.setStatus(Constant.STATUS_ENABLE);
//                branchService.save(branch);
//
//                codeIdInfo.put(code, branch.getId());
//            }
//
//            //根据数据处理level信息
//            Map<String, Integer> codeLevelInfo = getCodeLevelInfo(data);
//
////            保存级联关系
//            for(List<String> line : data){
//                String code = line.get(0);
//                String superiorCode = line.get(2);
//                Branch branch = new Branch();
//                branch.setCode(code);
//                branch.setId(codeIdInfo.get(code));
//                branch.setLevel(codeLevelInfo.get(code));
//                branch.setSuperiorId(codeIdInfo.get(superiorCode));
//                branchService.updateById(branch);
//
//            }
            branchService.batchAdd(data);

        } catch (Exception e) {
            logger.error("批量导入数据失败", e);
            model.put("err", "导入数据失败");
            redirectAttributes.addFlashAttribute("err", "导入数据失败");
        }
        return "redirect:list.do";
    }

//    @Menu(value = "下载模板")
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
//            String[] arrs = new String[]{"机构编码", "机构名称", "上级机构", "地址", "联系电话", "负责人"};
            String[] arrs = new String[]{"机构编码", "机构名称", "上级机构", "地址"};
            ExcelUtilNew.download(request, response, "机构信息模板", Arrays.asList(arrs), null);
        } catch (Exception e) {
            logger.error("下载机构信息模板失败", e);
        }
    }


//    @Menu(value = "机构管理", isView = true)
    @RequestMapping("/manage")
    public String toManage(ModelMap model) {
        List<Branch> branchList = branchService.findByObject(null);
        JSONArray array = new JSONArray();
        for (Branch branch : branchList) {
            JSONObject json = new JSONObject();
            json.put("id", branch.getId());
            json.put("cId", branch.getId());
            json.put("level", branch.getLevel());
            json.put("name", branch.getName());
            json.put("pId", branch.getSuperiorId());
            array.add(json);
        }
        model.put("data", array.toString());
        return "branch/manage";
    }

//    @Menu(value = "获取机构信息")
    @RequestMapping("/getBranchInfo")
    @ResponseBody
    public String getBranchInfo(ModelMap model) {
        List<Branch> branchList = branchService.findByObject(null);
        JSONArray array = new JSONArray();
        for (Branch branch : branchList) {
            JSONObject json = new JSONObject();
            json.put("id", branch.getId());
            json.put("cId", branch.getId());
            json.put("level", branch.getLevel());
            json.put("name", branch.getName());
            json.put("pId", branch.getSuperiorId());
            array.add(json);
        }
        return array.toString();
    }

    /**
     * 异步请求 删除
     *
     * @param branch id
     * @return
     */
//    @Menu(value = "删除机构")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Branch branch) {
        JSONObject json = new JSONObject();

        try {
            //如果有关联用户，禁止删除
            User user = new User();
            user.setBranchId(branch.getId());
            List<User> users = userService.findByObject(user);
            if (users != null && users.size() > 0) {
                json.put("code", -1);
                json.put("msg", "该机构下有用户，不能删除，请先处理用户信息。");
            } else {
                json.put("code", 0);
                json.put("msg", "");
            }
            branchService.delete(branch);
        } catch (Exception e) {
            logger.error("删除失败", e);
            json.put("code", -2);
            json.put("msg", "服务器错误");
        } finally {
            return json.toString();
        }
    }

//    @Menu(value = "更新机构tree")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateAll(String info) {
        JSONObject json = new JSONObject();
        if (StringUtils.isEmpty(info)) {
            json.put("code", -1);
            json.put("msg", "机构信息为空");
        } else {
            JSONArray array = JSON.parseArray(info);
            branchService.updateAll(array);
            json.put("code", 0);
        }
        return json.toJSONString();
    }

}

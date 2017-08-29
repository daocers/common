package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.mybatis.SearchParamUtil;
import co.bugu.framework.core.mybatis.ThreadLocalUtil;
import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.core.util.ShiroSessionUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.enums.PaperPolicyType;
import co.bugu.tes.model.PaperPolicy;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.model.QuestionPolicy;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IPaperPolicyService;
import co.bugu.tes.service.IQuestionMetaInfoService;
import co.bugu.tes.service.IQuestionPolicyService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

//@Menu(value = "试卷策略管理", isBox = true)
@Controller
@RequestMapping("/paperPolicy")
public class PaperPolicyController {
    @Autowired
    IUserService userService;
    @Autowired
    IPaperPolicyService paperPolicyService;
    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;
    @Autowired
    IQuestionPolicyService questionPolicyService;

    private static Logger logger = LoggerFactory.getLogger(PaperPolicyController.class);

    /**
     * 列表，分页显示
     *
     * @param paperPolicy 查询数据
     * @param curPage     当前页码，从1开始
     * @param showCount   当前页码显示数目
     * @param model
     * @return
     */
//    @Menu(value = "试卷策略列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(PaperPolicy paperPolicy, Integer curPage, Integer showCount, ModelMap model, HttpServletRequest request) {
        try {
            SearchParamUtil.processSearchParam(paperPolicy, request);
            PageInfo<PaperPolicy> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = paperPolicyService.findByObject(paperPolicy, pageInfo);
            model.put("pi", pageInfo);
            model.put("paperPolicy", paperPolicy);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "paper_policy/list";

    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
//    @Menu(value = "编辑试卷策略", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            List<QuestionMetaInfo> questionMetaInfoList = questionMetaInfoService.findByObject(null);
            model.put("questionMetaInfoList", questionMetaInfoList);
            PaperPolicy paperpolicy = paperPolicyService.findById(id);
            if (paperpolicy == null) {
                paperpolicy = new PaperPolicy();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                paperpolicy.setCode(format.format(new Date()));//后续需要修改为加上对应的用户部门岗位信息
            }
//            保存试题策略信息
            model.put("paperPolicy", paperpolicy);
            List<String> checkedMetaInfoIds = new ArrayList<>();
            String content = paperpolicy.getContent();
            if (StringUtils.isNotEmpty(content)) {
                List<Map> list = JSON.parseArray(content, Map.class);
                for (Map map : list) {
                    checkedMetaInfoIds.add((String) map.get("questionMetaInfoId"));
                }
            }
            model.put("metaInfoIds", checkedMetaInfoIds);
            List<List<QuestionPolicy>> data = new ArrayList<>();
            for (int i = 0; i < questionMetaInfoList.size(); i++) {
                QuestionMetaInfo questionMetaInfo = questionMetaInfoList.get(i);
                QuestionPolicy questionPolicy = new QuestionPolicy();
                questionPolicy.setQuestionMetaInfoId(questionMetaInfo.getId());
                List<QuestionPolicy> questionPolicyList = questionPolicyService.findByObject(questionPolicy);
                if (questionMetaInfoList != null && questionPolicyList.size() > 0) {
                    data.add(questionPolicyList);
                }
            }
            model.put("data", data);

        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "paper_policy/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param paperPolicy
     * @param questionMetaInfoId 题型id
     * @param model
     * @return
     */
//    @Menu(value = "保存试卷策略")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(PaperPolicy paperPolicy, int[] questionMetaInfoId, ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Integer userId = ShiroSessionUtil.getUserId();
            User user = userService.findById(userId);
            if (user != null) {
                paperPolicy.setUpdateTime(new Date());
                paperPolicy.setCreateUserId(userId);
                paperPolicy.setUpdateUserId(userId);
                paperPolicy.setDepartmentId(user.getDepartmentId());
                paperPolicy.setStationId(user.getStationId());
                paperPolicy.setBranchId(user.getBranchId());
            } else {
                logger.error("获取用户信息失败， 用户id: {}", userId);
                redirectAttributes.addFlashAttribute("err", "用户信息异常");
                redirectAttributes.addFlashAttribute(paperPolicy);
                return "redirect:edit.do";
            }
            paperPolicy.setStatus(CommonStatusEnum.ENABLE.getStatus());
            if (paperPolicy.getPercentable() == null) {
                paperPolicy.setPercentable(1);
            }
            paperPolicy.setQuestionMetaInfoId(JSON.toJSONString(questionMetaInfoId));
            if (paperPolicy.getId() == null) {
                paperPolicy.setCreateTime(new Date());
                paperPolicyService.save(paperPolicy);
            } else {
                paperPolicyService.updateById(paperPolicy);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
            redirectAttributes.addFlashAttribute("paperPolicy", paperPolicy);
            redirectAttributes.addFlashAttribute("err", "保存试卷策略失败");
            return "redirect:edit.do";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param paperPolicy 查询条件
     * @return
     */
//    @Menu(value = "获取全部试卷策略")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(PaperPolicy paperPolicy, HttpServletRequest request) {
        try {
            List<PaperPolicy> list = null;
            User user = userService.findById((Integer) BuguWebUtil.getUserId(request));
            if (paperPolicy.getPrivaryType() == 0) {//公开的
                Map<String, Object> map = new HashMap();
                map.put("EQ_privaryType", 0);
                map.put("NEQ_branchId", user.getBranchId());
                ThreadLocalUtil.set(map);
                list = paperPolicyService.findByObject(null);
            } else {// 等于1时候为私有
                paperPolicy.setBranchId(user.getBranchId());
                list = paperPolicyService.findByObject(paperPolicy);
            }
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param paperpolicy id
     * @return
     */
//    @Menu(value = "删除试卷策略")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(PaperPolicy paperpolicy) {
        try {
            paperPolicyService.delete(paperpolicy);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }

    /**
     * 获取策略信息
     * 以方便阅读的方式显示
     *
     * @param id
     * @return
     */
//    @Menu(value = "获取试卷策略信息")
    @RequestMapping("/getPolicyInfo")
    @ResponseBody
    public String getPolicyInfo(Integer id) {
        PaperPolicy policy = paperPolicyService.findById(id);
        String content = policy.getContent();
        if (StringUtils.isNotEmpty(content)) {
            StringBuilder stringBuilder = new StringBuilder();
            JSONArray jsonArray = JSON.parseArray(content);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject data = (JSONObject) jsonArray.get(i);
                if (policy.getSelectType() == PaperPolicyType.POLICY.getType()) {
                    Integer questionMetaInfoId = data.getInteger("questionMetaInfoId");
                    Integer questionPolicyId = data.getInteger("questionPolicyId");
                    double score = data.getShort("score");
                    QuestionMetaInfo questionMetaInfo = questionMetaInfoService.findById(questionMetaInfoId);
                    QuestionPolicy questionPolicy = questionPolicyService.findById(questionPolicyId);
                    stringBuilder.append(questionMetaInfo.getName())
                            .append(": 试题策略 ")
                            .append(questionPolicy.getName())
                            .append(", ")
                            .append("共 ")
                            .append(questionPolicy.getCount())
                            .append(" 题, 每题 ")
                            .append(score)
                            .append(" 分\n");
                } else {
                    Integer questionMetaInfoId = data.getInteger("questionMetaInfoId");
                    Integer count = data.getInteger("count");
                    double score = data.getShort("score");
                    QuestionMetaInfo questionMetaInfo = questionMetaInfoService.findById(questionMetaInfoId);

                    stringBuilder.append(questionMetaInfo.getName())
                            .append(":共 ")
                            .append(count)
                            .append(" 题, 每题 ")
                            .append(score)
                            .append(" 分\n");
                }

            }
            return stringBuilder.toString();
        }
        return "";
    }
}

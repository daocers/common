package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.core.util.ShiroSessionUtil;
import co.bugu.framework.util.JedisUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.enums.SceneStatusEnum;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.*;
import co.bugu.tes.service.*;
import co.bugu.tes.util.QuestionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

//@Menu(value = "场次管理", isBox = true)
@Controller
@RequestMapping("/scene")
public class SceneController {
    private static Logger logger = LoggerFactory.getLogger(SceneController.class);

    @Autowired
    ISceneService sceneService;
    @Autowired
    IPaperPolicyService paperPolicyService;
    @Autowired
    IUserService userService;
    @Autowired
    IQuestionBankService bankService;

    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;
    @Autowired
    IQuestionPolicyService questionPolicyService;
    @Autowired
    IPaperService paperService;

    /**
     * 我开场的考试
     *
     * @param model
     * @param request
     * @return
     */
//    @Menu(value = "场次列表", isView = true)
    @RequestMapping("/list")
    public String list(String type, Integer showCount, Integer curPage, ModelMap model, HttpServletRequest request) throws Exception {
        Integer userId = BuguWebUtil.getUserId(request);

        Map<Integer, String> statusMap = SceneStatusEnum.getStatusInfo();
        model.put("statusMap", statusMap);
        if ("my".equals(type)) {
            Scene scene = new Scene();
            scene.setCreateUserId(userId);
            PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = sceneService.findByObject(scene, pageInfo);
            model.put("pi", pageInfo);
            return "scene/list";
        } else if ("join".equals(type)) {
            Paper paper = new Paper();
            paper.setUserId(userId);
            PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
            paperService.findByObject(paper, pageInfo);
            List<Scene> sceneList = new ArrayList<>();
            for (Paper item : pageInfo.getData()) {
                Integer sceneId = item.getSceneId();
                Scene scene = sceneService.findById(sceneId);
                sceneList.add(scene);
            }
            model.put("pi", pageInfo);
            model.put("sceneList", sceneList);
            return "scene/join";
        } else {
            model.put("msg", "非法参数");
            return "redirect:/index.do";
        }
    }

    /**
     * 我参加的考试的信息
     *
     * @param showCount
     * @param curPage
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/join")
    public String listOfJoin(Integer showCount, Integer curPage, ModelMap model) throws Exception {
        Integer userId = ShiroSessionUtil.getUserId();
        Paper paper = new Paper();
        paper.setUserId(userId);
        PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
        paperService.findByObject(paper, pageInfo);
        List<Scene> sceneList = new ArrayList<>();
        for (Paper item : pageInfo.getData()) {
            Integer sceneId = item.getSceneId();
            Scene scene = sceneService.findById(sceneId);
            sceneList.add(scene);
        }
        model.put("pi", pageInfo);
        model.put("sceneList", sceneList);
        return "scene/join";
    }

    /**
     * 我开场的
     *
     * @param showCount
     * @param curPage
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/mine")
    public String listOfMine(Integer showCount, Integer curPage, ModelMap model, HttpServletRequest request) throws Exception {
        Scene scene = new Scene();
        scene.setStatus(SceneStatusEnum.WAITING.getStatus());
        scene.setCreateUserId(ShiroSessionUtil.getUserId());
        PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
        sceneService.findByObject(scene, pageInfo);
        model.put("pi", pageInfo);
        return "scene/mine";
    }

    /**
     * 我开场的，获取列表详情
     *
     * @param scene     查询参数
     * @param showCount
     * @param curPage
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/list/mine/content")
    public String listOfMineContent(Scene scene, Integer showCount, Integer curPage, ModelMap model) throws Exception {
        scene.setCreateUserId(ShiroSessionUtil.getUserId());
        PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
        sceneService.findByObject(scene, pageInfo);
        model.put("pi", pageInfo);
        return "scene/list_content";
    }


    //    @Menu(value = "开场", isView = true)
    @RequestMapping("/index")
    public String index(Integer id, ModelMap model) {
        if (id != null) {
            QuestionMetaInfo metaInfo = new QuestionMetaInfo();
            metaInfo.setStatus(0);
            List<QuestionMetaInfo> metaInfos = questionMetaInfoService.findByObject(metaInfo);
            Map<Integer, String> metaInfoIdNameMap = new HashMap<>();
            for (QuestionMetaInfo info : metaInfos) {
                metaInfoIdNameMap.put(info.getId(), info.getName());
            }

            Scene scene = sceneService.findById(id);
            if (scene.getPaperPolicyId() != null) {
                PaperPolicy paperPolicy = paperPolicyService.findById(scene.getPaperPolicyId());
                paperPolicy.setContent(getPolicyContentForShow(paperPolicy, metaInfoIdNameMap));
                model.put("paperPolicy", paperPolicy);
            }
            if (scene == null) {
                model.put("err", "没有对应的场次信息");
            } else {
                model.put("scene", scene);
            }

            PaperPolicy policy = new PaperPolicy();
            policy.setStatus(0);
            List<PaperPolicy> policyList = paperPolicyService.findByObject(policy);
            for (PaperPolicy paperPolicy : policyList) {
                paperPolicy.setContent(getPolicyContentForShow(paperPolicy, metaInfoIdNameMap));
            }
            model.put("paperPolicyList", policyList);

            QuestionBank bank = new QuestionBank();
            bank.setStatus(0);
            List<QuestionBank> bankList = bankService.findByObject(bank);
            model.put("bankList", bankList);
        } else {
            //新开场，需要校验是否有场次信息未完善
            Scene record = new Scene();
            record.setStatus(SceneStatusEnum.WAITING.getStatus());
            record.setCreateUserId(ShiroSessionUtil.getUserId());
            List<Scene> sceneList = sceneService.findByObject(record);
            if (CollectionUtils.isNotEmpty(sceneList)) {
                model.put("err", "您有场次待完善，请先处理！");
            }
        }
        return "scene/index";
    }

    private String getPolicyContentForShow(PaperPolicy paperPolicy, Map<Integer, String> metaInfoIdNameMap) {
        if (paperPolicy != null) {
            String content = paperPolicy.getContent();
            Integer selectType = paperPolicy.getSelectType();
            StringBuffer buffer = new StringBuffer();
            if (0 == selectType) {//随机选择
                List<HashMap> list = JSON.parseArray(content, HashMap.class);
                for (Map<String, String> map : list) {
                    Integer questionMetaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
                    Integer count = Integer.valueOf(map.get("count"));
                    Double score = Double.valueOf(map.get("score"));
                    buffer.append(metaInfoIdNameMap.get(questionMetaInfoId))
                            .append(", ")
                            .append(count)
                            .append("题, 每题")
                            .append(score)
                            .append("分<br/>");
                }
            } else if (1 == selectType) {//选择试题策略
                List<HashMap> list = JSON.parseArray(content, HashMap.class);
                for (Map<String, String> map : list) {
                    Integer questionMetaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
                    Integer questionPolicyId = Integer.valueOf(map.get("questionPolicyId"));
                    Double score = Double.valueOf(map.get("score"));

                    QuestionPolicy questionPolicy = questionPolicyService.findById(questionPolicyId);
                    String questionPolicyName = questionPolicy.getName();
//                    String queContent = questionPolicy.getContent();
//                    List<List> quesList = JSON.parseArray(queContent, List.class);
//                    for(List<Integer> info : quesList){
//                        Integer count = info.remove(info.size() - 1);
//                    }
                    buffer.append(metaInfoIdNameMap.get(questionMetaInfoId))
                            .append(", ")
                            .append("试题策略")
                            .append("<a href='/questionPolicy/edit.do?id=")
                            .append(questionPolicyId)
                            .append("&type=detail'>" + questionPolicyName + "</a>")
                            .append(", 每题")
                            .append(score)
                            .append("分<br/>");
                }
            }
            return buffer.toString();
        }
        return null;
    }

    //    @Menu(value = "保存场次")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Scene scene, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            Integer userId = ShiroSessionUtil.getUserId();
//            计算endTime，防止时间被篡改
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(scene.getBeginTime());
            calendar.add(Calendar.MINUTE, scene.getDelay());
            calendar.add(Calendar.MINUTE, scene.getDuration());
            scene.setEndTime(calendar.getTime());

            if (scene.getChangePaper() == null) {
                scene.setChangePaper(1);//为空的时候表示不可更换试卷
            }

            if(scene.getId() == null){//新增
                Date now = new Date();
                scene.setCreateUserId(userId);
                scene.setCreateTime(now);

                User user = userService.findById(userId);
                scene.setBranchId(user.getBranchId());
                scene.setDepartmentId(user.getDepartmentId());
                scene.setStationId(user.getStationId());
                scene.setCode(UUID.randomUUID().toString());

                scene.setStatus(SceneStatusEnum.WAITING.getStatus());
                sceneService.save(scene);
            }else{//更新
                scene = sceneService.findById(scene.getId());
                if(scene.getStatus() > 1){
                    json.put("code", -1);
                    json.put("msg", "只有待完善和就绪状态的场次可以修改");
                    return json.toJSONString();
                }
                sceneService.updateById(scene);
            }
            json.put("code", 0);
            json.put("data", scene.getId());
        } catch (Exception e) {
            json.put("code", -1);
            json.put("msg", "场次信息保存失败");
        }
        return json.toJSONString();
    }


    //    @Menu(value = "设置授权码")
    @RequestMapping(value = "/saveAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public String saveAuthCode(Scene scene) {
        JSONObject json = new JSONObject();
        json.put("code", 0);

        Scene record = sceneService.findById(scene.getId());
        if(record != null){
            if(!scene.getAuthCode().equals(record.getAuthCode())){
                sceneService.updateById(scene);
            }
        }else{
            json.put("code", -1);
            json.put("err", "没有找到对应场次");
        }
        return json.toJSONString();
    }

    //    @Menu(value = "选择试卷策略")
    @RequestMapping(value = "/savePaperPolicy", method = RequestMethod.POST)
    @ResponseBody
    public String savePaperPolicy(Integer id, Integer bankId, Integer paperPolicyId) {
        JSONObject json = new JSONObject();
        json.put("code", 0);

        Scene scene = new Scene();
        scene.setId(id);
        scene.setBankId(bankId);
        scene.setPaperPolicyId(paperPolicyId);
        List<Scene> sceneList = sceneService.findByObject(scene);
        if(CollectionUtils.isEmpty(sceneList)){//修改了题库和试卷策略
            //做了修改，需要入库
            PaperPolicy paperPolicy = paperPolicyService.findById(paperPolicyId);
            List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
            Map<Integer, Double> metaInfoIdScoreMap = new HashMap<>();
            double totalScore = 0;
            for (HashMap<String, String> map : list) {
                Integer metaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
                Double score = Double.valueOf(map.get("score"));
                metaInfoIdScoreMap.put(metaInfoId, score);
                totalScore = totalScore + score.doubleValue();
            }
            scene.setMetaScoreInfo(JSON.toJSONString(metaInfoIdScoreMap));
            scene.setPercentable(paperPolicy.getPercentable());
            scene.setTotalScore(totalScore);
            sceneService.updateById(scene);
        }else {
            //没做修改，直接返回
        }
        return json.toJSONString();
    }

    //    @Menu(value = "确认开场")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(Integer id) {
        JSONObject json = new JSONObject();
        Scene scene = sceneService.findById(id);
        if (scene == null) {
            json.put("code", -1);
            json.put("err", "没有对应的场次");
        } else {
            //确认信息完毕，开场
            scene = new Scene();
            scene.setId(id);
            scene.setUpdateTime(new Date());
            scene.setUpdateUserId(ShiroSessionUtil.getUserId());
            scene.setStatus(SceneStatusEnum.READY.getStatus());
            sceneService.updateById(scene);
            json.put("code", 0);
        }

        return json.toJSONString();
    }

    //    @Menu(value = "校验试卷策略可用")
    @RequestMapping("/checkPaperPolicy")
    @ResponseBody
    public String checkPaperPolicy(Integer bankId, Integer paperPolicyId) throws TesException {
        PaperPolicy paperPolicy = paperPolicyService.findById(paperPolicyId);
        Integer selectType = paperPolicy.getSelectType();
        boolean res = true;
        if (0 == selectType) {//随机选择
            List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
            for (Map<String, String> map : list) {
                Integer questionMetaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
                Integer count = Integer.valueOf(map.get("count"));
                Double score = Double.valueOf(map.get("score"));
                long exist = QuestionUtil.getCountByMetaInfoAndBank(questionMetaInfoId, bankId);
                if (count > exist) {
                    res = false;
                    break;
                }
            }
        } else if (1 == selectType) {//选择试题策略
            List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
            for (Map<String, String> map : list) {
                Integer questionPolicyId = Integer.valueOf(map.get("questionPolicyId"));
                QuestionPolicy questionPolicy = questionPolicyService.findById(questionPolicyId);
                res = QuestionUtil.checkQuestionPolicy(bankId, questionPolicy);
                if (!res) {
                    break;
                }
            }
        }
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("data", res);
        return json.toJSONString();
    }

    //    @Menu(value = "场次预览", isView = true)
    @RequestMapping("/preview")
    public String preview(Integer id, ModelMap model) {
        QuestionMetaInfo metaInfo = new QuestionMetaInfo();
        metaInfo.setStatus(0);
        List<QuestionMetaInfo> metaInfos = questionMetaInfoService.findByObject(metaInfo);

        Map<Integer, String> metaInfoIdNameMap = new HashMap<>();
        for (QuestionMetaInfo info : metaInfos) {
            metaInfoIdNameMap.put(info.getId(), info.getName());
        }

        Scene scene = sceneService.findById(id);
        PaperPolicy paperPolicy = paperPolicyService.findById(scene.getPaperPolicyId());
        model.put("scene", scene);
        model.put("paperPolicy", paperPolicy);
        return "scene/preview-content";
    }
}

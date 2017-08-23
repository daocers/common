package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.util.JedisUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.enums.SceneStatusEnum;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.*;
import co.bugu.tes.service.*;
import co.bugu.tes.util.QuestionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        if("my".equals(type)){
            Scene scene = new Scene();
            scene.setCreateUserId(userId);
            PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = sceneService.findByObject(scene, pageInfo);
            model.put("pi", pageInfo);
            return "scene/list";
        }else if("join".equals(type)){
            Paper paper = new Paper();
            paper.setUserId(userId);
            PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
            paperService.findByObject(paper, pageInfo);
            List<Scene> sceneList = new ArrayList<>();
            for(Paper item: pageInfo.getData()){
                Integer sceneId = item.getSceneId();
                Scene scene = sceneService.findById(sceneId);
                sceneList.add(scene);
            }
            model.put("pi", pageInfo);
            model.put("sceneList", sceneList);
            return "scene/join";
        }else{
            model.put("msg", "非法参数");
            return "redirect:/index.do";
        }
    }

//    @Menu(value = "开场", isView = true)
    @RequestMapping("/index")
    public String index(Integer id, ModelMap model){
        QuestionMetaInfo metaInfo= new QuestionMetaInfo();
        metaInfo.setStatus(0);
        List<QuestionMetaInfo> metaInfos = questionMetaInfoService.findByObject(metaInfo);
        Map<Integer, String> metaInfoIdNameMap = new HashMap<>();
        for(QuestionMetaInfo info: metaInfos){
            metaInfoIdNameMap.put(info.getId(), info.getName());
        }

        if(id != null){
            Scene scene = sceneService.findById(id);
            if(scene.getPaperPolicyId() != null){
                PaperPolicy paperPolicy = paperPolicyService.findById(scene.getPaperPolicyId());
                paperPolicy.setContent(getPolicyContentForShow(paperPolicy, metaInfoIdNameMap));
                model.put("paperPolicy", paperPolicy);
            }
            if(scene == null){
                model.put("err", "没有对应的场次信息");
            }else{
                model.put("scene", scene);
            }
        }

        PaperPolicy policy = new PaperPolicy();
        policy.setStatus(0);
        List<PaperPolicy> policyList = paperPolicyService.findByObject(policy);
        for(PaperPolicy paperPolicy : policyList){
            paperPolicy.setContent(getPolicyContentForShow(paperPolicy, metaInfoIdNameMap));
        }
        model.put("paperPolicyList", policyList);

        QuestionBank bank = new QuestionBank();
        bank.setStatus(0);
        List<QuestionBank> bankList = bankService.findByObject(bank);
        model.put("bankList", bankList);
        return "scene/index";
    }

    private String getPolicyContentForShow(PaperPolicy paperPolicy, Map<Integer, String> metaInfoIdNameMap){
        if(paperPolicy != null){
            String content = paperPolicy.getContent();
            Integer selectType = paperPolicy.getSelectType();
            StringBuffer buffer = new StringBuffer();
            if(0 == selectType){//随机选择
                List<HashMap> list = JSON.parseArray(content, HashMap.class);
                for(Map<String, String> map: list){
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
            }else if(1 == selectType){//选择试题策略
                List<HashMap> list = JSON.parseArray(content, HashMap.class);
                for(Map<String, String> map: list){
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
    public String save(Scene scene, HttpServletRequest request){
        JSONObject json = new JSONObject();
        try {
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(scene.getBeginTime());
            calendar.add(Calendar.MINUTE, scene.getDelay());
            calendar.add(Calendar.MINUTE, scene.getDuration());
            scene.setEndTime(calendar.getTime());
            scene.setCreateUserId((Integer) BuguWebUtil.getUserId(request));
            scene.setCreateTime(now);
            if (scene.getChangePaper() == null) {
                scene.setChangePaper(1);//为空的时候表示不可更换试卷
            }
            Integer userId = (Integer) BuguWebUtil.getUserId(request);
            User user = userService.findById(userId);
            scene.setBranchId(user.getBranchId());
            scene.setDepartmentId(user.getDepartmentId());
            scene.setCreateUserId(userId);

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String index = JedisUtil.incr(Constant.SCENE_INDEX) + "";
            JedisUtil.expire(Constant.SCENE_INDEX, 60);
            //设置创建的用户id
            scene.setCode(format.format(now) + index);
            if (scene.getId() == null) {
                scene.setStatus(SceneStatusEnum.WAITING.getStatus());
                sceneService.save(scene);
            } else {
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
    public String saveAuthCode(Scene scene){
        JSONObject json = new JSONObject();
        sceneService.updateById(scene);
        json.put("code", 0);
        return json.toJSONString();
    }

//    @Menu(value = "选择试卷策略")
    @RequestMapping(value = "/savePaperPolicy", method = RequestMethod.POST)
    @ResponseBody
    public String savePaperPolicy(Integer id, Integer bankId, Integer paperPolicyId){
        Scene scene = new Scene();
        scene.setId(id);
        scene.setBankId(bankId);
        scene.setPaperPolicyId(paperPolicyId);

        PaperPolicy paperPolicy = paperPolicyService.findById(paperPolicyId);
        List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
        Map<Integer, Double> metaInfoIdScoreMap = new HashMap<>();
        double totalScore = 0;
        for(HashMap<String, String> map: list){
            Integer metaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
            Double score = Double.valueOf(map.get("score"));
            metaInfoIdScoreMap.put(metaInfoId, score);
            totalScore = totalScore + score.doubleValue();
        }
        scene.setMetaScoreInfo(JSON.toJSONString(metaInfoIdScoreMap));
        scene.setPercentable(paperPolicy.getPercentable());
        scene.setTotalScore(totalScore);
        sceneService.updateById(scene);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        return json.toJSONString();
    }

//    @Menu(value = "确认开场")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(Integer id, HttpServletRequest request){
        JSONObject json = new JSONObject();
        Scene scene = sceneService.findById(id);
        if(scene == null){
            json.put("code", -1);
            json.put("err", "没有对应的场次");
        }else{
            //生成试卷


            scene = new Scene();
            scene.setId(id);
            scene.setUpdateTime(new Date());
            scene.setUpdateUserId((Integer) BuguWebUtil.getUserId(request));

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
        if(0 == selectType){//随机选择
            List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
            for(Map<String, String> map: list){
                Integer questionMetaInfoId = Integer.valueOf(map.get("questionMetaInfoId"));
                Integer count = Integer.valueOf(map.get("count"));
                Double score = Double.valueOf(map.get("score"));
                Integer exist = QuestionUtil.getCountByPropItemId(questionMetaInfoId, bankId, null);
                if(count > exist){
                    res = false;
                    break;
                }
            }
        }else if(1 == selectType){//选择试题策略
            List<HashMap> list = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
            for(Map<String, String> map: list){
                Integer questionPolicyId = Integer.valueOf(map.get("questionPolicyId"));
                QuestionPolicy questionPolicy = questionPolicyService.findById(questionPolicyId);
                res = QuestionUtil.checkQuestionPolicy(bankId, questionPolicy);
                if(!res){
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
    public String preview(Integer id, ModelMap model){
        QuestionMetaInfo metaInfo= new QuestionMetaInfo();
        metaInfo.setStatus(0);
        List<QuestionMetaInfo> metaInfos = questionMetaInfoService.findByObject(metaInfo);

        Map<Integer, String> metaInfoIdNameMap = new HashMap<>();
        for(QuestionMetaInfo info: metaInfos){
            metaInfoIdNameMap.put(info.getId(), info.getName());
        }

        Scene scene = sceneService.findById(id);
        PaperPolicy paperPolicy = paperPolicyService.findById(scene.getPaperPolicyId());
        model.put("scene", scene);
        model.put("paperPolicy", paperPolicy);
        return "scene/preview-content";
    }
}

package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.util.DateUtil;
//import co.bugu.tes.annotation.Menu;
import co.bugu.tes.enums.PaperStatusEnum;
import co.bugu.tes.enums.SceneStatusEnum;
import co.bugu.tes.model.*;
import co.bugu.tes.model.question.CommonQuestion;
import co.bugu.tes.service.*;
import co.bugu.websocket.WebSocketSessionUtil;
import com.alibaba.fastjson.JSON;
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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by user on 2017/1/12.
 * 考试类
 * 处理考试
 */
//@Menu(value = "考试管理", isBox = true)
@Controller
@RequestMapping("/exam")
public class ExamController {
    private static Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    ISceneService sceneService;
    @Autowired
    IPaperService paperService;
    @Autowired
    ICommonQuestionService questionService;
    @Autowired
    IQuestionMetaInfoService metaInfoService;
    @Autowired
    IAnswerService answerService;
    @Autowired
    IPaperPolicyService paperPolicyService;

    /**
     * @param model
     * @param type  type= my,我开场的， type= join, 我参加的
     * @return
     */
//    @Menu(value = "考试场次列表", isView = true)
    @RequestMapping("/list")
    public String list(ModelMap model, String type, HttpServletRequest request, Integer curPage, Integer showCount) throws Exception {
        Integer userId = (Integer) BuguWebUtil.getUserId(request);
        if ("join".equals(type)) {
            Paper obj = new Paper();
            obj.setUserId(userId);
            PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
            paperService.findByObject(obj, pageInfo);

            PageInfo<Scene> scenePageInfo = new PageInfo<>();
            scenePageInfo.setCurPage(pageInfo.getCurPage());
            scenePageInfo.setCount(pageInfo.getCount());
            scenePageInfo.setPageSize(pageInfo.getPageSize());
            scenePageInfo.setShowCount(pageInfo.getShowCount());
            List<Scene> sceneList = new ArrayList<>();
            for (Paper paper : pageInfo.getData()) {
                Scene scene = sceneService.findById(paper.getSceneId());
                sceneList.add(scene);
            }
            scenePageInfo.setData(sceneList);
            model.put("pi", scenePageInfo);
            model.put("paperList", pageInfo.getData());
            return "scene/score";
        } else if ("my".equals(type)) {
            Scene scene = new Scene();
            scene.setCreateUserId(userId);
            PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
            sceneService.findByObject(scene, pageInfo);
            model.put("pi", pageInfo);
        } else {
            model.put("err", "无效参数");
        }
        return "scene/list";
    }

    /**
     * 考试入口，输入授权码页面
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "exam/index";
    }


    /**
     * 跳转考试须知界面
     *
     * @param
     * @return
     */
//    @Menu(value = "考试须知", isView = true)
    @RequestMapping("/note")
    public String toNote(String authCode, ModelMap model) {
        Scene scene = new Scene();
        scene.setAuthCode(authCode);
        scene.setStatus(SceneStatusEnum.BEGIN.getStatus());//查找已经开场的场次
        List<Scene> sceneList = sceneService.findByObject(scene);
        if (sceneList != null && sceneList.size() == 1) {
            scene = sceneList.get(0);
            model.put("scene", scene);
        } else {
            if (sceneList == null || sceneList.size() == 0) {
                model.put("err", "没有找到对应的场次");
            } else {
                model.put("err", "场次编码重复，请联系管理员");
            }
            return "exam/index";
        }
        return "exam/note";
    }


    /**
     * 跳转到考试界面
     *
     * @param scene
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
//    @Menu(value = "参加考试", isView = true)
    @RequestMapping("/exam")
    public String toExam(Scene scene, ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        try{
            if (scene.getId() == null) {
                throw new Exception("非法操作,没有找到对应的场次");
            }
            boolean continueFlag = true;
            scene = sceneService.findById(scene.getId());
            Date now = new Date();
            Date beginTime = scene.getBeginTime();
            Date endTime = scene.getEndTime();
            if (beginTime.compareTo(now) > 0) {
                redirectAttributes.addFlashAttribute("msg", "考试开始时间未到，请等待。");
                continueFlag = false;
            } else {
                Integer delay = scene.getDelay();
                if (delay == null || delay == 0) {//不考虑递延状态，考试时间内都可以进入
                    if (endTime.compareTo(now) <= 0) {
                        redirectAttributes.addFlashAttribute("msg", "考试已经结束，无法参加考试。");
                        continueFlag = false;
                    }
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(beginTime);
                    calendar.add(Calendar.MINUTE, delay);
                    Date lastEntry = calendar.getTime();
                    if (lastEntry.compareTo(now) < 0) {
                        continueFlag = false;
                        redirectAttributes.addFlashAttribute("msg", "开场超过" + delay + "分钟，无法参加考试");
                    }
                }
            }

            //不符合考试条件，跳转到考试列表页面
            if (continueFlag == false) {
                return "redirect:index.do";
            }


            Integer userId = BuguWebUtil.getUserId(request);
            Paper paper = new Paper();
            paper.setUserId(userId);
            paper.setSceneId(scene.getId());
            List<Paper> paperList = paperService.findByObject(paper);
            if (paperList != null && paperList.size() > 0) {//已经生成过试卷
                paper = paperList.get(0);
                if (paper.getStatus() == PaperStatusEnum.ENABLE.getStatus()) {
                    Date beginAnswerTime = paper.getBeginTime();
                    Date shouldEndTime = DateUtil.add(beginAnswerTime, Calendar.MINUTE, scene.getDuration());
                    if (shouldEndTime.before(now)) {
                        redirectAttributes.addFlashAttribute("msg", "考试时间已结束，无法继续答题");
                        return "redirect:index.do";
                    } else {
                        Long leftSecond = (shouldEndTime.getTime() - now.getTime());
                        model.put("timeLeft", DateUtil.formatLeft(leftSecond));
                    }
                } else if (paper.getStatus() == PaperStatusEnum.COMMITED.getStatus()) {
                    redirectAttributes.addFlashAttribute("msg", "您已提交试卷，无法再次作答");
                    return "redirect:index.do";
                } else {
                    redirectAttributes.addFlashAttribute("msg", "试卷状态异常，无法再次作答");
                    return "redirect:index.do";
                }
            } else {//没有试卷，新生成
                paper = paperService.generatePaperForUser(scene, userId);

                Integer leftMinute = 0;
                Long left = (endTime.getTime() - now.getTime()) / 1000;
                if (left.intValue() < scene.getDuration()) {
                    leftMinute = left.intValue();
                } else {
                    leftMinute = scene.getDuration();
                }

                model.put("timeLeft", leftMinute / 60 + "h" + leftMinute % 60 + "m" + "0s");
            }

            Map<Integer, CommonQuestion> questionMap = new HashMap<>();
            List<Integer> idList = new ArrayList<>();
            String content = paper.getContent();
            Map map = JSON.parseObject(content, Map.class);
            Iterator<Integer> keyIter = map.keySet().iterator();
            while (keyIter.hasNext()) {
                Integer key = keyIter.next();
                List<Integer> ids = (List<Integer>) map.get(key);
                idList.addAll(ids);
            }

            for (Integer id : idList) {
                questionMap.put(id, questionService.findById(id));
            }

            Map<Integer, String> answerMap = new HashMap<>();
            Answer obj = new Answer();
            obj.setPaperId(paper.getId());
            List<Answer> answers = answerService.findByObject(obj);
            for (Answer answer : answers) {
                answerMap.put(answer.getQuestionId(), answer.getAnswer());
            }

            model.put("answerMap", JSON.toJSONString(answerMap));

            model.put("questionMap", JSON.toJSONString(questionMap));
            model.put("questionIdList", JSON.toJSONString(idList));
            model.put("paper", paper);
            model.put("scene", scene);

/**
 * 题型信息
 * */
            Map<Integer, QuestionMetaInfo> metaInfoMap = new HashMap<>();
            List<QuestionMetaInfo> metaInfoList = metaInfoService.findByObject(null);
            for (QuestionMetaInfo metaInfo : metaInfoList) {
                metaInfoMap.put(metaInfo.getId(), metaInfo);
            }
            model.put("metaInfo", JSON.toJSONString(metaInfoMap));

            return "exam/examine";
        }catch (Exception e){
            logger.error("进入考试失败", e);
            throw new Exception("进入考试失败", e);
        }
    }


    /**
     * 提交问题答案 单选判断等暂时不需要
     *
     * @param questionId 题目id
     * @param answer     答案
     * @param timeLeft   提交时候剩余时间
     * @param paperId    试卷id
     * @return
     */
//    @Menu(value = "提交试题")
    @RequestMapping(value = "/commitQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String commitQuestion(Integer paperId, Integer questionId, String answer, String timeLeft) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        if (StringUtils.isEmpty(answer)) {
            json.put("code", -1);
            json.put("msg", "答案为空");
        } else {
            Answer obj = new Answer();
            obj.setPaperId(paperId);
            obj.setQuestionId(questionId);
            List<Answer> answers = answerService.findByObject(obj);
            if (answers != null && answers.size() == 1) {
                Answer ans = answers.get(0);
                ans.setAnswer(answer);
                ans.setTimeLeft(timeLeft);
                answerService.updateById(ans);
            } else {
                Answer ans = new Answer();
                ans.setAnswer(answer);
                ans.setPaperId(paperId);
                ans.setQuestionId(questionId);
                ans.setTimeLeft(timeLeft);
                answerService.save(ans);
            }
        }
        return json.toJSONString();
    }


    /**
     * 提交试卷
     *
     * @param paperId
     * @param answerInfo
     * @return
     */
//    @Menu(value = "提交试卷")
    @RequestMapping(value = "/commitPaper", method = RequestMethod.POST)
    @ResponseBody
    public String commitPaper(Integer paperId, String answerInfo) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        try {
            boolean saveAnsFlag = true;
            Map<String, String> answerMap = null;
            try {
                answerMap = JSON.parseObject(answerInfo, HashMap.class);
                answerService.savePaperAnswer(answerMap, paperId);
            } catch (Exception e) {
                json.put("code", 1);
                json.put("msg", "保存试卷答案失败");
                saveAnsFlag = false;
                logger.error("保存试卷答案失败", e);
            }

            Paper paper = paperService.findById(paperId);
            Scene scene = sceneService.findById(paper.getSceneId());
            Map metaInfoIdScoreMap = JSON.parseObject(scene.getMetaScoreInfo(), HashMap.class);
            Double score = paperService.computeScore(metaInfoIdScoreMap, answerMap, paperId);
            paper.setOriginMark(score.doubleValue() + "");
            if (scene.getPercentable().equals(0)) {//百分制，需要处理
                paper.setMark(score / Double.valueOf(scene.getTotalScore()).doubleValue() * 100 + "");
            } else {
                paper.setMark(paper.getOriginMark());
            }
            paper.setId(paperId);
            if (saveAnsFlag) {
                paper.setStatus(3);//保存成功
            } else {
                paper.setStatus(4);//保存失败
            }
            paperService.updateById(paper);
            json.put("data", paper.getMark());
        } catch (Exception e) {
            logger.error("提交试卷信息失败", e);
            json.put("code", -1);
            json.put("msg", "提交试卷失败");
        }
        return json.toJSONString();
    }

    /**
     * 获取试题
     *
     * @param questionId 试题id
     * @return
     */
//    @Menu(value = "考试获取试题信息")
    @RequestMapping("/getQuestion")
    @ResponseBody
    public String getQuestion(Integer questionId) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        try {
            CommonQuestion question = questionService.findById(questionId);
            if (question == null) {
                json.put("code", -1);
                json.put("msg", "没有查到对应的试题");
            } else {
                JSONObject ques = new JSONObject();
                ques.put("title", question.getTitle());
                ques.put("content", question.getContent());
                ques.put("metaInfoId", question.getMetaInfoId());
                ques.put("remark", question.getExtraInfo());
                json.put("data", ques);
            }
        } catch (Exception e) {
            logger.error("获取试题信息失败", e);
            json.put("code", -1);
            json.put("msg", "获取试题信息失败");
        }
        return json.toJSONString();
    }


//    @Menu(value = "向客户端发送消息")
    @ResponseBody
    @RequestMapping("/sendMessage")
    public String sendMessageToClient() throws IOException {
        JSONObject json = new JSONObject();
        json.put("type", 4);
        Map<Integer, WebSocketSession> sessionMap = WebSocketSessionUtil.getAllWebSocketSessions();
        for (Map.Entry<Integer, WebSocketSession> entry : sessionMap.entrySet()) {
            WebSocketSession session = entry.getValue();
            session.sendMessage(new TextMessage(json.toJSONString()));
        }
        return "";
    }

    /**
     * 向指定客户端发消息
     *
     * @param userId
     * @param message
     * @throws IOException
     */
    private void sendMessageToUserClient(Integer userId, String message) throws IOException {
        WebSocketSession session = null;
        try{
            session = WebSocketSessionUtil.getWebSocketSession(userId);
            session.sendMessage(new TextMessage(message));
        }finally {
            session.close();
        }
    }


}

package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.util.JedisUtil;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.enums.PaperPolicyType;
import co.bugu.tes.enums.PaperType;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.*;
import co.bugu.tes.service.*;
import co.bugu.tes.util.QuestionUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperServiceImpl extends BaseServiceImpl<Paper> implements IPaperService {
    @Autowired
    IAnswerService answerService;
    @Autowired
    ICommonQuestionService questionService;
    @Autowired
    IQuestionPolicyService questionPolicyService;
    @Autowired
    ISceneService sceneService;

    @Override
    public boolean generateAllPaper(Scene scene) throws Exception {
//        if (scene.getId() == null) {
//            throw new Exception("场次编号不能为空");
//        }
//        scene = baseDao.selectOne("tes.scene.selectById", scene.getId());
//        if (scene == null) {
//            throw new Exception("未找到对应的场次信息");
//        }
//        String joinUser = scene.getJoinUser();
//        if (StringUtils.isEmpty(joinUser)) {
//            throw new Exception("该场次没有选择参考人员");
//        }
//        List<Integer> userIds = JSON.parseArray(joinUser, Integer.class);
//        Integer paperPolicyId = scene.getPaperPolicyId();
//        if (paperPolicyId == null) {
//            throw new Exception("该场次没有选择试卷生成策略");
//        }
//        PaperPolicy paperPolicy = baseDao.selectOne("tes.paperPolicy.selectById", paperPolicyId);
//        String content = paperPolicy.getContent();
//        if (StringUtils.isEmpty(content)) {
//            throw new Exception("该试卷策略内容为空，请联系管理员确认!");
//        }
////        获取paperpolicy，并连带获取关联的questionpolicy
//        List<Integer> paperQeustionIdList = new ArrayList<>();
//        List<HashMap> infos = JSON.parseArray(content, HashMap.class);
//
////        策略模式，需要将关联的试题策略查询出来
//        if (paperPolicy.getSelectType() == PaperPolicyType.POLICY.getType()) {
//            List<QuestionPolicy> questionPolicyList = new ArrayList<>();
//            for (Map map : infos) {
//                Integer questionPolicyId = Integer.parseInt(map.get("questionPolicyId").toString());
//                QuestionPolicy questionPolicy = baseDao.selectOne("tes.questionPolicy.selectById", questionPolicyId);
//                hgjhjhkj
//                questionPolicyList.add(questionPolicy);
//            }
//            paperPolicy.setQuestionPolicyList(questionPolicyList);
//        }
//
//
//        if (scene.getPaperType() == PaperType.UNIFY.getType()) {
//            //统一试卷，每个人试题一致
//            Map<Integer, Collection<Integer>> resQuesIds = getPaperQuestionIds(scene.getBankId(), paperPolicy);
//            for (Integer userId : userIds) {
//                savePaper(userId, scene.getId(), resQuesIds);
//            }
//        } else if (scene.getPaperType() == PaperType.RANDOM.getType()) {
//            //每个人都随机，都不一样
//            for (Integer userId : userIds) {
//                Map<Integer, Collection<Integer>> resQuesIds = getPaperQuestionIds(scene.getBankId(), paperPolicy);
//                savePaper(userId, scene.getId(), resQuesIds);
//            }
//        } else if (scene.getPaperType() == PaperType.IMPORT.getType()) {
//            //教师导入试题
////            暂未开通，后续添加
//        } else if (scene.getPaperType() == PaperType.DEEPRANDOM.getType()) {
//            //深度随机，只设置需要的试题数量，随机生成试卷
//        } else if (scene.getPaperType() == PaperType.DEEPUNIFY.getType()) {
////            深度统一，只设置需要的试题数量，随机生成试卷，每个用户统一
//        }
        return true;
    }

    /**
     * 保存用户的试卷
     *
     * @param userId
     * @param sceneId
     * @param paperQuestionIdInfo
     */
    private Paper savePaper(Integer userId, Integer sceneId, Map<Integer, Collection<Integer>> paperQuestionIdInfo) {
        Paper paper = new Paper();
        paper.setUserId(userId);
        paper.setAnswerFlag(1);
        paper.setSceneId(sceneId);
        paper.setStatus(CommonStatusEnum.ENABLE.getStatus());
        paper.setBeginTime(new Date());
        paper.setContent(JSON.toJSONString(paperQuestionIdInfo));
//        paper.setQuestionIds(JSON.toJSONString(paperQuestionIdInfo));
        baseDao.insert("tes.paper.insert", paper);
//        int index = 0;
//        Iterator<Integer> keyIter = paperQuestionIdInfo.keySet().iterator();
//        while (keyIter.hasNext()) {
//            Integer key = keyIter.next();
//            Collection<String> collection = paperQuestionIdInfo.get(key);
//            for (String item : collection) {
//                //关系的添加可以放到提交试题时候操作，整个插入，提高试卷生成速度
//                Integer questionId = Integer.parseInt(item);
//                Map<String, Integer> param = new HashMap<>();
//                param.put("paperId", paper.getId());
//                param.put("questionId", questionId);
//                param.put("idx", index++);
//                baseDao.insert("tes.paper.addQues", param);
//            }
//        }

        return paper;
    }

    /**
     * 获取最终的试卷试题
     *
     * @param bankId      题库，如果为空，表示从所有题库中随机选择
     * @param paperPolicy
     * @return Map<MetaInfoId, QuestionIds>
     */
    private Map<Integer, Collection<Integer>> getPaperQuestionIds(Integer bankId, PaperPolicy paperPolicy) throws Exception {
        Map<Integer, Collection<Integer>> questionIdInfo = new HashMap<>();
//        List<Integer> res = new ArrayList<>();
//        //策略模式
//        if (paperPolicy.getSelectType() == PaperPolicyType.POLICY.getType()) {
//            //        策略模式，需要将关联的试题策略查询出来
//            if (paperPolicy.getSelectType() == PaperPolicyType.POLICY.getType()) {
//                List<HashMap> infos = JSON.parseArray(paperPolicy.getContent(), HashMap.class);
//                List<QuestionPolicy> questionPolicyList = new ArrayList<>();
//                for (Map map : infos) {
//                    Integer questionPolicyId = Integer.parseInt(map.get("questionPolicyId").toString());
//                    QuestionPolicy questionPolicy = baseDao.selectOne("tes.questionPolicy.selectById", questionPolicyId);
//                    questionPolicyList.add(questionPolicy);
//                }
//                paperPolicy.setQuestionPolicyList(questionPolicyList);
//            }
//
//            List<QuestionPolicy> questionPolicyList = paperPolicy.getQuestionPolicyList();
//            if (questionPolicyList == null || questionPolicyList.size() == 0) {
//                throw new Exception("试题策略不能为空");
//            }
//            for (QuestionPolicy questionPolicy : questionPolicyList) {
//                String quesContent = questionPolicy.getContent();
//                if (StringUtils.isEmpty(quesContent)) {
//                    throw new Exception("试题策略内容不能为空");
//                }
//                List<String> propertyList = JSON.parseArray(quesContent, String.class);
//                for (String item : propertyList) {
//                    //            int数组，最后一个元素是该属性组合的所选试题数量，前面的为试题属性组合
//                    List<Integer> propertyIds = JSON.parseArray(item, Integer.class);
//                    Integer count = propertyIds.remove(propertyIds.size() - 1);
//                    long existCount = QuestionUtil.getCountByMetaInfoAndBankAndAttr(questionPolicy.getQuestionMetaInfoId(), bankId, propertyIds);
//                    if (existCount < count) {
//                        throw new Exception("试题数量不足");
//                    }
//                    Set<String> questionIds = QuestionUtil.findByMetaInfoAndBankAndAttr(questionPolicy.getQuestionMetaInfoId(), bankId, propertyIds, count);
//                    List<Integer> quesIdList = new ArrayList<>();
//                    for (String id : questionIds) {
//                        quesIdList.add(Integer.parseInt(id));
//                    }
//                    questionIdInfo.put(questionPolicy.getQuestionMetaInfoId(), QuestionUtil.getResult(quesIdList, count));
////                    res.addAll(QuestionUtil.getResult(quesIdList, count));
//                }
//            }
//        } else {//普通模式
//            String content = paperPolicy.getContent();
//            List<Map> info = JSON.parseArray(content, Map.class);
//            for (Map<String, String> map : info) {
//                Integer questionMetaInfoId = Integer.parseInt(map.get("questionMetaInfoId"));
//                Integer count = Integer.parseInt(map.get("count"));
//                Double score = Double.valueOf(map.get("score"));
////                res.addAll(QuestionUtil.getResultByQuesMetaId(questionMetaInfoId, count));
////                questionIdInfo.put(questionMetaInfoId, QuestionUtil.findByMetaInfo(questionMetaInfoId, count));
//            }
//        }

        return questionIdInfo;
    }


    @Override
    public Paper generatePaperForUser(Scene scene, Integer userId) throws Exception {
        if (scene.getId() == null) {
            throw new Exception("场次编号不能为空");
        }
        scene = baseDao.selectOne("tes.scene.selectById", scene.getId());
        if (scene == null) {
            throw new Exception("未找到对应的场次信息");
        }
        Integer paperPolicyId = scene.getPaperPolicyId();
        if (paperPolicyId == null) {
            throw new Exception("该场次没有选择试卷生成策略");
        }
        PaperPolicy paperPolicy = baseDao.selectOne("tes.paperPolicy.selectById", paperPolicyId);
        String content = paperPolicy.getContent();
        if (StringUtils.isEmpty(content)) {
            throw new Exception("该试卷策略内容为空，请联系管理员确认!");
        }
//        获取paperpolicy，并连带获取关联的questionpolicy
        List<Integer> paperQeustionIdList = new ArrayList<>();


        Paper paper = null;

        if (scene.getPaperType() == PaperType.UNIFY.getType()) {
            //统一试卷，每个人试题一致
            Map<Integer, Collection<Integer>> resQuesIds = getMetaAndQuestions(scene.getBankId(), paperPolicy);
            paper = savePaper(userId, scene.getId(), resQuesIds);
        } else if (scene.getPaperType() == PaperType.RANDOM.getType()) {
            //每个人都随机，都不一样
            Map<Integer, Collection<Integer>> resQuesIds = getMetaAndQuestions(scene.getBankId(), paperPolicy);
            paper = savePaper(userId, scene.getId(), resQuesIds);
        } else if (scene.getPaperType() == PaperType.IMPORT.getType()) {
            //教师导入试题
//            暂未开通，后续添加
        } else if (scene.getPaperType() == PaperType.DEEPRANDOM.getType()) {
            //深度随机，只设置需要的试题数量，随机生成试卷
        } else if (scene.getPaperType() == PaperType.DEEPUNIFY.getType()) {
//            深度统一，只设置需要的试题数量，随机生成试卷，每个用户统一
        }
        return paper;
    }

    @Override
    public Double computeScore(Map<Integer, Double> metaInfoIdScoreMap, Map<String, String> answerInfo, Integer paperId) {
        Paper paper = baseDao.selectOne("tes.paper.selectById", paperId);
        if (paper == null) {
            return null;
        } else {
            String content = paper.getContent();
            if (StringUtils.isNotEmpty(content)) {
                Double amount = 0.00;
                Map<Integer, List<Integer>> map = JSON.parseObject(content, HashMap.class);
                Iterator<Integer> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    Integer questionMetaInfoId = iter.next();
                    List<Integer> questionIds = map.get(questionMetaInfoId);
                    Double score = metaInfoIdScoreMap.get(questionMetaInfoId);
                    for (Integer questionId : questionIds) {
                        boolean res = checkQuestion(questionId, paperId, answerInfo.get(questionId + ""));
                        if (res) {
                            amount += score;
                        }
                    }
                }
                return amount;
            }
        }
        return null;
    }

    /**
     * 判断作答信息是否正确
     *
     * @param questionId
     * @param paperId
     * @param yourAnswer
     * @return
     */
    private boolean checkQuestion(Integer questionId, Integer paperId, String yourAnswer) {
        if (StringUtils.isEmpty(yourAnswer)) {
            return false;
        }
        String right = null;
        List<String> ansList = JedisUtil.hmget(Constant.QUESTION_ANSWER, questionId + "");
        if (CollectionUtils.isNotEmpty(ansList)) {
            right = ansList.get(0);
        } else {
            Answer answer = new Answer();
            answer.setPaperId(paperId);
            answer.setQuestionId(questionId);
            List<Answer> list = answerService.findByObject(answer);
            if (CollectionUtils.isEmpty(list)) {
                return false;
            } else {
                right = list.get(0).getAnswer();
            }
        }
        return right.equals(yourAnswer);
    }


    /**
     * 获取最终的试卷试题
     * <p>
     * 策略模式，不指定题库直接按照题型选择
     * 指定题库，不指定属性，直接按照 题型-题库选择
     * 指定题库，属性， 按照 题型-题库-属性选择
     *
     * @param bankId      题库，不指定题库直接按照题型选择
     * @param paperPolicy
     * @return Map<MetaInfoId, QuestionIds>
     */
    private Map<Integer, Collection<Integer>> getMetaAndQuestions(Integer bankId, PaperPolicy paperPolicy) throws Exception {
        Map<Integer, Collection<String>> questionIdInfo = new HashMap<>();//metaInfoId, questionIdSet
        //策略模式
        if (paperPolicy.getSelectType() == PaperPolicyType.POLICY.getType()) {
            //        策略模式，需要将关联的试题策略查询出来
            List<HashMap> paperPolicyInfo = JSON.parseArray(paperPolicy.getContent(), HashMap.class);

//            paperPolicy中没有questionPolicy的信息，先查询
            if (CollectionUtils.isEmpty(paperPolicy.getQuestionPolicyList())) {
                List<QuestionPolicy> questionPolicyList = new ArrayList<>();
                for (Map map : paperPolicyInfo) {
                    Integer questionMetaInfoId = Integer.parseInt(map.get("questionMetaInfoId").toString());
                    Integer questionPolicyId = Integer.parseInt(map.get("questionPolicyId").toString());

                    QuestionPolicy questionPolicy = baseDao.selectOne("tes.questionPolicy.selectById", questionPolicyId);
                    questionPolicyList.add(questionPolicy);
                }
                paperPolicy.setQuestionPolicyList(questionPolicyList);
            }

            List<QuestionPolicy> questionPolicyList = paperPolicy.getQuestionPolicyList();
            if (questionPolicyList == null || questionPolicyList.size() == 0) {
                throw new Exception("试题策略不能为空");
            }
            for (QuestionPolicy questionPolicy : questionPolicyList) {
                Set<String> queSet = new HashSet<>();//存放试题策略选出的试题
                String quesContent = questionPolicy.getContent();
                if (StringUtils.isEmpty(quesContent)) {
                    throw new Exception("试题策略内容不能为空");
                }
                List<String> propertyList = JSON.parseArray(quesContent, String.class);
                for (String item : propertyList) {
                    //            int数组，最后一个元素是该属性组合的所选试题数量，前面的为试题属性组合
                    List<Integer> propertyIds = JSON.parseArray(item, Integer.class);
                    Integer count = propertyIds.remove(propertyIds.size() - 1);
                    long existCount = QuestionUtil.getCountByMetaInfoAndBankAndAttr(questionPolicy.getQuestionMetaInfoId(), bankId, propertyIds);
                    if (existCount < count) {
                        throw new Exception("试题数量不足");
                    }
                    Set<String> questionIds = QuestionUtil.findByMetaInfoAndBankAndAttr(questionPolicy.getQuestionMetaInfoId(), bankId, propertyIds, count);
                    queSet.addAll(questionIds);//当前试题策略明细查询出来的题目
                }
                questionIdInfo.put(questionPolicy.getQuestionMetaInfoId(), queSet);
            }
        } else {//普通模式
            String content = paperPolicy.getContent();
            List<Map> info = JSON.parseArray(content, Map.class);
            for (Map<String, String> map : info) {
                Integer questionMetaInfoId = Integer.parseInt(map.get("questionMetaInfoId"));
                Integer count = Integer.parseInt(map.get("count"));
                Double score = Double.valueOf(map.get("score"));
                Set<String> res = QuestionUtil.findByMetaInfoAndBank(questionMetaInfoId, bankId, count);
                questionIdInfo.put(questionMetaInfoId, res);
            }
        }

        /**
         * 将string类型的id转换为int类型
         * */
        Map<Integer, Collection<Integer>> res = new HashMap<>();
        Iterator<Integer> keyIter = questionIdInfo.keySet().iterator();
        while(keyIter.hasNext()){
            Integer key = keyIter.next();
            Collection<String> set = questionIdInfo.get(key);
            Collection<Integer> newSet = new HashSet<>();
            for(String item: set){
                newSet.add(Integer.parseInt(item));
            }
            res.put(key, newSet);
        }

        return res;
    }

}

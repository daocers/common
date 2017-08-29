package co.bugu.tes.service.impl;


import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.core.util.JedisUtil;
import co.bugu.tes.model.question.CommonQuestion;
import co.bugu.tes.service.ICommonQuestionService;
import co.bugu.tes.util.QuestionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CommonQuestionServiceImpl extends BaseServiceImpl<CommonQuestion> implements ICommonQuestionService {
    private static Logger logger = LoggerFactory.getLogger(CommonQuestionServiceImpl.class);
//    @Autowired
//    BaseDao baseDao;

//    @Override
//    public int save(Question question, List<Map<String, Integer>> xList) {
//        int num = baseDao.insert("tes.commonQuestion.insert", question);
//        for (Map<String, Integer> map : xList) {
//            map.put("questionId", question.getId());
//            baseDao.insert("tes.commonQuestion.addToPropItem", map);
//
//            //            将属性信息添加到redis中的set上
//            Integer propItemId = map.get("propItemId");
//            JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + propItemId, question.getId() + "");
//        }
//        return num;
//    }

//    @Override
//    public int updateById(Question question) {
//        return baseDao.update("tes.commonQuestion.updateById", question);
//    }


    @Override
    public int saveOrUpdate(CommonQuestion question, List<Map<String, Integer>> xList) {
        int num;
        if (question.getId() == null) {
            num = baseDao.insert("tes.commonQuestion.insert", question);
        } else {
            num = baseDao.update("tes.commonQuestion.updateById", question);

            baseDao.delete("tes.commonQuestion.removeFromPropItem", question.getId());
        }
        for (Map<String, Integer> map : xList) {
            map.put("questionId", question.getId());
            baseDao.insert("tes.commonQuestion.addToPropItem", map);
        }
        QuestionUtil.updateCacheAfterUpdate(question);
        return num;
    }

//    @Override
//    public List<Map<String, Object>> selectCountOfPropInfo(Integer metaInfoId) {
//        return baseDao.selectList("tes.commonQuestion.selectCountOfPropInfo", metaInfoId);
//    }

    @Override
    public int batchAdd(List<CommonQuestion> questionList, int batchSize){
        List<CommonQuestion> list = new ArrayList<>();

        for (CommonQuestion question: questionList){//批量提交
            list.add(question);
            if(list.size() == batchSize){
                baseDao.insert("tes.commonQuestion.batchAdd", list);
                list.clear();
            }
        }

        //最后不满足一批次的，再次提交
        if(list.size() > 0){
            baseDao.insert("tes.commonQuestion.batchAdd", list);
        }

        //新启用线程 批量更新试题缓存
        new Thread(){
            @Override
            public void run() {
                logger.info("开始批量更新试题缓存");
                QuestionUtil.batchUpdateCacheAfterUpdate(questionList);
            }
        }.start();
        return questionList.size();
    }

    @Override
    public Set<String> findByMetaInfoAndBankAndAttr(Integer questionMetaInfoId,
                                                    Integer questionBankId, List<Integer> ids, Integer count) throws Exception {
        return QuestionUtil.findByMetaInfoAndBankAndAttr(questionMetaInfoId, questionBankId, ids, count);
    }

    @Override
    public Long getCountByMetaInfoAndBankAndAttr(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids){
        return QuestionUtil.getCountByMetaInfoAndBankAndAttr(questionMetaInfoId, questionBankId, ids);
    }

    @Override
    public int batchAdd(List<CommonQuestion> questionList, List<List<Integer>> propItemIdList) throws Exception {
        if(questionList.size() != propItemIdList.size()){
            throw new Exception("批量添加试题失败：属性信息不符");
        }
        int num = 0;
        for(int i = 0; i < questionList.size(); i++){
            CommonQuestion question = questionList.get(i);
            baseDao.insert("tes.commonQuestion.insert", question);
            for(Integer id: propItemIdList.get(i)){
                Map<String, Integer> map = new HashMap<>();
                map.put("questionId", question.getId());
                map.put("propItemId", id);
                baseDao.insert("tes.commonQuestion.addToPropItem", map);
            }
            QuestionUtil.updateCacheAfterUpdate(question);
            num++;
        }
        return num  ;
    }


    @Override
    public PageInfo findByObject(CommonQuestion record, Integer showCount, Integer curPage) throws Exception {
        PageInfo<CommonQuestion> pageInfo = new PageInfo<CommonQuestion>(showCount, curPage);
        baseDao.listByObject("tes.commonQuestion.findByObject", record, pageInfo);
        if(pageInfo.getData().size() > 0){
            for(CommonQuestion question: pageInfo.getData()){
                question.setPropertyItemList(baseDao.selectList("tes.propertyItem.findPropItemByQuestionId", question));
                question.setQuestionMetaInfo(baseDao.selectOne("tes.commonQuestionMetaInfo.selectById", question.getMetaInfoId()));
            }
        }
        return pageInfo;
    }

    @Override
    public CommonQuestion findById(Integer id) {
        logger.info("查找试题， id: {}", id);
        CommonQuestion question = null;
        try{
            question = JedisUtil.getObject(id, CommonQuestion.class);
        }catch (Exception e){
            logger.error("jedis getObject异常", e);
        }
        if(question == null){
            question = baseDao.selectOne("tes.commonQuestion.selectById", id);
            try {
                JedisUtil.setObject(JedisUtil.getKey(id, question), question);
            } catch (IOException e) {
                logger.error("jedis setObject异常", e);
            }
//            if(question != null){
//                question.setPropertyItemList(baseDao.selectList("tes.propertyItem.findPropItemByQuestionId", question.getId()));
//                question.setQuestionMetaInfo(baseDao.selectOne("tes.questionMetaInfo.selectById", question.getMetaInfoId()));
//            }
//            try {
//                JedisUtil.setObject(question);
//            } catch (Exception e) {
//                logger.error("jedisUtil setObject异常", e);
//            }
        }
        return question;
    }


}

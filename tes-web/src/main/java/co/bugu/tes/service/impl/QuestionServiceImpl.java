package co.bugu.tes.service.impl;


import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.util.JedisUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Question;
import co.bugu.tes.service.IQuestionService;
import co.bugu.tes.util.QuestionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements IQuestionService {
    private static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
//    @Autowired
//    BaseDao baseDao;

//    @Override
//    public int save(Question question, List<Map<String, Integer>> xList) {
//        int num = baseDao.insert("tes.question.insert", question);
//        for (Map<String, Integer> map : xList) {
//            map.put("questionId", question.getId());
//            baseDao.insert("tes.question.addToPropItem", map);
//
//            //            将属性信息添加到redis中的set上
//            Integer propItemId = map.get("propItemId");
//            JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + propItemId, question.getId() + "");
//        }
//        return num;
//    }

//    @Override
//    public int updateById(Question question) {
//        return baseDao.update("tes.question.updateById", question);
//    }


    @Override
    public int saveOrUpdate(Question question, List<Map<String, Integer>> xList) throws TesException {
        int num;
        if (question.getId() == null) {
            num = baseDao.insert("tes.question.insert", question);
        } else {
            num = baseDao.update("tes.question.updateById", question);

            baseDao.delete("tes.question.removeFromPropItem", question.getId());
        }
        for (Map<String, Integer> map : xList) {
            map.put("questionId", question.getId());
            baseDao.insert("tes.question.addToPropItem", map);
        }
        QuestionUtil.updateCacheAfterUpdate(question);
        return num;
    }

//    @Override
//    public List<Map<String, Object>> selectCountOfPropInfo(Integer metaInfoId) {
//        return baseDao.selectList("tes.question.selectCountOfPropInfo", metaInfoId);
//    }

    @Override
    public int batchAdd(List<Question> questionList) throws TesException {
        int num = 0;
        for(Question question: questionList){
            baseDao.insert("tes.question.insert", question);
            num++;
            QuestionUtil.updateCacheAfterUpdate(question);
        }
        return num;
    }

    @Override
    public Set<String> findQuestionByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException {
//        String[] keys = new String[ids.length];
//        for(int i = 0; i < ids.length; i++){
//            keys[i] = Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_"+ ids[i];
//        }
//        return JedisUtil.sinterForObj(keys);
        return QuestionUtil.findQuestionByPropItemId(questionMetaInfoId, questionBankId, ids);
    }

    @Override
    public int getCountByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException {
//        String[] keys = new String[ids.length];
//        for(int i = 0; i < ids.length; i++){
//            keys[i] = Constant.QUESTION_PROPITEM_ID +questionMetaInfoId + "_" + ids[i];
//        }
//        return JedisUtil.sinterForSize(keys);
        return QuestionUtil.getCountByPropItemId(questionMetaInfoId, questionBankId, ids);
    }

    @Override
    public int batchAdd(List<Question> questionList, List<List<Integer>> propItemIdList) throws TesException {
        if(questionList.size() != propItemIdList.size()){
            throw new TesException("批量添加试题失败");
        }
        int num = 0;
        for(int i = 0; i < questionList.size(); i++){
            Question question = questionList.get(i);
            baseDao.insert("tes.question.insert", question);
            for(Integer id: propItemIdList.get(i)){
                Map<String, Integer> map = new HashMap<>();
                map.put("questionId", question.getId());
                map.put("propItemId", id);
                baseDao.insert("tes.question.addToPropItem", map);
            }
            QuestionUtil.updateCacheAfterUpdate(question);
            num++;
        }
        return num  ;
    }


    @Override
    public PageInfo findByObject(Question record, Integer showCount, Integer curPage) throws Exception {
        PageInfo<Question> pageInfo = new PageInfo<Question>(showCount, curPage);
        baseDao.listByObject("tes.question.findByObject", record, pageInfo);
        if(pageInfo.getData().size() > 0){
            for(Question question: pageInfo.getData()){
                question.setPropertyItemList(baseDao.selectList("tes.propertyItem.findPropItemByQuestionId", question));
                question.setQuestionMetaInfo(baseDao.selectOne("tes.questionMetaInfo.selectById", question.getMetaInfoId()));
            }
        }
        return pageInfo;
    }

    @Override
    public Question findById(Integer id){
        Question question = null;
        try{
            question = JedisUtil.getJson(Constant.QUESTION_PERFIX + id, Question.class);
        }catch (Exception e){
            logger.error("jedis getJson异常", e);
        }
        if(question == null){
            question = baseDao.selectOne("tes.question.selectById", id);
            if(question != null){
                question.setPropertyItemList(baseDao.selectList("tes.propertyItem.findPropItemByQuestionId", question.getId()));
                question.setQuestionMetaInfo(baseDao.selectOne("tes.questionMetaInfo.selectById", question.getMetaInfoId()));
            }
            JedisUtil.setJson(Constant.QUESTION_PERFIX + id, question);
        }
        return question;
    }


}

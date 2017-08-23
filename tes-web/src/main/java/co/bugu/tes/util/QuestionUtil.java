package co.bugu.tes.util;

import co.bugu.framework.util.JedisUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Question;
import co.bugu.tes.model.QuestionPolicy;
import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 试题辅助类
 * 用于获取指定属性的试题 的数量或者指定属性的试题的id信息
 * Created by daocers on 2017/2/13.
 */
public class QuestionUtil {

    /**
     * 更新试题后更新缓存
     * @param question
     * @throws TesException
     */
    public static void updateCacheAfterUpdate(Question question) throws TesException {
        String questionId = question.getId().toString();

        Set<String> keys =  JedisUtil.keysLike(Constant.QUESTION_PROPITEM_ID);
        for(String key: keys){
            //先删除缓存中所有关于该列的缓存
            JedisUtil.srem(key, questionId);
        }


        //添加更新后的缓存数量
        Integer questionMetaInfoId = question.getMetaInfoId();
        Integer bankId = question.getQuestionBankId();
        List<Integer> propItemIdList = JSON.parseArray(question.getPropItemIdInfo(), Integer.class);

        //不指定题目属性
        JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId, questionId);
        JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" + Constant.QUESTION_BANK_ID +bankId, questionId);
        //指定题目属性
        for (Integer id : propItemIdList) {
            JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" +
                    Constant.QUESTION_BANK_ID + bankId + "_" + id, questionId);
            JedisUtil.sadd(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId
                    + "_" + id, questionId);
        }
    }

    /**
     * 根据题型id，属性id组合获取符合条件的试题的id
     *
     * @param questionMetaInfoId
     * @param questionBankId     题库id
     * @param ids                属性组合
     * @return
     * @throws TesException
     */
    public static Set<String> findQuestionByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException {
        if(ids == null){//不指定属性集合
            if(questionBankId == null){
                return JedisUtil.getAllOfSet(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId);
            }else{
                return JedisUtil.getAllOfSet(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId
                        + "_" + Constant.QUESTION_BANK_ID + questionBankId);
            }
        }else{//指定属性集合
            String[] keys = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                if (questionBankId != null && questionBankId != 0) {
                    keys[i] = Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" + Constant.QUESTION_BANK_ID +
                            + questionBankId + "_" + ids.get(i);
                } else {
                    keys[i] = Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" + ids.get(i);
                }
            }
            return JedisUtil.sinterForObj(keys);
        }

    }

    /**
     * 根据题型id，属性id组合获取符合条件的试题的数量
     *
     * @param questionMetaInfoId
     * @param questionBankId     题库id
     * @param ids
     * @return
     * @throws TesException
     */
    public static int getCountByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException {
        if(ids == null){
            if(questionBankId == null){
                return JedisUtil.scard(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId).intValue();
            }else{
                return JedisUtil.scard(Constant.QUESTION_PROPITEM_ID + questionMetaInfoId
                        + "_" + Constant.QUESTION_BANK_ID + questionBankId).intValue();
            }
        }else{
            String[] keys = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                if (questionBankId != null && questionBankId != 0) {//指定题库
                    keys[i] = Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" + Constant.QUESTION_BANK_ID +
                            + questionBankId + "_" + ids.get(i);
                } else {//不分题库，在所有的题目信息中获取
                    keys[i] = Constant.QUESTION_PROPITEM_ID + questionMetaInfoId + "_" + ids.get(i);
                }
            }
            return JedisUtil.sinterForSize(keys);
        }

    }

    /**
     * 根据数量获取最终的试题id组合
     *
     * @param questionIdList
     * @param count          需要选择的数量
     * @return
     */
    public static Set<Integer> getResult(List<Integer> questionIdList, Integer count) throws Exception {
        if (count > questionIdList.size()) {
            throw new Exception("需要选择的数量大于试题集合的数量");
        }
        Set<Integer> res = new HashSet<>();
        Collections.shuffle(questionIdList);
        for (int i = 0; i < count; i++) {
            res.add(questionIdList.get(i));
        }
        return res;
    }


    /**
     * 根据试题类型随机获取一定数量的试题
     *
     * @param questionMetaInfoId 试题类型id
     * @param count              需要的数量
     * @return
     */
    public static List<Integer> getResultByQuesMetaId(Integer questionMetaInfoId, Integer count) {
        return null;
    }

    /**
     * 检查试题策略是否可用（也就是题库题量是否可用）
     * @param bankId
     * @param questionPolicy
     * @return
     * @throws TesException
     */
    public static boolean checkQuestionPolicy(Integer bankId, QuestionPolicy questionPolicy) throws TesException {
        String content = questionPolicy.getContent();
        List<List> list = JSON.parseArray(content, List.class);
        for(List<Integer> item: list){
            Integer count = item.remove(item.size() - 1);
            Integer exist = getCountByPropItemId(questionPolicy.getQuestionMetaInfoId(), bankId, item);
            if(count > exist){
                return false;
            }
        }
        return true;
    }


}

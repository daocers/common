package co.bugu.tes.util;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.exception.TesJedisException;
import co.bugu.framework.core.util.ApplicationContextUtil;
import co.bugu.framework.core.util.JedisUtil;
import co.bugu.tes.DataException;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.QuestionPolicy;
import co.bugu.tes.model.question.CommonQuestion;
import co.bugu.tes.service.ICommonQuestionService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * 试题辅助类
 * 用于获取指定属性的试题 的数量或者指定属性的试题的id信息
 * Created by daocers on 2017/2/13.
 */
public class QuestionUtil {
    private static Logger logger = LoggerFactory.getLogger(QuestionUtil.class);

    /**
     * 生成缓存的key
     *
     * @param items
     * @return
     */
    private static String genKey(Integer... items) {
        StringBuilder builder = new StringBuilder();
        if (ArrayUtils.isNotEmpty(items)) {
            builder.append(Constant.QUESTION_ATTR_INFO).append("_");
            for (Integer item : items) {
                builder.append(item)
                        .append("_");
            }
        }
        if (builder.length() > 0) {
            return builder.substring(0, builder.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 更新试题后更新缓存
     * set存放， key依次为：
     * xxx_题型
     * XXX_题型_题库
     * XXX_题型_题库_属性值
     * <p>
     * 注意，每个属性值需要处理一次
     *
     * @param question
     * @throws TesJedisException
     */
    public static void updateCacheAfterUpdate(CommonQuestion question) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();

            updateCacheForQuestion(jedis, question);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 更新试题后更新缓存
     * 批量更新，用于批量插入试题使用
     * set存放， key依次为：
     * xxx_题型
     * XXX_题型_题库
     * XXX_题型_题库_属性值
     * <p>
     * 注意，每个属性值需要处理一次
     *
     * @param questionList
     */
    public static void batchUpdateCacheAfterUpdate(List<CommonQuestion> questionList) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();

            for (CommonQuestion question : questionList) {
                updateCacheForQuestion(jedis, question);
            }

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 更新单个试题的缓存信息
     *
     * @param jedis
     * @param question
     */
    private static void updateCacheForQuestion(Jedis jedis, CommonQuestion question) {
        String questionId = question.getId().toString();
        Set<String> keys = jedis.keys("*" + Constant.QUESTION_ATTR_INFO + "*");
        for (String key : keys) {
            //先删除缓存中所有关于该列的缓存
            jedis.srem(key, questionId);
        }

        addToSet(jedis, question);
    }

    /**
     * 试题信息加入set
     * @param jedis
     * @param question
     */
    private static void addToSet(Jedis jedis, CommonQuestion question){
        String questionId = question.getId().toString();
        //添加更新后的缓存数量
        Integer questionMetaInfoId = question.getMetaInfoId();
        Integer bankId = question.getQuestionBankId();
        List<Integer> propItemIdList = JSON.parseArray(question.getPropItemIdInfo(), Integer.class);
        //        题型
        jedis.sadd(genKey(questionMetaInfoId), questionId);
        //       题型-题库
        jedis.sadd(genKey(questionMetaInfoId, bankId), questionId);

        for (Integer id : propItemIdList) {
            //            题型-题库-属性id
            jedis.sadd(genKey(questionMetaInfoId, bankId, id), questionId);
        }
    }

    /**
     * 随机选择试题，只通过试题类型
     *
     * @param questionMetaInfoId
     * @param count              要选择的试题数量
     * @return
     */
    public static Set<String> findByMetaInfo(Integer questionMetaInfoId, Integer count) throws DataException {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            String key = genKey(questionMetaInfoId);
            long size = jedis.scard(key);//获取set的成员数量
            if (size >= count) {
                List<String> list = jedis.srandmember(key, count);
                Set<String> res = new HashSet<>(list);
                return res;
            } else {
                logger.warn("通过题型查找试题，题量不足");
                throw new DataException("题量不足，请联系管理员！");
            }

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 通过试题类型查询试题数量
     *
     * @param questionMetaInfoId
     * @return
     */
    public static Long getCountByMetaInfo(Integer questionMetaInfoId) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            String key = genKey(questionMetaInfoId);
            long size = jedis.scard(key);//获取set的成员数量
            return size;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 随机选择试题，通过试题和题库信息
     *
     * @param questionMetaInfoId
     * @param bankId             题库id
     * @param count              要选择的试题数量
     * @return
     */
    public static Set<String> findByMetaInfoAndBank(Integer questionMetaInfoId, Integer bankId, Integer count) throws DataException {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            String key = genKey(questionMetaInfoId, bankId);
            long size = jedis.scard(key);//获取set的成员数量
            if (size >= count) {
                List<String> list = jedis.srandmember(key, count);
                Set<String> res = new HashSet<>(list);
                return res;
            } else {
                logger.warn("通过题型题库查找试题，题量不足");
                throw new DataException("题量不足，请联系管理员！");
            }

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 随机选择试题，通过试题和题库信息 查询试题数量
     *
     * @param questionMetaInfoId
     * @param bankId             题库id
     * @return
     */
    public static Long getCountByMetaInfoAndBank(Integer questionMetaInfoId, Integer bankId) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            String key = genKey(questionMetaInfoId, bankId);
            long size = jedis.scard(key);//获取set的成员数量
            return size;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 通过题型，题库，属性id来获取
     *
     * @param questionMetaInfoId
     * @param bankId             题库id
     * @param count              要选择的试题数量
     * @return
     */
    public static Set<String> findByMetaInfoAndBankAndAttr(Integer questionMetaInfoId,
                                                           Integer bankId, List<Integer> ids, Integer count) throws DataException {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Long size = getCountByMetaInfoAndBankAndAttr(questionMetaInfoId, bankId, ids);
            String tmpKey = genTmpKeyForAttr(questionMetaInfoId, bankId, ids);
            if (size < count) {
                logger.warn("通过属性查找，试题题量不足");
                throw new DataException("题量不足，请联系管理员！");
            } else {
                List<String> resList = jedis.srandmember(tmpKey, count);//随机取出指定数量的试题
                Set<String> res = new HashSet<>(resList);
                return res;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 通过题型，题库，属性id来获取试题数量
     *
     * @param questionMetaInfoId
     * @param bankId             题库id
     * @return
     */
    public static long getCountByMetaInfoAndBankAndAttr(Integer questionMetaInfoId, Integer bankId, List<Integer> ids) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            if (CollectionUtils.isEmpty(ids)) {
                logger.warn("属性id值为空，无法查到数据");
                return 0;
            }
            String tmpKey = genTmpKeyForAttr(questionMetaInfoId, bankId, ids);
            if (!jedis.exists(tmpKey)) {
                List<String> keyList = new ArrayList<>();
                for (Integer id : ids) {
                    keyList.add(genKey(questionMetaInfoId, bankId, id));
                }
                jedis.sinterstore(tmpKey, keyList.toArray(new String[keyList.size()]));

                jedis.expire(tmpKey, 1800);//保存30分钟
            } else {
                long secondsLeft = jedis.ttl(tmpKey);
                if (secondsLeft < 5) {
                    jedis.expire(tmpKey, 30);
                }
            }
            Long size = jedis.scard(tmpKey);//查看存在的数量
            return size;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    /**
     * 生成暂存变量key
     *
     * @param questionMetaInfoId
     * @param bankId
     * @param ids
     * @return
     */
    private static String genTmpKeyForAttr(Integer questionMetaInfoId, Integer bankId, List<Integer> ids) {
        List<String> keyList = new ArrayList<>();
        for (Integer id : ids) {
            keyList.add(genKey(questionMetaInfoId, bankId, id));
        }
        List<Integer> list = new ArrayList<>();
        list.add(questionMetaInfoId);
        list.add(bankId);
        list.addAll(ids);
        String tmpKey = "tmp" + genKey(list.toArray(new Integer[list.size()]));
        return tmpKey;
    }


//    ++++++++++++++++++++++++++++++++++++++

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
     * 检查试题策略是否可用（也就是题库题量是否可用）
     *
     * @param bankId
     * @param questionPolicy
     * @return
     * @throws TesJedisException
     */
    public static boolean checkQuestionPolicy(Integer bankId, QuestionPolicy questionPolicy) {
        String content = questionPolicy.getContent();
        List<List> list = JSON.parseArray(content, List.class);
        for (List<Integer> item : list) {
            Integer count = item.remove(item.size() - 1);
            long exist = getCountByMetaInfoAndBankAndAttr(questionPolicy.getQuestionMetaInfoId(), bankId, item);
            if (count > exist) {
                return false;
            }
        }
        return true;
    }


    /**
     * 初始化试题缓存
     * @throws Exception
     */
    public static void initCacheOfCommonQuestion() throws Exception {
        logger.info("开始初始化试题缓存");
        Jedis jedis = null;
        try{
            jedis = JedisUtil.getJedis();
            Set<String> keys = jedis.keys("*" + Constant.QUESTION_ATTR_INFO + "*");
            //删除所有的属性缓存相关的key
            jedis.del(keys.toArray(new String[keys.size()]));

            CommonQuestion question = new CommonQuestion();
            question.setStatus(CommonStatusEnum.ENABLE.getStatus());
            ICommonQuestionService commonQuestionService = ApplicationContextUtil.getBean(ICommonQuestionService.class);
            PageInfo<CommonQuestion> pageInfo  = new PageInfo<>(100,  1);
            pageInfo = commonQuestionService.findByObject(question, pageInfo);
            while (CollectionUtils.isNotEmpty(pageInfo.getData())){
//            batchUpdateCacheAfterUpdate(pageInfo.getData());
                for(CommonQuestion commonQuestion: pageInfo.getData()){
                    addToSet(jedis, commonQuestion);
                }
                pageInfo.setCurPage(pageInfo.getCurPage() + 1);
                pageInfo = commonQuestionService.findByObject(question, pageInfo);
            }

        }finally {
            if(jedis != null){
                jedis.close();
            }
        }


        logger.info("试题缓存初始化完成");
    }
}

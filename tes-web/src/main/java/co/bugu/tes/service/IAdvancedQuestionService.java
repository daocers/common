package co.bugu.tes.service;

import co.bugu.tes.model.Question;
import co.bugu.tes.model.QuestionMetaInfo;

import java.util.List;

/**
 * Created by daocers on 2017/3/19.
 * 高级的 试题service
 * 根据题型不同使用不同的实现类，便于扩展题型
 */
public interface IAdvancedQuestionService {
    /**
     *
     * @param question
     * @return
     */
    QuestionMetaInfo getQuestionMetaInfo(Question question);

    /**
     *  检查试题是否有效，包括试题答案之类的
     * @param question
     * @return
     */
    boolean check(Question question);

    /**
     * 保存试题和对应的试题属性关系
     * @param question
     * @param propItemIds
     * @return
     */
    int save(Question question, List<Integer> propItemIds);

    /**
     * 获取试题信息
     * @param questionId
     * @return
     */
    int get(Integer questionId);

}

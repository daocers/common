package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.model.question.CommonQuestion;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICommonQuestionService extends IBaseService<CommonQuestion> {
//    int save(Question question, List<Map<String, Integer>> xList);

//    int updateById(Question question);

    int saveOrUpdate(CommonQuestion question, List<Map<String, Integer>> xList);

//    int delete(Question question);

//    Question findById(Integer id);

//    List<Question> findAllByObject(Question question);

//    PageInfo listByObject(Question question, PageInfo<Question> pageInfo) throws Exception;

//    List<Map<String, Object>> selectCountOfPropInfo(Integer metaInfoId);

    int batchAdd(List<CommonQuestion> questionList) throws TesException;


    Set<String> findByMetaInfoAndBankAndAttr(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids, Integer count) throws Exception;


    Long getCountByMetaInfoAndBankAndAttr(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids);

    int batchAdd(List<CommonQuestion> questionList, List<List<Integer>> propItemIdList) throws Exception;
}

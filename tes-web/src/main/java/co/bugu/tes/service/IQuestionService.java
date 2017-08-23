package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.model.Question;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQuestionService extends IBaseService<Question> {
//    int save(Question question, List<Map<String, Integer>> xList);

//    int updateById(Question question);

    int saveOrUpdate(Question question, List<Map<String, Integer>> xList) throws TesException;

//    int delete(Question question);

//    Question findById(Integer id);

//    List<Question> findAllByObject(Question question);

//    PageInfo listByObject(Question question, PageInfo<Question> pageInfo) throws Exception;

//    List<Map<String, Object>> selectCountOfPropInfo(Integer metaInfoId);

    int batchAdd(List<Question> questionList) throws TesException;


    Set<String> findQuestionByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException;


    int getCountByPropItemId(Integer questionMetaInfoId, Integer questionBankId, List<Integer> ids) throws TesException;

    int batchAdd(List<Question> questionList, List<List<Integer>> propItemIdList) throws TesException;
}

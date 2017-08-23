package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.QuestionMetaInfo;

import java.util.List;
import java.util.Map;

public interface IQuestionMetaInfoService extends IBaseService<QuestionMetaInfo> {
//    int save(QuestionMetaInfo questionmetainfo);
//
//    int updateById(QuestionMetaInfo questionmetainfo);
//
//    int saveOrUpdate(QuestionMetaInfo questionmetainfo);

    int saveOrUpdate(QuestionMetaInfo questionmetainfo, List<Map<String, Integer>> list);

//    int delete(QuestionMetaInfo questionmetainfo);
//
//    QuestionMetaInfo findById(Integer id);
//
//    List<QuestionMetaInfo> findAllByObject(QuestionMetaInfo questionmetainfo);
//
//    PageInfo listByObject(QuestionMetaInfo questionmetainfo, PageInfo<QuestionMetaInfo> pageInfo) throws Exception;

    void addProperty(Map<String, String> map);

    void deleteProperty(Map<String, String> map);

}

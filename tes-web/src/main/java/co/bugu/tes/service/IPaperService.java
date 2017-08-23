package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Paper;
import co.bugu.tes.model.Scene;

import java.util.Map;

public interface IPaperService extends IBaseService<Paper> {
//    int save(Paper paper);
//
//    int updateById(Paper paper);
//
//    int saveOrUpdate(Paper paper);
//
//    int delete(Paper paper);
//
//    Paper findById(Integer id);
//
//    List<Paper> findAllByObject(Paper paper);
//
//    PageInfo listByObject(Paper paper, PageInfo<Paper> pageInfo) throws Exception;

    //开场生成所有试卷。 scene中包含所有的场次信息，包含出题策略，考试人员等信息。
    boolean generateAllPaper(Scene scene) throws Exception;

    //适合为开场之前添加的考试用户出题，开场之后禁止添加用户,返回试卷id
    Paper generatePaperForUser(Scene scene, Integer userId) throws Exception;


    Double computeScore(Map<Integer, Double> metaInfoIdScoreMap, Map<String, String> answerInfo, Integer paperId);







}

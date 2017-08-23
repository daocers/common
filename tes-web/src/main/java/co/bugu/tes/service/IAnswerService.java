package co.bugu.tes.service;

import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Answer;

import java.util.Map;

/**
*
*/
public interface IAnswerService extends IBaseService<Answer> {
    boolean savePaperAnswer(Map<String, String> answerMap, Integer paperId);

}

package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.question.TradeQuestion;
import co.bugu.tes.service.ITradeQuestionService;
import org.springframework.stereotype.Service;

/**
* .
*/
@Service
public class TradeQuestionServiceImpl extends BaseServiceImpl<TradeQuestion> implements ITradeQuestionService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

}

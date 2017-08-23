package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.question.TypeInQuestion;
import co.bugu.tes.service.ITypeInService;
import org.springframework.stereotype.Service;

/**
* .
*/
@Service
public class TypeInServiceImpl extends BaseServiceImpl<TypeInQuestion> implements ITypeInService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

}

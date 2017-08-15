package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.UserRoleX;
import co.bugu.tes.service.IUserRoleXService;
import org.springframework.stereotype.Service;

/**
* .
*/
@Service
public class UserRoleXServiceImpl extends BaseServiceImpl<UserRoleX> implements IUserRoleXService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

}

package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.RolePermissionX;
import co.bugu.tes.service.IRolePermissionXService;
import org.springframework.stereotype.Service;

/**
* .
*/
@Service
public class RolePermissionXServiceImpl extends BaseServiceImpl<RolePermissionX> implements IRolePermissionXService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

}

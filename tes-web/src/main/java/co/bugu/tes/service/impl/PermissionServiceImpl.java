package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Permission;
import co.bugu.tes.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* .
*/
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

    @Override
    public List<Permission> findByRoleId(Integer roleId) {
        List<Permission> permissionList = baseDao.selectList("tes.permission.findByRoleId", roleId);
        return null;
    }
}

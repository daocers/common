package co.bugu.tes.service.impl;

import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Role;
import co.bugu.tes.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* .
*/
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
    @Override
    protected String getProjectName() {
        return "tes";
    }

    @Override
    public List<Role> findByUserId(Integer userId) {
        List<Role> roleList = baseDao.selectList("tes.role.findByUserId", userId);
        return roleList;
    }
}

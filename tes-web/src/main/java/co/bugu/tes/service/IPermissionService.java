package co.bugu.tes.service;

import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Permission;

import java.util.List;

/**
*
*/
public interface IPermissionService extends IBaseService<Permission> {
    List<Permission> findByRoleId(Integer roleId);

}

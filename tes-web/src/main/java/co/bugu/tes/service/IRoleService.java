package co.bugu.tes.service;

import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Role;

import java.util.List;

/**
*
*/
public interface IRoleService extends IBaseService<Role> {
    List<Role> findByUserId(Integer userId);

}

package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Authority;

import java.util.List;

public interface IAuthorityService extends IBaseService<Authority> {
//    int save(Authority authority);
//
//    int updateById(Authority authority);
//
//    int saveOrUpdate(Authority authority);
//
//    int delete(Authority authority);

    /**
     * 根据controller更新authority
     * @param authority
     */
    void batchUpdate(Authority authority);

//    Authority findById(Integer id);
//
//    List<Authority> findAllByObject(Authority authority);
//
//    PageInfo listByObject(Authority authority, PageInfo<Authority> pageInfo) throws Exception;
//
    void rebuildInfo(List<Authority> authorityList);

    List<String> getAllController();

    List<Authority> selectAuthorityByRole(Integer roleId);

    void initAuthority(String packageName);

}

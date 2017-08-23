package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Profile;

public interface IProfileService extends IBaseService<Profile> {
//    int save(Profile profile);
//
//    int updateById(Profile profile);
//
//    int saveOrUpdate(Profile profile);
//
//    int delete(Profile profile);
//
//    Profile findById(Integer id);
//
//    List<Profile> findAllByObject(Profile profile);
//
//    PageInfo listByObject(Profile profile, PageInfo<Profile> pageInfo) throws Exception;
    Profile findByUserId(Integer userId);

}

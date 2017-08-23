package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Profile;
import co.bugu.tes.service.IProfileService;
import co.bugu.framework.core.dao.BaseDao;
import co.bugu.framework.core.dao.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl extends BaseServiceImpl<Profile> implements IProfileService {
    @Override
    public Profile findByUserId(Integer userId) {
        Profile profile = new Profile();
        profile.setUserId(userId);
        return baseDao.selectOne("tes.profile.findByObject", profile);
    }
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Profile profile) {
//        return baseDao.insert("tes.profile.insert", profile);
//    }
//
//    @Override
//    public int updateById(Profile profile) {
//        return baseDao.update("tes.profile.updateById", profile);
//    }
//
//    @Override
//    public int saveOrUpdate(Profile profile) {
//        if(profile.getId() == null){
//            return baseDao.insert("tes.profile.insert", profile);
//        }else{
//            return baseDao.update("tes.profile.updateById", profile);
//        }
//    }
//
//    @Override
//    public int delete(Profile profile) {
//        return baseDao.delete("tes.profile.deleteById", profile);
//    }
//
//    @Override
//    public Profile findById(Integer id) {
//        return baseDao.selectOne("tes.profile.selectById", id);
//    }
//
//    @Override
//    public List<Profile> findAllByObject(Profile profile) {
//        return baseDao.selectList("tes.profile.listByObject", profile);
//    }
//
//    @Override
//    public PageInfo listByObject(Profile profile, PageInfo<Profile> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.profile.listByObject", profile, pageInfo);
//    }
}

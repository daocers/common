package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Scene;
import co.bugu.tes.model.User;
import co.bugu.tes.service.ISceneService;
import co.bugu.framework.core.dao.BaseDao;
import co.bugu.framework.core.dao.PageInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SceneServiceImpl extends BaseServiceImpl<Scene> implements ISceneService {
//    @Autowired
//    BaseDao baseDao;
//
    @Override
    public int save(Scene scene) {
        baseDao.insert("tes.scene.insert", scene);
        if(scene.getJoinUser() != null){
            List<Integer> userList = JSON.parseArray(scene.getJoinUser(), Integer.class);
            if(userList.size() > 0){
                baseDao.delete("tes.scene.deleteSceneUserX", scene.getId());
            }
            Map<String, Integer> map = new HashMap<>();
            map.put("sceneId", scene.getId());
            for(Integer userId: userList){
                map.put("userId", userId);
                baseDao.insert("tes.scene.addSceneUserX", map);
            }
        }
        return 0;
    }

    @Override
    public int updateById(Scene scene) {
        String joinUser = scene.getJoinUser();
        scene.setJoinUser(null);
        baseDao.update("tes.scene.updateById", scene);
        if(joinUser != null){
            List<Integer> userList = JSON.parseArray(scene.getJoinUser(), Integer.class);
            if(userList.size() > 0){
                baseDao.delete("tes.scene.deleteSceneUserX", scene.getId());
            }
            Map<String, Integer> map = new HashMap<>();
            map.put("sceneId", scene.getId());
            for(Integer userId: userList){
                map.put("userId", userId);
                baseDao.insert("tes.scene.addSceneUserX", map);
            }
        }
        return 0;
    }

//    @Override
//    public int saveOrUpdate(Scene scene) {
//        if(scene.getId() == null){
//            return baseDao.insert("tes.scene.insert", scene);
//        }else{
//            return baseDao.update("tes.scene.updateById", scene);
//        }
//    }
//
//    @Override
//    public int delete(Scene scene) {
//        return baseDao.delete("tes.scene.deleteById", scene);
//    }
//
    @Override
    public Scene findById(Integer id) {
        Scene scene = baseDao.selectOne("tes.scene.selectById", id);
        if(scene.getDepartmentId() != null){
            scene.setDepartment(baseDao.selectOne("tes.department.selectById", scene.getDepartmentId()));
        }
        if(scene.getBranchId() != null){
            scene.setBranch(baseDao.selectOne("tes.branch.selectById", scene.getBranchId()));
        }
        if(scene.getStationId() != null){
            scene.setStation(baseDao.selectOne("tes.station.selectById", scene.getStationId()));
        }
        if(scene.getCreateUserId() != null){
            scene.setCreateUser(baseDao.selectOne("tes.user.selectById", scene.getCreateUser()));
        }
        if(scene.getUpdateUserId() != null){
            if(scene.getUpdateUserId() == scene.getCreateUserId()){
                scene.setUpdateUser(scene.getCreateUser());
            }else{
                scene.setUpdateUser(baseDao.selectOne("tes.user.selectById", scene.getUpdateUserId()));
            }

        }
        return scene;
    }
//
//    @Override
//    public List<Scene> findAllByObject(Scene scene) {
//        return baseDao.selectList("tes.scene.listByObject", scene);
//    }
//
    @Override
    public PageInfo findByObject(Scene scene, PageInfo<Scene> pageInfo) throws Exception {
        pageInfo =  baseDao.listByObject("tes.scene.findByObject", scene, pageInfo);
        for(Scene item: pageInfo.getData()){
            if(item.getDepartmentId() != null){
                item.setDepartment(baseDao.selectOne("tes.department.selectById", item.getDepartmentId()));
            }
            if(item.getBranchId() != null){
                item.setBranch(baseDao.selectOne("tes.branch.selectById", item.getBranchId()));
            }
            if(item.getStationId() != null){
                item.setStation(baseDao.selectOne("tes.station.selectById", item.getStationId()));
            }
        }
        return  pageInfo;

    }

    @Override
    public boolean disabledUserOfScene(Scene scene, User user) {
        return false;
    }

    @Override
    public int addUserToScene(List<Integer> userIds, Scene scene) {
        baseDao.delete("tes.scene.deleteSceneUserX", scene.getId());
        Map<String, Integer> map = new HashMap<>();
        map.put("sceneId", scene.getId());
        map.put("userStatus", 0);
        for(Integer id: userIds){
            map.put("userId", id);
            baseDao.insert("tes.scene.addSceneUserX", map);
        }
        return 0;
    }

    @Override
    public int deleteUserOfScene(Integer userId, Integer sceneId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("sceneId", sceneId);
        map.put("userId", userId);
        baseDao.delete("tes.scene.deleteUserFromSceneUserX", map);
        return 0;
    }

    @Override
    public List<Scene> selectJoinedByUserId(Integer userId) {

        return baseDao.selectList("tes.scene.selectJoinedByUserId", userId);
    }

    @Override
    public int changeStatusToBegin(Scene scene) {
        return baseDao.update("tes.scene.changeStatusToBegin", scene);
    }

}

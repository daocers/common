package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Scene;
import co.bugu.tes.model.User;

import java.util.List;

public interface ISceneService extends IBaseService<Scene> {
//    int save(Scene scene);
//
//    int updateById(Scene scene);
//
//    int saveOrUpdate(Scene scene);
//
//    int delete(Scene scene);
//
//    Scene findById(Integer id);
//
//    List<Scene> findAllByObject(Scene scene);
//
//    PageInfo listByObject(Scene scene, PageInfo<Scene> pageInfo) throws Exception;

    //禁止考试。此时需要把用户从场次关联表中的状态改为禁用
    boolean disabledUserOfScene(Scene scene, User user);

    int addUserToScene(List<Integer> userIds, Scene scene);

    int deleteUserOfScene(Integer userId, Integer sceneId);

    List<Scene> selectJoinedByUserId(Integer userId);

    /**
     * 场次状态修改为已开始
     * @param scene
     * @return
     */
    int changeStatusToBegin(Scene scene);

}

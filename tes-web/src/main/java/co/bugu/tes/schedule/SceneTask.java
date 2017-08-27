package co.bugu.tes.schedule;

import co.bugu.tes.enums.SceneStatusEnum;
import co.bugu.tes.model.Scene;
import co.bugu.tes.service.ISceneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by daocers on 2017/8/27.
 * 处理场次的自动开场，自动封场，出成绩等问题
 *
 */
public class SceneTask {
    private static Logger logger = LoggerFactory.getLogger(SceneTask.class);
    @Autowired
    ISceneService sceneService;

    /**
     * 自动开场
     * 1 设置场次状态为begin，考生可以登录考试
     */
    public void autoBegin(){
        logger.info("开始刷新场次信息");
        Scene scene = new Scene();
        scene.setStatus(SceneStatusEnum.READY.getStatus());
        scene.setBeginTime(new Date());
        int num = sceneService.changeStatusToBegin(scene);
        logger.info("场次信息修改完毕，共开场 {} 次。", num);
    }

    /**
     * 自动封场，
     * 1 给客户端发消息自动提交试卷
     * 2 设置场次信息为end
     * 3 自动计算成绩
     */
    public void autoEnd(){

    }
}

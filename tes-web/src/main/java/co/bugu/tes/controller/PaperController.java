package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.mybatis.SearchParamUtil;
import co.bugu.framework.core.mybatis.ThreadLocalUtil;
import co.bugu.framework.core.util.ShiroSessionUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.model.Answer;
import co.bugu.tes.model.Paper;
import co.bugu.tes.model.Scene;
import co.bugu.tes.model.User;
import co.bugu.tes.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Menu(value = "试卷管理", isBox = true)
@Controller
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    IPaperService paperService;
    @Autowired
    IUserService userService;
    @Autowired
    ISceneService sceneService;
    @Autowired
    IAnswerService answerService;

    @Autowired
    IQuestionPolicyService questionPolicyService;
    private static Logger logger = LoggerFactory.getLogger(PaperController.class);

    @RequestMapping(value = "/list")
    public String list(Integer sceneId, String username, Integer curPage, Integer showCount, ModelMap model){
        try{
            Paper paper = new Paper();
            if(StringUtils.isNotEmpty(username)){
                User user = new User();
                user.setUsername(username);
                List<User> userList = userService.findByObject(user);
                if(CollectionUtils.isNotEmpty(userList)){
                    paper.setUserId(userList.get(0).getId());
                }
            }
            paper.setSceneId(sceneId);
            paper.setStatus(CommonStatusEnum.ENABLE.getStatus());
            PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
            paperService.findByObject(paper, pageInfo);
            model.put("pi", pageInfo);
        }catch (Exception e){
            logger.error("获取成绩失败", e);
        }

        return "paper/list";

    }

    /**
     * 列表，分页显示
     * 查询当前用户开场的场次试卷信息
     *
     * @param paper     查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
//    @Menu(value = "试卷列表", isView = true)
//    @RequestMapping(value = "/list")
//    public String list(Paper paper, String username, String sceneName, Integer curPage,
//                       Integer showCount, ModelMap model, HttpServletRequest request) {
//        try {
//            Integer userId = ShiroSessionUtil.getUserId();
//            if (StringUtils.isNotEmpty(username)) {
//                User user = new User();
//                user.setUsername(username);
//                List<User> userList = userService.findByObject(user);
//                if (CollectionUtils.isNotEmpty(userList)) {
//                    userId = userList.get(0).getId();
//                } else {
//                    userId = -1;//不存在该用户
//                }
//            }
//            Integer sceneId = null;
//            if (StringUtils.isNotEmpty(sceneName)) {
//                Scene scene = new Scene();
//                scene.setName(sceneName);
//                List<Scene> sceneList = sceneService.findByObject(scene);
//                if (CollectionUtils.isNotEmpty(sceneList)) {
//                    sceneId = sceneList.get(0).getId();
//                } else {
//                    sceneId = -1;
//                }
//            }
//            SearchParamUtil.processSearchParam(paper, request);
//            Map<String, Object> map = ThreadLocalUtil.get();
//            if (userId != null) {
//                map.put("EQ_userId", userId);
//                paper.setUserId(userId);
//            }
//            if (sceneId != null) {
//                map.put("EQ_sceneId", sceneId);
//                paper.setSceneId(sceneId);
//            }
//            ThreadLocalUtil.set(map);
//            PageInfo<Paper> pageInfo = new PageInfo<>(showCount, curPage);
//            pageInfo = paperService.findByObject(paper, pageInfo);
//            model.put("pi", pageInfo);
//            model.put("paper", paper);
//            Map<Integer, String> statusMap = new HashMap<>();
//            statusMap.put(0, "正常");
//            statusMap.put(1, "未作答");
//            statusMap.put(2, "作废");
//            statusMap.put(3, "已交卷");
//            statusMap.put(4, "提交失败");
//            model.put("statusMap", statusMap);
//        } catch (Exception e) {
//            logger.error("获取列表失败", e);
//            model.put("errMsg", "获取列表失败");
//        }
//        return "paper/list";
//
//    }


    /**
     * 查找当前用户的所有试卷信息
     * @param paper
     * @param curPage
     * @param showCount
     * @param model
     * @return
     */
    @RequestMapping(value = "/list/my.do")
    public String list(Paper paper, Integer curPage, Integer showCount, ModelMap model){
        try{
            paper.setUserId(ShiroSessionUtil.getUserId());
            PageInfo pageInfo = new PageInfo(showCount, curPage);
            pageInfo = paperService.findByObject(paper, pageInfo);
            model.put("pi", pageInfo);
        }catch (Exception e){
            logger.error("获取试卷信息失败", e);
            model.put("errMsg", "获取试卷列表失败");
        }
        return "paper/list";
    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
//    @Menu(value = "编辑试卷", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            Paper paper = paperService.findById(id);
            model.put("paper", paper);

        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "paper/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param paper
     * @param model
     * @return
     */
//    @Menu(value = "保存试卷")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Paper paper, ModelMap model) {
        try {
            if (paper.getId() == null) {
                paperService.save(paper);
            } else {
                paperService.updateById(paper);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("paper", paper);
            model.put("errMsg", "保存失败");
            return "paper/edit";
        }
        return "redirect:list.do";
    }

    //    @Menu(value = "获取试卷信息", isView = true)
    @RequestMapping(value = "/paperInfo")
    public String getPaperInfo(Integer paperId, Integer showCount, Integer curPage, ModelMap model) throws Exception {
        PageInfo<Answer> pageInfo = new PageInfo<>(showCount, curPage);
        Answer answer = new Answer();
        answer.setPaperId(paperId);
        pageInfo = answerService.findByObject(answer, pageInfo);
        model.put("pi", pageInfo);
        return "paper/show";
    }

    /**
     * 异步请求 获取全部
     *
     * @param paper 查询条件
     * @return
     */
//    @Menu(value = "获取全部试卷")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Paper paper) {
        try {
            List<Paper> list = paperService.findByObject(paper);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param paper id
     * @return
     */
//    @Menu(value = "删除试卷")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Paper paper) {
        try {
            paperService.delete(paper);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }

    //    @Menu(value = "试卷管理", isView = true)
    @RequestMapping("/manage")
    public String toManage(Paper paper, ModelMap model) throws TesException {
        try {

        } catch (Exception e) {
            logger.error("获取试卷信息失败", e);
            throw new TesException("获取试卷信息失败", e);
        }
        return null;
    }
}

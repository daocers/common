package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.EncryptUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("user")
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 列表，分页显示
     *
     * @param username  查询数据
     * @param name      查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(String username, String name, Integer curPage, Integer showCount, ModelMap model) {
        try {
            User user = new User();
            if (StringUtils.isNotEmpty(username)) {
                user.setUsername(username);
            }
            if (StringUtils.isNotEmpty(name)) {
                user.setName(name);
            }
            PageInfo<User> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = userService.findByObject(user, pageInfo);
            model.put("pi", pageInfo);
            model.put("user", user);

//            List<String> checkedRole = new ArrayList<>();
//            for(User u: pageInfo.getData()){
//                List<Role> list = roleService.selectRoleByUser(u.getId());
//                StringBuffer buffer = new StringBuffer();
//                for(Role role: list){
//                    buffer.append(role.getName()).append(" ");
//                }
//                checkedRole.add(buffer.toString());
//            }
//            model.put("checkedRole", checkedRole);

//            List<Department> departments = departmentService.findByObject(null);
//            List<Branch> branches = branchService.findByObject(null);
//            List<Station> stations = stationService.findByObject(null);
//            Map<Integer, String> departmentMap = new HashMap<>();
//            Map<Integer, String> branchMap = new HashMap<>();
//            Map<Integer, String> stationMap = new HashMap<>();
//            for (Department department : departments) {
//                departmentMap.put(department.getId(), department.getName());
//            }
//            for (Branch branch : branches) {
//                branchMap.put(branch.getId(), branch.getName());
//            }
//            for (Station station : stations) {
//                stationMap.put(station.getId(), station.getName());
//            }
//            model.put("departmentMap", departmentMap);
//            model.put("branchMap", branchMap);
//            model.put("stationMap", stationMap);
//            List<Role> roleList = roleService.findByObject(null);
//            model.put("roleList", roleList);
//            Map<Integer, String> roleInfoMap = new HashMap<>();
//            for(Role role: roleList){
//                roleInfoMap.put(role.getId(), role.getName());
//            }
//            model.put("roleInfoMap", JSON.toJSONString(roleInfoMap));
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "user/list";

    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            if (id != null) {
                User user = userService.findById(id);
                model.put("user", user);
            }
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "/user/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user, ModelMap model) {
        try {
            if (user.getId() == null) {
                userService.save(user);
            } else {
                userService.updateById(user);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("user", user);
            model.put("errMsg", "保存失败");
            return "user/edit";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param user 查询条件
     * @return
     */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(User user) {
        try {
            List<User> list = userService.findByObject(user);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param user id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(User user) {
        try {
            userService.delete(user);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }


    @RequestMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(Integer userId, ModelMap model) {
        User user = new User();
        user.setId(userId);
        user.setSalt(Constant.DEFALUT_SALT);
        user.setPassword(EncryptUtil.md5(Constant.DEFAULT_PASSWORD + Constant.DEFALUT_SALT));
        userService.updateById(user);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        return json.toJSONString();
    }
}

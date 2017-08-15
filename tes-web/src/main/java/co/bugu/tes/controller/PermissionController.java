package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Permission;
import co.bugu.tes.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("permission")
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    IPermissionService permissionService;

    private static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    /**
     * 列表，分页显示
     *
     * @param permission 查询数据
     * @param curPage    当前页码，从1开始
     * @param showCount  当前页码显示数目
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Permission permission, Integer curPage, Integer showCount, ModelMap model) {
        try {
            PageInfo<Permission> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = permissionService.findByObject(permission, pageInfo);
            model.put("pi", pageInfo);
            model.put("permission", permission);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "/permission/list";

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
                Permission permission = permissionService.findById(id);
                model.put("permission", permission);
            }
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "/permission/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param permission
     * @param model
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Permission permission, ModelMap model) {
        try {
            if (permission.getId() == null) {
                permissionService.save(permission);
            } else {
                permissionService.updateById(permission);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("permission", permission);
            model.put("errMsg", "保存失败");
            return "permission/edit";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param permission 查询条件
     * @return
     */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Permission permission) {
        try {
            List<Permission> list = permissionService.findByObject(permission);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param permission id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Permission permission) {
        try {
            permissionService.delete(permission);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

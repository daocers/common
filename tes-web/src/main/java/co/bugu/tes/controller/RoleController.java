package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Role;
import co.bugu.tes.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("role")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    IRoleService roleService;

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
    * 列表，分页显示
    * @param role  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Role role, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Role> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = roleService.listByObject(role, pageInfo);
            model.put("pi", pageInfo);
            model.put("role", role);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "/role/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model){
        try{
            if(id != null){
                Role role = roleService.findById(id);
                model.put("role", role);
            }
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "/role/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param role
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Role role, ModelMap model){
        try{
            roleService.saveOrUpdate(role);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("role", role);
            model.put("errMsg", "保存失败");
            return "role/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param role 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Role role){
        try{
            List<Role> list = roleService.findAllByObject(role);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param role id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Role role){
        try{
            roleService.delete(role);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.ExcelUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Group;
import co.bugu.tes.service.IGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("group")
@RequestMapping("/group")
public class GroupController {
    @Autowired
    IGroupService groupService;

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);

    /**
    * 列表，分页显示
    * @param group  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Group group, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Group> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = groupService.findByObject(group, pageInfo);
            model.put("pi", pageInfo);
            model.put("group", group);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "/group/list";

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
                Group group = groupService.findById(id);
                model.put("group", group);
            }
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "/group/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param group
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Group group, ModelMap model){
        try{
            if(group.getId() != null){
                groupService.updateById(group);
            }else{
                groupService.save(group);
            }
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("group", group);
            model.put("errMsg", "保存失败");
            return "group/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param group 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Group group){
        try{
            List<Group> list = groupService.findByObject(group);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param group id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Group group){
        try{
            groupService.delete(group);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

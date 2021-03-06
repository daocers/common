package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.service.IPropertyItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/propertyitem")
public class PropertyItemController {
    @Autowired
    IPropertyItemService propertyitemService;

    private static Logger logger = LoggerFactory.getLogger(PropertyItemController.class);

    /**
    * 列表，分页显示
    * @param propertyitem  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
//    @Menu(value = "属性条目列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(PropertyItem propertyitem, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<PropertyItem> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = propertyitemService.findByObject(propertyitem, pageInfo);
            model.put("pi", pageInfo);
            model.put("propertyitem", propertyitem);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "propertyitem/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
//    @Menu(value = "编辑属性条目", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model){
        try{
            PropertyItem propertyitem = propertyitemService.findById(id);
            model.put("propertyitem", propertyitem);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "propertyitem/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param propertyitem
    * @param model
    * @return
    */
//    @Menu(value = "保存属性条目")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(PropertyItem propertyitem, ModelMap model){
        try{
            if(propertyitem.getId() == null){
                propertyitemService.save(propertyitem);
            }else{
                propertyitemService.updateById(propertyitem);
            }
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("propertyitem", propertyitem);
            model.put("errMsg", "保存失败");
            return "propertyitem/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param propertyitem 查询条件
    * @return
    */
//    @Menu(value = "获取全部属性条目")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(PropertyItem propertyitem){
        try{
            List<PropertyItem> list = propertyitemService.findByObject(propertyitem);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param propertyitem id
    * @return
    */
//    @Menu(value = "删除属性条目")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(PropertyItem propertyitem){
        try{
            propertyitemService.delete(propertyitem);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

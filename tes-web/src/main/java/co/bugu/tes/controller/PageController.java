package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
//import co.bugu.tes.annotation.Menu;
import co.bugu.tes.model.Page;
import co.bugu.tes.service.IPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//@Menu(value = "交易页面管理", isBox = true)
@Controller
@RequestMapping("/page")
public class PageController {
    @Autowired
    IPageService pageService;

    private static Logger logger = LoggerFactory.getLogger(PageController.class);

    /**
    * 列表，分页显示
    * @param page  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
//    @Menu(value = "交易页面列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(Page page, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Page> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = pageService.findByObject(page, pageInfo);
            model.put("pi", pageInfo);
            model.put("page", page);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "page/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
//    @Menu(value = "编辑交易页面", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model){
        try{
            Page page = pageService.findById(id);
            model.put("page", page);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "page/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param page
    * @param model
    * @return
    */
//    @Menu(value = "保存交易页面")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Page page, ModelMap model){
        try{
            if(page.getId() == null){
                pageService.save(page);
            }else{
                pageService.updateById(page);
            }
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("page", page);
            model.put("errMsg", "保存失败");
            return "page/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param page 查询条件
    * @return
    */
//    @Menu(value = "获取全部交易页面")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Page page){
        try{
            List<Page> list = pageService.findByObject(page);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param page id
    * @return
    */
//    @Menu(value = "删除交易操作页面")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Page page){
        try{
            pageService.delete(page);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

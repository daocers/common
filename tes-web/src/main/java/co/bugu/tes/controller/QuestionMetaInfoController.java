package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.ExcelUtilNew;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.service.IPropertyItemService;
import co.bugu.tes.service.IPropertyService;
import co.bugu.tes.service.IQuestionMetaInfoService;
import co.bugu.tes.util.QuestionMetaInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Menu(value = "题型管理", isBox = true)
@Controller
@RequestMapping("/questionMetaInfo")
public class QuestionMetaInfoController {
    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;

    @Autowired
    IPropertyService propertyService;

    @Autowired
    IPropertyItemService propertyItemService;

    private static Logger logger = LoggerFactory.getLogger(QuestionMetaInfoController.class);

    /**
     * 列表，分页显示
     *
     * @param questionmetainfo 查询数据
     * @param curPage          当前页码，从1开始
     * @param showCount        当前页码显示数目
     * @param model
     * @return
     */
//    @Menu(value = "题型列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(QuestionMetaInfo questionmetainfo, Integer curPage, Integer showCount, ModelMap model) {
        try {
            PageInfo<QuestionMetaInfo> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = questionMetaInfoService.findByObject(questionmetainfo, pageInfo);
            model.put("pi", pageInfo);
            model.put("questionmetainfo", questionmetainfo);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "question_meta_info/list";

    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
//    @Menu(value = "编辑题型", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            if (id != null) {
                QuestionMetaInfo questionmetainfo = questionMetaInfoService.findById(id);
                model.put("questionmetainfo", questionmetainfo);

                List<Integer> propertyIdList = new ArrayList<>();
                if (questionmetainfo.getPropertyList() != null) {
                    for (Property property : questionmetainfo.getPropertyList()) {
                        propertyIdList.add(property.getId());
                    }
                }
                model.put("propertyIdList", propertyIdList);
            }

            List<Property> propertyList = propertyService.findByObject(null);
            if (propertyList != null) {
                for (Property property : propertyList) {
                    PropertyItem item = new PropertyItem();
                    item.setPropertyId(property.getId());
                    List<PropertyItem> itemList = propertyItemService.findByObject(item);
                    property.setPropertyItemList(itemList);
                }
            }
            model.put("propertyList", propertyList);
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }

        return "question_meta_info/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param questionmetainfo
     * @param propertyId       属性id
     * @param model
     * @return
     */
//    @Menu(value = "保存题型")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(QuestionMetaInfo questionmetainfo, int[] propertyId, ModelMap model) {
        try {
            List<Map<String, Integer>> list = new ArrayList<>();
            if (propertyId != null) {
                for (int i = 0; i < propertyId.length; i++) {
                    Integer id = propertyId[i];
                    Map<String, Integer> map = new HashMap<>();
                    map.put("propertyId", id);
                    map.put("idx", i);
                    list.add(map);
                }
            }
            questionMetaInfoService.saveOrUpdate(questionmetainfo, list);
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("questionmetainfo", questionmetainfo);
            model.put("errMsg", "保存失败");
            return "question_meta_info/edit";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param questionmetainfo 查询条件
     * @return
     */
//    @Menu(value = "获取全部题型")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(QuestionMetaInfo questionmetainfo) {
        try {
            List<QuestionMetaInfo> list = questionMetaInfoService.findByObject(questionmetainfo);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param questionmetainfo id
     * @return
     */
//    @Menu(value = "删除题型")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(QuestionMetaInfo questionmetainfo) {
        try {
            questionMetaInfoService.delete(questionmetainfo);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }

//    @Menu(value = "试题模板下载")
    @RequestMapping(value = "/downModel")
    public String downloadTemplate(@RequestParam Integer metaInfoId, ModelMap model,
                                   RedirectAttributes redirectAttributes,
                                   HttpServletRequest request, HttpServletResponse response) {
        try {
            QuestionMetaInfo metaInfo = questionMetaInfoService.findById(metaInfoId);
            if (metaInfo == null) {
                model.put("err", "没找到该题型信息");
                return "redirect:/question/batchAdd.do";
            }
            String metaName = metaInfo.getName();
            List<String> title = QuestionMetaInfoUtil.getModelTitle(metaInfo);

            String path = request.getSession().getServletContext().getRealPath("file");
            ExcelUtilNew.download(request, response, metaName, title, null);
            return null;
        } catch (Exception e) {
            logger.error("下载模板异常", e);
            model.put("err", "下载模板失败");
            redirectAttributes.addFlashAttribute("err", "下载模板失败");
            return "redirect:download.do";
        }
    }

//    @Menu(value = "导出试题信息")
    @RequestMapping("/download")
    public String toDownloadPage(ModelMap model) {
        List<QuestionMetaInfo> questionMetaInfos = questionMetaInfoService.findByObject(null);
        model.put("metaInfoList", questionMetaInfos);
        return "question_meta_info/download";
    }


}

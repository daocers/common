package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.question.TypeInQuestion;
import co.bugu.tes.service.ITypeInService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Menu(value = "数字录入管理", isBox = true)
@Controller("typeIn")
@RequestMapping("/typeIn")
public class TypeInController {
    @Autowired
    ITypeInService typeInService;

    private static Logger logger = LoggerFactory.getLogger(TypeInController.class);

    /**
     * 列表，分页显示
     *
     * @param typeInQuestion    查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
//    @Menu(value = "数字录入列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(TypeInQuestion typeInQuestion, Integer curPage, Integer showCount, ModelMap model) {
        try {
            PageInfo<TypeInQuestion> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = typeInService.findByObject(typeInQuestion, pageInfo);
            model.put("pi", pageInfo);
            model.put("typeInQuestion", typeInQuestion);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("err", "获取列表失败");
        }
        return "type_in/list";

    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
//    @Menu(value = "编辑数字录入", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            if (id != null) {
                TypeInQuestion typeInQuestion = typeInService.findById(id);
                model.put("typeInQuestion", typeInQuestion);
            } else {
                Random random = new Random();
                List<Double> list = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    list.add((double) (10000 + random.nextInt(1000000000)) / 100);
                }
                TypeInQuestion typeInQuestion = new TypeInQuestion();
                typeInQuestion.setContent(JSON.toJSONString(list));
                model.put("typeInQuestion", typeInQuestion);
            }
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("err", "获取信息失败");
        }
        return "type_in/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param typeInQuestion
     * @param model
     * @return
     */
//    @Menu(value = "保存文字录入")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TypeInQuestion typeInQuestion, ModelMap model) {
        try {
            typeInQuestion.setStatus(0);
            if (typeInQuestion.getId() == null) {
                typeInService.save(typeInQuestion);
            } else {
                typeInService.updateById(typeInQuestion);
            }
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("typeInQuestion", typeInQuestion);
            model.put("err", "保存失败");
            return "type_in/edit";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param typeInQuestion 查询条件
     * @return
     */
//    @Menu(value = "获取所有文字录入信息")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(TypeInQuestion typeInQuestion) {
        try {
            List<TypeInQuestion> list = typeInService.findByObject(typeInQuestion);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param typeInQuestion id
     * @return
     */
//    @Menu(value = "删除文字录入")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(TypeInQuestion typeInQuestion) {
        try {
            typeInService.delete(typeInQuestion);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

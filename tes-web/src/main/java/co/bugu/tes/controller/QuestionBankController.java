package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.model.QuestionBank;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IQuestionBankService;
import co.bugu.tes.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

//@Menu(value = "题库管理", isBox = true)
@Controller
@RequestMapping("/questionBank")
public class QuestionBankController {
    @Autowired
    IQuestionBankService questionbankService;
    @Autowired
    IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(QuestionBankController.class);

    /**
    * 列表，分页显示
    * @param questionbank  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
//    @Menu(value = "题库列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(QuestionBank questionbank, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<QuestionBank> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = questionbankService.findByObject(questionbank, pageInfo);
            model.put("pi", pageInfo);
            model.put("questionbank", questionbank);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "question_bank/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
//    @Menu(value = "编辑题库", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model){
        try{
            QuestionBank questionBank = questionbankService.findById(id);
            if(questionBank.getCreateUserId() != null){
                User user = userService.findById(questionBank.getCreateUserId());
                model.put("username", user.getName());
            }
            model.put("questionBank", questionBank);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "question_bank/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param questionbank
    * @param model
    * @return
    */
//    @Menu(value = "保存题库")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request, QuestionBank questionbank, ModelMap model){
        try{
            Date now = new Date();

            Integer currentUserId = (Integer) WebUtils.getSessionAttribute(request, "userId");
            if(questionbank.getId() == null){
                questionbank.setCreateTime(now);
                questionbank.setCreateUserId(currentUserId);
            }
            questionbank.setUpdateTime(now);
            questionbank.setUpdateUserId(currentUserId);
            questionbank.setStatus(CommonStatusEnum.ENABLE.getStatus());
            if(questionbank.getId() == null){
                questionbankService.save(questionbank);
            }else{
                questionbankService.updateById(questionbank);
            }
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("questionBank", questionbank);
            model.put("errMsg", "保存失败");
            return "question_bank/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param questionBank 查询条件
    * @return
    */
//    @Menu(value = "获取全部题库")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(QuestionBank questionBank){
        try{
            List<QuestionBank> list = questionbankService.findByObject(questionBank);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param questionBank id
    * @return
    */
//    @Menu(value = "删除题库")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(QuestionBank questionBank){
        try{
            questionbankService.delete(questionBank);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}

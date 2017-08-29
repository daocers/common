package co.bugu.tes.controller;

import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.model.*;
import co.bugu.tes.model.question.TradeQuestion;
import co.bugu.tes.service.IQuestionBankService;
import co.bugu.tes.service.ITradeQuestionService;
import co.bugu.tes.service.ITradeService;
import co.bugu.tes.service.IUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

//@Menu(value = "交易管理", isBox = true)
@Controller
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    ITradeService tradeService;
    @Autowired
    IUserService userService;
    @Autowired
    ITradeQuestionService questionService;
    @Autowired
    IQuestionBankService questionBankService;

    private static Logger logger = LoggerFactory.getLogger(TradeController.class);

    /**
     * 录制交易
     * 打开交易界面， 同时录入信息，记录当前交易界面的栏位信息，顺便出题一道
     *
     * @return
     */
//    @Menu(value = "录制交易", isView = true)
    @RequestMapping("/toRecord")
    public String record(ModelMap model) {
        List<QuestionBank> bankList = questionBankService.findByObject(null);
//        for (List<String> list : fieldList) {
//            String fieldName = list.get(0);
//            String fLabel = list.get(2);
//            String fValue = list.get(1);
//            String fText = list.get(3);
//            Integer checkable = list.get(4).equals("true") ? 0 : 1;
//        }
        model.put("bankList", bankList);
        return "trade/record";
    }

    /**
     * 保存交易模板，页面信息，顺便出题一道
     * @param name
     * @param code
     * @param description
     * @param pageUrl
     * @param fieldInfo
     * @param bankId
     * @param request
     * @return
     */
//    @Menu(value = "保存交易模板")
    @RequestMapping("/save")
    @ResponseBody
    public String saveModelAndQuestion(String name, String code, String description,
                                       String pageUrl, String fieldInfo, Integer bankId, HttpServletRequest request) {
        Date now = new Date();
        Integer userId = (Integer) BuguWebUtil.getUserId(request);
        User user = userService.findById(userId);
        JSONObject json = new JSONObject();
        Trade trade = new Trade();
        trade.setName(name);
        List<Trade> tradeList = tradeService.findByObject(trade);
        if(CollectionUtils.isNotEmpty(tradeList)){
            trade = tradeList.get(0);
        }else{
            trade.setCreateTime(now);
            trade.setCreateUserId(userId);
        }
        trade.setCode(code);
        trade.setStatus(CommonStatusEnum.ENABLE.getStatus());
        trade.setBranchId(user.getBranchId());
        trade.setDepartmentId(user.getDepartmentId());
        trade.setUpdateTime(now);
        trade.setUpdateUserId(userId);
        trade.setStationId(user.getStationId());

        Page page = new Page();
        page.setStatus(CommonStatusEnum.ENABLE.getStatus());
        page.setCode(code);
        page.setUrl(pageUrl);
        page.setContent(fieldInfo);

        tradeService.saveTradeAndPage(trade, page);
        json.put("code", 0);


        List<List> fieldList = JSON.parseArray(fieldInfo, List.class);
        for (List<String> list : fieldList) {
            list.remove(2);
        }

        TradeQuestion question = new TradeQuestion();
        question.setDescription(description);
        question.setAnswer(JSON.toJSONString(fieldList));
        question.setStatus(CommonStatusEnum.ENABLE.getStatus());
        question.setBankId(bankId);
        question.setDepartmentId(user.getDepartmentId());
        question.setBranchId(user.getBranchId());
        question.setCreateUserId(userId);
        question.setUpdateUserId(userId);
        question.setCreateTime(now);
        question.setUpdateTime(now);
        questionService.save(question);

        return json.toJSONString();
    }


    /**
     * 出题
     * @param fieldInfo  题目信息
     * @param tradeId   交易id
     * @param pageId 页面id
     * @param description 交易情景描述
     * @param bankId  题库管理
     * @return
     */
//    @Menu(value = "保存交易试题")
    @RequestMapping("/saveQuestion")
    @ResponseBody
    public String saveQuestion(String fieldInfo, Integer tradeId, Integer pageId, String description, Integer bankId) {
        return null;
    }



}

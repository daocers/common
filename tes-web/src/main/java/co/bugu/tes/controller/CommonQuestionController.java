package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.mybatis.SearchParamUtil;
import co.bugu.framework.core.util.ShiroSessionUtil;
import co.bugu.framework.util.ExcelUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.framework.util.exception.TesException;
import co.bugu.tes.enums.CommonStatusEnum;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.QuestionBank;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.model.User;
import co.bugu.tes.model.question.CommonQuestion;
import co.bugu.tes.service.ICommonQuestionService;
import co.bugu.tes.service.IQuestionBankService;
import co.bugu.tes.service.IQuestionMetaInfoService;
import co.bugu.tes.service.IUserService;
import co.bugu.tes.util.QuestionMetaInfoUtil;
import co.bugu.tes.util.QuestionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Menu(value = "试题管理", isBox = true)
@Controller
@RequestMapping("/commonQuestion")
public class CommonQuestionController {
    @Autowired
    ICommonQuestionService questionService;

    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;

    @Autowired
    IQuestionBankService questionBankService;
    @Autowired
    IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(CommonQuestionController.class);

    /**
     * 列表，分页显示
     *
     * @param question  查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
//    @Menu(value = "试题列表", isView = true)
    @RequestMapping(value = "/list")
    public String list(CommonQuestion question, Integer curPage, Integer showCount, ModelMap model, HttpServletRequest request) {
        try {
            /**
             * 添加查询条件
             * LK_**, EQ_***等
             * */
            SearchParamUtil.processSearchParam(question, request);
            PageInfo<CommonQuestion> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = questionService.findByObject(question, pageInfo);
            model.put("pi", pageInfo);
            model.put("question", question);
            List<QuestionMetaInfo> metaInfoList = questionMetaInfoService.findByObject(null);
            Map<Integer, String> metaInfoMap = new HashMap<>();
            for (QuestionMetaInfo metaInfo : metaInfoList) {
                metaInfoMap.put(metaInfo.getId(), metaInfo.getName());
            }
            model.put("metaInfoMap", metaInfoMap);
            List<QuestionBank> questionBankList = questionBankService.findByObject(null);
            Map<Integer, String> bankMap = new HashMap<>();
            for (QuestionBank bank : questionBankList) {
                bankMap.put(bank.getId(), bank.getName());
            }
            model.put("bankMap", bankMap);
            model.put("metaInfoList", metaInfoList);
            model.put("questionBankList", questionBankList);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "common_question/list";
    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
//    @Menu(value = "编辑试题", isView = true)
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model) {
        try {
            if (id != null) {
                CommonQuestion question = questionService.findById(id);
                QuestionMetaInfo metaInfo = questionMetaInfoService.findById(question.getMetaInfoId());
                if (metaInfo == null) {
                    model.put("err", "没有对应的题型");
                    return "redirect:list.do";
                }
                String code = metaInfo.getCode();
                if ("single".equals(code) || "multi".equals(code)) {
                    List<String> itemList = JSON.parseArray(question.getContent(), String.class);
                    StringBuilder builder = new StringBuilder();
                    char begin = 'A';
                    String regex = "^([A-Z]|[a-z])([:：])\\s{0,}";
                    for (String item : itemList) {
                        item = item.replaceAll(regex, "");
                        builder.append(begin)
                                .append(": ")
                                .append(item)
                                .append("\n");
                        begin += 1;
                    }
                    question.setContent(builder.toString());
                } else if ("judge".equals(code)) {
                    question.setContent("");
                    question.setAnswer(question.getAnswer().equals("T") ? "正确" : "错误");
                }
                model.put("question", question);
                model.put("propItemIdList", JSON.parseArray(question.getPropItemIdInfo(), Integer.class));
                model.put("propertyList", metaInfo.getPropertyList());
            }
            List<QuestionMetaInfo> metaInfoList = questionMetaInfoService.findByObject(null);
            model.put("metaInfoList", metaInfoList);
            List<QuestionBank> questionBankList = questionBankService.findByObject(null);
            model.put("questionBankList", questionBankList);
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "common_question/edit";
    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param question
     * @param model
     * @return
     */
//    @Menu(value = "保存试题")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(CommonQuestion question, ModelMap model) {
        try {
            List<Map<String, Integer>> xList = new ArrayList<>();
            List<Integer> itemList = JSON.parseArray(question.getPropItemIdInfo(), Integer.class);
            for (Integer id : itemList) {
                Map<String, Integer> map = new HashMap<>();
                map.put("propItemId", id);
                xList.add(map);
            }
            String[] item = question.getContent().split("\r\n");
            question.setContent(JSON.toJSONString(item));
            question.setAnswer(question.getAnswer().toUpperCase());
            questionService.saveOrUpdate(question, xList);
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("question", question);
            model.put("errMsg", "保存失败");
            return "common_question/edit";
        }
        return "redirect:list.do";
    }

    /**
     * 异步请求 获取全部
     *
     * @param question 查询条件
     * @return
     */
//    @Menu(value = "获取全部试题")
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(CommonQuestion question) {
        try {
            List<CommonQuestion> list = questionService.findByObject(question);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }


    /**
     * 异步请求 删除
     *
     * @param question id
     * @return
     */
//    @Menu(value = "删除试题")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(CommonQuestion question) {
        try {
            questionService.delete(question);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }

    /**
     * 下载模板文件
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
//    @Menu(value = "下载试题模板")
    @RequestMapping(value = "/downModel")
    public String download(@RequestParam(value = "metaInfoId") Integer metaInfoId,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "知识类试题模板";
        String path = request.getSession().getServletContext().getRealPath("file");
        File file = new File(path, "question.xlsx");
        ExcelUtil.download(file, response, fileName);
        return null;
    }

    /**
     * 跳转到批量添加页面
     *
     * @param model
     * @return
     */
//    @Menu(value = "批量添加试题", isView = true)
    @RequestMapping(value = "/batchAdd", method = RequestMethod.GET)
    public String batchAdd(ModelMap model) {
        try {
            List<QuestionMetaInfo> metaInfoList = questionMetaInfoService.findByObject(null);
            model.put("metaInfoList", metaInfoList);
            List<QuestionBank> questionBankList = questionBankService.findByObject(null);
            model.put("questionBankList", questionBankList);
        } catch (Exception e) {
            logger.error("批量导入试题，获取题型信息失败", e);
            model.put("errMsg", "获取题型信息失败");
        }
        return "common_question/import";
    }


    /**
     * 批量上传
     * <p>
     * 暂时考虑ajax调用
     *
     * @param file
     * @param metaInfoId 题型id
     */
//    @Menu(value = "导入试题文件")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String upload(MultipartFile file, Integer metaInfoId, Integer isPub,
                         Integer questionBankId, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            List<List<String>> data = ExcelUtil.getData(file);
            redirectAttributes.addFlashAttribute("questionBankId", questionBankId);
            redirectAttributes.addFlashAttribute("metaInfoId", metaInfoId);
            if (data == null || data.size() < 2) {
                redirectAttributes.addFlashAttribute("err", "模板无数据");
                return "redirect:batchAdd.do";
            }
            QuestionMetaInfo metaInfo = questionMetaInfoService.findById(metaInfoId);
            List<String> title = data.get(0);
            boolean modelOk = QuestionMetaInfoUtil.checkModelTitle(title, metaInfo);
            if (!modelOk) {
                redirectAttributes.addFlashAttribute("err", "模板信息有误，请下载最新模板");
                return "redirect:batchAdd.do";
            }
            String info = QuestionMetaInfoUtil.checkData(data, metaInfo);
            if (StringUtils.isNotEmpty(info)) {
                redirectAttributes.addFlashAttribute("err", info);
                return "redirect:batchAdd.do";
            }
            Map<String, Integer> indexInfo = new HashMap<>();
            for (int i = 0; i < title.size(); i++) {
                String col = title.get(i);
                if ("题目".equals(col)) {
                    indexInfo.put("title", i);
                } else if (col.startsWith("最佳答案")) {
                    indexInfo.put("answer", i);
                } else if (col.startsWith("选项") && !indexInfo.containsKey("item")) {
                    indexInfo.put("item", i);
                }
            }
            if (!indexInfo.containsKey("item")) {
                indexInfo.put("item", title.size());
            }
            List<Integer> propIndexList = new ArrayList<>();
            for (int i = 0; i < indexInfo.get("item"); i++) {
                if (i != indexInfo.get("title") && i != indexInfo.get("answer")) {
                    propIndexList.add(i);
                }
            }

            data.remove(0);//删除标题

            User user = userService.findById(ShiroSessionUtil.getUserId());

            List<CommonQuestion> questions = new ArrayList<>();
//            List<List<Integer>> propItemIds = new ArrayList<>();
            String regex = "^([A-Z]|[a-z])([:：])\\s*";
            Pattern pattern = Pattern.compile(regex);
            int num = 1;
            for (List<String> line : data) {
                num++;
                CommonQuestion question = new CommonQuestion();
                question.setTitle(line.get(indexInfo.get("title")));
                question.setAnswer(line.get(indexInfo.get("answer")));
                int i = indexInfo.get("item");
                List<String> list = new ArrayList<>();
                char pre = 'A';

                for (; i < line.size(); i++) {
                    String col = line.get(i);
                    if (StringUtils.isEmpty(col)) {
                        continue;
                    }
                    if (pattern.matcher(col).matches()) {
                        list.add(col.toUpperCase());
                    } else {
                        list.add(pre + ":" + col);
                    }
                    pre = (char) (pre + 1);
                }

                question.setContent(JSON.toJSONString(list));

                if (metaInfo.getCode().equals("single") || metaInfo.getCode().equals("multi")) {
                    question.setAnswer(question.getAnswer().toUpperCase());
                    char[] arr = question.getAnswer().toCharArray();
                    for (char c : arr) {
                        if (c < 'A' || c >= pre) {
                            redirectAttributes.addFlashAttribute("第" + num + "行答案【" + question.getAnswer() + "】有误");
                            return "redirect:batchAdd.do";
                        }
                    }
                }
                question.setExtraInfo("");
                question.setDepartmentId(user.getDepartmentId());
                question.setStationId(user.getStationId());
                question.setBranchId(user.getBranchId());
                question.setCreateUserId(ShiroSessionUtil.getUserId());
                question.setCreateTime(new Date());
                question.setMetaInfoId(metaInfoId);
                question.setQuestionBankId(questionBankId);
                question.setStatus(CommonStatusEnum.ENABLE.getStatus());

                List<Integer> propItemId = new ArrayList<>();
                for (Integer id : propIndexList) {
                    String idStr = line.get(id);
                    if (StringUtils.isNotEmpty(idStr)) {
                        propItemId.add(Integer.valueOf(idStr));
                    }
                }

                question.setPropItemIdInfo(JSON.toJSONString(propItemId));
                questions.add(question);
            }
            try {
//                int count = questionService.batchAdd(questions, propItemIds);
                int count = questionService.batchAdd(questions, 30);
                logger.info("批量添加试题成功，成功添加{}试题。", count);
            } catch (Exception e) {
                logger.error("保存题目失败", e);
                return null;
            }
        } catch (Exception e) {
            logger.error("文件解析失败", e);
            model.put("errMsg", "文件解析失败，请确认");
        }
        return "redirect:list.do";
    }


    /**
     * 根据题型id获取
     *
     * @param id
     * @return
     */
//    @Menu(value = "获取试题属性列表")
    @RequestMapping("/getPropertyList")
    @ResponseBody
    public String getPropertyListByMetaInfoId(Integer id) {
        try {
            QuestionMetaInfo questionMetaInfo = questionMetaInfoService.findById(id);
            List<Property> propertyList = questionMetaInfo.getPropertyList();
            return JSON.toJSONString(propertyList, true);
        } catch (Exception e) {
            logger.error("获取题型属性失败", e);
            return "-1";
        }
    }

    //    @Menu(value = "未知，待确认")
    @RequestMapping(value = "/{action}", method = RequestMethod.GET)
    public String answerOrShow(@PathVariable("action") String action, Integer id, ModelMap model) {
        try {
            CommonQuestion question = questionService.findById(id);
            model.put("question", question);
        } catch (Exception e) {
            logger.error("操作：{}， id：{}, 失败", new String[]{action, id + ""}, e);
            model.put("errMsg", "失败");
        }
        return "common_question/" + action;

    }


    /**
     * 根据表格中选择的题型和属性值，获取数据库中还剩下的题目数量
     *
     * @param propId json格式的字符串
     * @param metaId
     * @return
     */
//    @Menu(value = "")
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    @ResponseBody
    public String getCountByPropItemId(String propId, @RequestParam Integer metaId, Integer bankId) {
        try {
            long count = 0;
            if (bankId == null) {
                count = QuestionUtil.getCountByMetaInfo(metaId);
            } else {
                if (StringUtils.isEmpty(propId)) {
                    count = QuestionUtil.getCountByMetaInfoAndBank(metaId, bankId);
                } else {
                    List<Integer> ids = JSON.parseArray(propId, Integer.class);
                    count = questionService.getCountByMetaInfoAndBankAndAttr(metaId, bankId, ids);
                }
            }
            return count + "";
        } catch (Exception e) {
            logger.error("获取指定属性的试题数量失败", e);
            return "-1";
        }
    }


    /**
     * 将题目统计信息初始化到系统中，
     * 缓存在redis上
     */
    private void initQuestion() throws TesException {
        try {
            PageInfo<CommonQuestion> pageInfo = new PageInfo<>(100, 1);
            questionService.findByObject(null, pageInfo);
            while (pageInfo.getData().size() == 100) {
                List<CommonQuestion> questions = pageInfo.getData();
                for (CommonQuestion question : questions) {
                    QuestionUtil.updateCacheAfterUpdate(question);
                }
                pageInfo.setCurPage(pageInfo.getCurPage() + 1);
                questionService.findByObject(null, pageInfo);
            }
            logger.info("试题初始化完成, 当前时间： {}", new Date());
        } catch (Exception e) {
            logger.error("初始化试题缓存信息失败", e);
        }
    }

    /**
     * 将数组或者列表转化成对应的字符串表示 已经排过序
     *
     * @param item
     * @return
     */
    private static String arrToString(List<Integer> item) {
        Collections.sort(item, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        StringBuilder builder = new StringBuilder();
        for (Integer i : item) {
            builder.append(i).append(",");
        }
        return builder.toString();
    }

    /**
     * 通过给定的属性值ID的组合，获取对应的全排列
     *
     * @param arr
     * @param canRepeat
     * @return
     */
    private static List<List<Integer>> processItemId(int[] arr, boolean canRepeat) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list = inRecursion(list, arr, i + 1, canRepeat);
        }
        return list;
    }

    //递归内部执行
    private static List<List<Integer>> inRecursion(List<List<Integer>> list, int[] arr, int propNum, boolean canRepeat) {
        List<List<Integer>> newList = new ArrayList<>();
        if (list.size() == 0) {
            for (int id : arr) {
                List<Integer> l = new ArrayList<>();
                l.add(id);
                newList.add(l);
            }
        } else {
            for (List<Integer> item : list) {
                int count = item.size();
                for (int id : arr) {
                    if (propNum <= count) {
                        continue;
                    }
                    if (item.contains(id)) {
                        continue;
                    }
                    List<Integer> newItem = new ArrayList<>();
                    newItem.addAll(item);
                    newItem.add(id);
                    if (!canRepeat) {
                        if (listEqual(newList, newItem) || listEqual(list, newItem)) {
                            continue;
                        }
                    }
                    newList.add(newItem);
                }
            }
        }
        list.addAll(newList);
        return list;
    }

    private static boolean listEqual(List<List<Integer>> list, List<Integer> element) {
        for (List<Integer> item : list) {
            if (item.containsAll(element)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 更新试题缓存
     * 开场时候发现试题不足时候可以请求
     *
     * @return
     * @throws Exception
     */
//    @Menu(value = "")
    @RequestMapping(value = "/updateCache", method = RequestMethod.POST)
    @ResponseBody
    public String updateCache() throws Exception {
        JSONObject json = new JSONObject();
        try{
            QuestionUtil.initCacheOfCommonQuestion();
            json.put("code", 0);
        }catch (Exception e){
            json.put("code", -1);
            logger.error("更新试题缓存失败", e);
        }
        return json.toJSONString();

    }

    public static void main(String[] args) {
        String info = "A:                  招行";
        String regex = "([A-Z]|[a-z])([:：])\\s{0,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(info);
        boolean match = matcher.matches();
        System.out.println("match: " + match);
        info = info.replaceAll(regex, "");
        System.out.println("info: " + info);
    }
}

package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Authority;
import co.bugu.tes.service.IAuthorityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Map;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    IAuthorityService authorityService;

    private static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    /**
     * 列表，分页显示
     *
     * @param authority 查询数据
     * @param curPage   当前页码，从1开始
     * @param showCount 当前页码显示数目
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Authority authority, Integer curPage, Integer showCount, ModelMap model) {
        try {
            if (authority != null && authority.getType() != null && authority.getType() == -1) {
                authority.setType(null);
            }
            List<String> controllerList = authorityService.getAllController();
            model.put("controllerList", controllerList);
            PageInfo<Authority> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = authorityService.findByObject(authority, pageInfo);
            model.put("pi", pageInfo);
            model.put("authority", authority);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "authority/list";

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String toAdd() {
        return "authority/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(ModelMap model, Authority authority) {
        try {
            authority.setStatus(0);
            authority.setUrl(null);
            authority.setType(0);
            authorityService.save(authority);
            return "redirect:list.do";
        } catch (Exception e) {
            logger.error("保存失败", e);
            model.put("err", "保存失败");
            model.put("authority", authority);
            return "authority/add.do";
        }
    }

    /**
     * 查询数据后跳转到对应的编辑页面
     *
     * @param id    查询数据，一般查找id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ResponseBody
    public String toEdit(Integer id, ModelMap model) {
        JSONObject json = new JSONObject();
        try {
            Authority authority = authorityService.findById(id);
            json.put("code", 0);
            json.put("data", authority);
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            json.put("code", -1);
            json.put("msg", "服务错误");
        } finally {
            return json.toString();
        }

    }

    /**
     * 保存结果，根据是否带有id来表示更新或者新增
     *
     * @param authority
     * @param model
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Authority authority, ModelMap model) {
        JSONObject json = new JSONObject();
        try {
            if (authority.getId() == null) {
                authorityService.save(authority);
            } else {
                authorityService.updateById(authority);
            }
            json.put("code", 0);
        } catch (Exception e) {
            logger.error("保存失败", e);
            json.put("code", -1);
            json.put("err", "保存权限失败");
        }
        return json.toJSONString();
    }

    /**
     * 异步请求 获取全部
     *
     * @param authority 查询条件
     * @return
     */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Authority authority) {
        try {
            List<Authority> list = authorityService.findByObject(authority);
            return JsonUtil.toJsonString(list);
        } catch (Exception e) {
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
     * 异步请求 删除
     *
     * @param authority id
     * @return
     */

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Authority authority) {
        try {
            authorityService.delete(authority);
            return "0";
        } catch (Exception e) {
            logger.error("删除失败", e);
            return "-1";
        }
    }

    /**
     * 初始化当前系统的所有mappingRequest，
     * 并根据数据使用情况进行处理，已经存在的，忽略，否则入库
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/init")
    public String init(ModelMap model) {
//        try {
//            List<Authority> authorities = authorityService.findByObject(null);
//            List<String> codeList = new ArrayList<>();
//
//            Map<String, Integer> authCodeIdMap = new HashMap<>();
//            for (Authority a : authorities) {
//                codeList.add(a.getCode());
//                authCodeIdMap.put(a.getCode(), a.getId());
//            }
//            List<MvcParam> list = ReflectUtil.getAnnotationInfo(AuthorityController.class.getPackage().getName());
//
//            Set<String> controllerSet = new HashSet<>();
//            for (MvcParam param : list) {
//                controllerSet.add(param.getControllerName());
//            }
//
//
//            /**
//             * 处理菜单权限外层容器的增删改
//             * */
//            for (String ctrler : controllerSet) {
//                String code = ctrler.toUpperCase();
//
//                Authority auth = new Authority();
//                auth.setStatus(Constant.STATUS_ENABLE);
//                auth.setController(ctrler);
//                auth.setType(Constant.AUTH_TYPE_BOX);
//                auth.setCode(code);
//
//
//                auth.setIsApi(Constant.AUTH_API_FALSE);
//                if (authCodeIdMap.containsKey(code)) {
//                    auth.setId(authCodeIdMap.get(code));
//                    authorityService.updateById(auth);
//                } else {
//                    auth.setName(ctrler);
//                    auth.setDescription(code + "菜单");
//                    authorityService.save(auth);
//                    authCodeIdMap.put(code, auth.getId());
//                }
//            }
//
//            /**
//             * 处理菜单权限
//             * */
//            for (MvcParam param : list) {
//                String method = StringUtils.isEmpty(param.getMethod()) ? "" : param.getMethod().toUpperCase();
//                String url = param.getRootPath() + param.getPath();
//                String code = getCodeFromUrl(url) + (StringUtils.isEmpty(method) ? "" : "_" + method);
//                String parentCode = param.getControllerName().toUpperCase();
//
//
//                Authority auth = new Authority();
//                auth.setSuperiorId(authCodeIdMap.get(parentCode));
//                auth.setStatus(Constant.STATUS_ENABLE);
//                auth.setAction(param.getMethodName());
//                auth.setController(param.getControllerName());
//                auth.setDescription("");
//                auth.setName(code);
//                auth.setParam(null);
//                auth.setType(Constant.AUTH_TYPE_MENU);
//                auth.setUrl(url);
//                auth.setCode(code);
//                auth.setAcceptMethod(method);
//                auth.setIsApi(param.getApi() ? Constant.AUTH_API_TRUE : Constant.AUTH_API_FALSE);
//
//                if (authCodeIdMap.containsKey(code)) {
//                    auth.setId(authCodeIdMap.get(code));
//                    authorityService.updateById(auth);
//                } else {
//                    authorityService.save(auth);
//                }
//                codeList.remove(code);
//            }
//
//            /**
//             * 删除系统中不存在的权限信息
//             * */
//            for (String item : codeList) {
//                Integer id = authCodeIdMap.get(item);
//                Authority authority = new Authority();
//                authority.setId(id);
//                authorityService.delete(authority);
//            }
//
//
//        } catch (Exception e) {
//            logger.error("初始化工程内的信息失败", e);
//        }
//        return "redirect:list.do";
        return "";
    }

    private static String getCodeFromUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        } else {
            url = url.toUpperCase();
            if (url.startsWith("/")) {
                url = url.substring(1, url.length());
            }
            return url.replaceAll("/", "_");
        }
    }

    public static void main(String[] args) {
        String url = "/tes/role/get.do";
        System.out.println(getCodeFromUrl(url));
    }

    /**
     * 通过ztree进行管理
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(ModelMap modelMap) {
        try {
            Authority authority = new Authority();
            authority.setStatus(Constant.STATUS_ENABLE);
            List<Authority> list = authorityService.findByObject(authority);
            List<Map<String, Object>> res = new ArrayList<>();
            for (Authority auth : list) {
                Map<String, Object> map = new HashedMap();
                map.put("id", auth.getId());
                map.put("pId", auth.getSuperiorId() == null ? 0 : auth.getSuperiorId());
                map.put("name", StringUtils.isEmpty(auth.getName()) ? auth.getUrl() : auth.getName());
                if (auth.getUrl() != null && !auth.getUrl().equals("")) {//url菜单
                    map.put("dropInner", false);
                } else {//菜单目录

                }
                res.add(map);
            }
            modelMap.put("zNode", JSON.toJSONString(res));
        } catch (Exception e) {
            logger.error("获取权限管理信息失败", e);
            return "redirect:list.do";
        }
        return "authority/manage";
    }

    /**
     * 批量更新ztree提交的数据
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(String info) {
        try {
            List<Authority> authorityList = new ArrayList<>();
            JSONArray arr = JSON.parseArray(info);
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                Authority authority = null;
                Integer id = obj.getInteger("id");
                Integer superiorId = obj.getInteger("pId");
                String name = obj.getString("name");
                if (id == null) {//新增的
                    authority = new Authority();
                    authority.setId(id);
                    authority.setType(Constant.AUTH_TYPE_MENU);
                    authority.setDescription("");
                } else {
                    authority = authorityService.findById(id);
                }
                authority.setIdx(i);
                authority.setName(name);
                authority.setSuperiorId(superiorId);
                authority.setStatus(Constant.STATUS_ENABLE);
                authorityList.add(authority);
            }
            authorityService.rebuildInfo(authorityList);
            return "0";
        } catch (Exception e) {
            logger.error("批量更新失败", e);
            return "-1";
        }
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public String commit(Authority authority) {
        try {
            authorityService.updateById(authority);
        } catch (Exception e) {
            logger.error("表格提交失败", e);
            return "-1";
        }
        return "0";
    }


    @RequestMapping(value = "initMenu", method = RequestMethod.POST)
    public String initMenu() {
        try {
            authorityService.initAuthority(this.getClass().getPackage().getName());
        } catch (Exception e) {
            logger.error("初始化菜单失败", e);
        }
        return "redirect:list.do";
    }


}

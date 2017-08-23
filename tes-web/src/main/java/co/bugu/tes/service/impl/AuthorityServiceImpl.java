package co.bugu.tes.service.impl;


import co.bugu.framework.core.dao.BaseDao;
import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.core.util.ReflectUtil;
import co.bugu.tes.model.Authority;
import co.bugu.tes.service.IAuthorityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements IAuthorityService {
    @Autowired
    BaseDao<Authority> baseDao;

//    public int saveOrUpdate(Authority authority) {
//        if(authority.getId() == null){
//            return baseDao.insert("tes.authority.insert", authority);
//        }else{
//            return baseDao.update("tes.authority.updateById", authority);
//        }
//    }


    public void rebuildInfo(List<Authority> authorityList) {
        for (Authority authority : authorityList) {
            if (authority.getId() == null) {
                baseDao.insert("tes.authority.insert", authority);
            } else {
                baseDao.update("tes.authority.updateById", authority);
            }
        }
    }

    @Override
    public List<String> getAllController() {
        return baseDao.selectList("tes.authority.getAllController");
    }

    @Override
    public List<Authority> selectAuthorityByRole(Integer roleId) {
        List<Authority> list = baseDao.selectList("tes.authority.selectAuthorityByRole", roleId);
        return list;
    }

    @Override
    public void initAuthority(String packageName) {
        Integer version;
        Integer max = baseDao.selectOne("tes.authority.getMaxVersion");
        if (max == null) {
            version = 0;
        } else {
            version = max + 1;
        }
        Authority queryObj = new Authority();
//        获取当前包下的所有类
        Set<Class<?>> classes = ReflectUtil.getClasses(packageName);
        for (Class<?> cla : classes) {
//            List<Authority> list = getOnClass(cla);
            List<Authority> list = null;
            if (CollectionUtils.isNotEmpty(list)) {
                Authority authority = list.get(0);
                authority.setVersion(version);
                queryObj.setCode(authority.getCode());

                List<Authority> queryList = this.findByObject(queryObj);
                if (CollectionUtils.isNotEmpty(queryList)) {
                    authority.setId(queryList.get(0).getId());
                    authority.setDescription(queryList.get(0).getDescription());
                    this.updateById(authority);
                } else {
                    this.save(authority);
                }

//                list = getOnMethod(cla);
                list = null;
                if (CollectionUtils.isNotEmpty(list)) {
                    for (Authority a : list) {
                        a.setVersion(version);
                        a.setSuperiorId(authority.getId());
                        queryObj.setCode(a.getCode());
                        queryList = this.findByObject(queryObj);
                        if (CollectionUtils.isNotEmpty(queryList)) {
                            a.setId(queryList.get(0).getId());
                            a.setDescription(queryList.get(0).getDescription());
                            this.updateById(a);
                        } else {
                            this.save(a);
                        }
                    }
                }
            }
        }
        baseDao.update("tes.authority.enablePreviousVersion", version);
    }

    @Override
    public void batchUpdate(Authority authority) {

    }


    private String getAuthorityCode(Authority authority) {
        String method = authority.getAcceptMethod();
        String url = authority.getUrl();
        if (StringUtils.isEmpty(url)) {
            url = authority.getController();
        } else {
            url = url.toUpperCase();
            if (url.startsWith("/")) {
                url = url.substring(1, url.length());
            }
            url = url.replaceAll("/", "_");
        }
        String res = "";
        if(StringUtils.isEmpty(method)){
            res = url;
        }else{
            res = url + "_" + method;
        }
        return (url + method).toUpperCase();
    }

    /**
     * 获取方法级别的RequestMapping设置的http访问方法
     *
     * @param requestMapping
     * @return
     */
    private String getHttpMethod(RequestMapping requestMapping) {
        if (requestMapping == null) {
            return "";
        }
        RequestMethod[] ms = requestMapping.method();
        if (ms == null && ms.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (RequestMethod m : ms) {
                buffer.append(m.name())
                        .append("_");
            }
            if(buffer.length() > 0){
                return buffer.deleteCharAt(buffer.length() - 1).toString();
            }else{
                return "";
            }
        }
        return "";
    }

    /**
     * 获取方法的参数类型
     *
     * @param method
     * @return
     */
    private String getParameterTypes(Method method) {
        if (method == null) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();
            Class<?>[] classes = method.getParameterTypes();
            for (Class clazz : classes) {
                buffer.append(clazz.getSimpleName())
                        .append(";");
            }
            return buffer.toString();
        }

    }

    /**
     * 获取类级别的menuInfo
     *
     * @param clazz
     * @return
     */
//    private List<Authority> getOnClass(Class<?> clazz) {
//        List<Authority> list = new ArrayList<>();
//        String controller = clazz.getSimpleName();
//        String method = null;
//        Controller con = clazz.getAnnotation(Controller.class);
//        RestController restCon = clazz.getAnnotation(RestController.class);
//        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
//        Menu menu = clazz.getAnnotation(Menu.class);
//        if (con == null && restCon == null) {
//            return list;
//        }
//        Authority authority = new Authority();
//        if (requestMapping != null) {
//            authority.setName(controller);
//            method = getHttpMethod(requestMapping);
//            authority.setAcceptMethod(method);
//        }
//
//        if (menu != null) {
//            String name = menu.value();
//            boolean isBox = menu.isBox();
//            boolean isView = menu.isView();
//            authority.setController(controller);
//            authority.setAction("");
//            authority.setApi(false);
//            authority.setBox(isBox);
//            authority.setView(isView);
//            authority.setName(name);
//        }
//        authority.setCode(getAuthorityCode(authority));
//        list.add(authority);
//        return list;
//    }

    /**
     * 获取方法级别的MenuInfo
     *
     * @param clazz
     * @return
     */
//    private List<Authority> getOnMethod(Class<?> clazz) {
//        List<Authority> list = new ArrayList<>();
//
//        String controller = clazz.getSimpleName();
//        String rootPath = "";
//        Controller con = clazz.getAnnotation(Controller.class);
//        RestController restCon = clazz.getAnnotation(RestController.class);
//        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
//        Menu menu = clazz.getAnnotation(Menu.class);
//        if (con == null && restCon == null) {
//            return list;
//        }
//
//        if (requestMapping != null) {
//            rootPath = requestMapping.value()[0];
//        }
//
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method m : methods) {
//            Menu actionMenu = m.getAnnotation(Menu.class);
//            RequestMapping actionRequestMapping = m.getAnnotation(RequestMapping.class);
//            if (actionRequestMapping == null) {
//                continue;
//            }
//            ResponseBody actionResponseBody = m.getAnnotation(ResponseBody.class);
////            MenuInfo menuInfo = new MenuInfo();
//            Authority authority = new Authority();
//            authority.setAcceptMethod(getHttpMethod(actionRequestMapping));
//            authority.setAction(m.getName());
//            authority.setApi(actionResponseBody != null);
//            authority.setController(controller);
//            authority.setUrl(rootPath + actionRequestMapping.value()[0]);
//            authority.setParameter(getParameterTypes(m));
//
//
//            String name = "";
//            boolean isBox = false;
//            boolean isView = false;
//            if (actionMenu != null) {
//                name = actionMenu.value();
//                isBox = actionMenu.isBox();
//                isView = actionMenu.isView();
//            }
//
//            if (StringUtils.isEmpty(name)) {
//                name = m.getName();
//            }
//            authority.setName(name);
//            authority.setBox(isBox);
//            authority.setView(isView);
//            authority.setCode(getAuthorityCode(authority));
//            list.add(authority);
//
//        }
//        return list;
//    }


}

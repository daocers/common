package co.bugu.tes.shiro;

import co.bugu.tes.model.Permission;
import co.bugu.tes.model.Role;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IPermissionService;
import co.bugu.tes.service.IRoleService;
import co.bugu.tes.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QDHL on 2017/7/24.
 */
public class TesReam extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(TesReam.class);
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    /**
     * 为当前登录的Subject授予角色和权限
     *
     * @se e  经测试:本例中该方法的调用时机为需授权资源被访问时
     * @se e 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * @se e 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * @se e  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String currentUsername = (String) super.getAvailablePrincipal(principals);
        User user = new User();
        user.setUsername(currentUsername);
        List<User> userList = userService.findByObject(user);
        if (CollectionUtils.isNotEmpty(userList) && userList.size() == 1) {
            user = userList.get(0);
            List<Role> roleList = roleService.findByUserId(user.getId());
            if (CollectionUtils.isNotEmpty(roleList)) {
                for (Role role : roleList) {
                    info.addRole(role.getCode());
                    List<Permission> permissionList = permissionService.findByRoleId(role.getId());
                    if (CollectionUtils.isNotEmpty(permissionList)) {
                        for (Permission permission : permissionList) {
                            info.addStringPermission(permission.getCode());
                        }
                    }
                }
            }
            return info;
        } else {
            //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
            //详见applicationContext.xml中的<bean id="shiroFilter">的配置
            return null;
        }

    }


    /**
     * 验证当前登录的Subject
     *
     * @se e 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        //两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = "";
        StringBuilder builder = new StringBuilder();
        if (ArrayUtils.isNotEmpty(token.getPassword())) {
            for (char c : token.getPassword()) {
                builder.append(c);
            }
        }else{
            return null;
        }
        password = builder.toString();

        User user = new User();
        user.setUsername(username);
        List<User> userList = userService.findByObject(user);
        if (CollectionUtils.isNotEmpty(userList) && userList.size() == 1) {
            //加密后的password
            try {
                password = Base64.encodeToString((password + user.getSalt()).getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("获取加密后的密码失败", e);
            }
            if (password.equals(userList.get(0).getPassword())) {
                Session session = SecurityUtils.getSubject().getSession();
                session.setAttribute("userId", userList.get(0).getId());
                return new SimpleAuthenticationInfo(username, token.getPassword(), getName());
            }
        } else {
            return null;
        }
//        System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
//      User user = userService.getByUsername(token.getUsername());
//      if(null != user){
//          AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getNickname());
//          this.setSession("currentUser", user);
//          return authcInfo;
//      }else{
//          return null;
//      }
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
        //这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
//        if("jadyer".equals(token.getUsername())){
//            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("jadyer", "jadyer", this.getName());
//            this.setSession("currentUser", "jadyer");
//            return authcInfo;
//        }else if("玄玉".equals(token.getUsername())){
//            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("玄玉", "xuanyu", this.getName());
//            this.setSession("currentUser", "玄玉");
//            return authcInfo;
//        }
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
        return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @s ee  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
//        Subject currentUser = SecurityUtils.getSubject();
//        if(null != currentUser){
//            Session session = currentUser.getSession();
//            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
//            if(null != session){
//                session.setAttribute(key, value);
//            }
//        }
    }
}
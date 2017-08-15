package co.bugu.tes.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by daocers on 2017/8/13.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登陆请求页面
     *
     * @return
     */
    @RequestMapping("login")
    public String toLogin() {
        return "login/login";
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    @ResponseBody
    public String signIn(String username, String password, Integer rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe == 0);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                return "redirect:/";
            } else {
                return "login/login";
            }
        } catch (Exception e) {
            logger.error("登录失败", e);
            return "-1";
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    public String signOut() {
        SecurityUtils.getSubject().logout();
        return "login/login";
    }
}

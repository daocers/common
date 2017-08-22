package co.bugu.tes.controller;

import co.bugu.framework.util.EncryptUtil;
import co.bugu.tes.enumration.CommonStatus;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by daocers on 2017/8/13.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    IUserService userService;

    /**
     * 登陆请求页面
     *
     * @return
     */
    @RequestMapping("login")
    public String toLogin(ModelMap model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()) {
            model.put("username", subject.getPrincipal());
            model.put("rememberMe", 0);
        }
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
                return "0";
            } else {
                return "-1";
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

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "user/register";
    }

    @RequestMapping("/register")
    public String register(User user, RedirectAttributes redirectAttributes) {
        String salt = EncryptUtil.getSalt(6);
        user.setSalt(salt);
        String finalPass = Base64.encodeToString((user.getPassword() + user.getSalt()).getBytes());
        user.setPassword(finalPass);
        user.setStatus(CommonStatus.ENABLE.getStatus());
        userService.save(user);
        redirectAttributes.addAttribute("id", user.getId());
        return "redirect:/login.do";
    }
}

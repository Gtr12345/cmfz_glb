package com.baizhi.controller;

import com.baizhi.entity.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("loginAdmin")
public class LoginController {
    @RequestMapping("login")
    @ResponseBody
    public Map<String, Object> login(Admin admin, String enCode, HttpServletRequest request) {
        System.out.println(admin);
        System.out.println(enCode);
        Map<String, Object> map = new HashMap<>();
        //第一步获取主体
        Subject subject = SecurityUtils.getSubject();
        //封装token
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (enCode.equals(code)) {
            try {
                subject.login(token);
                System.out.println(token.getUsername());
                map.put("status", true);
            } catch (UnknownAccountException e) {
                System.out.println("用户名错误");
                e.getMessage();
                map.put("status", false);
                map.put("message", "用户名错误");

            } catch (IncorrectCredentialsException e) {
                System.out.println("密码错误");
                e.getMessage();
                map.put("status", false);
            }
        } else {
            System.out.println("验证码错误");
            map.put("status", false);
            map.put("message", "验证码错误");
        }
        return map;
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login/login.jsp";
    }
}

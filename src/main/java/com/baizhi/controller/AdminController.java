package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map<String, Object> login(Admin admin, String enCode, HttpServletRequest request) {
        System.out.println(admin);
        System.out.println(enCode);
        Map<String, Object> map = new HashMap<>();
        try {
            adminService.login(admin, enCode, request);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("login2")
    public Map<String, Object> login2(String phone, String oneCode, HttpServletRequest request) {
        System.out.println(phone);
        System.out.println(oneCode);
        Map<String, Object> map = new HashMap<>();
        try {
            adminService.login2(phone, oneCode, request);
            map.put("status", true);
        } catch (Exception e) {
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("clear")
    public void clear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("loginAdmin");
    }
}

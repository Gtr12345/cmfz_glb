package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("adminService")
@Transactional

public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void login(Admin admin, String enCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (code.equals(enCode)) {
            System.out.println(admin);
            Admin admin1 = adminDao.selectOne(admin);
            if (admin1 != null) {
                if (admin1.getPassword().equals(admin.getPassword())) {
                    session.setAttribute("loginAdmin", admin);
                } else {
                    throw new RuntimeException("密码错误");
                }
            } else {
                throw new RuntimeException("用户不存在");
            }
        } else {
            throw new RuntimeException("验证码错误");
        }
    }

    @Override
    public void login2(String phone, String oneCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object oneCode1 = session.getAttribute("oneCode");
        if (oneCode1 != null) {
            if (oneCode1.equals(oneCode)) {
                Admin admin1 = new Admin();
                admin1.setPhone(phone);
                Admin admin = adminDao.selectOne(admin1);
                if (admin1 != null) {
                    session.setAttribute("loginAdmin", admin);
                } else {
                    throw new RuntimeException("用户不存在");
                }
            } else {
                throw new RuntimeException("验证码错误");
            }
        } else {
            throw new RuntimeException("验证码超时");
        }

    }
}

package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("code")
@Controller
public class CodeConttroller {
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String code = SecurityCode.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        BufferedImage image = SecurityImage.createImage(code);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }
}

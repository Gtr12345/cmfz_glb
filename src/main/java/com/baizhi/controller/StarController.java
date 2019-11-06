package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;

    @RequestMapping("queryAll")
    private List<Star> queryAll() {
        List<Star> stars = starService.queryAll();
        stars.forEach(star -> System.out.println(star.getNickname()));
        return stars;
    }

    @RequestMapping("selectAll")
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        System.out.println("--------------------");
        Map<String, Object> map = starService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Star star, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                String id = starService.addStar(star);
                map.put("message", id);
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile photo, String id, HttpServletRequest request) throws IOException {
        System.out.println("-------------------------------------");
        //获取文件后缀，设置新名
        String filename = photo.getOriginalFilename();
        String substring = filename.substring(filename.indexOf("."), filename.length());
        String sname = UUID.randomUUID().toString() + substring;
        System.out.println(sname);
        Map<String, Object> map = new HashMap<>();
        try {
            //文件上传
            photo.transferTo(new File(request.getServletContext().getRealPath("/star/img"), sname));
            Star star = new Star();
            star.setId(id);
            star.setPhoto(sname);
            starService.edit(star);
            System.out.println(star);
            map.put("status", true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }
}

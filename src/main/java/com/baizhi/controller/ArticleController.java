package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("search")
    public List<Article> search(String content) {
        List<Article> articles = articleService.search(content);
        return articles;
    }
    @RequestMapping("selectAll")
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("add")
    public Map<String, Object> add(Article article) {
        Map<String, Object> map = new HashMap<>();
        articleService.add(article);
        map.put("status", true);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(Article article) {
        Map<String, Object> map = new HashMap<>();
        articleService.edit(article);
        map.put("status", true);
        return map;
    }

    @RequestMapping("delete")
    public Map<String, Object> delete(String id) {
        Map<String, Object> map = new HashMap<>();
        articleService.delete(id);
        map.put("status", true);
        return map;
    }
}

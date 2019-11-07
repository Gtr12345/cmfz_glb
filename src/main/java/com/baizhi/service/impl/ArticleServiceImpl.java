package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        System.out.println("ssss");
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        Integer count = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", articles);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @ClearRedis
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        articleDao.insert(article);
    }

    @Override
    @ClearRedis
    public void edit(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @ClearRedis
    public void delete(String id) {
        Article article = new Article();
        article.setId(id);
        articleDao.delete(article);
    }
}

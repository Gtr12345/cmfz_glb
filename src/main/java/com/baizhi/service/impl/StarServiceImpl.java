package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Star> stars = starDao.selectByRowBounds(star, rowBounds);
        int count = starDao.selectCount(star);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", stars);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public List<Star> queryAll() {
        Star star = new Star();
        List<Star> list = starDao.select(star);
        return list;
    }

    @Override
    @ClearRedis
    public String addStar(Star star) {
        star.setId(UUID.randomUUID().toString());
        star.setBir(new Date());
        int insert = starDao.insert(star);
        if (insert == 0) {
            throw new RuntimeException("添加失败");
        }
        return star.getId();
    }

    @Override
    @ClearRedis
    public void edit(Star star) {
        if ("".equals((star.getPhoto()))) {
            star.setPhoto(null);
        }
        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }
}

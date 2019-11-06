package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {
    Map<String, Object> selectAll(Integer page, Integer rows);

    List<Star> queryAll();

    String addStar(Star star);

    void edit(Star star);
}

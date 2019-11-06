package com.baizhi.dao;

import com.baizhi.entity.Trend;

import java.util.List;

public interface UserMapperDao {
    List<Trend> queryAllNan();

    List<Trend> queryAllNv();
}

package com.baizhi.service.impl;

import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.UserDao;
import com.baizhi.dao.UserMapperDao;
import com.baizhi.entity.Trend;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", users);
        map.put("total", count % rows == 0 ? count / rows : rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", users);
        map.put("total", count % rows == 0 ? count / rows : rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public List<User> queryAll() {
        List<User> users = userDao.selectAll();
        return users;
    }

    @Autowired
    private UserMapperDao userMapperDao;

    @Override
    public List<Trend> queryNan() {
        List<Trend> users = userMapperDao.queryAllNan();
        return users;
    }

    @Override
    public List<Trend> queryNv() {
        List<Trend> users = userMapperDao.queryAllNv();
        return users;
    }
}

package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Album album = new Album();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        Integer count = albumDao.selectCount(album);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", albums);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public String addalbum(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        album.setCreateDate(new Date());
        int insert = albumDao.insert(album);
        if (insert == 0) {
            throw new RuntimeException("添加失败");
        }
        return album.getId();
    }

    @Override
    public void edit(Album album) {
        System.out.println(album);
        if ("".equals(album.getCover())) {
            album.setCover(null);
        }
        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
}

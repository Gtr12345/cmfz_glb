package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    Map<String, Object> selectAll(Integer page, Integer rows);

    String addalbum(Album album);

    void edit(Album album);

    Album selectOne(String id);
}

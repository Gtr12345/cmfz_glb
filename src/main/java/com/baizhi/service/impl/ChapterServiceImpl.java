package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Override
    @RedisCache
    public Map<String, Object> selectChaptersByAlbumId(Integer page, Integer rows, String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        int count = chapterDao.selectCount(chapter);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", chapters);
        map.put("total", count % rows == 0 ? count / rows : rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @ClearRedis
    public String add(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int i = chapterDao.insertSelective(chapter);
        if (i == 0) {
            throw new RuntimeException("添加章节失败");
        }
        return chapter.getId();
    }

    @Override
    @ClearRedis
    public void edit(Chapter chapter) {
        System.out.println(chapter);
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if (i == 0) {
            throw new RuntimeException("修改章节失败");
        }
    }
}

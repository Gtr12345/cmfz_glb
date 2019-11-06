package com.baizhi.cmfz_glb;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Trend;
import com.baizhi.entity.User;
import com.baizhi.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class CmfzGlbApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StarService starService;
    @Autowired
    private UserService userService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ArticleService articleService;

    @Test
    void contextLoadssssss() {
        List<Trend> trends = userService.queryNan();
        trends.forEach(s -> System.out.println(s));
    }

    @Test
    void contextLoadsssss() {
        List<User> users = userService.queryAll();
        users.forEach(s -> System.out.println(s));
    }

    @Test
    void contextLoadssss() {
        Map<String, Object> map = articleService.selectAll(1, 3);
        System.out.println(map);
    }

    @Test
    void contextLoadsss() {
        Map<String, Object> map = chapterService.selectChaptersByAlbumId(1, 2, "4a6a3f9f-53c9-4325-9d15-a16b5ed5555c");
        System.out.println(map);
    }

    @Test
    void contextLoadss() {
        List<Chapter> chapters = chapterDao.selectAll();
        System.out.println(chapters);
    }

    @Test
    void contextLoads() {
        Map<String, Object> map = albumService.selectAll(1, 1);
        System.out.println(map);
    }

    @Test
    void contextLoads1() {
        Album album = new Album();
        album.setId("ec2d9079-975e-46a7-9d43-67c5d7a6fa90");
        album.setCover("1.jpg");
        int i = albumDao.updateByPrimaryKeySelective(album);
        System.out.println(i);
    }

    @Test
    void contextLoads1w() {
        Album album = new Album();
        album.setId("82a2c46a-0001-440c-9def-241544815aa6");
        album.setCover("1.jpg");
        albumService.edit(album);
    }
}

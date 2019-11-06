package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Trend;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("selectAll")
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        Map<String, Object> map = userService.selectUsersByStarId(page, rows, starId);
        return map;
    }

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = userService.selectAll(page, rows);
        System.out.println(map);
        return map;
    }

    @RequestMapping("export")
    public void export(HttpServletResponse resp) {
        List<User> users = userService.queryAll();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户", "用户"), User.class, users);

        String fileName = "用户报表(" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            //设置 response
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("content-disposition", "attachment;filename=" + fileName);
            workbook.write(resp.getOutputStream());/*D:/framework/untitled1/cmfz_glb/src/main/webapp/user/img*/
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("echarts")
    public Map<String, Object> echarts() {
        List<Trend> trends = userService.queryNan();
        List<Trend> trend = userService.queryNv();
        List<String> names = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        List<Integer> count1 = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {//月
            names.add(i + "月");
        }
        for (int i = 1; i <= 6; i++) {
            int j = i;
            for (Trend trend1 : trends) {//男
                if (trend1.getDate().equals(i)) {
                    count1.add(trend1.getCount());
                    j++;
                }
            }
            if (j == i) {
                count1.add(0);
            }
        }
        for (int i = 1; i <= 6; i++) {
            int j = i;
            for (Trend trend1 : trend) {//女
                if (trend1.getDate().equals(i)) {
                    count.add(trend1.getCount());
                    j++;
                }
            }
            if (j == i) {
                count.add(0);
            }
        }
        System.out.println("男" + count1);
        System.out.println("女" + count);
        //names.forEach(name-> System.out.println(name));
        Map<String, Object> map = new HashMap<>();
        map.put("names", names);
        map.put("count1", count1);//男
        map.put("count", count);//女
        return map;
    }
}

package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TestImport1 {
    //导入
    public static void main(String[] args) throws Exception {
        //获取输入流
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:/后期项目/day6/user.xls")));
        HSSFSheet sheet = workbook.getSheet("用户信息");
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            User user = new User();
            //获取行
            HSSFRow row = sheet.getRow(i);
            //获取类对象
            //Class<?> aClass = Class.forName("com.baizhi.poi.User");
            //Class<? extends User> aClass = user.getClass();
            /*Class<User> userClass = User.class;
            for (int j;j<;j++){

            }
            list.add(user);*/
        }
        list.forEach(user -> System.out.println(user));
    }
}

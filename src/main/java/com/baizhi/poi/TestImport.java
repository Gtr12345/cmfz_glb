package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestImport {
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
            //获取单元格
            HSSFCell cell = row.getCell(0);
            //获取单元格的值
            String id = cell.getStringCellValue();
            user.setId(id);

            HSSFCell cell1 = row.getCell(1);
            String name = cell1.getStringCellValue();
            user.setName(name);
            HSSFCell cell2 = row.getCell(2);
            Date bir = cell2.getDateCellValue();
            user.setBir(bir);
            list.add(user);
        }
        list.forEach(user -> System.out.println(user));
    }
}

package com.baizhi.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class TestPoi {
    public static void main(String[] args) throws Exception {
        //准备excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 3);
        //设置日期格式
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        //创建样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //把日期格式添加到样式里
        cellStyle.setDataFormat(format);
        //设置居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //创建字体样式
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        //绑定样式
        cellStyle.setFont(font);
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("测试");
        sheet.addMergedRegion(cellRangeAddress);
        //设置列宽
        sheet.setColumnWidth(0, 25 * 256);
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        //写值
        cell.setCellValue("索利亚尬痛");
        cell1.setCellValue(new Date());
        //把样式添加到单元格中
        cell.setCellStyle(cellStyle);
        cell1.setCellStyle(cellStyle);
        workbook.write(new FileOutputStream(new File("E:/后期项目/day6/test.xls")));
    }
}

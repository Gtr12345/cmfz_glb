package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名")
    private String username;
    private String password;
    private String salt;
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "电话")
    private String phone;
    @Excel(name = "省")
    private String province;
    @Excel(name = "市")
    private String city;
    private String sign;
    @Excel(name = "头像", type = 2)
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", format = "yyyy-MM-dd")
    private Date createDate;
    private String starId;
}

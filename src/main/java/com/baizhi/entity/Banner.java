package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Banner {
    @Id
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    @JSONField(format = "yyyy-MM-dd")
    private Date create_date;
}

package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * date: 2021/12/21 20:52
 *
 * @author: 薛定谔的猫猫虫
 * @message: 小组实体类--创建的每个小组
 */
@Data
@TableName("study_group")
public class Group {
    @TableId(value = "group_id",type = IdType.AUTO)
    private Integer id; // 小组编号

    private Integer leader; //组长id
    private String name; // 小组名称

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private String createTime; // 创建时间

    private String introduce; // 小组介绍

    @TableField(value = "team_head",fill = FieldFill.INSERT)
    private String teamHead; // 小组头像

}

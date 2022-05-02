package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * date: 2021/12/21 15:37
 *
 * @author: 薛定谔的猫猫虫
 * @message: 学生实体类
 */
@Data
@TableName("student")
public class Student {
    @TableId(value = "s_id",type=IdType.AUTO)
    private Integer id; //id

    @TableField("s_number")
    private String studentNumber; // 学生编号
    @TableField("email")
    private String email; // 邮箱

    private String password; // 密码

    @TableField(value = "user_name",fill = FieldFill.INSERT)
    private String userName; // 用户名

    private String sex; //性别

    @TableField(fill = FieldFill.INSERT)
    private Integer age; //年龄

    @TableField(value = "portrait_path",fill = FieldFill.INSERT)
    private String portraitPath; // 头像路径
}

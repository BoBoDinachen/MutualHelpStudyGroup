package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * date: 2021/12/21 21:02
 *
 * @author: 薛定谔的猫猫虫
 * @message: 学生小组实体类---每个小组的成员
 */
@Data
@TableName("student_group")
public class StudentGroup {
    @TableId(value = "member_id",type = IdType.AUTO)
    private Integer id; // 学生小组id

    @TableField("student_id")
    private Integer studentId; // 学生编号

    @TableField(exist = false)
    private Student student;

    @TableField("group_id")
    private Integer groupId; // 小组编号

    @TableField("is_leader")
    private Integer isLeader; // 是否是组长

    @TableField(value = "join_time",fill = FieldFill.INSERT)
    private String joinTime; // 加入小组时间


}

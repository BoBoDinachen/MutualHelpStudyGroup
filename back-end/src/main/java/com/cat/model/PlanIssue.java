package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * date: 2021/12/21 21:48
 *
 * @author: 薛定谔的猫猫虫
 * @message: 汇报情况实体类
 */
@Data
@TableName("plan_issue")
public class PlanIssue {
    @TableId(value = "report_id",type = IdType.AUTO)
    private Integer id; // 汇报情况编号

    private Integer studentId; // 学生id
    private Integer planId; // 计划id
    private Integer groupId; // 小组id

    @TableField(value = "report_time",fill = FieldFill.INSERT)
    private String reportTime; // 汇报时间

    private String issueExplain; // 说明
    private String issue; // 问题
    private String accessory; // 存储的附件路径
}

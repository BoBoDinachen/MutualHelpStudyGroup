package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("plan_audit")
public class PlanAudit {
    @TableId(value = "audit_id",type = IdType.AUTO)
    private Integer id; // 计划审核id

    @TableField("group_id")
    private Integer groupId;

    @TableField("student_id")
    private Integer studentId;

    @TableField("plan_id")
    private Integer planId;

    @TableField("pass_number")
    private Integer passNumber;
}

package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

/**
 * date: 2021/12/21 17:07
 *
 * @author: 薛定谔的猫猫虫
 * @message: 学习计划实体类
 */
@Data
@TableName(value = "plan")
public class Plan {
    @TableId(value = "plan_id",type = IdType.AUTO)
    private Integer id; // 计划编号

    @TableField("student_id")
    private Integer studentId;//学生id
    @TableField("group_id")
    private Integer groupId; // 小组id

    @TableField("plan_title")
    private String planTitle;// 计划标题
    private String target; // 计划目标

    @TableField(exist = false)
    private List<PlanTask> tasks;

    @TableField(value = "start_time",fill = FieldFill.INSERT)
    private String startTime; // 开始时间

    private String endTime; //结束时间
    @TableField("is_finish")
    private Integer isFinish; // 是否完成
}

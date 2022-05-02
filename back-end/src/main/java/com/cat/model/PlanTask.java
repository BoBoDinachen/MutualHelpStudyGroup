package com.cat.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "plan_task")
public class PlanTask {
    @TableId(value = "task_id",type = IdType.AUTO)
    private Integer id;
    @TableField("plan_id")
    private Integer planId;

    private String title;
    private Integer number;

    @TableField("is_finish2")
    private Integer isFinish2;
}

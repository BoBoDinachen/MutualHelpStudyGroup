package com.cat.service.planTask;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cat.mapper.PlanTaskMapper;
import com.cat.model.PlanTask;

/**
 * date: 2021/12/30 22:30
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface PlanTaskService {
    // 获取计划中的任务列表
    String getPlanTaskList(Integer planId);
    // 删除计划中的任务
    String deletePlanTask(Integer taskId);
    // 完成 / 取消完成任务
    String updatePlanTask(PlanTask planTask);
    // 新建计划任务
    String createPlanTask(PlanTask planTask);
}

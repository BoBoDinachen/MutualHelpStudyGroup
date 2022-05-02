package com.cat.controller;

import com.cat.model.PlanTask;
import com.cat.service.planTask.PlanTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/30 22:46
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/task")
public class PlanTaskController {
    PlanTaskServiceImpl planTaskService;
    @Autowired
    public void setPlanTaskService(PlanTaskServiceImpl planTaskService) {
        this.planTaskService = planTaskService;
    }

    /**
     * 获取计划的任务列表
     * @param planId
     * @return
     */
    @GetMapping("/list/{planId}")
    public String getPlanTaskList(@PathVariable Integer planId){
        return planTaskService.getPlanTaskList(planId);
    }

    /**
     * 删除计划任务
     * @param taskId
     * @return
     */
    @GetMapping("/delete/{taskId}")
    public String deletePlanTask(@PathVariable Integer taskId){
        return planTaskService.deletePlanTask(taskId);
    }

    /**
     * 更新任务的状态 --- 完成 | 取消完成
     * @param planTask
     * @return
     */
    @PostMapping("/update")
    public String updatePlanTask(@RequestBody PlanTask planTask){
        System.out.println(planTask);
        return planTaskService.updatePlanTask(planTask);
    }

    /**
     * 创建计划任务
     * @param planTask
     * @return
     */
    @PostMapping("/create")
    public String createPlanTask(@RequestBody PlanTask planTask){
        return planTaskService.createPlanTask(planTask);
    }
}

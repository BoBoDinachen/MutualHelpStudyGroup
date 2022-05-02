package com.cat.service.planTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cat.mapper.PlanTaskMapper;
import com.cat.model.PlanTask;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2021/12/30 22:31
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class PlanTaskServiceImpl implements PlanTaskService {
    PlanTaskMapper planTaskMapper;
    @Autowired
    public void setPlanTaskMapper(PlanTaskMapper planTaskMapper) {
        this.planTaskMapper = planTaskMapper;
    }

    /**
     * 获取计划中的任务列表
     * @param planId
     * @return
     */
    @Override
    public String getPlanTaskList(Integer planId) {
        QueryWrapper<PlanTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanTask::getPlanId,planId);
        List<PlanTask> planTasks = planTaskMapper.selectList(queryWrapper);
        if (planTasks.size() != 0){
            JSONObject data = new JSONObject();
            data.put("planTasks",planTasks);
            return ResultUtil.success("获取成功",data);
        }else {
            return ResultUtil.failed("未获取到");
        }
    }

    /***
     * 根据任务Id删除计划中的任务
     * @param taskId
     * @return
     */
    @Override
    public String deletePlanTask(Integer taskId) {
        int i = planTaskMapper.deleteById(taskId);
        if (i != 0){
            return ResultUtil.success("删除成功",null);
        }else {
            return ResultUtil.failed("删除失败");
        }
    }

    /**
     * 改变计划任务是否完成
     * @param planTask
     * @return
     */
    @Override
    public String updatePlanTask(PlanTask planTask) {
        int i = planTaskMapper.updateById(planTask);
        if (i != 0){
            return ResultUtil.success("修改成功",null);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 创建计划任务
     * @param planTask
     * @return
     */
    @Override
    public String createPlanTask(PlanTask planTask) {
        // 先找到这个计划的任务集合
        QueryWrapper<PlanTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanTask::getPlanId,planTask.getPlanId());
        List<PlanTask> planTasks = planTaskMapper.selectList(queryWrapper);
        int maxNumber = 0;
        if (planTasks.size() >= 2 && planTasks.size() < 7){
             for (PlanTask task:planTasks){
                 if (task.getNumber() > maxNumber){
                     maxNumber = task.getNumber();
                 }
             }
             planTask.setNumber(maxNumber+1);
             return this.createTask(planTask);
        }else if (planTasks.size() == 1){
            QueryWrapper<PlanTask> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(PlanTask::getPlanId,planTask.getPlanId());
            PlanTask planTask1 = planTaskMapper.selectOne(queryWrapper2);
//            System.out.println(planTask1.toString());
            planTask.setNumber(planTask1.getNumber()+1);
//            System.out.println(planTask.toString());
            return this.createTask(planTask);
        }else if (planTasks.size() >= 7){
            return ResultUtil.failed("子任务最多不能超过7个");
        }
        return this.createTask(planTask);
    }
    public String createTask(PlanTask planTask){
        int i = planTaskMapper.insert(planTask);
        if (i != 0){
            return ResultUtil.success("创建成功",null);
        }else {
            return ResultUtil.failed("创建失败");
        }
    }


}

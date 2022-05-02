package com.cat.controller;

import com.alibaba.fastjson.JSONObject;
import com.cat.model.Plan;
import com.cat.service.plan.PlanServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/30 18:42
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/plan")
@Slf4j
public class PlanController {
    PlanServiceImpl planService;
    @Autowired
    public void setPlanService(PlanServiceImpl planService) {
        this.planService = planService;
    }

    /**
     * 获取用户的所有计划
     * @param uid
     * @return
     */
    @GetMapping("/list/all/{uid}")
    public String getPlanList(@PathVariable Integer uid){
        String s = planService.getPlanList(uid);
        return s;
    }

    /**
     * 获取小组的计划列表
     * @param groupId
     * @return
     */
    @GetMapping("/list/{groupId}")
    public String getGroupPlanList(@PathVariable Integer groupId){
        String list = planService.getGroupPlanList(groupId);
        return list;
    }

    /**
     * 获取小组成员的计划列表
     * @param groupId
     * @param memberId
     * @return
     */
    @GetMapping("/list/{groupId}/{memberId}")
    public String getMemberPlanList(@PathVariable("groupId") Integer groupId,@PathVariable("memberId")Integer memberId){
        System.out.println(groupId+" "+memberId);
        String s = planService.getMemberPlanList(groupId, memberId);
        return s;
    }

    /**
     * 获取计划详情
     * @param planId
     * @return
     */
    @GetMapping("/info/{planId}")
    public String getPlanDetail(@PathVariable Integer planId){
        String s = planService.getPlanDetail(planId);
        return s;
    }

    /**
     * 删除计划
     * @param planId
     * @return
     */
    @GetMapping("/delete/{planId}")
    public String deletePlan(@PathVariable Integer planId){
        String s = planService.deletePlan(planId);
        return s;
    }

    /**
     * 更新计划
     * @return
     */
    @PostMapping("/update")
    public String updatePlan(@RequestBody JSONObject params){
        Integer planId = (Integer) params.get("planId");
        Integer isFinish = (Integer) params.get("isFinish");
        Plan plan = new Plan();
        plan.setId(planId);
        plan.setIsFinish(isFinish);
        return planService.updatePlan(plan);
    }

    /**
     * 创建计划
     * @param plan
     * @return
     */
    @PostMapping("/create")
    public String createPlan(@RequestBody Plan plan){
        return planService.createPlan(plan);
    }
}

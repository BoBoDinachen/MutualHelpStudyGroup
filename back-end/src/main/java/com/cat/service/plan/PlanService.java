package com.cat.service.plan;

import com.cat.model.Plan;

/**
 * date: 2021/12/21 17:02
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface PlanService {
    // 根据用户Id获取计划列表
    String getPlanList(Integer uid);
    // 根据小组Id获取计划列表
    String getGroupPlanList(Integer groupId);
    // 获取小组成员的计划列表
    String getMemberPlanList(Integer groupId,Integer memberId);
    // 根据计划Id获取计划详情
    String getPlanDetail(Integer planId);
    // 根据计划id删除计划
    String deletePlan(Integer planId);
    // 完成 | 取消完成 计划
    String updatePlan(Plan plan);
    // 新建计划
    String createPlan(Plan plan);
}

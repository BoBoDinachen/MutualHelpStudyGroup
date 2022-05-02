package com.cat.controller;

import com.cat.model.PlanAudit;
import com.cat.service.planAudit.PlanAuditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/30 21:37
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/plan")
public class PlanAuditController {
    PlanAuditServiceImpl planAuditService;
    @Autowired
    public void setPlanAuditService(PlanAuditServiceImpl planAuditService) {
        this.planAuditService = planAuditService;
    }

    /**
     * 根据小组id获取小组对应的计划审核列表
     * @param groupId
     * @return
     */
    @GetMapping("/audit/list/{groupId}")
    public String getGroupPlanAuditList(@PathVariable Integer groupId){
        return planAuditService.getGroupPlanAuditList(groupId);
    }

    /**
     * 提交计划审核
     * @param planAudit
     * @return
     */
    @PostMapping("/submit")
    public String submitPlanAudit(@RequestBody PlanAudit planAudit){
        return planAuditService.submitPlanAudit(planAudit);
    }

    /**
     * 查询计划是否已提交审核
     * @param planId
     * @return
     */
    @GetMapping("/audit/find/{planId}")
    public String selectPlanAudit(@PathVariable("planId")Integer planId){
        return planAuditService.selectPlanAudit(planId);
    }

    /**
     * 计划通过审核
     * @param planId
     * @return
     */
    @GetMapping("/audit/{planId}")
    public String planAuditPass(@PathVariable Integer planId){
        return planAuditService.updatePlanAudit(planId);
    }

    /**
     * 计划通过审核
     * @param planId
     * @return
     */
    @GetMapping("/audit/number/{planId}")
    public String getPlanAuditNumber(@PathVariable Integer planId){
        return planAuditService.getPlanAuditNumber(planId);
    }
}

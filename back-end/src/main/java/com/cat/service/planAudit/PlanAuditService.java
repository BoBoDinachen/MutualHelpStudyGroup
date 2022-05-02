package com.cat.service.planAudit;

import com.cat.model.PlanAudit;

/**
 * date: 2021/12/21 22:23
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface PlanAuditService {
    // 获取小组对应的计划审核列表
    String getGroupPlanAuditList(Integer groupId);
    // 提交计划审核
    String submitPlanAudit(PlanAudit planAudit);
    // 审核通过
    String updatePlanAudit(Integer auditId);
    // 查询计划是否已提交审核
    String selectPlanAudit(Integer planId);
    // 获取对应计划的审核次数
    String getPlanAuditNumber(Integer planId);
}

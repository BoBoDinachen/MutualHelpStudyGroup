package com.cat.service.condition;

import com.cat.model.PlanIssue;

/**
 * date: 2021/12/21 22:23
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface ConditionService {
    //获取问题列表
    String getConditionList(Integer groupId);
    // 汇报问题
    String reportCondition(PlanIssue planIssue);
}

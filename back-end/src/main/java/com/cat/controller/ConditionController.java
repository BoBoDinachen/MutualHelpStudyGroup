package com.cat.controller;

import com.cat.model.PlanIssue;
import com.cat.service.condition.ConditionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/31 9:18
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ConditionController {
    ConditionServiceImpl conditionService;
    @Autowired
    public void setConditionService(ConditionServiceImpl conditionService) {
        this.conditionService = conditionService;
    }

    /**
     * 获取问题列表
     * @param groupId
     * @return
     */
    @GetMapping("/list/{groupId}")
    public String getConditionList(@PathVariable Integer groupId){
        String s = conditionService.getConditionList(groupId);
        return s;
    }

    /**
     * 汇报问题
     * @return
     */
    @PostMapping("/report")
    public String reportCondition(@RequestBody PlanIssue planIssue){
        return conditionService.reportCondition(planIssue);
    }
}

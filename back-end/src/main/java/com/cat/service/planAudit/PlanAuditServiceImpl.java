package com.cat.service.planAudit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cat.mapper.PlanAuditMapper;
import com.cat.model.PlanAudit;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2021/12/21 22:24
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class PlanAuditServiceImpl implements PlanAuditService {
    PlanAuditMapper planAuditMapper;
    @Autowired
    public void setPlanAuditMapper(PlanAuditMapper planAuditMapper) {
        this.planAuditMapper = planAuditMapper;
    }

    /**
     * 获取小组的计划审核列表
     * @return
     */
    @Override
    public String getGroupPlanAuditList(Integer groupId) {
        QueryWrapper<PlanAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanAudit::getGroupId,groupId);
        List<PlanAudit> planAudits = planAuditMapper.selectList(queryWrapper);
        if (planAudits.size() != 0){
            JSONObject data = new JSONObject();
            data.put("planAudits",planAudits);
            return ResultUtil.success("获取成功",data);
        }else {
            return ResultUtil.failed("获取失败");
        }
    }

    /**
     * 提交计划审核
     * @param planAudit
     * @return
     */
    @Override
    public String submitPlanAudit(PlanAudit planAudit) {
        int i = planAuditMapper.insert(planAudit);
        if (i != 0){
            return ResultUtil.success("提交成功",null);
        }else {
            return ResultUtil.failed("创建失败");
        }
    }

    /**
     * 查询计划是否已提交审核
     * @param planId
     * @return
     */
    @Override
    public String selectPlanAudit(Integer planId) {
        QueryWrapper<PlanAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanAudit::getPlanId,planId);
        PlanAudit planAudit = planAuditMapper.selectOne(queryWrapper);
        if (planAudit != null){
            return ResultUtil.success("该计划已提交审核",planAudit.getId());
        }else {
            return ResultUtil.failed("该计划未提交");
        }
    }

    /**
     * 通过计划审核
     * @param planId
     * @return
     */
    @Override
    public String updatePlanAudit(Integer planId) {
        // 先查询计划的审核数量
        QueryWrapper<PlanAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanAudit::getPlanId,planId);
        PlanAudit planAudit = planAuditMapper.selectOne(queryWrapper);
        planAudit.setPassNumber(planAudit.getPassNumber()+1); // 每次让审核数量+1
        int i = planAuditMapper.updateById(planAudit);
        if (i != 0){
            JSONObject data = new JSONObject();
            data.put("currentPassNumber",planAudit.getPassNumber());
            return ResultUtil.success("审核成功",data);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 获取对应计划的审核次数
     * @param planId
     * @return
     */
    @Override
    public String getPlanAuditNumber(Integer planId) {
        QueryWrapper<PlanAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanAudit::getPlanId,planId);
        PlanAudit one = planAuditMapper.selectOne(queryWrapper);
        if (one != null){
            return ResultUtil.success("查询成功",one.getPassNumber());
        }else {
            return ResultUtil.failed("查询为空");
        }
    }
}

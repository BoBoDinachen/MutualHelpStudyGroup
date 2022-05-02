package com.cat.service.plan;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cat.mapper.PlanMapper;
import com.cat.mapper.PlanTaskMapper;
import com.cat.model.Plan;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2021/12/21 17:02
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class PlanServiceImpl  implements PlanService{
    PlanMapper planMapper;
    PlanTaskMapper planTaskMapper;
    @Autowired
    public void setPlanMapper(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }
    @Autowired
    public void setPlanTaskMapper(PlanTaskMapper planTaskMapper) {
        this.planTaskMapper = planTaskMapper;
    }

    /**
     * 获取用户全部计划
     * @param uid
     * @return
     */
    @Override
    public String getPlanList(Integer uid) {
        List<Plan> plans = planMapper.getPlanListByStudentId(uid);
        if (plans.size() != 0){
            JSONObject data = new JSONObject();
            data.put("planList",plans);
            return ResultUtil.success("查询所有计划成功",data);
        }else {
            return ResultUtil.failed("查询失败");
        }
    }

    /**
     * 获取小组内的全部计划
     * @param groupId
     * @return
     */
    @Override
    public String getGroupPlanList(Integer groupId) {
        List<Plan> plans = planMapper.getPlanListByGroupId(groupId);
        if (plans.size() != 0){
            JSONObject data = new JSONObject();
            data.put("planList",plans);
            return ResultUtil.success("查询小组计划列表成功",data);
        }else {
            return ResultUtil.failed("查询失败");
        }
    }

    /**
     * 查询小组内成员的计划列表
     * @param groupId
     * @param memberId
     * @return
     */
    @Override
    public String getMemberPlanList(Integer groupId, Integer memberId) {
        List<Plan> plans = planMapper.getPlanListByMember(groupId,memberId);
        for (Plan plan:plans){
            System.out.println(plan.toString());
        }
        if (plans.size() != 0){
            JSONObject data = new JSONObject();
            data.put("planList",plans);
            return ResultUtil.success("查询成员计划列表成功",data);
        }else {
            return ResultUtil.failed("查询为空");
        }
    }

    /**
     * 获取计划详情
     * @param planId
     * @return
     */
    @Override
    public String getPlanDetail(Integer planId) {
        Plan plan = planMapper.getPlanDetailById(planId);
        if (plan == null){
            return ResultUtil.failed("没有获取到");
        }else {
            JSONObject data = new JSONObject();
            data.put("plan",plan);
            return ResultUtil.success("获取成功",data);
        }
    }

    /**
     * 根据计划Id删除计划
     * @param planId
     * @return
     */
    @Override
    public String deletePlan(Integer planId) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Plan::getId,planId);
        int i = planMapper.delete(queryWrapper);
        if (i != 0){
            // 删除成功
            return ResultUtil.success("删除成功",null);
        }else {
            return ResultUtil.failed("删除失败");
        }
    }

    /**
     * 更新计划 ---- 完成 | 取消完成
     * @param plan
     * @return
     */
    @Override
    public String updatePlan(Plan plan) {
        int i = planMapper.updateById(plan);
        if (i != 0 ){
            return ResultUtil.success("修改成功",null);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 创建计划
     * @param plan
     * @return
     */
    @Override
    public String createPlan(Plan plan) {
        int i = planMapper.insert(plan);
        if (i != 0){
            JSONObject data = new JSONObject();
            data.put("planId",plan.getId());
            return ResultUtil.success("创建成功",data);
        }else {
            return ResultUtil.failed("创建失败");
        }
    }
}

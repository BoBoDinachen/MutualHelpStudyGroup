package com.cat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cat.model.Plan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * date: 2021/12/21 18:54
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Repository
public interface PlanMapper extends BaseMapper<Plan> {
    // 根据用户Id获取计划列表
    List<Plan> getPlanListByStudentId(Integer uid);
    // 根据小组Id获取计划列表
    List<Plan> getPlanListByGroupId(Integer groupId);
    // 获取小组成员的计划列表
    List<Plan> getPlanListByMember(@Param("groupId") Integer groupId, @Param("memberId") Integer memberId);
    // 根据计划Id获取计划详情
    Plan getPlanDetailById(Integer planId);
}

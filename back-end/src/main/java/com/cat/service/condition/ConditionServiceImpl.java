package com.cat.service.condition;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cat.mapper.ConditionMapper;
import com.cat.model.PlanIssue;
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
public class ConditionServiceImpl implements ConditionService {
    ConditionMapper conditionMapper;
    @Autowired
    public void setConditionMapper(ConditionMapper conditionMapper) {
        this.conditionMapper = conditionMapper;
    }

    /**
     * 获取问题列表
     * @param groupId
     * @return
     */
    @Override
    public String getConditionList(Integer groupId) {
        QueryWrapper<PlanIssue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlanIssue::getGroupId,groupId);
        List<PlanIssue> list = conditionMapper.selectList(queryWrapper);
        if (list.size() != 0){
            JSONObject data = new JSONObject();
            data.put("conditionList",list);
            return ResultUtil.success("获取问题列表成功",data);
        }else {
            return ResultUtil.failed("获取为空");
        }
    }

    /**
     * 汇报问题
     * @param planIssue
     * @return
     */
    @Override
    public String reportCondition(PlanIssue planIssue) {
        int i = conditionMapper.insert(planIssue);
        if (i != 0){
            return ResultUtil.success("汇报成功",null);
        }else {
            return ResultUtil.failed("汇报失败");
        }
    }
}

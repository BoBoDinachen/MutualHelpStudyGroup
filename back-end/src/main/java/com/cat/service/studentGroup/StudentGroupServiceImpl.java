package com.cat.service.studentGroup;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cat.mapper.GroupMapper;
import com.cat.mapper.StudentGroupMapper;
import com.cat.model.Group;
import com.cat.model.StudentGroup;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2021/12/21 16:39
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class StudentGroupServiceImpl implements StudentGroupService {
    StudentGroupMapper studentGroupMapper;
    GroupMapper groupMapper;
    @Autowired
    public void setStudentGroupMapper(StudentGroupMapper studentGroupMapper) {
        this.studentGroupMapper = studentGroupMapper;
    }
    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    /**
     * 获取小组的成员列表
     * @param groupId
     * @return
     */
    @Override
    public String getGroupMemberList(Integer groupId) {
        List<StudentGroup> memberList = studentGroupMapper.getGroupMemberList(groupId);
        if (memberList.size() != 0){
            JSONObject data = new JSONObject();
            data.put("memberList",memberList);
            return ResultUtil.success("获取成功",data);
        }else {
            return  ResultUtil.failed("获取为空");
        }
    }

    /**
     * 判断成员是否存在
     * @param groupId
     * @param studentId
     * @return
     */
    @Override
    public String isMemberExist(Integer groupId, Integer studentId) {
        List<StudentGroup> memberList = studentGroupMapper.getGroupMemberList(groupId);
        for (int i = 0; i < memberList.size(); i++) {
            StudentGroup member = memberList.get(i);
            if (member.getStudentId() == studentId){
                return ResultUtil.success("该成员已存在",null);
            }
        }
        return ResultUtil.failed("该成员不存在");
    }

    /**
     * 组长移出成员
     * @param leaderId
     * @param memberId
     * @param groupId
     * @return
     */
    @Override
    public String removeMember(Integer leaderId,Integer memberId, Integer groupId) {
        // 先判断leaderId
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Group::getId,groupId);
        queryWrapper.lambda().eq(Group::getLeader,leaderId);
        Group one = groupMapper.selectOne(queryWrapper);
        if (one != null){
            // 如果是组长，则执行
            QueryWrapper<StudentGroup> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(StudentGroup::getStudentId,memberId);
            queryWrapper2.lambda().eq(StudentGroup::getGroupId,groupId);
            int i = studentGroupMapper.delete(queryWrapper2);
            if (i != 0){
                return ResultUtil.success("移出成功",null);
            }else {
                return ResultUtil.failed("移出失败");
            }
        }else {
            // 如果不是，没有权限
            return ResultUtil.failed("不是组长,没有权限");
        }
    }

    /**
     * 成员退出小组
     * @param groupId
     * @param memberId
     * @return
     */
    @Override
    public String quitGroup(Integer groupId, Integer memberId) {
        // 先判断组员是否是组长
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Group::getLeader,memberId);
        queryWrapper.lambda().eq(Group::getId,groupId);
        Group one = groupMapper.selectOne(queryWrapper);
        if (one != null){
            // 如果是组长，则不能退出
            return ResultUtil.failed("组长不能退出小组");
        }else {
            QueryWrapper<StudentGroup> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(StudentGroup::getStudentId,memberId);
            queryWrapper2.lambda().eq(StudentGroup::getGroupId,groupId);
            int i = studentGroupMapper.delete(queryWrapper2);
            if (i != 0){
                return ResultUtil.success("退出成功",null);
            }else {
                return ResultUtil.failed("退出失败");
            }
        }
    }

    /**
     * 学生加入小组
     * @param studentId
     * @param groupId
     * @return
     */
    @Override
    public String joinGroup(Integer studentId, Integer groupId) {
        // 先判断小组里是否已存在
        QueryWrapper<StudentGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentGroup::getGroupId,groupId);
        queryWrapper.lambda().eq(StudentGroup::getStudentId,studentId);
        StudentGroup one = studentGroupMapper.selectOne(queryWrapper);
        if (one != null){
            // 如果存在,则不能加入
            return ResultUtil.failed("已存在，不能重复加入");
        }else {
            // 不存在，加入
            StudentGroup studentGroup = new StudentGroup();
            studentGroup.setGroupId(groupId);
            studentGroup.setStudentId(studentId);
            studentGroup.setIsLeader(0);
            int i = studentGroupMapper.insert(studentGroup);
            if (i != 0){
                return ResultUtil.success("加入成功",null);
            }else {
                return ResultUtil.failed("加入失败");
            }
        }
    }
}

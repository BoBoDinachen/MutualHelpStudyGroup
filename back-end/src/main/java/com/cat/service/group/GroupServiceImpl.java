package com.cat.service.group;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cat.mapper.GroupMapper;
import com.cat.mapper.StudentGroupMapper;
import com.cat.model.Group;
import com.cat.model.StudentGroup;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * date: 2021/12/21 22:24
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class GroupServiceImpl implements GroupService {
    GroupMapper groupMapper;
    StudentGroupMapper studentGroupMapper;
    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }
    @Autowired
    public void setStudentGroupMapper(StudentGroupMapper studentGroupMapper) {
        this.studentGroupMapper = studentGroupMapper;
    }

    /**
     * 获取小组列表
     * @param uid
     * @return
     */
    @Override
    public String getGroupList(Integer uid) {
        QueryWrapper<StudentGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentGroup::getStudentId,uid);
        List<StudentGroup> studentGroups = studentGroupMapper.selectList(queryWrapper);
        List<Group> groups = new ArrayList<>();
        for (StudentGroup studentGroup:studentGroups){
            Group group = groupMapper.selectById(studentGroup.getGroupId());
            groups.add(group);
        }
        if (groups.size() != 0){
            JSONObject data = new JSONObject();
            data.put("groups",groups);
            return ResultUtil.success("查询小组列表成功---按用户名",data);
        }else {
            return ResultUtil.failed("查询为空");
        }
    }

    /**
     * 按小组名字进行查询列表
     * @param name
     * @return
     */
    @Override
    public String getGroupListByName(String name) {
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        if (name.equals("all")){
            queryWrapper.lambda().like(Group::getName,"");
        }else {
            queryWrapper.lambda().like(Group::getName,name);
        }
        List<Group> groups = groupMapper.selectList(queryWrapper);
        if (groups.size() != 0){
            JSONObject data = new JSONObject();
            data.put("groups",groups);
            return ResultUtil.success("查询小组列表成功--按名字",data);
        }else {
            return ResultUtil.failed("查询为空");
        }
    }

    /**
     * 获取小组信息
     * @param groupId
     * @return
     */
    public String getGroupInfo(Integer groupId){
        Group group = groupMapper.selectById(groupId);
        if (group == null){
            return ResultUtil.failed("查找为空");
        }else {
            JSONObject data = new JSONObject();
            data.put("group",group);
            return ResultUtil.success("查找成功",data);
        }
    }

    /**
     * 获取小组头像路径
     * @param groupId
     * @return
     */
    @Override
    public String getGroupHeadPath(Integer groupId) {
        Group group = groupMapper.selectById(groupId);
        return group.getTeamHead();
    }

    /**
     * 修改小组信息
     * @param group
     * @return
     */
    @Override
    public String updateGroupInfo(Group group) {
        int i = groupMapper.updateById(group);
        if (i != 0){
            return ResultUtil.success("修改信息成功",null);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 修改小组头像地址
     * @param groupId
     * @param path
     * @return
     */
    @Override
    public String changeTeamHeadPath(Integer groupId, String path) {
        Group group = new Group();
        group.setId(groupId);
        group.setTeamHead(path);
        int i = groupMapper.updateById(group);
        if (i != 0){
            return ResultUtil.success("修改成功",null);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 创建小组
     * @param group
     * @return
     */
    @Override
    public String createGroup(Group group) {
        // 先查询是否存在相同小组名
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Group::getName,group.getName());
        Group selectOne = groupMapper.selectOne(queryWrapper);
        if (selectOne != null){
            return ResultUtil.failed("小组已存在");
        }else {
            // 插入小组表
            int i = groupMapper.insert(group);
            StudentGroup studentGroup = new StudentGroup();
            studentGroup.setStudentId(group.getLeader());
            studentGroup.setGroupId(group.getId());
            studentGroup.setIsLeader(1);
            // 插入学生小组表
            int j = studentGroupMapper.insert(studentGroup);
            if (i != 0 && j != 0 ){
                return ResultUtil.success("创建成功",null);
            }else {
                return ResultUtil.failed("创建失败");
            }
        }
    }

    /**
     * 解散小组
     * @param uid
     * @param groupId
     * @return
     */
    @Override
    public String dissolveGroup(Integer uid, Integer groupId) {
        // 删除对应的小组
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Group::getLeader,uid);
        queryWrapper.lambda().eq(Group::getId,groupId);
        int i = groupMapper.delete(queryWrapper);
        if (i != 0){
            return ResultUtil.success("解散成功",null);
        }else {
            return ResultUtil.failed("解散失败");
        }
    }
}

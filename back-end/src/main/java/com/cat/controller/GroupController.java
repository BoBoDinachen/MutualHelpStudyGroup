package com.cat.controller;

import com.alibaba.fastjson.JSONObject;
import com.cat.model.Group;
import com.cat.service.group.GroupServiceImpl;
import com.cat.service.studentGroup.StudentGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/30 10:50
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/group")
public class GroupController {
    GroupServiceImpl groupService;
    StudentGroupServiceImpl studentGroupService;
    @Autowired
    public void setGroupService(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }
    @Autowired
    public void setStudentGroupService(StudentGroupServiceImpl studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    /**
     * 查询小组列表
     * @param uid
     * @return
     */
    @GetMapping("/list/{uid}")
    public String getList(@PathVariable Integer uid){
        String s = groupService.getGroupList(uid);
        return s;
    }
    /**
     * 查询小组列表-- 按名字
     * @param groupName
     * @return
     */
    @GetMapping("/list/name")
    public String getListByName(@RequestParam("groupName") String groupName){
        String s = groupService.getGroupListByName(groupName);
        return s;
    }

    /**
     * 查询小组内是否存在某个成员
     * @param groupId
     * @param studentId
     * @return
     */
    @GetMapping("/findMember/{groupId}/{studentId}")
    public String findMemberById(@PathVariable Integer groupId,@PathVariable Integer studentId){
        String s = studentGroupService.isMemberExist(groupId, studentId);
        return s;
    }
    /**
     * 获取对应小组信息
     * @param groupId
     * @return
     */
    @GetMapping("/info/{groupId}")
    public String getInfo(@PathVariable Integer groupId){
        String info = groupService.getGroupInfo(groupId);
        return info;
    }

    /**
     * 修改小组信息
     * @param group
     * @return
     */
    @PostMapping("/update")
    public String update(@RequestBody Group group){
        return groupService.updateGroupInfo(group);
    }

    /**
     * 创建小组
     * @param group
     * @return
     */
    @PostMapping("/create")
    public String create(@RequestBody Group group){
        String s = groupService.createGroup(group);
        return s;
    }

    /**
     * 解散小组
     * @param params
     * @return
     */
    @PostMapping("/dissolve")
    public String dissolve(@RequestBody JSONObject params){
        Integer uid = (Integer) params.get("uid");
        Integer groupId = (Integer) params.get("groupId");
        String s = groupService.dissolveGroup(uid, groupId);
        return s;
    }


}

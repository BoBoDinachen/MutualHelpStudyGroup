package com.cat.controller;

import com.alibaba.fastjson.JSONObject;
import com.cat.service.studentGroup.StudentGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/30 14:09
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/member")
public class GroupMemberController {
    StudentGroupServiceImpl studentGroupService;
    @Autowired
    public void setStudentGroupService(StudentGroupServiceImpl studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    /**
     * 查询成员列表
     * @param groupId
     * @return
     */
    @GetMapping("/list/{groupId}")
    public String getList(@PathVariable Integer groupId){
        String list = studentGroupService.getGroupMemberList(groupId);
        return list;
    }

    /**
     * 移出成员
     * @param params
     * @return
     */
    @PostMapping("/remove")
    public String removeMember(@RequestBody JSONObject params){
        Integer leaderId = (Integer) params.get("leaderId");
        Integer groupId = (Integer) params.get("groupId");
        Integer memberId = (Integer)params.get("memberId");
        String s = studentGroupService.removeMember(leaderId, memberId, groupId);
        return s;
    }

    /**
     * 成员退出小组
     * @param params
     * @return
     */
    @PostMapping("/quit")
    public String quitGroup(@RequestBody JSONObject params){
        Integer groupId = (Integer) params.get("groupId");
        Integer memberId = (Integer) params.get("memberId");
        String s = studentGroupService.quitGroup(groupId, memberId);
        return s;
    }

    /**
     * 学生加入小组
     * @param params
     * @return
     */
    @PostMapping("/join")
    public String joinGroup(@RequestBody JSONObject params){
        Integer groupId = (Integer)params.get("groupId");
        Integer studentId = (Integer) params.get("studentId");
        return studentGroupService.joinGroup(studentId,groupId);
    }
}

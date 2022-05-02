package com.cat.service.studentGroup;

/**
 * date: 2021/12/21 17:03
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface StudentGroupService {
    // 获取小组的成员列表
    String getGroupMemberList(Integer groupId);
    // 判断成员是否存在
    String isMemberExist(Integer groupId,Integer studentId);
    // 组长移出成员
    String removeMember(Integer leaderId, Integer memberId,Integer groupId);
    // 成员退出小组
    String quitGroup(Integer groupId,Integer memberId);
    // 加入小组
    String joinGroup(Integer studentId,Integer groupId);
}

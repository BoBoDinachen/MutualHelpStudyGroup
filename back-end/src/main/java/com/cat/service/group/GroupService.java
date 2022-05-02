package com.cat.service.group;

import com.cat.model.Group;

/**
 * date: 2021/12/21 22:23
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface GroupService {
    // 获取小组列表 --- 用户id
    String getGroupList(Integer uid);
    // 获取小组列表 --- 小组名字
    String getGroupListByName(String name);
    // 获取小组信息
    String getGroupInfo(Integer groupInfo);
    // 获取小组头像路径
    String getGroupHeadPath(Integer groupId);
    // 创建小组
    String createGroup(Group group);
    // 修改小组信息
    String updateGroupInfo(Group group);
    // 修改小组头像地址
    String changeTeamHeadPath(Integer groupId,String path);
    // 解散小组
    String dissolveGroup(Integer uid,Integer groupId);
}

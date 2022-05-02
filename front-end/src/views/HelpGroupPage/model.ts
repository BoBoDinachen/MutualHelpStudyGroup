import { ref, reactive } from 'vue'
import { Group_Type, Member_Type, Student_Type } from '@/types/index'
import { getGroupList } from '@/services/api/group'
import { getMemberList } from '@/services/api/member'
import { getUserInfo } from '@/services/api/user'
import { userInfoStorage } from '@/utils/storageUtil'
const userInfo = userInfoStorage.value as Student_Type;

/**
 * 处理小组数据的hooks
 * @param uid 用户id
 * @returns 小组列表
 */
function useGroupList(uid?: number) {
  // 小组列表
  const state = reactive({
    groupList: [] as Group_Type[], // 小组列表
    activeGroupInfo: {} as Group_Type
  });
  initState(); // 初始化状态
  // 选中当前小组
  const changeActiveGroup = (id: string) => {
    // 将之前的状态都设置为false
    state.groupList.forEach(item => {
      item.isActive = false;
    })
    // 将对应id的组状态设置为true
    state.groupList.forEach((item, index) => {
      if (id === item.id) {
        state.activeGroupInfo = state.groupList[index];
        item.isActive = true;
      }
    })
  }
  // 修改当前的小组信息
  const updateActiveGroupInfo = (groupId: string, groupName: string, groupIntroduce: string) => {
    state.groupList.forEach((item, index) => {
      if (groupId === item.id) {
        item.name = groupName;
        item.introduce = groupIntroduce
      }
    })
  }
  // 初始化状态
  function initState() {
    state.groupList = []; // 先清空之前的状态
    // 初始化小组列表
    getGroupList(userInfo.id).then((res: any) => {
      console.log(res);
      if (res.data) {
        res.data.groups.forEach(async (item: any, index: number) => {
          // 获取当前组长的信息
          let leaderInfo: any = await getUserInfo(item.leader);
          item['leaderName'] = leaderInfo.data.student.userName;
          item['isActive'] = false;
          // 获取成员的数量
          let memberListData: any = await getMemberList(item.id)
          if (memberListData.data) {
            item['memberNumber'] = memberListData.data.memberList.length;
          }
          state.groupList.push(item);
          state.groupList[0].isActive = true;
          state.activeGroupInfo = state.groupList[0];
        });
      }
    })
  }
  return {
    state,
    changeActiveGroup,
    updateActiveGroupInfo,
    initState
  }
}


export {
  useGroupList
}
import { get, post } from '../index'

/**
 * 获取成员列表
 * @param groupId 小组id
 * @returns 
 */
const getMemberList = (groupId:string) => { 
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/member/list/" + groupId);
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
/**
 * 学生加入小组
 * @param studentId 学生id
 * @param groupId 小组id
 */
const joinGroup = (studentId:number,groupId:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/member/join", {studentId,groupId});
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}

/**
 * 成员退出小组
 * @param memberId 成员id
 * @param groupId 小组id
 */
const quitGroup = (memberId:number,groupId:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/member/quit", {memberId,groupId});
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
/**
 * 移出成员
 * @param leaderId 组长id
 * @param groupId 小组id
 * @param memberId 成员id
 */
const removeMember = (leaderId:number,groupId:string,memberId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/member/remove", {leaderId,groupId,memberId});
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
// 导出api
export {
  getMemberList,
  joinGroup,
  quitGroup,
  removeMember
}
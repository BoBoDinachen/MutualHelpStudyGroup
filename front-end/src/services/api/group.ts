import { get, post } from '../index'

/**
 * 获取小组列表
 * @param uid 用户id
 * @returns 
 */
const getGroupList = (uid:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/group/list/" + uid);
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
/**
 * 根据小组名字获取小组列表
 * @param groupName 小组名字
 * @returns 
 */
const getGroupListByName = (groupName:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/group/list/name", {groupName});
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
/**
 * 查找小组内是否存在某一成员
 * @param groupId 
 * @param studentId 
 */
const findMemberFormGroup = (groupId:number,studentId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/group/findMember/"+groupId+"/"+studentId);
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
/**
 * 修改小组信息
 * @param id 小组id
 * @param name 小组名字
 * @param introduce 小组介绍
 */
const updateGroupInfo = (id:number,name:string,introduce:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/group/update", { id, name, introduce });
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}

/**
 * 解散小组
 * @param uid 组长id
 * @param groupId 小组id
 */
const dissolveGroup = (uid:number,groupId:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/group/dissolve", { uid, groupId });
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 创建小组
 * @param leader 组长id
 * @param name 小组名字
 * @param introduce 小组介绍
 */
const createGroup = (leader:number,name:string,introduce:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/group/create", { leader, name,introduce });
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 上传小组头像
 * @param params 用户id属性和文件数据
 */
 const uploadAvatar = (params: FormData) => {
  return new Promise(async(resolve, reject) => {
    try {
      const res = await post("/upload/teamHead", params, { headers: { "Content-Type": "multipart/form-data" } });
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
// 导出
export {
  getGroupList,
  getGroupListByName,
  findMemberFormGroup,
  updateGroupInfo,
  dissolveGroup,
  createGroup,
  uploadAvatar
}
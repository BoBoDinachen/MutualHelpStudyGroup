import { get, post } from '../index'

/**
 * 获取便签列表
 * @param uid 用户id
 * @returns 
 */
const getStickyList = (uid:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/stick/list/"+uid);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 删除便签
 * @param stickId 便签id
 * @returns 
 */
const deleteStick = (stickId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/stick/delete/"+stickId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 创建便签
 * @param studentId 学生id
 * @param title 标题
 * @returns 
 */
const createStick = (studentId:number,title:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/stick/create", {studentId,title});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 修改便签
 * @param stickId 便签id
 * @param title 标题
 * @returns 
 */
const updateStick = (stickId:number,title:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/stick/update", {stickId,title});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}

export {
  getStickyList,
  createStick,
  updateStick,
  deleteStick
}
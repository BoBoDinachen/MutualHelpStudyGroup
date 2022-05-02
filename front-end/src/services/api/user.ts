import { AxiosPromise } from 'axios'
import { get, post } from '../index'

/**
 * 用户登录
 * @param email 
 * @param password 
 */
const userLogin = (params: { email: string, password: string }): AxiosPromise => {
  return new Promise((resolve, reject) => {
    post("/user/login", params).then((res: any) => {
      resolve(res);
    }, err => { reject(err) });
  })
}
/**
 * 用户注册
 * @param params 邮箱和密码
 */
const userRegister = (params: { email: string, password: string }) => {
  return new Promise((resolve, reject) => {
    post("/user/register", params).then((res: any) => {
      resolve(res);
    }, err => { reject(err) });
  });
}
/**
 * 修改用户信息
 * @param params 
 * @returns 
 */
const updateUserInfo = (params: {
  "userName"?: string
  "sex"?: string,
  "age"?: number,
  "password"?: string,
  "studentNumber"?: string,
  "id": number
}) => {
  return new Promise((resolve, reject) => {
    post("/user/update", params).then((res: any) => {
      resolve(res);
    }, err => { reject(err) });
  });
}
/**
 * 获取用户信息
 * @param uid 用户id
 */
const getUserInfo = (uid: string) => {
  return new Promise(async (resolve, reject) => {
    const res: any = await get("/user/info/" + uid, {});
    resolve(res);
  })
}
/**
 * 上传用户头像
 * @param params 用户id属性和文件数据
 */
const uploadAvatar = (params: FormData) => {
  return new Promise(async(resolve, reject) => {
    try {
      const res = await post("/upload/avatar", params, { headers: { "Content-Type": "multipart/form-data" } });
      resolve(res);
    } catch (error) {
      reject(error);
    }
  })
}
export {
  userLogin,
  userRegister,
  updateUserInfo,
  getUserInfo,
  uploadAvatar
}
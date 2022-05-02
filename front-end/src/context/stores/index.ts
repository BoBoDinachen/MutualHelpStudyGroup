import { defineStore } from 'pinia'
import {Student_Type} from '@/types/index'
/**
 * token和用户信息的存储
 */
export const userLoginInfoStore = defineStore("loginInfo", {
  state: () => {
    return {
      token: "",
      userInfo: {} as Student_Type
    }
  },
  actions: {
    // 修改用户信息
    updateUserInfo(user: Student_Type) {
      this.$state.userInfo = user;
    }
  },
})
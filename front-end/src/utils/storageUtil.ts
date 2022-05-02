import { useStorage } from '@vueuse/core'

const tokenStorage = useStorage(
  'token',
  {},
  sessionStorage,
  {
    serializer: {
      read: (v: any) => v ? JSON.parse(v) : null,
      write: (v: any) => JSON.stringify(v),
    }
  }
)
const userInfoStorage = useStorage(
  'userInfo',
  {},
  sessionStorage,
  {
    serializer: {
      read: (v: any) => v ? JSON.parse(v) : null,
      write: (v: any) => JSON.stringify(v),
    }
  }
)
const loginInfoStorage = useStorage(
  '互助学习App-loginInfo',
  {},
  localStorage,
  {
    serializer: {
      read: (v: any) => v ? JSON.parse(v) : null,
      write: (v: any) => JSON.stringify(v),
    }
  }
)
export {
  tokenStorage,
  userInfoStorage,
  loginInfoStorage
}
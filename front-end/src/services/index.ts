import axios from 'axios';
import { tokenStorage } from '@/utils/storageUtil'
import { useToast } from '@/components/index'
const toast = useToast();

// 环境的切换
if (import.meta.env.DEV) {
  axios.defaults.baseURL = '/api';
} else if (import.meta.env.PROD) {
  axios.defaults.baseURL = 'http://localhost:8081';
}

// 请求超时时间
axios.defaults.timeout = 5000;

// post请求头
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
// 请求拦截器
axios.interceptors.request.use(
  (config: any) => {
    // 每次发送请求之前判断是否存在token，如果存在，则统一在http请求的header都加上token，不用每次请求都手动添加了
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
    const token = tokenStorage.value;
    if (token) {
      config.headers['token'] = token
    }
    return config
  },
  error => {
    return Promise.resolve(error);
  })

// 响应拦截器
axios.interceptors.response.use(
  response => {
    if (response.status === 200) {
      if (response.data.code) {
        switch (response.data.code) {
          case 401:
            // 未登录            
            toast.warning({ message: "请先登录" });
            break;
          case 403:
            // token过期
            toast.warning({ message: "token已过期" });
            break;
        }
      }
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  }
);

/** 
 * get方法，对应get请求 
 * @param {String} url [请求的url地址] 
 * @param {Object} params [请求时携带的参数] 
 */
export function get(url: string, params?: any) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        toast.error({
          message: "请检查网络",
          time: 2500
        })
        reject(err.data)
      })
  });
}
/** 
 * post方法，对应post请求 
 * @param {String} url [请求的url地址] 
 * @param {Object} params [请求时携带的参数] 
 */
export function post(url: string, params: any,config?:any) {
  return new Promise((resolve, reject) => {
    axios.post(url, params,config)
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        toast.error({
          message: "请检查网络",
          time: 2500
        })
        reject(err.data)
      })
  });
}
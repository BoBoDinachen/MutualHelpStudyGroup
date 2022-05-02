import { createApp } from 'vue'
import Toast from './Toast.vue'

interface paramsType {
  type?: "info" | "success" | "warning" | "error",
  message: string,
  time?:number
}
class toast {
  el: HTMLElement;

  info = (params: paramsType) => {
    params.type = 'info';
    this.muntComponent(params);
  }

  success = (params: paramsType) => {
    params.type = 'success';
    this.muntComponent(params);
  }

  warning = (params: paramsType) => {
    params.type = 'warning';
    this.muntComponent(params);
  }

  error = (params: paramsType) => {
    params.type = 'error';
    this.muntComponent(params);
  }
  /**
   * 装载toast组件
   * @param params 
   */
  muntComponent = ({type,message,time=1000 }:paramsType) => {
    document.body.appendChild(this.el);
    const app = createApp(Toast, {
      type: type,
      message:message
    })
    app.mount(this.el);
    setTimeout(() => {
      document.body.removeChild(this.el);
      app.unmount();
    },time)
  }
  constructor() {
    this.el = document.createElement("div");
  }
}

export function useToast() {
  return new toast
}
import Dialog from './Dialog.vue'
import { createApp } from 'vue'

/**
 * 属性的类型
 */
interface paramsType {
  type?: "info" | "warning" | "success" | "error",
  title: string, // 标题
  content: string, // 内容
  onConfirmClick?: any // 点击肯定--事件
}

class dialog {
  // 容器
  el: HTMLElement;
  // 警告 弹出框
  warning = (params: paramsType) => {
    // 将check-box组件挂载到el元素上面
    document.body.appendChild(this.el);
    params.type = "warning";
    this.mountComponent(params);
  };
  // 成功 弹出框
  success = (params: paramsType) => {
    // 将check-box组件挂载到el元素上面
    document.body.appendChild(this.el);
    params.type = "success";
    this.mountComponent(params);
  };
  // 失败弹出框
  error = (params: paramsType) => {
    // 将check-box组件挂载到el元素上面
    document.body.appendChild(this.el);
    params.type = "error";
    this.mountComponent(params);
  };
  // 提示弹出框
  info = (params: paramsType) => {
    // 将check-box组件挂载到el元素上面
    document.body.appendChild(this.el);
    params.type = "info";
    this.mountComponent(params);
  };
  // 卸载全局组件
  unloadContainer = (app: any) => {
    document.body.removeChild(document.getElementById("dialog") as Node);
    app.unmount();
  };
  // 挂载全局组件
  mountComponent = (params: paramsType) => {
    const app = createApp(Dialog,
      {
        title: params.title,
        type: params.type,
        content: params.content,
        hidden: () => {
          this.unloadContainer(app);
        },
        onConfirmClick: () => {
          this.unloadContainer(app);
          if (params.onConfirmClick) {
            params.onConfirmClick();
          }
        }
      }
    );
    app.mount(this.el);
  };
  // 构造器
  constructor() {
    // 创建出容器el
    this.el = document.createElement("div");
    this.el.id = "dialog";
  };
}
// 导出方法
export function useDialog(): dialog {
  return new dialog();
}
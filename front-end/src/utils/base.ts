import { Router } from 'vue-router'
/**
 * 让路由延时进入
 * @param time 
 * @param params 
 */
function delayedSwitchRoute(time: number = 200, router: Router, params: {}) {
  setTimeout(() => {
    router.push(params);
  }, time)
}

/**
 * 获取当前日期前后N天
 * @param dayCount
 * @returns 日期字符串
 */
function getDateStr(dayCount: number) {
  var dd = new Date();
  dd.setDate(dd.getDate() + dayCount);//获取dayCount天后的日期 
  var y = dd.getFullYear();
  var m = dd.getMonth() + 1;//获取当前月份的日期 
  var d = dd.getDate();
  return m + "-" + d;
}

/**
 * 将字节转换为KB MB
 * @param bytes 字节数
 * @returns 
 */
function bytesToSize(bytes:number) {
  if (bytes === 0) return '0 B';
  let k = 1024,
    sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
    i = Math.floor(Math.log(bytes) / Math.log(k));
  return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i];
}

/**
 * 预览头像的方法
 */
 function previewImage(target: any, file: any) {
  let src = window.URL.createObjectURL(file);
  target.onload = function () {
    window.URL.revokeObjectURL(this.src);
  };
  target.src = src;
}
export {
  delayedSwitchRoute,
  getDateStr,
  bytesToSize,
  previewImage
}
import { get, post } from '../index'

const changeTaskStatus = (taskId:number,isFinish2:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/task/update", {id:taskId,isFinish2});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
const createPlanTask = (planId:number,title:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/task/create", {planId,title});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
export {
  changeTaskStatus,
  createPlanTask
}
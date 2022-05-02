import { get, post } from '../index'
import { Problem_Type } from '@/types/index'

/**
 * 获取计划详情
 * @param planInd 计划id
 * @returns 
 */
const getPlanInfo = (planInd:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/info/"+planInd);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}

/**
 * 获取成员的计划列表
 * @param groupId 小组id
 * @param memberId 成员id
 */
const getMemberPlanList = (groupId:string,memberId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/list/"+groupId+"/"+memberId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 获取小组内的所有计划列表
 * @param groupId 小组id
 */
const getGroupPlanList = (groupId: string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/list/"+groupId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 完成 / 取消计划
 * @param planId 计划id
 * @param isFinish 是否完成
 */
const changePlanStatus = (planId:number,isFinish:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/plan/update", {planId,isFinish});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 创建计划
 * @param params 
 */
const createPlan = (params: { studentId: number, groupId: number|string, planTitle: string, target: string, endTime: string }) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/plan/create", {...params});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 删除计划
 * @param planId 计划id
 * @returns 
 */
const deletePlan = (planId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/delete/"+planId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 提交计划审核
 * @param params 
 */
const submitPlanAudit = (params: {groupId:number|string,studentId:number,planId:number}) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/plan/submit", {...params});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 查询计划是否已通过审核
 * @param planId 计划id
 * @returns 
 */
const findPlanAudit = (planId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/audit/find/"+planId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 汇报计划问题
 * @param params 
 */
const reportPlanProblem = (params:Problem_Type) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await post("/problem/report", {...params});
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 计划通过审核
 * @param planId 计划id
 */
const passPlanAudit = (planId:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/audit/"+planId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 查询计划的审核次数
 * @param planId 计划id
 */
const getPlanAuditNumber = (planId:number) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/plan/audit/number/"+planId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
/**
 * 获取问题列表
 * @param planId 计划id
 * @returns 
 */
const getProblemList = (planId:string) => {
  return new Promise(async (resolve, reject) => {
    try {
      const res = await get("/problem/list/"+planId);
      resolve(res)
    } catch (error) {
      reject(error)
    }
  })
}
// 导出
export {
  getPlanInfo,
  getMemberPlanList,
  changePlanStatus,
  getGroupPlanList,
  createPlan,
  deletePlan,
  submitPlanAudit,
  findPlanAudit,
  reportPlanProblem,
  passPlanAudit,
  getPlanAuditNumber,
  getProblemList
}
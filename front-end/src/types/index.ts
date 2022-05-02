/**
 * 学生的结果类型
 */
interface Student_Type {
  id: number,
  email: string,
  userName: string,
  studentNumber: string,
  age: number,
  sex: string
}
/**
 * 计划的结果类型
 */
interface Plan_Type {
  id: number,
  groupId: number | string,
  studentId: number,
  student?:Student_Type,
  planTitle: string,
  target?: string,
  startTime?: string,
  endTime?: string,
  isFinish?: number,
  isSubmit?: boolean,
  auditNumber?:number,
  tasks: PlanTask_Type[]
}

/**
 * 计划阶段的结果类型
 */
interface PlanTask_Type {
  id: number,
  planId?: number,
  title: string,
  number?: number,
  isFinish2?: number
}

/**
 * 小组数据的结果类型
 */
interface Group_Type {
  id: string,
  name: string,
  leader?: number,
  leaderName?: string,
  createTime?: string,
  introduce: string,
  isActive?: boolean,
  memberNumber?: number,
  isJoin?: boolean,
  myPlans?: string,
}

/**
 * 小组成员类型
 */
interface Member_Type {
  id: number,
  groupId: number,
  isLeader: number,
  joinTime: string,
  student: { id: number, email: string, sex: string, userName: string }
}

/**
 * 计划问题结果类型
 */
interface Problem_Type {
  "id"?: number,
  "studentId": number,
  "student"?:Student_Type,
  "groupId": number,
  "planId": number,
  "plan"?:Plan_Type,
  "issue": string,
  "reportTime"?:string
}

/**
 * 便签的结果类型
 */
interface Sticky_Type {
  id?: number,
  studentId?: number,
  title?: string,
  createTime?: string
}
// 导出
export {
  Student_Type,
  Plan_Type,
  PlanTask_Type,
  Group_Type,
  Member_Type,
  Problem_Type,
  Sticky_Type
}
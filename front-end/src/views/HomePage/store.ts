import { ref, reactive } from 'vue'
import { PlanTask_Type, Plan_Type, Group_Type } from '@/types/index'
import { getMemberPlanList } from '@/services/api/plan';
import { userInfoStorage } from '@/utils/storageUtil'
const userInfo = userInfoStorage.value;

const store = {
  debug: true,

  state: reactive({
    activeGroupInfo: {} as Group_Type, // 当前选中的小组信息
    activePlanStatus: "all" as "all" | "finish" | "undone", // 当前计划列表的类型
    planList: [] as Plan_Type[], // 计划列表
    finishNumber: 0 as number, // 完成计划数量
    undoneNumber: 0 as number // 未完成计划数量
  }),
  // 改变当前选中的小组信息
  setActiveGroupInfo(groupInfo: Group_Type) {
    this.state.activeGroupInfo = groupInfo;
  },
  // 改变当前计划的列表状态
  setActivePlanStatus(status: "all" | "finish" | "undone") {
    this.state.activePlanStatus = status;
  },
  // 初始化列表
  initAllPlanList() {
    // 初始化成员计划列表
    getMemberPlanList(this.state.activeGroupInfo.id, userInfo.id).then((res: any) => {
      if (res.data) {
        store.setPlanListAction(res.data.planList);
      } else {
        store.setPlanListAction([]);
      }
    })
  },
  // 初始化计划列表
  setPlanListAction(planList: Plan_Type[]) {
    this.state.planList = planList;
  },
  // 获取完成计划数量
  getFinishNumber() {
    let list = this.state.planList.filter((item) => {
      return item.isFinish !== 0;
    })
    return list.length;
  },
  // 获取未完成计划数量
  getUndoneNumber() {
    let list = this.state.planList.filter((item) => {
      return item.isFinish !== 1;
    })
    return list.length;
  },
  // 增加计划
  // 删除计划
  // 过滤计划类型
  getPlanListByType(type: "all" | "finish" | "undone") {
    // 处理计划的类型
    switch (type) {
      case "all":
        return this.state.planList;
      case "finish":
        return this.state.planList.filter((item) => {
          return item.isFinish === 1;
        })
      case "undone":
        return this.state.planList.filter((item) => {
          return item.isFinish === 0;
        })
    }
  },


  // 添加子任务
  // 删除子任务

}

export {
  store
}
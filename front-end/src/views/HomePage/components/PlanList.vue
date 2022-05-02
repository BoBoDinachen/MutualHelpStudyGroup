<template>
  <div class="flex-1 overflow-y-scroll overflow-hidden bg-slate-50">
    <!-- 数据为空时 -->
    <div
      v-show="showNullBox"
      class="absolute top-1/4 w-full flex flex-col items-center justify-center"
    >
      <span class="font-bold mt-3 text-lg">计划列表空空如也....</span>
      <img src="@/assets/images/data-null.jpg" />
      <span class="font-bold mt-3 text-lg">快去创建你的计划吧~</span>
    </div>
    <!-- 正在进行 -->
    <ul :class="styles['plan-list-box']" v-show="showUndonePlanList">
      <div
        class="bg-slate-700 px-2 py-1 text-sm font-bold w-full text-white"
      >正在进行({{ undonePlanList.length }})</div>
      <!-- 每个计划子项 -->
      <plan-item
        :init-all-plan-list="() => { initAllPlanList(store.state.activeGroupInfo.id) }"
        :plan="item"
        v-for="(item,index) in undonePlanList"
        :key="item.id"
      ></plan-item>
    </ul>
    <!-- 已完成 -->
    <ul :class="styles['plan-list-box']" v-show="showFinishPlanList">
      <div
        class="bg-slate-700 px-2 py-1 text-sm font-bold w-full text-white"
      >已完成({{ finishPlanList.length }})</div>
      <!-- 每个计划子项 -->
      <plan-item
        :init-all-plan-list="() => { initAllPlanList(store.state.activeGroupInfo.id) }"
        :plan="item"
        v-for="(item,index) in finishPlanList"
        :key="item.id"
      ></plan-item>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watch, nextTick } from 'vue'
// *******************************************导入的组件和hooks
import PlanItem from './PlanItem.vue'
import { getMemberPlanList, findPlanAudit } from '@/services/api/plan'
import { store } from '../store'
import { userInfoStorage } from '@/utils/storageUtil'
import { Plan_Type } from '@/types'
// *******************************************定义状态、ref
const userInfo = userInfoStorage.value;
const finishPlanList = ref<Plan_Type[]>([]) // 已完成的计划列表
const undonePlanList = ref<Plan_Type[]>([]) // 未完成的计划列表
const showNullBox = ref(false); // 显示空状态的盒子
const showFinishPlanList = ref(true);
const showUndonePlanList = ref(true);

/**
 * 监听当前选中的小组信息
 */
watch(() => store.state.activeGroupInfo, () => {

  initAllPlanList(store.state.activeGroupInfo.id);
})

watch(() => store.state.planList, () => {
  initAllPlanList(store.state.activeGroupInfo.id);
})
/**
 * 监听当前计划的类型改变
 */
watch(() => store.state.activePlanStatus, () => {
  const type = store.state.activePlanStatus;
  switch (type) {
    case "all":
      showFinishPlanList.value = true;
      showUndonePlanList.value = true;
      break;
    case "finish":
      showFinishPlanList.value = true;
      showUndonePlanList.value = false;
      break;
    case "undone":
      showFinishPlanList.value = false;
      showUndonePlanList.value = true;
      break;
  }
})

// 初始化列表
function initAllPlanList(groupId: string) {
  finishPlanList.value = [];
  undonePlanList.value = [];
  // 初始化成员计划列表
  getMemberPlanList(groupId, userInfo.id).then((res: any) => {
    console.log(res);
    if (res.data) {
      showNullBox.value = false;
      res.data.planList.forEach((plan: Plan_Type) => {
        if (plan.isFinish === 1) {
          // 判断是否已提交审核
          findPlanAudit(plan.id).then((res: any) => {
            // console.log(res);
            plan.isSubmit = res.status;
            finishPlanList.value.push(plan);
          })
        } else {
          undonePlanList.value.push(plan);
        }
      })
    } else {
      showNullBox.value = true;
    }
  })
}
</script>
<style module="styles">
/* 列表 */
.plan-list-box {
  @apply w-full flex flex-col justify-start;
}
</style>
<template>
  <div class="flex-1 overflow-y-scroll overflow-hidden bg-slate-50 h-screen">
    <!-- 关闭 -->
    <div class="flex items-center ml-3 mt-3" @click="props.clickHide">
      <close theme="outline" size="20" fill="#333" />
      <span class="ml-1 fontb">关闭</span>
    </div>
    <!-- 成员信息显示 -->
    <div :class="styles['member-item']">
      <img
        :src="`${props.activeMemberId?'/api/download/avatar/'+props.activeMemberId+'?'+Math.ceil(Math.random() * 100000):''}`"
      />
      <div>
        <div class="flex items-center">
          <span class="text-sm font-bold">{{ activeMemberInfo?.userName }}</span>
          <female
            v-if="activeMemberInfo?.sex === '女'"
            class="ml-2"
            theme="outline"
            size="15"
            fill="#E5989B"
          />
          <male
            v-if="activeMemberInfo?.sex === '男'"
            class="ml-2"
            theme="outline"
            size="15"
            fill="#29b8db"
          />
        </div>
        <span class="text-sm text-slate-500 mt-1">{{ activeMemberInfo?.email }}</span>
      </div>
    </div>
    <!-- 数据为空时 -->
    <div
      v-show="showNullBox"
      class="absolute top-1/4 w-full flex flex-col items-center justify-center"
    >
      <img src="@/assets/images/data-null.jpg" />
      <span class="font-bold mt-3 text-lg">计划列表空空如也....</span>
    </div>
    <!-- 正在进行 -->
    <ul :class="styles['plan-list-box']" v-show="showUndonePlanList">
      <div
        class="bg-slate-700 px-2 py-1 text-sm font-bold w-full text-white"
      >正在进行({{ undonePlanList.length }})</div>
      <!-- 每个计划子项 -->
      <group-plan-item
        :init-all-plan-list="() => { initAllPlanList() }"
        :plan="item"
        v-for="(item,index) in undonePlanList"
        :key="item.id"
      ></group-plan-item>
    </ul>
    <!-- 已完成 -->
    <ul :class="styles['plan-list-box']" v-show="showFinishPlanList">
      <div
        class="bg-slate-700 px-2 py-1 text-sm font-bold w-full text-white"
      >已完成({{ finishPlanList.length }})</div>
      <!-- 每个计划子项 -->
      <group-plan-item
        :init-all-plan-list="() => { initAllPlanList() }"
        :plan="item"
        v-for="(item,index) in finishPlanList"
        :key="item.id"
      ></group-plan-item>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { ref, watch} from 'vue'
import {Female,Male,Close} from '@icon-park/vue-next'
// *******************************************导入的组件和hooks
import GroupPlanItem from './GroupPlanItem.vue'
import { getMemberPlanList, findPlanAudit } from '@/services/api/plan'
import { getUserInfo } from '@/services/api/user'

import { Plan_Type, Student_Type } from '@/types'
// *******************************************定义状态、ref
const finishPlanList = ref<Plan_Type[]>([]) // 已完成的计划列表
const undonePlanList = ref<Plan_Type[]>([]) // 未完成的计划列表
const showNullBox = ref(false); // 显示空状态的盒子
const showFinishPlanList = ref(true);
const showUndonePlanList = ref(true);
const activeMemberInfo = ref<Student_Type>();

const props = defineProps<{
  clickHide: ()=>void,
  activeMemberId: number,
  activeGroupId: string,
}>()


/**
 * 监听当前成员id的改变
 */
watch(() => props.activeMemberId, () => {
  // 初始化成员计划列表
  initAllPlanList();
  // 初始化成员信息
  initMemberInfo();
})

function initMemberInfo(){
  // 获取用户信息
  getUserInfo(props.activeMemberId+'').then((res:any)=>{
    if (res.status) {
      activeMemberInfo.value = res.data.student;
    }
  });
}

// 初始化列表
function initAllPlanList() {
  // 初始化成员计划列表
  finishPlanList.value = [];
  undonePlanList.value = [];
  getMemberPlanList(props.activeGroupId, props.activeMemberId).then((res: any) => {
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
.member-item {
  @apply flex justify-between items-center p-4 border-y
}
.member-item img {
  @apply w-12 h-12 rounded-md object-cover;
}
.member-item > div {
  @apply flex-1 ml-3 flex flex-col justify-between;
}
</style>
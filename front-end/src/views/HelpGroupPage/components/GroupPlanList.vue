<template>
  <div :class="styles['container-box']">
    <div class="flex items-center ml-3" @click="props.clickHide">
      <close theme="outline" size="20" fill="#333" />
      <span class="ml-1 fontb">关闭</span>
    </div>
    <!-- 顶部栏 -->
    <div :class="styles['top-bar']">
      <a
        :class="`${activePlanType === 'undone' ? 'border-b-4 border-orange-400' : ''}`"
        @click="() => { activePlanType = 'undone' }"
      >正在进行({{ undoneNumber }})</a>
      <a
        :class="`${activePlanType === 'finish' ? 'border-b-4 border-orange-400' : ''}`"
        @click="() => { activePlanType = 'finish' }"
      >已完成({{ finishNumber }})</a>
      <a
        :class="`${activePlanType === 'aduit' ? 'border-b-4 border-orange-400' : ''}`"
        @click="() => { activePlanType = 'aduit' }"
      >正在审核({{ aduitNumber }})</a>
    </div>
    <!-- 计划列表 -->
    <ul class="flex flex-col flex-1 overflow-hidden overflow-y-scroll space-y-2">
      <li :class="styles['plan-item-box']" v-for="plan in planList" :key="plan.id">
        <img :src="`/api/download/avatar/` + plan.studentId" />
        <div class="flex-1 flex flex-col justify-start ml-2">
          <span class="font-bold">计划：{{ plan.planTitle }}</span>
          <span class="text-sm font-bold text-slate-600">子任务：{{ plan.tasks.length }}</span>
          <span class="text-sm text-slate-600">
            {{ plan.student?.userName }}
            <b class="ml-2 text-xs">{{ plan.student?.sex }}</b>
          </span>
        </div>
        <div class="text-white text-sm">
          <span v-if="plan.isFinish === 0" class="px-2 py-1 rounded-sm bg-orange-500">正在进行</span>
          <span
            v-if="plan.isFinish === 1 && plan.isSubmit === false"
            class="px-2 py-1 rounded-sm bg-blue-500"
          >已完成</span>
          <span
            class="text-black mr-2"
            v-if="plan.isFinish === 1 && plan.isSubmit === true"
          >审核次数:{{ plan.auditNumber }}</span>
          <span
            @click="() => { handlePlanAudit(plan) }"
            v-if="plan.isFinish === 1 && plan.isSubmit === true"
            class="px-2 py-1 rounded-sm bg-green-500"
          >通过</span>
        </div>
      </li>
    </ul>
    <!-- 数据为空时 -->
    <div
      v-show="planList.length === 0"
      class="absolute top-1/4 w-full flex flex-col items-center justify-center"
    >
      <img src="@/assets/images/data-null.jpg" />
      <span class="font-bold mt-3 text-lg">暂时没有计划~</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch, onMounted, computed } from 'vue'
// ************************************************************ api/组件/函数
import { Close } from '@icon-park/vue-next'
import { Plan_Type } from '@/types';
import { getUserInfo } from '@/services/api/user'
import { useToast } from '@/components/index'
import { findPlanAudit, getGroupPlanList, passPlanAudit, getPlanAuditNumber } from '@/services/api/plan'
// ************************************************************ 定义状态
const activePlanType = ref<"undone" | "finish" | "aduit">("undone");
const planList = ref<Plan_Type[]>([]); // 小组列表
const props = defineProps<{
  activeGroupId: string,
  clickHide: () => void
}>()
const toast = useToast();
const undoneNumber = ref(); // 正在进行计划数量
const finishNumber = ref(); // 完成计划数量
const aduitNumber = ref(); // 审核计划数量

// 加载统计数据
const initNumber = () => {
  undoneNumber.value = 0;
  finishNumber.value = 0;
  aduitNumber.value = 0;
  getGroupPlanList(props.activeGroupId).then((res: any) => {
    if (res.data) {
      res.data.planList.forEach((plan: Plan_Type) => {
        findPlanAudit(plan.id).then((res3: any) => {
          // console.log(res);
          plan.isSubmit = res3.status;
          let list1 = res.data.planList.filter((plan: Plan_Type) => {
            return plan.isFinish !== 1;
          })
          let list2 = res.data.planList.filter((plan: Plan_Type) => {
            return plan.isFinish !== 0 && plan.isSubmit === false;
          })
          let list3 = res.data.planList.filter((plan: Plan_Type) => {
            return plan.isSubmit !== false && plan.isFinish === 1;
          })
          undoneNumber.value = list1.length;
          finishNumber.value = list2.length;
          aduitNumber.value = list3.length;
        });
      });
    }
  })
}
// 监听当前改变的小组信息
watch(() => props.activeGroupId, () => {
  initPlanList(props.activeGroupId, 'undone'); // 加载计划列表
  activePlanType.value = 'undone';
  initNumber(); //初始化计划的统计数量
})

// 加载计划列表
function initPlanList(groupId: string, type: "undone" | "finish" | "aduit") {
  planList.value = [];
  // 获取计划列表
  getGroupPlanList(groupId).then((res: any) => {
    if (res.data) {
      res.data.planList.forEach((plan: Plan_Type) => {
        // 获取用户信息
        getUserInfo(plan.studentId + '').then((res2: any) => {
          plan['student'] = res2.data.student;
          // 判断是否提交审核
          if (plan.isFinish === 1) {
            // 判断是否已提交审核
            findPlanAudit(plan.id).then((res3: any) => {
              // console.log(res);
              plan.isSubmit = res3.status;
              if (res3.status) {
                // 统计审核次数
                getPlanAuditNumber(plan.id).then((res4: any) => {
                  plan.auditNumber = res4.data;
                })
              }
              if (type === 'undone') {
                planList.value = res.data.planList.filter((item: Plan_Type) => {
                  return item.isFinish !== 1;
                });
              } else if (type === 'finish') {
                planList.value = res.data.planList.filter((item: Plan_Type) => {
                  return item.isFinish !== 0 && item.isSubmit === false;
                });
              } else if (type === 'aduit') {
                planList.value = res.data.planList.filter((item: Plan_Type) => {
                  return item.isSubmit !== false && item.isFinish === 1;
                });
              }

            })
          }
        });
      });
    }
  })
}

// 点击审核通过
const handlePlanAudit = (plan: Plan_Type) => {
  passPlanAudit(plan.id + '').then((res: any) => {
    console.log(res);
    if (res.status) {
      toast.success({ message: "审核成功！" })
      plan.auditNumber = res.data.currentPassNumber;
    }
  })

}

// 监听计划的类型
watch(() => activePlanType.value, () => {
  switch (activePlanType.value) {
    case 'undone':
      initPlanList(props.activeGroupId, 'undone');
      break;
    case 'finish':
      initPlanList(props.activeGroupId, 'finish');
      break;
    case 'aduit':
      initPlanList(props.activeGroupId, 'aduit');
      break;
  }
})
</script>
<style module="styles">
.container-box {
  @apply h-screen  w-full  py-3 flex flex-col justify-start overflow-hidden;
}
/* 顶部栏 */
.top-bar {
  @apply flex justify-around items-center border-b border-slate-400 py-3;
}
.top-bar a {
  @apply px-2 py-1 text-black active:bg-slate-400 rounded-sm
    transition-all duration-300 ease-in-out;
}
/* 列表子项 */
.plan-item-box {
  @apply flex justify-start items-start w-full p-2 mt-2 px-4;
}
.plan-item-box img {
  @apply w-12 h-12 object-cover rounded-full;
}
</style>
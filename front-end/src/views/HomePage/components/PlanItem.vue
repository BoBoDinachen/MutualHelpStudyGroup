<template>
  <!-- 每个计划列表的子项 -->
  <li
    :class="`${styles['plan-item-box']} ${plan.isFinish ? 'border-lime-600' : 'border-l-orange-500'}`"
  >
    <!-- 复选框 -->
    <div :class="styles['plan-item-check']">
      <img
        v-show="plan.isFinish === 1"
        class="w-5"
        src="@/assets/images/checked.png"
        @click="handleChecked"
      />
      <img
        v-show="plan.isFinish === 0"
        class="w-5"
        src="@/assets/images/unchecked.png"
        @click="handleUnChecked"
      />
    </div>
    <!-- 右边部分 -->
    <div class="flex-1 flex flex-col justify-start">
      <!-- 标题栏 -->
      <div class="w-full flex-1 h-5 flex justify-between items-center">
        <span class="text-black flex-1 font-bold text-base">{{ plan.planTitle }}</span>
        <span class="text-xs text-green-500" v-if="plan.isFinish === 1 && plan.isSubmit">已提交审核</span>
        <span class="text-xs text-orange-500" v-if="plan.isFinish === 1 && plan.isSubmit === false">还未提交审核</span>
        <more-one theme="outline" size="24" fill="#333" @click="() => { openPlanByDrawer(plan) }" />
      </div>
      <!-- 计划详情 -->
      <div :class="styles['plan-details-box']">
        <span>截止时间：{{ plan.endTime }}</span>
        <span>描述：{{ plan.target }}</span>
      </div>
      <!-- 展开 / 收起 阶段任务 -->
      <div class="flex items-center px-2 py-0.5 justify-around mt-2 w-fit bg-slate-200 rounded-sm">
        <up v-show="isUnfold" theme="outline" size="12" fill="#333" @click="handlePackUp" />
        <down v-show="!isUnfold" theme="outline" size="12" fill="#333" @click="handleUnfold" />
        <span
          class="text-xs font-bold text-slate-500"
          style="font-size: 10px;"
        >{{ isUnfold ? '收起' : (plan.isFinish === 1 ? '子任务' : taskFinishNumber + '/' + plan.tasks.length) }}</span>
      </div>
      <!-- 子任务 -->
      <ul ref="taskListRef" v-show="isUnfold" :class="styles['task-list-box']">
        <li :class="styles['task-item']" v-for="item in plan.tasks" :key="item.id">
          <span v-if="plan.isFinish === 1" class="w-2 h-2 bg-slate-600 rounded-full"></span>
          <img
            v-show="plan.isFinish !== 1 && item.isFinish2 === 0"
            @click="() => { handleFinishTask(item) }"
            class="w-3.5"
            src="@/assets/images/unchecked2.png"
          />
          <img
            v-show="plan.isFinish !== 1 && item.isFinish2 === 1"
            @click="() => { handleCancelTask(item) }"
            class="w-3.5"
            src="@/assets/images/checked2.png"
          />
          <span class="text-xs text-slate-600 ml-1">{{item.title }}</span>
        </li>
      </ul>
    </div>
  </li>
</template>
<script lang="ts" setup>
import { ref, inject, computed } from 'vue'
import { Up, Down, MoreOne } from '@icon-park/vue-next'
// ********************************************* 导入组件、hooks、工具函数
import { useToast } from '@/components/index'
import { PlanTask_Type, Plan_Type } from '@/types/index'
import { changePlanStatus } from '@/services/api/plan'
import { changeTaskStatus } from '@/services/api/task'
// ********************************************* 定义状态和ref
const toast = useToast();
const taskListRef = ref<any>(null);
const isUnfold = ref(false); // 是否展开

// 统计子任务的完成个数
const taskFinishNumber = computed(() => {
  let number = 0;
  props.plan.tasks.forEach((item) => {
    if (item.isFinish2 === 1) {
      number += 1;
    }
  })
  return number
})
// 定义组件属性
const props = defineProps<{
  plan: Plan_Type,
  initAllPlanList: () => void
}>()
// 打开抽屉
const openPlanByDrawer = inject("openPlanByDrawer") as (plan: Plan_Type) => void;

/**
 * 展开子任务
 */
const handleUnfold = (even: any) => {
  even.stopPropagation();
  isUnfold.value = true;
  setTimeout(() => {
    taskListRef.value.style.opacity = 1;
  }, 50);
}

/**
 * 收起子任务
 */
const handlePackUp = () => {
  taskListRef.value.style.opacity = 0;
  setTimeout(() => {
    isUnfold.value = false;
  }, 100);
}

/**
 * 点击取消计划
 */
const handleChecked = () => {
  props.plan.isFinish = 0;
  changePlanStatus(props.plan.id, 0).then((res: any) => {
    console.log(res);
    if (res.status) {
      setTimeout(() => { props.initAllPlanList(); }, 250)
      toast.info({ message: "取消计划" });
    }
  })
}
/**
 * 点击完成计划
 */
const handleUnChecked = () => {
  props.plan.isFinish = 1;
  changePlanStatus(props.plan.id, 1).then((res: any) => {
    console.log(res);
    if (res.status) {
      setTimeout(() => { props.initAllPlanList(); }, 250)
      toast.info({ message: "完成计划" });
    }
  })
}
/**
 * 点击完成任务
 */
const handleFinishTask = (task: PlanTask_Type) => {
  task.isFinish2 = 1;
  changeTaskStatus(task.id, 1).then((res: any) => {
    if (res.status) {
      toast.success({ message: "完成任务" });
    }
  })
}
/**
 * 点击取消任务
 */
const handleCancelTask = (task: PlanTask_Type) => {
  task.isFinish2 = 0;
  changeTaskStatus(task.id, 0).then((res: any) => {
    if (res.status) {
      toast.info({ message: "取消计划" });
    }
  })
}
</script>
<style module="styles">
/* 计划子项 */
.plan-item-box {
  @apply flex justify-between bg-white items-start mb-3  border-l-4 p-3;
}
.plan-details-box {
  @apply flex flex-col items-start flex-1 text-xs;
}
.plan-details-box span {
  font-size: 10px;
  @apply font-bold text-slate-500;
}
/* 复选框 */
.plan-item-check {
  @apply flex flex-col justify-center mr-3 h-6;
}

/* 子任务 */
.task-list-box {
  @apply mt-2  transition-all duration-300 ease-in-out;
}
.task-item {
  @apply flex w-full items-center py-1;
}
</style>
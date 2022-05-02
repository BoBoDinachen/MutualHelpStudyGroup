<template>
  <div :class="styles['create-box']">
    <!-- 添加计划 -->
    <div class="flex items-center">
      <span class="block h-3.5 w-3.5 bg-slate-700 rounded-full"></span>
      <input
        class="flex-1 font-bold h-12 px-3 truncate overflow-hidden"
        placeholder="准备做什么？"
        type="text"
        v-model="planInfo.planTitle"
      />
      <a class="text-orange-500" @click="handleAddPlan">添加</a>
    </div>
    <input
      class="text-sm font-bold text-slate-500 w-full truncate overflow-hidden"
      type="text"
      v-model="planInfo.target"
      placeholder="目标描述"
    />
    <!-- 选择结束时间 -->
    <div :class="styles['date-picker']">
      <div
        :class="`${activeDate === 'date' ? 'bg-teal-500' : 'bg-slate-300'}`"
        @click="() => { activeDate = 'date', planInfo.endTime = '' }"
      >
        <input v-model="planInfo.endTime" class="absolute w-16 opacity-0" type="date" />
        <span>选择日期</span>
      </div>
      <span
        :class="`${activeDate === 'null' ? 'bg-teal-500' : 'bg-slate-300'}`"
        @click="() => { activeDate = 'null', planInfo.endTime = '无期限' }"
      >没有日期</span>
      <label>截止日期：{{ planInfo.endTime }}</label>
    </div>
    <!-- 子任务 -->
    <ul>
      <li class="border-b flex items-center" v-for="(task,index) in taskList">
        <branch theme="outline" size="20" fill="#333" />
        <input
          type="text"
          class="h-12 pl-3 flex-1 text-sm font-bold text-slate-700 truncate overflow-hidden"
          placeholder="添加子任务"
          v-model="task.title"
        />
        <reduce-one theme="outline" size="18" fill="#333" @click="handleReduceTask" />
        <add-one class="ml-3" theme="outline" size="18" fill="#333" @click="handleAddTask" />
      </li>
      <li class="h-14"></li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { ref, inject } from 'vue';
import { Branch, AddOne, ReduceOne } from '@icon-park/vue-next'
// *******************************************************导入组件和hooks
import { userInfoStorage } from '@/utils/storageUtil'
import { PlanTask_Type, Plan_Type } from '@/types';
import { useDialog, useToast } from '@/components/index'
import { createPlan } from '@/services/api/plan'
import { createPlanTask } from '@/services/api/task'
import { store } from '../store'
// *******************************************************定义状态
const closeCreatePlanDrawer = inject("closeCreatePlanDrawer") as () => void;
const dialog = useDialog();
const toast = useToast();
const activeDate = ref<"date" | "null">("date");
const userInfo = userInfoStorage.value; // 用户信息
const planInfo = ref({
  groupId: "",
  studentId: 0,
  planTitle: "",
  target: "",
  endTime: ""
}); // 计划信息
const taskList = ref([
  { title: "" }
]); // 任务列表

/**
 * 点击添加任务
 */
const handleAddTask = () => {
  taskList.value.push({
    title: ""
  })
}
/**
 * 减去子任务
 */
const handleReduceTask = () => {
  if (taskList.value.length >= 2) {
    taskList.value.pop();
  }
}
/**
 * 点击添加计划
 */
const handleAddPlan = () => {
  //验证输入
  if (verifyInput()) {
    // 先询问
    dialog.info({
      title: "添加计划",
      content: "确定要添加嘛？",
      onConfirmClick: () => {
        // 执行请求
        planInfo.value.groupId = store.state.activeGroupInfo.id;
        planInfo.value.studentId = userInfo.id;
        createPlan(planInfo.value).then((res: any) => {
          console.log(res);
          if (res.status) {
            //判断任务列表是否为空，添加任务
            if (taskList.value.length !== 0) {
              taskList.value.forEach(async (task) => {
                if (task.title !== "") {
                  // 添加任务
                  let taskRes: any = await createPlanTask(res.data.planId, task.title);
                }
              })
            }
            successCallBack();
          }
        })
      }
    })
  }
}
// 添加成功之后的回调
function successCallBack() {
  toast.success({ message: "添加成功" });
  planInfo.value = {
    groupId: "",
    studentId: 0,
    planTitle: "",
    target: "",
    endTime: ""
  } // 重置计划信息
  store.initAllPlanList(); // 重新加载计划列表
  closeCreatePlanDrawer(); // 关闭添加模态框
}
/**
 * 验证输入
 */
const verifyInput = () => {
  if (planInfo.value.planTitle === "") {
    toast.warning({ message: "计划标题为空" });
    return false;
  } else if (planInfo.value.target === "") {
    toast.warning({ message: "目标描述为空" });
    return false;
  } else if (planInfo.value.endTime === "") {
    toast.warning({ message: "请选择日期" });
    return false;
  } else {
    return true;
  }
}
</script>
<style module="styles">
.create-box {
  @apply h-auto w-full px-4 overflow-hidden;
}
.date-picker {
  @apply w-full flex items-center justify-start space-x-3 text-xs py-3 border-y mt-3;
}
.date-picker > div {
  @apply relative flex items-center justify-center text-white rounded-2xl p-1 px-2;
}
.date-picker > span {
  @apply relative flex items-center justify-center text-white rounded-2xl p-1 px-2;
}
</style>
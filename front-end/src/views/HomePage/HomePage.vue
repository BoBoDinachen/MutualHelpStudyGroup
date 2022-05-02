<template>
  <div class="w-full h-full flex flex-col justify-start overflow-hidden">
    <!-- 顶部栏 -->
    <top-bar :open-side-bar="() => { showSideBar = true }"></top-bar>
    <!-- 计划列表 -->
    <plan-list></plan-list>
    <!-- 添加按钮 -->
    <div :class="`${styles['home-add-btn']} theme-dark`" @click="() => { showDrawer1 = true }">
      <plus theme="outline" size="45" fill="#ffffff" />
    </div>
    <!-- 侧边栏 -->
    <side-bar :show="showSideBar" :click-hide="() => { showSideBar = false }"></side-bar>
    <!-- 抽屉--创建计划 -->
    <drawer :show="showDrawer1" :click-hide="() => { showDrawer1 = false }">
      <create-plan></create-plan>
    </drawer>
    <!-- 抽屉--修改计划----删除计划----提交计划（成员审核）---汇报问题 -->
    <drawer :show="showDrawer2" :click-hide="() => { showDrawer2 = false }">
      <ul :class="styles['drawer-list']">
        <li
          class="text-green-600"
          v-if="activePlanInfo?.isFinish === 1 && activePlanInfo?.isSubmit === false"
          @click="handleMenuItem('submit', activePlanInfo)"
        >提交小组审核</li>
        <li class="text-blue-500" @click="handleMenuItem('report', activePlanInfo)">汇报问题</li>
        <li class="text-red-600" @click="handleMenuItem('delete', activePlanInfo)">删除计划</li>
        <li @click="() => { showDrawer2 = false }">取消</li>
      </ul>
    </drawer>
    <!-- 抽屉--汇报问题 -->
    <modal title="汇报问题" :show="showModal" :click-hide="() => { showModal = false }">
      <textarea
        class="w-full bg-slate-200 text-sm p-2 mt-2 rounded-sm"
        placeholder="请填你遇到的问题或者情况"
        cols="30"
        rows="4"
        v-model="issueContent"
      ></textarea>
      <div class="flex w-full justify-end items-center mt-2">
        <a class="px-4 rounded-sm py-1 bg-slate-500 text-white">取消</a>
        <a class="px-4 rounded-sm py-1 bg-green-500 text-white ml-3" @click="handleReport">确定提交</a>
      </div>
    </modal>
  </div>
</template>
<script setup lang="ts">
import { ref, provide } from 'vue'
import { Plus } from '@icon-park/vue-next'
// *********************************************** 导入组件
import { useDialog, useToast } from '@/components/index'
import { store } from './store'
import Modal from '@/components/Modal/Modal.vue'
import PlanList from './components/PlanList.vue'
import TopBar from './components/TopBar.vue'
import SideBar from './components/SideBar.vue'
import Drawer from '@/components/Drawer/Drawer.vue'
import CreatePlan from './components/CreatePlan.vue'
import { deletePlan, submitPlanAudit, reportPlanProblem } from '@/services/api/plan'
import { Plan_Type } from '@/types'
// ********************************************** 定义状态
const dialog = useDialog();
const toast = useToast();
const issueContent = ref(); // 要汇报的问题内容
const activePlanInfo = ref(); // 当前活动的计划信息
const showSideBar = ref(false); // 侧边栏
const showDrawer1 = ref(false); // 抽屉一---创建计划
const showDrawer2 = ref(false); // 抽屉二---显示计划的操作列表
const showModal = ref(false); // 模态框---汇报问题
/**
 * 打开抽屉---显示计划操作列表
 */
const openPlanByDrawer = (plan: Plan_Type) => {
  console.log("当前活动计划:", plan);
  activePlanInfo.value = plan;
  showDrawer2.value = true;
}

/**
 * 点击功能的每个菜单项
 */
const handleMenuItem = (name: "submit" | "report" | "delete", plan: Plan_Type) => {
  console.log(plan);
  switch (name) {
    case "submit":
      // 提交审核
      dialog.info({
        title: "提交计划",
        content: "确定要提交嘛?",
        onConfirmClick: () => {
          submitPlanAudit({ 'groupId': plan.groupId, 'planId': plan.id, 'studentId': plan.studentId }).then((res: any) => {
            if (res.status) {
              toast.success({ message: "提交成功" });
              showDrawer2.value = false;
            }
          })
        }
      })
      break;
    case "report":
      showDrawer2.value = false;
      showModal.value = true;
      break;
    case "delete":
      dialog.warning({
        title: "删除计划",
        content: "确定要删除嘛？",
        onConfirmClick: () => {
          deletePlan(plan.id).then((res: any) => {
            if (res.status) {
              store.initAllPlanList(); // 重新加载仓库的列表
              toast.success({ message: "删除成功" });
              showDrawer2.value = false;
            }
          })
        }
      })
      break;
  }

}

/**
 * 点击汇报问题
 */
const handleReport = () => {
  if (issueContent.value === "") {
    toast.warning({ message: "请填写汇报内容" });
  } else {
    dialog.info({
      title: "提交汇报",
      content: "确定要提交嘛?",
      onConfirmClick: () => {
        reportPlanProblem({
          groupId: activePlanInfo.value.groupId,
          issue: issueContent.value,
          planId: activePlanInfo.value.id,
          studentId: activePlanInfo.value.studentId
        }).then((res: any) => {
          if (res.status) {
            toast.success({ message: "汇报成功" });
            issueContent.value = "";
            showModal.value = false;
          }
        })
      }
    })
  }
}

/**
 * 向子组件注入
 */
provide("openPlanByDrawer", openPlanByDrawer);
provide("closeCreatePlanDrawer", () => { showDrawer1.value = false });
</script>
<style module="styles">
@import url(./index.css);
</style>
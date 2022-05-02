<template>
  <div class="flex-1 flex flex-col justify-start overflow-hidden">
    <!-- 标题栏 -->
    <div :class="styles['member-list-title']">
      <list-top theme="outline" size="20" fill="#333" />
      <span>成员列表({{ state.memberList.length }}/7)</span>
    </div>
    <!-- 成员列表 -->
    <ul :class="styles['member-list-box']">
      <li :class="styles['member-list-item']" v-for="item in state.memberList">
        <img :src="`/api/download/avatar/${item.student.id}?${Math.ceil(Math.random() * 100000)}`" />
        <div @click="handleMemberItem(item.student.id,item.groupId+'')">
          <div class="flex items-center">
            <span
              v-if="item.student.id === userInfo.id && item.isLeader !== 1"
              :class="`${styles['member-item-rank']} bg-green-500`"
            >我</span>
            <span
              v-if="item.isLeader === 0 && item.student.id !== userInfo.id"
              :class="`${styles['member-item-rank']} bg-slate-500`"
            >成员</span>
            <span
              v-if="item.isLeader === 1"
              :class="`${styles['member-item-rank']} bg-orange-500`"
            >组长</span>
            <span class="text-sm font-bold">{{ item.student.userName }}</span>
            <female
              v-if="item.student.sex === '女'"
              class="ml-2"
              theme="outline"
              size="15"
              fill="#E5989B"
            />
            <male
              v-if="item.student.sex === '男'"
              class="ml-2"
              theme="outline"
              size="15"
              fill="#29b8db"
            />
          </div>
          <span class="text-sm text-slate-500 mt-1">{{ item.student.email }}</span>
        </div>
        <more-one
          v-if="activeLeaderId === userInfo.id"
          @click="() => { openDrawer1(item.student.id) }"
          theme="outline"
          size="24"
          fill="#333"
        />
      </li>
    </ul>
    <!-- 抽屉---对每个成员的操作 -->
    <drawer :show="showDrawer1" :click-hide="() => { showDrawer1 = false }">
      <ul :class="styles['drawer-list']">
        <li @click="handleMemberItem(activeMemberId,activeGroupId)">查看信息</li>
        <li @click="handleRemoveMember">移除成员</li>
        <li @click="() => { showDrawer1 = false }">取消</li>
      </ul>
    </drawer>
    <!-- 抽屉---显示每个成员的计划列表和完成计划的情况 -->
    <drawer :show="showDrawer2" :click-hide="() => { showDrawer2 = false }">
      <member-plan-list :click-hide="() => { showDrawer2 = false }" :activeMemberId="activeMemberId" :activeGroupId="activeGroupId+''"></member-plan-list>
    </drawer>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted, watch, reactive } from 'vue'
import { ListTop, MoreOne, Female, Male } from '@icon-park/vue-next'
// *************************************** 导入组件
import { useDialog, useToast } from '@/components/index'
import MemberPlanList from './MemberPlanList.vue'
import Drawer from '@/components/Drawer/Drawer.vue'
import { Group_Type, Member_Type } from '@/types';
import { userInfoStorage } from '@/utils/storageUtil'
import { getMemberList, removeMember } from '@/services/api/member'
// *************************************** 定义状态
const toast = useToast();
const dialog = useDialog();
const showDrawer1 = ref(false);
const showDrawer2 = ref(false);
const activeGroupId = ref<string>(""); // 当前的小组id
const activeLeaderId = ref<number>(0); // 当前的组长id
const activeMemberId = ref<number>(0); // 当前选中的成员信息
const userInfo = userInfoStorage.value;
const state = reactive({
  memberList: [] as Member_Type[]
}) // 状态

/**
 * 加载小组的成员列表
 */
watch(() => activeGroupId.value, () => {
  // 初始化成员列表
  getMemberList(activeGroupId.value as string).then((res: any) => {
    console.log(res);
    if (res.data) {
      state.memberList = res.data.memberList;
      res.data.memberList.forEach((item: Member_Type, index: number) => {
        if (item.isLeader) {
          activeLeaderId.value = item.student.id; // 找到组长id
        }
      });
    } else {
      state.memberList = [];
    }
  })
})

/**
 * 更新状态---过滤对应的成员
 */
const decMemberFormState = (memberId:number)=>{
  state.memberList = state.memberList.filter((item)=>{
    return item.student.id !== memberId;
  })
}

/**
 * 点击移出
 */
const handleRemoveMember = () => {
  dialog.warning({
    title: "移除成员",
    content: "确定要移除嘛？",
    onConfirmClick: () => {
      removeMember(activeLeaderId.value, activeGroupId.value, activeMemberId.value).then((res: any) => {
        console.log(res);
        if (res.status) {
          toast.success({ message: "移除成功" });
          showDrawer1.value = false;
          decMemberFormState(activeMemberId.value);
        } else {
          toast.warning({ message: "请重试" });
        }
      })
    }
  })
}

// 点击每个成员弹出计划抽屉
const handleMemberItem = (memberId:number,groupId:string)=>{
  showDrawer1.value = false;
  showDrawer2.value = true;
  console.log(memberId);
  activeMemberId.value = memberId;
  activeGroupId.value = groupId;
}

/**
 * 打开抽屉1
 */
const openDrawer1 = (memberId: number) => {
  activeMemberId.value = memberId;
  showDrawer1.value = true;
}

/**
 * 改变当前所选的小组Id
 */
const changeActiveGroupId = (group: Group_Type) => {
  activeGroupId.value = group.id;
}
defineExpose({ changeActiveGroupId })
</script>
<style module="styles">
/* 标题栏 */
.member-list-title {
  @apply flex items-center p-4;
}
.member-list-title span:nth-of-type(2) {
  @apply text-sm font-bold text-slate-500 ml-2;
}
/* 成员列表 */
.member-list-box {
  @apply flex flex-1 flex-col justify-start overflow-y-scroll overflow-hidden;
}
/*  */
.member-list-item {
  @apply flex justify-between items-center p-4;
}
.member-list-item img {
  @apply w-12 h-12 object-cover rounded-md;
}
.member-list-item > div {
  @apply flex-1 ml-3 flex flex-col justify-between;
}
.member-item-rank {
  font-size: 9px;
  @apply text-white px-2 py-0.5 rounded-xl font-bold mr-1;
}
/* 抽屉的每个菜单子项 */
.drawer-list {
  @apply flex flex-col items-center
  w-full bg-slate-200;
}
.drawer-list li {
  @apply py-2 w-full text-center active:bg-slate-200 bg-white;
}
.drawer-list li:nth-of-type(1) {
  @apply text-blue-500;
}
.drawer-list li:nth-of-type(2) {
  @apply mb-2 text-red-500;
}
</style>
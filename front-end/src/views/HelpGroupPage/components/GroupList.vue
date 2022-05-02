<template>
  <div class="flex flex-col">
    <!-- 标题 -->
    <div :class="styles['group-list-title-box']">
      <div class="flex justify-start items-center">
        <list-top theme="outline" size="20" fill="#333" />
        <label class="text-sm font-bold text-slate-500 ml-2">小组列表({{ state.groupList.length }})</label>
      </div>
      <div class="flex justify-between items-center">
        <div :class="styles['group-list-title-mark']">
          <span class="w-2.5 h-2.5 rounded-full bg-orange-500"></span>
          <span>我创建的</span>
        </div>
        <div :class="styles['group-list-title-mark']">
          <span class="w-2.5 h-2.5 rounded-full bg-green-600"></span>
          <span>我加入的</span>
        </div>
      </div>
    </div>
    <!-- 列表 -->
    <ul :class="styles['group-list-box']">
      <li :class="styles['group-list-item']" v-for="item in state.groupList" :key="item.id">
        <img
          :class="`${item.isActive ? 'border-blue-600' : ''}`"
          :src="`/api/download/team/${item.id}?${Math.ceil(Math.random() * 100000)}`"
          @click="changeActiveGroup(item.id)"
        />
        <span :class="`${item.isActive ? 'text-blue-600' : ''}`">{{ item.name }}</span>
        <div v-if="item.leader !== userInfo.id" :class="styles['mark-item']">
          <span
            class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-600 opacity-75"
          ></span>
        </div>
        <div v-if="item.leader === userInfo.id" :class="styles['mark-item2']">
          <span
            class="animate-ping absolute inline-flex h-full w-full rounded-full bg-orange-600 opacity-75"
          ></span>
        </div>
      </li>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { watch   } from 'vue'
import {ListTop} from '@icon-park/vue-next'
// ********************************** 导入自定义组件和hooks
import { useGroupList, } from '../model'
import {userInfoStorage} from '@/utils/storageUtil'
import { Student_Type } from '@/types';
// ********************************** 定义状态---组件引用
const { state, changeActiveGroup,initState } = useGroupList();
const userInfo:Student_Type = userInfoStorage.value;
const emit = defineEmits(['getActiveGroupInfo'])


// 监听当前改变的小组
watch(()=>state.activeGroupInfo,()=>{
  emit("getActiveGroupInfo",state.activeGroupInfo);
})

/**
 * 删除指定的小组
 */
const deleteGroupFromList = (groupId:string)=>{
  state.groupList = state.groupList.filter((item)=>{
    return item.id !== groupId;
  })
  // 重新指定当前小组
  changeActiveGroup(state.groupList[0].id);
}

defineExpose({deleteGroupFromList,initState})
</script>
<style module="styles">
.group-list-title-box {
  @apply w-full flex justify-between items-center py-4 px-4;
}
/* 标记 */
.group-list-title-mark {
  @apply flex items-center mx-2;
}
.group-list-title-mark span:nth-of-type(2) {
  @apply text-xs ml-1 text-slate-500;
}
.mark-item {
  @apply block w-3 h-3 absolute right-4 top-1 bg-green-600 rounded-full;
}
.mark-item2 {
  @apply block w-3 h-3 absolute right-4 top-1 bg-orange-600 rounded-full;
}
/* 小组列表 */
.group-list-box {
  @apply w-full flex overflow-hidden overflow-x-scroll space-x-2 px-2.5 pb-3 justify-start;
}
/* 小组item */
.group-list-item {
  @apply relative flex flex-col justify-between items-center text-sm w-20;
}
.group-list-item img {
  @apply w-14 h-14 rounded-full border-2  mb-1;
}
.group-list-item > span {
  @apply w-20 text-center text-sm truncate;
}
</style>
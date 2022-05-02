<template>
  <!-- 首页顶部栏 -->
  <div :class="`${styles['home-top-bar']} theme-dark`">
    <!-- 图标 -->
    <div :class="styles['top-bar-left']" @click="openSideBar">
      <img :src="store.state.activeGroupInfo.id?'/api/download/team/'+store.state.activeGroupInfo.id+'?'+Math.ceil(Math.random() * 100000):''" />
      <span class="ml-2 font-bold">{{store.state.activeGroupInfo.name}}</span>
      <span class="text-sm ml-1">(切换小组)</span>
      <!-- 标记 -->
      <div :class="styles['mark-item']">
        <span
          class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-600 opacity-75"
        ></span>
      </div>
    </div>
    <!-- 操作列表 -->
    <div :class="styles['top-bar-right']">
      <!-- 显示计划总数 -->
      <div :class="styles['top-bar-right-plan']" @click="()=>{toast.warning({message:'功能暂未开发'})}">
        <box theme="outline" size="25" fill="#ccc" />
        <div>11</div>
      </div>
      <!-- 更多操作 -->
      <more
        @click="() => { showNetTraffic = true }"
        class="ml-6 active:bg-slate-600 rounded-full"
        theme="outline"
        size="30"
        fill="#ccc"
      />
    </div>
  </div>
  <!-- 悬浮窗 -->
  <net-monitor :show="showNetTraffic" :click-hide="() => { showNetTraffic = false }"></net-monitor>
  <!-- 待办箱 -->
  <drawer :show="showDrawer" :click-hide="() => { showDrawer = false }">
    <div>这里将显示所有的计划---对应的小组---以及完成的情况</div>
  </drawer>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Box, More } from '@icon-park/vue-next'
// ***********************************************导入组件
import Drawer from '@/components/Drawer/Drawer.vue'
import {useToast} from '@/components/index'
import NetMonitor from './NetMonitor.vue'
import {store} from '../store'
// ***********************************************状态
const showDrawer = ref(false);
const showNetTraffic = ref(false);
const toast = useToast();
defineProps<{
  openSideBar: () => void
}>()
</script>
<style module="styles">
/* 首页顶部栏 */
.home-top-bar {
  @apply h-14 flex justify-between items-center;
}
/* 顶部栏左边 */
.top-bar-left {
  @apply relative flex justify-start items-center h-full px-3 active:bg-slate-500;
}
/* 标记 */
.mark-item {
  @apply block w-2.5 h-2.5 absolute left-9 top-3 bg-green-600 rounded-full;
}
.top-bar-left img {
  @apply h-8 w-8 rounded-full border-2;
}
/* 顶部栏右边 */
.top-bar-right {
  @apply flex-1 flex justify-end items-center pr-4;
}
.top-bar-right-plan {
  @apply relative active:bg-slate-500 rounded-full;
}
.top-bar-right-plan > div {
  font-size: 9px;
  font-weight: bold;
  @apply absolute w-4 h-4 bg-white bottom-0 right-0 rounded-full  text-black 
  flex justify-center items-center;
}
</style>
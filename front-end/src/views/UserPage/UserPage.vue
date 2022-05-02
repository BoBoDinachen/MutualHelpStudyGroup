<template>
  <!-- 外层容器 -->
  <div class="w-full h-full flex flex-col items-center justify-start">
    <!-- 个人信息盒子 -->
    <div
      :class="styles['profile-box']"
      @click="() => { delayedSwitchRoute(200, router, { name: 'profile', params: { uid: 233 } }) }"
    >
      <img :src="`/api/download/avatar/${userInfo.id}?${Math.ceil(Math.random() * 100000)}`" class="h-16 w-16 object-cover rounded-full" />
      <!-- 中间部分--用户名|学号|性别 -->
      <div :class="styles['profile-content-box']">
        <div class="flex items-center">
          <span class="font-bold text-lg tracking-wider">{{userInfo.userName}}</span>
          <female v-if="userInfo.sex === '女'" class="ml-2" theme="outline" size="20" fill="#E5989B" />
          <male v-if="userInfo.sex === '男'" class="ml-2" theme="outline" size="20" fill="#29b8db"/>
        </div>
        <span class="text-zinc-400 font-semibold text-sm">{{userInfo.email}}</span>
      </div>
      <right theme="outline" size="24" fill="#333" />
    </div>
    <!-- 查看我的计划 -->
    <div :class="`${styles['look-plan-box']} theme-dark`">
      <img src="@/assets/images/task.svg" class="h-16" />
      <div class="flex flex-1 ml-3 flex-col justify-between items-start">
        <span class="font-extrabold">我的学习计划 0/10</span>
        <span class="text-sm mt-2">已完成:0 待完成:10</span>
      </div>
      <div @click="handleLookPlan" class="rounded-2xl bg-orange-400 px-3 py-1 text-xs">查看计划</div>
    </div>
    <!-- 菜单栏 -->
    <ul class="w-full mt-4">
      <!-- 每个菜单子项列表 -->
      <li
        v-for="item in menuList"
        :class="styles['menu-item']"
        @click="() => { handleMenuItem(item[0]) }"
      >
        <menu-icon :icon="item[0]" :size="24" :color="'#333'"></menu-icon>
        <span :class="styles['menu-item-title']">{{ item[1] }}</span>
        <right theme="outline" size="24" fill="#333" />
      </li>
    </ul>
    <!-- 便利贴 -->
    <sticky-note></sticky-note>
  </div>
</template>
<script setup lang="ts">
import { ref,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Right, Female,Male } from '@icon-park/vue-next'
// *******************************************************
import MenuIcon from './components/MenuIcon.vue'
import StickyNote from './components/StickyNote.vue'
import {getUserInfo} from '@/services/api/user'
import { delayedSwitchRoute } from '@/utils/base'
import {useToast} from '@/components/index'
import {Student_Type} from '@/types/index'
import {userInfoStorage} from '@/utils/storageUtil'
// ****************************************************
const router = useRouter();
const toast = useToast();
const userInfo = ref<Student_Type>(userInfoStorage.value); // 从sessionStorage获取到的数据
onMounted(()=>{
})
// 菜单项数据
const menuList = ref<any>([['config', '设置'], ['people-safe', '账户与安全'], ['platte', '主题皮肤'], ['trophy', '我的荣誉']]);

/**
 * 点击每个菜单子项
 */
const handleMenuItem = (name: string) => {
  switch (name) {
    case "config":
      delayedSwitchRoute(200, router, { name: 'config', params: { uid: "233" } })
      break;
    case "people-safe":
      delayedSwitchRoute(200, router, { name: 'profile', params: { uid: "233" } })
      break;
    case "platte":
      delayedSwitchRoute(200, router, { name: 'theme', params: { uid: "233" } })
      break;
    case "trophy":
      delayedSwitchRoute(200, router, { name: 'honor', params: { uid: "233" } })
      break;
  }
}
/**
 * 点击查看计划
 */
const handleLookPlan = ()=>{
  toast.success({message:"查看计划"});
  router.push({name:"home"});
}
</script>
<style module="styles">
@import url(./index.css);
</style>
<template>
  <!-- 遮罩层 -->
  <div v-show="isVisible" :class="styles['shade-box']" @click="clickHide"></div>
  <div v-show="isVisible" class="absolute h-full z-30 w-8/12">
    <!-- 侧边栏 -->
    <div ref="sideRef" v-show="isVisible" :class="styles['side-bar-box']">
      <!-- 透明盒子 -->
      <div class="h-14 w-full" style="background-color: rgba(0, 0, 0, 0);"></div>
      <div class="bg-white flex-1 flex flex-col overflow-hidden">
        <!-- 个人资料----头像----信息 -->
        <div
          :class="styles['side-profile-box']"
          @click="() => { delayedSwitchRoute(200, router, { name: 'user', params: { uid: '233' } }) }"
        >
          <img class="w-12 h-12 object-cover rounded-md" :src="`/api/download/avatar/${userInfo.id}?${Math.ceil(Math.random() * 100000)}`" />
          <div class="ml-2 flex flex-col justify-between p-1">
            <span class="font-bold">{{ userInfo.userName }}</span>
            <span
              class="text-xs mt-1 font-bold text-slate-500"
            >学号: {{ userInfo.studentNumber === '' ? '暂未绑定' : userInfo.studentNumber }}</span>
          </div>
        </div>
        <!-- 我的小组---个人和账户---学习数据 -->
        <ul>
          <li
            :class="styles['side-menu-item']"
            @click="() => { delayedSwitchRoute(300, router, { name: 'group' }) }"
          >
            <every-user theme="outline" size="20" fill="#333" />
            <span>我的小组</span>
          </li>
          <li
            :class="styles['side-menu-item']"
            @click="() => { delayedSwitchRoute(300, router, { name: 'user', params: { uid: userInfo.id } }) }"
          >
            <permissions theme="outline" size="20" fill="#333" />
            <span>个人账户</span>
          </li>
          <li
            :class="styles['side-menu-item']"
            @click="() => { delayedSwitchRoute(300, router, { name: 'data' }) }"
          >
            <chart-pie-one theme="outline" size="20" fill="#333" />
            <span>学习数据</span>
          </li>
        </ul>
        <!-- 加入的小组 --- 新建小组 -->
        <div class="flex-1 flex flex-col justify-start mt-1 overflow-hidden">
          <div class="w-full text-sm bg-gray-100 pl-3 mt-3 text-slate-500 font-bold">小组计划</div>
          <div class="p-3 pr-5 flex justify-start items-center">
            <all-application theme="outline" size="20" fill="#333" />
            <span class="ml-1 flex-1 font-bold text-sm">所有计划</span>
            <div class="flex items-center" @click="handleRefresh">
              <span class="text-xs">刷新</span>
              <refresh-one class="ml-3" theme="outline" size="20" fill="#333" />
            </div>
          </div>
          <ul class="flex-1 overflow-hidden overflow-y-scroll">
            <li
              @click="() => { handleGroupItem(item) }"
              :class="`${styles['side-menu-item']} ${store.state.activeGroupInfo.id === item.id ? 'bg-slate-200' : ''}`"
              v-for="(item,index) in groupList"
              :key="item.id"
            >
              <img :src="`/api/download/team/${item.id}?${Math.ceil(Math.random() * 100000)}`" />
              <div class="ml-2 flex-1 flex items-center">
                <span class="text-sm font-bold">{{ item.name }}</span>
                <span class="text-gray-400" style="font-size: 10px;">({{ item.memberNumber }}人)</span>
              </div>
              <span class="text-xs">计划:{{ item.myPlans }}</span>
            </li>
            <li :class="styles['side-menu-item']">
              <add-one theme="outline" size="20" fill="#333" />
              <span @click="() => { delayedSwitchRoute(300, router, { name: 'group' }) }">新建小组</span>
            </li>
          </ul>
        </div>
        <!-- 已完成 --- 回收站 -->
        <div :class="`${styles['side-bottom-btns']} theme-dark`">
          <div :class="`${styles['side-bottom-btn']} bg-green-500`" @click="()=>{toast.warning({message:'功能暂未开发'})}">
            <done-all theme="outline" size="18" fill="#fff" />
            <span class="ml-1">已完成</span>
          </div>
          <div :class="`${styles['side-bottom-btn']} bg-orange-400`" @click="()=>{toast.warning({message:'功能暂未开发'})}">
            <delete-one theme="outline" size="18" fill="#fff" />
            <span class="ml-1">回收站</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { EveryUser, Permissions, ChartPieOne, RefreshOne, AddOne, AllApplication, DoneAll, DeleteOne } from '@icon-park/vue-next'
// **************************************************** 导入组件和函数
import { useToast } from '@/components/index'
import { delayedSwitchRoute } from '@/utils/base'
import { userInfoStorage } from '@/utils/storageUtil'
import { getGroupList } from '@/services/api/group'
import { getGroupPlanList, getMemberPlanList } from '@/services/api/plan'
import { Group_Type, Student_Type } from '@/types'
import { getMemberList } from '@/services/api/member'
import { store } from '../store'
// ****************************************************定义状态
const toast = useToast();
const userInfo: Student_Type = userInfoStorage.value; // 当前用户的信息
const groupList = ref<Group_Type[]>([]); // 小组列表
const isVisible = ref(false); // 显示 / 隐藏
const sideRef = ref<any>(null);
const router = useRouter();
const props = defineProps<{
  show: boolean,
  clickHide: () => void
}>()
const sideHeight = computed(() => {
  return sideRef.value.scrollHeight
})

// 第一次加载
onMounted(() => {
  initGroupList(); // 初始化小组列表
})
// 初始化小组列表
function initGroupList() {
  groupList.value = []; // 先清空之前的
  getGroupList(userInfo.id).then((res: any) => {
    console.log(res);
    if (res.data) {
      res.data.groups.forEach(async (item: any, index: number) => {
        // 获取小组的计划数量和自己的计划数量
        const groupPlanList: any = await getGroupPlanList(item.id);
        const memberPlanList: any = await getMemberPlanList(item.id, userInfo.id);
        if (groupPlanList.data && memberPlanList.data) {
          item['myPlans'] = "(" + memberPlanList.data?.planList.length + "/" + groupPlanList.data?.planList.length + ")"
        } else if (groupPlanList.data && !memberPlanList.data) {
          item['myPlans'] = "(" + 0 + "/" + groupPlanList.data?.planList.length + ")"
        } else {
          item['myPlans'] = "(" + 0 + "/" + 0 + ")"
        }
        // 获取成员的数量
        let memberListData: any = await getMemberList(item.id)
        if (memberListData.data) {
          item['memberNumber'] = memberListData.data.memberList.length;
        }
        groupList.value.push(item);
        store.state.activeGroupInfo = groupList.value[0];
      });
    }
  })
}
/**
 * 点击刷新
 */
const handleRefresh = () => {
  initGroupList();
  toast.success({ message: "刷新成功" });
}
/**
 * 监听props值的变化
 */
watch(() => props.show, (value) => {
  // 显示模态框--缩放
  if (value) {
    isVisible.value = true;
    setTimeout(() => {
      sideRef.value.style.transform = `translateX(0)`;
    }, 80)
  } else {
    // 隐藏模态框--平移
    sideRef.value.style.transform = `translateX(-${sideHeight.value}px)`;
    setTimeout(() => {
      isVisible.value = false;
    }, 500);
  }
})
/**
 * 点击每个小组项，改变当前选中小组
 */
const handleGroupItem = (groupInfo: Group_Type) => {
  store.setActiveGroupInfo(groupInfo);
  props.clickHide();
}
</script>
<style module="styles">
/* 遮罩层 */
.shade-box {
  /* background-color: rgba(80, 81, 79, 0.6); */
  @apply z-20 absolute h-screen w-screen;
}
/* 侧边栏盒子 */
.side-bar-box {
  box-shadow: 0 70px 50px -12px rgb(0 0 0 / 0.25);
  @apply w-full h-full z-40
  transition-transform duration-500 ease-in-out
  overflow-y-hidden
  flex flex-col justify-start;
}
/* 显示个人信息 */
.side-profile-box {
  @apply w-full flex items-center px-3 py-3 active:bg-slate-300;
}
/* 顶部的菜单栏 */
.side-menu-item {
  @apply p-3 pr-5 flex justify-start items-center
  active:bg-gray-200;
}
.side-menu-item span:nth-of-type(2) {
  @apply ml-2 text-sm font-bold;
}
.side-menu-item img {
  @apply w-9 h-9 rounded-full;
}
.side-bottom-btns {
  @apply flex justify-end p-3;
}
.side-bottom-btn {
  @apply flex justify-center items-center ml-3 text-sm  px-2 py-1 rounded-sm;
}
</style>
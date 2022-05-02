<template>
  <!-- 遮罩层 -->
  <div v-show="isVisible" :class="styles['shade-box']" @click="clickHide"></div>
  <div ref="boxRef" v-show="isVisible" :class="styles['window-box']">
    <!-- 标题栏 -->
    <div :class="styles['window-box-bar']">
      <span class="text-base font-bold">更多操作</span>
      <close @click="clickHide" theme="outline" size="17" fill="#333" />
    </div>
    <!-- 内容区域 -->
    <div class="flex-1 flex flex-col justify-start">
      <span class="ml-3 mt-3 text-sm font-bold">当前小组： {{store.state.activeGroupInfo.name}}</span>
      <!-- 统计完成和未完成面板 -->
      <div :class="styles['window-box-info']">
        <span>已完成 {{store.getFinishNumber()}}</span>
        <span>未完成 {{store.getUndoneNumber()}}</span>
      </div>
      <!-- 选项列表 -->
      <ul class="pb-1">
        <li @click="()=>{handleMenuItem(item)}" :class="styles['menu-item']" v-for="item in menuList">
          <preview-open theme="outline" size="18" fill="#333" />
          <span>{{ item.title }}</span>
          <round v-show="!item.active" theme="outline" size="17" fill="#333" />
          <check-one v-show="item.active" theme="outline" size="17" fill="#22c55e" />
        </li>
        <li :class="`${styles['menu-item']} border-t`" @click="()=>{toast.warning({message:'功能暂未开发'})}">
          <clear theme="outline" size="17" fill="#ef4444" />
          <span>清空已完成</span>
        </li>
        <li
          :class="styles['menu-item']"
          @click="() => { delayedSwitchRoute(200, router, { name: 'config', params: { uid: '233' } }) }"
        >
          <setting-two theme="outline" size="17" fill="#1e6fff" />
          <span>设置</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { Close, PreviewOpen, Round, CheckOne, Clear, SettingTwo } from '@icon-park/vue-next'
// *******************************************************导入组件和函数
import { delayedSwitchRoute } from '@/utils/base'
import {store} from '../store'
import {useToast} from '@/components/index'
// ********************************************************定义状态
const router = useRouter();
const isVisible = ref(false);
const boxRef = ref<any>(null);
const toast = useToast();

const menuList = ref([
  { title: "所有", name: "all", active: true },
  { title: "已完成", name: "finish", active: false },
  { title: "未完成", name: "unfinished", active: false },
])
// ************************************************************props选项
const props = defineProps<{
  show: boolean,
  clickHide: () => void
}>()
/**
 * 监听props值的变化
 */
watch(() => props.show, (value) => {
  // 显示模态框--缩放
  if (value) {
    isVisible.value = true;
    setTimeout(() => {
      boxRef.value.style.opacity = 1;
    }, 100)
  } else {
    // 隐藏模态框--缩放
    boxRef.value.style.opacity = 0;
    setTimeout(() => {
      isVisible.value = false;
    }, 300);
  }
})
/**
 * 点击每个菜单项
 */
const handleMenuItem = (menu:{name:string,active:boolean})=>{
  switch(menu.name){
    case "all":
      menuList.value.forEach((item)=>{
        item.active = false;
      })
      menu.active = true;
      store.setActivePlanStatus('all');
      break;
    case "finish":
      menuList.value.forEach((item)=>{
        item.active = false;
      })
      menu.active = true;
      store.setActivePlanStatus('finish');
      break;
    case "unfinished":
      menuList.value.forEach((item)=>{
        item.active = false;
      })
      menu.active = true;
      store.setActivePlanStatus('undone');
      break;
  }
}
</script>
<style module="styles">
.shade-box {
  /* background-color: rgba(80, 81, 79, 0.6); */
  @apply z-20 absolute h-screen w-full left-0 top-0;
}
.window-box {
  box-shadow: 0 25px 50px -12px rgb(0 0 0 / 0.25);
  @apply z-50 absolute right-3 top-3 w-64 bg-white rounded-sm
  flex flex-col justify-start 
  transition-opacity duration-300 ease-in-out;
}
/* 面板标题栏 */
.window-box-bar {
  @apply flex justify-between items-center
  px-3 py-2 bg-gray-200 rounded-t-sm;
}
/* 统计面板 */
.window-box-info {
  @apply w-full p-3 flex justify-between items-center;
}
.window-box-info span {
  @apply flex-1 h-10 rounded-sm text-white text-base
  flex justify-center items-center;
}
.window-box-info span:nth-of-type(1) {
  @apply bg-green-500 mr-2;
}
.window-box-info span:nth-of-type(2) {
  @apply bg-orange-400 ml-2;
}
/* 菜单选项 */
.menu-item {
  @apply px-3 py-2 flex justify-between items-center text-sm
  active:bg-slate-200;
}
.menu-item span:nth-of-type(2) {
  @apply flex-1 ml-2;
}
</style>
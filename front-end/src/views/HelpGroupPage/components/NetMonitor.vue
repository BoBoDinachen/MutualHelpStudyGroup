<template>
  <!-- 遮罩层 -->
  <div v-show="isVisible" :class="styles['shade-box']" @click="clickHide"></div>
  <div ref="boxRef" v-show="isVisible" :class="styles['window-box']">
    <!-- 标题栏 -->
    <div :class="styles['window-box-bar']">
      <span class="text-base text-slate-700 font-bold">更多操作</span>
      <close @click="clickHide" theme="outline" size="17" fill="#333" />
    </div>
    <!-- 内容区域 -->
    <div class="flex-1 flex flex-col justify-start py-2">
      <ul>
        <li :class="styles['menu-item']" @click="openModal">
          <newlybuild theme="outline" size="20" fill="#02c39a" />
          <span class="text-slate-700">创建小组</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch, inject } from 'vue'
import { Close, Newlybuild } from '@icon-park/vue-next'
// *******************************************************导入组件和函数
// ********************************************************定义状态 / 接收父组件的共享状态
const isVisible = ref(false);
const boxRef = ref<any>(null)
const openCreateModal = inject("openCreateModal") as () => void;
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
    display();
  } else {
    // 隐藏模态框--缩放
    hidden();
  }
})
/**
 * 控制内部的显示和隐藏
 */
const display = () => {
  isVisible.value = true;
  setTimeout(() => {
    boxRef.value.style.opacity = 1;
  }, 100)
}
const hidden = () => {
  boxRef.value.style.opacity = 0;
  setTimeout(() => {
    isVisible.value = false;
  }, 300);
}
/**
 * 打开模态框
 */
const openModal = ()=>{
  props.clickHide();
  openCreateModal();
}
</script>
<style module="styles">
.shade-box {
  /* background-color: rgba(80, 81, 79, 0.6); */
  @apply z-20 absolute h-screen w-screen left-0 top-0;
}
.window-box {
  box-shadow: 0 25px 50px -12px rgb(0 0 0 / 0.25);
  @apply z-50 absolute right-3 top-3 w-52 bg-white rounded-sm
  flex flex-col justify-start 
  transition-opacity duration-300 ease-in-out;
}
/* 面板标题栏 */
.window-box-bar {
  @apply flex justify-between items-center
  px-3 py-2 bg-gray-200 rounded-t-sm;
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
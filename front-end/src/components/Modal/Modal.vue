<template>
  <div v-show="isVisible" class="fixed top-0 left-0 h-screen w-screen z-10">
    <!--  遮罩层 -->
    <div :class="styles['shade-box']" @click="clickHide"></div>
    <!-- 中间区域 -->
    <div ref="modalRef" :class="styles['modal-box']">
      <div :class="styles['modal-title-bar']">
        <span class="font-bold text-base">{{title}}</span>
        <close @click="clickHide" theme="outline" size="18" fill="#333" />
      </div>
      <slot>这里放置模态框的HTML模板</slot>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'

import { Close } from '@icon-park/vue-next'

const props = defineProps<{
  title:string,
  show: boolean,
  clickHide: () => void
}>()

// 控制模态框的显示和隐藏
const isVisible = ref(false);
const modalRef = ref<any>(null);

/**
 * 监听props值的变化
 */
watch(() => props.show, (value) => {
  // 显示模态框--缩放
  if (value) {
    isVisible.value = true;
    setTimeout(() => {
      modalRef.value.style.transform = "scale(1)";
    }, 100)
  } else {
    // 隐藏模态框--缩放
    modalRef.value.style.transform = "scale(0)";
    setTimeout(() => {
      isVisible.value = false;
    }, 300);
  }
})

</script>
<style module="styles">
.shade-box {
  background-color: rgba(80, 81, 79, 0.6);
  @apply z-20 absolute h-screen w-full;
}
/* 模态框的主页区域 */
.modal-box {
  box-shadow: 0 0 8px 0.1px lightslategray;
  @apply z-30 absolute m-auto left-0 right-0 top-0 bottom-0 w-96 h-fit p-3 bg-white
  rounded-sm flex flex-col justify-start items-center
  transition-transform duration-300 ease-in-out
}
/* 模态框的标题栏 */
.modal-title-bar {
  @apply z-40 w-full flex justify-between items-center
}
/* 模态框的关闭按钮 */
.modal-icon-close {
}
</style>
<template>
  <div v-show="isShow" class="absolute h-screen w-full z-10 top-0 left-0">
    <!--  遮罩层 -->
    <div v-show="isShow" :class="styles['shade-box']" @click="clickHide"></div>
    <!-- 上滑块 -->
    <div v-show="isShow" class="absolute h-screen w-full overflow-hidden">
      <div ref="slideRef" :class="styles['slide-box']">
        <slot>这里可以放置HTML模板</slot>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref, computed, onMounted, watch, nextTick } from 'vue'
const isShow = ref(false);
const slideRef = ref<any>(null);
const props = defineProps<{
  show: boolean,
  clickHide: () => void,
}>()

/**
 * 计算滑块的高度
 */
const slideHeight = computed(() => {
  return slideRef.value.scrollHeight
})

// 监听props的值，然后隐藏和显示抽屉
watch(() => props.show, (value) => {
  // console.log(value);
  nextTick(() => {
    if (value) {
      isShow.value = true;
      setTimeout(() => {
        slideRef.value.style.bottom = 0 + "px"
      }, 100)
    } else {
      slideRef.value.style.bottom = -slideHeight.value + "px"
      setTimeout(() => {
        isShow.value = false;
      }, 300);
    }
  })

})

</script>
<style module="styles">
.shade-box {
  background-color: rgba(80, 81, 79, 0.6);
  @apply z-40 absolute h-screen w-full;
}
.slide-box {
  /* bottom: v-bind("`-${slideHeight}px`"); */
  @apply absolute z-50 bottom-0 w-full bg-white pt-2
    transition-all duration-300 ease-in-out
    flex justify-center items-center
}
</style>
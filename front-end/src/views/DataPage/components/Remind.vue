<template>
  <div :class="`${styles['remind-box']} theme-dark`">
    <img class="w-20" src="@/assets/images/analyse.png" alt="">
    <div :class="styles['right-info-box']">
      <span>当前总计划:<b class="ml-1 mr-4">28/100</b>剩余:<b class="ml-1">72</b></span>
      <span class="mt-1">过去几天没有达成的计划,得加油了</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import {MarketAnalysis} from '@icon-park/vue-next'
</script>
<style module="styles">
.remind-box{
  @apply w-full p-2 flex justify-start rounded-md shadow-md
}
/* 右边的信息 */
.right-info-box{
  @apply flex flex-col justify-center text-sm
}
</style>
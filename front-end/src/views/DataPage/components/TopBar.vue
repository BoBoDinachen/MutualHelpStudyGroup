<template>
  <div :class="`${styles['top-bar-box']}`">
    <chart-pie-one theme="outline" size="24" fill="#333"/>
    <span class="ml-2">学习复盘</span>
  </div>
</template>
<script setup lang="ts">
import {ChartPieOne} from '@icon-park/vue-next'
</script>
<style module="styles">
.top-bar-box{
  @apply px-3 py-3 flex items-center shadow-lg
}
</style>
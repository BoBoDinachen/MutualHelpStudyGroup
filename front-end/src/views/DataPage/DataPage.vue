<template>
  <div class="h-full overflow-hidden flex flex-col justify-between">
    <!-- 顶部栏 -->
    <top-bar></top-bar>
    <!-- 内容区域 -->
    <div class="flex-1 overflow-hidden overflow-y-scroll p-3">
      <!-- 提醒框 -->
      <remind></remind>
      <!-- 统计面板 -->
      <panel-group></panel-group>
      <!-- 数据图表 -->
      <data-chart></data-chart>
    </div>
  </div>
</template>
<script setup lang="ts">
  import TopBar from './components/TopBar.vue'
  import Remind from './components/Remind.vue'
  import PanelGroup from './components/PanelGroup.vue'
  import DataChart from './components/DataChart.vue'
</script>
<style>
</style>
<template>
  <div class="w-full flex justify-between">
    <div :class="`${styles['panel-item']} bg-violet-400`">
      <div :class="styles['panel-title']">
        <data-screen theme="outline" size="20" fill="#ffff"/>
        <label class="ml-2">完成计划总数</label>
      </div>
      <div :class="styles['bottom-info-box']">
        <span>28</span>
        <div>
          <span>今日完成</span>
          <span>+0</span>
        </div>
      </div>
    </div>
    <div :class="`${styles['panel-item']} bg-orange-400 ml-3`">
       <div :class="styles['panel-title']">
        <data-screen theme="outline" size="20" fill="#ffff"/>
        <label class="ml-2">完成子任务总量</label>
      </div>
      <div :class="styles['bottom-info-box']">
        <span>108</span>
        <div>
          <span>今日完成</span>
          <span>+0</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import {DataScreen} from '@icon-park/vue-next'
</script>
<style module="styles">
/* 每个面板 */
.panel-item {
  @apply text-white w-3/6 mt-3  rounded-md p-2 shadow-md
  flex flex-col justify-between 
}
/* 面板标题 */
.panel-title{
  @apply flex justify-start items-center text-sm font-bold
}
/* 下方的信息 */
.bottom-info-box{
  @apply mt-5 flex justify-start items-center
}
.bottom-info-box > span{
  @apply text-2xl mr-2
}
.bottom-info-box > div{
  @apply text-xs flex flex-col
}
</style>
<template>
  <div v-show="props.show" :class="styles['spin-box']">
    <img class="w-10 h-10 animate-spin" src="@/assets/images/loading-icon.svg" alt="">
    <span>{{props.description === undefined?"正在加载中...":props.description}}</span>
  </div>
</template>
<script setup lang="ts">
import {ref,computed} from 'vue'
const props =  defineProps<{
  description?:string,
  size?: "samll"|"medium"|"large",
  show: boolean,
}>()

const spinSize = computed(()=>{
  switch(props.size){
    case "samll":
      return
    case "medium":
      return
    case "large":
      return
  }
})
</script>
<style module="styles">
.spin-box {
  @apply absolute m-auto left-0 right-0 top-0 bottom-0  w-fit h-24 p-3
   bg-black bg-opacity-50 text-white font-bold text-sm
   rounded-md space-y-2
   flex flex-col justify-center items-center
}
/* 遮罩层 */
.spin-box::before {
  content: "";
  @apply fixed z-50 w-screen h-screen top-0 left-0
}
</style>
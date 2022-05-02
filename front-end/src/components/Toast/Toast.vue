<template>
  <div :class="styles['toast-box']">
    <check-one v-if="type==='success'" theme="outline" size="24" fill="#22c55e"/>
    <close-one v-if="type==='error'" theme="outline" size="24" fill="#e98b8b"/>
    <attention v-if="type==='warning'" theme="outline" size="24" fill="#fb923c"/>
    <info v-if="type==='info'" theme="outline" size="24" fill="#1e6eff"/>
    <div class="ml-2 text-sm">{{message}}</div>
  </div>
</template>
<script setup lang="ts">
import { onMounted } from 'vue'
import {Info,Attention,CloseOne,CheckOne} from '@icon-park/vue-next'
onMounted(()=>{
  
})

const props = defineProps<{
  type: "info" | "success" | "warning" | "error",
  message: string
}>()
</script>
<style module="styles">
.toast-box {
  box-shadow: 0 0 2px 0.1px lightslategray;
  @apply z-50 absolute m-auto left-0 right-0 top-5 h-10 w-fit px-5 py-2 rounded-3xl bg-white
   flex justify-start items-center font-bold
}
</style>
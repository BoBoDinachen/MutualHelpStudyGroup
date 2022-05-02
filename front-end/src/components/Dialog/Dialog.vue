<template>
  <div :class="styles['dialog-wall']">
    <div :class="styles['dialog-box']">
      <div class="flex justify-start items-center">
        <tips-one v-if="type === 'info'" theme="outline" size="24" fill="#1e6eff" />
        <attention v-if="type === 'warning'" theme="outline" size="24" fill="#fb923c" />
        <success v-if="type === 'success'" theme="outline" size="24" fill="#22c55e" />
        <error v-if="type === 'error'" theme="outline" size="24" fill="#e98b8b" />
        <span class="ml-2 text-lg">{{ title }}</span>
      </div>
      <div class="flex-1 mt-3 text-sm">{{ content }}</div>
      <div class="flex justify-end items-center">
        <a
          v-if="type === 'warning' || type === 'info' || type==='success'"
          :class="`${styles['dialog-box-btn']} bg-slate-500`"
          @click="hidden"
        >取消</a>
        <a :class="`${styles['dialog-box-btn']} ${btnColor}`" @click="onConfirmClick">确定</a>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Attention, Success, Error, TipsOne } from '@icon-park/vue-next'

const props = defineProps<{
  type: "info" | "warning" | "success" | "error",
  title: string,
  content: string,
  hidden: any,
  onConfirmClick: any
}>()

const btnColor = ref(
  props.type === 'info' ?
    'bg-blue-500' : (props.type === 'warning' ?
      'bg-orange-400' : (props.type === 'success' ?
        'bg-green-500' : 'bg-red-400'))
)
</script>
<style module="styles">
.dialog-wall {
  background-color: rgba(80, 81, 79, 0.6);
  @apply absolute z-50 top-0 w-screen h-screen flex justify-center items-center;
}
.dialog-box {
  box-shadow: 0 0 10px 0.1px lightslategray;
  @apply px-5 py-5 w-80 h-32 bg-white rounded-sm flex flex-col justify-between;
}
.dialog-box-btn {
  @apply text-white rounded-sm px-4 py-1 text-sm ml-3 mt-2
}
</style>
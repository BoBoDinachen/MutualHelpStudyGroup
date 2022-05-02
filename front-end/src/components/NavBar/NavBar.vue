<template>
  <div :class="styles['nav-bar-box']">
    <left class="absolute" theme="outline" size="26" fill="#333" @click="goBack"/>
    <span class=" text-lg  flex-1 w-full text-center font-semibold">{{title}}</span>
  </div>
</template>
<script lang="ts" setup>
import {Left} from '@icon-park/vue-next'
import {useRouter} from 'vue-router'

const router = useRouter();
// 后退
function goBack(){
  router.go(-1);
}
defineProps<{
  title: string
}>()
</script>
<style module="styles">
.nav-bar-box{
  box-shadow: 0 2px 10px 0px lavender;
  @apply flex relative items-center w-full pb-3 pt-4 px-3
}
</style>
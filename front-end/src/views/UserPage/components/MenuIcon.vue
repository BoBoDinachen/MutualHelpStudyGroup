<template>
  <config v-if="icon === 'config'" theme="outline" :size="size" :fill="color" />
  <people-safe v-if="icon === 'people-safe'" theme="outline" size="24" :fill="color" />
  <platte v-if="icon === 'platte'" theme="outline" :size="size" :fill="color" />
  <trophy v-if="icon === 'trophy'" theme="outline" :size="size" :fill="color" />
</template>
<script setup lang="ts">
import { Config, PeopleSafe, Platte, Trophy } from '@icon-park/vue-next'

/**
 * 定义属性
 */
defineProps<{
  icon: string,
  size: number,
  color: string
}>()
</script>
<style>
</style>
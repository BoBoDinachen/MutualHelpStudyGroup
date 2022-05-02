<template>
  <div class="flex flex-col justify-between h-screen">
    <div class="flex-1 overflow-y-scroll">
      <!-- 缓存全部路由 -->
      <!-- <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>-->
      <!-- 缓存指定路由 -->
      <router-view v-if="route.meta.keepAlive" v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
      <router-view v-if="!route.meta.keepAlive"></router-view>
      <!-- 不缓存 -->
      <!-- <router-view></router-view> -->
    </div>
    <menu-bar></menu-bar>
  </div>
</template>
<script setup lang="ts">
import { RouterView, useRoute } from 'vue-router'
import MenuBar from '@/layouts/MenuBar/MenuBar.vue'
const route = useRoute();
</script>
<style>
</style>
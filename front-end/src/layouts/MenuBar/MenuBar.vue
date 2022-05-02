<template>
  <ul class="flex justify-between items-center">
    <li class="nav-item">
      <div class="btn" @click="() => { handleActive(0) }">
        <home-two theme="outline" size="22" :fill="activeMenu === 0 ? '#70C1B3' : '#333'" />
      </div>
    </li>
    <li class="nav-item" @click="() => { handleActive(1) }">
      <div class="btn">
        <every-user theme="outline" size="22" :fill="activeMenu === 1 ? '#70C1B3' : '#333'" />
      </div>
    </li>
    <li class="nav-item" @click="() => { handleActive(2) }">
      <div class="btn">
        <market-analysis theme="outline" size="22" :fill="activeMenu === 2 ? '#70C1B3' : '#333'" />
      </div>
    </li>
    <li class="nav-item" @click="() => { handleActive(3) }">
      <div class="btn">
        <user theme="outline" size="22" :fill="activeMenu === 3 ? '#70C1B3' : '#333'" />
      </div>
    </li>
  </ul>
</template>
<script setup lang="ts">
import { HomeTwo, EveryUser, MarketAnalysis, User } from '@icon-park/vue-next'
import { useRouter, useRoute } from 'vue-router'
import { ref, watch, onMounted } from 'vue'
// ********************************************
import { userInfoStorage } from '@/utils/storageUtil'
// **************************************************
const activeMenu = ref(0);
const router = useRouter();
const route = useRoute();

/**
 * 加载时判断路由
 */
onMounted(() => {
  switch (route.path.split("/")[2]) {
    case "home":
      activeMenu.value = 0;
      break;
    case "group":
      activeMenu.value = 1;
      break;
    case "data":
      activeMenu.value = 2;
      break;
    case "user":
      activeMenu.value = 3;
      break;
  }
})
/**
 * 监听当前路由，改变当前活动菜单
 */
watch(() => route.path, (value, preValue) => {
  switch (value.split("/")[2]) {
    case "home":
      activeMenu.value = 0;
      break;
    case "group":
      activeMenu.value = 1;
      break;
    case "data":
      activeMenu.value = 2;
      break;
    case "user":
      activeMenu.value = 3;
      break;
  }
})

function handleActive(index: number): void {
  switch (index) {
    case 0:
      router.push({ path: "/app/home" });
      break;
    case 1:
      router.push({ path: "/app/group" });
      break;
    case 2:
      router.push({ path: "/app/data" });
      break;
    case 3:
      router.push({ name: "user", params: { uid: userInfoStorage.value.id } });
      break;
  }
}
</script>
<style scoped>
.nav-item {
  @apply overflow-hidden flex-1;
}
.btn {
  @apply relative flex justify-center items-center py-5 px-5;
}
.btn::after {
  content: "";
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: radial-gradient(circle, #2b2b2b 10%, transparent 10%);
  transform: scale(10);
  opacity: 0;
  transition: all 0.5s;
}
.btn:active::after {
  transform: scale(0);
  opacity: 0.5;
  transition: 0s;
}
</style>
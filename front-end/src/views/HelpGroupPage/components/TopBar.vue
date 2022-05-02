<template>
  <div :class="`${styles['top-bar']} theme-dark`">
    <span class="font-bold w-20">我的小组</span>
    <div :class="styles['search-box']">
      <input type="text" placeholder="搜索小组名" @input="(e) => { handleSearch(e) }" />
      <search theme="outline" size="18" fill="#333" />
    </div>
    <more
      @click="() => { showMonitor = true }"
      class="w-20 flex justify-end"
      theme="outline"
      size="26"
      fill="#fff"
    />
    <!-- 操作悬浮框 -->
    <net-monitor :show="showMonitor" :click-hide="() => { showMonitor = false }"></net-monitor>
  </div>
  <!-- 搜索结果 -->
  <search-result
    :groupList="groupList"
    :show="showSearchBox"
    :clickHide="() => { showSearchBox = false }"
  ></search-result>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Search, More } from '@icon-park/vue-next'
import { debouncedWatch } from '@vueuse/core'
// **************************************************** 导入组件和hooks
import SearchResult from './SearchResult.vue'
import NetMonitor from './NetMonitor.vue'
import { getGroupListByName,findMemberFormGroup } from '@/services/api/group'
import { getUserInfo } from '@/services/api/user'
import { getMemberList } from '@/services/api/member'
import { Group_Type } from '@/types'
import {userInfoStorage} from '@/utils/storageUtil'
// **************************************************** 定义状态
const showMonitor = ref<boolean>(false);
const inputValue = ref();
const showSearchBox = ref(false); // 显示搜索结果框
const groupList = ref<any[]>([]);
const userInfo = userInfoStorage.value;
/**
 * 触发搜索
 */
const handleSearch = (e: any) => {
  showSearchBox.value = true; // 显示搜索结果框
  inputValue.value = e.target.value;
}

// 去获取搜索的小组列表
function getGroupList() {
  groupList.value = []; // 每次先清空
  if (inputValue.value !== "") {
    getGroupListByName(inputValue.value).then((res: any) => {
      if (res.data) {
        res.data.groups.forEach(async (item: any, index: number) => {
          // 判断当前用户是否存在小组中
          let exist:any =  await findMemberFormGroup(item.id,userInfo.id);
          item['isJoin'] = exist.status;
          // 获取当前组长的信息
          let leaderInfo: any = await getUserInfo(item.leader);
          item['leaderName'] = leaderInfo.data.student.userName;
          // 获取成员的数量
          let memberListData: any = await getMemberList(item.id)
          if (memberListData.data) {
            item['memberNumber'] = memberListData.data.memberList.length;
          }
          groupList.value.push(item);
        });

      } else {
        groupList.value = [];
      }
      console.log(res);
    })
  }
}
// 防抖函数
debouncedWatch(inputValue, getGroupList, {
  debounce: 500,
})
</script>
<style module="styles">
.top-bar {
  @apply flex justify-between items-center px-3 py-3;
}
/* 搜索框 */
.search-box {
  @apply flex-1 flex justify-start items-center;
}
.search-box input {
  @apply text-slate-800 h-8 px-3 text-sm rounded-l-2xl flex-1;
}
.search-box span {
  @apply h-8 bg-white flex items-center justify-center rounded-r-2xl pr-2;
}
</style>
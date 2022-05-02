<template>
  <div class="w-full h-screen border-b">
    <div class="w-full flex items-center p-3">
      <div class="flex items-center ml-3" @click="props.clickHide">
        <close theme="outline" size="20" fill="#333" />
        <span class="ml-1 fontb">关闭</span>
      </div>
      <label class="flex-1 text-right font-bold text-lg ml-2 ">汇报列表</label>
    </div>
    <ul class="flex flex-col flex-1 overflow-hidden overflow-y-scroll space-y-2">
      <li :class="styles['issue-item-box']" v-for="item in issueList" :key="item.id">
        <img :src="`/api/download/avatar/` + item.studentId" />
        <div class="flex-1 flex flex-col justify-start ml-2">
          <span class="font-bold">计划：{{ item.plan?.planTitle }}</span>
          <span>问题描述：{{ item.issue }}</span>
          <span class="text-sm text-slate-600">
            {{ item.student?.userName }}
            <b class="ml-2 text-xs">{{ item.student?.sex }}</b>
          </span>
          <span class="text-sm text-slate-600">汇报时间：{{ item.reportTime }}</span>
        </div>
      </li>
    </ul>
     <!-- 数据为空时 -->
    <div
      v-show="issueList.length === 0"
      class="absolute top-1/4 w-full flex flex-col items-center justify-center"
    >
      <img src="@/assets/images/data-null.jpg" />
      <span class="font-bold mt-3 text-lg">暂时没有成员提交问题~</span>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'
import { getProblemList } from '@/services/api/plan'
import { getUserInfo } from '@/services/api/user'
import { getPlanInfo } from '@/services/api/plan'
import { Problem_Type } from '@/types';
import {Close} from '@icon-park/vue-next'

const props = defineProps<{
  activeGroupId: string,
  clickHide: ()=>void
}>()
const issueList = ref<Problem_Type[]>([]); // 问题列表
// 监听当前改变的小组信息
watch(() => props.activeGroupId, () => {
  issueList.value = [];
  getProblemList(props.activeGroupId).then((res: any) => {
    if (res.data) {
      res.data.conditionList.forEach((item: Problem_Type) => {
        // 先获取用户信息
        getUserInfo(item.studentId + '').then((res1: any) => {
          item.student = res1.data.student;
          // 获取计划进行
          getPlanInfo(item.planId + '').then((res2: any) => {
            item.plan = res2.data.plan;
            issueList.value.push(item);
          })
        })
      });
    }
  })
})

</script>
<style module="styles">
/* 列表子项 */
.issue-item-box {
  @apply flex justify-start items-start w-full p-2 mt-2 px-4;
}
.issue-item-box img {
  @apply w-12 h-12 object-cover rounded-full;
}
</style>
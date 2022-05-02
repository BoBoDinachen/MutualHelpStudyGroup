<template>
  <div v-show="isShow">
    <!-- 遮罩层 -->
    <div v-show="isShow" :class="styles['side-box']" @click="props.clickHide"></div>
    <!-- 结果框 -->
    <div v-show="isShow" :class="styles['search-result-box']" ref="slideRef">
      <!-- 顶部栏 -->
      <div class="flex justify-between items-center px-3">
        <div class="flex items-center">
          <find theme="outline" size="20" fill="#333" />
          <span class="ml-2 text-sm">搜索结果</span>
          <span class="ml-1 text-sm">{{ props.groupList.length }}</span>
        </div>
        <close theme="outline" size="20" fill="#333" @click="props.clickHide" />
      </div>
      <div class="h-1 mt-2 bg-slate-300"></div>
      <!-- 结果为空 -->
      <div v-show="props.groupList.length === 0" class="flex flex-col items-center">
        <img class="w-32 rounded-md" src="@/assets/images/isNull.jpg" />
        <span class="text-slate-500 mt-1">没有搜索到结果...</span>
      </div>
      <!-- 小组列表 -->
      <ul :class="styles['search-list']">
        <li :class="styles['group-item']" v-for="item in props.groupList" :key="item.id">
          <img class="w-11 h-11 object-fill rounded-md" :src="`/api/download/team/${item.id}`" />
          <!-- 右边的信息 -->
          <div class="flex flex-col ml-2 flex-1 overflow-hidden">
            <div class="w-56 flex items-center">
              <span class="font-bold text-sm truncate w-fit">{{ item.name }}</span>
              <span class="text-sm ml-1">({{ item.memberNumber }}/7)</span>
            </div>
            <span class="text-xs text-slate-600">小组长：{{ item.leaderName }}</span>
            <div class="text-xs text-slate-600 text-ellipsis truncate">简介：{{ item.introduce }}</div>
          </div>
          <a v-if="!item.isJoin" :class="styles['btn-1']" @click="() => { handleJoinGroup(item.id) }">加入小组</a>
          <a :class="styles['btn-2']" v-if="item.memberNumber === 7">成员已满</a>
          <a :class="styles['btn-2']" v-if="item.isJoin">已加入</a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script setup lang="ts">
import { watch, ref, computed,inject } from 'vue'
import { Close, Find } from '@icon-park/vue-next'
// ***************************************************
import { Group_Type } from '@/types';
import { userInfoStorage } from '@/utils/storageUtil'
import { useDialog,useToast } from '@/components/index'
import { joinGroup } from '@/services/api/member'
// ******************************************************
const addGroupFromList = inject('addGroupFromList') as (groupId:string)=> void;
const isShow = ref(false);
const slideRef = ref<any>(null);
const userInfo = userInfoStorage.value;
const dialog = useDialog();
const toast = useToast();
/**
 * 计算结果框的高度
 */
const slideHeight = computed(() => {
  return slideRef.value.scrollHeight
})

/**
 * 监听是否显示
 */
watch(() => props.show, () => {
  if (props.show) {
    // 显示
    isShow.value = true;
    setTimeout(() => {
      slideRef.value.style.top = 3.5 + "rem";
    }, 100)
  } else {
    // 隐藏
    slideRef.value.style.top = -slideHeight.value + "px"
    setTimeout(() => {
      isShow.value = false;
    }, 300);
  }
})
/**
 * 点击加入小组
 */
const handleJoinGroup = (groupId: string) => {
  dialog.warning({
    title: "加入小组", content: "确定要加入该小组嘛？", onConfirmClick: () => {
      joinGroup(userInfo.id, groupId).then((res:any) => {
        console.log(res);
        if (res.status) {
          toast.success({message:res.msg});
          addGroupFromList(groupId); // 向父组件传递数据
        }else{
          toast.warning({message:"不能重复加入"});
        }
      })
    }
  })
}
const props = defineProps<{
  groupList: Group_Type[]
  show: boolean,
  clickHide: () => void
}>()
</script>
<style module="styles">
.side-box {
  background-color: rgba(0, 0, 0, 0.3);
  @apply fixed w-screen h-screen z-40;
}
.search-result-box {
  @apply absolute top-14 bg-white w-full pt-3 pb-4 z-50 
  transition-all duration-300 ease-in-out;
}
.group-item {
  @apply flex justify-between items-center;
}
.search-list {
  @apply mt-2 px-3 flex-col space-y-4 overflow-hidden overflow-y-scroll;
}
.btn-1 {
  @apply px-2 py-1 text-white text-xs rounded-sm bg-green-500;
}
.btn-2 {
  @apply px-2 py-1 text-white text-xs rounded-sm bg-slate-500;
}
</style>
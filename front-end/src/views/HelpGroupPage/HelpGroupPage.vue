<template>
  <div class="w-full h-full flex flex-col justify-start overflow-hidden">
    <!-- 顶部栏--标题---搜索框---更多操作 -->
    <top-bar></top-bar>
    <!-- 内容区域 -->
    <div class="flex-1 flex flex-col justify-between overflow-hidden">
      <!-- 小组列表--- -->
      <group-list ref="groupListRef" @get-active-group-info="getActiveGroupInfo"></group-list>
      <div class="h-1 bg-slate-200"></div>
      <!-- 小组信息盒子 -->
      <group-info ref="groupInfoRef" @delete-group-from-list="deleteGroupFromList"></group-info>
      <div class="h-1 bg-slate-200"></div>
      <!-- 小组成员列表 -->
      <member-list ref="memberListRef"></member-list>
    </div>
    <!-- 弹窗---创建小组 -->
    <modal title="创建小组" :show="showCreateModal" :click-hide="() => { showCreateModal = false }">
      <div :class="styles['form-input-item']">
        <label>小组名字</label>
        <input v-model="groupFrom.name" type="text" />
      </div>
      <div class="flex items-start w-full p-3">
        <label class="mr-2 text-orange-400">小组介绍</label>
        <textarea
          v-model="groupFrom.introduce"
          class="flex-1 rounded-sm text-sm bg-slate-200 px-3 py-1"
          wrap="hard"
          rows="3"
          placeholder="请输入介绍"
        ></textarea>
      </div>
      <div :class="styles['form-btns']">
        <a @click="() => { showCreateModal = false }">取消</a>
        <a @click="handleConfirmCreate">确定创建</a>
      </div>
    </modal>
  </div>
</template>
<script setup lang="ts">
import { ref, provide } from 'vue'
// ************************************************ 导入组件--hooks
import Modal from '@/components/Modal/Modal.vue'
import TopBar from './components/TopBar.vue'
import GroupList from './components/GroupList.vue'
import MemberList from './components/MemberList.vue'
import GroupInfo from './components/GroupInfo.vue'
import { createGroup } from '@/services/api/group'
import {useToast} from '@/components/index'
import { Group_Type } from '@/types/index'
import { userInfoStorage } from '@/utils/storageUtil'
// ************************************************ 定义状态/向子组件提供共享状态
const showCreateModal = ref<boolean>(false);
const toast = useToast();
const groupInfoRef = ref<any>(null); // 当前小组信息节点
const memberListRef = ref<any>(null); // 成员列表节点
const groupListRef = ref<any>(null); // 小组列表节点
const userInfo = userInfoStorage.value;
const groupFrom = ref<{name:string,introduce:string}>({
  name:"",
  introduce:""
})
/**
 * 显示弹窗---创建小组
 */
const openCreateModal = () => {
  showCreateModal.value = true;
}

/**
 * 确定创建
 */
const handleConfirmCreate = ()=>{
  if (groupFrom.value.name === "") {
    toast.warning({message:"小组名为空"});
  }else if(groupFrom.value.introduce === ""){
    toast.warning({message:"小组介绍为空"});
  }else{
    createGroup(userInfo.id,groupFrom.value.name,groupFrom.value.introduce).then((res:any)=>{
      console.log(res);
      if (res.status) {
        toast.success({message:"创建成功"});
        showCreateModal.value = false;
      }else{
        toast.warning({message:"小组名字已存在"});
      }
    })
  }
}

/**
 * 获取当前的小组信息
 */
const getActiveGroupInfo = (value: Group_Type) => {
  memberListRef.value.changeActiveGroupId(value);
  groupInfoRef.value.changeActiveGroupInfo(value);
};

/**
 * 从小组列表中删除指定的小组
 */
const deleteGroupFromList = (groupId: string) => {
  console.log(groupId);
  groupListRef.value.deleteGroupFromList(groupId);
}
/**
 * 添加小组
 */
const addGroupFromList = (groupId: string) => {
  groupListRef.value.initState();
}
provide("openCreateModal", openCreateModal)
provide("addGroupFromList", addGroupFromList);
</script>
<style module="styles">
/* 修改信息框的表单 */
.form-input-item {
  @apply w-full flex justify-start items-center p-3;
}
.form-input-item label {
  @apply mr-2 text-orange-400;
}
.form-input-item input {
  @apply bg-slate-200 rounded-sm h-9 px-3 flex-1 text-sm;
}
.form-btns {
  @apply w-full flex justify-end space-x-3 mt-2 px-3
}
.form-btns a:nth-of-type(1) {
  @apply bg-slate-500 text-white px-2 py-1 rounded-sm;
}
.form-btns a:nth-of-type(2) {
  @apply bg-green-600 text-white px-2 py-1 rounded-sm;
}
</style>
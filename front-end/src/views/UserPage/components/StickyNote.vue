<template>
  <!-- 便贴 -->
  <div :class="styles['sticky-notes']">
    <div :class="`${styles['sticky-notes-bar']} theme-dark`">
      <tag-one theme="outline" size="24" fill="#ffffff" />
      <span class="flex-1 ml-3">我的便贴</span>
      <add @click="() => { showModal = true }" theme="outline" size="24" fill="#ffffff" />
    </div>
    <!-- 便贴列表 -->
    <ul class="border-b-2">
      <li v-for="item in stickNotesList" :class="styles['sticky-notes-item']">
        <div class="flex justify-between items-center">
          <span class="text-gray-500 text-xs mt-2">{{item.createTime}}</span>
          <more @click="()=>{handleMore(item.id)}" theme="outline" size="24" fill="#333" />
        </div>
        <div>
          <dot theme="outline" size="15" fill="#333" />
          <span class="text-sm">{{item.title}}</span>
        </div>
      </li>
    </ul>
  </div>
  <!-- 上滑抽屉 -->
  <drawer v-model:show="showDrawer" :clickHide="() => { showDrawer = false }">
    <ul :class="styles['drawer-list']">
      <li @click="handleUpdate()">修改</li>
      <li @click="handleDelete()">删除</li>
      <li @click="() => { showDrawer = false }">取消</li>
    </ul>
  </drawer>
  <!-- 弹出模态框 -->
  <modal title="添加便贴" v-model:show="showModal" :clickHide="() => { showModal = false }">
    <div :class="styles['note-form-box']">
      <textarea rows="4" wrap="hard" placeholder="输入你的便贴" v-model="noteContent"></textarea>
      <div :class="styles['note-form-btns']">
        <a @click="() => { showModal = false }">取消</a>
        <a @click="handleAdd">确定添加</a>
      </div>
    </div>
  </modal>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { TagOne, Add, Dot, More } from '@icon-park/vue-next'
// ********************************************************************
import Drawer from '@/components/Drawer/Drawer.vue'
import Modal from '@/components/Modal/Modal.vue'
import { useToast,useDialog } from '@/components/index'
import { userInfoStorage } from '@/utils/storageUtil'
import { getStickyList, deleteStick, updateStick, createStick } from '@/services/api/sticky'
import { Sticky_Type } from '@/types'
// ********************************************************************
const toast = useToast();
const stickNotesList = ref<Sticky_Type[]>([]);
const userInfo = userInfoStorage.value;
// ********************************************************************
const showDrawer = ref(false);
const showModal = ref(false);
const activeStickId = ref(0);
// 便贴的内容
const noteContent = ref("");
const dialog = useDialog();

onMounted(() => {
  initList();
})
// 初始化列表
function initList(){
  getStickyList(userInfo.id).then((res: any) => {
    if (res.status) {
      stickNotesList.value = res.data
    }
  })
}
// 点击修改
const handleUpdate = ()=>{

}

// 点击删除
const handleDelete = ()=>{
  console.log(activeStickId.value);
  dialog.warning({
    title:"删除便贴",
    content:"确定要删除嘛？",
    onConfirmClick:()=>{
      deleteStick(activeStickId.value).then((res:any)=>{
        console.log(res);
        
        if(res.status){
          toast.success({message:"删除成功！"});
          initList(); // 重新加载列表
          showDrawer.value = false;
        }
      })
    }
  })
}

/**
 * 点击更多
 */
const handleMore = (stickId:any) => {
  showDrawer.value = true;
  activeStickId.value = stickId;
}
/**
 * 点击添加
 */
const handleAdd = () => {
  if (noteContent.value === "") {
    toast.warning({ message: "内容为空" });
  } else {
    createStick(userInfo.id, noteContent.value).then((res: any) => {
      if (res.status) {
        toast.success({ message: "添加成功" });
        initList();
        showModal.value = false;
        noteContent.value = '';
      }
    })
  }
}
</script>
<style module="styles">
/* 便利贴 */
.sticky-notes {
  @apply w-11/12 mt-5 rounded-md flex flex-col justify-start shadow-md;
}
.sticky-notes-bar {
  @apply flex justify-between items-center px-2 py-2 w-full rounded-t-md;
}
.sticky-notes-item {
  @apply border border-b-0 px-2 py-2 flex flex-col justify-start;
}
/* 抽屉的每个菜单子项 */
.drawer-list {
  @apply flex flex-col items-center
  w-full bg-slate-200;
}
.drawer-list li {
  @apply py-2 w-full text-center active:bg-slate-200 bg-white;
}
.drawer-list li:nth-of-type(1) {
  @apply text-blue-500;
}
.drawer-list li:nth-of-type(2) {
  @apply mb-2 text-red-500;
}
/* 添加便贴的模态框 */
.note-form-box {
  @apply w-full flex flex-col justify-start;
}
.note-form-box textarea {
  @apply w-full border p-2 mt-2 text-sm;
}
/* 表单按钮 */
.note-form-btns {
  @apply w-full flex justify-end items-center mt-3;
}
.note-form-btns a {
  @apply px-3 py-1 text-white mx-3 rounded-sm;
}
.note-form-btns a:nth-of-type(1) {
  @apply bg-red-400;
}
.note-form-btns a:nth-of-type(2) {
  @apply bg-green-500;
}
</style>
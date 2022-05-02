<template>
  <div :class="styles['group-info-box']">
    <!-- 模态框--上传头像 -->
    <modal :show="showUploadModal" title="上传头像" :click-hide="() => { showUploadModal = false }">
      <div class="flex items-center space-x-3">
        <img class="w-28 h-28 object-cover rounded-full border-4" ref="previewImgRef" />
        <div class="flex flex-col justify-between">
          <span>图片类型: {{ fileInfo?.type }}</span>
          <span>图片大小: {{ fileInfo?.size }}</span>
        </div>
      </div>
      <div :class="styles['upload-btns']">
        <button @click="() => { showUploadModal = false }">取消</button>
        <button @click="handleClickUpload">确定</button>
      </div>
    </modal>
    <img
      v-if="activeGroupInfo.id"
      class="w-14 h-14 object-fill rounded-md"
      :src="`/api/download/team/${activeGroupInfo.id}?${Math.ceil(Math.random() * 100000)}`"
    />
    <!-- 右边的信息 -->
    <div class="flex flex-col flex-1 ml-3 overflow-hidden">
      <hamburger-button
        class="absolute right-3"
        @click="changeShowMenu"
        theme="outline"
        size="24"
        fill="#333"
      />
      <div class="w-56 flex items-center mb-1">
        <span class="font-bold truncate w-fit">{{ activeGroupInfo.name }}</span>
        <span>({{ activeGroupInfo?.memberNumber }}/7)</span>
      </div>
      <span class="text-xs text-slate-600">创建时间：{{ activeGroupInfo.createTime }}</span>
      <span class="text-xs mb-1 text-slate-600">小组长：{{ activeGroupInfo.leaderName }}</span>
      <div class="text-sm text-ellipsis truncate">简介：{{ activeGroupInfo.introduce }}</div>
    </div>
    <!-- 遮罩层 -->
    <div v-show="showMenuBox" :class="styles['side-box']" @click="() => { showMenuBox = false }"></div>
    <!-- 操作选项 -->
    <div v-show="showMenuBox" ref="menuRef" :class="styles['menu-list-box']">
      <ul>
        <li
          :class="styles['menu-item']"
          v-if="activeGroupInfo.leader === userInfo.id"
          @click="() => { handleMenuItem('update') }"
        >修改小组信息</li>
        <li :class="styles['menu-item']" v-if="activeGroupInfo.leader === userInfo.id">
          <input
            type="file"
            multiple="false"
            size="5"
            @change="(e) => { changeUpload(e) }"
            accept="image/jpeg, image/jpg, image/png"
            class="opacity-0 w-full h-auto absolute left-0"
          />
          更换小组头像
        </li>
        <li
          :class="styles['menu-item']"
          @click="() => { handleMenuItem('lookPlan') }"
        >查看小组计划({{ groupPlanNumber }})</li>
        <li :class="styles['menu-item']" @click="() => { handleMenuItem('lookReport') }">查看汇报情况</li>
        <li
          :class="styles['menu-item']"
          @click="() => { handleMenuItem('dissolve') }"
          v-if="activeGroupInfo.leader === userInfo.id"
        >解散小组</li>
        <li
          :class="styles['menu-item']"
          @click="() => { handleMenuItem('quit') }"
          v-if="activeGroupInfo.leader !== userInfo.id"
        >退出小组</li>
      </ul>
    </div>
    <!-- 模态框 --- 修改小组信息 -->
    <modal title="修改小组信息" :show="showUpdateModal" :click-hide="() => { showUpdateModal = false }">
      <div :class="styles['form-input-item']">
        <label>小组名字</label>
        <input ref="nameRef" type="text" :value="activeGroupInfo?.name" />
      </div>
      <div class="flex items-start w-full p-3">
        <label class="mr-2 text-orange-400">小组介绍</label>
        <textarea
          ref="introduceRef"
          class="flex-1 rounded-sm text-sm bg-slate-200 px-3 py-1"
          wrap="hard"
          rows="3"
          :value="activeGroupInfo?.introduce"
          placeholder="请输入介绍"
        ></textarea>
      </div>
      <div :class="styles['form-btns']">
        <a @click="() => { showUpdateModal = false }">取消</a>
        <a @click="confirmUpdate">确定修改</a>
      </div>
    </modal>
    <!-- 抽屉 --- 查看小组计划的审核列表 -->
    <drawer :show="showGroupPlanDrawer" :click-hide="() => { showGroupPlanDrawer = false }">
      <group-plan-list
        :activeGroupId="activeGroupId + ''"
        :click-hide="() => { showGroupPlanDrawer = false }"
      ></group-plan-list>
    </drawer>
    <!-- 抽屉 --- 查看汇报情况 -->
    <drawer :show="showGroupReportDrawer" :click-hide="() => { showGroupReportDrawer = false }">
      <report-list :activeGroupId="activeGroupId+''" :click-hide="() => { showGroupReportDrawer = false }"></report-list>
    </drawer>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { HamburgerButton } from '@icon-park/vue-next'
// *****************************************************
import { Group_Type } from '@/types/index'
import { useToast, useDialog } from '@/components/index'
import Modal from '@/components/Modal/Modal.vue'
import Drawer from '@/components/Drawer/Drawer.vue'
import GroupPlanList from './GroupPlanList.vue'
import ReportList from './ReportList.vue'
import { updateGroupInfo, dissolveGroup, uploadAvatar } from '@/services/api/group'
import { quitGroup } from '@/services/api/member'
import { userInfoStorage } from '@/utils/storageUtil'
import { bytesToSize, previewImage } from '@/utils/base'
import { getGroupPlanList } from '@/services/api/plan'
// ************************************************
const activeGroupInfo = ref<Group_Type>({
  id: "",
  name: "",
  introduce: ""
});// 当前小组信息
const menuRef: any = ref(null); // 菜单节点
const introduceRef: any = ref(null); // 介绍节点
const nameRef: any = ref(null); // 名字节点
const showMenuBox = ref(false); // 显示菜单选项盒子
const userInfo = userInfoStorage.value;
const toast = useToast();
const dialog = useDialog();
const showUpdateModal = ref(false); //显示修改小组信息模态框
const fileInfo = ref<{ type: string, size: string }>(); // 文件信息
const previewImgRef = ref(); // 上传头像预览节点
const currentUploadFile = ref(); // 当前上传的文件
const showUploadModal = ref(false); // 显示上传头像的模态框
const showGroupPlanDrawer = ref(false); // 显示小组计划列表的抽屉
const showGroupReportDrawer = ref(false); // 显示小组汇报的抽屉
const activeGroupId = ref("");
const groupPlanNumber = ref(0);

const emit = defineEmits(['deleteGroupFromList']);
/**
 * 改变当前选中小组信息
 */
const changeActiveGroupInfo = (value: Group_Type) => {
  activeGroupInfo.value = value;
  activeGroupId.value = value.id;
  getPlanNumber(value.id);
}

// 计算当前小组的计划个数
function getPlanNumber(groupId: string) {
  groupPlanNumber.value = 0;
  getGroupPlanList(groupId).then((res: any) => {
    if (res.data) {
      groupPlanNumber.value = res.data.planList.length;
    }
  });
};

/**
 * 改变显示盒子
 */
const changeShowMenu = () => {
  showMenuBox.value = !showMenuBox.value;
  menuRef.value.style.transform = "scale(0)";
  if (showMenuBox.value) {
    setTimeout(() => {
      menuRef.value.style.transform = "scale(1)";
    }, 100)
  }
}

/**
 * 改变头像，选择文件时
 */
const changeUpload = (e: any) => {
  console.log(e.target.files);
  let file = e.target.files[0];
  currentUploadFile.value = file;
  let target = previewImgRef.value;
  fileInfo.value = { type: file.type, size: bytesToSize(file.size) };
  // 显示预览头像框
  showUploadModal.value = true;
  previewImage(target, file);
}

/**
 * 点击上传头像
 */
const handleClickUpload = () => {
  let params = new FormData();
  params.append("groupId", activeGroupInfo.value.id + ''); // 加入小组id
  params.append("avatar", currentUploadFile.value); // 加入上传文件
  uploadAvatar(params).then((res: any) => {
    console.log(res);
    if (res.status) {
      showUploadModal.value = false;
      toast.success({ message: "上传成功" });
    }
  })
}

/**
 * 点击每个菜单按钮时
 */
const handleMenuItem = (itemName: "update" | "lookPlan" | "lookReport" | "dissolve" | "quit") => {
  switch (itemName) {
    // 修改小组信息
    case "update":
      setTimeout(() => {
        showUpdateModal.value = true;
      }, 200)
      break;
    case "lookPlan":
      setTimeout(() => {
        showGroupPlanDrawer.value = true;
      }, 200)
      break;
    case "lookReport":
      setTimeout(() => {
        showGroupReportDrawer.value = true;
      }, 200)
      break;
    case "dissolve":
      setTimeout(() => {
        dialog.warning({
          title: "解散小组", content: "确定要解散嘛？", onConfirmClick: () => {
            dissolveGroup(userInfo.id, activeGroupInfo.value.id).then((res: any) => {
              if (res.status) {
                toast.success({ message: "解散成功" });
                emit("deleteGroupFromList", activeGroupInfo.value.id);
                showMenuBox.value = false;
              } else {
                toast.warning({ message: "请重试" });
              }
            })
          }
        });
      }, 200)
      break;
    case "quit":
      setTimeout(() => {
        dialog.warning({
          title: "退出小组", content: "确定要退出嘛？", onConfirmClick: () => {
            quitGroup(userInfo.id, activeGroupInfo.value.id).then((res: any) => {
              if (res.status) {
                toast.success({ message: "退出成功" });
                emit("deleteGroupFromList", activeGroupInfo.value.id);
                showMenuBox.value = false;
              } else {
                toast.warning({ message: "请重试" });
              }
            })
          }
        });
      }, 200)
      break;
  }
}

/**
 * 点击确定修改
 */
const confirmUpdate = () => {
  let groupId: any = activeGroupInfo.value?.id;
  let groupName = nameRef.value.value;
  let groupIntroduce = introduceRef.value.value;
  if (groupName === "") {
    toast.warning({ message: "小组名字为空" })
  } else if (groupIntroduce === "") {
    toast.warning({ message: "小组介绍为空" });
  } else {
    updateGroupInfo(groupId, groupName, groupIntroduce).then((res: any) => {
      console.log(res); // 修改成功
      if (res.status) {
        toast.success({ message: "修改成功" });
        showUpdateModal.value = false;
        activeGroupInfo.value.name = groupName;
        activeGroupInfo.value.introduce = groupIntroduce;
      } else {
        toast.warning({ message: "请重试" });
      }
    })
  }
}

defineExpose({ changeActiveGroupInfo });

</script>
<style module="styles">
.group-info-box {
  @apply w-full border-2 p-3 
          flex justify-start;
}
/* 操作列表 */
.menu-list-box {
  background-color: #6d6875;
  @apply absolute w-60 p-3 text-white shadow-lg right-6 mt-6 rounded-sm
  transition-transform duration-300 ease-in-out;
}
.menu-item {
  @apply w-full px-3 py-2 rounded-sm text-white active:bg-white active:text-black;
}
/* 遮罩层 */
.side-box {
  @apply w-screen h-screen fixed top-0 left-0;
}
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
  @apply w-full flex justify-end space-x-3 mt-2;
}
.form-btns a:nth-of-type(1) {
  @apply bg-slate-500 text-white px-2 py-1 rounded-sm;
}
.form-btns a:nth-of-type(2) {
  @apply bg-green-600 text-white px-2 py-1 rounded-sm;
}
/* 上传头像框的按钮 */
.upload-btns {
  @apply w-full flex justify-end items-center mt-3 space-x-3;
}
.upload-btns button:nth-of-type(1) {
  @apply px-4 py-1 bg-slate-400 text-white rounded-sm;
}
.upload-btns button:nth-of-type(2) {
  @apply px-4 py-1 bg-green-600 text-white rounded-sm;
}
</style>
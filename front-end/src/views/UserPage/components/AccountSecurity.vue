<template>
  <!-- 外层容器 -->
  <div class="h-full flex flex-col">
    <!-- 加载 -->
    <spin :show="isLoading" description="上传中..."></spin>
    <!-- 模态框--修改昵称 -->
    <modal
      :show="showUpdateNameModal"
      title="修改昵称"
      :click-hide="() => { showUpdateNameModal = false }"
    >
      <input
        v-model="userInfo.userName"
        :class="styles['form-input']"
        type="text"
        placeholder="输入你的昵称"
      />
      <div :class="styles['form-btns']">
        <button @click="() => { showUpdateNameModal = false }">取消</button>
        <button @click="() => { handleConfirm('updateName') }">确定</button>
      </div>
    </modal>
    <!-- 模态框--修改年龄 -->
    <modal
      :show="showUpdateAgeModal"
      title="修改年龄"
      :click-hide="() => { showUpdateAgeModal = false }"
    >
      <input
        v-model="userInfo.age"
        :class="styles['form-input']"
        type="number"
        placeholder="输入你的年龄"
      />
      <div :class="styles['form-btns']">
        <button @click="() => { showUpdateAgeModal = false }">取消</button>
        <button @click="() => { handleConfirm('updateAge') }">确定</button>
      </div>
    </modal>
    <!-- 模态框--修改学号 -->
    <modal
      :show="showUpdateNumberModal"
      title="绑定学号"
      :click-hide="() => { showUpdateNumberModal = false }"
    >
      <input
        v-model="userInfo.studentNumber"
        :class="styles['form-input']"
        type="number"
        placeholder="输入你的学号"
      />
      <div :class="styles['form-btns']">
        <button @click="() => { showUpdateNumberModal = false }">取消</button>
        <button @click="() => { handleConfirm('updateNumber') }">确定</button>
      </div>
    </modal>
    <!-- 模态框--上传头像 -->
    <modal :show="showUploadModal" title="上传头像" :click-hide="() => { showUploadModal = false }">
      <div class="flex items-center space-x-3">
        <img class="w-28 h-28 object-cover rounded-full border-4" ref="previewImgRef" />
        <div class="flex flex-col justify-between">
          <span>图片类型: {{ fileInfo?.type }}</span>
          <span>图片大小: {{ fileInfo?.size }}</span>
        </div>
      </div>
      <div :class="styles['form-btns']">
        <button @click="() => { showUploadModal = false }">取消</button>
        <button @click="() => { handleConfirm('uploadAvatar') }">确定</button>
      </div>
    </modal>
    <!-- 顶部栏 -->
    <nav-bar title="账户与安全"></nav-bar>
    <div class="flex flex-1 flex-col items-center overflow-y-scroll overflow-hidden">
      <!-- 用户相关--昵称--性别--年龄 -->
      <ul class="w-full">
        <li class="flex relative justify-between items-center px-4 py-3 active:bg-slate-200">
          <input
            type="file"
            multiple="false"
            size="5"
            @change="(e) => { changeUpload(e) }"
            accept="image/jpeg, image/jpg, image/png"
            class="opacity-0 w-14 h-14 absolute right-4"
          />
          <span class="text-center font-bold">头像</span>
          <img class="h-14 w-14 object-cover rounded-full" :src="`/api/download/avatar/${userInfo.id}?${Math.ceil(Math.random() * 100000)}`" />
        </li>
        <li :class="styles['profile-item']" @click="() => { handleMenuItem('updateName') }">
          <span>昵称</span>
          <span>{{ userInfo.userName }}</span>
        </li>
        <li :class="styles['profile-item']" @click="() => { handleMenuItem('updateSex') }">
          <span>性别</span>
          <span>{{ userInfo.sex }}</span>
        </li>
        <li :class="styles['profile-item']" @click="() => { handleMenuItem('updateAge') }">
          <span>年龄</span>
          <span>{{ userInfo.age }}</span>
        </li>
      </ul>
      <!-- 绑定学号--账户--修改密码 -->
      <ul class="border-y-2 mt-4 py-3 w-full">
        <li :class="styles['profile-item']" @click="() => { handleMenuItem('updateNumber') }">
          <span>学号</span>
          <span>{{ userInfo.studentNumber === undefined ? '绑定学号' : userInfo.studentNumber }}</span>
        </li>
        <li :class="styles['profile-item']">
          <span>账户</span>
          <span>{{ userInfo.email }}</span>
        </li>
        <li
          :class="styles['profile-item']"
          @click="() => { delayedSwitchRoute(200, router, { name: 'passwrod' }) }"
        >
          <span>修改密码</span>
        </li>
      </ul>
      <!-- 退出登录 -->
      <div class="rounded-md py-3 px-32 mt-8 text-white bg-rose-500" @click="handleLogout">退出登录</div>
      <!-- 注销账户 -->
      <div class="flex justify-between items-center mt-8 px-4 py-3 active:bg-slate-200">
        <img class="h-5" src="@/assets/images/warning.svg" />
        <span class="text-orange-400 ml-2">注销账户</span>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { delayedSwitchRoute } from '@/utils/base'
import NavBar from '@/components/NavBar/NavBar.vue'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
// *****************************************************
import { updateUserInfo, uploadAvatar } from '@/services/api/user'
import Modal from '@/components/Modal/Modal.vue'
import Spin from '@/components/Spin/Spin.vue'
import { Student_Type } from '@/types/index'
import { useDialog } from '@/components/index'
import { useToast } from '@/components/index'
import { userInfoStorage } from '@/utils/storageUtil'
import { bytesToSize, previewImage } from '@/utils/base'
// ******************************************************
const previewImgRef = ref(); // 上传头像预览节点
const currentUploadFile = ref(); // 当前上传的文件
const router = useRouter(); // 路由器
const userInfo = ref<Student_Type>(userInfoStorage.value);
const fileInfo = ref<{ type: string, size: string }>();
const dialog = useDialog(); // 弹出框
const toast = useToast(); // 消息盒子
const showUpdateNameModal = ref(false); // 显示修改昵称模态框
const showUpdateAgeModal = ref(false); // 显示修改年龄模态框
const showUpdateNumberModal = ref(false); // 显示修改学号的模态框
const showUploadModal = ref(false); // 显示上传头像的模态框
const isLoading = ref(false); // 是否加载

/**
 * 点击退出登录 --- 清除用户信息
 */
function handleLogout() {
  dialog.warning({
    title: "退出登录",
    content: "确定要退出登录吗?",
    onConfirmClick: () => {
      router.push("/login");
      toast.success({message:"退出成功！"});
    }
  });
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
 * 点击每个菜单子项
 */
const handleMenuItem = (value: "updateName" | "updateSex" | "updateAge" | "updateNumber") => {
  switch (value) {
    case "updateName":
      setTimeout(() => {
        showUpdateNameModal.value = true;
      }, 250)
      break;
    case "updateSex":
      if (userInfo.value.sex === "男") {
        userInfo.value.sex = "女";
      } else {
        userInfo.value.sex = "男";
      }
      updateUserInfo({ id: userInfo.value.id, sex: userInfo.value.sex }).then((res: any) => {
        console.log(res);
        if (res.status) {
          toast.success({ message: "修改成功" });
        }
      })
      break;
    case "updateAge":
      setTimeout(() => {
        showUpdateAgeModal.value = true;
      }, 250)
      break;
    case "updateNumber":
      setTimeout(() => {
        showUpdateNumberModal.value = true;
      }, 250)
      break;
  }
}
/**
 * 点击每个模态框的确定，执行对应的请求
 */
const handleConfirm = (value: "updateName" | "updateAge" | "updateNumber" | "uploadAvatar") => {
  switch (value) {
    case "updateName":
      if (verifyNull(userInfo.value.userName, "输入昵称为空")) {
        updateUserInfo({ id: userInfo.value.id, userName: userInfo.value.userName }).then((res: any) => {
          console.log(res);
          if (res.status) {
            toast.success({ message: "修改成功" });
            showUpdateNameModal.value = false;
          }
        })
      }
      break;
    case "updateAge":
      if (verifyNull(userInfo.value.age, "输入年龄为空")) {
        updateUserInfo({ id: userInfo.value.id, age: userInfo.value.age }).then((res: any) => {
          console.log(res);
          if (res.status) {
            toast.success({ message: "修改成功" });
            showUpdateAgeModal.value = false;
          }
        })
      }
      break;
    case "updateNumber":
      if (verifyNull(userInfo.value.studentNumber, "输入学号为空")) {
        updateUserInfo({ id: userInfo.value.id, studentNumber: userInfo.value.studentNumber }).then((res: any) => {
          console.log(res);
          if (res.status) {
            toast.success({ message: "修改成功" });
            showUpdateNumberModal.value = false;
          }
        })
      }
      break;
    case "uploadAvatar":
      isLoading.value = true; //显示加载
      let params = new FormData();
      params.append("uid", userInfo.value.id + ''); // 加入用户id
      params.append("avatar", currentUploadFile.value); // 加入上传文件
      uploadAvatar(params).then((res:any)=>{
        console.log(res);
        if (res.status) {
          isLoading.value = false;
          showUploadModal.value = false;
          toast.success({message:"上传成功"});
        }
      })
      break;
  }
  // 验证是否为空
  function verifyNull(value: string | number, message: string): boolean {
    if (value === "") {
      toast.warning({ message });
      return false;
    } else {
      return true;
    }
  }
}
</script>
<style module="styles">
.profile-item {
  @apply flex flex-col justify-start items-start px-4 py-3 font-bold active:bg-slate-200;
}
.profile-item span:nth-of-type(2) {
  @apply font-normal text-sm text-slate-500;
}
/* 输入框样式 */
.form-input {
  @apply w-full h-10 px-3 py-1 mt-3 bg-slate-200 focus:border-green-500 focus:border-2 rounded-sm;
}
/* 按钮 */
.form-btns {
  @apply w-full flex justify-end items-center mt-3 space-x-3;
}
.form-btns button:nth-of-type(1) {
  @apply px-4 py-1 bg-slate-400 text-white rounded-sm;
}
.form-btns button:nth-of-type(2) {
  @apply px-4 py-1 bg-green-600 text-white rounded-sm;
}
</style>
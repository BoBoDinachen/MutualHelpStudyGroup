<template>
  <div>
    <nav-bar title="修改密码"></nav-bar>
    <div :class="styles['form-box']">
      <div class="relative">
        <input
          placeholder="输入原密码"
          :class="`${activeRequire === 'oldPassword' ? 'border-red-400 border-2' : ''}`"
          :value="oldPassword"
          :type="`${isVisible ? 'text' : 'password'}`"
        />
        <preview-close-one
          @click="switchVisible"
          v-show="isVisible === false"
          :class="styles['form-icon']"
          theme="outline"
          size="20"
          fill="#333"
        />
        <preview-open
          @click="switchVisible"
          v-show="isVisible === true"
          :class="styles['form-icon']"
          theme="outline"
          size="20"
          fill="#333"
        />
      </div>
      <input
        type="text"
        :class="`${activeRequire === 'password' ? 'border-red-400 border-2' : ''}`"
        placeholder="请输入新密码"
        v-model="formData.password"
      />
      <input
        type="text"
        :class="`${activeRequire === 'repassword' ? 'border-red-400 border-2' : ''}`"
        placeholder="确认密码"
        v-model="formData.repassword"
      />
      <button :class="styles['form-btn']" @click="handleConfirm">确定修改</button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import NavBar from '@/components/NavBar/NavBar.vue'
import { PreviewCloseOne, PreviewOpen } from '@icon-park/vue-next'
import { ref, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useToast, useDialog } from '@/components/index'
import { getUserInfo, updateUserInfo } from '@/services/api/user'
import { userInfoStorage } from '@/utils/storageUtil'
// ***************************************************
const isVisible = ref(false); // 密码是否可见
const oldPassword = ref("");
const toast = useToast();
const dialog = useDialog();
const router = useRouter();
const activeRequire = ref<"oldPassword" | "password" | "repassword" | "null">();
const formData = reactive({
  uid: userInfoStorage.value.id,
  password: "",
  repassword: "",
})
/**
 * 页面装载完成时
 */
onMounted(() => {
  getUserInfo(formData.uid).then((res: any) => {
    console.log(res);
    oldPassword.value = res.data.student.password;
  })
})
/**
 * 切换密码是否可见
 */
const switchVisible = () => {
  isVisible.value = !isVisible.value;
}
/**
 * 确定修改
 */
const handleConfirm = () => {
  // 验证输入
  if (verifyForm()) {
    updateUserInfo({
      id: formData.uid,
      password: formData.password
    }).then((res: any) => {
      console.log(res);
      if (res.status) {
        dialog.success({
          title: "重置密码成功",
          content: "将返回登录页面,重新登录",
          onConfirmClick: () => {
            router.push("/login");
          }
        })
      }
    })
  }
}

function verifyForm(): boolean {
  if (formData.password === "") {
    toast.warning({ message: "请填写密码" });
    activeRequire.value = "password";
    return false;
  } else if (formData.repassword === "") {
    toast.warning({ message: "再次输入密码" });
    activeRequire.value = "repassword"
    return false;
  } else if (formData.password !== formData.repassword) {
    toast.warning({ message: "两次密码不一致" });
    return false;
  } else if (oldPassword.value === "") {
    toast.warning({ message: "请填写原密码" });
    activeRequire.value = "oldPassword";
    return false;
  } else {
    activeRequire.value = "null";
    return true;
  }
}
</script>
<style module="styles">
.form-box {
  @apply flex flex-col space-y-3 px-5 mt-5;
}
.form-box input {
  @apply h-12 w-full bg-slate-200 px-3 rounded-sm;
}
.form-btn {
  @apply w-full bg-orange-500 py-3 rounded-sm text-white;
}
.form-icon {
  right: 5%;
  @apply absolute flex justify-center items-center m-auto top-0 bottom-0;
}
</style>
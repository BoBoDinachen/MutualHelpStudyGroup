<template>
  <div>
    <spin :show="isLoading" description="正在注册..."></spin>
    <!-- 导航栏 -->
    <nav-bar title="注册用户"></nav-bar>
    <ul :class="styles['register-box']">
      <li :class="styles['register-form-item']">
        <input
          placeholder="您的邮箱"
          :class="`${styles['register-form-input']} ${activeUnfilled === 'email-fill' ? 'border-red-500 border-2' : ''}`"
          type="text"
          v-model="formData.email"
        />
      </li>
      <li :class="styles['register-form-item']">
        <input
          placeholder="输入密码"
          v-model="formData.password"
          :class="`${styles['register-form-input']} ${activeUnfilled === 'password-fill' ? 'border-red-500 border-2' : ''}`"
          :type="`${isVisible ? 'text' : 'password'}`"
        />
        <preview-close-one
          @click="switchVisible"
          v-show="isVisible === false"
          :class="styles['register-icon']"
          theme="outline"
          size="20"
          fill="#333"
        />
        <preview-open
          @click="switchVisible"
          v-show="isVisible === true"
          :class="styles['register-icon']"
          theme="outline"
          size="20"
          fill="#333"
        />
      </li>
      <li :class="styles['register-form-item']">
        <input
          :class="`${styles['register-form-input']} ${activeUnfilled === 'rePassword-fill' ? 'border-red-500 border-2' : ''}`"
          placeholder="重复密码"
          v-model="formData.rePassword"
          type="password"
        />
      </li>
      <a :class="styles['register-btn']" @click="handleRegister">确定注册</a>
    </ul>
  </div>
</template>
<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { PreviewCloseOne, PreviewOpen } from '@icon-park/vue-next'
// ****************************************************** 引入hooks和组件
import Spin from '@/components/Spin/Spin.vue'
import NavBar from '@/components/NavBar/NavBar.vue'
import { useToast, useDialog } from '@/components/index'
import { userRegister } from '@/services/api/user'
import { loginInfoStorage } from '@/utils/storageUtil'
// ************************************************** 组件状态和数据
const isLoading = ref(false);
const toast = useToast();
const router = useRouter();
const dialog = useDialog();
const isVisible = ref(false); // 密码是否可见
const activeUnfilled = ref<"email-fill" | "password-fill" | "rePassword-fill" | "null">();
interface FormDataType {
  email: string, password: string, rePassword: string
}
// 表单数据
const formData = reactive<FormDataType>({
  email: "",
  password: "",
  rePassword: ""
});

/**
 * 点击注册
 * 获取输入框的值并验证，然后向后端发送请求
 */
const handleRegister = () => {
  // toast.success({ message: "点击注册" });
  const verify = verifyFrom(formData);
  if (verify) {
    isLoading.value = true;
    userRegister({ email: formData.email, password: formData.password }).then((res: any) => {
      if (res.status) {
        loginInfoStorage.value = {
          email: formData.email,
          password: ""
        },
          isLoading.value = false
        dialog.success({
          title: "注册成功!",
          content: "返回登录页",
          onConfirmClick: () => {
            router.push("/login");
          }
        })
      }
    }, err => {
      isLoading.value = false;
    })
  }
}

/**
 * 验证表单
 */
const verifyFrom = (formData: FormDataType) => {
  if (formData.email === "") {
    activeUnfilled.value = "email-fill";
    toast.warning({ message: "请填写邮箱" });
    return false;
  } else if (formData.password === "") {
    activeUnfilled.value = "password-fill";
    toast.warning({ message: "请填写密码" });
    return false;
  } else if (formData.rePassword === "") {
    activeUnfilled.value = "rePassword-fill";
    toast.warning({ message: "请再次填写密码" });
    return false;
  } else {
    activeUnfilled.value = "null";
    if (!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(formData.email)) {
      toast.warning({ message: "邮箱格式错误" });
      activeUnfilled.value = "email-fill";
      return false;
    } else if (formData.password.length > 8) {
      toast.warning({ message: "密码长度不能超过八位" });
      activeUnfilled.value = "password-fill"
      return false;
    } else if (formData.rePassword !== formData.password) {
      toast.warning({ message: "两次密码不一样" });
      activeUnfilled.value = "rePassword-fill";
      return false;
    } else {
      return true;
    }
  }
}

/**
 * 切换密码是否可见
 */
const switchVisible = () => {
  isVisible.value = !isVisible.value;
}
</script>
<style module="styles">
.register-box {
  @apply p-5 flex flex-col justify-start;
}
.register-form-item {
  @apply relative mb-3;
}
.register-form-input {
  @apply bg-gray-100 w-full h-12 rounded-sm  px-3 focus:border-green-600 focus:border-2;
}
.register-btn {
  @apply w-full bg-green-600 text-white  h-12 mt-5 rounded-sm flex justify-center items-center;
}
.register-icon {
  right: 5%;
  @apply absolute flex justify-center items-center m-auto top-0 bottom-0;
}
</style>
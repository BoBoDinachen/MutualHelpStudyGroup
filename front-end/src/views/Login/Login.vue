<template>
  <div :class="styles['login-box']">
    <spin :show="isLoading" description="正在登录中..."></spin>
    <img src="@/assets/images/background.svg" />
    <h2 class="text-5xl my-8" style="font-family: 'XiaoTu';">互助学习</h2>
    <!-- 表单 -->
    <div :class="styles['login-form-box']">
      <div
        :class="`${styles['login-form-item']} ${active === 'email' ? 'border-b-blue-600' : (active === 'require_email' ? 'border-b-red-500' : '')}`"
      >
        <mail class="mr-5" theme="outline" size="24" fill="#333" />
        <input
          placeholder="请输入邮箱号"
          type="text"
          v-model="formData.email"
          @focus="() => { activeInput('email') }"
        />
      </div>
      <div
        :class="`${styles['login-form-item']} ${active === 'password' ? 'border-b-blue-600' : (active === 'require_password' ? 'border-b-red-500' : '')}`"
      >
        <lock class="mr-5" theme="outline" size="24" fill="#333" />
        <input
          placeholder="请输入密码"
          v-model="formData.password"
          type="password"
          @focus="() => { activeInput('password') }"
        />
      </div>
      <!-- 登录按钮 -->
      <div :class="styles['login-btn']" @click="handleLogin">登录</div>
      <div class="flex w-full justify-between mt-5 text-slate-400">
        <span>忘记密码</span>
        <span @click="() => { router.push('/register') }">立即注册</span>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { Mail, Lock } from '@icon-park/vue-next'
// ***************************************************导入组件和hooks
import Spin from '@/components/Spin/Spin.vue'
import { useToast } from '@/components/index'
import { userLogin } from '@/services/api/user'
import { tokenStorage, userInfoStorage,loginInfoStorage } from '@/utils/storageUtil'
// **************************************定义状态
const formData = reactive<{ email: string, password: string }>({
  email: "492697494@qq.com",
  password: "123456"
});
const isLoading = ref(false);
const router = useRouter();
const toast = useToast();
const active = ref<"email" | "password" | "require_email" | "require_password">();
// ****************************************************

onMounted(() => {
  // 加载登录信息
  // if (loginInfoStorage.value.email) {
  //   formData.email = loginInfoStorage.value.email;
  //   formData.password = loginInfoStorage.value.password;
  // }
})
/**
 * 点击登录
 */
function handleLogin() {
  // router.push("/app");
  const verify = verifyFrom(formData);
  // 如果验证通过，则执行登录
  if (verify) {
    isLoading.value = true;
    userLogin(formData).then((res) => {
      console.log(res);
      if (res.status) {
        isLoading.value = false;
        // 将token和user信息存入sessionstorage中
        tokenStorage.value = res.data.token;
        userInfoStorage.value = res.data.user;
        router.push("/app");
        toast.success({ message: "登录成功" });
      } else {
        isLoading.value = false;
        toast.error({ message: "请检查邮箱和密码" });
      }
    },(err)=>{
      console.log(err);
      isLoading.value = false;
    })
  }
}
/**
 * 验证表单
 */
const verifyFrom = (formData: any): boolean => {
  if (formData.email === "") {
    toast.warning({
      message: "请输入邮箱"
    });
    active.value = "require_email"
    return false;
  } else if (formData.password === "") {
    toast.warning({
      message: "请输入密码"
    });
    active.value = "require_password"
    return false
  } else {
    return true;
  }
}

/**
 * 改变当前活动输入框
 */
const activeInput = (name: "email" | "password") => {
  active.value = name;
}
</script>
<style module="styles">
.login-box {
  @apply h-screen flex flex-col justify-start pt-5 items-center;
}
.login-form-box {
  @apply w-full px-12 py-5;
}
.login-form-item {
  @apply flex items-center justify-start pb-2 mb-8 px-1 border-b-2;
}
.login-btn {
  @apply w-full bg-blue-600 py-3 text-center text-white mt-7 rounded-sm text-base tracking-widest;
}
</style>
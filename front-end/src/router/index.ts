import path from 'path';
import { createRouter, createWebHistory,createWebHashHistory, RouteRecordRaw } from 'vue-router'

// 路由懒加载
const MainPark = () => import("@/layouts/MainPark/MainPark.vue");
const Home = () => import("@/views/HomePage/HomePage.vue");
const HelpGroup = () => import("@/views/HelpGroupPage/HelpGroupPage.vue");
const Data = () => import("@/views/DataPage/DataPage.vue");
const User = () => import("@/views/UserPage/UserPage.vue");
const AccountSecurity = () => import("@/views/UserPage/components/AccountSecurity.vue");
const Login = () => import("@/views/Login/Login.vue");
const Register = () => import("@/views/Register/Register.vue");
const ChangePassword = () => import("@/views/UserPage/components/ChangePassword.vue");
const Config = () => import("@/views/UserPage/components/Config.vue");
const ThemeConfig = () => import("@/views/UserPage/components/ThemeConfig.vue");
const Honor = () => import("@/views/UserPage/components/Honor.vue");


const routes: RouteRecordRaw[] = [
  {
    path: "/app", component: MainPark, redirect: { name: "home" }, children: [
      { path: "home", name: "home",meta: {keepAlive:true}, component: Home },
      { path: "group", name: "group",meta: {keepAlive:true}, component: HelpGroup },
      { path: "data", name: "data", meta: {keepAlive:true}, component: Data },
      { path: "user/:uid", name: "user",meta: {keepAlive:true}, component: User },
      { path: "user/profile/:uid", name: "profile", component: AccountSecurity },
      { path: "user/passwrod", name: "passwrod", component: ChangePassword },
      { path: "user/config", name: "config", component: Config },
      { path: "user/theme", name: "theme", component: ThemeConfig },
      { path: "user/honor", name: "honor", component: Honor },
    ]
  },
  { path: "/", component: Login },
  { path: "/login", component: Login },
  { path: "/register", component: Register }
]

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
export const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHashHistory(),
  routes, // `routes: routes` 的缩写
})

/**
 * 导航守卫，用于判断是否用户登录
 */
router.beforeEach((to, from, next) => {
  // if (to.name !== 'Login' && !isAuthenticated) next({ name: 'Login' })
  // else next()
  console.log("导航守卫", to);
  next();
})
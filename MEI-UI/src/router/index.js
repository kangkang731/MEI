import { createRouter, createWebHistory } from 'vue-router'
import i18n from '@/locale'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/Home.vue'),
      meta: { titleKey: 'common.home' },
    },
    {
      path: '/policy',
      name: 'policy',
      component: () => import('@/views/Policy.vue'),
      meta: { titleKey: 'common.policy' },
    },
    {
      path: '/policy/:id',
      name: 'policyDetail',
      component: () => import('@/views/PolicyDetail.vue'),
      meta: { titleKey: 'policyDetail.title' },
    },
    {
      path: '/enterprise-entry',
      name: 'enterpriseEntry',
      component: () => import('@/views/EnterpriseEntry.vue'),
      meta: { titleKey: 'common.enterpriseEntry' },
    },
    {
      path: '/project-apply',
      name: 'projectApply',
      component: () => import('@/views/ProjectApply.vue'),
      meta: { titleKey: 'common.projectApply' },
    },
    {
      path: '/achievement',
      name: 'achievement',
      component: () => import('@/views/Achievement.vue'),
      meta: { titleKey: 'common.achievement' },
    },
    {
      path: '/achievement/:id',
      name: 'achievementDetail',
      component: () => import('@/views/AchievementDetail.vue'),
      meta: { titleKey: 'achievementDetail.title' },
    },
    {
      path: '/financing',
      name: 'financing',
      component: () => import('@/views/Financing.vue'),
      meta: { titleKey: 'common.financing' },
    },
    {
      path: '/talent',
      name: 'talent',
      component: () => import('@/views/Talent.vue'),
      meta: { titleKey: 'common.talent' },
    },
    {
      path: '/consultation',
      name: 'consultation',
      component: () => import('@/views/Consultation.vue'),
      meta: { titleKey: 'common.consultation' },
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue'),
      meta: { titleKey: 'login.title' },
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue'),
      meta: { titleKey: 'register.title' },
    },
    {
      path: '/personal-center',
      name: 'personalCenter',
      component: () => import('@/views/PersonalCenter.vue'),
      meta: { titleKey: 'personalCenter.title', keepAlive: true },
    },
  ],
})

const { t } = i18n.global

// 路由守卫：设置页面标题（跟随语言切换） + 权限校验
router.beforeEach((to, from) => {
  if (to.meta.titleKey) {
    document.title = t(to.meta.titleKey) + t('common.platformSuffix')
  }
  // 登录校验
  if (to.meta.requireAuth && !localStorage.getItem('token')) {
    return { name: 'login', query: { redirect: to.fullPath } }
  }
})

// 处理动态组件加载失败（如网络故障、编译错误），防止白屏
router.onError((error) => {
  console.warn('路由加载异常:', error?.message)
})

// 重写 push 方法，静默处理导航拒绝（如同路由重复导航等），防止路由状态损坏
const originalPush = router.push
router.push = function push(location) {
  return originalPush.call(this, location).catch(() => {})
}

// 同步重写 replace 方法保持一致性
const originalReplace = router.replace
router.replace = function replace(location) {
  return originalReplace.call(this, location).catch(() => {})
}

export default router

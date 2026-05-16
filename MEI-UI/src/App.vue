<template>
  <el-config-provider :locale="elementLocale">
    <div v-if="pageError" class="error-boundary">
      <div class="error-content">
        <h2>{{ t('app.errorTitle') }}</h2>
        <p>{{ t('app.errorDesc') }}</p>
        <div class="error-actions">
          <el-button type="primary" @click="recover">{{ t('app.refreshPage') }}</el-button>
          <el-button @click="goHome">{{ t('app.backToHome') }}</el-button>
        </div>
      </div>
    </div>
    <router-view v-slot="{ Component }" v-else>
      <keep-alive>
        <component :is="Component" :key="$route.fullPath" />
      </keep-alive>
    </router-view>
  </el-config-provider>
</template>

<script setup>
import { ref, computed, onErrorCaptured } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { ElConfigProvider } from 'element-plus'
import zhCN from './locale/element-zhCN'
import boCN from './locale/element-boCN'

const { t, locale } = useI18n()

const elementLocale = computed(() => locale.value === 'bo-CN' ? boCN : zhCN)

const router = useRouter()
const pageError = ref(false)

onErrorCaptured((err) => {
  console.warn('组件渲染异常，已启动错误保护:', err)
  pageError.value = true
  return false
})

const recover = () => window.location.reload()
const goHome = () => { pageError.value = false; router.push('/') }
</script>

<style>
/* ===== 全局布达拉宫藏族主题样式 ===== */
:root {
  --tibet-red: #C41E3A;
  --tibet-gold: #DAA520;
  --tibet-white: #FDF5E6;
  --tibet-dark: #2C1810;
  --tibet-blue: #1a3a5c;
  --tibet-bg: #FFF8F0;
  --tibet-border: #E8D5B7;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Microsoft YaHei', 'SimSun', sans-serif;
  background: var(--tibet-bg);
  color: #333;
}

/* 全局滚动条美化 */
::-webkit-scrollbar { width: 8px; }
::-webkit-scrollbar-track { background: var(--tibet-white); }
::-webkit-scrollbar-thumb { background: var(--tibet-gold); border-radius: 4px; }

/* 全局卡片样式 */
.el-card {
  border-radius: 12px !important;
  border: 1px solid var(--tibet-border) !important;
  overflow: hidden;
  transition: all 0.3s ease;
}
.el-card:hover {
  box-shadow: 0 8px 24px rgba(218, 165, 32, 0.15) !important;
}

/* 全局按钮样式 */
.el-button--primary {
  background: linear-gradient(135deg, var(--tibet-red), #E85D75) !important;
  border-color: var(--tibet-red) !important;
  border-radius: 6px !important;
}
.el-button--primary:hover {
  background: linear-gradient(135deg, #D4253F, var(--tibet-red)) !important;
  box-shadow: 0 4px 12px rgba(196, 30, 58, 0.3);
}

/* 全局标签页样式 */
.el-tabs__item.is-active {
  color: var(--tibet-red) !important;
  font-weight: bold;
}
.el-tabs__active-bar {
  background-color: var(--tibet-gold) !important;
}

/* 全局表格样式 */
.el-table th.el-table__cell {
  background: linear-gradient(135deg, var(--tibet-red), #E85D75) !important;
  color: #fff !important;
}
.el-table--border {
  border-color: var(--tibet-border) !important;
}

/* 全局分页样式 */
.el-pagination button:hover {
  color: var(--tibet-gold) !important;
}
.el-pagination.is-background .el-pager li.is-active {
  background: var(--tibet-red) !important;
}

/* 头部导航通用样式 */
.tibet-header {
  background: linear-gradient(135deg, var(--tibet-red), #8B0000) !important;
  color: #fff !important;
  box-shadow: 0 2px 12px rgba(196, 30, 58, 0.3);
  padding: 0 20px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.tibet-header .logo {
  display: flex;
  align-items: center;
  gap: 12px;
}
.tibet-header .logo h1 {
  font-size: 20px;
  color: var(--tibet-gold);
  text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
}
.tibet-header .el-menu {
  background: transparent !important;
  border-bottom: none !important;
  flex: 1;
  display: flex !important;
  justify-content: center;
  overflow-x: auto;
  overflow-y: hidden;
}
.tibet-header .el-menu .el-menu-item {
  color: rgba(255,255,255,0.85) !important;
  font-size: 13px;
  padding: 0 10px !important;
  border-bottom: none !important;
  flex-shrink: 0;
}
.tibet-header .el-menu .el-menu-item:hover,
.tibet-header .el-menu .el-menu-item.is-active {
  background: rgba(255,255,255,0.15) !important;
  color: var(--tibet-gold) !important;
}

/* 页面容器通用 */
.page-container {
  min-height: 100vh;
  background: var(--tibet-bg);
}

/* 底部通用样式 */
.tibet-footer {
  background: linear-gradient(135deg, var(--tibet-dark), #4A2A1A);
  color: rgba(255,255,255,0.8);
  text-align: center;
  padding: 30px 0;
  margin-top: 40px;
}
.tibet-footer p {
  margin: 5px 0;
  font-size: 13px;
}
.tibet-footer .gold-text {
  color: var(--tibet-gold);
}

/* 藏族装饰条纹 */
.tibet-stripe {
  height: 4px;
  background: linear-gradient(90deg, var(--tibet-red), var(--tibet-gold), var(--tibet-red), var(--tibet-gold));
  background-size: 200% 100%;
  animation: stripeMove 3s linear infinite;
}
@keyframes stripeMove {
  0% { background-position: 0% 0%; }
  100% { background-position: 200% 0%; }
}

/* 错误边界 UI */
.error-boundary {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: var(--tibet-bg);
}
.error-content {
  text-align: center;
  padding: 48px;
  background: #fff;
  border-radius: 12px;
  border: 1px solid var(--tibet-border);
  box-shadow: 0 4px 16px rgba(0,0,0,0.08);
}
.error-content h2 {
  font-size: 24px;
  color: var(--tibet-red);
  margin-bottom: 12px;
}
.error-content p {
  color: #666;
  margin-bottom: 24px;
}
.error-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
}

/* 居中通知弹窗动画 */
@keyframes meiFadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
@keyframes meiScaleIn {
  from { transform: scale(0.8); opacity: 0; }
  to { transform: scale(1); opacity: 1; }
}
</style>

<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('policyDetail.title') }}</h1>
      </div>
      <el-menu mode="horizontal" :default-active="menuActive" @select="handleMenuSelect">
        <el-menu-item index="/">{{ t('common.home') }}</el-menu-item>
        <el-menu-item index="/policy">{{ t('common.policy') }}</el-menu-item>
        <el-menu-item index="/enterprise-entry">{{ t('common.enterpriseEntry') }}</el-menu-item>
        <el-menu-item index="/project-apply">{{ t('common.projectApply') }}</el-menu-item>
        <el-menu-item index="/achievement">{{ t('common.achievement') }}</el-menu-item>
        <el-menu-item index="/financing">{{ t('common.financing') }}</el-menu-item>
        <el-menu-item index="/talent">{{ t('common.talent') }}</el-menu-item>
        <el-menu-item index="/consultation">{{ t('common.consultation') }}</el-menu-item>
        <el-menu-item index="/personal-center">{{ t('common.personalCenter') }}</el-menu-item>
      </el-menu>
      <div class="header-right">
        <el-tooltip :content="t('common.switchLang')" placement="bottom">
          <el-button class="lang-btn" @click="switchLang">
            <span class="lang-icon">藏/汉</span>
          </el-button>
        </el-tooltip>
      </div>
    </header>

    <el-main>
      <div class="section-wrapper" v-loading="loading">
        <!-- 面包屑 -->
        <div class="breadcrumb">
          <router-link to="/">{{ t('policyDetail.breadcrumbHome') }}</router-link> &gt;
          <router-link to="/policy">{{ t('policyDetail.breadcrumbPolicy') }}</router-link> &gt;
          {{ t('policyDetail.breadcrumbDetail') }}
        </div>

        <!-- 政策内容 -->
        <el-card shadow="hover" class="policy-detail-card" v-if="policyDetail">
          <div class="policy-header">
            <el-tag :type="policyDetail.type === '0' ? 'danger' : 'warning'" size="large">
              {{ policyDetail.type === '0' ? t('common.policyTag') : t('common.noticeTag') }}
            </el-tag>
            <h1 class="policy-title">{{ policyDetail.title }}</h1>
            <div class="policy-meta">
              <span class="publish-time">{{ t('policyDetail.publishedAt') }}{{ policyDetail.createTime?.substring(0, 10) }}</span>
              <el-button type="primary" size="small" round @click="collectPolicy" :disabled="!isLoggedIn">
                <el-icon><Star /></el-icon>
                {{ isCollected ? t('policyDetail.collected') : t('policyDetail.collect') }}
              </el-button>
            </div>
          </div>

          <el-divider />

          <div class="policy-content" v-html="policyDetail.content"></div>

          <el-divider />

          <div class="policy-actions">
            <el-button type="primary" round @click="goBack">
              <el-icon><ArrowLeft /></el-icon>
              {{ t('policyDetail.backToList') }}
            </el-button>
            <el-button round @click="downloadPolicy">
              <el-icon><Download /></el-icon>
              {{ t('policyDetail.downloadPolicyFile') }}
            </el-button>
          </div>
        </el-card>

        <el-empty v-else :description="t('policyDetail.noPolicyData')" :image-size="120" />
      </div>
    </el-main>

    <footer class="tibet-footer">
      <div class="section-wrapper">
        <p>{{ t('common.copyright') }}</p>
        <p class="gold-text">{{ t('common.icp') }}</p>
      </div>
    </footer>

    <!-- 登录提示弹窗 -->
    <el-dialog
      v-model="loginDialog"
      width="500px"
      center
      :close-on-click-modal="false"
      :show-close="false"
      class="login-dialog"
    >
      <div class="login-dialog-content">
        <div class="login-icon-wrapper">
          <div class="login-icon-bg">
            <el-icon :size="48" color="#fff"><Lock /></el-icon>
          </div>
        </div>
        <div class="login-text">
          <h3 class="login-title">{{ t('common.needLogin') }}</h3>
          <p class="login-desc">{{ t('common.loginToCollect') }}<br/>{{ t('common.collectAndView') }}</p>
        </div>
        <div class="login-actions">
          <el-button size="large" @click="loginDialog = false" class="btn-cancel">{{ t('common.notLoginNow') }}</el-button>
          <el-button size="large" type="primary" @click="goToLogin" class="btn-login">{{ t('common.loginNow') }}</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import request from '@/utils/request'
import { notify } from '@/utils/notify'
import { Star, ArrowLeft, Download, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const { t, locale } = useI18n()
const menuActive = '/policy'
const handleMenuSelect = (index) => router.push(index)

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const loading = ref(false)
const policyDetail = ref(null)
const isCollected = ref(false)
const isLoggedIn = ref(!!localStorage.getItem('token'))
const loginDialog = ref(false)

// 获取政策详情
const loadPolicyDetail = async () => {
  const id = route.params.id
  if (!id) {
    notify('error', t('policyDetail.invalidId'))
    return
  }

  loading.value = true
  try {
    const res = await request.get(`/mei/policy/${id}`)
    
    console.log('政策详情响应:', res.data)

    if (res.data?.code === 200) {
      policyDetail.value = res.data.data
      console.log('政策详情数据:', policyDetail.value)
      
      // 检查是否已收藏
      checkIfCollected(id)
    } else {
      notify('error', res.data?.msg || t('policyDetail.loadFailed'))
    }
  } catch (error) {
    console.error('加载政策详情失败:', error)
    const msg = error.response?.data?.msg || error.response?.data?.message || error.message || t('policyDetail.loadFailed')
    notify('error', msg)
  } finally {
    loading.value = false
  }
}

// 检查是否已收藏
const checkIfCollected = async (policyId) => {
  const userId = localStorage.getItem('userId')
  if (!userId) return

  try {
    const res = await request.get('/mei/favorite/check', {
      params: { userId, policyId }
    })
    if (res.data?.code === 200) {
      isCollected.value = res.data.data
    }
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

// 收藏政策
const collectPolicy = async () => {
  if (!isLoggedIn.value) {
    // 显示登录提示弹框
    loginDialog.value = true
    return
  }

  const userId = localStorage.getItem('userId')
  const policyId = route.params.id

  try {
    if (isCollected.value) {
      // 取消收藏
      const res = await request.delete(`/mei/favorite/remove/${userId}/${policyId}`)
      if (res.data?.code === 200) {
        isCollected.value = false
        notify('success', t('policyDetail.uncollectSuccess'))
      } else {
        notify('error', res.data?.msg || t('policyDetail.uncollectFailed'))
      }
    } else {
      // 添加收藏
      const res = await request.post('/mei/favorite/add', {
        userId: Number(userId),
        policyId: Number(policyId),
        policyTitle: policyDetail.value.title
      })
      if (res.data?.code === 200) {
        isCollected.value = true
        notify('success', t('policyDetail.collectSuccess'))
      } else {
        notify('error', res.data?.msg || t('policyDetail.collectFailed'))
      }
    }
  } catch (error) {
    console.error('收藏操作失败:', error)
    const msg = error.response?.data?.msg || error.response?.data?.message || error.message || t('policyDetail.operationFailed')
    notify('error', msg)
  }
}

// 跳转到登录页
const goToLogin = () => {
  loginDialog.value = false
  router.push('/login')
}

// 下载政策
const downloadPolicy = () => {
  if (!policyDetail.value) {
    notify('error', t('policy.noPolicyData'))
    return
  }
  const title = policyDetail.value.title || '政策文件'
  const content = policyDetail.value.content || ''
  const text = `【${title}】\n\n${content}\n\n--- 西藏双创平台 ---`
  const blob = new Blob([text], { type: 'application/msword' })
  const url = URL.createObjectURL(blob)
  const a = document.createElement('a')
  a.href = url
  a.download = `${title}.doc`
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
  URL.revokeObjectURL(url)
  notify('success', t('policyDetail.downloadStarted'))
}

// 返回
const goBack = () => {
  router.back()
}

onMounted(() => {
  loadPolicyDetail()
})
</script>

<style scoped>
.section-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}
.logo-img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 2px solid var(--tibet-gold);
}
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
.lang-btn {
  background: rgba(255,255,255,0.15) !important;
  border: 1px solid var(--tibet-gold) !important;
  color: var(--tibet-gold) !important;
  border-radius: 6px !important;
  padding: 8px 12px !important;
}
.lang-btn:hover {
  background: rgba(218, 165, 32, 0.3) !important;
}
.lang-icon {
  font-size: 13px;
  font-weight: bold;
}
.breadcrumb {
  padding: 16px 0;
  color: #999;
  font-size: 13px;
}
.breadcrumb a {
  color: var(--tibet-red);
  text-decoration: none;
}
.policy-detail-card {
  margin-top: 20px;
}
.policy-header {
  margin-bottom: 20px;
}
.policy-title {
  font-size: 28px;
  font-weight: 700;
  margin: 16px 0;
  line-height: 1.4;
  color: #303133;
}
.policy-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}
.publish-time {
  color: #909399;
  font-size: 14px;
}
.policy-content {
  padding: 20px 0;
  line-height: 1.8;
  font-size: 16px;
  color: #606266;
}
.policy-content :deep(p) {
  margin-bottom: 16px;
}
.policy-content :deep(img) {
  max-width: 100%;
  height: auto;
  margin: 16px 0;
}
.policy-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
  padding: 20px 0;
}

/* 登录提示弹窗样式 */
.login-dialog {
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}
.login-dialog :deep(.el-dialog__header) {
  display: none;
}
.login-dialog :deep(.el-dialog__body) {
  padding: 0;
}
.login-dialog-content {
  padding: 40px 30px;
  text-align: center;
}
.login-icon-wrapper {
  margin-bottom: 24px;
}
.login-icon-bg {
  width: 80px;
  height: 80px;
  margin: 0 auto;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
  animation: float 3s ease-in-out infinite;
}
@keyframes float {
  0%, 100% { transform: translateY(0px); }
  50% { transform: translateY(-8px); }
}
.login-title {
  font-size: 24px;
  font-weight: 700;
  color: #303133;
  margin: 0 0 16px 0;
  letter-spacing: 1px;
}
.login-desc {
  font-size: 15px;
  color: #909399;
  line-height: 1.8;
  margin: 0 0 32px 0;
}
.login-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}
.login-actions .el-button {
  min-width: 120px;
  border-radius: 8px;
  font-weight: 500;
}
.login-actions .btn-cancel {
  background: #f5f7fa;
  border-color: #e4e7ed;
  color: #606266;
}
.login-actions .btn-cancel:hover {
  background: #e8eaed;
  border-color: #c0c4cc;
}
.login-actions .btn-login {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}
.login-actions .btn-login:hover {
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
  transform: translateY(-2px);
}
</style>

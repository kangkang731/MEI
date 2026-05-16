<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('policy.title') }}</h1>
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
        <!-- 藏汉双语切换 -->
        <el-tooltip :content="$t('common.switchLang')" placement="bottom">
          <el-button class="lang-btn" @click="switchLang">
            <span class="lang-icon">藏/汉</span>
          </el-button>
        </el-tooltip>
      </div>
    </header>

    <el-main>
      <div class="section-wrapper">
        <!-- 面包屑 -->
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('policy.title') }}</div>

        <!-- 搜索栏 -->
        <div class="search-bar">
          <el-input
            v-model="searchKey"
            :placeholder="t('policy.searchPlaceholder')"
            class="search-input"
            clearable
            @keyup.enter="searchPolicy"
            @clear="searchPolicy"
          >
            <template #prefix
              ><el-icon><Search /></el-icon
            ></template>
          </el-input>
          <el-button type="primary" @click="searchPolicy">{{ t('common.search') }}</el-button>
        </div>

        <!-- 政策分类 -->
        <el-tabs v-model="activeTab" class="policy-tabs">
          <el-tab-pane :label="t('policy.nationalPolicy')" name="national"></el-tab-pane>
          <el-tab-pane :label="t('policy.xzPolicy')" name="xz"></el-tab-pane>
          <el-tab-pane :label="t('policy.cityPolicy')" name="city"></el-tab-pane>
        </el-tabs>

        <!-- 政策列表 -->
        <div class="policy-list" v-if="policyList.length > 0">
          <el-card v-for="policy in policyList" :key="policy.id" class="policy-card" shadow="hover">
            <div class="policy-header">
              <el-tag :type="policy.type === '0' ? 'danger' : 'warning'" size="small">
                {{ policy.type === '0' ? t('common.policyTag') : t('common.noticeTag') }}
              </el-tag>
              <h3>{{ policy.title }}</h3>
              <span class="publish-time">{{ policy.create_time?.substring(0, 10) }}</span>
            </div>
            <div class="policy-body">
              <p>{{ policy.content?.substring(0, 150) }}...</p>
            </div>
            <div class="policy-footer">
              <el-button type="primary" size="small" round @click="viewPolicy(policy.id)"
                >{{ t('policy.policyInterpret') }}</el-button
              >
              <el-button size="small" round @click="collectPolicy(policy.id)">{{ t('policy.collect') }}</el-button>
              <el-button size="small" round @click="downloadPolicy(policy.id)">{{ t('policy.download') }}</el-button>
            </div>
          </el-card>
        </div>

        <el-empty v-else :description="t('policy.noPolicyData')" :image-size="80" />

        <!-- 分页 -->
        <el-pagination
          v-if="total > 0"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-sizes="[5, 10, 20]"
          v-model:page-size="pageSize"
          v-model:current-page="currentPage"
          @size-change="loadPolicyList"
          @current-change="loadPolicyList"
          class="pagination"
        />
      </div>
    </el-main>

    <footer class="tibet-footer">
      <div class="section-wrapper">
        <p>{{ t('common.footerFull') }}</p>
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
import { ref, watch, onMounted } from 'vue'
import request from '@/utils/request'
import { notify } from '@/utils/notify'
import { Search, Lock } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

const { t, locale } = useI18n()

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const searchKey = ref('')
const activeTab = ref('xz')
const policyList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const router = useRouter()
const menuActive = '/policy'
const handleMenuSelect = (index) => router.push(index)
const loginDialog = ref(false)

// 加载政策列表
const loadPolicyList = async () => {
  try {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
    }

    if (searchKey.value && searchKey.value.trim()) {
      params.keyword = searchKey.value.trim()
      params.title = searchKey.value.trim()
    }
    // 标签页映射到 type 字段：国家级=全部, 自治区=政策(0), 地市=公告(1)
    const typeMap = { national: '', xz: '0', city: '1' }
    if (activeTab.value && typeMap[activeTab.value] !== undefined && typeMap[activeTab.value] !== '') {
      params.type = typeMap[activeTab.value]
    }
    
    console.log('请求参数:', params)
    
    const res = await request.get('/mei/policy/list', { params })

    console.log('政策列表响应:', res.data)

    if (res.data?.code === 200) {
      // 适配 TableDataInfo 格式（有 rows 字段）
      if (res.data.rows !== undefined) {
        policyList.value = res.data.rows || []
        total.value = res.data.total || 0
      } 
      // 适配 AjaxResult 格式（有 data.list 字段）
      else if (res.data.data) {
        policyList.value = res.data.data.list || res.data.data || []
        total.value = res.data.data.total || 0
      }
      else {
        policyList.value = []
        total.value = 0
      }
      
      console.log('政策列表数据:', policyList.value)
      console.log('总数:', total.value)
    } else {
      notify('error', res.data?.message || res.data?.msg || t('policy.loadFailed'))
      policyList.value = []
      total.value = 0
    }
  } catch (error) {
    console.error('加载政策列表失败:', error)
    const msg = error.response?.data?.msg || error.response?.data?.message || error.message || t('policy.loadFailed')
    notify('error', msg)
    policyList.value = []
    total.value = 0
  }
}

const searchPolicy = () => {
  currentPage.value = 1
  loadPolicyList()
}
const viewPolicy = (id) => {
  router.push(`/policy/${id}`)
}
const collectPolicy = async (id) => {
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  if (!token || !userId) {
    notify('warning', t('policy.loginToCollect'))
    loginDialog.value = true
    return
  }

  try {
    const policy = policyList.value.find(p => p.id == id)
    const policyTitle = policy ? policy.title : t('common.unknown')

    const res = await request.post('/mei/favorite/add', {
      userId: Number(userId),
      policyId: Number(id),
      policyTitle: policyTitle
    })

    if (res.data?.code === 200) {
      notify('success', t('policy.collectSuccess'))
    } else {
      notify('error', res.data?.msg || t('policy.collectFailed'))
    }
  } catch (error) {
    console.error('收藏失败:', error)
    const msg = error.response?.data?.msg || error.response?.data?.message || error.message || t('policy.collectFailed')
    notify('error', msg)
  }
}

// 跳转到登录页
const goToLogin = () => {
  loginDialog.value = false
  router.push('/login')
}
const downloadPolicy = async (id) => {
  try {
    const policy = policyList.value.find(p => p.id == id)
    const title = policy?.title || t('common.policy2')
    const content = policy?.content || ''

    // 生成文本文件内容
    const text = `【${title}】\n\n${content}\n\n--- ${t('common.platformTitle')} ---`
    const blob = new Blob([text], { type: 'application/msword' })
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = `${title}.doc`
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    URL.revokeObjectURL(url)
    notify('success', t('policy.downloadStarted'))
  } catch (e) {
    console.error('下载失败:', e)
    notify('error', t('policy.downloadFailed'))
  }
}

watch(activeTab, () => {
  currentPage.value = 1
  loadPolicyList()
})
onMounted(loadPolicyList)
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
/* 头部右侧 */
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
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}
.search-input {
  flex: 1;
  max-width: 400px;
}
.policy-tabs {
  margin-bottom: 20px;
}
.policy-card {
  margin-bottom: 16px;
}
.policy-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}
.policy-header h3 {
  flex: 1;
  font-size: 16px;
}
.publish-time {
  color: #999;
  font-size: 12px;
  white-space: nowrap;
}
.policy-body p {
  color: #666;
  line-height: 1.6;
  font-size: 14px;
}
.policy-footer {
  margin-top: 12px;
  display: flex;
  gap: 8px;
}
.pagination {
  margin-top: 24px;
  text-align: right;
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

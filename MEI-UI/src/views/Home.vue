<template>
  <div class="home-container page-container">
    <!-- 藏族装饰条纹 -->
    <div class="tibet-stripe"></div>

    <!-- 头部导航 -->
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ $t('home.title') }}</h1>
      </div>
      <el-menu :default-active="$route.name" mode="horizontal" @select="handleMenuSelect">
        <el-menu-item index="home">{{ $t('common.home') }}</el-menu-item>
        <el-menu-item index="policy">{{ $t('common.policy') }}</el-menu-item>
        <el-menu-item index="enterpriseEntry">{{ $t('common.enterpriseEntry') }}</el-menu-item>
        <el-menu-item index="projectApply">{{ $t('common.projectApply') }}</el-menu-item>
        <el-menu-item index="achievement">{{ $t('common.achievement') }}</el-menu-item>
        <el-menu-item index="financing">{{ $t('common.financing') }}</el-menu-item>
        <el-menu-item index="talent">{{ $t('common.talent') }}</el-menu-item>
        <el-menu-item index="consultation">{{ $t('common.consultation') }}</el-menu-item>
        <el-menu-item index="personalCenter">{{ $t('common.personalCenter') }}</el-menu-item>
      </el-menu>
      <div class="header-right">
        <!-- 藏汉双语切换 -->
        <el-tooltip :content="$t('common.switchLang')" placement="bottom">
          <el-button class="lang-btn" @click="switchLang">
            <span class="lang-icon">藏/汉</span>
          </el-button>
        </el-tooltip>
        <el-button class="login-btn" @click="goLogin" v-if="!isLoggedIn">{{ t('common.login') }}</el-button>
        <el-dropdown v-else @command="handleUserCommand">
          <span class="user-dropdown">
            <el-avatar :size="32" :src="userAvatar">{{ userName?.charAt(0) }}</el-avatar>
            <span class="user-name">{{ userName || t('home.userFallback') }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="center">{{ t('home.personalCenter') }}</el-dropdown-item>
              <el-dropdown-item command="logout">{{ t('common.logout') }}</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </header>

    <!-- 布达拉宫巨幅轮播 Banner -->
    <div class="hero-banner" :style="{ backgroundImage: 'url(' + bannerBg + ')' }">
      <div class="hero-overlay"></div>
      <div class="hero-content">
        <h2 class="hero-title">{{ t('home.heroTitle') }}</h2>
        <p class="hero-subtitle">{{ t('home.heroSubtitle') }}</p>
        <div class="hero-stats">
          <div class="stat-item">
            <span class="stat-num">{{ stats.companyNum }}</span>
            <span class="stat-label">{{ t('home.statCompany') }}</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-num">{{ stats.projectNum }}</span>
            <span class="stat-label">{{ t('home.statProject') }}</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-item">
            <span class="stat-num">{{ stats.achievementNum }}</span>
            <span class="stat-label">{{ t('home.statAchievement') }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 快捷入口 -->
    <section class="quick-entry">
      <div class="section-wrapper">
        <div class="entry-grid">
          <router-link to="/policy" class="entry-card">
            <div class="entry-icon" style="background:#C41E3A"><el-icon><Document /></el-icon></div>
            <span>{{ t('home.policyService') }}</span>
          </router-link>
          <router-link to="/enterprise-entry" class="entry-card">
            <div class="entry-icon" style="background:#DAA520"><el-icon><OfficeBuilding /></el-icon></div>
            <span>{{ t('home.enterpriseEntry') }}</span>
          </router-link>
          <router-link to="/project-apply" class="entry-card">
            <div class="entry-icon" style="background:#1a3a5c"><el-icon><FolderAdd /></el-icon></div>
            <span>{{ t('home.projectApply') }}</span>
          </router-link>
          <router-link to="/financing" class="entry-card">
            <div class="entry-icon" style="background:#2E7D32"><el-icon><Money /></el-icon></div>
            <span>{{ t('home.financingService') }}</span>
          </router-link>
          <router-link to="/talent" class="entry-card">
            <div class="entry-icon" style="background:#6A1B9A"><el-icon><User /></el-icon></div>
            <span>{{ t('home.talentService') }}</span>
          </router-link>
          <router-link to="/achievement" class="entry-card">
            <div class="entry-icon" style="background:#E65100"><el-icon><Trophy /></el-icon></div>
            <span>{{ t('home.achievementDisplay') }}</span>
          </router-link>
        </div>
      </div>
    </section>

    <!-- 核心内容区 -->
    <el-main>
      <div class="section-wrapper">
        <el-row :gutter="24">
          <!-- 左侧：政策公告 -->
          <el-col :span="14">
            <div class="content-card">
              <div class="card-title">
                <span class="title-dot"></span>
                <h2>{{ $t('home.policyNotice') }}</h2>
                <router-link to="/policy" class="more-link">{{ t('common.more') }}</router-link>
              </div>
              <div class="policy-list" v-if="policyList.length > 0">
                <div v-for="item in policyList" :key="item.id" class="policy-row" @click="viewPolicy(item)">
                  <el-tag :type="item.type === '0' ? 'danger' : 'warning'" size="small" class="policy-tag">
                    {{ item.type === '0' ? t('common.policyTag') : t('common.noticeTag') }}
                  </el-tag>
                  <span class="policy-title">{{ item.title }}</span>
                  <span class="policy-time">{{ item.create_time?.substring(0,10) }}</span>
                </div>
              </div>
              <el-empty v-else :description="t('home.noPolicyNotice')" :image-size="80" />
            </div>
          </el-col>

          <!-- 右侧：双创数据统计 -->
          <el-col :span="10">
            <div class="content-card">
              <div class="card-title">
                <span class="title-dot" style="background: var(--tibet-gold)"></span>
                <h2>{{ $t('home.dataStatistics') }}</h2>
              </div>
              <div ref="chartRef" class="chart-container"></div>
            </div>
          </el-col>
        </el-row>

        <!-- 成果展示 -->
        <div class="content-card" style="margin-top: 24px;">
          <div class="card-title">
            <span class="title-dot" style="background: #E65100"></span>
            <h2>{{ $t('home.achievementShow') }}</h2>
            <router-link to="/achievement" class="more-link">{{ t('common.more') }}</router-link>
          </div>
          <el-row :gutter="20" v-if="achievementList.length > 0">
            <el-col :span="8" v-for="item in achievementList" :key="item.id">
              <el-card :body-style="{ padding: '0' }" class="achievement-card" shadow="hover">
                <el-image :src="item.cover || defaultCover" class="achievement-cover" fit="cover" />
                <div class="achievement-info">
                  <h3>{{ item.title }}</h3>
                  <p>{{ item.content?.substring(0, 50) }}...</p>
                  <el-button type="primary" size="small" round @click="viewDetail(item)">{{ t('home.viewDetail') }}</el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <el-empty v-else :description="t('home.noAchievementData')" :image-size="80" />
        </div>
      </div>
    </el-main>

    <!-- 底部 -->
    <footer class="tibet-footer">
      <div class="section-wrapper">
        <p>{{ t('common.footerFull') }}</p>
        <p class="gold-text">{{ t('common.icp') }} | {{ t('common.techSupport') }}</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, onActivated, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import * as echarts from 'echarts'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document, OfficeBuilding, FolderAdd, Money, User, Trophy } from '@element-plus/icons-vue'

const router = useRouter()
const { t, locale } = useI18n()

const bannerBg = ref('/src/assets/banner-potala.jpg')
const defaultCover = '/src/assets/logo.png'

const resolveCover = (cover) => {
  if (!cover) return ''
  return cover.replace(/^https?:\/\/[^/]+/, '')
}
const chartRef = ref(null)
let chartInstance = null

const isLoggedIn = ref(!!localStorage.getItem('token'))
const userName = ref(localStorage.getItem('userName') || '')
const userAvatar = ref(localStorage.getItem('avatar') || '')

// 统计数据
const stats = ref({
  companyNum: 0,
  projectNum: 0,
  achievementNum: 0
})

// 政策列表
const policyList = ref([])
// 成果列表
const achievementList = ref([])

// 加载政策公告
const loadPolicies = async () => {
  try {
    const res = await axios.get('/mei/policy/list', { params: { pageNum: 1, pageSize: 6 } })
    if (res.data?.code === 200) {
      policyList.value = res.data.data?.list || res.data.data || []
    } else {
      ElMessage.error(res.data?.message || t('home.loadPolicyFailed'))
      policyList.value = []
    }
  } catch (e) {
    console.error('加载政策公告失败:', e)
    const msg = e.response?.data?.msg || e.response?.data?.message || e.message || t('home.loadPolicyError')
    ElMessage.error(msg)
    policyList.value = []
  }
}


// 加载成果列表
const loadAchievements = async () => {
  try {
    const res = await axios.get('/mei_achievement', { 
      params: { 
        isShow: '1'
      } 
    })
    const data = res.data
    if (data.code === 200) {
      const list = data.data || data.rows || []
      achievementList.value = list.slice(0, 3).map(item => ({
        id: item.id,
        title: item.title || item.achievementName,
        content: item.content || item.description,
        cover: resolveCover(item.coverImage || item.cover)
      }))
    } else {
      achievementList.value = []
    }
  } catch (e) {
    console.error('加载成果失败:', e)
    achievementList.value = []
  }
}

// 加载统计数据
const loadStats = async () => {
  try {
    const res = await axios.get('/mei/statistics/list', {
      params: {
        pageNum: 1,
        pageSize: 1
      }
    })
    const data = res.data
    if (data.code === 200 && data.rows && data.rows.length > 0) {
      const latest = data.rows[0]
      stats.value = {
        companyNum: latest.companyCount || '120+',
        projectNum: latest.projectCount || '80+',
        achievementNum: latest.achievementCount || '45+'
      }
    } else {
      stats.value = { companyNum: '120+', projectNum: '80+', achievementNum: '45+' }
    }
  } catch (e) {
    console.error('加载统计失败:', e)
    stats.value = { companyNum: '120+', projectNum: '80+', achievementNum: '45+' }
  }
}

// 语言切换
const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
  ElMessage.success(newLang === 'bo-CN' ? t('home.switchToBo') : t('home.switchToZh'))
}

// 登录跳转
const goLogin = () => {
  router.push('/login')
}

// 处理菜单选择（包括折叠后的菜单项）
const handleMenuSelect = (index) => {
  const routeMap = {
    home: '/',
    policy: '/policy',
    enterpriseEntry: '/enterprise-entry',
    projectApply: '/project-apply',
    achievement: '/achievement',
    financing: '/financing',
    talent: '/talent',
    consultation: '/consultation',
    personalCenter: '/personal-center'
  }
  
  const path = routeMap[index]
  if (path) {
    router.push(path)
  }
}

// 刷新用户登录状态（页面激活时调用）
const refreshUserState = () => {
  const token = localStorage.getItem('token')
  isLoggedIn.value = !!token
  userName.value = localStorage.getItem('userName') || ''
  userAvatar.value = localStorage.getItem('avatar') || ''
}

// 用户下拉菜单
const handleUserCommand = (cmd) => {
  if (cmd === 'center') router.push('/personal-center')
  else if (cmd === 'logout') {
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    localStorage.removeItem('userName')
    localStorage.removeItem('username')
    localStorage.removeItem('nickName')
    localStorage.removeItem('avatar')
    localStorage.removeItem('userAvatar')
    localStorage.removeItem('email')
    localStorage.removeItem('phonenumber')
    localStorage.removeItem('sex')
    isLoggedIn.value = false
    ElMessage.success(t('home.logoutSuccess'))
  }
}

// 查看政策
const viewPolicy = (item) => {
  router.push('/policy')
}

// 查看详情
const viewDetail = (item) => {
  router.push(`/achievement/${item.id}`)
}

// 初始化图表
const initChart = () => {
  chartInstance = echarts.init(chartRef.value)
  const option = {
    tooltip: { trigger: 'axis' },
    legend: {
      data: [t('home.legendCompany'), t('home.legendProject')],
      bottom: 0,
      textStyle: { color: '#666' }
    },
    grid: { left: '3%', right: '4%', bottom: '20%', top: '5%', containLabel: true },
    xAxis: {
      type: 'category',
      data: [t('home.regionLhasa'), t('home.regionShigatse'), t('home.regionShannan'), t('home.regionNyingchi'), t('home.regionChamdo'), t('home.regionNagqu'), t('home.regionAli')],
      axisLabel: { color: '#999' }
    },
    yAxis: { type: 'value', splitLine: { lineStyle: { color: '#f0e8d8' } } },
    series: [
      {
        name: t('home.legendCompany'),
        type: 'bar',
        data: [120, 80, 50, 60, 40, 30, 20],
        itemStyle: {
          borderRadius: [4, 4, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#C41E3A' },
            { offset: 1, color: '#E85D75' }
          ])
        }
      },
      {
        name: t('home.legendProject'),
        type: 'bar',
        data: [80, 50, 30, 40, 25, 15, 10],
        itemStyle: {
          borderRadius: [4, 4, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#DAA520' },
            { offset: 1, color: '#F0C860' }
          ])
        }
      }
    ]
  }
  chartInstance.setOption(option)
}

onActivated(() => {
  refreshUserState()
})

onMounted(() => {
  refreshUserState()
  loadPolicies()
  loadAchievements()
  loadStats()
  initChart()
  
  // 正确的 resize 事件处理
  const handleResize = () => {
    if (chartInstance) {
      chartInstance.resize()
    }
  }
  window.addEventListener('resize', handleResize)
  
  // 保存处理函数引用以便卸载时移除
  window._homeResizeHandler = handleResize
})

// 语言切换时重新初始化图表
watch(locale, () => {
  if (chartInstance) {
    chartInstance.dispose()
  }
  initChart()
})

onUnmounted(() => {
  // 移除 resize 事件监听
  if (window._homeResizeHandler) {
    window.removeEventListener('resize', window._homeResizeHandler)
    window._homeResizeHandler = null
  }
  
  // 安全销毁 ECharts 实例
  if (chartInstance) {
    try {
      chartInstance.dispose()
      chartInstance = null
    } catch (e) {
      console.warn('ECharts 实例销毁失败:', e)
    }
  }
})
</script>

<style scoped>
.home-container {
  padding-top: 0;
}

/* Logo */
.logo-img {
  width: 42px;
  height: 42px;
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
.login-btn {
  background: rgba(255,255,255,0.2) !important;
  border: none !important;
  color: #fff !important;
  border-radius: 6px !important;
}
.user-dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #fff;
}
.user-name {
  font-size: 14px;
  color: var(--tibet-gold);
}

/* ===== 英雄Banner ===== */
.hero-banner {
  position: relative;
  height: 420px;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, rgba(44,24,16,0.7), rgba(26,58,92,0.5));
}
.hero-content {
  position: relative;
  z-index: 2;
  text-align: center;
  color: #fff;
}
.hero-title {
  font-size: 42px;
  font-weight: bold;
  text-shadow: 2px 2px 8px rgba(0,0,0,0.5);
  margin-bottom: 12px;
}
.hero-subtitle {
  font-size: 18px;
  opacity: 0.9;
  margin-bottom: 40px;
}
.hero-stats {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
}
.stat-item {
  text-align: center;
}
.stat-num {
  display: block;
  font-size: 36px;
  font-weight: bold;
  color: var(--tibet-gold);
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}
.stat-label {
  font-size: 14px;
  opacity: 0.85;
  margin-top: 4px;
  display: block;
}
.stat-divider {
  width: 2px;
  height: 50px;
  background: rgba(255,255,255,0.3);
}

/* 容器 */
.section-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* ===== 快捷入口 ===== */
.quick-entry {
  margin-top: -50px;
  position: relative;
  z-index: 3;
  padding-bottom: 20px;
}
.entry-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
}
.entry-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 24px 10px;
  background: #fff;
  border-radius: 12px;
  text-decoration: none;
  color: #333;
  box-shadow: 0 4px 16px rgba(0,0,0,0.08);
  transition: all 0.3s ease;
  border: 1px solid var(--tibet-border);
}
.entry-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(196, 30, 58, 0.15);
  border-color: var(--tibet-gold);
}
.entry-icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 24px;
}
.entry-card span {
  font-size: 14px;
  font-weight: 500;
}

/* 内容卡片 */
.content-card {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  border: 1px solid var(--tibet-border);
}
.card-title {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 2px solid #f0e8d8;
}
.title-dot {
  width: 4px;
  height: 20px;
  background: var(--tibet-red);
  border-radius: 2px;
}
.card-title h2 {
  font-size: 18px;
  flex: 1;
}
.more-link {
  font-size: 13px;
  color: var(--tibet-red);
  text-decoration: none;
}
.more-link:hover { color: var(--tibet-gold); }

/* 政策列表 */
.policy-row {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px dashed #f0e8d8;
  cursor: pointer;
  transition: all 0.2s;
}
.policy-row:hover {
  padding-left: 8px;
  background: rgba(218, 165, 32, 0.05);
}
.policy-tag { flex-shrink: 0; }
.policy-title {
  flex: 1;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #444;
}
.policy-time {
  font-size: 12px;
  color: #999;
  flex-shrink: 0;
}

/* 图表 */
.chart-container {
  width: 100%;
  height: 320px;
}

/* 成果卡片 */
.achievement-card {
  border-radius: 12px !important;
  overflow: hidden;
  transition: all 0.3s;
}
.achievement-card:hover {
  transform: translateY(-4px);
}
.achievement-cover {
  width: 100%;
  height: 160px;
  display: block;
}
.achievement-info {
  padding: 16px;
}
.achievement-info h3 {
  font-size: 16px;
  margin-bottom: 8px;
}
.achievement-info p {
  font-size: 13px;
  color: #666;
  margin-bottom: 12px;
  line-height: 1.5;
}

/* 响应式 */
@media (max-width: 768px) {
  .entry-grid { grid-template-columns: repeat(3, 1fr); }
  .hero-title { font-size: 28px; }
  .hero-banner { height: 300px; }
  .hero-stats { gap: 20px; }
  .stat-num { font-size: 28px; }
}
</style>

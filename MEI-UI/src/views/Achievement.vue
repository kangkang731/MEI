<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('achievement.title') }}</h1>
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
      <div class="section-wrapper">
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('achievement.title') }}</div>

        <!-- 筛选 -->
        <el-card shadow="never" class="filter-card">
          <el-form :inline="true" :model="searchForm">
            <el-form-item :label="t('achievement.projectId')">
              <el-input v-model="searchForm.projectId" :placeholder="t('achievement.projectIdPlaceholder')" style="width:160px" />
            </el-form-item>
            <el-form-item :label="t('achievement.displayStatus')">
              <el-select v-model="searchForm.isShow" :placeholder="t('achievement.all')" style="width:120px">
                <el-option :label="t('achievement.all')" value="" />
                <el-option :label="t('achievement.showing')" value="1" />
                <el-option :label="t('achievement.hidden')" value="0" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="fetchData">{{ t('achievement.query') }}</el-button>
              <el-button @click="searchForm.projectId=''; searchForm.isShow=''; fetchData()">{{ t('achievement.reset') }}</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 成果网格 -->
        <div class="achievement-grid" v-loading="loading">
          <el-card v-for="item in achievementList" :key="item.id" shadow="hover" class="ach-card">
            <el-image :src="item.cover || defaultCover" fit="cover" class="ach-cover" :preview-src-list="[item.cover]" />
            <div class="ach-body">
              <div class="ach-header">
                <el-tag size="small" type="primary">#{{ item.project_id }}{{ t('achievement.projectSuffix') }}</el-tag>
                <el-tag size="small" :type="item.is_show === '1' ? 'success' : 'danger'">
                  {{ item.is_show === '1' ? t('achievement.showing') : t('achievement.hidden') }}
                </el-tag>
              </div>
              <h3 class="ach-title">{{ item.title }}</h3>
              <p class="ach-desc">{{ item.content?.substring(0,80) }}...</p>
              <div class="ach-footer">
                <span class="ach-time">{{ item.create_time?.substring(0,10) }}</span>
                <el-button type="primary" size="small" round @click="viewDetail(item.id)">{{ t('achievement.viewDetail') }}</el-button>
              </div>
            </div>
          </el-card>
        </div>
        <el-empty v-if="achievementList.length === 0 && !loading" :description="t('achievement.noData')" :image-size="80" />
      </div>
    </el-main>

    <footer class="tibet-footer">
      <div class="section-wrapper">
        <p>{{ t('common.copyright') }}</p>
        <p class="gold-text">{{ t('common.icp') }}</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import axios from 'axios'
import { notify } from '@/utils/notify'

const { t, locale } = useI18n()

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const router = useRouter()
const menuActive = '/achievement'
const handleMenuSelect = (index) => router.push(index)

const defaultCover = '/src/assets/logo.png'
const searchForm = reactive({ projectId: '', isShow: '' })
const achievementList = ref([])
const loading = ref(false)

// 数据库存储的图片路径可能带服务器地址前缀，去掉后通过Vite代理访问
const resolveCover = (cover) => {
  if (!cover) return ''
  return cover.replace(/^https?:\/\/[^/]+/, '')
}

const fetchData = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: 1,
      pageSize: 100
    }

    if (searchForm.projectId) {
      params.projectId = searchForm.projectId
    }
    if (searchForm.isShow !== '') {
      params.isShow = searchForm.isShow
    }

    console.log('请求参数:', params)

    // 使用公开接口（不需要登录认证）
    const res = await axios.get('/mei_achievement', { params })

    console.log('成果列表响应:', res.data)

    const data = res.data

    // 适配不同的响应格式
    if (data.code === 200) {
      // TableDataInfo 格式（有 rows 字段）
      if (data.rows) {
        achievementList.value = data.rows.map(item => ({
          id: item.id,
          project_id: item.projectId,
          title: item.title || item.achievementName,
          content: item.content || item.description,
          cover: resolveCover(item.coverImage || item.cover),
          is_show: item.isShow,
          create_time: item.createTime
        }))
      }
      // AjaxResult 格式（有 data 字段）
      else if (data.data) {
        const listData = data.data.list || data.data
        achievementList.value = (Array.isArray(listData) ? listData : []).map(item => ({
          id: item.id,
          project_id: item.projectId,
          title: item.title || item.achievementName,
          content: item.content || item.description,
          cover: resolveCover(item.coverImage || item.cover),
          is_show: item.isShow,
          create_time: item.createTime
        }))
      } else {
        achievementList.value = []
      }

      console.log('成果列表数据:', achievementList.value)
    } else {
      achievementList.value = []
    }
  } catch (e) {
    console.error('获取成果列表失败:', e)
    const msg = e.response?.data?.msg || e.message || t('achievement.loadFailed')
    notify('error', msg)
    achievementList.value = []
  } finally {
    loading.value = false
  }
}

const viewDetail = (id) => {
  router.push(`/achievement/${id}`)
}

onMounted(fetchData)
</script>

<style scoped>
.section-wrapper { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.filter-card { margin-bottom: 24px; padding: 16px; }

.achievement-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(340px, 1fr));
  gap: 20px;
}
.ach-card {
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s;
}
.ach-card:hover { transform: translateY(-4px); }
.ach-cover { width: 100%; height: 180px; }
.ach-body { padding: 16px; }
.ach-header { display: flex; gap: 8px; margin-bottom: 10px; }
.ach-title { font-size: 16px; margin-bottom: 8px; }
.ach-desc { color: #666; font-size: 13px; line-height: 1.5; margin-bottom: 12px; }
.ach-footer { display: flex; justify-content: space-between; align-items: center; }
.ach-time { color: #999; font-size: 12px; }
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
</style>

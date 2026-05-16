<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('achievementDetail.title') }}</h1>
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
        <div class="breadcrumb">
          <router-link to="/">{{ t('achievementDetail.breadcrumbHome') }}</router-link> &gt;
          <router-link to="/achievement">{{ t('achievementDetail.breadcrumbAchievement') }}</router-link> &gt;
          {{ t('achievementDetail.breadcrumbDetail') }}
        </div>

        <div v-if="detail" class="detail-container">
          <el-card shadow="never" class="detail-card">
            <h2 class="detail-title">{{ detail.title }}</h2>
            <div class="detail-meta">
              <el-tag type="primary">#{{ detail.projectId }}{{ t('achievementDetail.projectSuffix') }}</el-tag>
              <el-tag :type="detail.isShow === '1' ? 'success' : 'info'">
                {{ detail.isShow === '1' ? t('achievementDetail.showing') : t('achievementDetail.hidden') }}
              </el-tag>
              <span class="detail-time">{{ detail.createTime?.substring(0, 10) }}</span>
            </div>
            <el-divider />

            <div class="detail-cover" v-if="detail.cover">
              <el-image :src="detail.cover" fit="contain" :preview-src-list="[detail.cover]" class="cover-img" />
            </div>

            <div class="detail-content">
              <h3>{{ t('achievementDetail.intro') }}</h3>
              <div class="content-text">{{ detail.content }}</div>
            </div>
          </el-card>
        </div>

        <el-empty v-else-if="!loading" :description="t('achievementDetail.noData')" :image-size="80">
          <el-button type="primary" @click="$router.push('/achievement')">{{ t('achievementDetail.backToList') }}</el-button>
        </el-empty>
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
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
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
const route = useRoute()
const menuActive = '/achievement'
const handleMenuSelect = (index) => router.push(index)

const detail = ref(null)
const loading = ref(false)

const resolveCover = (cover) => {
  if (!cover) return ''
  return cover.replace(/^https?:\/\/[^/]+/, '')
}

const fetchDetail = async () => {
  loading.value = true
  try {
    const id = route.params.id
    const res = await axios.get(`/mei_achievement/${id}`)

    if (res.data?.code === 200) {
      const item = res.data.data
      detail.value = {
        id: item.id,
        projectId: item.projectId,
        title: item.title,
        content: item.content,
        cover: resolveCover(item.cover),
        isShow: item.isShow,
        createTime: item.createTime
      }
    } else {
      notify('error', res.data?.msg || t('achievementDetail.loadFailed'))
      detail.value = null
    }
  } catch (e) {
    console.error('获取成果详情失败:', e)
    const msg = e.response?.data?.msg || e.message || t('achievementDetail.loadFailed')
    notify('error', msg)
    detail.value = null
  } finally {
    loading.value = false
  }
}

onMounted(fetchDetail)
</script>

<style scoped>
.section-wrapper { max-width: 1000px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }

.detail-container { margin-top: 8px; }
.detail-card { padding: 8px; }
.detail-title { font-size: 24px; margin-bottom: 16px; color: #303133; }
.detail-meta { display: flex; align-items: center; gap: 12px; margin-bottom: 8px; }
.detail-time { color: #999; font-size: 13px; margin-left: auto; }

.detail-cover { text-align: center; margin: 20px 0; }
.cover-img { max-width: 100%; max-height: 480px; border-radius: 8px; }

.detail-content { margin-top: 20px; }
.detail-content h3 { font-size: 18px; margin-bottom: 12px; color: #303133; }
.content-text { line-height: 2; color: #555; font-size: 15px; white-space: pre-wrap; word-break: break-word; }
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

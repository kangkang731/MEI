<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('consultation.title') }}</h1>
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
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('common.consultation') }}</div>

        <el-row :gutter="20">
          <!-- 左侧：历史咨询 -->
          <el-col :span="7">
            <el-card shadow="never" v-loading="loading">
              <div class="consult-header">
                <h3>{{ t('consultation.historyTitle') }}</h3>
                <el-button type="text" @click="clearConsult">{{ t('consultation.clearAll') }}</el-button>
              </div>
              <div v-for="item in consultList" :key="item.id" class="consult-item" @click="selectConsult(item)">
                <div class="ci-top">
                  <span class="ci-title">{{ item.title }}</span>
                  <el-tag :type="item.status === '1' ? 'success' : 'warning'" size="small">
                    {{ item.status === '1' ? t('common.replied') : t('common.pendingReply') }}
                  </el-tag>
                </div>
                <div class="ci-time">{{ item.createTime }}</div>
              </div>
              <el-empty v-if="consultList.length === 0 && !loading" :description="t('consultation.noRecords')" :image-size="60" />
            </el-card>
          </el-col>

          <!-- 右侧：咨询表单 + 回复 -->
          <el-col :span="17">
            <el-card shadow="never">
              <h3 style="margin-bottom:20px;">{{ t('consultation.submitTitle') }}</h3>
              <el-form :model="consultForm" :rules="consultRules" ref="consultFormRef" label-width="100px">
                <el-form-item :label="t('consultation.titleLabel')" prop="title">
                  <el-input v-model="consultForm.title" :placeholder="t('consultation.titlePlaceholder')" />
                </el-form-item>
                <el-form-item :label="t('consultation.typeLabel')" prop="type">
                  <el-select v-model="consultForm.type" :placeholder="t('consultation.typePlaceholder')" style="width:100%">
                    <el-option :label="t('consultation.typeProjectApply')" value="project" />
                    <el-option :label="t('consultation.typeAchievement')" value="achievement" />
                    <el-option :label="t('consultation.typeFinancing')" value="financing" />
                    <el-option :label="t('consultation.typeTalent')" value="talent" />
                    <el-option :label="t('consultation.typePolicy')" value="policy" />
                    <el-option :label="t('consultation.typeOther')" value="other" />
                  </el-select>
                </el-form-item>
                <el-form-item :label="t('consultation.contentLabel')" prop="content">
                  <el-input v-model="consultForm.content" type="textarea" rows="6" :placeholder="t('consultation.contentPlaceholder')" />
                </el-form-item>
                <el-form-item :label="t('consultation.contactLabel')" prop="contact">
                  <el-input v-model="consultForm.contact" :placeholder="t('consultation.contactPlaceholder')" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" size="large" @click="submitConsult">{{ t('consultation.submitBtn') }}</el-button>
                  <el-button size="large" @click="resetConsultForm">{{ t('consultation.resetBtn') }}</el-button>
                </el-form-item>
              </el-form>

              <!-- 回复展示 -->
              <div v-if="currentConsult?.reply" class="reply-section">
                <el-divider content-position="left"><span class="reply-label">{{ t('consultation.expertReply') }}</span></el-divider>
                <el-card shadow="inner" class="reply-card">
                  <p class="reply-content">{{ currentConsult.reply }}</p>
                  <p class="reply-time">{{ currentConsult.replyTime }}</p>
                </el-card>
              </div>
            </el-card>
          </el-col>
        </el-row>
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

const router = useRouter()
const { t, locale } = useI18n()
const menuActive = '/consultation'
const handleMenuSelect = (index) => router.push(index)

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const consultList = ref([])
const currentConsult = ref(null)
const consultFormRef = ref(null)
const loading = ref(false)
const consultForm = reactive({ title: '', type: '', content: '', contact: '' })
const consultRules = {
  title: [{ required: true, message: () => t('consultation.validateTitle'), trigger: 'blur' }],
  type: [{ required: true, message: () => t('consultation.validateType'), trigger: 'change' }],
  content: [{ required: true, message: () => t('consultation.validateContent'), trigger: 'blur' }],
  contact: [{ required: true, message: () => t('consultation.validateContact'), trigger: 'blur' }],
}

const fetchList = async () => {
  loading.value = true
  try {
    const userId = localStorage.getItem('userId')
    if (!userId) {
      return
    }

    const res = await axios.get('/mei/consult/list', {
      params: {
        userId: userId,
        pageNum: 1,
        pageSize: 100
      }
    })

    const data = res.data
    const list = data.data?.list || data.rows || []
    if (data.code === 200) {
      consultList.value = list.map(item => ({
        id: item.id,
        title: item.title,
        content: item.content,
        reply: item.reply,
        status: item.status,
        createTime: item.createTime,
        replyTime: item.replyTime,
        contact: item.contact
      }))
    } else {
      consultList.value = []
    }
  } catch (e) {
    console.error('获取咨询列表失败:', e)
    consultList.value = []
  } finally {
    loading.value = false
  }
}

const selectConsult = (item) => {
  currentConsult.value = item
  consultForm.title = item.title
  consultForm.content = item.content
  consultForm.contact = item.contact
}

const submitConsult = async () => {
  try {
    await consultFormRef.value.validate()

    // 校验登录状态
    const token = localStorage.getItem('token')
    const userId = localStorage.getItem('userId')

    if (!token || !userId) {
      notify('warning', t('consultation.loginFirst'))
      router.push('/login')
      return
    }

    const res = await axios.post('/mei/consult', {
      userId: parseInt(userId),
      title: consultForm.title,
      type: consultForm.type,
      content: consultForm.content,
      contact: consultForm.contact,
      status: '0' // 0-待回复
    })

    if (res.data.code === 200) {
      notify('success', t('consultation.submitSuccess'))
      resetConsultForm()
      fetchList()
    } else {
      notify('error', res.data.msg || t('consultation.submitFailed'))
    }
  } catch (e) {
    console.error('提交失败:', e)
    const msg = e.response?.data?.msg || e.message || t('consultation.submitFailed')
    notify('error', msg)
  }
}

const resetConsultForm = () => {
  if (consultFormRef.value) {
    consultFormRef.value.resetFields()
  }
  currentConsult.value = null
}

const clearConsult = async () => {
  consultList.value = []
  currentConsult.value = null
  notify('success', t('consultation.clearSuccess'))
}

onMounted(() => {
  if (localStorage.getItem('token')) {
    fetchList()
    // 自动填充用户手机号
    const phone = localStorage.getItem('phonenumber')
    if (phone) {
      consultForm.contact = phone
    }
  }
})
</script>

<style scoped>
.section-wrapper { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.consult-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
.consult-item { cursor: pointer; padding: 10px 0; border-bottom: 1px solid #f0e8d8; }
.consult-item:hover { padding-left: 4px; }
.ci-top { display: flex; justify-content: space-between; align-items: center; margin-bottom: 4px; }
.ci-title { font-size: 14px; margin-right: 8px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
.ci-time { color: #999; font-size: 12px; }
.reply-section { margin-top: 20px; }
.reply-label { color: var(--tibet-gold); font-weight: bold; }
.reply-card { background: #FFFCF5; }
.reply-content { line-height: 1.8; color: #444; }
.reply-time { color: #999; font-size: 12px; text-align: right; margin-top: 12px; }
.header-right {
  margin-left: auto;
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
  font-weight: bold;
}
</style>

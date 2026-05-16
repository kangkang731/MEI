<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('projectApply.title') }}</h1>
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
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('projectApply.title') }}</div>

        <el-card shadow="never" class="apply-card">
          <h2 class="page-title">{{ t('projectApply.formTitle') }}</h2>
          <p class="page-desc">{{ t('projectApply.formDesc') }}</p>
          <el-divider />

          <el-form :model="applyForm" :rules="applyRules" ref="applyFormRef" label-width="120px">
            <el-form-item :label="t('projectApply.projectName')" prop="projectName">
              <el-input v-model="applyForm.projectName" :placeholder="t('projectApply.projectNamePlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('projectApply.projectType')" prop="projectType">
              <el-select v-model="applyForm.projectType" :placeholder="t('projectApply.projectTypePlaceholder')" style="width:100%">
                <el-option :label="t('projectApply.typeHighlandIT')" value="IT" />
                <el-option :label="t('projectApply.typeTibetMedicine')" value="MEDICINE" />
                <el-option :label="t('projectApply.typeCulturalTourism')" value="CULTURE" />
                <el-option :label="t('projectApply.typeAgriTech')" value="AGRICULTURE" />
                <el-option :label="t('projectApply.typeIntangibleHeritage')" value="INTANGIBLE" />
                <el-option :label="t('projectApply.typeOther')" value="OTHER" />
              </el-select>
            </el-form-item>
            <el-form-item :label="t('projectApply.projectDesc')" prop="desc">
              <el-input v-model="applyForm.desc" type="textarea" rows="6" :placeholder="t('projectApply.projectDescPlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('projectApply.attachment')">
              <el-upload action="/mei/upload" :headers="uploadHeaders" multiple :limit="5" :on-exceed="handleExceed" :on-success="handleUploadSuccess" accept=".pdf,.doc,.docx,.jpg,.png">
                <el-button type="primary">{{ t('projectApply.uploadBtn') }}</el-button>
                <template #tip>
                  <div class="upload-tip">{{ t('projectApply.uploadTip') }}</div>
                </template>
              </el-upload>
            </el-form-item>
            <el-form-item :label="t('projectApply.applicantName')" prop="contactName">
              <el-input v-model="applyForm.contactName" :placeholder="t('projectApply.applicantNamePlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('projectApply.applicantPhone')" prop="contactPhone">
              <el-input v-model="applyForm.contactPhone" :placeholder="t('projectApply.applicantPhonePlaceholder')" maxlength="11" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" @click="submitApply">{{ t('projectApply.submitApply') }}</el-button>
              <el-button size="large" @click="resetForm">{{ t('projectApply.reset') }}</el-button>
            </el-form-item>
          </el-form>
        </el-card>

        <!-- 申报记录 -->
        <el-card shadow="never" class="apply-card" style="margin-top:24px;">
          <h2 class="page-title">{{ t('projectApply.myRecords') }}</h2>
          <el-table :data="applyRecords" border style="width:100%" v-loading="loading">
            <el-table-column prop="id" :label="t('projectApply.columnId')" width="60" />
            <el-table-column prop="projectName" :label="t('projectApply.columnProjectName')" />
            <el-table-column prop="projectType" :label="t('projectApply.columnType')" width="120" />
            <el-table-column prop="createTime" :label="t('projectApply.columnTime')" width="180" />
            <el-table-column :label="t('projectApply.columnStatus')" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
                  {{ scope.row.status === '1' ? t('common.approved') : scope.row.status === '2' ? t('common.rejected') : t('common.reviewing') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column :label="t('projectApply.columnOperation')" width="80">
              <template #default>
                <el-button type="primary" link @click="ElMessage.info(t('common.detail'))">{{ t('projectApply.viewDetail') }}</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-empty v-if="applyRecords.length === 0" :description="t('projectApply.noRecords')" :image-size="60" />
        </el-card>
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
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import axios from 'axios'
import request from '@/utils/request'
import { notify } from '@/utils/notify'

const { t, locale } = useI18n()

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const router = useRouter()
const menuActive = '/project-apply'
const handleMenuSelect = (index) => router.push(index)

const applyFormRef = ref(null)
const loading = ref(false)

// 上传请求头
const uploadHeaders = computed(() => {
  const token = localStorage.getItem('token')
  return token ? { Authorization: 'Bearer ' + token } : {}
})

const applyForm = reactive({
  projectName: '', projectType: '', desc: '', contactName: '', contactPhone: '', fileList: []
})
const applyRecords = ref([])
const applyRules = {
  projectName: [{ required: true, message: t('projectApply.validateProjectName'), trigger: 'blur' }],
  projectType: [{ required: true, message: t('projectApply.validateProjectType'), trigger: 'change' }],
  desc: [{ required: true, message: t('projectApply.validateProjectDesc'), trigger: 'blur' }, { min: 20, message: t('projectApply.validateDescMin'), trigger: 'blur' }],
  contactName: [{ required: true, message: t('projectApply.validateApplicantName'), trigger: 'blur' }],
  contactPhone: [
    { required: true, message: t('projectApply.validateApplicantPhone'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: t('projectApply.validatePhoneFormat'), trigger: 'blur' }
  ],
}

const handleExceed = () => notify('warning', t('projectApply.maxFiles'))
const handleUploadSuccess = (res) => {
  applyForm.fileList.push(res.data?.fileId || res.fileId)
  notify('success', t('projectApply.uploadSuccess'))
}

// 获取申报记录
const fetchApplyRecords = async () => {
  loading.value = true
  try {
    const userId = localStorage.getItem('userId')
    if (!userId) {
      applyRecords.value = []
      loading.value = false
      return
    }

    const res = await request.get('/mei/project/list', {
      params: {
        userId: userId,
        pageNum: 1,
        pageSize: 100
      }
    })

    console.log('申报记录响应:', res.data)

    if (res.data?.code === 200) {
      const raw = res.data.data
      const data = raw?.list || raw?.rows || raw || []
      if (Array.isArray(data)) {
        applyRecords.value = data.map(item => ({
          id: item.id,
          projectName: item.projectName,
          projectType: item.projectType,
          createTime: item.createTime,
          status: item.status
        }))
      } else {
        applyRecords.value = []
      }
    } else {
      applyRecords.value = []
    }
  } catch (error) {
    console.error('获取申报记录失败:', error)
    applyRecords.value = []
  } finally {
    loading.value = false
  }
}

const submitApply = async () => {
  try {
    await applyFormRef.value.validate()

    // 校验登录状态
    const token = localStorage.getItem('token')
    const userId = localStorage.getItem('userId')

    if (!token || !userId) {
      notify('warning', t('projectApply.loginFirst'))
      router.push('/login')
      return
    }

    console.log('提交申报数据:', applyForm)

    const res = await request.post('/mei/project/apply', {
      userId: parseInt(userId),
      projectName: applyForm.projectName,
      projectType: applyForm.projectType,
      desc: applyForm.desc,
      contact: applyForm.contactName + ' ' + applyForm.contactPhone,
      contactName: applyForm.contactName,
      contactPhone: applyForm.contactPhone,
      fileList: applyForm.fileList
    })

    console.log('申报响应:', res.data)

    if (res.data?.code === 200) {
      notify('success', t('projectApply.submitSuccess'))
      resetForm()
      fetchApplyRecords() // 刷新申报记录
    } else {
      notify('error', res.data?.msg || t('projectApply.submitFailed'))
    }
  } catch (err) {
    console.error('提交失败:', err)
    if (err.response?.data?.msg) {
      notify('error', err.response.data.msg)
    } else if (err.response?.status === 401) {
      notify('warning', t('projectApply.loginFirst'))
      router.push('/login')
    } else if (err.response?.status === 403) {
      notify('error', t('projectApply.noPermission'))
    } else if (!err.response) {
      notify('error', t('projectApply.networkError'))
    }
  }
}

const resetForm = () => {
  if (applyFormRef.value) {
    applyFormRef.value.resetFields()
  }
  applyForm.fileList = []
}

onMounted(() => {
  fetchApplyRecords()
})
</script>

<style scoped>
.section-wrapper { max-width: 900px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.apply-card { padding: 32px; border-radius: 12px; }
.page-title { font-size: 20px; margin-bottom: 8px; }
.page-desc { color: #666; font-size: 14px; }
.upload-tip { color: #999; font-size: 12px; margin-top: 4px; }
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

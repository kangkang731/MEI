<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('talent.title') }}</h1>
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
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('common.talent') }}</div>

        <!-- 招聘信息 -->
        <el-table
          v-loading="loading"
          :data="recruitList"
          border
          style="width:100%; margin-top:16px;"
          :empty-text="t('talent.noData')"
        >
          <el-table-column prop="company" :label="t('talent.columnCompany')" width="180" />
          <el-table-column prop="position" :label="t('talent.columnJob')" width="150" />
          <el-table-column prop="salary" :label="t('talent.columnSalary')" width="120">
            <template #default="scope">
              <span class="salary-text">{{ scope.row.salary }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="education" :label="t('talent.columnEducation')" width="100" />
          <el-table-column prop="experience" :label="t('talent.columnExperience')" width="100" />
          <el-table-column prop="address" :label="t('talent.columnLocation')" width="120" />
          <el-table-column prop="requirement" :label="t('talent.columnRequirement')" show-overflow-tooltip />
          <el-table-column :label="t('talent.columnOperation')" width="180" fixed="right">
            <template #default="scope">
              <el-button type="primary" link @click="viewRecruit(scope.row)">{{ t('talent.viewDetail') }}</el-button>
              <el-button type="success" link @click="applyPosition(scope.row)">{{ t('talent.applyNow') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 招聘详情弹窗 -->
        <el-dialog v-model="recruitDialog" :title="t('talent.dialogTitle')" width="600px">
          <div v-if="currentRecruit" class="recruit-detail">
            <h3>{{ currentRecruit.position }}</h3>
            <p><span class="label">{{ t('talent.labelCompany') }}</span>{{ currentRecruit.company }}</p>
            <p><span class="label">{{ t('talent.labelSalary') }}</span>{{ currentRecruit.salary }}</p>
            <p><span class="label">{{ t('talent.labelLocation') }}</span>{{ currentRecruit.address }}</p>
            <p><span class="label">{{ t('talent.labelRequirement') }}</span>{{ currentRecruit.requirement }}</p>
            <el-button type="primary" size="large" @click="applyPosition(currentRecruit)" style="margin-top:16px;">{{ t('talent.applyNow') }}</el-button>
          </div>
        </el-dialog>
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
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import axios from 'axios'
import { ElMessageBox, ElMessage } from 'element-plus'
import { notify } from '@/utils/notify'

const router = useRouter()
const { t, locale } = useI18n()
const menuActive = '/talent'
const handleMenuSelect = (index) => router.push(index)

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const recruitList = ref([])
const recruitDialog = ref(false)
const currentRecruit = ref(null)
const loading = ref(false)

// 获取招聘信息列表
const fetchRecruit = async () => {
  loading.value = true
  try {
    console.log('========== 开始请求招聘信息 ==========')
    console.log('请求URL: /mei/talent/recruit')

    const res = await axios.get('/mei/talent/recruit')

    console.log('响应数据:', res.data)

    if (res.data?.code === 200) {
      const data = res.data.data || []
      console.log('原始数据:', data)

      // 后端返回的数据需要转换格式以匹配前端显示
      recruitList.value = data.map(item => ({
        id: item.id,
        companyId: item.companyId,
        company: item.companyName || t('talent.defaultCompany'),
        position: item.jobName,
        salary: item.salary || t('common.faceToFace'),
        address: item.workLocation || '',
        requirement: item.requirement || t('common.noRequirement'),
        education: item.education || t('common.noLimit'),
        experience: item.experience || t('common.noLimit'),
        createTime: item.createTime
      }))

      console.log('转换后的招聘列表:', recruitList.value)
    } else {
      notify('warning', res.data?.msg || t('talent.noData'))
      recruitList.value = []
    }
  } catch (error) {
    console.error('获取招聘信息失败:', error)
    const msg = error.response?.data?.msg || error.message || t('talent.loadFailed')
    notify('error', msg)
    recruitList.value = []
  } finally {
    loading.value = false
  }
}

// 查看招聘详情
const viewRecruit = (row) => {
  currentRecruit.value = row
  recruitDialog.value = true
}

// 立即应聘
const applyPosition = async (row) => {
  const token = localStorage.getItem('token')
  if (!token) {
    ElMessageBox.confirm(t('talent.loginFirst'), t('common.login'), {
      confirmButtonText: t('talent.loginConfirmButtons')[0],
      cancelButtonText: t('talent.loginConfirmButtons')[1],
      type: 'warning'
    }).then(() => {
      router.push('/login')
    })
    return
  }

  const userId = localStorage.getItem('userId')
  if (!userId) {
    notify('warning', t('talent.loginFirst'))
    router.push('/login')
    return
  }

  try {
    // 检查是否已填写求职登记
    const checkRes = await axios.get('/mei/talent/check', { params: { userId } })
    const hasRegistered = checkRes.data?.code === 200 && (checkRes.data.data === true || checkRes.data.data === 'true')
    if (hasRegistered) {
      // 已登记，直接提交应聘
      const res = await axios.post('/mei/talent/apply', {
        userId: parseInt(userId),
        position: row.position || '',
        name: '',
        phone: '',
        experience: '',
        resumeUrl: ''
      })
      if (res.data?.code === 200) {
        notify('success', t('talent.applySuccess'))
        recruitDialog.value = false
      } else {
        notify('error', res.data?.msg || t('talent.applyFailed'))
      }
    } else {
      // 未登记，提示先填写
      notify('warning', t('talent.fillJobInfo'))
    }
  } catch (error) {
    console.error('应聘失败:', error)
    const msg = error.response?.data?.msg || error.message || t('talent.applyFailed')
    notify('error', msg)
  }
}

onMounted(() => {
  fetchRecruit()
})
</script>

<style scoped>
.section-wrapper { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.page-title { font-size: 20px; text-align: center; }
.recruit-detail { line-height: 2; }
.label { color: #999; display: inline-block; width: 60px; }
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

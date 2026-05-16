<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('personalCenter.title') }}</h1>
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
        <el-button class="lang-btn" @click="switchLang">
          {{ locale === 'zh-CN' ? 'བོད་ཡིག' : '中文' }}
        </el-button>
      </div>
    </header>

    <el-main>
      <div class="section-wrapper">
        <div class="breadcrumb"><router-link to="/">{{ t('personalCenter.breadcrumbHome') }}</router-link> &gt; {{ t('personalCenter.breadcrumbCenter') }}</div>

        <el-row :gutter="20">
          <!-- 左侧：个人信息 + 菜单 -->
          <el-col :span="6">
            <el-card shadow="never">
              <div class="avatar-section">
                <el-avatar :size="80" :src="userInfo.avatar">{{ userInfo.nickName?.charAt(0) || '?' }}</el-avatar>
                <h3>{{ userInfo.nickName || t('common.notSet') }}</h3>
                <p>{{ userInfo.phonenumber || t('common.notBound') }}</p>
                <el-button type="primary" size="small" round @click="handleEditInfo">{{ t('personalCenter.editProfile') }}</el-button>
              </div>
              <el-divider />
              <el-menu :default-active="activeMenu" class="side-menu" @select="handleMenuSelect">
                <el-menu-item index="1"><el-icon><OfficeBuilding /></el-icon>{{ t('personalCenter.myEnterprise') }}</el-menu-item>
                <el-menu-item index="2"><el-icon><Document /></el-icon>{{ t('personalCenter.myApplications') }}</el-menu-item>
                <el-menu-item index="3"><el-icon><Money /></el-icon>{{ t('personalCenter.financeRecords') }}</el-menu-item>
                <el-menu-item index="4"><el-icon><ChatDotRound /></el-icon>{{ t('personalCenter.myConsultations') }}</el-menu-item>
                <el-menu-item index="6"><el-icon><Star /></el-icon>{{ t('personalCenter.myFavorites') }}</el-menu-item>
                <el-menu-item index="7"><el-icon><User /></el-icon>{{ t('personalCenter.myInfo') }}</el-menu-item>
                <el-menu-item index="5"><el-icon><SwitchButton /></el-icon>{{ t('personalCenter.logout') }}</el-menu-item>
              </el-menu>
            </el-card>
          </el-col>

          <!-- 右侧内容区 -->
          <el-col :span="18">
            <el-card shadow="never" v-loading="loading">
              <!-- 我的企业 -->
              <div v-if="activeMenu === '1'">
                <h3 class="sub-title">{{ t('personalCenter.enterpriseTitle') }}</h3>
                <div v-if="companyList.length > 0">
                  <el-card v-for="item in companyList" :key="item.id" shadow="hover" class="company-card" style="margin-bottom:16px;">
                    <el-descriptions :column="2" border>
                      <el-descriptions-item :label="t('personalCenter.labelCompanyName')">{{ item.companyName }}</el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelRegion')">{{ item.region }}</el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelScope')">{{ item.business || '-' }}</el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelContact')">{{ item.linkman }}</el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelPhone')">{{ item.phone }}</el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelStatus')">
                        <el-tag :type="item.status === '1' ? 'success' : item.status === '2' ? 'danger' : 'warning'">
                          {{ item.status === '1' ? t('common.approved') : item.status === '2' ? t('common.rejected') : t('common.reviewing') }}
                        </el-tag>
                      </el-descriptions-item>
                      <el-descriptions-item :label="t('personalCenter.labelEntryTime')" :span="2">{{ item.createTime || '-' }}</el-descriptions-item>
                    </el-descriptions>
                  </el-card>
                </div>
                <el-empty v-else :description="t('personalCenter.noEnterprise')">
                  <el-button type="primary" @click="$router.push('/enterprise-entry')">{{ t('personalCenter.goEntry') }}</el-button>
                </el-empty>
              </div>

              <!-- 我的申报 -->
              <div v-if="activeMenu === '2'">
                <h3 class="sub-title">{{ t('personalCenter.applicationTitle') }}</h3>
                <el-table :data="applyList" border style="width:100%">
                  <!-- <el-table-column prop="id" label="编号" width="60" /> -->
                  <el-table-column prop="projectName" :label="t('personalCenter.columnProjectName')" />
                  <el-table-column :label="t('common.status')" width="100">
                    <template #default="scope">
                      <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
                        {{ scope.row.status === '1' ? t('common.approved') : scope.row.status === '2' ? t('common.rejected') : t('common.reviewing') }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="createTime" :label="t('personalCenter.columnTime')" width="180" />
                  <el-table-column :label="t('personalCenter.columnOperation')" width="80">
                    <template #default><el-button type="primary" link>{{ t('personalCenter.viewDetail') }}</el-button></template>
                  </el-table-column>
                </el-table>
                <el-empty v-if="applyList.length === 0" :description="t('personalCenter.noApplications')" :image-size="60" />
              </div>

              <!-- 融资记录 -->
              <div v-if="activeMenu === '3'">
                <h3 class="sub-title">{{ t('personalCenter.financeTitle') }}</h3>
                <el-table :data="financeList" border style="width:100%">
                  <!-- <el-table-column prop="id" label="编号" width="60" /> -->
                  <el-table-column prop="productName" :label="t('personalCenter.columnUsage')" min-width="180">
                    <template #default="scope">
                      {{ scope.row.productName || '-' }}
                    </template>
                  </el-table-column>
                  <el-table-column prop="money" :label="t('personalCenter.columnAmount')" width="130">
                    <template #default="scope">
                      {{ scope.row.money || '-' }}
                    </template>
                  </el-table-column>
                  <el-table-column :label="t('common.status')" width="100">
                    <template #default="scope">
                      <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
                        {{ scope.row.status === '1' ? t('common.approved') : scope.row.status === '2' ? t('common.rejected') : t('common.reviewing') }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="createTime" :label="t('common.time')" width="180" />
                </el-table>
                <el-empty v-if="financeList.length === 0" :description="t('personalCenter.noFinanceRecords')" :image-size="60" />
              </div>

              <!-- 我的咨询 -->
              <div v-if="activeMenu === '4'">
                <h3 class="sub-title">{{ t('personalCenter.consultationTitle') }}</h3>
                <el-table :data="consultList" border style="width:100%">
                  <!-- <el-table-column prop="id" label="编号" width="60" /> -->
                  <el-table-column prop="title" :label="t('personalCenter.columnTitle')" min-width="150" />
                  <el-table-column prop="content" :label="t('personalCenter.columnContent')" min-width="200" show-overflow-tooltip />
                  <el-table-column prop="reply" :label="t('personalCenter.columnReply')" min-width="200" show-overflow-tooltip>
                    <template #default="scope">
                      {{ scope.row.reply || t('personalCenter.noReply') }}
                    </template>
                  </el-table-column>
                  <el-table-column :label="t('common.status')" width="100">
                    <template #default="scope">
                      <el-tag :type="scope.row.status === '1' ? 'success' : 'warning'">
                        {{ scope.row.status === '1' ? t('common.replied') : t('common.pendingReply') }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="createTime" :label="t('common.time')" width="180" />
                </el-table>
                <el-empty v-if="consultList.length === 0" :description="t('personalCenter.noConsultations')" :image-size="60" />
              </div>

              <!-- 我的收藏 -->
              <div v-if="activeMenu === '6'">
                <h3 class="sub-title">{{ t('personalCenter.favoritesTitle') }}</h3>
                <el-table :data="favoriteList" border style="width:100%">
                  <!-- <el-table-column prop="policyId" label="政策ID" width="80" /> -->
                  <el-table-column prop="policyTitle" :label="t('personalCenter.columnPolicyTitle')" />
                  <el-table-column prop="createTime" :label="t('personalCenter.columnCollectTime')" width="180">
                    <template #default="scope">
                      {{ formatTime(scope.row.createTime) }}
                    </template>
                  </el-table-column>
                  <el-table-column :label="t('common.operation')" width="150">
                    <template #default="scope">
                      <el-button type="primary" link size="small" @click="viewFavorite(scope.row.policyId)">{{ t('personalCenter.viewDetailBtn') }}</el-button>
                      <el-button type="danger" link size="small" @click="removeFavorite(scope.row.policyId)">{{ t('personalCenter.cancelCollect') }}</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <el-empty v-if="favoriteList.length === 0" :description="t('personalCenter.noFavorites')" :image-size="60" />
              </div>

              <!-- 我的信息（求职登记） -->
              <div v-if="activeMenu === '7'">
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:16px; padding-bottom:8px; border-bottom:2px solid var(--tibet-border);">
                  <h3 style="font-size:16px; margin:0;">{{ t('personalCenter.infoTitle') }} <el-tag v-if="jobRegistered" type="success" size="small" style="margin-left:8px;">{{ t('common.registered') }}</el-tag></h3>
                  <el-button type="primary" size="small" @click="openJobEdit">{{ jobRegistered ? t('personalCenter.editBtn') : t('personalCenter.goRegister') }}</el-button>
                </div>
                <div v-if="jobRegistered">
                  <el-descriptions :column="2" border>
                    <el-descriptions-item :label="t('personalCenter.labelName')">{{ jobInfo.name }}</el-descriptions-item>
                    <el-descriptions-item :label="t('personalCenter.labelPhoneNumber')">{{ jobInfo.phone }}</el-descriptions-item>
                    <el-descriptions-item :label="t('personalCenter.labelJobPosition')">{{ jobInfo.position }}</el-descriptions-item>
                    <el-descriptions-item :label="t('personalCenter.labelExperience')">{{ jobInfo.experience }}</el-descriptions-item>
                    <el-descriptions-item :label="t('personalCenter.labelResume')" :span="2">{{ jobInfo.resumeUrl || t('common.notUploaded') }}</el-descriptions-item>
                  </el-descriptions>
                </div>
                <el-empty v-else :description="t('common.notRegistered')" :image-size="80">
                  <el-button type="primary" @click="openJobEdit">{{ t('personalCenter.registerNow') }}</el-button>
                </el-empty>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 编辑资料弹窗 -->
        <el-dialog v-model="editInfoDialog" :title="t('personalCenter.editProfileTitle')" width="450px">
          <el-form :model="editForm" ref="editFormRef" label-width="80px">
            <el-form-item :label="t('personalCenter.labelNickname')">
              <el-input v-model="editForm.nickName" :placeholder="t('personalCenter.nicknamePlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelPhoneNumber')">
              <el-input v-model="editForm.phonenumber" :placeholder="t('personalCenter.phonePlaceholder')" maxlength="11" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelEmail')">
              <el-input v-model="editForm.email" :placeholder="t('personalCenter.emailPlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelGender')">
              <el-radio-group v-model="editForm.sex">
                <el-radio value="0">{{ t('personalCenter.male') }}</el-radio>
                <el-radio value="1">{{ t('personalCenter.female') }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelAvatar')">
              <el-upload
                action="/system/user/profile/avatar"
                name="avatarfile"
                :headers="uploadHeaders"
                accept=".jpg,.png,.jpeg"
                :limit="1"
                :on-success="handleAvatarUpload"
                :on-error="handleAvatarError"
                :show-file-list="false"
              >
                <el-avatar :size="80" :src="editForm.avatar">{{ editForm.nickName?.charAt(0) || '?' }}</el-avatar>
                <template #tip>
                  <div class="upload-tip">{{ t('personalCenter.avatarUploadTip') }}</div>
                </template>
              </el-upload>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="editInfoDialog = false">{{ t('common.cancel') }}</el-button>
            <el-button type="primary" @click="submitEdit">{{ t('common.save') }}</el-button>
          </template>
        </el-dialog>

        <!-- 求职信息编辑弹窗 -->
        <el-dialog v-model="jobEditDialog" :title="t('personalCenter.editJobInfoTitle')" width="520px">
          <el-form :model="jobForm" :rules="jobRules" ref="jobFormRef" label-width="100px">
            <el-form-item :label="t('personalCenter.labelName')" prop="name">
              <el-input v-model="jobForm.name" :placeholder="t('personalCenter.namePlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelPhoneNumber')" prop="phone">
              <el-input v-model="jobForm.phone" :placeholder="t('personalCenter.phonePlaceholder')" maxlength="11" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelJobPosition')" prop="position">
              <el-input v-model="jobForm.position" :placeholder="t('personalCenter.jobPlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('personalCenter.labelExperience')" prop="experience">
              <el-select v-model="jobForm.experience" :placeholder="t('personalCenter.experiencePlaceholder')" style="width:100%">
                <el-option :label="t('personalCenter.experienceFresh')" value="应届生" />
                <el-option :label="t('personalCenter.experienceUnder1')" value="1年以下" />
                <el-option :label="t('personalCenter.experience1to3')" value="1-3年" />
                <el-option :label="t('personalCenter.experience3to5')" value="3-5年" />
                <el-option :label="t('personalCenter.experienceOver5')" value="5年以上" />
              </el-select>
            </el-form-item>
            <el-form-item :label="t('personalCenter.resumeUpload')">
              <el-upload
                action="/mei/upload"
                :headers="uploadHeaders"
                accept=".pdf,.doc,.docx"
                :limit="1"
                :on-success="handleResumeUpload"
              >
                <el-button type="primary">{{ t('personalCenter.uploadResume') }}</el-button>
                <template #tip>
                  <div class="upload-tip">{{ t('personalCenter.resumeUploadTip') }}</div>
                </template>
              </el-upload>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="jobEditDialog = false">{{ t('common.cancel') }}</el-button>
            <el-button type="primary" @click="submitJob" :loading="jobSubmitting">{{ t('common.save') }}</el-button>
          </template>
        </el-dialog>

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
              <p class="login-desc">{{ t('common.loginToView') }}<br/>{{ t('common.loginDataDesc') }}</p>
            </div>
            <div class="login-actions">
              <el-button size="large" @click="loginDialog = false" class="btn-cancel">{{ t('common.notLoginNow') }}</el-button>
              <el-button size="large" type="primary" @click="goToLogin" class="btn-login">{{ t('common.loginNow') }}</el-button>
            </div>
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
import { ref, reactive, onMounted, computed } from 'vue'
import { Document, OfficeBuilding, Money, ChatDotRound, SwitchButton, Lock, Star, User } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import request from '@/utils/request'
import { notify, showConfirm } from '@/utils/notify'

const { t, locale } = useI18n()
const router = useRouter()

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const menuActive = '/personal-center'
const activeMenu = ref('1')
const editInfoDialog = ref(false)
const loginDialog = ref(false)
const editFormRef = ref(null)
const loading = ref(false)

// 登录状态
const isLoggedIn = computed(() => {
  return !!localStorage.getItem('token')
})

// 上传请求头
const uploadHeaders = computed(() => {
  const token = localStorage.getItem('token')
  return token ? { Authorization: 'Bearer ' + token } : {}
})

// 用户信息
const userInfo = ref({
  userId: null,
  userName: '',
  nickName: '',
  phonenumber: '',
  avatar: '',
  email: '',
  sex: '0'
})

// 各类列表数据
const applyList = ref([])
const companyList = ref([])
const financeList = ref([])
const consultList = ref([])
const favoriteList = ref([])

// 求职登记
const jobFormRef = ref(null)
const jobSubmitting = ref(false)
const jobRegistered = ref(false)
const jobEditDialog = ref(false)
const jobInfo = ref({ name: '', phone: '', position: '', experience: '', resumeUrl: '' })
const jobForm = reactive({
  name: '',
  phone: '',
  position: '',
  experience: '',
  resumeUrl: ''
})
const jobRules = {
  name: [{ required: true, message: () => t('personalCenter.validateName'), trigger: 'blur' }],
  phone: [
    { required: true, message: () => t('personalCenter.validatePhone'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: () => t('personalCenter.validatePhoneFormat'), trigger: 'blur' }
  ],
  position: [{ required: true, message: () => t('personalCenter.validateJob'), trigger: 'blur' }],
  experience: [{ required: true, message: () => t('personalCenter.validateExperience'), trigger: 'change' }],
}

// 编辑表单
const editForm = reactive({
  nickName: '',
  phonenumber: '',
  avatar: '',
  email: '',
  sex: '0'
})

// 菜单选择
const handleMenuSelect = (index) => {
  // 顶部导航（index 以 / 开头）
  if (index.startsWith('/')) {
    router.push(index)
    return
  }
  // 退出登录
  if (index === '5') {
    ElMessageBox.confirm(t('personalCenter.logoutConfirm'), t('notify.info'), {
      confirmButtonText: t('common.confirm'),
      cancelButtonText: t('common.cancel'),
      type: 'warning'
    }).then(() => {
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('userName')
      localStorage.removeItem('userAvatar')
      notify('success', t('personalCenter.logoutSuccess'))
      router.push('/')
    }).catch(() => {})
    return
  }

  // 检查是否登录
  if (!isLoggedIn.value) {
    loginDialog.value = true
    return
  }

  activeMenu.value = index
  fetchData(index)
}

// 跳转到登录页
const goToLogin = () => {
  loginDialog.value = false
  router.push('/login')
}

// 处理编辑资料点击
const handleEditInfo = () => {
  if (!isLoggedIn.value) {
    loginDialog.value = true
    return
  }
  editInfoDialog.value = true
}

// 获取数据
const fetchData = async (menu) => {
  loading.value = true
  try {
    const userId = localStorage.getItem('userId')
    if (!userId) {
      notify('warning', t('personalCenter.loginFirst'))
      return
    }

    switch(menu) {
      case '1': // 我的企业
        await fetchMyCompany(userId)
        break
      case '2': // 我的申报
        await fetchApplyList(userId)
        break
      case '3': // 融资记录
        await fetchFinanceList(userId)
        break
      case '4': // 我的咨询
        await fetchConsultList(userId)
        break
      case '6': // 我的收藏
        await fetchFavoriteList()
        break
      case '7': // 我的信息（求职登记）
        await fetchJobInfo(userId)
        break
    }
  } catch (error) {
    console.error('获取数据失败:', error)
    const msg = error.response?.data?.msg || error.message || t('personalCenter.loadDataFailed')
    notify('error', msg)
  } finally {
    loading.value = false
  }
}

// 获取申报列表
const fetchApplyList = async (userId) => {
  try {
    const res = await request.get('/mei/project/list', {
      params: { userId, pageNum: 1, pageSize: 100 }
    })
    if (res.data?.code === 200) {
      const data = res.data.data?.list || res.data.data || res.data.rows || []
      applyList.value = data.map(item => ({
        id: item.id,
        projectName: item.projectName,
        status: item.status,
        createTime: item.createTime
      }))
    }
  } catch (error) {
    console.error('获取申报列表失败:', error)
    applyList.value = []
  }
}

// 获取我的企业
const fetchMyCompany = async (userId) => {
  try {
    const res = await request.get('/mei/company/list', {
      params: { userId }
    })
    if (res.data?.code === 200) {
      companyList.value = res.data.data || res.data.rows || []
    }
  } catch (error) {
    console.error('获取企业信息失败:', error)
    companyList.value = []
  }
}

// 获取融资列表
const fetchFinanceList = async (userId) => {
  try {
    const res = await request.get('/mei/finance/list', {
      params: { userId, pageNum: 1, pageSize: 100 }
    })
    if (res.data?.code === 200) {
      const data = res.data.data?.list || res.data.data || res.data.rows || []
      financeList.value = data.map(item => ({
        id: item.id,
        productName: item.reason || t('common.financing'),
        money: item.money,
        status: item.status,
        createTime: item.createTime
      }))
    }
  } catch (error) {
    console.error('获取融资列表失败:', error)
    financeList.value = []
  }
}

// 获取咨询列表
const fetchConsultList = async (userId) => {
  try {
    const res = await request.get('/mei/consult/list', {
      params: { userId, pageNum: 1, pageSize: 100 }
    })
    if (res.data?.code === 200) {
      const data = res.data.data?.list || res.data.data || res.data.rows || []
      consultList.value = data.map(item => ({
        id: item.id,
        title: item.title,
        content: item.content,
        reply: item.reply,
        status: item.status,
        createTime: item.createTime
      }))
    }
  } catch (error) {
    console.error('获取咨询列表失败:', error)
    consultList.value = []
  }
}

// 获取收藏列表
const fetchFavoriteList = async () => {
  try {
    const userId = localStorage.getItem('userId')
    if (!userId) {
      notify('warning', t('personalCenter.loginFirst'))
      return
    }

    const res = await request.get('/mei/favorite/list', {
      params: { userId }
    })

    if (res.data?.code === 200) {
      favoriteList.value = res.data.rows || []
      console.log('收藏列表:', favoriteList.value)
    } else {
      notify('error', res.data?.msg || t('personalCenter.loadDataFailed'))
    }
  } catch (error) {
    console.error('获取收藏列表失败:', error)
    const msg = error.response?.data?.msg || error.message || t('personalCenter.loadDataFailed')
    notify('error', msg)
    favoriteList.value = []
  }
}

// 获取求职登记信息
const fetchJobInfo = async (userId) => {
  try {
    const res = await request.get('/mei/talent/info', { params: { userId } })
    if (res.data?.code === 200 && res.data.data) {
      const info = res.data.data
      jobInfo.value = {
        name: info.name || '',
        phone: info.phone || '',
        position: info.position || '',
        experience: info.experience || '',
        resumeUrl: info.resumeUrl || ''
      }
      jobForm.name = info.name || ''
      jobForm.phone = info.phone || ''
      jobForm.position = info.position || ''
      jobForm.experience = info.experience || ''
      jobForm.resumeUrl = info.resumeUrl || ''
      jobRegistered.value = true
    } else {
      jobRegistered.value = false
      jobInfo.value = { name: '', phone: '', position: '', experience: '', resumeUrl: '' }
    }
  } catch (error) {
    console.error('获取求职信息失败:', error)
    jobRegistered.value = false
  }
}

// 简历上传成功回调
const handleResumeUpload = (response) => {
  jobForm.resumeUrl = response.data?.url || response.url || ''
  notify('success', t('personalCenter.resumeUploadSuccess'))
}

// 打开求职信息编辑弹窗
const openJobEdit = () => {
  if (jobRegistered.value) {
    jobForm.name = jobInfo.value.name
    jobForm.phone = jobInfo.value.phone
    jobForm.position = jobInfo.value.position
    jobForm.experience = jobInfo.value.experience
    jobForm.resumeUrl = jobInfo.value.resumeUrl
  }
  jobEditDialog.value = true
}

// 提交求职登记
const submitJob = async () => {
  if (!jobFormRef.value) return
  await jobFormRef.value.validate(async (valid) => {
    if (valid) {
      jobSubmitting.value = true
      try {
        const userId = localStorage.getItem('userId')
        const res = await request.post('/mei/talent/apply', {
          userId: userId ? parseInt(userId) : null,
          name: jobForm.name,
          phone: jobForm.phone,
          position: jobForm.position,
          experience: jobForm.experience,
          resumeUrl: jobForm.resumeUrl || ''
        })
        if (res.data?.code === 200) {
          notify('success', jobRegistered.value ? t('personalCenter.jobInfoUpdateSuccess') : t('personalCenter.jobInfoRegisterSuccess'))
          jobEditDialog.value = false
          // 刷新展示数据
          jobInfo.value = {
            name: jobForm.name,
            phone: jobForm.phone,
            position: jobForm.position,
            experience: jobForm.experience,
            resumeUrl: jobForm.resumeUrl
          }
          jobRegistered.value = true
        } else {
          notify('error', res.data?.msg || t('personalCenter.submitFailed'))
        }
      } catch (error) {
        console.error('提交失败:', error)
        const msg = error.response?.data?.msg || error.message || t('personalCenter.submitFailed')
        notify('error', msg)
      } finally {
        jobSubmitting.value = false
      }
    }
  })
}

// 重置求职表单
const resetJobForm = () => {
  if (jobFormRef.value) {
    jobFormRef.value.resetFields()
  }
  jobForm.resumeUrl = ''
}

// 格式化时间
const formatTime = (timeStr) => {
  if (!timeStr) return '-'
  const date = new Date(timeStr)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 查看收藏的政策详情
const viewFavorite = (policyId) => {
  router.push(`/policy/${policyId}`)
}

// 取消收藏
const removeFavorite = async (policyId) => {
  try {
    await showConfirm(t('personalCenter.confirmCancelCollect'), { title: t('personalCenter.cancelCollect') })
  } catch {
    return
  }
  try {
    const userId = localStorage.getItem('userId')
    const res = await request.delete(`/mei/favorite/remove/${userId}/${policyId}`)
    if (res.data?.code === 200) {
      notify('success', t('personalCenter.cancelCollectSuccess'))
      await fetchFavoriteList()
    } else {
      notify('error', res.data?.msg || t('personalCenter.cancelCollectFailed'))
    }
  } catch (error) {
    console.error('取消收藏失败:', error)
    const msg = error.response?.data?.msg || error.message || t('personalCenter.cancelCollectFailed')
    notify('error', msg)
  }
}

// 头像上传成功回调
const handleAvatarUpload = (res) => {
  if (res.code === 200) {
    editForm.avatar = res.imgUrl
    notify('success', t('personalCenter.avatarUploadSuccess'))
  } else {
    notify('error', res.msg || t('personalCenter.avatarUploadFailed'))
  }
}

// 头像上传失败回调
const handleAvatarError = (error) => {
  console.error('头像上传失败:', error)
  notify('error', t('personalCenter.avatarUploadNetworkError'))
}

// 提交编辑
const submitEdit = async () => {
  try {
    // 调用若依框架接口更新用户信息
    const res = await request.put('/system/user/profile', {
      nickName: editForm.nickName,
      phonenumber: editForm.phonenumber,
      email: editForm.email,
      sex: editForm.sex
    })
    if (res.data?.code === 200) {
      notify('success', t('personalCenter.profileUpdateSuccess'))
      editInfoDialog.value = false

      // 重新获取用户信息
      await initUserInfo()
    } else {
      notify('error', res.data?.msg || t('personalCenter.profileUpdateFailed'))
    }
  } catch (error) {
    console.error('修改失败:', error)
    const msg = error.response?.data?.msg || error.message || t('personalCenter.profileUpdateFailed')
    notify('error', msg)
  }
}

// 初始化用户信息
const initUserInfo = async () => {
  const userId = localStorage.getItem('userId')
  const token = localStorage.getItem('token')

  // 先从 localStorage 恢复用户信息（确保二次进入时有数据显示）
  if (userId && token) {
    userInfo.value = {
      userId: userId,
      userName: localStorage.getItem('userName') || '',
      nickName: localStorage.getItem('nickName') || '',
      phonenumber: localStorage.getItem('phonenumber') || '',
      avatar: localStorage.getItem('avatar') || '',
      email: localStorage.getItem('email') || '',
      sex: localStorage.getItem('sex') || '0'
    }
    editForm.nickName = userInfo.value.nickName
    editForm.phonenumber = userInfo.value.phonenumber
    editForm.avatar = userInfo.value.avatar
    editForm.email = userInfo.value.email
    editForm.sex = userInfo.value.sex
  } else {
    return
  }

  // 尝试从 API 获取最新用户信息
  try {
    const res = await request.get('/system/user/profile')
    if (res.data?.code === 200) {
      const user = res.data.data
      userInfo.value = {
        userId: user.userId,
        userName: user.userName,
        nickName: user.nickName || userInfo.value.nickName,
        phonenumber: user.phonenumber || userInfo.value.phonenumber,
        avatar: user.avatar || userInfo.value.avatar,
        email: user.email || userInfo.value.email,
        sex: user.sex || userInfo.value.sex
      }
      editForm.nickName = userInfo.value.nickName
      editForm.phonenumber = userInfo.value.phonenumber
      editForm.avatar = userInfo.value.avatar
      editForm.email = userInfo.value.email
      editForm.sex = userInfo.value.sex

      // 同步最新数据回 localStorage
      localStorage.setItem('nickName', userInfo.value.nickName)
      localStorage.setItem('phonenumber', userInfo.value.phonenumber)
      localStorage.setItem('avatar', userInfo.value.avatar)
      localStorage.setItem('email', userInfo.value.email)
      localStorage.setItem('sex', userInfo.value.sex)
    }
  } catch (error) {
    // API 调用失败时，继续使用 localStorage 中的数据
    console.error('获取用户信息失败，使用本地缓存:', error.message)
  }
}

onMounted(() => {
  initUserInfo()
  // 页面加载时自动加载当前菜单数据
  if (localStorage.getItem('token')) {
    fetchData(activeMenu.value)
  }
})

// 页面激活时重新加载用户信息和数据（解决路由缓存问题）
onActivated(() => {
  initUserInfo()
  if (localStorage.getItem('token')) {
    fetchData(activeMenu.value)
  }
})
</script>

<style scoped>
.section-wrapper { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.avatar-section { text-align: center; padding: 16px 0; }
.avatar-section h3 { margin: 10px 0 4px; }
.avatar-section p { color: #999; font-size: 13px; margin-bottom: 12px; }
.side-menu { border-right: none; }
.sub-title { font-size: 16px; margin-bottom: 16px; padding-bottom: 8px; border-bottom: 2px solid var(--tibet-border); }
.upload-tip { color: #999; font-size: 12px; margin-top: 8px; }
.lang-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  border: none;
  border-radius: 20px;
  font-weight: 500;
  padding: 6px 16px;
  font-size: 13px;
}
.lang-btn:hover {
  opacity: 0.9;
  color: #fff;
}
.header-right {
  display: flex;
  align-items: center;
  margin-left: auto;
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

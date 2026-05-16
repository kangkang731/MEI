<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('financing.title') }}</h1>
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
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('financing.title') }}</div>

        <h2 class="section-title">{{ t('financing.productRecommend') }}</h2>
        <el-row :gutter="20" v-loading="loading">
          <el-col :span="8" v-for="item in financingList" :key="item.id">
            <el-card shadow="hover" class="finance-card">
              <div class="fc-header">
                <h3>{{ item.name }}</h3>
                <el-tag type="success" size="large">{{ item.rate }}%</el-tag>
              </div>
              <div class="fc-body">
                <p><span class="label">{{ t('financing.amountLabel') }}</span>{{ item.amount }}{{ t('financing.amountUnit') }}</p>
                <p><span class="label">{{ t('financing.termLabel') }}</span>{{ item.term }}{{ t('financing.termUnit') }}</p>
                <p><span class="label">{{ t('financing.suitableFor') }}</span>{{ item.scenario }}</p>
              </div>
              <div class="fc-footer">
                <el-button type="primary" round @click="applyFinancing(item.id)">{{ t('financing.applyNow') }}</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 融资申请记录 -->
        <h2 class="section-title" style="margin-top: 40px;">{{ t('financing.applicationRecords') }}</h2>
        <el-card shadow="never" v-loading="recordLoading">
          <el-table :data="financeRecords" border style="width:100%">
            <!-- <el-table-column prop="id" label="编号" width="80" /> -->
            <el-table-column prop="money" :label="t('financing.columnAmount')" width="140" />
            <el-table-column prop="reason" :label="t('financing.columnUsage')" min-width="200">
              <template #default="scope">
                {{ scope.row.reason || '-' }}
              </template>
            </el-table-column>
            <el-table-column :label="t('financing.columnStatus')" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === '1' ? 'success' : scope.row.status === '2' ? 'danger' : 'warning'">
                  {{ scope.row.status === '1' ? t('common.approved') : scope.row.status === '2' ? t('common.rejected') : t('common.reviewing') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" :label="t('financing.columnTime')" width="180" />
          </el-table>
          <el-empty v-if="financeRecords.length === 0" :description="t('financing.noRecords')" :image-size="60" />
        </el-card>

        <!-- 融资申请弹窗 -->
        <el-dialog v-model="dialogVisible" :title="t('financing.dialogTitle')" width="500px">
          <el-form :model="financeForm" label-width="100px">
            <el-form-item :label="t('financing.productLabel')">
              <el-input v-model="financeForm.productName" disabled />
            </el-form-item>
            <el-form-item :label="t('financing.amountInput')">
              <el-input v-model="financeForm.amount" type="number" />
            </el-form-item>
            <el-form-item :label="t('financing.contactLabel')">
              <el-input v-model="financeForm.contact" :placeholder="t('financing.contactPlaceholder')" />
            </el-form-item>
            <el-form-item :label="t('financing.usageLabel')">
              <el-input v-model="financeForm.remark" type="textarea" rows="3" :placeholder="t('financing.usagePlaceholder')" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="dialogVisible = false">{{ t('financing.cancel') }}</el-button>
            <el-button type="primary" @click="submitFinance" :loading="submitting">{{ t('financing.submitApply') }}</el-button>
          </template>
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
const menuActive = '/financing'
const handleMenuSelect = (index) => router.push(index)

const financingList = ref([
  { id: 1, name: t('financing.product1'), rate: 3.8, amount: '50-500', term: '6-36', scenario: t('financing.scenario1') },
  { id: 2, name: t('financing.product2'), rate: 4.2, amount: '100-1000', term: '12-60', scenario: t('financing.scenario2') },
  { id: 3, name: t('financing.product3'), rate: 3.5, amount: '20-200', term: '6-24', scenario: t('financing.scenario3') },
])

const loading = ref(false)
const recordLoading = ref(false)
const financeRecords = ref([])

const dialogVisible = ref(false)
const submitting = ref(false)
const financeForm = reactive({ productName: '', amount: '', contact: '', remark: '' })

// 获取融资申请记录
const fetchFinanceRecords = async () => {
  recordLoading.value = true
  try {
    const userId = localStorage.getItem('userId')
    const res = await axios.get('/mei/finance/list', {
      params: { userId, pageNum: 1, pageSize: 50 }
    })
    if (res.data?.code === 200) {
      const raw = res.data.data
      const list = raw?.list || raw?.rows || raw || []
      financeRecords.value = Array.isArray(list) ? list : []
    }
  } catch (e) {
    console.error('获取融资记录失败:', e)
  } finally {
    recordLoading.value = false
  }
}

const applyFinancing = (id) => {
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  if (!token || !userId) {
    notify('warning', t('financing.loginFirst'))
    router.push('/login')
    return
  }

  const product = financingList.value.find(i => i.id === id)
  financeForm.productName = product.name
  financeForm.amount = ''
  financeForm.contact = ''
  financeForm.remark = ''
  dialogVisible.value = true
}

const submitFinance = async () => {
  const token = localStorage.getItem('token')
  const userId = localStorage.getItem('userId')

  if (!token || !userId) {
    notify('warning', t('financing.loginFirst'))
    router.push('/login')
    return
  }

  submitting.value = true
  try {
    const res = await axios.post('/mei/finance/apply', {
      userId: parseInt(userId),
      productName: financeForm.productName,
      amount: financeForm.amount,
      contact: financeForm.contact,
      description: financeForm.remark
    })

    if (res.data.code === 200) {
      notify('success', t('financing.submitSuccess'))
      dialogVisible.value = false
      financeForm.amount = ''
      financeForm.contact = ''
      financeForm.remark = ''
      fetchFinanceRecords()
    } else {
      notify('error', res.data.msg || t('financing.submitFailed'))
    }
  } catch (e) {
    console.error('提交失败:', e)
    const msg = e.response?.data?.msg || e.message || t('financing.submitFailed')
    notify('error', msg)
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  fetchFinanceRecords()
})
</script>

<style scoped>
.section-wrapper { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.section-title { font-size: 20px; margin-bottom: 20px; }

.finance-card { height: 100%; transition: all 0.3s; }
.finance-card:hover { transform: translateY(-4px); }
.fc-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
.fc-header h3 { font-size: 16px; }
.fc-body { margin-bottom: 20px; }
.fc-body p { margin: 8px 0; color: #555; }
.label { color: #999; display: inline-block; width: 50px; }
.fc-footer { text-align: right; }
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

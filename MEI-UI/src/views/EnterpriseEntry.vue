<template>
  <div class="page-container">
    <div class="tibet-stripe"></div>
    <header class="tibet-header">
      <div class="logo">
        <img src="@/assets/logo.png" alt="西藏双创" class="logo-img" />
        <h1>{{ t('enterpriseEntry.title') }}</h1>
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
        <div class="breadcrumb"><router-link to="/">{{ t('common.home') }}</router-link> &gt; {{ t('enterpriseEntry.title') }}</div>

        <el-card shadow="never" class="entry-card">
          <h2 class="page-title">{{ t('enterpriseEntry.formTitle') }}</h2>
          <p class="page-desc">{{ t('enterpriseEntry.formDesc') }}</p>
          <el-divider />

          <el-form ref="entryFormRef" :model="entryForm" :rules="entryRules" label-width="130px">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.companyName')" prop="companyName">
                  <el-input v-model="entryForm.companyName" :placeholder="t('enterpriseEntry.companyNamePlaceholder')" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.creditCode')" prop="creditCode">
                  <el-input v-model="entryForm.creditCode" :placeholder="t('enterpriseEntry.creditCodePlaceholder')" maxlength="18" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.region')" prop="region">
                  <el-select v-model="entryForm.region" :placeholder="t('enterpriseEntry.regionPlaceholder')" style="width:100%">
                    <el-option
                      v-for="item in regionOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.businessScope')" prop="business">
                  <el-select v-model="entryForm.business" :placeholder="t('enterpriseEntry.businessScopePlaceholder')" style="width:100%">
                    <el-option
                      v-for="item in businessOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.contact')" prop="linkman">
                  <el-input v-model="entryForm.linkman" :placeholder="t('enterpriseEntry.contactPlaceholder')" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item :label="t('enterpriseEntry.phone')" prop="phone">
                  <el-input v-model="entryForm.phone" :placeholder="t('enterpriseEntry.phonePlaceholder')" maxlength="11" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item :label="t('enterpriseEntry.remark')">
              <el-input
                v-model="entryForm.remark"
                type="textarea"
                :rows="4"
                :placeholder="t('enterpriseEntry.remarkPlaceholder')" 
                maxlength="500"
                show-word-limit
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="large" @click="submitForm" :loading="submitting">{{ t('enterpriseEntry.submitApply') }}</el-button>
              <el-button size="large" @click="resetForm">{{ t('enterpriseEntry.reset') }}</el-button>
            </el-form-item>
          </el-form>
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
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import axios from 'axios'
import { notify } from '@/utils/notify'
import { ElMessage } from 'element-plus'

const router = useRouter()
const { t, locale } = useI18n()
const menuActive = '/enterprise-entry'
const handleMenuSelect = (index) => router.push(index)

const switchLang = () => {
  const newLang = locale.value === 'zh-CN' ? 'bo-CN' : 'zh-CN'
  locale.value = newLang
  localStorage.setItem('language', newLang)
}

const entryFormRef = ref(null)
const fileList = ref([])
const submitting = ref(false)

// 西藏各地区选项
const regionOptions = computed(() => [
  { value: '拉萨市', label: t('enterpriseEntry.lhasa'), children: [
    { value: '城关区', label: t('enterpriseEntry.chengguan') },
    { value: '堆龙德庆区', label: t('enterpriseEntry.duilongdeqing') },
    { value: '达孜区', label: t('enterpriseEntry.dazi') },
    { value: '林周县', label: t('enterpriseEntry.linzhou') },
    { value: '当雄县', label: t('enterpriseEntry.dangxiong') },
    { value: '尼木县', label: t('enterpriseEntry.nimu') },
    { value: '曲水县', label: t('enterpriseEntry.qushui') },
    { value: '墨竹工卡县', label: t('enterpriseEntry.mozhugongka') }
  ]},
  { value: '日喀则市', label: t('enterpriseEntry.shigatse'), children: [
    { value: '桑珠孜区', label: t('enterpriseEntry.sangzhizi') },
    { value: '南木林县', label: t('enterpriseEntry.nanmulin') },
    { value: '江孜县', label: t('enterpriseEntry.jiangzi') },
    { value: '定日县', label: t('enterpriseEntry.dingri') },
    { value: '萨迦县', label: t('enterpriseEntry.sajia') },
    { value: '拉孜县', label: t('enterpriseEntry.lazhi') }
  ]},
  { value: '山南市', label: t('enterpriseEntry.shannan'), children: [
    { value: '乃东区', label: t('enterpriseEntry.naidong') },
    { value: '扎囊县', label: t('enterpriseEntry.zhanang') },
    { value: '贡嘎县', label: t('enterpriseEntry.gongga') },
    { value: '桑日县', label: t('enterpriseEntry.sangri') },
    { value: '琼结县', label: t('enterpriseEntry.qiongjie') }
  ]},
  { value: '林芝市', label: t('enterpriseEntry.nyingchi'), children: [
    { value: '巴宜区', label: t('enterpriseEntry.bayi') },
    { value: '工布江达县', label: t('enterpriseEntry.gongbujiangda') },
    { value: '米林县', label: t('enterpriseEntry.milin') },
    { value: '墨脱县', label: t('enterpriseEntry.motuo') },
    { value: '波密县', label: t('enterpriseEntry.bomi') }
  ]},
  { value: '昌都市', label: t('enterpriseEntry.chamdo'), children: [
    { value: '卡若区', label: t('enterpriseEntry.karuo') },
    { value: '江达县', label: t('enterpriseEntry.jiangda') },
    { value: '贡觉县', label: t('enterpriseEntry.gongjue') },
    { value: '类乌齐县', label: t('enterpriseEntry.leiuvuqi') }
  ]},
  { value: '那曲市', label: t('enterpriseEntry.nagqu'), children: [
    { value: '色尼区', label: t('enterpriseEntry.seni') },
    { value: '嘉黎县', label: t('enterpriseEntry.jiali') },
    { value: '比如县', label: t('enterpriseEntry.biru') },
    { value: '聂荣县', label: t('enterpriseEntry.nierong') }
  ]},
  { value: '阿里地区', label: t('enterpriseEntry.ali'), children: [
    { value: '噶尔县', label: t('enterpriseEntry.gaer') },
    { value: '普兰县', label: t('enterpriseEntry.pulan') },
    { value: '札达县', label: t('enterpriseEntry.zhada') },
    { value: '日土县', label: t('enterpriseEntry.ritu') }
  ]},
])

// 主营领域选项
const businessOptions = computed(() => [
  { label: t('enterpriseEntry.highlandIT'), value: '高原信息技术' },
  { label: t('enterpriseEntry.tibetMedicine'), value: '藏医药信息化' },
  { label: t('enterpriseEntry.culturalTourism'), value: '文旅数字化' },
  { label: t('enterpriseEntry.agriTech'), value: '农牧科技' },
  { label: t('enterpriseEntry.intangibleHeritage'), value: '非遗文创' },
  { label: t('enterpriseEntry.ecommerce'), value: '电子商务' },
  { label: t('enterpriseEntry.cleanEnergy'), value: '清洁能源' },
  { label: t('enterpriseEntry.other'), value: '其他' }
])

const entryForm = reactive({
  companyName: '',
  creditCode: '',
  region: '',
  business: '',
  linkman: '',
  phone: '',
  remark: '',
  status: '0' // 0-待审核
})

const entryRules = computed(() => ({
  companyName: [{ required: true, message: t('enterpriseEntry.validateCompanyName'), trigger: 'blur' }],
  creditCode: [
    { required: true, message: t('enterpriseEntry.validateCreditCode'), trigger: 'blur' },
    { min: 18, max: 18, message: t('enterpriseEntry.validateCreditCodeLen'), trigger: 'blur' }
  ],
  region: [{ required: true, message: t('enterpriseEntry.validateRegion'), trigger: 'change' }],
  business: [{ required: true, message: t('enterpriseEntry.validateScope'), trigger: 'change' }],
  linkman: [{ required: true, message: t('enterpriseEntry.validateContact'), trigger: 'blur' }],
  phone: [
    { required: true, message: t('enterpriseEntry.validatePhone'), trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: t('enterpriseEntry.validatePhoneFormat'), trigger: 'blur' }
  ],
}))

// 文件上传成功回调
const handleFileSuccess = (response, uploadFile) => {
  console.log('文件上传成功:', response)
  notify('success', t('enterpriseEntry.uploadSuccess'))
}

// 提交表单
const submitForm = async () => {
  if (!entryFormRef.value) return
  
  await entryFormRef.value.validate(async (valid) => {
    if (valid) {
      // 校验登录状态
      const token = localStorage.getItem('token')
      const userId = localStorage.getItem('userId')
      
      if (!token || !userId) {
        notify('warning', t('enterpriseEntry.loginFirst'))
        router.push('/login')
        return
      }
      
      submitting.value = true
      try {
        const data = {
          userId: parseInt(userId),
          companyName: entryForm.companyName,
          region: entryForm.region,
          business: entryForm.business,
          linkman: entryForm.linkman,
          phone: entryForm.phone,
          status: '0',
          createTime: new Date().toISOString().slice(0, 19).replace('T', ' ')
        }
        
        const res = await axios.post('/mei/company', data)
        
        if (res.data.code === 200) {
          notify('success', t('enterpriseEntry.submitSuccess'))
          resetForm()
        } else {
          notify('error', res.data.msg || t('enterpriseEntry.submitFailed'))
        }
      } catch (error) {
        console.error('提交失败:', error)
        const msg = error.response?.data?.msg || error.message || t('enterpriseEntry.submitFailed')
        notify('error',msg)
      } finally {
        submitting.value = false
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  entryFormRef.value.resetFields()
  fileList.value = []
}
</script>

<style scoped>
.section-wrapper { max-width: 900px; margin: 0 auto; padding: 0 20px; }
.logo-img { width: 36px; height: 36px; border-radius: 50%; border: 2px solid var(--tibet-gold); }
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
.breadcrumb { padding: 16px 0; color: #999; font-size: 13px; }
.breadcrumb a { color: var(--tibet-red); text-decoration: none; }
.entry-card { padding: 32px; border-radius: 12px; }
.page-title { font-size: 20px; margin-bottom: 8px; }
.page-desc { color: #666; font-size: 14px; }
.upload-tip { color: #999; font-size: 12px; margin-top: 4px; }
</style>

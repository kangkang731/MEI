<template>
  <div class="login-container">
    <div class="login-overlay"></div>
    <div class="login-box">
      <div class="login-header">
        <h2>{{ t('register.title') }}</h2>
        <p>{{ t('register.subtitle') }}</p>
      </div>

      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerRules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="registerForm.username"
            :placeholder="t('register.usernamePlaceholder')"
            prefix-icon="User"
            size="large"
            clearable
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            :placeholder="t('register.passwordPlaceholder')"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>

        <el-form-item prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            :placeholder="t('register.confirmPasswordPlaceholder')"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleRegister"
            class="login-btn"
          >
            {{ loading ? t('register.registering') : t('register.registerBtn') }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <el-link type="primary" @click="goLogin">{{ t('register.hasAccount') }}</el-link>
        <el-divider direction="vertical" />
        <el-link @click="goBack">{{ t('register.backHome') }}</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import request from '@/utils/request'
import { notify } from '@/utils/notify'

const { t } = useI18n()

const router = useRouter()
const registerFormRef = ref(null)
const loading = ref(false)

const registerForm = reactive({
  username: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error(t('register.passwordMismatch')))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, message: t('register.validateUsername'), trigger: 'blur' },
    { min: 2, max: 20, message: t('register.validateUsernameLen'), trigger: 'blur' }
  ],
  password: [
    { required: true, message: t('register.validatePassword'), trigger: 'blur' },
    { min: 5, max: 20, message: t('register.validatePasswordLen'), trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: t('register.validateConfirmPassword'), trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  if (!registerFormRef.value) return

  await registerFormRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true
    try {
      const res = await request.post('/register', {
        username: registerForm.username,
        password: registerForm.password,
        code: '',
        uuid: ''
      })

      if (res.data.code === 200) {
        notify('success', t('register.registerSuccess'))
        setTimeout(() => {
          router.push('/login')
        }, 1500)
      } else {
        notify('error', res.data.msg || t('register.registerFailed'))
      }
    } catch (error) {
      const errorMsg = error.response?.data?.msg || error.message || t('register.registerNetworkError')
      notify('error', errorMsg)
    } finally {
      loading.value = false
    }
  })
}

const goLogin = () => {
  router.push('/login')
}

const goBack = () => {
  router.push('/')
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url('@/assets/bg-tibet.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: relative;
  padding: 20px;
}

.login-overlay {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 1;
}

.login-box {
  position: relative;
  z-index: 2;
  width: 100%;
  max-width: 420px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
  padding: 40px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  font-size: 28px;
  color: #333;
  margin: 0 0 10px 0;
  font-weight: 600;
}

.login-header p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

.login-form {
  margin-top: 20px;
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
}

.login-footer {
  margin-top: 20px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>

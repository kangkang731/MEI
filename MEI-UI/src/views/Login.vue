<template>
  <div class="login-container">
    <div class="login-overlay"></div>
    <div class="login-box">
      <div class="login-header">
        <h2>{{ t('login.title') }}</h2>
        <p>{{ t('login.subtitle') }}</p>
      </div>
      
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            :placeholder="t('login.usernamePlaceholder')"
            prefix-icon="User"
            size="large"
            clearable
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            :placeholder="t('login.passwordPlaceholder')"
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
            @click="handleLogin"
            class="login-btn"
          >
            {{ loading ? t('login.logining') : t('login.loginBtn') }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <el-link type="primary" @click="goRegister">{{ t('login.noAccount') }}</el-link>
        <el-divider direction="vertical" />
        <el-link @click="goBack">{{ t('login.backHome') }}</el-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useI18n } from 'vue-i18n'
import request from '@/utils/request'

const { t } = useI18n()

const router = useRouter()
const route = useRoute()

const loginFormRef = ref(null)
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: t('login.validateUsername'), trigger: 'blur' },
    { min: 2, max: 20, message: t('login.validateUsernameLen'), trigger: 'blur' }
  ],
  password: [
    { required: true, message: t('login.validatePassword'), trigger: 'blur' },
    { min: 5, max: 20, message: t('login.validatePasswordLen'), trigger: 'blur' }
  ]
}

// 处理登录
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    try {
      // 调用后端登录接口
      const res = await request.post('/login', {
        username: loginForm.username,
        password: loginForm.password,
        code: '',  // 如果需要验证码，后续添加
        uuid: ''   // 如果需要验证码，后续添加
      })
      
      const data = res.data
      
      if (data.code === 200 && data.token) {
        // 保存登录信息
        localStorage.setItem('token', data.token)
        console.log('✅ Token 已保存:', data.token)
        
        // 获取用户信息
        await getUserInfo()
        
        ElMessage.success(t('login.loginSuccess'))
        
        // 跳转到原页面或首页
        const redirect = route.query.redirect || '/'
        router.push(redirect)
      } else {
        ElMessage.error(data.msg || t('login.loginFailed'))
      }
    } catch (error) {
      console.error('登录失败:', error)
      const errorMsg = error.response?.data?.msg || error.message || t('login.loginNetworkError')
      ElMessage.error(errorMsg)
    } finally {
      loading.value = false
    }
  })
}

// 获取用户信息
const getUserInfo = async () => {
  try {
    console.log('📡 正在调用 /getInfo 接口...')
    const res = await request.get('/getInfo')
    console.log('📥 /getInfo 完整响应:', res)
    console.log('📥 /getInfo res.data:', res.data)
    
    const data = res.data
    
    if (data.code === 200) {
      console.log('✅ 接口返回成功')
      console.log('📦 data.user:', data.user)
      console.log(' data.user.userId:', data.user?.userId)
      console.log(' data.user.userName:', data.user?.userName)
      
      if (data.user) {
        const user = data.user
        
        // 保存用户信息到 localStorage
        localStorage.setItem('userId', user.userId)
        localStorage.setItem('userName', user.userName || user.username || '')
        localStorage.setItem('username', user.userName || user.username || '') // 兼容旧代码
        localStorage.setItem('nickName', user.nickName || '')
        localStorage.setItem('avatar', user.avatar || '')
        localStorage.setItem('email', user.email || '')
        localStorage.setItem('phonenumber', user.phonenumber || '')
        localStorage.setItem('sex', user.sex || '0')
        
        console.log('✅ localStorage 已更新:')
        console.log('  - userId:', localStorage.getItem('userId'))
        console.log('  - userName:', localStorage.getItem('userName'))
        console.log('  - token:', localStorage.getItem('token'))
      } else {
        console.error('❌ data.user 不存在！')
      }
    } else {
      console.error('❌ 接口返回失败，code:', data.code, 'msg:', data.msg)
    }
  } catch (error) {
    console.error('❌ 获取用户信息异常:', error)
    console.error('❌ error.response:', error.response)
    console.error('❌ error.response.data:', error.response?.data)
  }
}

// 去注册页面
const goRegister = () => {
  router.push('/register')
}

// 返回首页
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

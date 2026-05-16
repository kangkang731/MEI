import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import i18n from './locale'
import './utils/request'  // 导入 axios 拦截器配置

const app = createApp(App)

app.use(router)
app.use(i18n)

// 全局错误处理器，防止组件渲染异常导致白屏且无法恢复
app.config.errorHandler = (err) => {
  console.warn('Vue 组件渲染异常:', err)
}

app.mount('#app')

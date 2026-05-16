import { createI18n } from 'vue-i18n'
import zhCN from './lang/zh-CN'
import boCN from './lang/bo-CN' // 藏语配置

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('language') || 'zh-CN',
  messages: {
    'zh-CN': zhCN,
    'bo-CN': boCN,
  },
})

export default i18n

// import { fileURLToPath, URL } from 'node:url'
//
// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'
// import vueDevTools from 'vite-plugin-vue-devtools'
//
// // https://vite.dev/config/
// export default defineConfig({
//   plugins: [
//     vue(),
//     vueDevTools(),
//   ],
//   resolve: {
//     alias: {
//       '@': fileURLToPath(new URL('./src', import.meta.url))
//     },
//   },
// })
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import path from 'path'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
      imports: ['vue', 'vue-router', 'pinia']
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    })
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    port: 8080,
    proxy: {
      '/login': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/register': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/getInfo': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/getRouters': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/system': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/profile': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/mei_achievement': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/mei_statistics': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/mei': {
        target: 'http://localhost:8085',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/mei/, '')
      }
    }
  }
})
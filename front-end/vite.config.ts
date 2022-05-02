import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path';
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    host: true,
    proxy: {
      '/api': {
        target: 'http://192.168.43.203:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  },
  base: './',
  resolve: {
    alias: {
      "@": path.resolve(__dirname,"src")
    },
    extensions: ['.js', '.json', '.ts']
  }
})

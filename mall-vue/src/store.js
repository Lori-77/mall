import { reactive } from 'vue'

// 全局共享状态（登录用户、购物车数量等）
export const store = reactive({
  user: JSON.parse(localStorage.getItem('user') || 'null'),

  login(userData) {
    this.user = userData
    localStorage.setItem('user', JSON.stringify(userData))
  },

  logout() {
    this.user = null
    localStorage.removeItem('user')
  }
})

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { store } from '../store.js'

const router = useRouter()

const isLogin = ref(true)
const form = ref({ username: '', password: '' })
const msg = ref('')

const submit = async () => {
  msg.value = ''
  if (!form.value.username || !form.value.password) {
    msg.value = '请填写用户名和密码'
    return
  }

  const url = isLogin.value
    ? 'http://localhost:8080/users/login'
    : 'http://localhost:8080/users/register'

  try {
    const res = await axios.post(url, form.value)
    if (res.data.code === 200) {
      if (isLogin.value) {
        // 写入全局 store，Navbar 会立刻更新
        store.login(res.data.data)
        router.push('/')
      } else {
        msg.value = '注册成功，请登录'
        const savedName = form.value.username
        const savedPwd = form.value.password
        isLogin.value = true
        form.value.username = savedName
        form.value.password = savedPwd
      }
    } else {
      msg.value = res.data.msg
    }
  } catch (e) {
    msg.value = '请求失败，请检查网络'
  }
}
</script>

<template>
  <div class="login-page">
    <div class="form-box">
      <h2>{{ isLogin ? '登录' : '注册' }}</h2>

      <input v-model="form.username" placeholder="用户名" />
      <input v-model="form.password" type="password" placeholder="密码" />

      <p class="msg" v-if="msg">{{ msg }}</p>

      <button class="btn" @click="submit">{{ isLogin ? '登录' : '注册' }}</button>

      <p class="switch">
        {{ isLogin ? '没有账号？' : '已有账号？' }}
        <a href="#" @click.prevent="isLogin = !isLogin; msg = ''">
          {{ isLogin ? '去注册' : '去登录' }}
        </a>
      </p>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.form-box {
  width: 380px;
  background: var(--white);
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
}

.form-box h2 {
  text-align: center;
  margin-bottom: 24px;
}

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 16px;
  border: 1px solid var(--border);
  border-radius: 4px;
  font-size: 14px;
}

input:focus {
  outline: none;
  border-color: var(--primary);
}

.btn {
  width: 100%;
  padding: 12px;
  background: var(--primary);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.btn:hover {
  opacity: 0.9;
}

.msg {
  color: var(--danger);
  text-align: center;
  margin-bottom: 12px;
  font-size: 14px;
}

.switch {
  text-align: center;
  margin-top: 16px;
  font-size: 14px;
  color: #666;
}

.switch a {
  color: var(--primary);
}
</style>

<script setup>
import { store } from '../store.js'
</script>

<template>
  <header class="navbar">
    <h1 class="logo" @click="$router.push('/')">ABC商城</h1>
    <nav>
      <router-link to="/">首页</router-link>
      <router-link to="/product">商品</router-link>
      <router-link to="/cart">购物车</router-link>
      <template v-if="!store.user">
        <router-link to="/login">登录</router-link>
      </template>
      <template v-else>
        <router-link v-if="store.user.username === 'admin'" to="/admin">后台</router-link>
        <router-link to="/user"><span class="username">{{ store.user.username }}</span></router-link>
        <a href="#" @click.prevent="store.logout()">退出</a>
      </template>
    </nav>
  </header>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: var(--dark);
  color: var(--white);
  padding: 14px 40px;
}

.logo {
  cursor: pointer;
  font-size: 20px;
}

nav {
  display: flex;
  align-items: center;
  gap: 24px;
}

nav a {
  color: var(--white);
  font-size: 15px;
  transition: color 0.2s;
}

nav a:hover,
nav a.router-link-active {
  color: var(--primary);
}

.username {
  color: var(--primary);
}
</style>

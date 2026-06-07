<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { store } from '../store.js'

const users = ref([])
const orders = ref([])

const fetchOrders = async () => {
  try {
    const res = await axios.get('http://localhost:8080/orders', {
      params: { userId: store.user.id }
    })
    if (res.data.code === 200) {
      orders.value = res.data.data
    }
  } catch (e) {
    console.log('加载订单失败')
  }
}

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8080/users')
    if (res.data.code === 200) {
      users.value = res.data.data
    }
  } catch (e) {
    console.log('加载失败')
  }

  if (store.user) {
    fetchOrders()
  }
})
</script>

<template>
  <div class="user-page" v-if="store.user">
    <h2>用户中心</h2>
    <div class="info-card">
      <p><strong>用户名：</strong>{{ store.user.username }}</p>
    </div>

    <!-- 我的订单（管理员不显示） -->
    <template v-if="store.user.username !== 'admin'">
    <h3 class="list-title">我的订单</h3>
    <table class="order-table" v-if="orders.length > 0">
      <thead>
        <tr>
          <th>订单号</th>
          <th>商品</th>
          <th>金额</th>
          <th>时间</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="o in orders" :key="o.id">
          <td>#{{ o.id }}</td>
          <td>{{ o.items }}</td>
          <td>￥{{ o.totalPrice }}</td>
          <td>{{ o.createTime ? o.createTime.substring(0, 10) : '' }}</td>
        </tr>
      </tbody>
    </table>
    <p v-else class="no-order">暂无订单</p>
    </template>

    <h3 class="list-title">所有用户列表</h3>
    <table class="user-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>用户名</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="u in users" :key="u.id">
          <td>{{ u.id }}</td>
          <td>{{ u.username }}</td>
        </tr>
      </tbody>
    </table>
  </div>

  <div class="no-login" v-else>
    <p>请先 <router-link to="/login">登录</router-link></p>
  </div>
</template>

<style scoped>
.user-page {
  padding: 40px 0;
}

.user-page h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 26px;
}

.info-card {
  background: var(--white);
  padding: 50px;
  border-radius: 8px;
  text-align: center;
  font-size: 22px;
  max-width: 700px;
  margin: 0 auto;
}

.list-title {
  margin-top: 40px;
  margin-bottom: 16px;
  font-size: 20px;
}

.order-table,
.user-table {
  width: 100%;
  border-collapse: collapse;
  background: var(--white);
  border-radius: 8px;
  overflow: hidden;
}

.order-table th,
.order-table td,
.user-table th,
.user-table td {
  padding: 16px;
  text-align: center;
  border-bottom: 1px solid var(--border);
  font-size: 15px;
}

.order-table th,
.user-table th {
  background: var(--dark);
  color: var(--white);
}

.user-table th:first-child,
.user-table td:first-child {
  width: 20%;
}

.user-table th:last-child,
.user-table td:last-child {
  width: 80%;
}

.no-order {
  text-align: center;
  color: #999;
  padding: 30px;
  background: var(--white);
  border-radius: 8px;
}

.no-login {
  text-align: center;
  padding: 80px 0;
  font-size: 18px;
}

.no-login a {
  color: var(--primary);
}
</style>

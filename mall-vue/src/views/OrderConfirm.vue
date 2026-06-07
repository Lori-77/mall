<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const cart = ref(JSON.parse(localStorage.getItem('cart') || '[]'))

const totalPrice = () => {
  return cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)
}

// 支付（模拟）
const pay = () => {
  alert('支付成功！')
  localStorage.removeItem('cart')
  router.push('/cart')
}
</script>

<template>
  <div class="confirm-page">
    <h2>确认订单</h2>

    <div class="order-box">
      <table>
        <thead>
          <tr>
            <th>商品</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cart" :key="item.id">
            <td>{{ item.name }}</td>
            <td>￥{{ item.price }}</td>
            <td>{{ item.quantity }}</td>
            <td>￥{{ (item.price * item.quantity).toFixed(2) }}</td>
          </tr>
        </tbody>
      </table>

      <div class="total">
        合计：<span>￥{{ totalPrice() }}</span>
      </div>

      <div class="btns">
        <button class="pay-btn" @click="pay">支付</button>
        <button class="back-btn" @click="router.push('/cart')">返回购物车</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.confirm-page {
  padding: 40px 0;
}

.confirm-page h2 {
  text-align: center;
  margin-bottom: 24px;
  font-size: 24px;
}

.order-box {
  max-width: 700px;
  margin: 0 auto;
  background: var(--white);
  border-radius: 10px;
  padding: 30px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 14px;
  text-align: center;
  border-bottom: 1px solid var(--border);
  font-size: 15px;
}

th {
  background: var(--dark);
  color: var(--white);
}

.total {
  text-align: right;
  padding: 20px 0;
  font-size: 18px;
}

.total span {
  color: var(--danger);
  font-size: 24px;
  font-weight: bold;
}

.btns {
  display: flex;
  justify-content: center;
  gap: 16px;
}

.pay-btn {
  padding: 12px 40px;
  background: var(--danger);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.back-btn {
  padding: 12px 40px;
  background: #999;
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.pay-btn:hover, .back-btn:hover {
  opacity: 0.85;
}
</style>

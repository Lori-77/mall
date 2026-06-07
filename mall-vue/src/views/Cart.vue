<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const cart = ref(JSON.parse(localStorage.getItem('cart') || '[]'))

const getImageUrl = (name) => {
  return new URL(`../assets/${name}`, import.meta.url).href
}

// 刷新购物车数据
const refreshCart = () => {
  cart.value = JSON.parse(localStorage.getItem('cart') || '[]')
}

// 修改数量
const changeQty = (item, delta) => {
  item.quantity += delta
  if (item.quantity <= 0) {
    removeItem(item)
    return
  }
  localStorage.setItem('cart', JSON.stringify(cart.value))
  refreshCart()
}

// 删除
const removeItem = (item) => {
  cart.value = cart.value.filter(i => i.id !== item.id)
  localStorage.setItem('cart', JSON.stringify(cart.value))
}

// 计算总价
const totalPrice = () => {
  return cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)
}
</script>

<template>
  <div class="cart-page">
    <h2>购物车</h2>

    <div v-if="cart.length === 0" class="empty">
      <p>购物车是空的，去<router-link to="/product">逛逛</router-link>吧</p>
    </div>

    <div v-else>
      <table class="cart-table">
        <thead>
          <tr>
            <th>商品</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in cart" :key="item.id">
            <td class="goods">
              <img :src="getImageUrl(item.image)" :alt="item.name" @click="router.push(`/product/${item.id}`)" />
              <span>{{ item.name }}</span>
            </td>
            <td>￥{{ item.price }}</td>
            <td>
              <button class="qty-btn" @click="changeQty(item, -1)">-</button>
              <span class="qty">{{ item.quantity }}</span>
              <button class="qty-btn" @click="changeQty(item, 1)">+</button>
            </td>
            <td>￥{{ (item.price * item.quantity).toFixed(2) }}</td>
            <td><button class="del-btn" @click="removeItem(item)">删除</button></td>
          </tr>
        </tbody>
      </table>

      <div class="bottom">
        <p class="total">合计：<span>￥{{ totalPrice() }}</span></p>
        <button class="btn" @click="router.push('/order-confirm')">去结算</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.cart-page {
  padding: 40px 0;
}

.cart-page h2 {
  margin-bottom: 24px;
  font-size: 24px;
}

.empty {
  text-align: center;
  padding: 80px 0;
  font-size: 18px;
  color: #999;
}

.empty a {
  color: var(--primary);
}

.cart-table {
  width: 100%;
  border-collapse: collapse;
  background: var(--white);
  border-radius: 8px;
  overflow: hidden;
}

.cart-table th,
.cart-table td {
  padding: 20px 16px;
  text-align: center;
  border-bottom: 1px solid var(--border);
  font-size: 15px;
}

.cart-table th {
  background: var(--dark);
  color: var(--white);
}

.goods {
  display: flex;
  align-items: center;
  gap: 16px;
  justify-content: center;
}

.goods img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
  cursor: pointer;
}

.qty-btn {
  width: 32px;
  height: 32px;
  border: 1px solid var(--border);
  background: var(--white);
  cursor: pointer;
  border-radius: 3px;
  font-size: 18px;
}

.qty {
  margin: 0 14px;
  font-size: 16px;
}

.del-btn {
  padding: 6px 16px;
  border: none;
  background: var(--danger);
  color: var(--white);
  border-radius: 3px;
  cursor: pointer;
  font-size: 14px;
}

.bottom {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 24px;
  background: var(--white);
  padding: 24px;
  border-radius: 8px;
}

.total {
  font-size: 18px;
}

.total span {
  color: var(--danger);
  font-size: 24px;
  font-weight: bold;
}

.btn {
  padding: 14px 36px;
  background: var(--danger);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.btn:hover {
  opacity: 0.85;
}
</style>

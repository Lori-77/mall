<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const product = ref(null)
const loading = ref(true)

const getImageUrl = (name) => {
  return new URL(`../assets/${name}`, import.meta.url).href
}

const getCart = () => {
  return JSON.parse(localStorage.getItem('cart') || '[]')
}

const addToCart = () => {
  const cart = getCart()
  const exist = cart.find(item => item.id === product.value.id)
  if (exist) {
    exist.quantity++
  } else {
    cart.push({
      id: product.value.id,
      name: product.value.name,
      price: product.value.price,
      image: product.value.image,
      quantity: 1
    })
  }
  localStorage.setItem('cart', JSON.stringify(cart))
  alert('已加入购物车')
}

onMounted(async () => {
  try {
    const res = await axios.get(`http://localhost:8080/products/${route.params.id}`)
    if (res.data.code === 200) {
      product.value = res.data.data
    }
  } catch (e) {
    console.log('加载失败')
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="detail-page">
    <div v-if="loading" class="loading">加载中...</div>

    <div v-else-if="product" class="detail-box">
      <div class="img-box">
        <img :src="getImageUrl(product.image)" :alt="product.name" />
      </div>
      <div class="info">
        <h1>{{ product.name }}</h1>
        <p class="price">￥{{ product.price }}</p>
        <p class="stock">库存：{{ product.stock }}</p>
        <p class="desc">{{ product.description || '暂无描述' }}</p>
        <div class="btns">
          <button class="btn-cart" @click="addToCart">加入购物车</button>
          <button class="btn-back" @click="router.back()">返回</button>
        </div>
      </div>
    </div>

    <div v-else class="empty">商品不存在</div>
  </div>
</template>

<style scoped>
.detail-page {
  padding: 40px 0;
}

.loading, .empty {
  text-align: center;
  padding: 80px 0;
  font-size: 16px;
  color: #999;
}

.detail-box {
  display: flex;
  gap: 50px;
  background: var(--white);
  padding: 40px;
  border-radius: 10px;
  align-items: flex-start;
}

.img-box {
  flex: 0 0 420px;
}

.img-box img {
  width: 100%;
  aspect-ratio: 4 / 3;
  object-fit: cover;
  border-radius: 8px;
}

.info {
  flex: 1;
}

.info h1 {
  font-size: 24px;
  margin-bottom: 16px;
}

.price {
  color: var(--danger);
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 12px;
}

.stock {
  font-size: 14px;
  color: #999;
  margin-bottom: 20px;
}

.desc {
  font-size: 15px;
  color: #555;
  line-height: 1.8;
  margin-bottom: 30px;
  padding: 16px 0;
  border-top: 1px solid var(--border);
}

.btns {
  display: flex;
  gap: 14px;
}

.btn-cart {
  padding: 12px 36px;
  border: none;
  border-radius: 4px;
  font-size: 15px;
  cursor: pointer;
  background: var(--danger);
  color: var(--white);
}

.btn-back {
  padding: 12px 36px;
  border: none;
  border-radius: 4px;
  font-size: 15px;
  cursor: pointer;
  background: #bbb;
  color: var(--white);
}

.btn-cart:hover, .btn-back:hover {
  opacity: 0.85;
}
</style>

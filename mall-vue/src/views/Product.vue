<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const products = ref([])
const keyword = ref('')
const sortType = ref('')  // 'asc' 价格从低到高, 'desc' 从高到低, '' 默认

// 排序后的商品列表
const sortedProducts = computed(() => {
  const list = [...products.value]
  if (sortType.value === 'asc') {
    list.sort((a, b) => a.price - b.price)
  } else if (sortType.value === 'desc') {
    list.sort((a, b) => b.price - a.price)
  }
  return list
})

const getImageUrl = (name) => {
  return new URL(`../assets/${name}`, import.meta.url).href
}

const fetchProducts = async () => {
  try {
    const res = await axios.get('http://localhost:8080/products')
    if (res.data.code === 200) {
      products.value = res.data.data
    }
  } catch (e) {
    console.log('加载失败')
  }
}

const search = async () => {
  if (!keyword.value.trim()) {
    fetchProducts()
    return
  }
  try {
    const res = await axios.get('http://localhost:8080/products/search', {
      params: { keyword: keyword.value }
    })
    if (res.data.code === 200) {
      products.value = res.data.data
    }
  } catch (e) {
    console.log('搜索失败')
  }
}

onMounted(fetchProducts)

const goDetail = (id) => {
  router.push(`/product/${id}`)
}
</script>

<template>
  <div class="product-page">
    <h2>全部商品</h2>

    <div class="toolbar">
      <div class="search-bar">
        <input v-model="keyword" placeholder="搜索商品..." @keyup.enter="search" />
        <button class="btn" @click="search">搜索</button>
      </div>
      <div class="sort-bar">
        <span>排序：</span>
        <button :class="{ active: sortType === '' }" @click="sortType = ''">默认</button>
        <button :class="{ active: sortType === 'asc' }" @click="sortType = 'asc'">价格↑</button>
        <button :class="{ active: sortType === 'desc' }" @click="sortType = 'desc'">价格↓</button>
      </div>
    </div>

    <div class="flex-grid">
      <div class="card" v-for="item in sortedProducts" :key="item.id">
        <img :src="getImageUrl(item.image)" :alt="item.name" @click="goDetail(item.id)" />
        <h3 @click="goDetail(item.id)" class="card-name">{{ item.name }}</h3>
        <p class="price">￥{{ item.price }}</p>
        <p>库存：{{ item.stock }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.product-page {
  padding: 50px 0;
}

.product-page h2 {
  font-size: 28px;
  margin-bottom: 24px;
}

.search-bar {
  display: flex;
  gap: 10px;
}

.search-bar input {
  flex: 1;
  max-width: 400px;
  padding: 10px 14px;
  border: 1px solid var(--border);
  border-radius: 4px;
  font-size: 14px;
}

.search-bar input:focus {
  outline: none;
  border-color: var(--primary);
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  gap: 20px;
  flex-wrap: wrap;
}

.sort-bar {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  white-space: nowrap;
}

.sort-bar button {
  padding: 6px 12px;
  border: 1px solid var(--border);
  background: var(--white);
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  color: #666;
}

.sort-bar button.active {
  border-color: var(--primary);
  color: var(--primary);
}

.btn {
  padding: 10px 24px;
  background: var(--primary);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.btn:hover { opacity: 0.9; }

.card {
  width: 300px;
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 18px;
  background: var(--white);
  text-align: center;
  transition: box-shadow 0.3s, transform 0.2s;
}

.card:hover { box-shadow: 0 6px 16px rgba(0,0,0,0.12); transform: translateY(-2px); }

.card img {
  width: 100%;
  height: 240px;
  object-fit: cover;
  border-radius: 6px;
  cursor: pointer;
}

.card h3 { margin: 12px 0 6px; font-size: 16px; }

.card-name {
  cursor: pointer;
  color: #333;
}

.card-name:hover {
  color: var(--primary);
}

.price {
  color: var(--danger);
  font-size: 18px;
  font-weight: bold;
}
</style>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const products = ref([])
const loadError = ref(false)

// 轮播图
const slides = [
  { img: 'AMD.jpg', alt: 'AMD' },
  { img: 'AMD2.jpg', alt: 'AMD2' },
  { img: 'AMD3.jpg', alt: 'AMD3' }
]
const currentSlide = ref(0)
let timer = null
const startCarousel = () => {
  timer = setInterval(() => {
    currentSlide.value = (currentSlide.value + 1) % slides.length
  }, 3000)
}
onUnmounted(() => clearInterval(timer))

const getImageUrl = (name) => {
  return new URL(`../assets/${name}`, import.meta.url).href
}

onMounted(async () => {
  startCarousel()
  try {
    const res = await axios.get('http://localhost:8080/products')
    if (res.data.code === 200) {
      products.value = res.data.data
    }
  } catch (e) {
    loadError.value = true
  }
})

const goDetail = (id) => {
  router.push(`/product/${id}`)
}
</script>

<template>
  <!-- 横幅 -->
  <section class="banner">
    <img src="../assets/主页.jpg" alt="banner" />
  </section>

  <!-- 商品列表 -->
  <section class="products">
    <h2>热门商品</h2>
    <p v-if="loadError" class="tip">加载失败，请检查后端是否启动</p>
    <div class="flex-grid">
      <div class="card" v-for="item in products.slice(0, 8)" :key="item.id" @click="goDetail(item.id)">
        <img :src="getImageUrl(item.image)" :alt="item.name" />
        <h3>{{ item.name }}</h3>
        <p class="price">￥{{ item.price }}</p>
        <p>库存：{{ item.stock }}</p>
      </div>
    </div>
  </section>

  <!-- 新品推荐 -->
  <section class="intro">
    <div class="carousel">
      <img
        v-for="(s, i) in slides"
        :key="i"
        :src="getImageUrl(s.img)"
        :alt="s.alt"
        :class="{ active: i === currentSlide }"
      />
    </div>

    <div class="text">
      <h2>新品推荐</h2>
      <p>高性能电竞设备，打造你的专属游戏空间。</p>
    </div>
  </section>
</template>

<style scoped>
.banner {
  width: 100%;
}

.banner img {
  width: 100%;
  height: 500px;
}

.products {
  padding: 40px 0;
}

.products h2 {
  text-align: center;
  margin-bottom: 28px;
  font-size: 26px;
}

.tip {
  text-align: center;
  color: #999;
  margin-bottom: 20px;
}

.card {
  width: 300px;
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 18px;
  background: var(--white);
  text-align: center;
  transition: box-shadow 0.3s, transform 0.2s;
  cursor: pointer;
}

.card:hover {
  box-shadow: 0 6px 16px rgba(0,0,0,0.12);
  transform: translateY(-2px);
}

.card img {
  width: 100%;
  height: 240px;
  object-fit: cover;
  border-radius: 6px;
}

.card h3 {
  margin: 12px 0 6px;
  font-size: 16px;
}

.price {
  color: var(--danger);
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 4px;
}

.intro {
  background: var(--white);
  padding: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  gap: 60px;
  max-width: 1272px;
  margin: 0 auto;
}

.carousel {
  width: 350px;
  flex-shrink: 0;
  position: relative;
  overflow: hidden;
  border-radius: 8px;
}

.carousel img {
  width: 100%;
  height: 260px;
  object-fit: cover;
  display: none;
  border-radius: 8px;
}

.carousel img.active {
  display: block;
}

.dots {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
}

.dots span {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255,255,255,0.5);
  cursor: pointer;
}

.dots span.on {
  background: var(--white);
}

.intro h2 {
  font-size: 22px;
  margin-bottom: 10px;
}

.intro p {
  color: #666;
  font-size: 15px;
  line-height: 1.8;
}
</style>

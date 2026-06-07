<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// ===== 商品管理 =====
const products = ref([])
const showForm = ref(false)
const isEdit = ref(false)
const form = ref({ id: null, name: '', price: null, stock: null, image: '', description: '', status: 1 })

const fetchProducts = async () => {
  const res = await axios.get('http://localhost:8080/products/all')
  if (res.data.code === 200) products.value = res.data.data
}

// 新增
const openAdd = () => {
  isEdit.value = false
  form.value = { id: null, name: '', price: null, stock: null, image: '', description: '', status: 1 }
  showForm.value = true
}

// 编辑
const openEdit = (item) => {
  isEdit.value = true
  form.value = { ...item }
  showForm.value = true
}

// 提交
const submit = async () => {
  const data = { ...form.value }
  if (isEdit.value) {
    await axios.put('http://localhost:8080/products', data)
  } else {
    await axios.post('http://localhost:8080/products', data)
  }
  showForm.value = false
  fetchProducts()
}

// 删除
const del = async (id) => {
  if (!confirm('确认删除？')) return
  await axios.delete(`http://localhost:8080/products/${id}`)
  fetchProducts()
}

// 上架/下架切换
const toggle = async (id) => {
  await axios.put(`http://localhost:8080/products/${id}/toggle`)
  fetchProducts()
}

// ===== 用户管理 =====
const users = ref([])

const fetchUsers = async () => {
  const res = await axios.get('http://localhost:8080/users')
  if (res.data.code === 200) users.value = res.data.data
}

const delUser = async (id, username) => {
  if (username === 'admin') { alert('不能删除admin自己'); return }
  if (!confirm(`确认删除用户 ${username}？`)) return
  await axios.delete(`http://localhost:8080/users/${id}`)
  fetchUsers()
}

onMounted(() => {
  fetchProducts()
  fetchUsers()
})
</script>

<template>
  <div class="admin-page">
    <h2>管理员后台</h2>

    <!-- 商品管理 -->
    <h3>商品管理 <button class="btn-add" @click="openAdd">添加商品</button></h3>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>名称</th>
          <th>价格</th>
          <th>库存</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in products" :key="p.id" :class="{ offline: p.status === 0 }">
          <td>{{ p.id }}</td>
          <td>{{ p.name }}</td>
          <td>￥{{ p.price }}</td>
          <td>{{ p.stock }}</td>
          <td>
            <button class="btn-edit" @click="openEdit(p)">编辑</button>
            <button class="btn-toggle" :class="p.status === 1 ? 'off' : 'on'" @click="toggle(p.id)">
              {{ p.status === 1 ? '下架' : '上架' }}
            </button>
            <button class="btn-del" @click="del(p.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 用户管理 -->
    <h3 style="margin-top: 40px;">用户管理</h3>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>用户名</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="u in users" :key="u.id">
          <td>{{ u.id }}</td>
          <td>{{ u.username }}</td>
          <td>
            <button class="btn-sm btn-del" @click="delUser(u.id, u.username)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 弹窗 -->
    <div class="modal" v-if="showForm" @click.self="showForm = false">
      <div class="modal-box">
        <h3>{{ isEdit ? '编辑商品' : '添加商品' }}</h3>
        <input v-model="form.name" placeholder="商品名称" />
        <input v-model.number="form.price" type="number" placeholder="价格" />
        <input v-model.number="form.stock" type="number" placeholder="库存" />
        <input v-model="form.image" placeholder="图片文件名" />
        <input v-model="form.description" placeholder="描述（选填）" />
        <div class="modal-btns">
          <button class="btn-primary" @click="submit">{{ isEdit ? '保存' : '添加' }}</button>
          <button class="btn-cancel" @click="showForm = false">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.admin-page { padding: 40px 0; }
.admin-page h2 { font-size: 26px; margin-bottom: 24px; }
.admin-page h3 { font-size: 20px; margin-bottom: 14px; }

.btn-add {
  padding: 6px 16px;
  background: var(--primary);
  color: var(--white);
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

table {
  width: 100%;
  border-collapse: collapse;
  background: var(--white);
  border-radius: 8px;
  overflow: hidden;
}

th, td {
  padding: 14px;
  text-align: center;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
}

th { background: var(--dark); color: var(--white); }

.offline { background: #fafafa; color: #aaa; }

.btn-edit, .btn-del {
  padding: 5px 14px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  font-size: 13px;
}
.btn-edit { background: var(--primary); color: var(--white); }
.btn-del  { background: var(--danger); color: var(--white); margin-left: 8px; }

/* 下架/上架按钮，大一点明显 */
.btn-toggle {
  padding: 6px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  margin-left: 8px;
}
.btn-toggle.off { background: #e6a23c; color: var(--white); }
.btn-toggle.on  { background: #67c23a; color: var(--white); }

/* 弹窗 */
.modal {
  position: fixed; inset: 0;
  background: rgba(0,0,0,0.4);
  display: flex; justify-content: center; align-items: center;
  z-index: 100;
}
.modal-box {
  background: var(--white); padding: 30px;
  border-radius: 8px; width: 400px;
}
.modal-box h3 { text-align: center; margin-bottom: 20px; }
.modal-box input {
  width: 100%; padding: 10px; margin-bottom: 14px;
  border: 1px solid var(--border); border-radius: 4px; font-size: 14px;
}
.modal-box input:focus { outline: none; border-color: var(--primary); }
.modal-btns { display: flex; gap: 12px; justify-content: center; margin-top: 8px; }
.btn-primary {
  padding: 10px 24px; background: var(--primary); color: var(--white);
  border: none; border-radius: 4px; cursor: pointer;
}
.btn-cancel {
  padding: 10px 24px; background: #999; color: var(--white);
  border: none; border-radius: 4px; cursor: pointer;
}
</style>

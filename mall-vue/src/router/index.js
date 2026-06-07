import { createRouter, createWebHistory } from "vue-router";
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import UserCenter from '../views/UserCenter.vue'
import Product from '../views/Product.vue'
import ProductDetail from '../views/ProductDetail.vue'
import Cart from '../views/Cart.vue'
import OrderConfirm from '../views/OrderConfirm.vue'
import Admin from '../views/Admin.vue'

const routes = [
    { path: '/', name: 'Home', component: Home},
    { path: '/product', name: 'Product', component: Product},
    { path: '/product/:id', name: 'ProductDetail', component: ProductDetail},
    { path: '/login', name: 'Login', component: Login},
    { path: '/user', name: 'UserCenter', component: UserCenter},
    { path: '/cart', name: 'Cart', component: Cart},
    { path: '/order-confirm', name: 'OrderConfirm', component: OrderConfirm},
    { path: '/admin', name: 'Admin', component: Admin},
]

export default createRouter({
   history: createWebHistory(),
   routes,
})

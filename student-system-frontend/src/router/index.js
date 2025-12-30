import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/student',
      name: 'student',
      component: () => import('../views/StudentList.vue') // 以后要写的学生列表页
    }
  ]
})

export default router
import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/auth/LoginView.vue'
import HomeView from '../components/layout/MainLayout.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/auth/RegisterView.vue'),
    },
  ],
})

export default router

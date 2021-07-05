import Vue from 'vue'
import VueRouter from 'vue-router'
import signIn from '../views/SignIn.vue'
import Admin from "../views/Admin";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'SignIn',
    component: signIn
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  },
  {
    path: "*",
    redirect: "/signin"
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

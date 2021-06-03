import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Popular from '../views/Popular.vue'
import Categories from '../views/category/Categories.vue'
import CreateCategory from '../views/category/CreateCategory.vue'
import EditCategory from '../views/category/EditCategory.vue'
import Posts from '../views/post/Posts'
import CreatePost from '../views/post/CreatePost'
import EditPost from '../views/post/EditPost'
import Post from '../views/post/Post'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/popular',
    name: 'Popular',
    component: Popular
  },
  {
    path: '/categories',
    name: 'Categories',
    component: Categories
  },
  {
    path: '/create-category',
    name: 'CreateCategory',
    component: CreateCategory
  },
  {
    path: '/edit-category/:id',
    name: 'EditCategory',
    component: EditCategory
  },
  {
    path: '/content-creator/posts/create',
    name: 'CreatePost',
    component: CreatePost
  },
  {
    path: '/content-creator/posts/edit/:id',
    name: 'EditPost',
    component: EditPost
  },
  {
    path: '/content-creator/posts',
    name: 'ContentCreatorPosts',
    component: Posts
  },
  {
    path: '/post/:id',
    name: 'Post',
    component: Post
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

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
import FilteredPosts from '../views/post/FilteredPosts'
import Users from '../views/user/Users'
import CreateUser from '../views/user/CreateUser'
import EditUser from '../views/user/EditUser'

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
        component: CreateCategory,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/edit-category/:id',
        name: 'EditCategory',
        component: EditCategory,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/content-creator/posts/create',
        name: 'CreatePost',
        component: CreatePost,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/content-creator/posts/edit/:id',
        name: 'EditPost',
        component: EditPost,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/content-creator/posts',
        name: 'ContentCreatorPosts',
        component: Posts,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/post/:id',
        name: 'Post',
        component: Post
    },
    {
        path: '/posts/filtered/:param/:value',
        name: 'FilteredPosts',
        component: FilteredPosts
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            guest: true
        }
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
        meta: {
            guest: true
        }
    },
    {
        path: '/users',
        name: 'Users',
        component: Users,
        meta: {
            admin: true
        }
    },
    {
        path: '/users/create',
        name: 'CreateUser',
        component: CreateUser,
        meta: {
            admin: true
        }
    },
    {
        path: '/users/edit/:id',
        name: 'EditUser',
        component: EditUser,
        meta: {
            admin: true
        }
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {

        if (localStorage.getItem('token') == null) {
            next({
                name: 'Login',
            })
        } else {
            next();
        }
    } else if (to.matched.some(record => record.meta.admin)) {
        if (localStorage.getItem('token') == null) {
            next({
                name: 'Login',
            })
        } else {
            if (localStorage.getItem('adminAccess') == null){
                next({
                    name: 'Login',
                })
            } else {
                next();
            }
        }
    } else if (to.matched.some(record => record.meta.guest)) {
        if (localStorage.getItem('token') == null) {
            next();
        } else {
            next({name: 'Home'});
        }
    } else {
        next()
    }
})

export default router

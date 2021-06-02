import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'



Vue.config.productionTip = false

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueAxios, axios)

const baseURL = process.env.VUE_APP_BASE_URL;
if (typeof baseURL !== 'undefined')
{
  Vue.axios.defaults.baseURL = baseURL;
}
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

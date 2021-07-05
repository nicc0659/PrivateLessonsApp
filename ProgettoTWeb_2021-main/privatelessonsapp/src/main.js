import Vue from 'vue'
import App from './App.vue'
import Vuetify from 'vuetify'
import "./plugins/vuetify.js"
import vuetify from './plugins/vuetify'
import router from './router'
import store from './store'
import axios from "axios"
import VueAxios from "vue-axios"
import Snotify from "vue-snotify";
import 'vue-snotify/styles/material.css';


Vue.use(Vuetify);
Vue.use(VueAxios, axios);
Vue.use(Snotify);

Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

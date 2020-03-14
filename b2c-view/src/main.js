// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'jquery'
import Dialog from 'v-dialogs'
import store from './store/store'
import vUploader from 'v-uploader'

Vue.config.productionTip = false
Vue.use(ElementUI, {zIndex: 6000})
Vue.use(Dialog)
Vue.use(vUploader)
Vue.use(Vuex)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: {App},
  template: '<App/>'
})

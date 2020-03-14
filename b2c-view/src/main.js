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
import config from "./config"

Vue.config.productionTip = false
Vue.use(ElementUI, {zIndex: 6000})
Vue.use(Dialog)
Vue.use(vUploader, {
  uploadFileUrl: config.BASE_PATH + config.APP_CONTEXT + config.V_UPLOAD_FILE,
  deleteFileUrl: config.BASE_PATH + config.APP_CONTEXT + config.V_DEL_FILE,
  uploadFileObjName: 'file',
  fileSizeLimit: '1MB',
  showMessage: (vue, message) => {
    vue.$message.error(message)
  }
})
Vue.use(Vuex)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: {App},
  template: '<App/>'
})

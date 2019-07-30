import axios from 'axios'
import config from '../config'
import qs from 'querystring'
import {Message} from 'element-ui'

const baseAxios = axios.create({
  timeout: config.AXIOS_TIMEOUT,
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
  }
})
baseAxios.all = axios.all
baseAxios.spread = axios.spread
// Add a request interceptor
baseAxios.interceptors.request.use(axiosConfig => {
  axiosConfig.url = config.BASE_PATH + config.APP_CONTEXT + axiosConfig.url
  // 自定义参数,用于后台返回success为false时直接弹出后台返回错误信息
  if (axiosConfig.showError === undefined) {
    axiosConfig.showError = true;
  }
  if (axiosConfig.contentType === 'form') {
    axiosConfig.headers['Content-Type'] = 'application/x-www-form-urlencoded'
    axiosConfig.data = qs.stringify(axiosConfig.data)
  } else {
    axiosConfig.data = JSON.stringify(axiosConfig.data || {})
  }
  return axiosConfig
}, error => {
  return Promise.reject(error)
})
// Add a response interceptor
baseAxios.interceptors.response.use(response => {
  if (response.data.success) {
    return response.data
  }
  if (response.data.errorCode === 1007) {
    // 未授权,跳回登录页面
    localStorage.clear();
    top.location.replace('/#/');
    return Promise.reject(response.data.errorMsg);
  }
  if (response.data.success === undefined) {
    // 不符合标准返回格式,不做处理
    return response.data
  }
  if (response.config.showError) {
    // 直接弹出后台返回错误信息
    Message.error(response.data.errorMsg);
    return Promise.reject(new Error(response.data.errorMsg));
  }
  return response.data
}, error => {
  if (error.response.status === 401 || error.response.status === 403) {
    // 未授权,跳回登录页面
    localStorage.clear()
    top.location.replace('/#/')
  } else {
    Message.error(error.response.statusText)
  }
  return Promise.reject(error)
})
export default baseAxios

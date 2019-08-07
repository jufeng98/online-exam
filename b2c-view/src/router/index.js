import Vue from 'vue'
import Router from 'vue-router'
/*
 * @符号表示是在本地文件系统引入文件,@代表源代码目录,一般是src,引入@的目的是避免../../此类丑陋的语法
 */
import login from '@/pages/users/login'
import usersManage from '@/pages/users/users-manage'
import helloWorld from '@/components/hello-world'
import mainMenu from '@/pages/main-menu'
import env from '@/pages/actuator/env'
import mappings from '@/pages/actuator/mappings'
import configprops from '@/pages/actuator/configprops'
import conditions from '@/pages/actuator/conditions'
import beans from '@/pages/actuator/beans'
import loggers from '@/pages/actuator/loggers'
import metrics from '@/pages/actuator/metrics'
import threads from '@/pages/actuator/threads'
import authoritiesManage from '@/pages/system/authorities-manage'
import menusManage from '@/pages/system/menus-manage'
import topicsManage from '@/pages/learntrain/topics-manage'
import sectionsManage from '@/pages/learntrain/sections-manage'

Vue.use(Router)

export default new Router({
  routes: [
    {path: '/', name: 'login', component: login},
    {path: '/usersManage', name: 'usersManage', component: usersManage},
    {path: '/helloWorld', name: 'helloWorld', component: helloWorld},
    {path: '/mainMenu', name: 'mainIndex', component: mainMenu},
    {path: '/env', name: 'env', component: env},
    {path: '/mappings', name: 'mappings', component: mappings},
    {path: '/configprops', name: 'configprops', component: configprops},
    {path: '/conditions', name: 'conditions', component: conditions},
    {path: '/beans', name: 'beans', component: beans},
    {path: '/loggers', name: 'loggers', component: loggers},
    {path: '/metrics', name: 'metrics', component: metrics},
    {path: '/threads', name: 'threads', component: threads},
    {path: '/authoritiesManage', name: 'authoritiesManage', component: authoritiesManage},
    {path: '/menusManage', name: 'menusManage', component: menusManage},
    {path: '/topicsManage', name: 'topicsManage', component: topicsManage},
    {path: '/sectionsManage', name: 'sectionsManage', component: sectionsManage},
  ]
})

import Vue from 'vue'

import Cookies from 'js-cookie'
import { sha256 } from "js-sha256"

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css
import '@/styles/easyboot.scss' // easyboot css

import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'

import '@/icons' // icon
import '@/permission' // permission control

import { getDicts } from '@/api/system/dict/data'
import Pagination from '@/components/Pagination'
import { deepClone } from '@/utils'
// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.use(permission)

import { parseTime, resetForm, addDateRange, selectDictLabel, download, handleTree } from '@/utils/easyboot'
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.deepClone = deepClone
Vue.prototype.sha256 = sha256

Vue.prototype.msgSuccess = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'success' })
}

Vue.prototype.msgError = function(msg) {
  this.$message({ showClose: true, message: msg + '失败', type: 'error' })
}

Vue.prototype.msgInfo = function(msg) {
  this.$message.info(msg)
}

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)
Vue.use(ElementUI, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})
// https://cn.vuejs.org/v2/api/index.html#productionTip
// 设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = false
// 取消 Vue 所有的日志与警告。
Vue.config.silent = false
// 务必在加载 Vue 之后，立即同步设置以下内容
Vue.config.devtools = true

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import * as echarts from 'echarts' 

import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';

import 'jquery';

import router from './router'
import axios from 'axios'
Vue.prototype.$axios=axios
import './assets/css/main.css';
import './assets/css/theme.scss';

Vue.prototype.$echarts = echarts
Vue.config.productionTip = false


Vue.use(ElementUI);
//Vue.use(Calendar);
require('./mock/index');//运行mock

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  render: h => h(App),
  router
 
})


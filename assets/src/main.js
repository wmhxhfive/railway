// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Router from 'vue-router'
import routerConfig from './router/router';
import ddd from './tool';
import axios from 'axios'
import VeeValidate from 'vee-validate';

Vue.use(VeeValidate);
Vue.use(Router);

// document.addEventListener( "click",function(e){
//   console.log('点击：', e.target);
// },false );

Vue.config.productionTip = false
Vue.prototype.$ajax = axios

var router = new Router(routerConfig)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App, },
  template: '<App />'
})

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Router from 'vue-router'
import routerConfig from './router';
import axios from 'axios'
import VeeValidate from 'vee-validate';

Vue.use(VeeValidate);
Vue.use(Router);

Vue.config.productionTip = false
Vue.prototype.$ajax = axios
Vue.prototype.setCookie = function(cname, cvalue, exdays) {
	if(exdays){
	    var d = new Date();
	    d.setTime(d.getTime() + (exdays*60*1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	}else{
		document.cookie = cname + "=" + cvalue + "; ";
	}
}
Vue.prototype.getCookie= function(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
Vue.prototype.clearCookie= function(name) {  
    this.setCookie(name, "", -1);  
}

var router = new Router(routerConfig)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

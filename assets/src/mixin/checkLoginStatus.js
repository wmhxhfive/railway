/**
 * 检测用户登录状态
 * 
 */
import _mixin from "@/mixin/localStore"
// let _mixin = require("@/mixin/localStore")

var myMixin = {
  created () {
    if(this.getCookie('token')){
      window.location.href="#/manager";
    }
  },
  ..._mixin
}

export default myMixin

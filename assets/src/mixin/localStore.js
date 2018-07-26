// 定义一个混入对象
var myMixin = {
  methods: {
    setCookie: function(cname, cvalue, exdays) {
	  if(exdays){
	      var d = new Date();
	      d.setTime(d.getTime() + (exdays*60*1000));
	      var expires = "expires="+d.toUTCString();
	      document.cookie = cname + "=" + cvalue + "; " + expires;
	  }else{
	    document.cookie = cname + "=" + cvalue + "; ";
	  }
	},
	getCookie: function(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0; i<ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1);
	        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
	    }
	    return "";
	},
	clearCookie: function(name) {  
	    this.setCookie(name, "", -1);  
	},
	/**
	 * HTML5本地存储
	 * 
	 */
	setLocalSave: function (k, v) {
	    var localSet = window.localStorage;
	    if (localSet) {
	        localSet.setItem(k, v);
	    } else {
	        this.setCookie(k, v);
	    }
	},
	/**
	 * 读取HTML5本地存储
	 * 
	 */
	getLocalSave: function (k) {
	    var localSet = window.localStorage;
	    if (localSet) {
	        return localSet.getItem(k) || '';
	    } else {
	        return this.getCookie(k) || '';
	    }
	},
	/**
     * 获取URL参数
     * 
     */
    getParameter: function (s) {
        var uri = location.search.replace('?', '');
        var reg = new RegExp("(^|&)" + s + "=([^&]*)(&|$)", "i");
        var r = uri.match(reg);

        if (r != null) {
            return unescape(r[2]);
        }

        return '';
    }
  }
}

export default myMixin;

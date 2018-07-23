import Vue from 'vue'
export default {
	'Tool-version': 1
}
Date.prototype.format = function(fmt) {
	if(isNaN(this.valueOf())){
		return "";
	}
	var o = { 
		"m+" : this.getMonth()+1,                 //月份 
		"d+" : this.getDate(),                    //日 
		"h+" : this.getHours(),                   //小时 
		"i+" : this.getMinutes(),                 //分 
		"s+" : this.getSeconds(),                 //秒 
		"q+" : Math.floor((this.getMonth()+3)/3), //季度 
		"S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
}

String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/gm,'');
}

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

alert = function(str, fn){;
  var dj=document.getElementById('__alert');
  if(dj){
    dj.remove();
  }
  var oDiv = document.createElement('div');
  oDiv.id="__alert";
  oDiv.className='alert-box';
  oDiv.innerHTML = '<div>'+(str||'无内容')+'</div>';
  document.body.appendChild(oDiv);
  setTimeout(function(){
    oDiv.remove();
    fn && fn();
  }, 1500);
}
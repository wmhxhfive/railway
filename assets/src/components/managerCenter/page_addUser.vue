<template>
    <div>
      <div class="condition">
        <label>用户名</label>
        <input type='text' :class="{needed:needed}" @change="checkUser" placeholder="用户名" v-model="username"/>
        <span class="err-msg" :style="errMsgRed?'color: red':''">{{errMsg}}</span>
      </div>
      <div class="condition">
        <label>手机号</label>
        <input type='text' placeholder="手机号"  v-model="moblie"/>
      </div>
      <div class="condition">
        <label>密码</label>
        <input type='password' :class="{needed:needed}" placeholder="密码"  v-model="password"/>
      </div>
      <div class="condition">
        <label>&nbsp;</label><button @click="register()" v-show="showBtn">注册</button>
      </div>
    </div>
</template>

<script>
import config from '@/config'
import md5 from 'js-md5';

export default {
  name: 'page_add_user',
  data () {
    return {
    	username:'',
      password:'',
      moblie: '',
      errMsg:'',
      errMsgRed: false,
      needed: false,
      showBtn: true,
    }
  },
  beforeCreate(){
    if(!this.getCookie('token')){
      window.location.href="#/login";
    }
  },
  methods:{
    checkUser(){
      this.$ajax({
        method: 'get',
        url: config.urlList.checkUser,
        params: { 
          username: this.username,
        }
      }).then((data) => {
        console.log(data);
        this.errMsg = data.data.message ||'';
        if(data.data.ret === '0'){
          this.errMsgRed = false;
        }else{
          this.errMsgRed = true;
        }
      })
    },
    register(){
      if(!this.username || !this.password){
        this.needed = true;
        return;
      }
      this.needed = false;
      this.showBtn = false;
      this.$ajax({
        method: 'post',
        url: config.urlList.register,
        headers:{"Content-Type": "application/json; charset=utf-8"},
        transformRequest: [function (data) {
          return JSON.stringify(data);
        }],
        data: { 
          name : this.username,
          password: md5(this.password),
          moblie: this.moblie
         }
      }).then((data) => {
        console.log(data);
        alert(data.data.message);
        if(data.data.ret === '0'){
          window.location.reload();
        }else{
          window.location.reload();
        }
      })
    }
  }
}
</script>
<style>
  .condition{
    padding: 10px 0;
  }
  label{
    width: 100px;
    display: inline-block;
    text-align: right;
    margin-right: 10px; 
  }
  .condition .needed {
    border-color: red;
  }
  .err-msg{
    color: green;
  }
</style>

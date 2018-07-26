<template>
  <div>
  <div class="htmleaf-container">
    <div class="wrapper">
      <div class="container">
        <h1>欢迎</h1>
          <div class="form">
            <input type="text" :class="{needed:needed}" v-model.trim='username' placeholder="用户名">
            <input type="password" :class="{needed:needed}" v-model.trim='password' placeholder="密码">
            <transition name="fade">
              <button @click="login" v-show="showBtn" id="login-button">登录</button>
            </transition>
          </div>
      </div>
      
      <ul class="bg-bubbles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
      </ul>
    </div>
  </div>
  <div style="text-align:center;margin:50px 0; font:normal 25px/36px '隶书';color:#000000">
<h2><img src="../../assets/logo.png"/>TDRS车载外部设备图像监测系统</h2>
</div>
</div>
</template>

<script>
import config from '@/net/config'
import md5 from 'js-md5';

export default {
  name: 'monitor',
  data () {
    return {
        username:'',
        password: '',
        moblie: '',
        needed: false,
        showBtn: true,
      } 
  },
  beforeCreate(){
    if(this.getCookie('token')){
      window.location.href="#/manager";
    }
  },
  methods: {
    login(){
      if(!this.username || !this.password){
        this.needed = true;
        return;
      }
      this.needed = false;
      this.showBtn = false;
      console.log(md5(this.password));
      this.$ajax({
        method: 'post',
        url: config.urlList.login,
        headers:{"Content-Type": "application/json; charset=utf-8"},
        transformRequest: [function (data) {
          return JSON.stringify(data);
        }],
        data: { 
          name : this.username,
          password: md5(this.password),
          moblie:''
         }
      }).then((data) => {
        console.log(data);
        if(data.data.ret === '0'){
          localStorage.setItem('username', this.username);
          this.setCookie('token', md5(this.username+config.INAPP));
          window.location.href="#/manager";
        }else{
          alert(data.data.message);
          this.needed = true;
          this.showBtn = true;
        }
      })
    }
  }
}
</script>

<style scoped="">
h1,h2,h3{
  font-family: Cambria,"LiSu";
}
h1 img,h2 img,h3 img{
  height: 36px;
  margin-right: 20px;
}
.wrapper {
  background: #3094d2;
  background: -webkit-linear-gradient(top left, #1a6c9f 0%, #61c3ff 100%);
  background: linear-gradient(to bottom right, #1a6c9f 0%, #61c3ff 100%);
  opacity: 0.8;
  position: absolute;
  top: 50%;
  left: 0;
  width: 100%;
  height: 400px;
  margin-top: -200px;
  overflow: hidden;

}

.wrapper.form-success .container h1 {
  -webkit-transform: translateY(85px);
      -ms-transform: translateY(85px);
          transform: translateY(85px);
}
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 80px 0;
  height: 400px;
  text-align: center;
}
.container h1 {
  font-size: 40px;
  -webkit-transition-duration: 1s;
          transition-duration: 1s;
  -webkit-transition-timing-function: ease-in-put;
          transition-timing-function: ease-in-put;
  font-weight: 200;
}
.form {
  padding: 20px 0;
  position: relative;
  z-index: 2;
}
.form input {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  border: 1px solid rgba(255, 255, 255, 0.4);
  background-color: rgba(255, 255, 255, 0.2);
  width: 250px;
  border-radius: 3px;
  padding: 10px 15px;
  margin: 0 auto 10px auto;
  display: block;
  text-align: center;
  font-size: 18px;
  color: white;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
  font-weight: 300;
}
.form input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
.form input:focus {
  background-color: white;
  width: 300px;
  color: #3094d2;
}
.form input.needed {
  border-color: red;
}
.form button {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  background-color: white;
  border: 0;
  padding: 10px 15px;
  border-radius: 3px;
  width: 250px;
  cursor: pointer;
  font-size: 18px;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
}
.form button:hover {
  background-color: #f5f7f9;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
.bg-bubbles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}
.bg-bubbles li {
  position: absolute;
  list-style: none;
  display: block;
  width: 40px;
  height: 40px;
  background-color: rgba(255, 255, 255, 0.15);
  bottom: -160px;
  -webkit-animation: square 8s infinite;
  animation: square 8s infinite;
  -webkit-transition-timing-function: linear;
  transition-timing-function: linear;
}
.bg-bubbles li:nth-child(1) {
  left: 10%;
}
.bg-bubbles li:nth-child(2) {
  left: 20%;
  width: 80px;
  height: 80px;
  -webkit-animation-delay: 2s;
          animation-delay: 2s;
  -webkit-animation-duration: 17s;
          animation-duration: 17s;
}
.bg-bubbles li:nth-child(3) {
  left: 25%;
  -webkit-animation-delay: 4s;
          animation-delay: 4s;
}
.bg-bubbles li:nth-child(4) {
  left: 40%;
  width: 60px;
  height: 60px;
  -webkit-animation-duration: 22s;
          animation-duration: 22s;
  background-color: rgba(255, 255, 255, 0.25);
}
.bg-bubbles li:nth-child(5) {
  left: 70%;
}
.bg-bubbles li:nth-child(6) {
  left: 80%;
  width: 120px;
  height: 120px;
  -webkit-animation-delay: 3s;
          animation-delay: 3s;
  background-color: rgba(255, 255, 255, 0.2);
}
.bg-bubbles li:nth-child(7) {
  left: 32%;
  width: 160px;
  height: 160px;
  -webkit-animation-delay: 7s;
          animation-delay: 7s;
}
.bg-bubbles li:nth-child(8) {
  left: 55%;
  width: 20px;
  height: 20px;
  -webkit-animation-delay: 15s;
          animation-delay: 15s;
  -webkit-animation-duration: 40s;
          animation-duration: 40s;
}
.bg-bubbles li:nth-child(9) {
  left: 25%;
  width: 10px;
  height: 10px;
  -webkit-animation-delay: 2s;
          animation-delay: 2s;
  -webkit-animation-duration: 40s;
          animation-duration: 40s;
  background-color: rgba(255, 255, 255, 0.3);
}
.bg-bubbles li:nth-child(10) {
  left: 90%;
  width: 160px;
  height: 160px;
  -webkit-animation-delay: 11s;
          animation-delay: 11s;
}
@-webkit-keyframes square {
  0% {
    -webkit-transform: translateY(0);
            transform: translateY(0);
  }
  100% {
    -webkit-transform: translateY(-700px) rotate(600deg);
            transform: translateY(-700px) rotate(600deg);
  }
}
@keyframes square {
  0% {
    -webkit-transform: translateY(0);
            transform: translateY(0);
  }
  100% {
    -webkit-transform: translateY(-700px) rotate(600deg);
            transform: translateY(-700px) rotate(600deg);
  }
}
</style>

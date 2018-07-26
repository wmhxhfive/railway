<template>
	<div class="header">
    <a class="menu-click" @click="toggleHeaderMenu">菜单</a>
    <img src="../../assets/header-img.png" class="header-kj"></img>
    <div class="header-user" @click="toggleHeaderUser">
      <!-- {{username}}<span class="down-arrow" :class="open?'up':''"></span> -->
      <transition name="fade">
        <div class="header-menu" v-show="open">
          <!-- <span @click.stop="">
            更改密码
          </span> -->
          <span @click.stop="exit">
            退出
          </span>
        </div>
      </transition>
    </div>
		<div class="header-center">
      <img src="../../assets/logo.png" /><span class="tdrs">TDRS</span>车载外部设备图像监测系统
    </div>
	</div>
</template>

<script>
export default {
  name: 'manager',
  props:['username'],
  data () {
    return {
      open: false
    }
  },
  components:{
  },
  created() {
  },
  methods:{
    toggleHeaderMenu(){
      this.$emit('toggleMenuOpen', {})
    },
    toggleHeaderUser(){
      this.open = !this.open;
    },
    exit(){
      this.clearCookie('token');
      window.location.href="#/login";
    }
  },
  mounted(){
  }
}
</script>

<style scoped="">
.header{
  line-height: 65px;
	height: 65px;
	width: 100%;
  color: #fff;
	background: url('../../assets/header-bg.png') repeat;
}
.menu-click{
  position: absolute;
  left: 20px;
  cursor: pointer;
}
.header-kj{
  height: 82px;
  float: left;
  display: inline-block;
}
.header-center{
  text-align: center;
  font-weight: bold;
  font-size: 35px;
  font-family: Cambria,"LiSu";
  margin: 0 auto;
  height: 65px;
  width: 600px;
  background: url('../../assets/header-center-bg.png') repeat;
  position: relative;
}
.header-center:before{
  content: "";
  display: inline-block;
  height: 65px;
  width: 70px;
  position: absolute;
  left: -70px;
  background: url('../../assets/header-center-left.png') no-repeat;
}
.header-center:after{
  content: "";
  display: inline-block;
  height: 65px;
  width: 70px;
  position: absolute;
  right: -70px;
  background: url('../../assets/header-center-right.png') no-repeat;
}
.header-center img{
  height: 30px;
  margin-right: 10px;
}
.header-center .tdrs{
  font-size: 30px;
}
.header-user{
  width: 200px;
  float: right;
  text-align: right;
  margin-right: 30px; 
  font-size: 18px;
  cursor: pointer;
  position: relative;
}
.down-arrow{
  display: inline-block;
  margin-left: 10px;
  width: 10px;  
  height: 10px;
  vertical-align: middle;
  border-left: 3px solid #fff;  
  border-bottom: 3px solid #fff; 
  -webkit-transform: translate(0,-50%) rotate(-45deg);  
  transform: translate(0,-50%) rotate(-45deg);
  transition:0.3s;
  -webkit-transition:0.3s; /* Safari */
}
.down-arrow.up{  
  -webkit-transform: rotate(135deg);  
  transform: rotate(135deg);
}
.header-menu{
  background: #278acb;
  position: absolute;
  right: -20px;
  border: 1px solid #fff;
  margin-top: -10px;
  width: 120px;
  text-align: center;
  padding: 15px 0 5px;
  z-index: 11;
}
.header-menu:before{
  content: "";
  display: inline-block;
  height: 20px;
  width: 20px;
  top:-11px;
  right: 40%;
  position: absolute;
  border-right: 1px solid #fff;
  border-top: 1px solid #fff;
  -webkit-transform: rotate(-45deg);  
  background-color: #278acb;
  transform: rotate(-45deg);
}
.header-menu span{
  display: block;
  line-height: 30px;
  text-align: center;
}
.header-menu span:hover{
  background-color: #164d72;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>

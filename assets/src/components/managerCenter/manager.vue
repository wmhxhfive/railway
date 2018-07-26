<template>
  <div>
    <header-view :username="username" @toggleMenuOpen="toggleMenuOpen"></header-view>
    <div class="menu" :class="{'menu-close': menu_close}">
      <template v-for='item,index in btnList'>
        <span :class="{ current: index===mark }" @click="open_page(index, item.page)">{{item.name}}</span>
      </template>
    </div>
    <div class="my-iframe" :class="{'menu-close': menu_close}">
      <iframe name="myframe" v-bind:src="iframeURL"></iframe>
    </div>
  </div>    
</template>

<script>
import Header from '@/components/common/header'
import checkLoginStatus from '@/mixin/checkLoginStatus'
import localStore from '@/mixin/localStore'

export default {
  name: 'manager',
  mixins: [localStore],
  data () {
    return {
      username: '',
      mark: 0,
      menu_close: true,
      iframeURL: '#/trainload',
      btnList: [{
        name: '首页',
        page: 'trainload'
      },{
        name: '机车列表',
        page: 'page_Train'
      },{
        name: '添加新用户',
        page: 'page_addUser'
      }]
    }
  },
  components:{
    'header-view': Header
  },
  created() {
    this.username = this.getLocalSave('username') || 'admin';
  },
  methods:{
    open_page(index, p_url){
      if(this.mark == index){
        myframe.location.reload();
      }else{
        this.mark = index;
        this.iframeURL = '#/'+p_url;
      }
    },
    toggleMenuOpen(){
      console.log(this.menu_close?"开":"关");
      this.menu_close = !this.menu_close;
    }
  },
  mounted(){
  }
}
</script>

<style>
ul,li {
  list-style: none;
  padding: 0;
  margin: 0;
}
.menu {
  width: 200px;
  background: #2f2d2d;
  position: absolute;
  top: 66px;
  bottom: 0;
  color: #d1cfcf;
  left: 0;
  transition-duration: 0.3s
}
.menu.menu-close{
  left: -200px;
}
.menu>span {
  display: block;
  padding: 15px 20px;
  font-size: 18px;
  font-weight: lighter;
  cursor: pointer;
  border-bottom: 0.5px solid #838282;
  transition:0.3s;
  -webkit-transition:0.3s; /* Safari */
}
.menu>span:hover {
  background-color: #64b3e4;
  color: #fff;
}
.menu>span.current {
  background-color: #3094d2;
  color: #fff;
}
.my-iframe {
  display: block;
  position: absolute;
  top: 66px;
  bottom: 0;
  right: 0;
  left: 200px;
  overflow: hidden;
  transition-duration: 0.3s
}
.my-iframe.menu-close{
  left: 0px;
}
.my-iframe iframe{
  border: none;
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}
</style>

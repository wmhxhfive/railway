<template>
  <div class="com-alert">
    <transition-group name="list" tag="ul">
      <li v-for="item in msg" v-bind:key="item.time" :class="{cred: item.color}">
        <div>
          {{item.msg}}
        </div>
      </li>
    </transition-group>
  </div>
</template>
<script>
export default {
 name: 'alert',
 data() {
  return {
    msg: []
  };
 },
 mounted () {
  window.alert = this.alert
  window.toast = this.toast
 },
 methods: {
  alert (msg, fn) {
    this.toast (msg, fn, true);
  },
  toast (msg, fn, color) {
    this.msg.push({
      msg: msg,
      color: color || false,
      time: Math.floor(Math.random() * 100000)
    });
    setTimeout(()=>{
      this.msg.shift();
      fn && fn();
    }, 5000)
  }
 },
 watch:{
  msg () {
    
  }
 }
};
</script>

<style scoped>
.com-alert {
 position: fixed;
 top: 50px;
 right: 20px;
 width: 350px;
 z-index: 9990;
 transition: all .3s ease-in-out;
}
.com-alert li {
  width: 100%;
  text-align: center;
  padding: 30px 20px;
  background: #d2e2ff;
  border: 1px solid #69d8ff;
  color: #238c22;
  margin-bottom: 10px;
  box-sizing: border-box;
  list-style: none;
}
.com-alert li.cred {
  color: #c23a1a;
}
.list-enter-active, .list-leave-active {
  transition: all 0.5s;
}
.list-enter {
  opacity: 0;
  transform: translateY(30px);
}
.list-leave-to {
  opacity: 0;
  transform: translateY(-30px);
}
</style>
<template>
  <div>
    <a class="box1" href="#/page_Train">
        机车列表
    </a>
    <a class="box2" href="#/page_addUser">
      添加新用户
    </a>
	</div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
    }
  },
  beforeCreate(){
    if(!this.getCookie('token')){
      window.location.href="#/login";
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.box1,.box2{
  font-size: 25px;
  margin: 30px 0 0 30px;
  display: inline-block;
  width: 200px;
  height: 200px;
  line-height: 200px;
  text-align: center;
  color: #fff;
  cursor: pointer;
  opacity: 0.8;
  text-decoration: none;
}
.box1:hover,.box2:hover{
  opacity: 1;
}
.box1{
  background-color: #52b2c0;
}

.box2{
  background-color: #ffac00;
}
</style>

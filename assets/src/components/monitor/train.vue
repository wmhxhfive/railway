<template>
    <div calss="train-list">
      <div v-for="item,index in trainInfos" v-if='index<6' class="train" :class="{ current: index===mark }" @click='select(index)'>
        <div class="time-info">
          <span style="font-size: 24px;">
            {{item.railNo}}
          </span>
        </div>
        <div class="time-info" style="font-size: 20px;font-weight: bolder;">
          {{new Date(item.checkDate).format('hh:ii:ss')}}
          <span class="err-status-ok" :class="{'err-status-warn': item.isNormal == '1'}"></span>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'train',
  props:['trainInfos', 'trainDetailInfos'],
  data () {
    return {
      msg: '',
      mark: 0
    }
  },
  created(){

  },
  methods:{
    select(index) {
      this.mark = index;
      this.$emit('changeTrain', index);
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped="">
.train-list{
  height: 93vh;
  overflow: hidden; 
}
.train{
  background-color: #494747;
  margin: 0;
  overflow: hidden;
  height: 15.5vh;
  opacity: 0.5;
  position: relative;
  color: #fff;
  cursor: pointer;
  padding: 1vh 0 0 2vh;
  -moz-box-sizing: border-box;  /*Firefox3.5+*/
  -webkit-box-sizing: border-box; /*Safari3.2+*/
  -o-box-sizing: border-box; /*Opera9.6*/
  -ms-box-sizing: border-box; /*IE8*/
  box-sizing: border-box;
  transition: 0.5s;
}
.train.current{
  opacity: 1;
}
.train::before{
  height: 1px;
  width: 100%;
  content: "";
  left: 0;
  border-bottom: 1px solid #9f9e9e;
  position: absolute;
  bottom: 0;
}
.train.current::after{
  opacity: 1;
  content: "";
  width: 18px;
  height: 40px;
  background: url('../../assets/train-arrow.png') no-repeat;
  background-size: 18px;
  right: 0;
  top: calc(7vh - 18px);
  position: absolute;
}
.time-info{
  margin-top:2vh; 
}
.time-info .err-status-ok{
  content: "";
  display: inline-block;
  width: 25px;
  height:25px;
  background: url('../../assets/ok-tg.png') 0/25px no-repeat;
  margin-left: 10px;
  vertical-align: middle;
}
.time-info .err-status-warn{
  background: url("../../assets/warn-tg.png") 0/25px no-repeat;
}
</style>

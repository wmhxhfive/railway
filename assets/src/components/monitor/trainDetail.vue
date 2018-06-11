<template>
  <section>
    <div class="detail-info" >
        <span>机车ID：<span class="time">{{trainInfo.id}}</span></span>
        <span>机车型号：<span class="time">{{trainInfo.railNo}}</span></span>
        <!-- <span>是否异常： <b>{{trainInfo.isNormal?"是":"否"}}</b></span> -->
        <span>进站时间： <span class="time">{{new Date(trainInfo.checkDate).format('yyyy年mm月dd日 hh:ii:ss')}}</span></span>
    </div>
    <ul class="img-list">
      <li v-for="item in trainDetailInfos" v-if="item.trainInfoId == trainInfo.id" :class="{'is-error':item.analyResult}" v-on:click="showImage(item.url, item.partNo)">
        <img :src="item.url"/>
        <div class="part-no">{{item.partNo}}</div>
        <span class="analysis-res" v-if="item.analyResult">
          <img src="../../assets/warn-tg.png"/>
        </span>
        <div class="err-cont" v-if="item.analyResult">{{item.errorReason}}</div>
      </li>
    </ul>
    <div class="err-corner" v-show="showfoot">
      <div>合肥站</div>
      <img src="../../assets/logo.png"/>
      <span>机车设备智能检测</span>
    </div>
    <mydialog-bar v-model="sendVal" type="cancel" :title="Title" :content="Content" @:cancel="clickCancel()"></mydialog-bar>
  </section>    
</template>

<script>
import dialogBar from '@/components/common/dialogBar'

export default {
  name: 'monitor',
  props:['trainInfo', 'trainDetailInfos', 'showfoot'],
  data(){
    return {
      Title: '',
      Content: '',
      sendVal: false,//控制显示
    }
  },
  components:{
    'mydialog-bar': dialogBar,
  },
  methods:{
    showImage(url, title){
      this.Content = '<img src="'+url+'" style="height:100%;"/>';
      this.Title = title;
      this.sendVal = true;
    }
  }
}
</script>

<style>
.detail-info {
  height: 9vh; 
  line-height: 9vh;
  color: #0d90e1;
  padding: 1vh 0 0 30px;
  font-size: 20px;
}
.detail-info .time{
  font-size: 30px;
  padding-right:30px;
  color: #1e1b1b;
  font-weight: bold;
}
.img-list {
  margin: 0 3vh;
}
.img-list .is-error{
  border: 2px solid red;
}
.img-list li{
  width: 33%;
  height: 28.5vh;
  margin: 0.5vh 0.1%;
  display: inline-block;
  position: relative;
  border: 1px solid #3094d2;
  -moz-box-sizing: border-box;  /*Firefox3.5+*/
  -webkit-box-sizing: border-box; /*Safari3.2+*/
  -o-box-sizing: border-box; /*Opera9.6*/
  -ms-box-sizing: border-box; /*IE8*/
  box-sizing: border-box;
  cursor: pointer;
  text-align: center;
}
.img-list img{
  max-height: 100%;
  max-width: 100%;
}
.img-list .analysis-res{
  height: 30px;
  width: 30px;
  bottom: 10px;
  right: 10px;
  position: absolute;
  z-index: 2;
}
.img-list .part-no {
    position: absolute;
    top: 0;
    left: 0;
    background-color: #565656;
    color: #fff;
    padding: 5px 0;
    width: 45px;
}
.img-list .err-cont{
  height: 1.5em;
  line-height: 1.5em;
  text-align: center;
  bottom: 0;
  position: absolute;
  width: 100%;
  background-color: rgb(255,255,255, 0.8);
  color: #ef7f12;
}
.err-corner{
  position: absolute;
  right: 2.5vh;
  bottom: 3vh;
  text-align: right;
  font-size: 2em;
  background-color: #d7d6d6;
  color: #278acb;
}
.err-corner div{
  font-size: 0.8em;
  opacity: 0.3;
}
.err-corner img{
  vertical-align: middle;
  height: 1em;
  margin-left: 20px;
  opacity: 0.2;
}
.err-corner span{
  font-family: "隶书";
  opacity: 0.2;
  color: #1e1b1b;
}
</style>

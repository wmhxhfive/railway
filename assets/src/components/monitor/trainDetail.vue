<template>
  <section>
    <div class="detail-info" v-if="trainInfo">
      {{typeof trainInfo.id}}
        <span>机车ID：<span class="time">{{trainInfo.id}}</span></span>
        <span>机车型号：<span class="time">{{trainInfo.railNo}}</span></span>
        <!-- <span>是否异常： <b>{{trainInfo.isNormal?"是":"否"}}</b></span> -->
        <span>进站时间： <span class="time">{{new Date(trainInfo.checkDate).format('yyyy年mm月dd日 hh:ii:ss')}}</span></span>
    </div>
    <ul class="img-list">
      <li v-for="item in trainDetailInfos" v-if="item.trainInfoId == trainInfo.id" :class="{'is-error':item.analyResult}" @click="showImage(item)">
        <img :src="item.url"/>
        <div class="part-no">{{item.partNo}}</div>
        <span class="analysis-res" v-if="item.analyResult">
          <img src="../../assets/warn-tg.png"/>
        </span>
        <div class="err-cont" v-if="item.analyResult" :title="item.errorReason">
          <span>{{item.errorReason}}</span>
        </div>
      </li>
    </ul>
    <div class="err-corner" v-show="showfoot">
      <div>合肥站</div>
      <img src="../../assets/logo.png"/>
      <span>机车设备智能检测</span>
    </div>
    <mydialog-bar v-model="sendVal" type="defalut" :title="Title" :cancel="clickCancel">
      <img :src="imgUrl" style="max-width: 100%;height: 100%;display: inline-block;"/>
      <!-- 故障编辑 -->
      <div class="edit-box" v-if="editable">
        故障：
        <template v-if="isEditing">
          <input class="edit-input" type="text" v-model="errorReason" />
          <a @click="update">保存</a>
          <a @click="cancelEdit">取消</a>
        </template>
        <template v-else>
          <span class="edit-text" :title="errorReason">{{errorReason||'无'}}</span>
          <a v-show="editable" @click="edit">编辑</a><br>
        </template>
      </div>
    </mydialog-bar>
  </section>    
</template>

<script>
import dialogBar from '@/components/common/dialogBar'
import config from '@/config'

export default {
  name: 'monitor',
  props:['trainInfo', 'trainDetailInfos', 'showfoot','editable'],
  data(){
    return {
      Title: '',
      sendVal: false,//控制显示
      imgUrl:'',//dialog image url
      eidtId: null,
      isEditing: false,
      errorReason:'',//错误信息
      originError: '',
      editingItem: null,
    }
  },
  components:{
    'mydialog-bar': dialogBar,
  },
  methods:{
    showImage(item){
      console.log(item);
      this.editingItem = item;
      this.imgUrl = item.url;
      this.Title = item.partNo;
      this.eidtId = item.id;
      this.errorReason = item.errorReason;
      this.sendVal = true;
    },
    clickCancel(){
    },
    edit(){
      this.isEditing=!this.isEditing;
      this.originError = this.errorReason;
    },
    cancelEdit(){
      this.isEditing=!this.isEditing;
      this.errorReason = this.originError;
    },
    update(){
      var self = this;
      self.isEditing=!self.isEditing;
      self.$ajax({
        method: 'post',
        url: config.urlList.update,
        headers:{"Content-Type": "application/json; charset=utf-8"},
        transformRequest: [function (data) {
          return JSON.stringify(data);
        }],
        data: {
            errorReason: self.errorReason,
            id: self.eidtId
          }
      }).then((data) => {
        var ret = data.data;
        console.log(data);
        if(ret.ret === '0'){
          self.editingItem.errorReason = self.errorReason;
          self.editingItem.analyResult = 1;
          alert('保存成功');
        }else{
          self.errorReason = self.originError;
          alert('保存失败');
        }
      })
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
  background: #ededed url('../../assets/default_img.png') no-repeat center/auto 60%;
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
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
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
.edit-box{
  width: 60%;
  margin: 0 auto;
  text-align: left;
  line-height: 45px;
}
.edit-box a{
  color: #58bfff;
  padding: 0 10px;
  cursor: pointer;
}
.edit-box a:hover{
  text-decoration:underline;
}
.edit-text{
  display: inline-block;
  font-size: 18px;
  width: 350px;
  text-align: left;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  vertical-align: middle;
}
.edit-box input.edit-input{
  width: 350px;
  font-size: 18px;
  border: 0.5px solid #ddd;
}
</style>

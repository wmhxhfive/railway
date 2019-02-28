<template>
  <section>
    <div class="detail-info" v-if="trainInfo">
        <span>车型车号：<span class="time">{{trainInfo.railNo}}</span></span>
        <!-- <span>是否异常： <b>{{trainInfo.isNormal?"是":"否"}}</b></span> -->
        <span>入库时间： <span class="time">{{new Date(trainInfo.checkDate).format('yyyy年mm月dd日 hh:ii:ss')}}</span></span>
    </div>
    <ul class="img-list" :class="{more: trainDetailInfo.list && trainDetailInfo.list.length>9}">
      <li v-for="item in trainDetailInfo.list" :class="{'is-error':item.analyResult}" @click="showImage(item)" :id="'li_'+item.id">
        <img :src="item.url"/>
        <span class="deleteBtn" v-show="admin" @click.stop="deleteItem(item.id, $event)">删除</span>
        <div class="part-no">{{railNoList[item.partNumber]}}</div>
        <a class="images-count" v-if="trainDetailInfo[item.partNumber] && trainDetailInfo[item.partNumber].length>1" 
          @click.stop="showMutilImages(trainDetailInfo[item.partNumber])"
          href="#/multi" target="_blank">
          <img src="../../assets/images/multi-pic.png"/>
        </a>
        <span class="analysis-res" v-if="item.analyResult">
          <img src="../../assets/images/warn-tg.png"/>
        </span>
        <div class="err-cont" v-if="item.analyResult" :title="item.errorReason">
          <span>{{item.errorReason}}</span>
        </div>
      </li>
    </ul>
    <div class="err-corner" v-show="showfoot">
      <div>合肥站</div>
      <img src="../../assets/images/logo.png"/>
      <span>TDRS车载外部设备图像监测系统</span>
    </div>
    <mydialog-bar v-model="sendVal" type="defalut" :title="Title" :cancel="clickCancel">
      <div class="big-img-box" ref="imgHtml">
      </div>
      <!-- <a class="rorate-btn" @click="rotateImg" title="旋转"></a> -->
      <!-- 故障编辑 -->
      <div class="edit-box">
        预警信息：
        <template v-if="isEditing && editable">
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
import webUrls from '@/net/webUrls'
import localStore from '@/mixin/localStore'

export default {
  name: 'monitor',
  props:['trainInfo', 'trainDetailInfo', 'showfoot','editable'],
  mixins: [localStore],
  data(){
    return {
      Title: '',
      sendVal: false,//控制显示
      imgHtml: '',
      eidtId: null,
      isEditing: false,
      errorReason:'',//错误信息
      originError: '',// 保存原错误信息
      editingItem: null,
      rotate: 0,
      admin: false, // 控制“删除”按钮
      railNoList:{
        '1': '左信号感应器',// Ⅰ
        '2': '右信号感应器',
        '3': 'Ⅱ左信号感应器',
        '4': 'Ⅱ右信号感应器',
        '5': '左侧速度传感器',
        '6': '右侧速度传感器',
        '7': '机车标签'
      }
    }
  },
  created(){
    // console.log('==', this.getParameter('admin'));
    this.admin = this.getParameter('admin')
  },
  components:{
    'mydialog-bar': dialogBar,
  },
  mounted(){
  },
  methods:{
    showImage(item){
      this.editingItem = item;
      this.Title = this.railNoList[item.partNumber];
      this.eidtId = item.id;
      this.errorReason = item.errorReason;
      this.sendVal = true;
      this.rotate = 0;
      this.isEditing = false;
      this.$refs.imgHtml.innerHTML = `<img id="bigImg" src="${item.url}" />`

      this.$nextTick(function(){
        $('#bigImg').smartZoom({});
      })
    },
    showMutilImages(multiImgs){
      this.setLocalSave('MULTIIMAGELIST', JSON.stringify(multiImgs))
    },
    clickCancel(){
    },
    rotateImg(){
       // this.rotate += 90;
       // $('#bigImg').rotate({ animateTo:this.rotate})
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
      console.log("self.errorReason", self.errorReason);
      if(!self.errorReason || !self.errorReason.trim()){
        alert("预警内容不能为空");
        return;
      }
      self.isEditing=!self.isEditing;
      self.$ajax({
        method: 'post',
        url: webUrls.urlList.update,
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
    },
    deleteItem(id, event){
      console.log('==》 ', event);
      if(!confirm('确定删除吗？')){
        return;
      }
      this.$ajax({
          method: 'post',
          url: webUrls.urlList.delete,
          headers:{"Content-Type": "application/json; charset=utf-8"},
          params: { 
             id: id
          }
        }).then((data) => {
          var ret = data.data;
          if(ret.ret == '0'){
            alert('删除成功');
            if (event) {
                $(event.target).closest('li').remove()
            }
          }else{
            alert('删除失败')
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
  border: 0.5px solid #3094d2;
  -moz-box-sizing: border-box;  /*Firefox3.5+*/
  -webkit-box-sizing: border-box; /*Safari3.2+*/
  -o-box-sizing: border-box; /*Opera9.6*/
  -ms-box-sizing: border-box; /*IE8*/
  box-sizing: border-box;
  cursor: pointer;
  text-align: center;
  background: #ededed url('../../assets/images/default_img.png') no-repeat center/auto 60%;
}
.img-list.more li{
  width: 24.8%;
}
.img-list img{
  max-height: 100%;
  max-width: 100%;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.img-list .deleteBtn{
  position: absolute;
  right: 5px;
}
.img-list .deleteBtn:hover{
  text-decoration: underline;
  color: #1046c2;
}
.img-list .images-count{
  height: 30px;
  width: 30px;
  bottom: 0;
  left: 5px;
  position: absolute;
  z-index: 2;
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
  padding: 5px 10px;
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
.big-img-box{
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.rorate-btn {
  display: inline-block;
  position: absolute;
  top: 20px;
  right: 60px;
  background: url('../../assets/images/rorate-btn-img.png') no-repeat;
  background-size: 25px;
  height: 25px;
  width: 25px;
  cursor: pointer;
  opacity: 0.8;
}
.rorate-btn:hover{
  opacity: 1;
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

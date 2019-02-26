<template>
  <div class="monitor">
    <div class="train-bar">
      <train-view :trainInfos="trainInfos" :trainDetailInfos="trainDetailInfos" v-on:changeTrain="changeTrain"></train-view>
      <a class="more-train" href="#/manager" target="_blank">
        <img src="../../assets/images/train-header.png"/>
        进站记录
      </a>
    </div>
    <div class="detail-box">
      <train-detail :trainInfo="trainInfos[trainIndex]" showfoot="true" :trainDetailInfo="trainDetailInfo" :editable="false"></train-detail>
    </div>
  </div>    
</template>

<script>
import train from '@/components/monitor/train'
import trainInfo from '@/components/monitor/trainDetail'
import webUrls from '@/net/webUrls'
import localStore from '@/mixin/localStore'
var QS = require('qs');

export default {
  name: 'monitor',
  mixins: [localStore],
  data () {
    return {
      trainIndex: 0,
      trainInfos: [], //*/,
      trainDetailInfos: [],//*/,
      trainDetailInfo: {list: [], Index: []}
    }
  },
  components:{
    'train-view': train,
    'train-detail': trainInfo
  },
  created(){
    this.init();
    this.timer && setInterval(()=>this.init(), 30000)
  },
  methods:{
    init() {
      var self = this;
      this.$ajax({
        method: 'post',
        url: webUrls.urlList.trainInfo,
        transformRequest: [function (data) {
          return QS.stringify(data);
        }],
        data: { 
          railStation : '合肥'
        }
      }).then((data) => {
        console.log('数据列表 - ', data);
        if(data.status == 200){
          this.trainInfos=data.data.trainInfos || [];
          this.trainDetailInfos = data.data.trainDetailInfos || [];
          self.trainInfoChange()
        }
      })
    },
    trainInfoChange(){
      let lists = this.trainDetailInfos;
      let trainDetailInfo = {list: [], Index: []}
      for (var key in lists) {
        if(this.trainInfos[this.trainIndex]['id'] == lists[key]['trainInfoId']){
          let parts = this.getTitle(lists[key]['partNo'])
          if(trainDetailInfo.Index.indexOf(parts[0])<0){
            trainDetailInfo.Index.push(parts[0])
            trainDetailInfo.list.push({ ...lists[key], partNumber: parts[0] })
          }
          if(!trainDetailInfo[parts[0]])
            trainDetailInfo[parts[0]] = []
          trainDetailInfo[parts[0]].push({ ...lists[key], partNumber: parts[0], partIndex: parts[1] })
        }
      }
      console.log('List====>', trainDetailInfo)
      this.trainDetailInfo = trainDetailInfo
    },
    getTitle(no){
      var res = ['', ''];
      res = no.split(/\-|\_/)
      return res;
    },
    changeTrain(index){
      this.trainIndex = index;
    }
  },
  watch: {
    trainIndex: function(){
      this.trainInfoChange()
    }
  },
  beforeDestroy(){
    this.timer && clearInterval(this.timer)
  }
}
</script>

<style>
ul,li{
  list-style: none;
  padding: 0;
  margin: 0;
}
.train-bar{
  width: 12%;
  top: 0;
  bottom: 0;
  left: 0;
  position: absolute;
  display: inline-block;
  overflow: hidden;
  background-color: #908f8f;
}
.more-train{
  height: 7vh;
  line-height:7vh;
  color: #b3b3b3;
  text-align: center;
  background-color: #666666;
  position: absolute;
  bottom: 0;
  width: 100%;
  cursor: pointer;
  text-decoration: none;
}
.more-train img{
  height: 25px;
  vertical-align: middle;
  margin-right: 5px;
}
.detail-box{  
  width: 88%;
  display: inline-block;
  left: 12%;
  right: 0;
  bottom: 0;
  top: 0;
  position: absolute;
  background-color: #d7d6d6;
  overflow:hidden;
}
</style>

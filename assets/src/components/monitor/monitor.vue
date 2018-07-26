<template>
  <div class="monitor">
    <div class="train-bar">
      <train-view :trainInfos="trainInfos" :trainDetailInfos="trainDetailInfos" v-on:changeTrain="changeTrain"></train-view>
      <div class="more-train">
        <img src="../../assets/train-header.png"/>
        进站记录
      </div>
    </div>
    <div class="detail-box">
      <train-detail :trainInfo="trainInfos[trainIndex]" showfoot="true" :trainDetailInfo="trainDetailInfo" :editable="false"></train-detail>
    </div>
  </div>    
</template>

<script>
import train from '@/components/monitor/train'
import trainInfo from '@/components/monitor/trainDetail'
import config from '@/net/config'
var QS = require('qs');

export default {
  name: 'monitor',
  data () {
    return {
      trainIndex: 0,
      trainInfos: [], //*/[{"id":14,"railNo":"DF070080","isNormal":"1","checkDate":"2018-04-28 23:00:01","railStation":"hf","errorReason":"机车标签外观有无缺损、裂纹、变形"},{"id":34,"railNo":"DF07 0081","isNormal":"0","checkDate":"2018-04-28 23:00:01","railStation":"hf","errorReason":"焊装支架有无开焊、变形"},{"id":17,"railNo":"DF07 0101","isNormal":null,"checkDate":"2018-04-28 23:00:00","railStation":"hf","errorReason":"6个安装螺丝有无缺失、缺损、松动"},{"id":16,"railNo":"DF08 0080","isNormal":null,"checkDate":"2018-04-28 20:00:01","railStation":"hf","errorReason":"开口销有无缺失"},{"id":15,"railNo":"DF07 0077","isNormal":null,"checkDate":"2018-04-28 20:00:00","railStation":"hf","errorReason":"连接电缆是否绑扎牢固、线缆固定卡有无脱落、电缆外观有无变形、缺损"},{"id":16,"railNo":"DF08 0080","isNormal":null,"checkDate":"2018-04-28 20:00:01","railStation":"hf","errorReason":"开口销有无缺失"},{"id":15,"railNo":"DF07 0077","isNormal":null,"checkDate":"2018-04-28 20:00:00","railStation":"hf","errorReason":"连接电缆是否绑扎牢固、线缆固定卡有无脱落、电缆外观有无变形、缺损"}],
      trainDetailInfos: [],//*/[{"trainInfoId":14,"partNo":"1","url":"http://106.12.21.105/page/tmp_train.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"1","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"1","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"2","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"3","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":1,"checkDate":"2018-04-28 23:00:01","errorReason":"这里是故障原因"},{"trainInfoId":14,"partNo":"1","url":"http://106.12.21.105/page/tmp_train.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"4","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"1","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"5-1","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":14,"partNo":"5-2","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"},{"trainInfoId":34,"partNo":"5","url":"http://106.12.21.105/page/tmp_zh.jpg","analyResult":0,"checkDate":"2018-04-28 23:00:01"}],
      trainDetailInfo: []
    }
  },
  components:{
    'train-view': train,
    'train-detail': trainInfo
  },
  created(){
    this.init();
    setInterval(()=>this.init(), 30000)
  },
  methods:{
    init() {
      var self = this;
      this.$ajax({
        method: 'post',
        url: config.urlList.trainInfo,
        transformRequest: [function (data) {
          return QS.stringify(data);
        }],
        data: { 
          railStation : '合肥'
         }
      }).then((data) => {
        console.log(data);
        if(data.status == 200){
          this.trainInfos=data.data.trainInfos || [];
          this.trainDetailInfos = data.data.trainDetailInfos || [];
          self.trainInfoChange()
        }
      })
    },
    trainInfoChange(){
      var list = this.trainDetailInfos;
      this.trainDetailInfo = [];
      for (var key in list) {
        if(this.trainInfos[this.trainIndex]['id'] == list[key]['trainInfoId']){
          this.trainDetailInfo.push(list[key])
        }
      }
      console.log('===', this.trainDetailInfo);
    },
    changeTrain(index){
      this.trainIndex = index;
    }
  },
  watch: {
    trainIndex: function(){
      this.trainInfoChange()
    }
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

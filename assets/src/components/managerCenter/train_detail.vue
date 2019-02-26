<template>
	<div class="detail-dialog">
		<train-detail :trainInfo="trainInfo" :trainDetailInfo="trainDetailInfo" :editable="false"></train-detail>
	</div>
</template>

<script>
import webUrls from '@/net/webUrls'
import trainInfoView from '@/components/monitor/trainDetail'
import checkLoginStatus from '@/mixin/checkLoginStatus'
import localStore from '@/mixin/localStore'
var QS = require('qs');

export default {
  name: 'page_Train',
  mixins: [ localStore ],
  data () {
    return {
    	trainInfo: {},
    	trainDetailInfo:[]
    }
  },
  components:{
    'train-detail': trainInfoView
  },
  mounted(){
		this.trainInfo = JSON.parse(this.getLocalSave('BLANKTRAININFO'));
  	this.$ajax({
      method: 'post',
      url: webUrls.urlList.trainDetail,
      transformRequest: [function (data) {
        return QS.stringify(data);
      }],
      data: { 
        id: this.trainInfo.id
      }
    }).then((data) => {
      var ret = data.data;
      if(ret.ret === '0'){
        this.trainInfoOp(ret.trainDetailInfos);
      }else{
        this.trainDetailInfo = [];
      }
    })
  },
  methods:{
    trainInfoOp(lists){
      let trainDetailInfo = {list: [], Index: []}
      for (var key in lists) {
				let parts = this.getTitle(lists[key]['partNo'])
				if(trainDetailInfo.Index.indexOf(parts[0])<0){
					trainDetailInfo.Index.push(parts[0])
					trainDetailInfo.list.push({ ...lists[key], partNumber: parts[0] })
				}else{
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
  	getParameter: function (s) {
        var uri = location.search.replace('?', '');
        var reg = new RegExp("(^|&)" + s + "=([^&]*)(&|$)", "i");
        var r = uri.match(reg);

        if (r != null) {
            return unescape(r[2]);
        }

        return '';
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

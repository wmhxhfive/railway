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
    //       ret = {
  		//   "ret": "0",
  		//   "message": "ok",
  		//   "trainInfos": [],
  		//   "trainDetailInfos": [
  		//     {
  		//       "trainInfoId": 14,
  		//       "partNo": "1",
  		//       "url": "www.baidu",
  		//       "analyResult": 1,
  		//       "checkDate": "2018-04-28 23:00:01"
  		//     },
  		//     {
  		//       "trainInfoId": 14,
  		//       "partNo": "2",
  		//       "url": "www.baidu",
  		//       "analyResult": 1,
  		//       "checkDate": "2018-04-28 23:00:01"
  		//     }
  		//   ]
  		// }
      if(ret.ret === '0'){
        this.trainDetailInfo = ret.trainDetailInfos;
      }else{
      	this.trainDetailInfo = [];
      }
    })
  },
  methods:{
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

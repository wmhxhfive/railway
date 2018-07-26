<template>
	<div class="detail-dialog">
		<train-detail :trainInfo="trainInfo" :trainDetailInfo="trainDetailInfo" :editable="true"></train-detail>
	</div>
</template>

<script>
import config from '@/net/config'
import trainInfoView from '@/components/monitor/trainDetail'
var QS=require('qs');

export default {
  name: 'page_Train',
  data () {
    return {
    	trainInfo: {},
    	trainDetailInfo:[]
    }
  },
  components:{
    'train-detail': trainInfoView
  },
  beforeCreate(){
    // if(!this.getCookie('token')){
    //   window.location.href="#/login";
    // }
  },
  mounted(){
  	if(window.localStorage){
		this.trainInfo = JSON.parse(window.localStorage.getItem('BLANKTRAININFO'));
	}
	this.$ajax({
      method: 'post',
      url: config.urlList.trainDetail,
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

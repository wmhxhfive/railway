<template>
    <div>
	    <div class="cont-header">
	    	<div class="cont-box">
	    		<div class="condition">
		    		<label>选择站点</label>
		    		<select v-model="railStation">
					    <option disabled value="">请选择</option>
					    <option value="合肥">合肥</option>
					</select>
		    		<label for="label">预警信息</label>
		    		<input type='checkbox' id="label" v-model="isNormal"/>
		    		<!-- <label>&nbsp;</label><button @click="search()">查询</button> -->
		    	</div>
		    </div>
	    	<div class="cont-line"></div>
	    </div>
	    <div class="info-box">
	    	<table class="list-table">
	    		<tr>
	    			<th>序号</th>
	    			<th>车型车号</th>
	    			<th>入库时间</th>
	    			<th>预警信息</th>
	    			<th>检测图片</th>
	    		</tr>
	    		<template v-if="searchList.length>0">
		    		<tr v-for="item,index in searchList">
		    			<td>{{++index}}</td>
		    			<td>{{item.railNo}}</td>
		    			<td>{{item.checkDate}}</td>
		    			<td :class="{error:item.isNormal=='1'}">{{item.isNormal=='1'?'有':'-'}}</td>
		    			<td><a href="#/traindetail" @click="saveDetail(item)" target="_blank">查看详情</a></td>
		    		</tr>
	    		</template>
	    		<template v-else>
		    		<tr>
		    			<td colspan="5">[空]</td>
		    		</tr>
	    		</template>
	    	</table>
		</div>
	</div>
</template>

<script>
import webUrls from '@/net/webUrls'
import checkLoginStatus from '@/mixin/checkLoginStatus'
import localStore from '@/mixin/localStore'
var QS=require('qs');

export default {
  name: 'page_Train',
  mixins: [localStore],
  data () {
    return {
    	railNo: '',
    	beginCheckDate: '',
    	endCheckDate: '',
    	isNormal: true,//0无 1有
    	railStation:'合肥',
    	page: 0,//0开始
    	pageSize: 30,
    	searchList: []
    }
  },
  mounted(){
  	var self = this;
  	//日期时间范围
	laydate.render({
	  elem: '#time',type: 'datetime',range: true,//date time datetime
	  calendar: true,
	  format:'yyyy-MM-dd',
	  done: function(value, date, endDate){
	    console.log(value); //得到日期生成的值，如：2017-08-18
	    self.beginCheckDate=value.substr(0, 19);
  		self.endCheckDate=value.substring(22)
	  }
	})

  	this.loadTrainList();
  	setInterval(()=>this.loadTrainList(), 30000);
  },
  methods:{
  	search(){  
  		this.loadTrainList();
  	},
  	saveDetail(item){
  		this.setLocalSave('BLANKTRAININFO', JSON.stringify(item));
  	},
  	loadTrainList(){
	  	this.$ajax({
	      method: 'post',
	      url: webUrls.urlList.trainList,
	      headers:{"Content-Type": "application/json; charset=utf-8"},
	      transformRequest: [function (data) {
	        return JSON.stringify(data);
	      }],
	      data: { 
	        railNo: this.railNo,
			isNormal: this.isNormal?1:0,
			railStation: this.railStation,
			beginCheckDate: this.beginCheckDate,
			endCheckDate: this.endCheckDate,
			page: this.page,
			pageSize: this.pageSize,
	      }
	    }).then((data) => {
	  	  var ret = data.data;
	  	  console.log('loadTrainList-', ret);
	      if(ret.message == 'success'){
	          // ret = {"ret":"0","message":"success","trainInfoList":[{"id":34,"railNo":"hello1","isNormal":null,"checkDate":"2018-04-28 23:00:01","railStation":"hf","errorReason":"11"},{"id":17,"railNo":"ca123","isNormal":null,"checkDate":"2018-04-28 23:00:00","railStation":"hf","errorReason":"11"},{"id":16,"railNo":"hello","isNormal":null,"checkDate":"2018-04-28 20:00:01","railStation":"hf","errorReason":"11"},{"id":15,"railNo":"ca123","isNormal":null,"checkDate":"2018-04-28 20:00:00","railStation":"hf","errorReason":"11"},{"id":14,"railNo":"hello","isNormal":null,"checkDate":"2018-04-28 23:00:01","railStation":"hf","errorReason":"11"},{"id":5,"railNo":"hello","isNormal":"0","checkDate":"2018-04-21 23:00:01","railStation":"hf","errorReason":"11"},{"id":2,"railNo":"ca123","isNormal":"0","checkDate":"2018-04-21 23:00:00","railStation":"hf","errorReason":"11"},{"id":1,"railNo":"ca123","isNormal":"0","checkDate":"2018-04-17 23:00:00","railStation":"hf","errorReason":"11"}],"totalNum":8,"totalPage":1}
	        this.searchList = ret.trainInfoList;
	      }else{
	      	this.searchList = [];
	      }
	    })
	},
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.cont-header{
	width: 100%;
	background-color: #d6edfc;
}
.cont-box{
	min-height: 80px;
	width: 100%;
}
.cont-line{
	height: 15px;
	width: 100%;
	background: url('../../assets/cont_bg.png') repeat;
}
.condition{
	padding: 10px 0 0;
}
.condition:last-child{
	padding-bottom: 10px;
}
.cont-box label{
	width: 100px;
	display: inline-block;
	text-align: right;
	margin-right: 10px; 
}

.detail-dialog{
	position: absolute;
	top: 0;
	bottom: 0;
	width: 100%;
	z-index: 11;
	background-color: #fff;

}
.close{
	width: 40px;
	height: 40px;
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
}
.close:before,.close:after{
	position: absolute;
	content: "";
	top:50%;
	left: 0;
	background: #000;
	width: 100%;
	height: 3px;
	transition-duration:0.5s;
	-moz-transition-duration:0.5s;
	/* Safari and Chrome */
	-webkit-transition-duration:0.5s;
	/* Opera */
	-o-transition-duration:0.5s;
}
.close:before{
	transform:rotate(45deg);
	-webkit-transform:rotate(45deg);
	-moz-transform:rotate(45deg);
	-ms-transform:rotate(45deg);
	-o-transform:rotate(45deg);
}
.close:after{
	transform:rotate(-45deg);
	-webkit-transform:rotate(-45deg);
	-moz-transform:rotate(-45deg);
	-ms-transform:rotate(-45deg);
	-o-transform:rotate(-45deg);
}
.close:hover:before{
	transform:rotate(225deg);
	-webkit-transform:rotate(225deg);
	-moz-transform:rotate(225deg);
	-ms-transform:rotate(225deg);
	-o-transform:rotate(225deg);
	background: #04649e;
}
.close:hover:after{
	transform:rotate(135deg);
	-webkit-transform:rotate(135deg);
	-moz-transform:rotate(135deg);
	-ms-transform:rotate(135deg);
	-o-transform:rotate(135deg);
	background: #04649e;
}
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .5s ease;
}
.slide-fade-leave-active {
  transition: all .5s ease;
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateY(-20px);
  opacity: 0;
}
</style>

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
	    	<div class="page-box" v-show="totalPage > 1">
	    		<div class="page-inner">
	    			<span>第{{page}}页 / 共{{totalPage}}页</span>
	    			<a :class="{dis: page<=1}" @click="page--">上一页</a>
	    			<a :class="{dis: page>=totalPage}" @click="page++">下一页</a>
						<input class="tmp-page" type="number" v-model="tmpPage"/>
	    			<a @click="gotoPage" style="margin: 0;">确定</a>
	    		</div>
	    	</div>
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
			timer: null,
    	railNo: '',
    	beginCheckDate: '',
    	endCheckDate: '',
    	isNormal: true,//0无 1有
    	railStation:'合肥',
    	page: 1,// 接口从0开始
    	pageSize: 20,
			tmpPage: 1,
    	totalPage: 0,
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
  	this.timer = setInterval(()=>this.loadTrainList(), 30000);
  },
  actived(){
  },
  methods:{
  	search(){  
			this.tmpPage = 1
			this.page = 1
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
					page: this.page-1,
					pageSize: this.pageSize,
	      }
	    }).then((data) => {
	  	  var ret = data.data;
	  	  console.log('loadTrainList-', ret);
	      if(ret.message == 'success'){
	        this.searchList = ret.trainInfoList;
	        this.totalPage = ret.totalPage;
	      }else{
	      	this.searchList = [];
	      }
	    })
		},
		gotoPage (){
			if(this.tmpPage < this.totalPage){
				this.page = this.tmpPage
			}else{
				this.tmpPage = this.totalPage
				this.page = this.totalPage
			}
		}
	},
  watch:{
  	page(){
  		this.loadTrainList();
  		// this.initPageList();
  	}
  },
  beforeDestroy(){
  	this.timer && clearInterval(this.timer)
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
	background: url('../../assets/images/cont_bg.png') repeat;
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

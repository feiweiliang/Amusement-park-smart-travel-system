<template>

 <div class="content-box">
  <div class="container">

<el-container>
  <el-header><h2>当前园区的总人数为：{{totalNumber}} 人</h2></el-header>
  <el-main>
    <p style="text-align: center;font-size: 18px;">点击地图以刷新查看人数</p>
<div><map-showtotalnumber></map-showtotalnumber></div>
  <!-- </el-main>
    <el-main>
       <el-header><h2>园区实时人数变化图</h2></el-header>
        
        <div style="margin-top:50px">

<div id="id-dynamic-total" style="width: 800px;height:400px;"></div>
  </div>-->
  </el-main> 
</el-container>
      
        </div>
    </div>


</template>
<script>
    import mapShowpathplaningVue from '../map-showpathplaning.vue';

    import * as echarts from 'echarts';
import MapShowtotalnumber from '../map-showtotalnumber.vue';
import axios from 'axios';
    
export default{
    components:{
    mapShowpathplaningVue,
    MapShowtotalnumber
},
    data:function(){
        return{
            totalNumber:144
        }
    },
    methods: {

         myechart:function(){
        var app = {};

var chartDom = document.getElementById('id-dynamic-total');
var myChart = echarts.init(chartDom);
var option;

const categories = (function () {
  let now = new Date();
  let res = [];
  let len = 10;
  while (len--) {
    res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
    now = new Date(+now - 2000);
  }
  return res;
})();
const categories2 = (function () {
  let res = [];
  let len = 10;
  while (len--) {
    res.push(10 - len - 1);
  }
  return res;
})();
const data = (function () {
  let res = [];
  let len = 10;
  while (len--) {
    res.push(Math.round(Math.random() * 1000));
  }
  return res;
})();
const data2 = (function () {
  let res = [];
  let len = 0;
  while (len < 10) {
    res.push(+(Math.random() * 10 + 5).toFixed(1));
    len++;
  }
  return res;
})();
option = {
  title: {
    text: 'Dynamic Data'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#283b56'
      }
    }
  },
  legend: {},
  toolbox: {
    show: true,
    feature: {
      dataView: { readOnly: false },
      restore: {},
      saveAsImage: {}
    }
  },
  dataZoom: {
    show: false,
    start: 0,
    end: 100
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: true,
      data: categories
    },
    {
      type: 'category',
      boundaryGap: true,
      data: categories2
    }
  ],
  yAxis: [
    {
      type: 'value',
      scale: true,
      name: 'Price',
      max: 30,
      min: 0,
      boundaryGap: [0.2, 0.2]
    },
    {
      type: 'value',
      scale: true,
      name: 'Order',
      max: 1200,
      min: 0,
      boundaryGap: [0.2, 0.2]
    }
  ],
  series: [
    {
      name: 'Dynamic Bar',
      type: 'bar',
      xAxisIndex: 1,
      yAxisIndex: 1,
      data: data
    },
    {
      name: 'Dynamic Line',
      type: 'line',
      data: data2
    }
  ]
};
app.count = 11;
setInterval(function () {
  let axisData = new Date().toLocaleTimeString().replace(/^\D*/, '');
  data.shift();
  data.push(Math.round(Math.random() * 1000));
  data2.shift();
  data2.push(+(Math.random() * 10 + 5).toFixed(1));
  categories.shift();
  categories.push(axisData);
  categories2.shift();
  categories2.push(app.count++);
  myChart.setOption({
    xAxis: [
      {
        data: categories
      },
      {
        data: categories2
      }
    ],
    series: [
      {
        data: data
      },
      {
        data: data2
      }
    ]
  });
}, 2100);

option && myChart.setOption(option);
    }
    }, 
    mounted() {
    this.myechart()
   },
   created(){
    axios.get('http://127.0.0.1:8181/user/getAllUsersNumber')
    .then(res=>{this.totalNumber=res.data.totalNumber})
    .catch((e)=>{
      console.log(e);
    })
   }
}
</script>

<style scoped>
.dynamic-change-totalnumber-echart{
margin-top:10%;
}

  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  

  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }


</style>

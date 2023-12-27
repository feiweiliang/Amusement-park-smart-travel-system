<template>
       <div class="content-box" >
    <div class="container">
<h1>各游玩项目的排队时长情况</h1>
<p>注：本数据主要基于用户排队和游玩的历史数据进行统计分析后获得</p>
<div id="waiting-time-chart" style="width:800px;height: 500px;margin-top: 40px;"></div>

        </div></div>
</template>
<script>
 import * as echarts from 'echarts';
import axios from 'axios';
export default{
  data() {
    return {
      projectName:"33",
      waitingTime:34
    }
  },
    methods:{
myechart:function(){

var chartDom = document.getElementById('waiting-time-chart');
var myChart = echarts.init(chartDom);
var option;

option = {
  title: {
    text: '各游玩项目等待时长'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {},
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'value',
    boundaryGap: [0, 0.01]
  },
  yAxis: {
    type: 'category',
    // data: ['地动山摇', '丛林冒险', '刺激战场', '和平精英', '过山车', '鬼屋']
    data:this.projectName
  },
  series: [
    {
      name: '等待时长（分钟）',
      type: 'bar',
      // data: [15, 30, 120, 99, 10, 36]
    data:this.waitingTime
    }
  ]
};

option && myChart.setOption(option);

}
   

    },
    mounted() {
      const _this=this;
      axios.get('http://127.0.0.1:8181/map/getGroundWaitTime').then(
        res=>{
          _this.projectName=res.data.data.groundTitle;
          _this.waitingTime=res.data.data.waitTime;
         console.log("a:",res.data); 
         _this.myechart();
      });

   

    }
    }

</script>

<style scoped>

</style>


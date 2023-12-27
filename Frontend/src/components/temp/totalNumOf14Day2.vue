
<!--
这一个文件的作用是按照时间段来展示园区所有人数的。（某一个特定的日期）
  -->

<template> 
<div class="ShowOfPast14Day">
  
   <div id="main2" style="width: 600px;height:400px;">

   </div> 
   </div> 
   
   </template> 

   <script> 
   export default {
    
     name: 'Echarts', 
     methods:{ 
      myEcharts(){ 
        // 基于准备好的dom，初始化echarts实例
    var myChart = this.$echarts.init(document.getElementById('main2')); 
    var TheDate='6月5日';
    // 指定图表的配置项和数据 
    var option = { 
      
      title: { text: '园区'+TheDate+'分时段人数展示'
      },
      tooltip: {
      }, 
      legend: { data:['人数'] },
      xAxis: { data: ["8-9点","9-10点","9-10点","10-11点","11-12点","12-13点"] 
      },
      yAxis: {
      }, 
      series: [{ name: '人数', type: 'bar', data: [5, 20, 36, 10, 10, 20] }
      ] };// 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
      } },
        mounted() { this.myEcharts();
         } }
   </script> 

   <style>
   
   </style>

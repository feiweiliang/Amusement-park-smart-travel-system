
<!--
这一个文件的作用是用来按照日期来展示园区所有人数的。
  -->

<template> 
<div class="Echarts">
 
   <div id="NumberOfEveryProjectBefore14Days" style="width: 600px;height:400px;">

   </div> 
   </div> 
   
   </template> 

   <script> 
   export default {
     name: 'Echarts', 
     methods:{ 
      myEcharts(){ 
        // 基于准备好的dom，初始化echarts实例
    var myChart = this.$echarts.init(document.getElementById('NumberOfEveryProjectBefore14Days')); 
    // 指定图表的配置项和数据 
    var option = { 
      title: { text: '近14天园区人数展示' 
      },
      tooltip: {
      }, 
      legend: { data:['人数'] },
      xAxis: { data: ["6月1日","6月2日","6月3日","6月4日","6月5日","6月6日"] 
      },
      yAxis: {
      }, 
      series: [{ name: '人数', type: 'bar', data: [55, 250, 365, 105, 150, 250] }
      ] };// 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
      } },
        mounted() { this.myEcharts();
         } }
   </script> 

   <style>
   
   </style>

<template> 
<div class="ShowOfPast14Day">
  
   <div id="projectNum" style="width: 1200px;height:400px;">

   </div> 
   </div> 
   
   </template> 

   <script> 
   import axios from 'axios'
   var strdata;//项目名称
   var strnum;//项目人数
   export default {
    
     name: 'Echarts', 
     methods:{ 
      //将字符串转化为data类型输出
      splitToObj(obj){
        var arr=[];
        var strobj=[];//返回data类型数据
        arr=obj.split(',');//根据逗号拆分返回值
        //获取时间
        for(var i in arr){
            strobj[i]=(arr[i]);
          }
        return strobj;
      }, 
      myEcharts(){ 
        // 基于准备好的dom，初始化echarts实例
    var myChart = this.$echarts.init(document.getElementById('projectNum')); 
    var middle1=sessionStorage.getItem('strdata3');//中间变量记录项目名称
    var resultNum5=this.splitToObj(middle1);
    var middle2=sessionStorage.getItem('strnum3');//中间变量记录项目人数
    var resultNum6=this.splitToObj(middle2);
    var TheDate='今日';
    // 指定图表的配置项和数据 
    var option = { 
      
      title: { text: '园区'+TheDate+'分项目人数展示'
      },
      tooltip: {
      }, 
      legend: { data:['人数'] },
      xAxis: { data: resultNum5 
      },
      yAxis: {
      }, 
      series: [{ name: '人数', type: 'bar', data: resultNum6 }
      ] };// 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
      }, 
      splits(obj){//对象转化为数组
        var arr=[];
        var strobj=[];
        arr=obj.split(',');//根据逗号拆分返回值
        //获取时间
        for(var i in arr){
          if(arr[i].includes("{")){
            strobj[i]=(arr[i].substring(2,arr[i].indexOf("\":")));
          }else{
            strobj[i]=(arr[i].substring(1,arr[i].indexOf("\":")));
          }
        }
        sessionStorage.setItem('strdata3',strobj);
        //获取人数
        for(var i in arr){
          if(arr[i].includes("}")){
            strnum=strnum+','+arr[i].substring(arr[i].indexOf("\":")+2,arr[i].length-1);
          }else{
            if(arr[i].includes("{")){
              strnum=arr[i].substring(arr[i].indexOf("\":")+2,arr[i].length);
            }else{
              strnum=strnum+','+arr[i].substring(arr[i].indexOf("\":")+2,arr[i].length);
            }
          }
        }
        sessionStorage.setItem('strnum3',strnum);
      }},
      created() {
        axios.get('http://localhost:8080/getUserListproject').then(res=>{
                    let msg=JSON.stringify(res.data[0]);
                    var ss=this.splits(msg);
                    
                })
      },
        mounted() { this.myEcharts();
         } }
   </script> 

   <style>
   
   </style>

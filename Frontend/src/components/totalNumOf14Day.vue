<!--
这一个文件的作用是用来按照日期来展示园区所有人数的。
  -->

<template> 
<div class="Echarts">
 
   <div id="NumberOfEveryProjectBefore14Days" style="width: 1200px;height:400px;">
   </div> 
   </div> 
   </template> 

   <script> 
   import axios from 'axios'
   var strdata;//时间
   var strnum;//人数
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
    var myChart = this.$echarts.init(document.getElementById('NumberOfEveryProjectBefore14Days')); 
    var middle1=sessionStorage.getItem('strdata');//中间变量记录时间
    var resultNum1=this.splitToObj(middle1);
    var middle2=sessionStorage.getItem('strnum');//中间变量记录人数
    var resultNum2=this.splitToObj(middle2);
    // 指定图表的配置项和数据 
    var option = { 
      title: { text: '近14天园区人数展示' 
      },
      tooltip: {
      }, 
      legend: { data:['人数'] },
      axisLabel: {
            interval: 0,
            rotate: 45 // 角度
        },
      xAxis: { data:  resultNum1 
      //xAxis: { data: getData() 
      },
      yAxis: {
      }, 
      //series: [{ name: '人数', type: 'bar', data: strnum }
      series: [{ name: '人数', type: 'bar', data: resultNum2 }
      ] };// 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
      } ,
      splits(obj){//对象转化为数组
        var arr=[];
        var strobj=[];
        arr=obj.split(',');//根据逗号拆分返回值
        //获取时间
        for(var i in arr){
          if(arr[i].includes("{")){
            strdata=arr[i].substring(1,arr[i].indexOf(':'));
            strobj[i]=eval(arr[i].substring(1,arr[i].indexOf(':')));
          }else{
            strdata=strdata+','+arr[i].substring(0,arr[i].indexOf(':'));
            strobj[i]=(eval(arr[i].substring(0,arr[i].indexOf(':'))));
          }
        }
        sessionStorage.setItem('strdata',strobj);
        //获取人数
        for(var i in arr){
          if(arr[i].includes("}")){
            strnum=strnum+','+arr[i].substring(arr[i].indexOf(':')+1,arr[i].length-1);
          }else{
            if(arr[i].includes("{")){
              strnum=arr[i].substring(arr[i].indexOf(':')+1,arr[i].length);
            }else{
              strnum=strnum+','+arr[i].substring(arr[i].indexOf(':')+1,arr[i].length);
            }
          }
        }
        sessionStorage.setItem('strnum',strnum);
      }},
      
      created() {
        axios.get('http://127.0.0.1:8181/user/getNumbersOfUserInLast14Days').then(res=>{
                    let msg=JSON.stringify(res.data.data);
                    // console.log(msg);
                    this.splits(msg);

                })
      },
        mounted() { this.myEcharts();
         } }
   </script> 

   <style>
   
   </style>

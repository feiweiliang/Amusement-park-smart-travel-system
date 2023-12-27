
<!--
这一个文件的作用是按照时间段来展示园区所有人数的。（某一个特定的日期）
  -->

<template> 

<div class="ShowOfPast14Day">

    <div class="block">
    <span class="demonstration">选择一个日期以查看分时段人流</span>
    <el-date-picker
      v-model="DateOf14"
      type="date"
      placeholder="选择日期"
      value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
      end-placeholder="结束日期"
  :picker-options="pickerOptions"
  @change="updatechart">
    </el-date-picker>
    <!-- <p>这是时间点：{{this.DateOf14}}</p> -->
  </div>
 
   <div id="main2" style="width: 1200px;height:400px;">
   <!--<div>
          <span>
            <datePicker id="datePicker" @setDate="changeDate"></datePicker>
          </span>
    </div>-->

   </div> 
   </div> 
   
   </template> 
   <script> 
   import axios from 'axios'
   var strdata;//时间
   var strnum;//人数
   export default {
     data() {
      return {
       
        // 选择的范围  14天之前到今天
       
        pickerOptions: {
           disabledDate(time) {
          let curDate = new Date().getTime();
          let three = 14 * 24 * 3600 * 1000;
          let threeMonths = curDate - three;
          return time.getTime() > Date.now() || time.getTime() < threeMonths;
        },
          shortcuts: [{  
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {  
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
        DateOf14: '',
        value2: '',
      };
    },
     name: 'Echarts', 
     methods:{
      updatechart(){
      //console.log("xxx:",this.DateOf14);
     
        // axios.post('http://127.0.0.1:8181/user/getNumbersOfUserInLast1Days',
        
        //   {"specifyDate":this.DateOf14}

        // ).then(res=>{
        //            // alert(this.DateOf14);
        //             let msg=JSON.stringify(res.data.data.data);
        //             console.log(res.data);
        //             this.splits(msg);
                  
        //         });
        //         console.log(sessionStorage.getItem('strnum2'));
        //     this.myEcharts();    
            //console.log("xxx:",this.DateOf14);
     
        const _this = this;
        axios.post('http://127.0.0.1:8181/user/getNumbersOfUserInLast1Days',
        
          {"specifyDate":this.DateOf14}

        ).then(res=>{
                   // alert(this.DateOf14);
                    let msg=JSON.stringify(res.data.data);
                   // console.log(res.data);
                    _this.splits(msg);
                    //console.log(msg);
                  _this.myEcharts(); 
                });
        console.log(sessionStorage.getItem('strnum2'));
      },
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
    var myChart = this.$echarts.init(document.getElementById('main2')); 
    var middle1=sessionStorage.getItem('strdata2');//中间变量记录时间
    var resultNum3=this.splitToObj(middle1);
    var middle2=sessionStorage.getItem('strnum2');//中间变量记录人数
    var resultNum4=this.splitToObj(middle2);

    var TheDate="所选日期的";
    // 指定图表的配置项和数据 
    var option = { 
      
      title: { text: '园区'+TheDate+'分时段人数展示'
      },
      tooltip: {
      }, 
      legend: { data:['人数'] },
      xAxis: { data: resultNum3 
      },
      yAxis: {
      }, 
      series: [{ name: '人数', type: 'bar', data: resultNum4 }
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
            strobj[i]=(arr[i].substring(2,arr[i].indexOf("\":")));
          }else{
            strobj[i]=(arr[i].substring(1,arr[i].indexOf("\":")));
          }
        }
        sessionStorage.setItem('strdata2',strobj);
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
        sessionStorage.setItem('strnum2',strnum);
      },

      getdate(){
        var _this = this;
		      let yy = new Date().getFullYear();
		      let mm = new Date().getMonth()+1;
		      let dd = new Date().getDate();
		      let hh = new Date().getHours();
		      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
		      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
		      _this.gettime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
		      console.log(this.gettime);
      }
      
      },
      
      // created() {
      //   axios.post({url:'http://127.0.0.1:8181/user/getNumbersOfUserInLast1Days',
      //   params:{
      //     specifyDate:this.DateOf14
      //   }
        
      //   }).then(res=>{
      //               let msg=JSON.stringify(res.data.data);
      //               console.log(res.data);
      //               this.splits(msg);
      //           })
      // },
        mounted() { 
          this.myEcharts();
         },

      // updated(){
      //   axios.post({url:'http://127.0.0.1:8181/user/getNumbersOfUserInLast1Days',
      //   params:{
      //     specifyDate:this.DateOf14
      //   }
        
      //   }).then(res=>{
      //               let msg=JSON.stringify(res.data.data);
      //               this.splits(msg);
      //           })
      // }
      }
   </script> 





   
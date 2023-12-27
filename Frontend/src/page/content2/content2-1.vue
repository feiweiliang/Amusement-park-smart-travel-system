<template>
    <div  class="content-box">
        <div class="content">
            <div class="word-content2-2">
                我们根据人流密度帮您选择最佳的路线，请选择你想玩的地方：

               
            </div>
            <el-col :span="8">
                <el-select v-model="value" placeholder="请选择" class="choose-content2-2" @change="">
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8">
              <!-- <h1>eee{{value}}</h1>  -->
               <div ><el-button @click="plan(value)" class="choose-content2-2" type="warning">一键规划路线</el-button></div>
            </el-col>
            <el-col :span="8">
                <div><el-button @click="handleClick"  class="choose-content2-2" type="danger">加入到我的出行</el-button></div>
            </el-col>
            <!-- <div class="wd">
              <tr v-for="(v,i) in userlist" key = "i">
                <el-col :span="12" class="choose-content2-2"><td>{{v.x}}</td></el-col>
                <el-col :span="12" class="choose-content2-2"><td>{{v.y}}</td></el-col>
              </tr> -->
           
              <div key="path" v-if="vistwo">  选择项目之后显示</div>
         <mapShowpathplaningVue key="path" v-if="vistwo"></mapShowpathplaningVue>
          <!-- <mapplan-vue></mapplan-vue> -->
             
          
        </div>
          
<!-- <mapShowpathplaningVue key="path" v-if="vistwo"></mapShowpathplaningVue> -->

            <!-- <mapShowtotalnumberVue key="map" v-if="visfir"></mapShowtotalnumberVue>
              
              <mapShowpathplaningVue key="path" v-if="vistwo"></mapShowpathplaningVue>


              <dialog-component v-if="Visiable" ref="dialog"></dialog-component> -->
    </div>
</template>
<script>
    import echarts from 'echarts';
    
    import dialogComponent from "./dialog";
    import mapShowpathplaningVue from '../map-showpathplaning.vue';
    import mapShowtotalnumberVue from '../map-showtotalnumber.vue';
    import mapplanVue from '../mapplan.vue';
    // const chinaJson = require('@./src/assets/map/parkmap.json');
    export default{
        components:{
          dialogComponent,
          mapplanVue,
          mapShowpathplaningVue,
         // mapShowtotalnumberVue,

      },
      
      data() {
      return {
        inject:['reroad'],
        options: [{
          value: '大门',
          label: '大门'
        }, {
          value: '乘风破浪',
          label: '乘风破浪'
        }, {
          value: '地动山摇',
          label: '地动山摇'
        }, {
          value: '双峰埃及',
          label: '双峰埃及'
        }, {
          value: '星际飞碟',
          label: '星际飞碟'
        }, {
          value: '天旋地转',
          label: '天旋地转'
        }, {
          value: '玩偶对对碰',
          label: '玩偶对对碰'
        }, {
          value: '唐古拉雪山',
          label: '唐古拉雪山'
        }, {
          value: '暴风眼',
          label: '暴风眼'
        }, {
          value: '飞跃极限',
          label: '飞跃极限'
        }, {
          value: '中央水池',
          label: '中央水池'
        }, {
          value: '跳楼机',
          label: '跳楼机'
        }, {
          value: '欢乐小寨',
          label: '欢乐小寨'
        }, {
          value: '恐龙危机',
          label: '恐龙危机'
        }, {
          value: '音乐餐厅',
          label: '音乐餐厅'
        },{
          value: '梦幻城堡',
          label: '梦幻城堡'
        },],
        value: '',
        label:'',
        Visiable:false,
        list:[],
        vistwo:false,
        visfir:false,
      }
      
    },
    
 //   created(){},
      // userlist:function(){
        
      //   this.visfir=true,
      //   this.vistwo=false
        
      
      // return JSON.parse(sessionStorage.getItem('user'))
      // },
      mounted(){

      
        if (location.href.indexOf("#reloaded")<=0) {
        location.href = location.href + "#reloaded"+"#reloaded";
        location.reload();
      }
    
    },
  
    computed:{  
      // userlist:function(){
      //   this.visfir=false;
      //   this.vistwo=true;
      //   return JSON.parse(sessionStorage.getItem('user'));    
      // }
      // function(){
      //   if(sessionStorage.getItem('user')){
      //   this.visfir=false,
      //   this.vistwo=true
      // }
      // }
      //  userlist:function(){
        
      //     this.visfir=false,
      //     this.vistwo=true
        
      // return JSON.parse(sessionStorage.getItem('user'))
      // }, 
    },

      mounted() {
       
      }
    
    ,
    
    methods:{
      // 7.实现点击事件，点击事件一定要包含以下内容
      	handleClick(data){
          this.Visiable=true;
          this.$nextTick(()=>{
          //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
          //init调用的是dialog-component组件里面的init方法
          //data是传递给弹窗页面的值
            this.$refs.dialog.init(data);
          })
        },  
         //下拉框选定地点
        classSelect(e){
          for (var i in this.options) {
      		i=e.target.selectedIndex;
			    label=this.options[i].label;
           
		      }
		      console.log(label)
        },
        plan(place){
          //alert(place);
          this.vistwo=true;
          this.visfir = false;
          //console.log("1:2:",this.vistwo,this.visfir);
          //给后台传输地点

          this.$axios.get('http://127.0.0.1:8181/path/getRoutePlan/33/'+this.value).then(res=>{
           
            sessionStorage.setItem('pathxxx',JSON.stringify(res.data.data));

          //  console.log("plan:",res.data.data)
          })
          
        } 
       },
    }
</script>

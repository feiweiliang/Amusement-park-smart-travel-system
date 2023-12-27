<template>
    <div  class="content-box">
        <div class="content">
            <div class="word-content2-2" style="font: size 15px;; width:500px;margin-left: 30%;">
                基于当前的用户数量、舒适度、天气状况以及道路人流密度我们给您推荐一下项目
            </div>
            
            <table class="tb">
                <tr v-for="(v,i) in list" >
                   
                    <el-col :span="6" class="choose-content2-2"><td>TOP {{i}}</td></el-col>
                    <el-col :span="6" class="choose-content2-2"><td>{{v}}</td></el-col>
                    <el-col :span="6" class="choose-content2-2"><td><el-button @click="plan(v)">一键规划路线</el-button></td></el-col>
                    <el-col :span="6" class="choose-content2-2"><td><el-button @click="handleClick">加入到我的出行</el-button></td></el-col>
                </tr>  
            </table>
        </div>
        <dialog-component v-if="Visiable" ref="dialog"></dialog-component>       
    </div>
    
</template>
<script>
    import echarts from 'echarts';
    import axios from 'axios';
    import Vue from 'vue';
    import dialogComponent from "./dialog";
    
    // const chinaJson = require('@./src/assets/map/parkmap.json');
    Vue.prototype.$axios = axios;
    
    export default{
        name:"topthree",
        components:{
          dialogComponent
        },
        data(){
            return{
                list:[],
                Visiable:false
            }
        },
        created(){
            this.$axios.get('http://127.0.0.1:8181/travel/getTravelRecommend').then(res=>{
                this.list=res.data.data;
                console.log(res)
            })
            .catch(error=>{
                console.error(error);
            })
        },
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
            plan(place){
                
                //给后台传输地点
                this.$axios.post('http://localhost:8080/getUserList',{
                    data: place
                }).then(function(res){
                    console.log(res)
                })
                //从后台得到到该地点的json路线文件
                this.$axios.get('http://localhost:8080/getUserList').then(res=>{
                    sessionStorage.setItem('user',JSON.stringify(res.data))
                    
                    console.log(res)
                })
                // sessionStorage.setItem()
                this.$router.push({path: 'content2-1',title: '路线规划'});
            }
        }
    }
</script>

<template>
    <div  class="content-box2">       
            <table class="tb">
                <tr v-for="(v,i) in list" key = "i">
                    <el-col :span="12" style="font-weight:bold;font-size:xx-large;"   class="choose-content4-2"><td>{{v}}</td></el-col>
                    <el-col :span="6" class="choose-content4-2"><td><el-button type="button" class="el-button"  @click="handleClick">删除</el-button></td></el-col>
                </tr>  
            </table>
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
            this.$axios.get('http://127.0.0.1:8181/user/getFavorite/2').then(res=>{
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
                //this.$router.push({path: 'content2-1',title: '路线规划'});
            }
        }
    }
</script>
<style>
 button{
            outline: 0;
        }
        .el-button {
            padding: 0 24px;
            border: 1px solid #1E9FFF;
            font-size: 14px;
            color: #1E9FFF;
            -webkit-transition: background-color .3s ease-in, border-color .3s ease-in;
            transition: background-color .3s ease-in, border-color .3s ease-in;
            line-height: 26px;
            border-radius: 4px;
            cursor: pointer;
        }
        .el-button:hover {
            background-color: #1E9FFF;
            color: #fff;}
        /* }
.content-box2 {
  display: flex;
  justify-content: center;
  align-items: stretch;
  background-image: url("../../assets/img/42background.jpg");
  background-size: 100% 100%;
  height: 100%;
  width: 100%;
  position: fixed;
} */
</style>

<template>
<div class="content-box" >
    <div class="container">
 <h1 style="margin-bottom:50px">选择需要加入排队管理的项目</h1>

<div >

    <el-checkbox-group v-model="checkList" >
    <el-checkbox label="地动山摇"></el-checkbox>
    <el-checkbox label="双峰埃及"></el-checkbox>
    <el-checkbox label="乘风破浪"></el-checkbox>
    <el-checkbox label="欢乐小寨" ></el-checkbox><br/>
    <el-checkbox label="恐龙危机" ></el-checkbox>
    <el-checkbox label="音乐餐厅"></el-checkbox>
    <el-checkbox label="梦幻城堡"></el-checkbox>
    <el-checkbox label="唐古拉雪山"></el-checkbox><br/>
    <el-checkbox label="暴风眼" ></el-checkbox>
    <el-checkbox label="飞跃极限"></el-checkbox>
    <el-checkbox label="玩偶对对碰"></el-checkbox>
    <el-checkbox label="天旋地转"></el-checkbox><br/>
    <el-checkbox label="星际飞碟"></el-checkbox>

    
  </el-checkbox-group>
<el-row>
 
  <el-button type="primary" @click="submitcheckbox">排队规划</el-button>
</el-row>
  
</div>

    </div></div>
   
    
</template>

<style scoped>

</style>
<script>
import axios from 'axios';

  export default {
    data () {
      return {
        checkList: [],
        WaitingList:[]
      };
    },
    methods:{
        //使用这个函数用来提交多选的项目
        submitcheckbox:function(){
          //  window.alert(this.checkList)
          this.$axios.post('http://127.0.0.1:8181/api/getWatingPlaningList',this.checkList)
          .then(
            function(res){
                this.WaitingList=res;
            }
          ).catch({
            function(err){
                console.log(err);
            }
          })

        }
    }
  };
</script>

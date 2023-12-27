
<!-- 这个文件没有用 -->
<template>

   <el-row class="tac">
  <el-col :span="3">
    <h5>侧边导航栏</h5>
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose">
      
        
      <el-menu-item index="1" >
        <i class="el-icon-document"></i>
        <span slot="title">导航1</span>
      </el-menu-item>
      <el-menu-item index="2">
        <i class="el-icon-setting"></i>
        <span slot="title">导航2</span>
      </el-menu-item>
     
      <el-menu-item index="3" >
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">导航四</span>
      </el-menu-item>
    </el-menu>
  </el-col>
 
</el-row>
</template>


<script>
  export default {
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>
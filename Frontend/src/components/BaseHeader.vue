<!-- 这个文件没有用 -->
<template>

    <el-menu :default-active="activeIndex" class="base-header" mode="horizontal" @select="handleSelect">
  <el-menu-item index="1">园区状况</el-menu-item>
   <el-menu-item index="2">出行建议</el-menu-item>
  <el-menu-item index="3">排队管理</el-menu-item>
  <el-menu-item index="4">我的出行</el-menu-item>
</el-menu>

</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1'
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>
<style scoped>
.base-header{
    float: right;
    margin-right: 300px;
}
</style>
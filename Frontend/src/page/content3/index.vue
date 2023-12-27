<template>
  <div>
    
    <!-- 一级菜单下面所拥有的二级菜单 -->
    <el-aside>
      <SideMenu :itemList='itemList'></SideMenu>
    </el-aside>
    <!-- 以及二级菜单所对应的页面 -->
    <el-main>
      <router-view></router-view>
    </el-main>
 
  </div>
</template>
 
<script>
import SideMenu from '@/components/sidemenu/SideMenu';
export default {
  components: {
    SideMenu
  },
  data(){
    return{
      itemList: [
        { path: 'content3-1', title: '项目排队时长预测' },
        // { path: 'content3-2', title: '多项目排队管理' },
        // { path: 'content3-3', title: '二级菜单1-3' },
        // { path: 'content3-4', title: '二级菜单1-4' },
        // { path: 'content3-5', title: '二级菜单1-5' }
      ]
    }
  }
}
</script>
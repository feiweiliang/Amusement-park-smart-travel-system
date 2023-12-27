<!-- <template>
  
<whole/>

</template>

<script>

import totalNumOf14Day from './components/totalNumOf14Day'
import totalNumOf14Day2 from './components/totalNumOf14Day2'
import calendarComponent from './components/CalendarForSelectOneTime.vue'
import DynamicShowProject from './components/ProjectDynamicChangeOfTheDate.vue'
import BaseHeader from './components/BaseHeader.vue'
import BaseLiderVue from './components/BaseLider1.vue'
import whole from './components/common/whole.vue'
export default {
  
  name: 'App',
  components: {
    totalNumOf14Day,
    totalNumOf14Day2,
    calendarComponent,
    DynamicShowProject,
    BaseHeader,
    BaseLiderVue,
    whole
    
  }

  
  
  
}


</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style> -->

<template>
  <div id="app" :class="style ? 'theme1' : ''">
    <router-view></router-view>
  </div>
</template>

<script>
import bus from '@/utils/bus';

export default {
  data(){
    return{
      style: false
    }
  },
  created(){
    bus.$on('global_theme', (msg) => {    // 接收子组件传来 global_theme 的值
      this.style = msg;
    });
  }
}
</script>

<template>
  
<div>
  <p>过去十四天的园区人数（按照日期）</p>
  <totalNumOf14Day/>
    
  
<calendarComponent></calendarComponent>
<p>过去十四天中具体某天的园区人数（按照时间段）</p>
  <totalNumOf14Day2/>
  <DynamicShowProject></DynamicShowProject>
  </div>

</template>

<script>

import totalNumOf14Day from './components/totalNumOf14Day'
import totalNumOf14Day2 from './components/totalNumOf14Day2'
import calendarComponent from '../../components/CalendarForSelectOneTime.vue'
import DynamicShowProject from '../../components/temp/ProjectDynamicChangeOfTheDate.vue'
export default {
  
  name: 'past14info',
  components: {
    totalNumOf14Day,
    totalNumOf14Day2,
    calendarComponent,
    DynamicShowProject
    
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
</style>

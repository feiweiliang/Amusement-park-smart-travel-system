<template>
  
<div class="content-box">
  <div class="container">
  <p>过去十四天的园区人数（按照日期）</p>
  <totalNumOf14Day/>
    
  
<calendarComponent></calendarComponent>
<p>过去十四天中具体某天的园区人数（按照时间段）</p>
  <totalNumOf14Day2/>
  <!-- <DynamicShowProject></DynamicShowProject> -->
  </div></div>

</template>

<script>

import totalNumOf14Day from '../../components/totalNumOf14Day'
import totalNumOf14Day2 from '../../components/totalNumOf14Day2'

import DynamicShowProject from '../../components/temp/ProjectDynamicChangeOfTheDate.vue'

export default {
  
  name: 'past14info',
  components: {
    totalNumOf14Day,
    totalNumOf14Day2,
    //calendarComponent,
    DynamicShowProject
    
  },
  data:function(){
    return({


    })
  }

  
  
  
}


</script>

<style>

</style>

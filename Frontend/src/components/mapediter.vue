<template>
<el-card class="box-card " >
  
<template v-if="currentPolygon && currentPolygon.points ">
     
<div slot="header" class="clearfix">
     <h1>{{currentPolygon.attrTitle}}<el-button style="float: right; padding: 3px 0" type="text">加入我的出行</el-button></h1>
</div> 
<!-- 这里插入图片 -->
  <div class="demo-image">
  <div class="block">
    
    <el-image 
      style="width: 500px; height: 300px"
      :src="require('./img/img'+String(this.currentPolygon.id)+'.jpg')"
      :fit="fit" lazy></el-image>

     <!-- <img :src="require('./img/img9.jpg')" style="width: 500px;height:300px"/> -->
  </div>
</div>
  <div class="text item">
    <h3>项目描述</h3>
  </div>
 <div class="text item" style="width:70%;word-wrap:break-word;">
{{discription}}
  </div>
   <!-- <div class="text item">
  id是这个：{{this.currentPolygon.id}}
  </div> -->
   


  </template>

  <template v-else>
      <div>从左侧地图中选择区域，单击查看详情</div>
    </template>
  
   
  

</el-card>

  

    
    
 
</template>

<script>
import axios from 'axios';

export default {
    props: {
        polygon: {
            type: Object
        }
    },
    data() {
        return {
            fit:"fill",
        
            buildingTypes: this.$root.buildingTypes,
            currentPolygon: null,
            value:5,
            discription:"描述信息在这里",
        };
    },
    watch: {
      polygon: function(polygon){
        this.currentPolygon = polygon
        this.$nextTick(() => {
          document.getElementById('infoInputer') && document.getElementById('infoInputer').focus()
        }),
        axios.get("http://127.0.0.1:8181/map/getGroundDesc/"+polygon.id)
      .then(res=>{
        //console.log(this.currentPolygon.id);
        this.discription=res.data.data;
      })
        
      }
      
    },
    methods: {
        
    },
    mounted() {
      
    },
    updated(){
     
    }
};
</script>


<style scoped>
 .text {
    font-size: 20px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 600px;
  }



</style>

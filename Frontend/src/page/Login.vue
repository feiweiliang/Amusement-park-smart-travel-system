<template>
 
  <div id="building">
  
    <div class="login_box">
      <div class="ava_box">
        
      </div>
      <!-- 账号 -->
      <el-form :model="loginForm" ref="loginFormRef" :rules="loginFormRules" label-width="0px" class="login_form">
        <el-form-item prop="username">
  <el-label>帐号</el-label>
          <el-input v-model="username" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
           <el-label>密码</el-label>
          <el-input  v-model="password" show-password="true" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login" round>登录</el-button>
          <!--      <el-button type="success" round>成功按钮</el-button> -->
          
        </el-form-item>
      </el-form>
    
    </div>

 </div>
</template>

<script>
import data from 'css-tree/data'

 export default {
    data(){
    return{
      // 表单数据绑定
     
      username:"",
      password:""
    
  }
 },
 methods:{
    
    login(){
     // alert(this.password)
    // this.$refs.loginFormRef.validate(async valid => { // 验证登录数据
    //     // if (!valid) { // 根据登录结果判断是否发起登录请求
    //     //   return (this.loginLoading = false)
    //     // }
    //     const { data: res } = await this.$http.post('http://127.0.0.1:8181/portal/login', this.loginForm)
        
    //     this.$message.success('登录成功!')
    //     window.sessionStorage.setItem('id', res.data.id)
    //     this.$router.push('/whole') // 跳转到home.vue
        
    //   })

    const _this=this;
    this.$axios(
        {method:"POST",
        url:"http://127.0.0.1:8181/portal/login",
        data:{
           "mobile_phone":_this.username,
           "password":_this.password
        }}
    ).then(res=>{
        console.log(res)
     
        if(res.data.status=='0'){  
          sessionStorage.setItem('id',JSON.stringify(res.data.data.id))
        _this.$router.push({path: '/Home',title: '整体页面布局'});
         }else{
          alert("登录失败！请重新输入\n错误情况："+res.data.message
        );
         }
    })
    
 }
 }
 }
</script>
<style>

 .login_container {
 background-color: #2b4b6b;
 height: 100%; }

 .login_box {
 height: 300px;
 width: 450px;
 /* background-color: #fff; */
 background-color: rgba(255,255,255,0.7);;
 border-radius: 3px;
 position: absolute;
 left: 50%;
 top: 50%;
 /* 横轴，纵轴 */
 transform: translate(-50%, -50%);
 }

 .ava_box {
 height: 130px;
 width: 130px;
 border: 1px solid #eee;
 border-radius: 50%;
 padding: 10px;
 box-shadow: 0 0 10px #ddd;
 position: absolute;
 left: 50%;
 transform: translate(-50%, -50%);
 background-color: #fff;

 
 }
 .btns{
   display: flex;
     justify-content: flex-end;

 }
 .login_form{
 position: absolute;
 bottom: 0px;
 width: 100%;
 padding: 0 20px;
   box-sizing: border-box;

  }
  #building{
  background:url("../assets/img/building.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

 
</style>
<script setup>
</script>

<template>
  <div class="wrapper">
     <form action="">
  <div class="card">
        <div class="Title_content">Đăng ký</div>
        <div class="label">Name</div>
        <input class="input">
        <div class="label">Username</div>
        <input class="input">
        <div class="label">Password</div>
        <input class="input" type="password">
        <div class="label">Confirm password</div>
        <input class="input" type="password">
        <button class="button" id="login" type="submit">Đăng ký</button>
  </div>
  </form>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.card{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  display: flex;
  flex-direction: column;    
  border-radius: 1.25rem;
  border-width: 0;
  width: 300px;
  height: 390px;
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
  align-items: center;
  align-self: center;
}
.Title_content{
    font-size: large;
    margin-top: 15px;
    font-weight: bold;
}
.input{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  border-radius: 1.25rem;
  width: 250px;
  height: 35px;
  outline: none;
  padding: 4px;
  border: 2px solid #d4ecf8;
  color:#2f4562;
  background-color:#f0f7ff;
  margin-top: 5px;
  text-align: center;
}
.label{
    align-self: flex-start;
    margin-top:5px;
    margin-left:45px;
}
.input:focus{
  background-color:white;
  border: 2px solid #d4ecf8;
}
.forgot_pw{
    align-self: flex-start;
    margin-top: 10px;
    font-size: 13px;
    margin-left:45px;
    color:#74c5e1;
}
.register{
    align-self:center;
    margin-top: 10px;
    font-size: 13px;
    color:#74c5e1;
}
.button{
  margin-top: 15px;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  display: flex;
  align-items: center;     
  justify-content: center;
  border-radius: 0.7rem;
  border-width: 0;
  width: 90px;
  height: 30px;
}
#login{
  color:#3d5875;
  background-color:#bce3f5;
}
#register{
  margin-top: 20px;
  width: 150px;
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
}
.wrapper{
  display: flex;
  justify-self: center;
  margin-top: 50px;
}
</style>

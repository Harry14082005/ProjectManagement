<script setup>
defineProps({
  text: {
    type: String,
    default:'Xóa'
  },
  type:{
    type: String,
    default:'primary'
  },
  status_text:{
    type: String,
    default:'Public'
  }
})
</script>

<template>
  <div class="card">
    <div class="Title">
        <div class="Title_content">Sprint 1 — FE</div>
        <div class="status">{{ status_text }}</div>
    </div>
    <div class="content">Các task frontend cần hoàn thành trong sprint đầu tiên.</div>
    <div class="RowMember">
    <div class="member_card">
        <div class="avatar_1">A</div>
        <div class="avatar_2">B</div>
        <div class="avatar_2">C</div>
        <div class="memberCount">3 members</div>
    </div>
    </div>
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
  justify-items: left;
  border-radius: 1.25rem;
  border-width: 0;
  width: 350px;
  height: 180px;
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
}
.Title_content{
    display: flex;
    font-size: large;
    align-self:flex-start;
    margin-left: 20px;
    margin-top: 15px;
    font-weight: bold;
}
.content{
    display: flex;
    justify-self:flex-start;
    font-size: large;
    margin-left: 20px;
    margin-right: 20px;
    margin-top: 15px;
    text-align: justify;
}
.status{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-weight: bold;
  font-size: 12px;
  display: flex;
  align-items: center;     
  justify-content: center;
  border-radius: 1.25rem;
  border-width: 0;
  width: 60px;
  height: 25px;
  border: 1.5px solid #74c5e1;
  background-color: #f0f7ff;
  color:#3d5875;
  margin-top: 15px;
}
.Title{
    display: flex;
    justify-content: space-between;
    margin-right:20px;
}
.member_card{
    display: flex;
    margin-top: 10px;
    margin-left: 20px;
}
.avatar_1{
    width: 30px;
    height: 30px;
    border: 1.5px solid #ffffff;
    border-radius: 30px;
    background-color: #74c5e1;
    color:#3d5875;
    text-align: center;
    font-weight: bold;
}
.avatar_2{
    width: 30px;
    height: 30px;
    border: 2px solid #ffffff;
    border-radius: 30px;
    background-color: #74c5e1;
    color:#3d5875;
    margin-left: -5px;
    text-align: center;
    font-weight: bold;
}
.RowMember{
    display: flex;
}
.memberCount{
    margin-top: 2px;
    margin-left: 10px;

}
</style>

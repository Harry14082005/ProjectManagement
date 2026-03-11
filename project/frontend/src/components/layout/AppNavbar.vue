<script setup>
import IconList from '../icons/IconList.vue';
import IconSetting from '../icons/IconSetting.vue';
import IconCreate from '../icons/IconCreate.vue';
const emit = defineEmits(['open-modal']);
</script>

<template>
  <div class="navbar">
    <div class="list_icon">
      <IconList/>
    </div>
    <input class="search" placeholder="Search"></input>
    
    <div class="create" @click="emit('open-modal')">
      <div>Tạo mới</div>
      <IconCreate></IconCreate>
    </div>
    
    <div class="right_navbar">
      <div><i id="icon1" class="fa-solid fa-bell"></i></div>
      <div id="icon2" class="avatar">A</div>
      <div id="icon3"><IconSetting/></div>
    </div>
  </div>
</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
  .list_icon{
    font-size: 30px;
    color:black;
    text-justify: auto;
    align-items: center;
    justify-items: center;
    margin-right: 10px;
  }
  .search{
    border:1px solid grey;
    width: 50vw;
    height: 35px;
    font-size: 20px;
    border-radius: 1.25rem;
    text-align: center;
    justify-self:center;
    align-self: center;
    margin-left: 0;
  }
  .search::placeholder{
    color:#5a5a5a7e;
    text-align: center;
  }
  
  i{
    font-size: 15px;
    color:#2c3e50;
  }
  #icon2{
    background-color: #dddee3;
    border-radius: 20px;
    color:#2c3e50;
    justify-content: center;
    align-content: center;
    text-align: center;
    text-justify: auto;
    font-weight: bold;
  }
  #icon3{
    display: flex;
    color: #2c3e50;
    align-items: center;
    justify-content: center;
  }
  .navbar{
    display: flex;
    width: 100%;
    padding: 0 5px;
    justify-content: space-between;
    font-family: "Quicksand", sans-serif;
  }
  .right_navbar{
    display: flex;
    align-items: center;
    margin-right: 0px;

  }
  .right_navbar div{
    width: 35px;
    height: 35px;
    background-color: #dddee3;
    color:#2c3e50;
    border-radius: 20px;
    justify-content: center;
    align-content: center;
    text-align: center;
    text-justify: auto;
    margin-left: 5px;
    margin-right: 5px;
    align-items: center;
    justify-items: center;
  }
  .create{
    cursor: pointer;
    display: flex;
    gap:5px;
    border:1px solid grey;
    width: 100px;
    height: 35px;
    font-size: 16px;
    border-radius: 1.25rem;
    align-self: center;
    align-content: center;
    justify-content: center;
    padding: 5px;
    color:#2c3e50;
    align-items: center;
    font-weight: 500;
    margin-left: auto;
    margin-right: 10px;
  }
  
</style >
<script setup>
import IconList from '../icons/IconList.vue';
import IconSetting from '../icons/IconSetting.vue';
import IconCreate from '../icons/IconCreate.vue';
const emit = defineEmits(['open-modal'],['toggle-profile']);
</script>

<template>
  <div class="navbar">
    <div class="list_icon">
      <img id="logo" src="../../assets/logo.png"></img>
      <div>Kabala</div>
    </div>
    <!-- <input class="search" placeholder="Search"></input> -->
    
    <div class="create" @click="emit('open-modal')">
      <div>Tạo mới</div>
      <IconCreate></IconCreate>
    </div>
    
    <div class="right_navbar">
      <div><i id="icon1" class="fa-solid fa-bell"></i></div>
      <div id="icon2" class="avatar" @click="emit('toggle-profile')">A</div>
      <div id="icon3"><IconSetting/></div>
    </div>
  </div>
</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
  #logo{
    margin-left: 5px;
    width: 23px;
    height: 23px;
  }
  .list_icon div{
    font-size: 22px;
    margin-left: 7px;
    font-weight: 550;
  }
  .list_icon{
    
    display: flex;
    font-size: 30px;
    color:rgb(255, 255, 255);
    align-content: center;
    align-items: center;
    justify-items: center;
    margin-right: 10px;
  }
  /*.search{
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
  */
  i{
    font-size: 15px;
    color:#2c3e50;
  }
  #icon2{
    background-color: #ffffff;
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
    background-color: #3d5875;
    display: flex;
    width: 100%;
    height: 60px;
    padding: 0 20px;
    justify-content: space-between;
    font-family: "Quicksand", sans-serif;
  }
  .right_navbar{
    display: flex;
    align-items: center;
    margin-right: 0px;
    cursor: pointer;
  }
  .right_navbar div{
    width: 35px;
    height: 35px;
    background-color: #ffffff;
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
  .right_navbar div:active{
    transform: scale(0.95);
  }
  .create{
    cursor: pointer;
    display: flex;
    gap:5px;
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
    background-color: #ffffff;
  }
  .create:active {
  transform: scale(0.95); 
}
</style >
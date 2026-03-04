<script setup>
import IconList from '../icons/IconList.vue'
import IconSetting from '../icons/IconSetting.vue';
</script>

<template>
  <div class="navbar">
    <div class="list_icon">
      <IconList/>
    </div>
    <div class="search"> Search </div>
    <div class="left_navbar">
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
    color:#5a5a5a7e;
    text-align: center;
    justify-self:center;
    align-self: center;
    margin-left: 0;
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
  .left_navbar{
    display: flex;
    align-items: center;
    margin-right: 0px;

  }
  .left_navbar div{
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
</style >
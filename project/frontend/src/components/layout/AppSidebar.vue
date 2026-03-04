<script setup>
import IconList from '../icons/IconList.vue'
import IconSetting from '../icons/IconSetting.vue';
</script>

<template>
  <div class="sidebar">
    <div class="logo">Trello</div>
  </div>

</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.sidebar{
    font-family: "Quicksand", sans-serif;
    width: 30%;
    height: 100vh;
    border: 1px solid rgb(205, 205, 205);
}
div.logo{
    color:#2c3e50;
    border-bottom:0.5px solid rgb(205, 205, 205);;
    padding:10px;
    border-radius: 5px;
}
</style >
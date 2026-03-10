<script setup>
import Navbar from '../layout/AppNavbar.vue'
import Sidebar from '../layout/AppSidebar.vue'
import CreateSpace from './CreateSpace.vue';
import { ref } from 'vue'
const isModalOpen = ref(false);
//Ham mo hop thoai
const openModal = () => {
  isModalOpen.value = true;
}

const closeModal = () => {
  isModalOpen.value = false;
}
</script>

<template>
    <div class="wrapper">
  <Navbar @open-modal="openModal"></Navbar>
    <div class="main-container">
    <Sidebar></Sidebar>
    <div class="maincontent">
        <div class="label_maincontent">
             <div>Đã xem gần đây</div>
        </div>
        <div>CÁC KHÔNG GIAN LÀM VIỆC CỦA BẠN</div>
    </div>
  </div>
      <CreateSpace v-if="isModalOpen" @close-modal="closeModal"></CreateSpace>
    </div>
</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.main-container{
    
    display:grid;
    grid-template-columns: 18% 1fr;
    grid-template-rows: 100px 1fr;
    grid-template-areas:
    "sidebar maincontent";
}
.wrapper{
    font-family: "Quicksand", sans-serif;
    display:flex;
    flex-direction: column;
}
Navbar{
    grid-area: navbar;
}
Sidebar{
    grid-area: sidebar;
}
.maincontent{
    grid-area: maincontent;
    align-self: flex-start;
    margin-left: 30px;
    margin-top: 30px;
}
.maincontent div{
    font-weight: 700;
    font-family: "Quicksand", sans-serif;
}
</style >
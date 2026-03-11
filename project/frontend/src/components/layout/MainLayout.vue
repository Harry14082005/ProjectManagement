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
    <div class="overview_profile">
        <div class="main_inf">
            <div class="avatar">A</div>
            <div class="name_user"></div>
        </div>
        <div>Hồ sơ và hiển thị</div>
        <div>Trợ giúp</div>
        <div>Đăng xuất</div>
    </div>
</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.overview_profile{
    visibility: hidden;
}
.avatar{
    display: flex;
    width: 30%;
    height: 20%;
    background-color: aliceblue;
    align-items: center;
    justify-items: center;
    justify-content: center;
    align-content: center;
    
}
.main-container{
    background-color: #f0f7ff;
    width: 100%;
    height: 100%;
    display:grid;
    grid-template-columns: 18% 1fr;
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
    margin-left: 3%;
    margin-top: 30px;
}
.maincontent div{
    font-weight: 700;
    font-family: "Quicksand", sans-serif;
}
.overview_profile{
    position:absolute;
    color: black;
    top:63px;
    right: 20px;;
    width: 240px;
    height: 35%;
    border-radius: 1.25rem;
    border: 0.5px solid #c9d4e0 ;
    background-color: rgb(255, 255, 255);
}
</style >
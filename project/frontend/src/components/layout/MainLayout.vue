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

const isShowSummaryProfile = ref(false);

const SummaryProfile=()=>{
    isShowSummaryProfile.value= isShowSummaryProfile.value ===false ? true:false;
}

</script>

<template>
    <div class="wrapper">
  <Navbar @open-modal="openModal" @toggle-profile="SummaryProfile"></Navbar>
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
    <div class="overview_profile" v-if="isShowSummaryProfile">
            <div class="avatar">A</div>
            <div class="name_user">User Name</div>
        <div>Hồ sơ và hiển thị</div>
        <div>Trợ giúp</div>
        <div>Đăng xuất</div>
    </div>
</template>
  


<style scoped>

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
    font-family: "Quicksand", sans-serif;
    position:absolute;
    color:#2c3e50;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap:7px;
    top:63px;
    right: 17px;;
    width: 260px;
    height: 240px;
    border-radius: 1.25rem;
    border: 0.5px solid #d4ecf8;
    background-color: rgb(255, 255, 255);
}
.avatar{
    font-weight: 700;
    margin-top: 16px;
    display: flex;
    width: 50px;
    height: 50px;
    background-color: aliceblue;
    align-items: center;
    justify-content: center;
    border-radius: 100%;
    border: 0.5px solid #2c3e50;
}
.name_user{
    font-weight: 700;
    font-size: larger;
    margin-bottom: 10px;
}
</style >
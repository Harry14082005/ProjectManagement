<script setup>
import { ref } from 'vue'
import Navbar from '../layout/AppNavbar.vue'
import Sidebar from '../layout/AppSidebar.vue'
</script>

<template>
    <div class="wrapper">
  <Navbar></Navbar>
    <body>
    <Sidebar></Sidebar>
    <div class="maincontent">
        <div class="label_maincontent">
             <div>Đã xem gần đây</div>
        </div>
        <div>CÁC KHÔNG GIAN LÀM VIỆC CỦA BẠN</div>
    </div>
      </body>
    </div>
</template>
  


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
body{
    
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
</style >
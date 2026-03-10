<script setup>
import IconList from '../icons/IconList.vue'
import IconSetting from '../icons/IconSetting.vue'
import IconBoard from '../icons/IconBoard.vue'
import IconHome from '../icons/IconHome.vue'
import IconMember from '../icons/IconMember.vue'
import IconGears from '../icons/IconGears.vue'
import { ref } from 'vue'
const isShow = ref(false)
</script>

<template>
  <div class="sidebar">
  
  <div class="Sidebar_items">
    <IconBoard></IconBoard>
    <div>Bảng</div>
  </div> 
  <div class="Sidebar_items">
    <IconHome></IconHome>
    <div>Trang chủ</div>
  </div>
  <div class="separator"></div>
  <div>Không gian làm việc</div>
  <div class="space" @click="isShow = !isShow">
    <div class="space_avatar">T</div>
    <div>Tên không gian làm việc</div>
  </div>
  <div v-if="isShow" class="DetailOptionSpace">
      <div>
        <IconBoard></IconBoard>
        <div>Bảng</div>
      </div>
      <div>
        <IconMember></IconMember>
        <div>Thành viên</div>
      </div>
      <div>
        <IconGears></IconGears>
        <div>Cài đặt</div>
      </div>
    </div>
  </div> 
</template>
  


<style scoped>
.icon_setting{
  width: 10px;
  height: 10px;
}
.DetailOptionSpace {
  margin-left:30px;
  padding: 10px;
  font-size: 17px;
}
.DetailOptionSpace div{
  margin-top: 2px;
  display: flex;
  align-items: center;
  align-content: center;
  flex-direction: row;
  gap: 7px;
}
.DetailOptionSpace div div{ 
  margin-left: 10px;
  
} 
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.sidebar{
    display: flex;
    flex-direction: column;
    font-family: "Quicksand", sans-serif;
    width: 100%;
    height: 100vh;
    margin-top:30px;
    gap: 10px;
    font-size: 17px;
    cursor:pointer;
}
div.Sidebar_items{
  display: flex;
  align-items: center;
  flex-direction: row;
  color:#2c3e50;

}
.Sidebar_items div{
  align-self:center;
  margin-left: 6%;
  font-size: 18px;
  font-weight: 400;
  

}
.separator{
  width: 100%;
  height:1px;
  background-color: grey;
}
.space{
  display: flex;
  flex-direction: row;
  gap:10px;
  align-items: center;
  font-size: 15px;
}
.space_avatar{
  width: 30px;
  height: 30px;
  background-color:#74c5e1;
  border-radius: 5px;
  color:rgb(255, 255, 255);
  font-weight: bold;
  text-align: center;
  align-content: center;
  justify-content: center;
}
</style >
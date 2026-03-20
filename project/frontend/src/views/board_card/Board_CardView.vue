<script setup>
import { ref,onMounted,watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import MainLayout from '@/components/layout/MainLayout.vue'
import Button from '@/components/base/BaseButton.vue'
import Card from '@/components/base/BaseCard.vue'
import Task from '@/components/base/BaseTask.vue'
import ModalCreateCard from '@/components/layout/CreateCard.vue' 

const route=useRoute();
const isLoading = ref(true);

const boardData = ref(null);

const cards=ref([]);

const isModalOpen = ref(false);

const refreshListAfterCreate = () => {
  const spaceId = route.params.idSpace;
  const boardId = route.params.idBoard;
  fetchBoardCard(spaceId, boardId);
}

const fetchBoardCard = async (SpaceId, BoardId) => {
  isLoading.value = true;
  try {
    const token = localStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${token}` };

    const BoardRes = await axios.get(`http://localhost:8080/api/boards/${BoardId}`, { headers });
    boardData.value = BoardRes.data.data || BoardRes.data;

    const CardRes = await axios.get(`http://localhost:8080/api/boards/${BoardId}/cards`, { headers });
    cards.value = CardRes.data.data || CardRes.data;
    
  } catch (error) {
    console.error("Lỗi khi lấy dữ liệu thẻ:", error);
    cards.value = [];
  } finally {
    isLoading.value = false;
  }
}

onMounted(() => {
  const spaceId = route.params.idSpace; 
  const boardId = route.params.idBoard;
  
  if (spaceId && boardId) {
    fetchBoardCard(spaceId, boardId); 
  }
});

watch(
  () => route.params, 
  (newParams) => {
    if (newParams.idSpace && newParams.idBoard) {
      fetchBoardCard(newParams.idSpace, newParams.idBoard);
    }
  },
  { deep: true }
);
</script>
<template>
  <MainLayout>
    <div class="NameSpace">
        <div>{{ boardData?.name || 'Đang tải...' }}</div>
    </div>
      <Button 
          :text="'+ Thêm danh sách mới'"
          :type="'ghost'"
          @click="isModalOpen = true">
      </Button>
        <div class="border_card">
          <Card v-for="card in cards" 
                :key="card.id"
                :name_card="card.name"
          >
            <Task></Task>
          </Card>
          </div>
        <ModalCreateCard 
      v-if="isModalOpen"
      :boardId="route.params.idBoard"
      @close="isModalOpen = false"
      @created="refreshListAfterCreate"
    />
  </MainLayout>
</template>
<style scoped> 
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.border_card{
  border-radius: 1.25rem;
  border: 1px solid #bce3f5;
  width: 77.5vw;
  height: 70vh;
  margin-top: 20px;
  margin-right: 50px;
  display: flex;
  overflow-x: auto;
  gap:30px;
  background-color: #ffffff;
  padding:25px 25px;
}
.NameSpace div{
  font-family:"Quicksand", sans-serif; 
  font-size: 25px;
  font-weight:600;
  margin-bottom: 5px;
}
/* Tùy chỉnh thanh cuộn (Scrollbar) cho khung danh sách */

/* 1. Kích thước tổng thể của thanh cuộn ngang */
.border_card::-webkit-scrollbar {
  height: 14px; 
}

/* 2. Phần rãnh trượt (Track) */
.border_card::-webkit-scrollbar-track {
  background: transparent; /* Để trong suốt cho đẹp */
  /* Margin giúp rãnh trượt thụt vào ở 2 đầu, không đè lên góc bo tròn của viền */
  margin: 0 20px; 
}

/* 3. Cục lăn (Thumb) */
.border_card::-webkit-scrollbar-thumb {
  background-color: #bce3f5; /* Màu đồng điệu với viền của bạn */
  border-radius: 20px; /* Bo tròn cục lăn */
  /* Dùng viền trắng xung quanh cục lăn để tạo cảm giác nó đang "lơ lửng" cách xa mép dưới */
  border: 4px solid #ffffff; 
}

/* 4. Hiệu ứng khi di chuột vào cục lăn */
.border_card::-webkit-scrollbar-thumb:hover {
  background-color: #8bbcd6; 
}
</style>

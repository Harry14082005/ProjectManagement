<script setup>
import { ref,onMounted,watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import MainLayout from '@/components/layout/MainLayout.vue'
import Card from '@/components/base/BaseBoard.vue'
import Button from '@/components/base/BaseButton.vue'

const route=useRoute();
const isLoading = ref(true);

const boardData = ref(null);

const cards=ref([]);

const fetchBoardCard = async (SpaceId, BoardId) => {
  isLoading.value = true;
  try {
    const token = localStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${token}` };

    // SỬA DÒNG NÀY: Xóa chữ spaces/${SpaceId} đi cho khớp với Backend
    const BoardRes = await axios.get(`http://localhost:8080/api/boards/${BoardId}`, { headers });
    boardData.value = BoardRes.data.data || BoardRes.data;

    // LƯU Ý DÒNG NÀY: Mình không thấy CardController của bạn, 
    // nên nếu API lấy danh sách Thẻ bên Java cũng KHÔNG CÓ chữ spaces, 
    // thì bạn cũng phải sửa nó thành `/api/boards/${BoardId}/cards` luôn nhé!
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
          :text="'+ Thêm thẻ mới'"
          :type="'ghost'">
      </Button>
        <div class="border_card">
        <Card
          v-for="card in cards"
          :key="card.id"
          :title="card.name"></Card>
          </div>
  </MainLayout>
</template>
<style scoped> 
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.border_card{
  width: 96%;
  margin-right: 50px;
  display: grid;
  gap:20px 50px;
  grid-template-columns: repeat(3, 1fr);;
}
.NameSpace div{
  font-family:"Quicksand", sans-serif; 
  font-size: 25px;
  font-weight:600;
  margin-bottom: 5px;
}
</style>

<script setup>
import { ref,onMounted,watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import draggable from 'vuedraggable'

import MainLayout from '@/components/layout/MainLayout.vue'
import Button from '@/components/base/BaseButton.vue'
import Card from '@/components/base/BaseCard.vue'
import Task from '@/components/base/BaseTask.vue'
import ModalCreateCard from '@/components/layout/CreateCard.vue' 
import TaskInfo from '@/components/layout/TaskInfo.vue'

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

    // 1. Lấy thông tin Board
    const BoardRes = await axios.get(`http://localhost:8080/api/boards/${BoardId}`, { headers });
    boardData.value = BoardRes.data.data || BoardRes.data;

    // 2. Lấy danh sách Cards
    const CardRes = await axios.get(`http://localhost:8080/api/boards/${BoardId}/cards`, { headers });
    let tempCards = CardRes.data.data || CardRes.data;

    // 3. CHIÊU ĐỘC: Lặp qua từng Card để lấy Task tương ứng
    // Dùng Promise.all để lấy tất cả Task của các Card cùng lúc cho nhanh
    const cardsWithTasks = await Promise.all(tempCards.map(async (card) => {
      try {
        // Gọi API lấy task của từng card (Giả sử bạn có endpoint này)
        const taskRes = await axios.get(`http://localhost:8080/api/cards/${card.id}/tasks`, { headers });
        
        // Trả về card có kèm thêm mảng tasks vừa lấy được
        return {
          ...card,
          tasks: taskRes.data.data || taskRes.data // Nhét task vào đây
        };
      } catch (err) {
        console.error(`Không lấy được task cho card ${card.id}`, err);
        return { ...card, tasks: [] }; // Lỗi thì cho mảng rỗng
      }
    }));

    // 4. Cập nhật lại biến cards xịn sò đã có đủ Task
    cards.value = cardsWithTasks;

  } catch (error) {
    console.error("Lỗi tổng thể:", error);
    cards.value = [];
  } finally {
    isLoading.value = false;
  }
}

const handleCreateTask = async (cardId, taskName) => {
  try {
    const token = localStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${token}` };

    const payload = {
      name: taskName,
      is_completed: false 
    };

    await axios.post(`http://localhost:8080/api/cards/${cardId}/tasks`, payload, { headers });

    console.log(`Đã thêm thành công thẻ: ${taskName} vào danh sách ${cardId}`);

    const spaceId = route.params.idSpace;
    const boardId = route.params.idBoard;
    fetchBoardCard(spaceId, boardId);

  } catch (error) {
    console.error("Lỗi khi gọi API thêm thẻ:", error);
  }
};

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
          <draggable 
         v-model="cards" 
          item-key="id" 
          class="border_card" 
          animation="200"
          handle=".drag-handle-card" 
          ghost-class="ghost-card"
        >
      <template #item="{ element: card }">
        <div class="drag-handle-card">
          <Card :key="card.id"
                :cardId="card.id"
                :name_card="card.name"
                @add-new-task="handleCreateTask"
          >
            <draggable 
            v-model="card.tasks" 
            item-key="id"
            group="tasks" 
            animation="200"
            ghost-class="ghost-task"
            class="task-list-container" 
          >
            <template #item="{ element: task }">
            <Task
               :key="task.id"
               :task_name="task.name">
            </Task>
            </template>
            </draggable>
          </Card>
          </div>
      </template>
      </draggable>
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
  cursor: pointer;
}
.NameSpace div{
  font-family:"Quicksand", sans-serif; 
  font-size: 25px;
  font-weight:600;
  margin-bottom: 5px;
}

.border_card::-webkit-scrollbar {
  height: 14px; 
}

.border_card::-webkit-scrollbar-track {
  background: transparent; 
  margin: 0 20px; 
}

.border_card::-webkit-scrollbar-thumb {
  background-color: #bce3f5; 
  border-radius: 20px; 
  border: 4px solid #ffffff; 
}

.border_card::-webkit-scrollbar-thumb:hover {
  background-color: #8bbcd6; 
}

/* Phần css cho hiệu ứng drag */
.task-list-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap:5px;
}
.ghost-card {
  opacity: 0.9;
}

.ghost-task {
  opacity: 0.9;
}

.drag-handle-card {
  cursor: grab;
}
.drag-handle-card:active {
  cursor: grabbing;
}
</style>

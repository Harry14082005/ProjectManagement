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

const isDetailTaskOpen=ref(false);
const selectedTask=ref(null);
const OpenDetailTask=(task)=>{
  selectedTask.value=task;
  isDetailTaskOpen.value=true;
}

const handleTaskChange = async (event, targetCardId) => {

  const action = event.added || event.moved;
  
  if (action) {
    const task = action.element; // Dữ liệu của task vừa kéo
    const newPosition = action.newIndex; // Vị trí mới 

    try {
      const token = localStorage.getItem('token');
      // Gọi API
      await axios.put(`http://localhost:8080/api/tasks/${task.id}/move`, {
        cardId: targetCardId, // ID của cột hiện tại
        position: newPosition // Vị trí mới
      }, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      console.log(`Đã lưu Task "${task.name}" vào vị trí ${newPosition} của cột ${targetCardId}`);
    } catch (error) {
      console.error("Lỗi cập nhật vị trí Task:", error);
    }
  }
};


const handleCardChange = async (event) => {
  if (event.moved) {
    const card = event.moved.element; 
    const newPosition = event.moved.newIndex;

    console.log(newPosition);

    try {
      const token = localStorage.getItem('token');
      await axios.put(`http://localhost:8080/api/cards/${card.id}/move`, {
        position: newPosition 
      }, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      console.log(`Đã chuyển Cột "${card.title}" sang vị trí ${newPosition}`);
    } catch (error) {
      console.error("Lỗi cập nhật vị trí Card:", error);
    }
  }
};

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
    let tempCards = CardRes.data.data || CardRes.data;

    // Dùng Promise.all để lấy tất cả Task của các Card cùng lúc 
    const cardsWithTasks = await Promise.all(tempCards.map(async (card) => {
      try {
        // Gọi API lấy task của từng card 
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

    //Cập nhật lại biến cards đã có đủ Task
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

const handleDeleteCard=async(cardId)=>{
  try{
    const token=localStorage.getItem('token');
    await axios.delete(`http://localhost:8080/api/cards/${cardId}`, {
      headers: { 'Authorization': `Bearer ${token}` }
    });
    
    console.log(`Đã xóa thành công danh sách có ID: ${cardId}`);
    const spaceId = route.params.idSpace;
    const boardId = route.params.idBoard;
    fetchBoardCard(spaceId, boardId);
    
  } catch (error) {
    console.error("Lỗi khi xóa danh sách (Card):", error);
    alert("Không thể xóa danh sách này, vui lòng thử lại!");
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
          <draggable 
         v-model="cards" 
          item-key="id" 
          class="border_card" 
          animation="200"
          handle=".drag-handle-card" 
          ghost-class="ghost-card"
          group="cards"
          @change="handleCardChange"
        >
      <template #item="{ element: card }">
        <div class="drag-handle-card">
          <Card :key="card.id"
                :cardId="card.id"
                :name_card="card.name"
                @add-new-task="handleCreateTask"
                @delete-card="handleDeleteCard"
          >
            <draggable 
            v-model="card.tasks" 
            item-key="id"
            group="tasks" 
            animation="200"
            ghost-class="ghost-task"
            class="task-list-container" 
            @change="handleTaskChange($event, card.id)"
          >
            <template #item="{ element: task }">
            <Task
               :key="task.id"
               :task_name="task.name"
               @click="OpenDetailTask(task)">
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
    <TaskInfo v-if="isDetailTaskOpen"
      :task_name="selectedTask?.name"
      @close="isDetailTaskOpen=false">
    </TaskInfo>
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

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import IconUser from '../icons/IconUser.vue';
import BaseComment from '../base/BaseComment.vue';
const emit = defineEmits(['close'])
const close_detail_task =()=>{
  emit('close');
}
defineProps({
  task_name:{
    type:String,
    default:'Task name'
  }
})
</script>

<template>
  <Teleport to="body">
    <div class="modal-overlay" @click.self="close_detail_task">
      <div class="modal">
         <div class="label_task">{{ task_name }}</div>
        <div class="modal-content">
        
        <div class="left-modal">
        <div
          class="modal-assign"
          @keyup.enter="handleCreateCard" 
          autofocus>
          Thêm thành viên  +
        </div>
        <div class="wrapper-deadline">
          <div>Hạn chót</div>
          <input class="Deadline" type="date"/>
        </div>
        </div>
        <div class="right-modal">
        <div class="comment_nav">
          <input type="text" placeholder="Viết bình luận...">
        </div>
        <BaseComment></BaseComment>
        </div>
        </div>
        
        <div class="modal-actions">
          <button class="btn-cancel" @click="close_detail_task">Hủy</button>
          <button class="btn-submit">
            {{ isCreating ? 'Đang tạo...' : 'Lưu thẻ' }}
          </button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
.label_task{
  font-size: 25px;
  font-weight: 500;
  margin-left: 5px;
}
.left-modal{
  padding: 16px 0px;
  width: 25%;
  display: flex;
  flex-direction: column;
  justify-items: center;
}
.comment_nav{
  padding: 5px 4%;
  width: 100%;
  display: flex;
  gap: 10px;
  align-content: center;
  justify-content: center;
  margin-top: 10px;
}

.comment_nav input{
  font-family: "Quicksand", sans-serif;;
  padding: 15px;
  border-radius: 1.25rem;
  border:0px;
  width: 100%;
  height: 40px;
  display: flex;
}
.comment_nav input:focus{
  border: 1px solid #a0d8f1;
  outline: none;
}
.modal-content{
  display: flex;
  gap:25px;
  flex-direction: row;
}
.right-modal{
  display: flex;
  flex-direction: column;
  width: 70%;
  height: fit-content;
  min-height: 130px;
  border-radius: 1.25rem;
  background-color: #e8f4fa;
  margin-bottom: 30px;
}
.wrapper-deadline{
  display: flex;
  flex-direction: column;
  gap:2px;
  
}
.wrapper-deadline div{
  font-weight:500;
  margin-left: 10px;
}
.Deadline{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 14px;
  display: flex;   
  justify-content: center;
  text-align: center;
  border-radius: 1.25rem;
  width: 100%;
  height: 35px;
  outline: none;
  padding: 4px;
  border: 1px solid #ccc;
  color:inherit;
}
.Deadline:hover{
  cursor: pointer;
  border-color: #74c5e1;
}
/* Di chuyển toàn bộ CSS của Modal từ file cũ sang đây */
.modal-overlay {
  position: fixed; top: 0; left: 0; width: 100vw; height: 100vh;
  background: rgba(0, 0, 0, 0.4); display: flex;
  justify-content: center; align-items: center; z-index: 1000;
}
.modal {
  background: white; padding: 24px; border-radius: 1.25rem;
  width: 750px; max-width: 90%; box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  font-family: "Quicksand", sans-serif;
}
.modal h2 { margin-top: 0; margin-bottom: 16px; font-size: 20px; }
.modal-assign {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%; 
  padding: 10px 12px; border: 1px solid #ccc;
  border-radius: 1.25rem; font-size: 15px; margin-bottom: 5px;
  box-sizing: border-box; font-family: inherit;
  height:35px;
}
.modal-assign:hover {
  cursor: pointer;
  outline: none; border-color: #74c5e1;
}
.modal-actions { display: flex; justify-content: flex-end; gap: 12px; }
.btn-cancel, .btn-submit {
  padding: 8px 16px; border-radius: 1.25rem; cursor: pointer;
  font-weight: 600; font-family: inherit; border: none;
}
.btn-cancel { background: #f1f1f1; color: #333; }
.btn-cancel:hover { background: #e1e1e1; }
.btn-submit { 
  margin-right: 10px;
  background: #0079bf;; color: white; }
.btn-submit:hover { background: #00659f;; }
.btn-submit:disabled, .btn-cancel:disabled { opacity: 0.6; cursor: not-allowed; }
</style>
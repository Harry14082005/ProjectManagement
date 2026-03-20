<script setup>
import IconAdd from '../icons/IconAdd.vue';
defineProps({
  name_card:{
    type:String,
    default:'Tên Card'
  }
})
</script>
<template>
  <div class="card-wrapper">
    <div class="card_name">{{ name_card }}</div>
    <slot></slot>
    <div class="add_task">
      <IconAdd></IconAdd>
        <div>Thêm thẻ mới</div>
  </div>
  </div>
</template>


<style scoped>
.add_task{
  color:#1a5270;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  margin-right: auto;
  margin-top: 5px;
  margin-left:10px;
  padding:0 4px;
  cursor: pointer;
  padding: 3px 16px 3px 10px;
  transition: all 0.2s ease;
  border-radius: 1.25rem;
}
.add_task:hover{
  background-color: #1a5270;
  color:#ffffff;
}
.add_task:active{
  transform: scale(0.95);
}
.card_name{
    font-size: 17px;
    color:#1a5270;
    font-weight: 500;
    text-align: center;
    margin-top: 10px;
    margin-right: auto;
    padding-left:13px
}
.card-wrapper{
    display: flex;
    flex-shrink: 0;
    gap:5px;
    flex-direction: column;
    align-items: center;
    justify-items: center;
    border-radius: 1.25rem;
    width: 260px;
    height: fit-content;
    padding-bottom: 8px;
    background-color: #d4ecf8;
}


</style>
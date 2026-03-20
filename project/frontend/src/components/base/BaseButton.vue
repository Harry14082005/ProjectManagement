<script setup>
defineProps({
  text: {
    type: String,
    default:'Xóa'
  },
  type:{
    type: String,
    default:'primary'
  }
})
</script>

<template>
  <div :class="['btn',`btn-${type}`]">
    {{ text }}
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.btn{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  display: flex;
  align-items: center;     
  justify-content: center;
  border-radius: 1.25rem;
  border-width: 0;
  width: fit-content;
  padding: 0 15px;
  height: 35px;
}

.btn-primary{
  color:#2f4562;
  background-color:#a0d8f1;
}

.btn-secondary{
  color:#3d5875;
  background-color:#bce3f5;
}
.btn-ghost{
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
}
.btn-ghost:hover{
  cursor: pointer;
}
.btn-ghost:active{
  transform: scale(0.95);
}
.btn-delete{
  color:#7d1f3a;
  background-color:#d97998;
}
</style>

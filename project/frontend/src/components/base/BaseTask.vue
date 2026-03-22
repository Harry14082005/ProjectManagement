<script setup>
import IconEdit from '../icons/IconEdit.vue';
const props=defineProps({
    task_name:{
        type:String,
        default:'Task Name'
    },
    deadline:{
        type:String,
        default:''
    }
})
</script>
<template>
    <div class="task">
        <input type="checkbox" id="checkbox">
        <label for="checkbox">{{ task_name }}</label>
        <div class="deadline">{{ deadline }}</div>
        <IconEdit class="edit_task"></IconEdit>
    </div>
</template>
<style scoped>
.edit_task{
    display: none;
}
.task:hover .edit_task{
    display: block;
    transition: all 0.2s ease;
}
label{
    font-size: 14px;
    color: #1a5270;
    line-height: 1; 
    display: inline-block;
}
.deadline{
    display: flex;
    font-size: 10px;
    align-items: center;
    justify-content: center;
    margin-left: auto;
    line-height: 1;
}
.task{
    color: #1a5270;
    display: flex;
    align-items: center;
    justify-items: center;
    align-content: center;
    gap:10px;
    width: 93%;
    background-color: #ffffff;
    border-radius: 1.25rem;
    height: 30px;
    padding: 10px;
    margin-bottom: 5px;
    cursor: pointer;
}
#checkbox{
    align-self: center;
    border-radius: 1.25rem;
}
</style>
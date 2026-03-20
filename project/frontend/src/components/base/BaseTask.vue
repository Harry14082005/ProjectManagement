<script>

</script>
<template>
    <div class="task">
        <input type="checkbox" id="checkbox">
        <label for="checkbox">Task name</label>
        <div class="deadline">25/10</div>
    </div>
</template>
<style scoped>
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
}
#checkbox{
    align-self: center;
    border-radius: 1.25rem;
}
</style>
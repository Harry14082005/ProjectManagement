<script setup>
import { ref,onMounted,watch } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import MainLayout from '@/components/layout/MainLayout.vue'
import SearchName from '@/components/base/BaseInput.vue'
import Button from '@/components/base/BaseButton.vue'
import MemberInfo from '@/components/base/MemberInfo.vue'
const route=useRoute();
const isLoading = ref(true);

const spaceData = ref(null);

const fetchSpaceMember= async (SpaceId)=>{
  isLoading.value=true;
  try{
  const token=localStorage.getItem('token');
  const headers={'Authorization':`Bearer ${token}`};

  const SpaceRes= await axios.get(`http://localhost:8080/api/spaces/${SpaceId}`,{headers});
  spaceData.value=SpaceRes.data.data || SpaceRes.data;

  /*const SpaceRes= await axios.get(`http://localhost:8080/api/spaces/${SpaceId}/members`,{headers});
  spaceData.value=SpaceRes.data.data || SpaceRes.data;
  }
  */
  }
  catch(error){
    console.error("Lỗi khi lấy dữ liệu",error);
  }
  finally{
    isLoading.value=false;
  }
}

onMounted(()=>{
 if(route.params.id) 
 fetchSpaceMember(route.params.id)
});

watch(
  ()=>route.params.id,
  (newId)=>{
    if(newId) fetchSpaceMember(newId)
  }
)
</script>
<template>
  <MainLayout>
    <div class="NameSpace">
        <div>{{ spaceData?.name || 'Đang tải...' }}</div>
    </div>
    <div class="container">
    <div class="AddMember">
    <SearchName
    :placeholder="'Nhập tên người dùng muốn thêm vào Space'"></SearchName>
    <Button
        :type="'secondary'"
        :text="'Thêm'">
    </Button>
    </div>
    <div class="membersInSpace">
    </div>
    </div>
  </MainLayout>
</template>
<style scoped> 
@import url('https://fonts.googleapis.com/css2?family=Google+Sans+Flex:opsz,wght@6..144,1..1000&family=Quicksand:wght@300..700&display=swap');
.NameSpace div{
  font-family:"Quicksand", sans-serif; 
  font-size: 25px;
  font-weight:600;
  margin-bottom: 5px;
}
.container{
    margin-top:20px;
    display: flex;
    justify-self:center;
    flex-direction: column;
    gap:10px;
    width: 80%;
    align-items: center;
    justify-content: center;
}
.AddMember{
    display: flex;
    align-items: center;
    justify-content: center;
    gap:10px;
    width: 100%;
}
.membersInSpace{
    display: flex;
    margin-top: 50px;
    border-radius: 1.25rem;
    width: 750px;
    height: 400px;
    background-color: white;
    border: 1px solid #d4ecf8;
}
</style>

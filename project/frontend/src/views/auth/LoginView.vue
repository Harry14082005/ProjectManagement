<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import LoginLayout from '@/components/layout/LoginLayout.vue'

const router = useRouter()

// 1. Khai báo biến lưu trữ dữ liệu
const form = reactive({
  username: '',
  password: ''
})

const errorMessage = ref('')
const isLoading = ref(false) // Thêm biến này để làm hiệu ứng loading cho nút

// 2. Hàm xử lý gửi dữ liệu sang Java
const handleLogin = async () => {
  // Xóa lỗi cũ và bật trạng thái loading
  errorMessage.value = ''
  
  if (!form.username || !form.password) {
    errorMessage.value = "Vui lòng nhập đầy đủ thông tin!"
    return
  }

  isLoading.value = true

  try {
    // Gọi Axios gửi dữ liệu (Nhớ đảm bảo cổng 8080 đúng với server Java của bạn)
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      username: form.username,
      password: form.password
    })

    console.log("Đăng nhập thành công:", response.data)
    
    // Lưu token vào bộ nhớ trình duyệt
    if (response.data.token) {
      localStorage.setItem('token', response.data.token)
    }

    // Chuyển hướng về trang chủ
    router.push('/')

  } catch (error) {
    console.error("Lỗi từ Axios:", error)
    
    // Xử lý thông báo lỗi cho người dùng
    if (error.response) {
      if (error.response.status === 401) {
        errorMessage.value = "Sai tên đăng nhập hoặc mật khẩu!"
      } else {
        errorMessage.value = `Lỗi Server: ${error.response.status}`
      }
    } else {
      errorMessage.value = "Không thể kết nối đến Backend. Hãy kiểm tra lại Server Java!"
    }
  } finally {
    // Tắt trạng thái loading dù thành công hay thất bại
    isLoading.value = false
  }
}

// 3. Hàm chuyển trang đăng ký
const goToRegister = () => {
  router.push('/register')
}

</script>
<template>
  <LoginLayout>
     <form @submit.prevent="handleLogin">
  <div class="card">
        <div class="Title_content">Đăng nhập</div>
        <div class="label">Username</div>
        <input v-model="form.username"class="input">
        <div class="label">Password</div>
        <input v-model="form.password" class="input" type="password">
        <div class="forgot_pw">Forgot your password</div>
        <button class="button" id="login" type="submit">Đăng nhập</button>
        <button class="button" id="register" @click="goToRegister" type="button">Tạo tài khoản mới</button>
  </div>
  </form>
  </LoginLayout>
</template>
<style scoped> 
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
.card{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  display: flex;
  flex-direction: column;    
  border-radius: 1.25rem;
  border-width: 0;
  width: 300px;
  height: 350px;
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
  align-items: center;
  align-self: center;
}
.Title_content{
    font-size: large;
    margin-top: 15px;
    font-weight: bold;
}
.input{
  font-family:"Quicksand", sans-serif;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  border-radius: 1.25rem;
  width: 250px;
  height: 40px;
  outline: none;
  padding: 4px;
  border: 2px solid #d4ecf8;
  color:#2f4562;
  background-color:#f0f7ff;
  margin-top: 5px;
  text-align: center;
}
.label{
    align-self: flex-start;
    margin-top:15px;
    margin-left:45px;
}
.input:focus{
  background-color:white;
  border: 2px solid #d4ecf8;
}
.forgot_pw{
    align-self: flex-start;
    margin-top: 10px;
    font-size: 13px;
    margin-left:45px;
    color:#74c5e1;
}
.register{
    align-self:center;
    margin-top: 10px;
    font-size: 13px;
    color:#74c5e1;
}
.button{
  margin-top: 10px;
  font-optical-sizing: auto;
  font-style:normal;
  font-size: 15px;
  display: flex;
  align-items: center;     
  justify-content: center;
  border-radius: 1.25rem;
  border-width: 0;
  width: 90px;
  height: 30px;
}
#login{
  color:#3d5875;
  background-color:#bce3f5;
}
#register{
  margin-top: 20px;
  width: 150px;
  border: 1px solid #bce3f5;
  background-color: white;
  color:#2f4562;
}
</style>

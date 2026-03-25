<script setup>
import Navbar from '../layout/AppNavbar.vue'
import Sidebar from '../layout/AppSidebar.vue'
import CreateSpace from './CreateSpace.vue';
import NotificationCard from '../base/NotificationCard.vue';
import ProfileSetting from './ProfileSetting.vue';
import { onMounted, onUnmounted, ref, computed, watch } from 'vue'
import api from '@/services/api'
import { useRouter } from 'vue-router'
import { notificationStore } from '@/stores/notificationStore.js';

const router = useRouter();
const username = ref('');

const isModalOpen = ref(false);
const openModal = () => isModalOpen.value = true;
const closeModal = () => isModalOpen.value = false;

let isToggling = false;
const isShowSummaryProfile = ref(false);
const profileRef = ref(null);

const SummaryProfile = () => {
  isToggling = true;
  isShowSummaryProfile.value = !isShowSummaryProfile.value;
  if (isShowSummaryProfile.value) isShowNotification.value = false;
  setTimeout(() => { isToggling = false; }, 0);
}

const isProfileSettingOpen = ref(false);
const openProfileSetting = () => {
  isProfileSettingOpen.value = true;
  isShowSummaryProfile.value = false;
}

const isShowNotification = ref(false);
const notificationRef = ref(null);

const toggleNotification = () => {
  isToggling = true;
  isShowNotification.value = !isShowNotification.value;
  if (isShowNotification.value) isShowSummaryProfile.value = false;
  setTimeout(() => { isToggling = false; }, 0);
}

const handleClickOutside = (event) => {
  if (isToggling) return;
  if (isShowNotification.value && notificationRef.value && !notificationRef.value.contains(event.target)) {
    isShowNotification.value = false;
  }
  if (isShowSummaryProfile.value && profileRef.value && !profileRef.value.contains(event.target)) {
    isShowSummaryProfile.value = false;
  }
}

const fetchUserProfile = async () => {
  try {
    const response = await api.get("/users/profile");
    username.value = response.data.data.name;
  }
  catch (error) {
    if (error.response && error.response.status === 401) {
      router.push('/');
    }
    username.value = 'Guest';
  }
}

const notifications = computed(() => notificationStore.notifications);

const hasUnreadNotifications = computed(() => {
  return notificationStore.notifications.some(notify => !notify.readStatus);
});

const fetchNotifications = async () => {
  try {
    const response = await api.get("/notifications");
    const rawData = Array.isArray(response.data) ? response.data : (response.data.data || []);
    notificationStore.setNotifications(rawData);
  } catch (error) {
    console.error("Lỗi khi lấy thông báo", error);
  }
}

let sseController = null;

const connectSSE = async () => {
  const token = localStorage.getItem('token');
  if (!token) return;

  // Dùng AbortController để có thể huỷ kết nối
  sseController = new AbortController();

  try {
    const response = await fetch('http://localhost:8080/api/notifications/subscribe', {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Accept': 'text/event-stream',
      },
      signal: sseController.signal,
    });

    if (!response.ok) {
      console.error('SSE connection failed:', response.status);
      // Tự động reconnect sau 5 giây
      setTimeout(() => connectSSE(), 5000);
      return;
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';

    while (true) {
      const { done, value } = await reader.read();
      if (done) break;

      buffer += decoder.decode(value, { stream: true });

      // Xử lý từng dòng SSE (format: "data:...\n\n")
      const lines = buffer.split('\n');
      buffer = lines.pop() || ''; // Giữ lại phần chưa hoàn chỉnh

      for (const line of lines) {
        if (line.startsWith('data:')) {
          const jsonStr = line.slice(5).trim();
          if (!jsonStr) continue;
          try {
            const data = JSON.parse(jsonStr);
            // Nếu code là 2000 thì không hiển thị thông báo
            if (data.code === 2000) continue;
            // Thêm notification mới vào store
            if (data.data) {
              notificationStore.addNotification(data.data);
            } else if (data.id) {
              notificationStore.addNotification(data);
            }
          } catch (e) {
            console.error('Lỗi parse SSE message:', e);
          }
        }
      }
    }
  } catch (error) {
    if (error.name === 'AbortError') return; // Bị huỷ chủ động
    console.error('SSE connection error:', error);
    // Tự động reconnect sau 5 giây
    setTimeout(() => connectSSE(), 5000);
  }
};

onMounted(() => {
  fetchUserProfile();
  fetchNotifications();
  connectSSE();
  document.addEventListener('click', handleClickOutside);
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
  if (sseController) {
    sseController.abort();
    sseController = null;
  }
})

const handleLogOut = () => {
  const isConfirm = window.confirm("Bạn có chắc chắn muốn đăng xuất không?");
  if (isConfirm) {
    localStorage.removeItem('token');
    router.push('/');
  }
}
</script>

<template>
    <div class="wrapper">
  <Navbar :hasUnread="hasUnreadNotifications" @open-modal="openModal" @toggle-profile="SummaryProfile" @toggle-notification="toggleNotification"></Navbar>
    <div class="main-container">
    <Sidebar></Sidebar>
    <div class="maincontent">
        <slot></slot>
    </div>
  </div>
      <CreateSpace v-if="isModalOpen" @close-modal="closeModal"></CreateSpace>
      <ProfileSetting v-if="isProfileSettingOpen" @close="isProfileSettingOpen = false"></ProfileSetting>
    </div>
    <div class="overview_profile" v-if="isShowSummaryProfile" ref="profileRef">
            <div class="avatar">{{username.charAt(0).toUpperCase()}}</div>
            <div class="name_user">{{ username }}</div>
        <div @click="openProfileSetting">Hồ sơ và hiển thị</div>
        <!--<div>Trợ giúp</div>-->
        <div @click="handleLogOut">Đăng xuất</div>
    </div>
    <div class="notifications" v-if="isShowNotification" ref="notificationRef">
        <div class="notifications-header">Thông báo</div>
        <div class="notifications-list">
            <NotificationCard 
                v-for="notify in notifications" 
                :key="notify.id" 
                :notification="notify" 
                @marked-read="(id) => notificationStore.markAsRead(id)"
                @deleted="(id) => notificationStore.removeNotification(id)"
            />
            <div v-if="notifications.length === 0" class="empty-state">
                Không có thông báo nào
            </div>
        </div>
    </div>
</template>
  


<style scoped>
.main-container{
    background-color: #f0f7ff;
    width: 100%;
    height: 100%;
    display:grid;
    grid-template-columns: 18% 1fr;
    grid-template-areas:
    "sidebar maincontent";
}
.wrapper{
    font-family: "Quicksand", sans-serif;
    display:flex;
    flex-direction: column;
}
.maincontent{
    grid-area: maincontent;
    align-self: flex-start;
    margin-left: 3%;
    margin-top: 30px;
    padding-bottom: 50px;
}
.maincontent div{
    font-weight: 700;
    font-family: "Quicksand", sans-serif;
}
.overview_profile{
    font-family: "Quicksand", sans-serif;
    position:absolute;
    color:#2c3e50;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap:7px;
    top:63px;
    right: 17px;;
    width: 260px;
    height: 200px;
    border-radius: 1.25rem;
    border: 0.5px solid #d4ecf8;
    background-color: rgb(255, 255, 255);
    cursor: pointer;
}
.notifications{
    display: flex;
    font-family: "Quicksand", sans-serif;
    position:absolute;
    color:#2c3e50;
    flex-direction: column;
    top:63px;
    right: 17px;
    width: 380px;
    max-height: 480px;
    overflow-y: auto;
    border-radius: 1.25rem;
    border: 0.5px solid #d4ecf8;
    background-color: rgb(255, 255, 255);
    padding: 16px;
    box-shadow: 0 4px 20px rgba(0,0,0,0.08);
    z-index: 100;
}
.notifications-header {
    font-weight: 700;
    font-size: 16px;
    width: 100%;
    text-align: left;
    padding-bottom: 12px;
    border-bottom: 1px solid var(--border, #e0edf8);
    margin-bottom: 16px;
}
.notifications-list {
    width: 100%;
    display: flex;
    flex-direction: column;
}
.empty-state {
    text-align: center;
    color: #6b8799;
    padding: 20px 0;
    font-size: 14px;
}
.avatar{
    font-weight: 700;
    margin-top: 16px;
    display: flex;
    width: 50px;
    height: 50px;
    background-color: aliceblue;
    align-items: center;
    justify-content: center;
    border-radius: 100%;
    border: 0.5px solid #2c3e50;
}
.name_user{
    font-weight: 700;
    font-size: larger;
    margin-bottom: 10px;
}
.overview_profile div:hover{
    color:#74c5e1;
}
</style >
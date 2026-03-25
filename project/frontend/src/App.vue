<script setup>
import { onMounted } from 'vue';
import { RouterView } from 'vue-router'
import { globalSignal } from '@/stores/eventbus.js';

import { fetchEventSource } from '@microsoft/fetch-event-source';
import { notificationStore } from '@/stores/notificationStore.js';

const controller = new AbortController();

const setupGlobalSSE = async () => {
  const token = localStorage.getItem('token');
  if (!token) return;

  const sseUrl = `http://localhost:8080/api/notifications/subscribe`;

  try {
    await fetchEventSource(sseUrl, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Accept': 'text/event-stream',
      },
      signal: controller.signal,
      onmessage(event) {
        try {
          const data = JSON.parse(event.data);
          console.log("📩 Nhận tín hiệu SSE:", data);
          
          // 1. Xử lý tín hiệu đồng bộ hóa (Signals)
          if (data.type === 'BOARD_UPDATED') {
            globalSignal.value = { action: 'RELOAD_BOARD', boardId: data.boardId, timestamp: Date.now() };
          } 
          else if (data.type === 'SPACE_UPDATED') {
            globalSignal.value = { action: 'RELOAD_SPACES', timestamp: Date.now() };
          }
          else if (data.type === 'BOARD_LIST_UPDATED') {
            globalSignal.value = { action: 'RELOAD_BOARDS', spaceId: data.spaceId, timestamp: Date.now() };
          }

          // 2. Xử lý thông báo (Notifications)
          // Nếu data có cấu trúc của một Notification, đẩy vào Store
          if (data.id && data.message) {
            notificationStore.addNotification(data);
          }
        } catch (e) {
          console.error("Lỗi parse dữ liệu SSE:", e);
        }
      },
      onerror(err) {
        console.error("Lỗi kết nối SSE (fetch):", err);
      }
    });
  } catch (error) {
    console.error("Lỗi khi thiết lập SSE:", error);
  }
};

onMounted(() => {
  setupGlobalSSE();
});
</script>

<template>
  <RouterView />
</template>

<style scoped>
</style>

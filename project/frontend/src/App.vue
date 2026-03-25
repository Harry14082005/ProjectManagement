<script setup>
import { onMounted, ref, watch } from 'vue';
import { RouterView } from 'vue-router'
import { globalBus } from '@/stores/eventbus.js';

import { fetchEventSource } from '@microsoft/fetch-event-source';
import { notificationStore } from '@/stores/notificationStore.js';
import { useAuthStore } from '@/stores/auth.js';

const authStore = useAuthStore();
const controller = ref(new AbortController());
let reconnectTimeout = null;

const setupGlobalSSE = async () => {
  const token = authStore.token || localStorage.getItem('token');
  if (!token) {
    console.log("⚠️ SSE: Không có token, bỏ qua kết nối.");
    return;
  }

  // Clear any existing timeout
  if (reconnectTimeout) {
    clearTimeout(reconnectTimeout);
    reconnectTimeout = null;
  }

  // Hủy kết nối cũ nếu có để tránh duplicate
  if (controller.value) {
    controller.value.abort();
  }
  controller.value = new AbortController();

  const sseUrl = `http://localhost:8080/api/notifications/subscribe?token=${token}`;
  console.log("🔌 SSE: Bắt đầu kết nối tới", sseUrl);

  try {
    await fetchEventSource(sseUrl, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Accept': 'text/event-stream',
      },
      signal: controller.value.signal,
      onopen(response) {
        if (response.ok) {
          console.log("✅ SSE: Kết nối thành công!");
        } else if (response.status === 401 || response.status === 403) {
          console.error("❌ SSE: Token không hợp lệ hoặc hết hạn (403). Dừng retry.");
          // Không throw để tránh onclose/onerror retry vô hạn
        } else {
          console.error("❌ SSE: Kết nối thất bại, status:", response.status);
        }
      },
      onmessage(event) {
        try {
          if (!event.data || event.data === ':heartbeat' || event.data === ':') return;
          
          const data = JSON.parse(event.data);
          console.log("📩 SSE Message:", data);
          
          const typeStr = (data.type || '').toUpperCase();
          if (typeStr) console.log(`🏷️ SSE Event Type Identified: ${typeStr}`);

          // 1. Tín hiệu reload
          let reloadAction = null;
          const isUserSpaceUpdate = ['SPACE_USER_UPDATE', 'ADD_USER_IN_SPACE', 'DELETE_USER_FROM_SPACE'].includes(typeStr);
          const isBoardEvent = typeStr.includes('BOARD');
          const isSpaceEvent = typeStr.includes('SPACE');
          
          // Fix typo: isSpaceUpdate -> isSpaceEvent
          if (data.code == 2000 || isUserSpaceUpdate || isBoardEvent || isSpaceEvent) {
            switch (typeStr) {
              case 'SPACE_USER_UPDATE':
              case 'ADD_USER_IN_SPACE':
              case 'DELETE_USER_FROM_SPACE':
                reloadAction = 'RELOAD_ALL';
                break;
              case 'CARD_TASK_UPDATE':
                reloadAction = 'RELOAD_BOARD_TASKS';
                break;
              case 'SPACE_MEMBER_UPDATE':
                reloadAction = 'RELOAD_SPACE_MEMBERS';
                break;
              case 'BOARD_UPDATED':
                globalBus.emitSignal('RELOAD_BOARD', { boardId: data.boardId });
                break;
              case 'SPACE_BOARD_UPDATE':
              case 'BOARD_LIST_UPDATED':
              case 'BOARD_CREATED':
              case 'BOARD_DELETED':
              case 'ADD_BOARD':
              case 'DELETE_BOARD':
                reloadAction = 'RELOAD_BOARDS';
                break;
              case 'SPACE_UPDATED':
              case 'SPACE_MEMBER_UPDATE':
                reloadAction = 'RELOAD_SPACE_MEMBERS';
                break;
              case 'SPACE_DELETE':
              case 'DELETE_SPACE':
                reloadAction = 'RELOAD_ALL';
                break;
              default:
                if (data.code == 2000) reloadAction = 'RELOAD_PAGE';
                else if (isBoardEvent) reloadAction = 'RELOAD_BOARDS';
                else if (isSpaceEvent) reloadAction = 'RELOAD_SPACES';
            }
          }

          if (reloadAction) {
            // Trong các signal notification, referenceId thường là ID của container (Space/Board)
            const sid = data.spaceId || data.referenceId || data.parentId;
            globalBus.emitSignal(reloadAction, { spaceId: sid });
            console.log(`📤 [App.vue] Emitted ${reloadAction} for spaceId: ${sid}`);
          }

          // 2. Thông báo
          if (data.id && (data.message || data.content)) {
            if (!data.message && data.content) data.message = data.content;
            notificationStore.addNotification(data);
            console.log("🔔 SSE: Đã thêm thông báo mới.");
          }
        } catch (e) {
          console.error("❌ SSE: Lỗi xử lý message:", e, event.data);
        }
      },
      onclose() {
        if (!controller.value.signal.aborted) {
          console.log("🔌 SSE: Server đóng kết nối. Thử kết nối lại sau 5s...");
          reconnectTimeout = setTimeout(() => setupGlobalSSE(), 5000);
        }
      },
      onerror(err) {
        if (!controller.value.signal.aborted) {
          console.error("❌ SSE: Lỗi hệ thống:", err);
          // throw để fetch-event-source tự retry nếu cần, hoặc để im
        }
      }
    });
  } catch (error) {
    if (error.name !== 'AbortError') {
      console.error("❌ SSE: Lỗi kết nối:", error);
      reconnectTimeout = setTimeout(() => setupGlobalSSE(), 5000);
    }
  }
};

onMounted(() => {
  setupGlobalSSE();
});

// Watch token để kết nối lại khi user login/logout
watch(() => authStore.token, (newToken) => {
  if (newToken) {
    console.log("🔑 SSE: Token thay đổi, khởi tạo lại kết nối.");
    setupGlobalSSE();
  } else {
    if (reconnectTimeout) clearTimeout(reconnectTimeout);
    controller.value.abort();
  }
});
</script>

<template>
  <RouterView />
</template>

<style scoped>
</style>

<template>
  <div class="flex flex-col h-screen bg-gradient-to-b from-gray-800 via-gray-900 to-black">
    <!-- Sidebar -->
    <div class="flex flex-col h-full w-full md:w-64 min-w-[140px] bg-gray-900 rounded-r-lg shadow-lg">
      <!-- Header -->
      <div class="flex h-20 shrink-0 items-center px-6 border-b border-gray-700">
        <img class="h-16 w-auto" src="../assets/PolitiquaLogo.jpg" alt="OV'tje Logo" />
        <h1 class="text-2xl font-bold text-white ml-4 hidden md:block">Politiqua</h1>
      </div>

      <!-- Navigation Links -->
      <nav class="flex flex-1 flex-col overflow-y-auto mt-8 px-4">
        <ul role="list" class="flex flex-col gap-y-4">
          <li v-for="link in navLinks" :key="link.path">
            <router-link
                :to="link.path"
                class="relative flex gap-x-4 rounded-md p-3 text-base font-medium transition-all duration-300 ease-in-out text-gray-300 hover:text-white hover:bg-gradient-to-r from-blue-950 to-blue-400"
                :class="{
                'bg-gradient-to-r from-blue-600 to-blue-300 text-white': $route.path === link.path,
              }"
            >
              {{ link.name }}
            </router-link>
          </li>
        </ul>
      </nav>

      <!-- Light/Dark Mode Toggle -->
      <div class="flex items-center justify-between mt-auto px-6 py-4 border-t border-gray-700">
        <div class="flex items-center">
          <span class="mr-3 text-white text-sm font-medium">Dark/Light Mode</span>
          <label class="relative inline-flex items-center cursor-pointer">
            <input
                type="checkbox"
                class="sr-only peer"
                @change="toggleDarkMode"
                :checked="isDarkMode"
            />
            <div class="w-14 h-8 bg-gray-400 rounded-full peer-checked:bg-indigo-700 transition-colors duration-300">
              <div
                  class="absolute top-1 left-1 w-6 h-6 bg-white rounded-full transition-transform duration-300"
                  :class="isDarkMode ? 'translate-x-6' : 'translate-x-0'"
              ></div>
            </div>
          </label>
        </div>
      </div>
<!--Fauzan gebruik deze user section       -->
      <!-- User Section -->
      <div class="flex items-center justify-between px-6 py-6 border-t border-gray-700">
        <router-link to="/accountpage" v-if="authStore.isSignedIn">
          <a class="flex items-center gap-x-4 hover:bg-gray-800 p-3 rounded-lg transition-colors duration-300">
            <div
                class="h-10 w-10 rounded-full flex items-center justify-center font-bold text-lg"
                :style="iconStyles"
            >
              {{ authStore.initials || '' }}
            </div>
            <span class="text-white text-base font-medium hidden md:block">
              {{ authStore.name || 'Finish your account' }}
            </span>
          </a>
        </router-link>


        <!-- Login Button -->
        <router-link to="/login" v-if="!authStore.isSignedIn">
          <button
              class="text-gray-400 hover:text-white bg-blue-700 hover:bg-blue-800 rounded-md px-5 py-2 text-sm font-semibold transition-all duration-300"
          >
            Login
          </button>
        </router-link>

        <!-- Logout Button -->
        <router-link to="/login" v-if="authStore.isSignedIn">
          <button
              @click="logout"
              class="text-gray-400 hover:text-white transition-all duration-300"
              aria-label="Logout"
          >
            <img src="@/assets/logout.svg" alt="Logout" class="w-6 h-6" />
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref, watchEffect} from 'vue';
import { useAuthStore } from '@/stores/auth';

const isDarkMode = ref(false);

watchEffect(() => {
  if (isDarkMode.value) {
    document.body.classList.add('dark');
    localStorage.setItem('theme', 'dark');
  } else {
    document.body.classList.remove('dark');
    localStorage.setItem('theme', 'light');
  }
});

const authStore = useAuthStore();

// Helper function to determine if a color is light or dark
function isColorDark(color) {
  // Remove the hash if it exists
  const hex = color.replace('#', '');

  // Convert to RGB
  const r = parseInt(hex.substring(0, 2), 16);
  const g = parseInt(hex.substring(2, 4), 16);
  const b = parseInt(hex.substring(4, 6), 16);

  // Calculate brightness
  const brightness = (r * 299 + g * 587 + b * 114) / 1000;

  // Return true if brightness is below 128 (dark color threshold)
  return brightness < 128;
}

// Computed styles for the icon
const iconStyles = computed(() => {
  const colour = authStore.colour || '#00ffff';
  const isDark = isColorDark(colour);

  return {
    backgroundColor: colour,
    color: isDark ? '#fff' : '#000', // White text for dark background, black for light background
    border: `2px solid ${isDark ? '#fff' : '#000'}`, // Matching border color
  };
});

// Load user data from local storage or from the store when the component is mounted
onMounted(() => {
  authStore.loadUserFromStorage(); // Load user data from local storage
  const savedTheme = localStorage.getItem('theme');
  if (savedTheme === 'dark') {
    isDarkMode.value = true;
  }
});

const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value;
};

// Handle user logout
const logout = () => {
  authStore.logout();
};

// Define navigation links for dynamic rendering
const navLinks = [
  { name: 'Home', path: '/' },
  { name: 'Forum', path: '/forumOverview' },
  { name: 'Parties', path: '/partyOverview' },
];
</script>

<style scoped>
html {
  scroll-behavior: smooth;
}

body {
  font-family: 'Inter', sans-serif;
  background-color: #1a202c;
}
</style>

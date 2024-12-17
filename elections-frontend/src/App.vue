<template>
    <div :class="['flex h-screen', { dark: isDarkMode }]">
        <!-- Apply dark class conditionally -->
        <div class="sidebar-container">
            <Sidebar />
        </div>
        <div class="main-content flex-grow overflow-y-auto">
            <!-- Allow main content to scroll -->
            <router-view :is-dark-mode="isDarkMode"></router-view>
        </div>
    </div>
</template>

<script>
import Sidebar from '@/components/SideBar.vue' // Adjust the path as necessary

export default {
    name: 'App',
    components: {
        Sidebar,
    },
    data() {
        return {
            isDarkMode: false,
        }
    },
    methods: {
        toggleDarkMode() {
            this.isDarkMode = !this.isDarkMode
            // Toggle the dark class on the body
            if (this.isDarkMode) {
                document.body.classList.add('dark')
                localStorage.setItem('theme', 'dark')
            } else {
                document.body.classList.remove('dark')
                localStorage.setItem('theme', 'light')
            }
        },
    },
    mounted() {
        // Check saved theme preference on load
        const savedTheme = localStorage.getItem('theme')
        if (savedTheme === 'dark') {
            this.isDarkMode = true
            document.body.classList.add('dark')
        }
    },
}
</script>

<style scoped>
.sidebar-container {
    width: 300px; /* Adjusted width for better visibility */
    background-color: #1f2937; /* Dark sidebar background */
    color: white; /* Ensure text is visible in dark mode */
}

.main-content {
    background-color: #ffffff; /* Light background for light mode */
    color: #000; /* Dark text color for light mode */
    width: 100%;
}
.dark .main-content {
    background-color: #1f2937; /* Dark background for dark mode */
    color: #e5e7eb; /* Light text color for dark mode */
}

/* Global scrollbar styles */
::-webkit-scrollbar {
    width: 8px; /* Width of the scrollbar */
}

::-webkit-scrollbar-track {
    background: #f1f1f1; /* Background of the scrollbar track (light mode) */
}

::-webkit-scrollbar-thumb {
    background: #888; /* Color of the scrollbar thumb (light mode) */
}

::-webkit-scrollbar-thumb:hover {
    background: #555; /* Color of the scrollbar thumb on hover (light mode) */
}

/* Dark mode scrollbar styles */
.dark ::-webkit-scrollbar-track {
    background: #2d3748; /* Background of the scrollbar track (dark mode) */
}

.dark ::-webkit-scrollbar-thumb {
    background: #4a5568; /* Color of the scrollbar thumb (dark mode) */
}

.dark ::-webkit-scrollbar-thumb:hover {
    background: #2c5282; /* Color of the scrollbar thumb on hover (dark mode) */
}
</style>

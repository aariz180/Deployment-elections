<template>
    <div
        class="p-6 w-full bg-white shadow rounded-lg my-2 dark:bg-gray-900 dark:text-white relative"
    >
        <!-- Three Dots Menu -->
        <div
            v-if="props.userId === Number(authStore.userId)"
            class="absolute top-4 right-4"
        >
            <button
                v-if="props.userId === Number(authStore.userId)"
                @click="toggleMenu($event)"
                class="p-2 text-gray-500 hover:text-gray-700 dark:text-gray-300 dark:hover:text-gray-100"
            >
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="2"
                    stroke="currentColor"
                    class="w-6 h-6"
                >
                    <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        d="M12 6.75a.75.75 0 110-1.5.75.75 0 010 1.5zm0 5.25a.75.75 0 110-1.5.75.75 0 010 1.5zm0 5.25a.75.75 0 110-1.5.75.75 0 010 1.5z"
                    />
                </svg>
            </button>
            <div
                v-if="menuOpen"
                ref="menuDropdown"
                class="absolute right-0 mt-2 w-32 bg-white dark:bg-gray-800 shadow-md rounded-md z-10"
            >
                <button
                    @click="$emit('edit-forum-action')"
                    class="block w-full text-left px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-700"
                >
                    Edit Forum
                </button>
                <button
                    @click="$emit('delete-forum-action')"
                    class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:hover:bg-gray-700"
                >
                    Delete Forum
                </button>
            </div>
        </div>

        <div class="px-8">
            <div class="border-b pb-4 mb-4 dark:border-gray-700">
                <h1 class="text-3xl font-bold text-gray-800 dark:text-white">
                    {{ title }}
                </h1>
                <div class="text-lg text-gray-500 mt-2 dark:text-gray-400">
                    Posted by
                    <span
                        class="font-semibold text-gray-700 dark:text-gray-200"
                        >{{ username ? username : 'Anonymous' }}</span
                    >
                    <span class="mx-2">|</span>
                    {{
                        new Date(timestamp ? timestamp : '').toLocaleDateString(
                            'en-GB',
                            {
                                year: 'numeric',
                                month: 'long',
                                day: 'numeric',
                            }
                        )
                    }}
                </div>
            </div>

            <!-- Tags Section -->
            <div class="mt-2 flex flex-wrap gap-2 mb-4">
                <div
                    v-for="(tag, index) in tags"
                    :key="index"
                    class="px-4 py-2 text-sm font-bold text-white bg-blue-600 rounded cursor-pointer hover:bg-blue-700 dark:bg-blue-700 dark:hover:bg-blue-600"
                >
                    {{ tag.tagName }}
                </div>
            </div>

            <div class="text-gray-700 text-base mb-4 dark:text-gray-300">
                {{ content }}
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useAuthStore } from '@/stores/auth'

/**
 * Interface representing a tag in the forum post.
 */
interface Tag {
    tagName: string
    tagDescription: string
}

/**
 * Props for the Forum Post component.
 */
const props = defineProps({
    /**
     * Title of the forum post.
     */
    title: String,

    /**
     * Content of the forum post.
     */
    content: String,

    /**
     * Timestamp of the forum post.
     */
    timestamp: String,

    /**
     * Username of the forum post creator.
     */
    username: String,

    /**
     * User ID of the forum post creator.
     */
    userId: Number,

    /**
     * Array of tags associated with the forum post.
     */
    tags: Array<Tag>,
})

/**
 * The authentication store used to verify the current user.
 */
const authStore = useAuthStore()

/**
 * Ref to control the dropdown menu visibility.
 */
const menuOpen = ref(false)

/**
 * Reference to the dropdown menu element.
 */
const menuDropdown = ref<HTMLElement | null>(null)

/**
 * Toggles the visibility of the dropdown menu.
 * @param event - The click event triggered to toggle the menu.
 */
function toggleMenu(event: MouseEvent) {
    event.stopPropagation() // Prevent click event from bubbling up
    menuOpen.value = !menuOpen.value
}

/**
 * Closes the dropdown menu if a click occurs outside of it.
 * @param event - The mouse click event.
 */
function handleClickOutside(event: MouseEvent) {
    if (
        menuDropdown.value &&
        !menuDropdown.value.contains(event.target as Node)
    ) {
        menuOpen.value = false
    }
}

/**
 * Lifecycle hook to check if the current user is the original poster
 * and to attach the click outside listener.
 */
onMounted(() => {
    document.addEventListener('click', handleClickOutside)
})

/**
 * Lifecycle hook to remove the click outside listener when the component unmounts.
 */
onUnmounted(() => {
    document.removeEventListener('click', handleClickOutside)
})
</script>

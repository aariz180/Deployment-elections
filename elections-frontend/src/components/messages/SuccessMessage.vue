<template>
    <div class="max-w-lg mx-auto py-6">
        <div
            id="alert-additional-content-3"
            class="p-6 mb-6 text-green-800 border border-green-300 rounded-lg bg-green-50 dark:bg-gray-800 dark:text-green-400 dark:border-green-800"
            role="alert"
        >
            <div class="flex items-center">
                <svg
                    class="flex-shrink-0 w-5 h-5 me-2"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                >
                    <path
                        d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"
                    />
                </svg>
                <span class="sr-only">Info</span>
                <h3 class="text-xl font-medium">{{ successHeader }}</h3>
            </div>
            <div class="mt-4 mb-6 text-base">
                {{ successBody }}
            </div>
            <div class="flex">
                <button
                    type="button"
                    class="text-green-800 bg-transparent border border-green-800 hover:bg-green-900 hover:text-white focus:ring-4 focus:outline-none focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2 text-center dark:hover:bg-green-600 dark:border-green-600 dark:text-green-400 dark:hover:text-white dark:focus:ring-green-800"
                    data-dismiss-target="#alert-additional-content-3"
                    aria-label="Close"
                    @click="$emit('close-message')"
                >
                    Dismiss
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
defineProps({
    successHeader: String,
    successBody: String,
})
</script>

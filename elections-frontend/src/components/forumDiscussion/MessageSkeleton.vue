<template>
    <div
        class="p-4 bg-white rounded-lg shadow my-2 dark:bg-gray-900 dark:text-white"
    >
        <div
            class="relative flex items-start space-x-4 py-4 last:border-b-0 dark:border-gray-700"
        >
            <!-- Avatar Skeleton -->
            <div
                class="skeleton h-10 w-10 rounded-full bg-gray-300 dark:bg-gray-600"
            ></div>

            <!-- Message Content Skeleton -->
            <div class="flex-1">
                <!-- User Info and Timestamp Skeleton -->
                <div
                    class="skeleton h-4 w-1/3 bg-gray-300 dark:bg-gray-600 rounded mb-2"
                ></div>
                <div
                    class="skeleton h-4 w-2/3 bg-gray-300 dark:bg-gray-600 rounded mb-2"
                ></div>

                <!-- Message Content Skeleton -->
                <div
                    class="skeleton h-5 bg-gray-300 dark:bg-gray-600 rounded mb-4"
                ></div>
            </div>

            <!-- Three Dots Menu Skeleton -->
            <div class="absolute top-4 right-4">
                <div
                    class="skeleton h-5 w-5 rounded-full bg-gray-300 dark:bg-gray-600"
                ></div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.skeleton {
    animation: pulse 1.5s infinite;
}

@keyframes pulse {
    0% {
        opacity: 0.5;
    }
    50% {
        opacity: 1;
    }
    100% {
        opacity: 0.5;
    }
}
</style>

<template>
    <div
        class="max-w-2xl px-8 py-6 bg-white rounded-lg shadow-md dark:bg-gray-900"
    >
        <div class="flex items-center justify-between">
            <span
                class="skeleton h-6 w-40 bg-gray-300 dark:bg-gray-700 rounded"
            ></span>
            <div class="flex flex-wrap">
                <span
                    v-for="n in 3"
                    :key="n"
                    class="skeleton h-10 w-32 bg-blue-600 rounded mx-2"
                ></span>
            </div>
        </div>

        <div class="mt-6">
            <span
                class="skeleton h-10 bg-gray-300 dark:bg-gray-700 rounded"
            ></span>
            <p
                class="skeleton h-8 mt-2 bg-gray-300 dark:bg-gray-700 rounded"
            ></p>
        </div>

        <div class="flex items-center justify-between mt-7">
            <span
                class="skeleton h-6 w-32 bg-gray-300 dark:bg-gray-700 rounded"
            ></span>
            <div class="flex items-center">
                <div
                    class="skeleton h-14 w-14 rounded-full bg-gray-300 dark:bg-gray-700"
                ></div>
                <span
                    class="skeleton h-6 w-40 bg-gray-300 dark:bg-gray-700 rounded ml-3"
                ></span>
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

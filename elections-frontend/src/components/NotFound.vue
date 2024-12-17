<template>
    <div
        class="min-h-[40vh] dark:bg-gray-900 flex flex-col justify-center items-center px-4 sm:px-6 lg:px-8"
    >
        <div class="max-w-md w-full space-y-8 text-center">
            <div class="mb-8 space-y-4">
                <!-- Added space-y-4 for more spacing between lines -->
                <h2
                    class="mt-6 text-6xl font-extrabold text-gray-900 dark:text-gray-100"
                >
                    No Forums Found
                </h2>
                <p
                    class="mt-2 text-3xl font-bold text-gray-900 dark:text-gray-100"
                >
                    We couldn't find any matching Forums.
                </p>
                <p class="mt-2 text-sm text-gray-600 dark:text-gray-400">
                    Try adjusting your filters or check back later.
                </p>
            </div>
            <div class="mt-8">
                <a
                    href="/forumOverview"
                    class="inline-flex items-center px-4 py-2 border border-transparent text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    <svg
                        class="mr-2 -ml-1 h-5 w-5"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                    >
                        <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M3 12h18m-9-9l9 9-9 9"
                        />
                    </svg>
                    Go back to Forum
                </a>
            </div>
        </div>
    </div>
</template>

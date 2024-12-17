<template>
    <div
        class="max-w-full w-full px-12 py-8 bg-white rounded-lg shadow-md dark:bg-gray-900 h-672"
    >
        <div class="flex items-center justify-between">
            <!-- Display the timestamp in 'en-GB' format -->
            <span
                class="text-sm font-light text-gray-600 dark:text-gray-400 min-w-32"
            >
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
            </span>

            <!-- Iterate over the forumTags array and display each tag as a button-like element -->
            <div class="flex flex-wrap">
                <span
                    v-for="(tag, index) in tags"
                    :key="index"
                    class="px-4 py-2 m-1 text-sm font-bold text-gray-100 transition-all duration-300 transform bg-blue-600 rounded cursor-pointer hover:bg-blue-500 hover:scale-105"
                    tabindex="0"
                    role="button"
                    @click="$emit('filter-by-tag', tag.tagName)"
                >
                    {{ tag.tagName }}
                    <!-- Display tag name -->
                </span>
            </div>
        </div>

        <div class="mt-4">
            <!-- Forum title as a clickable link -->
            <a
                @click="navigateToPost(id, title)"
                href="#"
                class="text-3xl font-bold text-gray-700 dark:text-white hover:text-gray-600 dark:hover:text-gray-200 hover:underline"
                tabindex="0"
                role="link"
            >
                {{ title }}
            </a>

            <!-- Forum content/summary -->
            <p class="mt-2 text-gray-600 dark:text-gray-300">
                {{ content }}
            </p>
        </div>

        <div class="flex items-center justify-between mt-6">
            <!-- Link to the full post/discussion -->
            <a
                @click="navigateToPost(id, title)"
                class="text-blue-600 dark:text-blue-400 hover:underline"
                tabindex="0"
                role="link"
            >
                Read more
            </a>

            <!-- Display user's avatar and name -->
            <div class="flex items-center">
                <img
                    :src="`https://ui-avatars.com/api/?name=${encodeURIComponent(user || 'Anonymous')}`"
                    alt="user-avatar"
                    class="m-1.5 rounded h-12"
                />
                <a
                    class="font-bold text-gray-700 cursor-pointer dark:text-gray-200"
                    tabindex="0"
                    role="link"
                >
                    {{ user }}
                </a>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

interface Tag {
    tagName: string
}

// Define the props passed to the component
defineProps({
    id: Number,
    content: String,
    timestamp: String,
    title: String,
    user: String,
    tags: Array<Tag>,
})

const router = useRouter()

/**
 * Navigates the user to the SingleForumOverview page for the selected post.
 *
 * @param {number} forumId - The unique identifier of the forum post to view.
 * @param title
 * @returns {void}
 */
function navigateToPost(
    forumId: number | undefined,
    title: string | undefined
): void {
    if (forumId === undefined || title === undefined) {
        return
    }
    const formattedTitle = title
        .trim()
        .replace(/\s+/g, '_')
        .replace(/[^a-zA-Z0-9_]/g, '')
        .toLowerCase()
    router.push({
        name: 'ForumDiscussion',
        params: { forumId: forumId, forumTitle: formattedTitle },
    })
}
</script>

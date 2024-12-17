<template>
    <div :class="isDarkModeClass">
        <header
            class="bg-white dark:bg-gray-800 flex flex-col items-center justify-center pt-16 p-4 mb-8"
        >
            <h2
                class="text-center text-base font-semibold text-sky-500 dark:text-sky-400"
            >
                Latest Political Changes
            </h2>
            <p
                class="mx-auto mt-2 max-w-lg text-pretty text-center text-4xl font-medium tracking-tight text-gray-950 dark:text-gray-200 sm:text-5xl"
            >
                Join the discussion and share your insights!
            </p>
        </header>

        <div class="w-full flex justify-center">
            <div class="w-full max-w-screen-md">
                <SearchFilter
                    @filter-keywords="setKeyWords"
                    @select-filter="setSortValue"
                    :tag="loggedPostTags"
                    @remove-tag="setTagRemoval"
                />
            </div>
        </div>

        <section
            class="bg-white dark:bg-gray-800 flex items-center justify-center pt-5 p-2 pb-20"
        >
            <div
                class="flex-col justify-center space-y-8 max-w-screen-md w-full h-screen"
            >
                <!-- Existing Posts -->
                <div v-if="postDataArray.length > 0">
                    <PostCard
                        @filter-by-tag="setTagsFromPost"
                        v-for="post in filteredPosts"
                        :key="post.id"
                        :id="post.id"
                        :content="post.content"
                        :timestamp="post.timestamp"
                        :title="post.title"
                        :user="post.user.fullName"
                        :tags="post.tags"
                        class="mb-5"
                    />
                </div>

                <!-- Loading Skeletons for New Posts -->
                <div v-if="checkLoadingMore" class="flex-col justify-center">
                    <PostCardSkeleton
                        v-for="index in 10"
                        :key="index"
                        class="w-full max-w-screen-md mb-5"
                    />
                </div>

                <!-- Custom Observer lol - Fauzan Fauzan -->
                <div ref="target">
                    <div
                        class="grid m-1 place-content-center bg-white px-4 dark:bg-gray-900 dark:text-white"
                    >
                        <h1
                            class="uppercase tracking-widest text-gray-500 dark:text-gray-300"
                        >
                            You have reached the bottom of the forum page!
                        </h1>
                    </div>
                </div>

                <!-- No Posts Found -->
                <div
                    v-if="!checkDataAvailability && filteredPosts.length === 0"
                    class="h-20"
                >
                    <NotFound />
                </div>
            </div>
        </section>
    </div>

    <!-- Loading Info Message Component   -->
    <div
        v-if="displayInfoMessage"
        class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
    >
        <info-message
            v-if="displayInfoMessage"
            :info-message-header="infoMessageHeader"
            :info-message-body="infoMessageBody"
            :info-extra-action="infoExtraAction"
            @close-message="handleCloseMessage"
            @extra-action="handleExtraAction"
        ></info-message>
    </div>

    <!-- Sticky Add New Forum Button -->
    <div class="fixed bottom-5 right-5 z-50">
        <button
            class="flex items-center px-6 py-3 space-x-2 font-medium tracking-wide text-white capitalize transition-colors duration-300 transform bg-blue-600 rounded-full hover:bg-blue-500 focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-80 shadow-lg"
            @click="navigateToForumForm"
        >
            <!-- Pen Icon (SVG) -->
            <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                class="w-6 h-6"
            >
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15.232 5.232a1.5 1.5 0 011.936-.328l2.6 1.3a1.5 1.5 0 01.328 1.936l-7.634 7.634a1.5 1.5 0 01-1.414.414L7.5 16.5l.328-2.556a1.5 1.5 0 01.414-1.414l7.634-7.634z"
                />
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M16 7l1.5 1.5"
                />
            </svg>
            <!-- Button Text -->
            <span class="ml-2">Create</span>
        </button>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useIntersectionObserver } from '@vueuse/core'
import SearchFilter from '@/components/SearchFilter.vue'
import NotFound from '@/components/NotFound.vue'
import {
    fetchForumData,
    fetchForumDataASC,
    fetchForumDataDESC,
} from '@/helpers/forum/fetchForumData'
import PostCard from '@/components/postCard/PostCard.vue'
import PostCardSkeleton from '@/components/postCard/PostCardSkeleton.vue'
import InfoMessage from '@/components/messages/InfoMessage.vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const displayInfoMessage = ref<boolean>(false)

// Info message content
const infoMessageHeader = ref<string>('')
const infoMessageBody = ref<string>('')
const infoExtraAction = ref<string>('')

// Prop for enabling dark mode
const props = defineProps({
    isDarkMode: Boolean,
})

// Compute dark mode class for the component
const isDarkModeClass = computed(() => (props.isDarkMode ? 'dark' : ''))

// Holds the keywords for filtering posts
const loggedSearchKeywords = ref('')

// Holds the selected sorting criteria for posts
const loggedSortKeywords = ref('')

// Tracks the forumTags currently selected for filtering posts
const loggedPostTags = ref<string[]>([])

// Indicates if the initial data fetch is still loadingMessageDisplayStatus
const loadingStatus = ref(true)

// Holds the array of posts retrieved from the server
const postDataArray = ref<Forum[]>([])

// Reference to the target DOM element for the intersection observer
const target = ref(null)

// Tracks the current page of pagination for fetching new posts
let checkPagination = ref(0)

// Prevents multiple fetches while data is being retrieved
let checkFetching = ref(false)

// Indicates if more posts are being loaded for infinite scrolling
let checkLoadingMore = ref(false)

// Indicates if more data is available for pagination
let checkDataAvailability = ref(true)

interface Forum {
    id: number
    content: string
    timestamp: string
    title: string
    user: { fullName: string }
    tags: { tagName: string; tagDescription: string }[]
}

/**
 * Fetches post data from the API based on the current pagination page.
 * Prevents multiple simultaneous fetches and manages loadingMessageDisplayStatus states.
 *
 * @returns {Promise<void>} A promise that resolves when the fetch is complete.
 */
async function handlePostRendering(): Promise<void> {
    if (checkFetching.value) return // Prevent multiple fetches
    checkFetching.value = true // Set fetching flag to true
    checkLoadingMore.value = true // Set loadingMessageDisplayStatus state to true when fetching new posts
    let response: { data: Array<any> } = { data: [] }
    try {
        switch (loggedSortKeywords.value) {
            case 'old':
                response = await fetchForumDataASC(checkPagination.value)
                break
            case 'new':
                response = await fetchForumDataDESC(checkPagination.value)
                break
            default:
                response = await fetchForumData(checkPagination.value)
        }

        // Determine if there are fewer than 10 posts returned
        if (response.data.length < 10) {
            checkDataAvailability.value = false
        }

        console.log(response.data)
        // Map the response data to a standardized format
        const populatePostCards: any[] = response.data.map((post) => ({
            ...post,
            user: {
                fullName:
                    post.user?.fullName ||
                    post.user?.name ||
                    post.user?.username ||
                    'Anonymous',
            },
            tags: post.forumCategories.map((category: any) => ({
                tagName: category.tagName,
                tagDescription: category.tagDescription,
            })),
        }))

        // Append new posts to the existing array if any new posts were fetched
        if (populatePostCards.length > 0) {
            postDataArray.value.push(...populatePostCards)
        }
    } catch (error) {
        console.error('Error fetching forum data: ', error)
    } finally {
        loadingStatus.value = false // Set loadingMessageDisplayStatus state to false for initial fetch
        checkLoadingMore.value = false // Set loadingMessageDisplayStatus state to false after fetching
        checkPagination.value++ // Update pagination after the fetch
        checkFetching.value = false // Reset fetching flag
    }
}

/**
 * Sets the tag selected from a post for filtering.
 * Normalizes the tag to lower case before adding it to the loggedTags array.
 *
 * @param {string} tag - The tag selected from a post.
 * @returns {void}
 */
function setTagsFromPost(tag: string): void {
    if (loggedPostTags.value.includes(tag.toLowerCase())) return // Prevent duplicate forumTags
    loggedPostTags.value.push(tag.toLowerCase())
}

/**
 * Removes a tag from the loggedTags array based on the provided tag.
 *
 * @param {string} tag - The tag to be removed from the loggedTags.
 * @returns {void}
 */
function setTagRemoval(tag: string): void {
    const lowerTag = tag.toLowerCase() // Normalize to lower case
    const index = loggedPostTags.value.indexOf(lowerTag)
    if (index > -1) {
        loggedPostTags.value.splice(index, 1) // Remove the tag if found
    }
}

/**
 * Sets the keywords for filtering posts based on user input.
 * Resets pagination to 0, clears current posts, and fetches new posts
 * based on the updated search keywords.
 *
 * @param {string} keywords - The keywords input from the user to filter posts.
 * @returns {void}
 */
function setKeyWords(keywords: string): void {
    loggedSearchKeywords.value = keywords.toLowerCase() // Normalize to lower case
    loggedSortKeywords.value = '' // Resets Sorting Keywords
    checkPagination.value = 0 // Reset pagination to the first page
    postDataArray.value = [] // Clear current posts
    checkDataAvailability.value = true // Reset data availability for pagination
    handlePostRendering() // Fetch new posts based on keywords
}

/**
 * Sets the sorting criteria for posts based on user input.
 * Resets pagination to 0, clears current posts, and fetches new posts
 * based on the updated sorting option.
 *
 * @param {string} keywords - The sorting criteria (e.g., 'new', 'oldest') input by the user.
 * @returns {void}
 */
function setSortValue(keywords: string): void {
    loggedSortKeywords.value = keywords.toLowerCase() // Normalize to lower case
    checkPagination.value = 0 // Reset pagination to the first page
    postDataArray.value = [] // Clear current posts
    checkDataAvailability.value = true // Reset data availability for pagination
    handlePostRendering() // Fetch new posts based on sorting option
}

/**
 * Computed property that filters and sorts the array of posts based on
 * the user's search keywords and selected sorting criteria.
 *
 * @returns {Array<Object>} An array of filtered and sorted post objects.
 */
const filteredPosts = computed(() => {
    let fPosts = postDataArray.value // Start with the existing posts
    // Step 1: Filter posts based on search keywords
    if (loggedSearchKeywords.value) {
        const lowercasedKeywords = loggedSearchKeywords.value.toLowerCase()
        fPosts = fPosts.filter((post: any) =>
            post.title.toLowerCase().includes(lowercasedKeywords)
        )
    }

    // Step 2: Filter posts based on selected forumTags
    if (loggedPostTags.value.length > 0) {
        fPosts = fPosts.filter((post) =>
            post.tags.some((tag: any) =>
                loggedPostTags.value
                    .map((tag) => tag.toLowerCase())
                    .includes(tag.tagName.toLowerCase())
            )
        )
    }

    return fPosts // Return the filtered and sorted posts
})

/**
 * Handles user feedback for login or registration requirement.
 * Displays a message if the user is not logged in and provides instructions for logging in or registering.
 */
const handleNotLoggedInFeedback = (): void => {
    displayInfoMessage.value = true
    infoMessageHeader.value = 'Login or Register Required'
    infoMessageBody.value = `
        You need to be logged in to proceed with this action.
        If you already have an account, please log in.
        If you're new here, please register to create an account and access this feature.
    `
    infoExtraAction.value = 'Login or Register'
    return
}

/**
 * Closes the success message component when the user acknowledges the message.
 */
function handleCloseMessage(): void {
    displayInfoMessage.value = false
}

const router = useRouter()

/**
 * Handles the action of navigating the user to the login page.
 */
function handleExtraAction(): void {
    router.push('Login')
}

const authStore = useAuthStore()

/**
 * Navigates to the forum creation page if the user is signed in.
 * If the user is not signed in, emits an event to notify the parent component.
 *
 * @returns {void}
 * This function does not return anything. It either triggers navigation or emits an event.
 */
const navigateToForumForm = (): void => {
    if (!authStore.isSignedIn) {
        handleNotLoggedInFeedback()
        return
    }

    router.push({
        name: 'CreateForum',
    })
}

/**
 * Fetches initial posts when the component is mounted.
 */
onMounted(() => {
    handlePostRendering() // Fetch initial posts on mount
})

/**
 * Sets up an intersection observer to load more posts when the target element is in view.
 *
 * @param {HTMLElement} target - The target element to observe for intersection.
 * @param {Function} callback - The function to execute when the intersection status changes.
 * @param {Object} callbackParams - The parameters for the callback function.
 * @param {boolean} callbackParams.isIntersecting - Indicates if the target element is currently in view.
 *
 * @returns {void}
 */
useIntersectionObserver(target, ([{ isIntersecting }]) => {
    if (isIntersecting && !checkFetching.value && checkDataAvailability.value) {
        handlePostRendering() // Load more data when the last card is in view
    }
    return
})
</script>

<template>
    <header class="mt-8 flex flex-col items-center justify-center">
        <!-- Header with Title and Subtitle -->
        <h1
            class="mb-6 text-4xl font-extrabold leading-none tracking-tight text-gray-900 md:text-5xl lg:text-6xl dark:text-white"
        >
            New
            <span class="text-blue-600 dark:text-blue-500">Forum</span> Creation
        </h1>
        <p
            class="text-lg font-normal text-gray-500 lg:text-xl dark:text-gray-400 mb-8"
        >
            Start a new discussion and share your question or opinion!
        </p>
    </header>

    <div :class="isDarkModeClass">
        <div class="flex justify-center items-center max-h-screen">
            <div
                class="w-full max-w-4xl bg-white dark:bg-gray-800 p-4 rounded-lg shadow-lg border border-gray-300 dark:border-gray-600"
            >
                <form @submit.prevent="createForum" class="space-y-6">
                    <!-- Title Input Field -->
                    <fieldset>
                        <label
                            for="title"
                            class="block text-gray-700 dark:text-gray-200 font-semibold mb-2"
                            >Title</label
                        >
                        <input
                            id="title"
                            type="text"
                            v-model="title"
                            class="w-full px-4 py-3 bg-gray-100 dark:bg-gray-900 border dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-400"
                            placeholder="Enter the forum title"
                        />
                        <alert-box
                            v-if="titleError"
                            class="mt-2"
                            :alert-header="titleErrorTitle"
                            :alert-body="titleErrorBody"
                        ></alert-box>
                    </fieldset>

                    <!-- Body Textarea -->
                    <fieldset>
                        <label
                            for="body"
                            class="block text-gray-700 dark:text-gray-200 font-semibold mb-2"
                            >Body</label
                        >
                        <textarea
                            id="body"
                            rows="6"
                            v-model="body"
                            class="w-full px-4 py-3 bg-gray-100 dark:bg-gray-900 border dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-400 min-h-[150px]"
                            placeholder="Write the forum content"
                        ></textarea>
                        <alert-box
                            v-if="bodyError"
                            class="mt-2"
                            :alert-header="bodyErrorTitle"
                            :alert-body="bodyErrorBody"
                        ></alert-box>
                    </fieldset>

                    <!-- Label Dropdown -->
                    <fieldset>
                        <label
                            for="label"
                            class="block text-gray-700 dark:text-gray-200 font-semibold mb-2"
                            >Label</label
                        >
                        <select
                            id="label"
                            v-model="selectedLabel"
                            @change="addLabel"
                            class="w-full px-4 py-3 bg-gray-100 dark:bg-gray-900 border dark:border-gray-600 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-400"
                        >
                            <option value="" disabled>Select a label</option>
                            <option v-if="loadingTags" disabled>
                                Loading forumTags...
                            </option>
                            <option
                                v-for="(tag, index) in forumTags"
                                :key="index"
                                :value="tag"
                                :disabled="selectedLabels.includes(tag)"
                            >
                                {{ tag.tagName }}
                            </option>
                        </select>
                    </fieldset>

                    <!-- Display Selected Labels as Tags -->
                    <div
                        v-if="selectedLabels.length"
                        class="mt-2 flex flex-wrap space-x-2"
                    >
                        <div
                            v-for="(label, index) in selectedLabels"
                            :key="index"
                            class="px-4 py-2 m-1 text-sm font-bold text-white bg-blue-600 rounded cursor-pointer hover:bg-blue-700"
                            @click="removeLabel(label)"
                        >
                            {{ label.tagName }} &times;
                        </div>
                    </div>

                    <!-- Submit and Cancel Buttons -->
                    <div class="flex justify-end space-x-4 w-full">
                        <router-link to="/forumOverview">
                            <button
                                type="button"
                                class="w-full px-10 py-3 bg-gray-300 dark:bg-gray-700 text-gray-800 dark:text-white font-semibold rounded-lg hover:bg-gray-400 dark:hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500 dark:focus:ring-gray-400"
                            >
                                Cancel
                            </button>
                        </router-link>

                        <button
                            type="submit"
                            class="w-1/6 py-3 bg-blue-500 dark:bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-400"
                        >
                            Create Forum
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Loading Message Component   -->
    <div
        v-if="forumPostingStatus"
        class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
    >
        <loading-message
            v-if="forumPostingStatus"
            :loading-header="loadingMessageHeader"
            :loading-body="loadingMessageBody"
        ></loading-message>
    </div>

    <!-- Success Message Component -->
    <div
        v-if="postDataValidation"
        class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
    >
        <success-message
            v-if="postDataValidation"
            :success-header="successMessageHeader"
            :success-body="successMessageBody"
            @close-message="closeMessage"
        ></success-message>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import AlertBox from '@/components/AlertBox.vue'
import SuccessMessage from '@/components/messages/SuccessMessage.vue'
import { postForum } from '@/helpers/forum/postForum'
import { fetchForumTags } from '@/helpers/forum/fetchForumTags'
import LoadingMessage from '@/components/messages/LoadingMessage.vue'
import { useRouter } from 'vue-router'

// Form field references
const title = ref<string>('')
const body = ref<string>('')
const selectedLabel = ref<TagResponseItem | null>(null)
const selectedLabels = ref<TagResponseItem[]>([])
const userId = ref<number>()

// Validation error flags for form fields
const titleError = ref<boolean>(false)
const bodyError = ref<boolean>(false)

// Validation error messages for form fields
const titleErrorTitle = ref<string>('')
const titleErrorBody = ref<string>('')
const bodyErrorTitle = ref<string>('')
const bodyErrorBody = ref<string>('')

// Validation status indicators
const inputValidation = ref<boolean>(false)
const postDataValidation = ref<boolean>(false)
const forumPostingStatus = ref<boolean>(false)

// Loading message fields
const loadingMessageHeader = ref<string>('')
const loadingMessageBody = ref<string>('')

// Success message fields
const successMessageHeader = ref<string>('')
const successMessageBody = ref<string>('')

// Tag-related references
const forumTags = ref<TagResponseItem[]>([])
const loadingTags = ref<boolean>(true)

const newForumId = ref<number>()
const newForumTitle = ref<string>('')

// Prop for enabling dark mode
const props = defineProps({
    isDarkMode: Boolean,
})

// Define the type for the response items from the API
interface TagResponseItem {
    id: number
    tagId: number
    tagName: string
}

// Compute dark mode class for the component
const isDarkModeClass = computed(() => (props.isDarkMode ? 'dark' : ''))

/**
 * Adds a label to `selectedLabels` array if it's not already included.
 * Clears `selectedLabel` after addition.
 */
const addLabel = (): void => {
    if (
        selectedLabel.value &&
        !selectedLabels.value.includes(selectedLabel.value)
    ) {
        selectedLabels.value.push(selectedLabel.value)
    }
    selectedLabel.value = null
}

/**
 * Removes a specific label from `selectedLabels`.
 * @param {string} label - The label to be removed.
 */
const removeLabel = (label: TagResponseItem): void => {
    selectedLabels.value = selectedLabels.value.filter((l) => l !== label)
}

/**
 * Validates form fields `title` and `body`.
 * Updates error flags and messages based on validation results.
 */
const validateForm = (): void => {
    if (title.value.trim().length === 0) {
        titleError.value = true
        titleErrorTitle.value = 'Empty Title'
        titleErrorBody.value = 'A title field is required.'
    } else {
        titleError.value = false
    }

    if (body.value.trim().length === 0) {
        bodyError.value = true
        bodyErrorTitle.value = 'Empty Body'
        bodyErrorBody.value = 'A body field is required.'
    } else {
        bodyError.value = false
    }

    inputValidation.value = !titleError.value && !bodyError.value
}

/**
 * Handles the creation of a new forum post by validating the form and posting the data to the backend.
 *
 * If the post is successful, it resets the form fields and shows a success message. If there is an error,
 * it logs the error and can display an appropriate message to the user.
 *
 * @returns {Promise<void>} A promise that resolves when the function finishes execution.
 */
const createForum = async (): Promise<void> => {
    // Validate form inputs
    validateForm()
    fetchUserData()
    if (inputValidation.value) {
        // Collect the IDs of the selected tags
        const selectedTagsIds = selectedLabels.value.map((t) => t.id)

        if (!userId.value) {
            console.error('User ID is not available')
            return
        }

        forumPostingStatus.value = true
        loadingMessageHeader.value = 'Creating Your Forum'
        loadingMessageBody.value =
            'Please wait while we submit your forum post. This may take a few moments...'

        try {
            // Attempt to post the forum data
            const result: any = await postForum(
                body.value,
                title.value,
                userId.value, // Assuming 12 is the user ID here
                selectedTagsIds
            )

            // If the forum post is successful
            if (result) {
                forumPostingStatus.value = false
                newForumId.value = result.data.id
                newForumTitle.value = result.data.title
                title.value = ''
                body.value = ''
                selectedLabels.value = []
                postDataValidation.value = true

                // Set success message
                successMessageHeader.value = 'Forum Created Successfully'
                successMessageBody.value =
                    'Your new forum post has been successfully created! You can now view it in the forums and start engaging with other users.'

                // Optionally, you can add any further actions, like redirecting to the forum view page.
            } else {
                console.error('Failed to post the forum. Please try again.')
                // You could optionally provide additional error feedback to the user here
            }
        } catch (error) {
            console.error('Forum creation error:', error)
            // You could also display a user-friendly error message here
        }
    } else {
        console.log('Input validation failed.')
        // Provide feedback about the form validation failure (optional)
    }
}

/**
 * Closes the success message component.
 */
function closeMessage(): void {
    postDataValidation.value = false
    navigateToPost(newForumId.value, newForumTitle.value)
}

/**
 * Fetches user data from localStorage and sets the `userId` reactive value.
 * If no user data is found in localStorage, an error is logged.
 *
 * @returns {void}
 */
function fetchUserData(): void {
    const userData = localStorage.getItem('userData')
    if (userData) {
        const parsedData = JSON.parse(userData)
        userId.value = parsedData.userId
    } else {
        console.error('No user data found!')
    }
    return
}

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

/**
 * Fetches available forum forumTags from the API on component mount.
 * The fetched forumTags are used as options for categorizing forum posts.
 */
onMounted(() => {
    fetchForumTags()
        .then((response) => {
            forumTags.value = response.data as TagResponseItem[]
            console.log('received forumTags: ', forumTags.value)
            loadingTags.value = false
        })
        .catch((error) => {
            console.error('Failed to fetch tags:', error)
            loadingTags.value = false
        })
})
</script>

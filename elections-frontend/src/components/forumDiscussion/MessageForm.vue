<template>
    <div
        class="p-6 bg-white rounded-lg shadow-md my-4 dark:bg-gray-900 dark:text-white"
    >
        <div class="flex items-center space-x-4 mb-4">
            <!-- Avatar Placeholder -->
            <img
                :src="`https://ui-avatars.com/api/?name=${encodeURIComponent(userFullName || 'Anonymous')}`"
                alt="user-avatar"
                class="m-1.5 rounded h-12"
            />
            <div class="flex-1">
                <!-- Input Field -->
                <input
                    @keydown.enter="postMessageOnClick"
                    v-model="message"
                    type="text"
                    class="w-full p-3 border border-gray-300 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Type your message here..."
                />
            </div>
        </div>

        <!-- Send Message Button -->
        <button
            @click="postMessageOnClick"
            class="w-full py-2 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 dark:bg-blue-700 dark:hover:bg-blue-600"
        >
            Send Message
        </button>
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { postMessage } from '@/helpers/forumDiscussion/postMessage'
import { fetchUserFromLocalStorage } from '@/helpers/user/fetchUserData'
import { useAuthStore } from '@/stores/auth'

const props = defineProps({
    forumId: Number,
})

const emit = defineEmits<{
    (eventName: 'postMessageStatus', status: boolean): void
    (eventName: 'is-logged-in', status: boolean): void
}>()

/**
 * Ref to hold the user's input message.
 */
const message = ref<string>('')

/**
 * Ref to hold the user ID retrieved from local storage.
 */
const userIdMessageForm = ref<number | undefined>()

/**
 * Ref to hold the user FullName retrieved from local storage.
 */
const userFullName = ref<string | null>()

/**
 * Ref to hold the forum ID passed as a prop.
 */
const forumIdMessageForm = ref<number | undefined>()

// Set the forum ID from the passed props.
forumIdMessageForm.value = props.forumId

/**
 * Sends the user's message to the server when the "Send Message" button is clicked.
 * Calls the `postMessage` helper with the message, forum ID, and user ID.
 * @async
 * @returns {Promise<void>} A promise that resolves when the message is posted.
 */
const postMessageOnClick = async (): Promise<void> => {
    const authentication = handleLoggedInCheck()

    if (!authentication) {
        emit('is-logged-in', false)
        return
    }

    if (!message.value.trim()) {
        return
    }

    emit('postMessageStatus', true)
    const response = await postMessage(
        message.value,
        forumIdMessageForm.value,
        userIdMessageForm.value
    )
    if (response) {
        emit('postMessageStatus', false)
        message.value = ''
        return
    }
    return
}

/**
 * Retrieves the user ID from local storage and assigns it to `userIdMessageForm`.
 */
const retrieveUserId = (): void => {
    const userData = fetchUserFromLocalStorage()
    if (!userData) {
        return
    }
    userFullName.value = userData.name
    userIdMessageForm.value = userData.userId
}

const authStore = useAuthStore()

const handleLoggedInCheck = (): Boolean => {
    return authStore.isSignedIn
}

// Lifecycle hook to retrieve the user ID when the component is mounted.
onMounted(() => {
    retrieveUserId()
})
</script>

<template>
    <div
        class="p-4 bg-white rounded-lg shadow my-2 dark:bg-gray-900 dark:text-white"
    >
        <div
            v-for="(message, index) in messages"
            :key="index"
            class="relative flex items-start space-x-4 py-4 border-b last:border-b-0 dark:border-gray-700"
        >
            <img
                :src="`https://ui-avatars.com/api/?name=${encodeURIComponent(message.user.fullName || 'Anonymous')}`"
                alt="user-avatar"
                class="m-1.5 rounded h-12"
            />
            <!-- Message Content -->
            <div class="flex-1">
                <!-- User Info and Timestamp -->
                <div class="text-sm text-gray-500 mb-2 dark:text-gray-400">
                    <span class="font-semibold text-gray-700 dark:text-white">{{
                        message.user.fullName
                    }}</span>
                    <span class="mx-2">|</span>
                    {{
                        new Date(message.timestamp || '').toLocaleString(
                            'en-GB',
                            {
                                day: 'numeric',
                                month: 'long',
                                year: 'numeric',
                                hour: '2-digit',
                                minute: '2-digit',
                            }
                        )
                    }}
                </div>
                <div>
                    <div
                        v-if="
                            messageEditActive &&
                            messageSelectedId === message.id
                        "
                    >
                        <textarea
                            @keydown.enter="
                                handleMessageUpdate(message.id, message.content)
                            "
                            name="updateMessage"
                            v-model="message.content"
                            class="w-full max-w-xl bg-gray-200 dark:bg-gray-700 text-black dark:text-white border border-gray-400 dark:border-gray-600 rounded p-2 resize-y"
                            rows="3"
                        ></textarea>
                    </div>
                    <div v-else>
                        <!-- Message Text -->
                        <p class="text-gray-800 text-base dark:text-gray-300">
                            {{ message.content }}
                        </p>
                    </div>
                </div>

                <!-- Action Buttons (for the message not in edit mode) -->
                <div
                    v-if="
                        !messageEditActive && messageSelectedId === message.id
                    "
                    class="flex items-center space-x-6 mt-3 text-sm text-gray-500 dark:text-gray-400"
                >
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Reply
                    </button>
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Share
                    </button>
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Report
                    </button>
                </div>

                <!-- Cancel Button for Edit Mode (only for the selected message in edit mode) -->
                <div
                    v-if="messageEditActive && messageSelectedId === message.id"
                    class="flex items-center space-x-6 mt-3 text-sm text-gray-500 dark:text-gray-400"
                >
                    <button
                        class="hover:text-red-500 dark:hover:text-red-400"
                        @click="
                            handleMessageUpdate(message.id, message.content)
                        "
                    >
                        Save
                    </button>
                    <button
                        class="hover:text-red-500 dark:hover:text-red-400"
                        @click="closeDisplayMessageInput"
                    >
                        Cancel
                    </button>
                </div>

                <!-- Action Buttons (for other messages not in edit mode) -->
                <div
                    v-else
                    class="flex items-center space-x-6 mt-3 text-sm text-gray-500 dark:text-gray-400"
                >
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Reply
                    </button>
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Share
                    </button>
                    <button
                        class="hover:text-blue-500 dark:hover:text-blue-400"
                    >
                        Report
                    </button>
                </div>
            </div>
            <!-- Three Dots Menu -->
            <div
                v-if="message.user.id === Number(authStore.userId)"
                class="absolute top-4 right-4"
            >
                <button
                    @click="toggleMenu($event, index)"
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
                    v-if="menuOpenStates.get(index)"
                    :id="`menu-${index}`"
                    class="absolute right-0 mt-2 w-32 bg-white dark:bg-gray-800 shadow-md rounded-md z-10"
                >
                    <button
                        @click="displayMessageEditInput(message.id)"
                        class="block w-full text-left px-4 py-2 text-sm text-gray-800 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-700"
                    >
                        Edit
                    </button>
                    <button
                        @click="$emit('delete-message-action', message.id)"
                        class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:hover:bg-gray-700"
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>
        <div v-if="loadingMessage">
            <message-skeleton></message-skeleton>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue'
import { fetchMessageData } from '@/helpers/forumDiscussion/fetchMessageData'
import { useAuthStore } from '@/stores/auth'
import { updateMessage } from '@/helpers/forumDiscussion/updateMessage'
import MessageSkeleton from '@/components/forumDiscussion/MessageSkeleton.vue'

/**
 * User object interface
 */
interface User {
    id: number
    fullName: string
}

/**
 * Message object interface
 */
interface Message {
    id: number
    content: string
    timestamp: string
    user: User
}

/**
 * Holds the list of messages to be displayed.
 */
const messages = ref<Message[]>([])

/**
 * A map that tracks the open/closed state of each message's dropdown menu.
 * Key: message index, Value: boolean indicating if the menu is open.
 */
const menuOpenStates = ref<Map<number, boolean>>(new Map())

const messageEditActive = ref<boolean>(false)
const messageSelectedId = ref<number>()

const populatingMessages = ref<boolean>(false)

const authStore = useAuthStore()

/**
 * Emits events to parent components for various actions.
 */
const emit = defineEmits<{
    /**
     * Resets the refresh display status.
     * @param eventName
     * @param status - A boolean indicating whether the display should be refreshed or not.
     */
    (eventName: 'reset-refresh-display', status: boolean): void

    /**
     * Emits the edit message action.
     * @param eventName
     * @param messageId - The message to be edited.
     */
    (eventName: 'edit-message-action', messageId: number): void

    /**
     * Emits the delete message action.
     * @param eventName
     * @param messageId - The message to be deleted.
     */
    (eventName: 'delete-message-action', messageId: number): void
}>()

/**
 * Props passed from the parent component.
 */
const props = defineProps({
    forumId: Number,
    refreshDisplay: Boolean,
    loadingMessage: Boolean,
})

/**
 * Toggles the visibility of the dropdown menu for a specific message.
 * This function will close all other open menus and toggle the current menu.
 *
 * @param event - The click event triggered to toggle the menu.
 * @param index - The index of the message whose menu is being toggled.
 */
function toggleMenu(event: MouseEvent, index: number) {
    event.stopPropagation() // Prevent click event from bubbling up
    const isOpen = menuOpenStates.value.get(index) || false
    closeAllMenus() // Close all other menus
    menuOpenStates.value.set(index, !isOpen) // Toggle the current menu
}

/**
 * Closes all open dropdown menus by clearing the menu state.
 */
function closeAllMenus() {
    menuOpenStates.value.clear()
}

/**
 * Closes the dropdown menu if a click occurs outside of it.
 *
 * @param event - The mouse click event.
 */
function handleClickOutside(event: MouseEvent) {
    const target = event.target as HTMLElement

    // Check if the click occurred inside any open menu
    const clickedInsideMenu = Array.from(menuOpenStates.value.keys()).some(
        (index) => {
            const menu = document.querySelector(`#menu-${index}`)
            return menu?.contains(target)
        }
    )

    // If clicked outside any open menu, close all menus
    if (!clickedInsideMenu) {
        closeAllMenus()
    }
}

/**
 * Activates the message editing mode for the given message ID.
 *
 * @param messageId - The ID of the message to be edited.
 */
function displayMessageEditInput(messageId: number): void {
    messageSelectedId.value = Number(messageId)
    messageEditActive.value = true
    console.log(messageId)
}

/**
 * Closes the message editing input and clears the selected message ID.
 */
function closeDisplayMessageInput(): void {
    messageEditActive.value = false
    messageSelectedId.value = undefined
}

/**
 * Handles updating the content of a message by calling the `updateMessage` function.
 * If the update is successful, it closes the edit input and refreshes the message display.
 * If the update fails, it logs an error.
 *
 * @param messageId - The ID of the message to be updated.
 * @param content - The new content to update the message with.
 * @returns A promise that resolves when the update process is complete.
 */
async function handleMessageUpdate(
    messageId: number,
    content: string
): Promise<void> {
    if (!content.trim()) {
        console.error('Content is empty')
        return
    }
    const response = await updateMessage(messageId, content)
    if (response) {
        closeDisplayMessageInput()
        await populateMessageDisplay()
        return
    } else {
        console.error('Failed to update message', messageId)
        return
    }
}

/**
 * Fetches and populates the message display with the data from the API.
 * This function is invoked on component mount.
 */
async function populateMessageDisplay(): Promise<void> {
    populatingMessages.value = true
    try {
        const response = await fetchMessageData(props.forumId)
        if (response) {
            populatingMessages.value = false
            messages.value = response.data
        }
        return
    } catch (error) {
        populatingMessages.value = false
        console.error('Error fetching message data:', error)
    }
}

onMounted(() => {
    populateMessageDisplay()

    // Add event listener for handling clicks outside of menus
    document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
    /**
     * Called when the component is unmounted.
     * - Removes the event listener for clicks outside of the dropdown menus to prevent memory leaks.
     */
    document.removeEventListener('click', handleClickOutside)
})

watch(
    () => props.refreshDisplay,
    (newVal) => {
        /**
         * Watches for changes to the `refreshDisplay` prop.
         * - If the prop is set to `true`, it triggers a refresh of the message display.
         * - Emits an event to reset the refresh display status to `false` after the refresh.
         *
         * @param newVal - The new value of the `refreshDisplay` prop.
         */
        if (newVal) {
            closeAllMenus()
            populateMessageDisplay()
            emit('reset-refresh-display', false)
        }
    }
)
</script>

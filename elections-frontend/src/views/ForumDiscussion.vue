<template>
    <div :class="isDarkModeClass">
        <div class="flex justify-center h-screen min-h-screen mt-2">
            <div
                v-if="loadingMessageDisplayStatus"
                class="flex justify-center items-center h-full w-full"
            >
                <forum-loading-state />
            </div>
            <div v-if="!loadingMessageDisplayStatus" class="flex w-full">
                <div class="w-1/5"></div>
                <div class="w-full flex flex-col p-6">
                    <discussion-header
                        @delete-forum-action="
                            handleWarningMessageForumDeletionDisplay
                        "
                        v-if="forumData && forumData.user"
                        :key="forumData.id"
                        :title="forumData.title"
                        :content="forumData.content"
                        :timestamp="forumData.timestamp"
                        :username="forumData.user.fullName"
                        :userId="forumData.user.id"
                        :tags="forumTags"
                    ></discussion-header>
                    <message-display
                        v-if="forumData && forumData.user"
                        :key="forumData.id"
                        :forumId="forumData.id"
                        :refresh-display="postingMessageCRUDSuccess"
                        :loading-message="postingMessageStatus"
                        @reset-refresh-display="handleFeedbackTermination"
                        @delete-message-action="handleMessageDeletion"
                        :user="forumData.user.fullName"
                    ></message-display>
                    <message-form
                        @post-message-status="handleMessageFeedback"
                        @is-logged-in="handleLoggedInCheck"
                        v-if="forumData && forumData.user"
                        :key="forumData.id"
                        :forumId="forumData.id"
                    ></message-form>
                </div>
                <div class="w-1/5"></div>
            </div>
        </div>

        <!-- Success Message Component -->
        <div
            v-if="displaySuccessMessage"
            class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
        >
            <success-message
                v-if="displaySuccessMessage"
                :success-header="successMessageHeader"
                :success-body="successMessageBody"
                @close-message="handleFeedbackTermination"
            ></success-message>
        </div>

        <!-- Info Message Component   -->
        <div
            v-if="displayInfoMessage"
            class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
        >
            <info-message
                v-if="displayInfoMessage"
                :info-message-header="infoMessageHeader"
                :info-message-body="infoMessageBody"
                :info-extra-action="infoExtraAction"
                @close-message="handleFeedbackTermination"
                @extra-action="handleExtraAction"
            ></info-message>
        </div>

        <!-- Warning Message Component   -->
        <div
            v-if="displayWarningMessage"
            class="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50"
        >
            <warning-message
                v-if="displayWarningMessage"
                :warning-header="warningMessageHeader"
                :warning-body="warningMessageBody"
                @first-warning-action="handleDeletionConfirmation"
                @secondary-warning-action="handleDeletionConfirmation"
            ></warning-message>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, ref } from 'vue'
import { fetchForumDataById } from '@/helpers/forum/fetchForumData'
import ForumLoadingState from '@/components/forumDiscussion/DiscussionLoading.vue'
import MessageDisplay from '@/components/forumDiscussion/MessageDisplay.vue'
import MessageForm from '@/components/forumDiscussion/MessageForm.vue'
import SuccessMessage from '@/components/messages/SuccessMessage.vue'
import InfoMessage from '@/components/messages/InfoMessage.vue'
import DiscussionHeader from '@/components/forumDiscussion/DiscussionHeader.vue'
import WarningMessage from '@/components/messages/WarningMessage.vue'
import { deleteForum } from '@/helpers/forum/deleteForum'
import { deleteMessage } from '@/helpers/forumDiscussion/deleteMessage'

// Represents the data of a forum post.
const forumData = ref<Forum>()

// Represents a list of forum tags.
const forumTags = ref<ForumTag[]>([])

interface Forum {
    id: number
    content: string
    timestamp: string
    title: string
    user: any
}

interface ForumTag {
    tagName: string
    tagDescription: string
}

// Prop for enabling dark mode
const props = defineProps({
    isDarkMode: Boolean,
})

const displayInfoMessage = ref<boolean>(false)
const displayWarningMessage = ref<boolean>(false)
const displaySuccessMessage = ref<boolean>(false)

const forumDeletionSuccess = ref<boolean>(false)

// Represents the info that gets displayed when the user is not logged in
const infoMessageHeader = ref<string>('')
const infoMessageBody = ref<string>('')
const infoExtraAction = ref<string>('')

const warningMessageHeader = ref<string>('')
const warningMessageBody = ref<string>('')

// Represents the state for controlling the display of the loading message.
const loadingMessageDisplayStatus = ref<boolean>(true)

// Represents the status of posting a message, and success or failure of posting.
const postingMessageStatus = ref<boolean>(false)
const postingMessageCRUDSuccess = ref<boolean>(false)

const successMessageHeader = ref<string>('')
const successMessageBody = ref<string>('')

// Compute dark mode class for the component
const isDarkModeClass = computed(() => (props.isDarkMode ? 'dark' : ''))

/**
 * Fetches and sets the forum post data, including associated tags, based on the forum ID
 * from the route parameters.
 *
 * @param {number} forumId - The ID of the forum post to fetch data for.
 * @returns {Promise<void>} A promise that resolves when the forum data and tags have been populated.
 */
async function populateForumHeader(forumId: number): Promise<void> {
    try {
        // Fetch forum data by ID
        const response = await fetchForumDataById(forumId)

        console.log(response.data)

        // Mapping forum categories to forum tags.
        if (response.data.forumCategories) {
            forumTags.value = response.data.forumCategories.map(
                (category: any) => ({
                    tagName: category.tagName || 'Unnamed Tag',
                    tagDescription:
                        category.tagDescription || 'No description available',
                })
            )
        }
        // Set the forum data object with the fetched data.
        forumData.value = response.data
    } catch (error) {
        // Log any errors that occur during data fetching.
        console.error('Error fetching forum data:', error)
    } finally {
        // Set loading state to false after data is loaded or an error occurs.
        loadingMessageDisplayStatus.value = false
    }
}

/**
 * Handles the feedback display based on the message posting status.
 *
 * @param {boolean} status - The status indicating whether a message is being posted (true) or has been posted successfully (false).
 */
function handleMessageFeedback(status: boolean): void {
    switch (status) {
        case true:
            // If posting a message, update status to show skeleton
            postingMessageStatus.value = true
            break
        case false:
            // If message posting was successful, update status to show message
            postingMessageStatus.value = false
            postingMessageCRUDSuccess.value = true
            break
    }
}

/**
 * Handles the check for user login status and displays an informational message if not logged in.
 *
 * This function checks the value of the `check` parameter to determine if the user is logged in.
 * If the user is not logged in, it sets the appropriate message in the `infoMessageHeader`,
 * `infoMessageBody`, and `infoExtraAction` to prompt the user to log in or register.
 *
 * @param {boolean} check - A boolean value indicating whether the user is logged in (true) or not (false).
 * @returns {void} - This function doesn't return anything; it modifies reactive state values for displaying messages.
 */
function handleLoggedInCheck(check: boolean): Boolean {
    if (!check) {
        displayInfoMessage.value = true
        infoMessageHeader.value = 'Login or Register Required'
        infoMessageBody.value = `
          You need to be logged in to proceed with this action.
          If you already have an account, please log in.
          If you're new here, please register to create an account and access this feature.
      `
        infoExtraAction.value = 'Login or Register'
        return false
    }
    return true
}

/**
 * Displays a warning message for forum deletion and asks for confirmation.
 */
function handleWarningMessageForumDeletionDisplay(): void {
    warningMessageHeader.value = 'Confirm Forum Deletion'
    warningMessageBody.value =
        'Are you sure you want to delete this forum? Deleting this forum will permanently remove all associated responses, comments, and any related data. This action cannot be undone.'
    displayWarningMessage.value = true
}

/**
 * Handles the confirmation of forum deletion based on user response.
 *
 * If the user confirms the deletion, the forum will be deleted, and success or failure messages will be displayed.
 * If the user cancels, the process will be aborted without any changes.
 *
 * @param {boolean} check - A boolean indicating whether the user confirmed the deletion (true) or canceled (false).
 * @returns {Promise<void>} - A promise that resolves after the forum deletion process completes,
 *                             either displaying a success message or aborting the deletion.
 */
async function handleDeletionConfirmation(check: boolean): Promise<void> {
    console.log(check)
    if (check) {
        displayWarningMessage.value = false
        const deletionSuccess = await handleForumDeletion()
        if (deletionSuccess) {
            forumDeletionSuccess.value = true
            displaySuccessMessage.value = true
            successMessageHeader.value = 'Forum Deleted!'
            successMessageBody.value =
                'The forum has been successfully deleted!'
        }
        return
    }
    displayWarningMessage.value = false
    return
}

/**
 * Deletes the forum post if the user confirms the deletion.
 */
async function handleForumDeletion(): Promise<Boolean> {
    if (forumData.value && forumData.value.id) {
        return await deleteForum(forumData.value.id)
    }
    return false
}

/**
 * Deletes a message by its ID and updates the UI based on the operation's success or failure.
 *
 * This function sets the loading state to true while the message deletion is in progress and
 * updates the success or failure states based on the response from the backend.
 *
 * @param {number} messageId - The ID of the message to be deleted.
 * @returns {Promise<boolean>} - Returns a Promise that resolves to a boolean:
 *                               - `true` if the message was successfully deleted,
 *                               - `false` if the deletion failed or if the `messageId` is invalid.
 */
async function handleMessageDeletion(messageId: number): Promise<boolean> {
    if (messageId) {
        // Call the helper function to delete the message
        const response = await deleteMessage(messageId)

        // If deletion was successful, hide loading and set the success state
        if (response) {
            postingMessageCRUDSuccess.value = true
            return true
        }

        // Log failure if the deletion wasn't successful
        console.error(
            'Failed to delete message with id: ',
            messageId,
            'Reason: ',
            response
        )
        return false
    }

    // Log an error if the message ID is not valid
    console.error('Not a valid messageId: ', messageId)
    return false
}

/**
 * Closes the success message component when the user acknowledges the message.
 *
 * If the forum deletion was successful, it redirects the user to the forum overview page.
 *
 * @returns {void} This function does not return anything; it modifies reactive state values
 * and potentially triggers a route navigation if the forum deletion was successful.
 */
function handleFeedbackTermination(): void {
    displayInfoMessage.value = false
    postingMessageCRUDSuccess.value = false
    displaySuccessMessage.value = false

    if (forumDeletionSuccess.value) {
        router.push('/ForumOverview')
        forumDeletionSuccess.value = false
    }

    return
}

const router = useRouter()

/**
 * Navigates the user to the login page.
 */
function handleExtraAction(): void {
    router.push('/Login')
}

// Get the current route for retrieving the forum ID parameter.
const route = useRoute()

// Fetch the forum data when the component is mounted.
onMounted(() => {
    populateForumHeader(Number(route.params.forumId))
})
</script>

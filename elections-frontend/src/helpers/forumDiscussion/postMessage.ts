import axios from 'axios'

/**
 * Posts a message to the server.
 *
 * @param {string} content - The content of the message to be posted.
 * @param {number} forumId - The ID of the forum where the message is posted.
 * @param {number} userId - The ID of the user posting the message.
 * @returns {Promise<boolean>} - Returns a promise resolving to `true` if the message is successfully posted, otherwise `false`.
 */
export async function postMessage(
    content: string,
    forumId: number | undefined,
    userId: number | undefined
): Promise<boolean> {
    if (!forumId) {
        throw new Error('forum ID is undefined')
    }

    if (!userId) {
        throw new Error('user ID is undefined')
    }

    try {
        const timestamp: string = new Date().toISOString()
        return await axios.post('http://localhost:8080/messages/create', {
            content: content,
            timestamp: timestamp,
            forumId: forumId,
            userId: userId,
        })
    } catch (error) {
        console.error('Error posting message data: ', error)
        return false
    }
}

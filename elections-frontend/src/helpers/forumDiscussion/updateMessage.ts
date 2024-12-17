import axios from 'axios'

/**
 * Updates the content of a message by its ID.
 *
 * @param id - The ID of the message to be updated.
 * @param content - The new content to update the message with.
 * @returns A promise that resolves to a boolean indicating whether the update was successful.
 * @throws Will log an error if the update request fails.
 */
export async function updateMessage(
    id: number,
    content: string
): Promise<boolean> {
    const timestamp: string = new Date().toISOString()
    try {
        // Sending the update request to the backend with the message ID, new content, and timestamp
        const response = await axios.post(
            `http://localhost:8080/messages/update/${id}`,
            {
                content: content,
                timestamp: timestamp,
            }
        )
        return response.status === 200
    } catch (error) {
        console.error('Error updating message: ', error)
        return false
    }
}

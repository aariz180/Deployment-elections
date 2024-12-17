import axios from 'axios'

/**
 * Fetches message data for a specific forum by ID.
 *
 * @param {string} id - The ID of the forum to fetch messages for.
 * @returns {Promise<any>} A promise that resolves with the message data or undefined if an error occurs.
 * @throws Will log an error to the console if the request fails.
 */
export async function fetchMessageData(id: number | undefined): Promise<any> {
    if (id === undefined) {
        throw new Error('id is undefined')
    }

    try {
        return await axios.get(`http://localhost:8080/messages/forum/${id}`)
    } catch (error) {
        console.error('Error fetching message data: ', error)
        return
    }
}

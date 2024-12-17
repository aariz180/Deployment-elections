import axios from 'axios'

/**
 * Fetches all available forum tags from the backend API.
 *
 * @returns {Promise<any>} A promise that resolves to the response data containing all forum tags.
 */
export async function fetchForumTags(): Promise<any> {
    try {
        return await axios.get('http://localhost:8080/forums/tags/all')
    } catch (error) {
        console.error('Error fetching forum tags:', error)
        return
    }
}

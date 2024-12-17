import axios from 'axios'

/**
 * Posts a new forum entry to the backend API.
 *
 * @param {string} content - The content of the forum post.
 * @param {string} title - The title of the forum post.
 * @param {number} userId - The ID of the user creating the post.
 * @param {number[]} tagIds - An array of tag IDs associated with the forum post.
 * @returns {Promise<boolean>} - Returns `true` if the post was successfully created, otherwise `false`.
 */
export async function postForum(
    content: string,
    title: string,
    userId: number,
    tagIds: number[]
): Promise<boolean> {
    try {
        const timestamp = new Date().toISOString()
        return await axios.post('http://localhost:8080/forums/create', {
            content: content,
            timestamp: timestamp,
            title: title,
            userId: userId,
            forumTagIds: tagIds,
        })
    } catch (error) {
        console.error('Error posting forum data: ', error)
        return false
    }
}

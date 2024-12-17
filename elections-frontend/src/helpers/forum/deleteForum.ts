import axios from 'axios'

/**
 * Deletes a forum post by its ID.
 *
 * Sends a DELETE request to the backend API to remove the forum post with the specified ID.
 * If the deletion request is successful, the function returns {@code true}. If an error occurs,
 * it logs the error to the console and returns {@code false}.
 *
 * @param id the ID of the forum post to delete
 * @return {@code true} if the forum post was successfully deleted, {@code false} otherwise
 */
export async function deleteForum(id: number): Promise<boolean> {
    try {
        return await axios.delete(`http://localhost:8080/forums/delete/${id}`)
    } catch (error) {
        console.error('Error deleting forum: ', error)
        return false
    }
}

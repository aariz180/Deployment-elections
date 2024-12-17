import axios from 'axios'

/**
 * Deletes a message by its ID.
 *
 * @param id - The ID of the message to be deleted.
 * @returns A promise that resolves to a boolean indicating whether the deletion was successful.
 * If an error occurs, it logs the error and returns `false`.
 */
export async function deleteMessage(id: number): Promise<boolean> {
    try {
        console.log(id)
        // Send a DELETE request to the server to delete the message with the specified ID
        await axios.delete(`http://localhost:8080/messages/delete/${id}`)
        return true
    } catch (error) {
        console.error('Error deleting message: ', error)
        return false
    }
}

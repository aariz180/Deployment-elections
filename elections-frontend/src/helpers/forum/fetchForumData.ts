import axios from 'axios'

/**
 * Fetches forum data from the server.
 *
 * @param {number} page - The page number of the forum data to be fetched.
 * @returns {Promise<any>} A promise that resolves to the forum data for the specified page.
 * @throws {Error} Logs an error message if the request fails.
 */
export async function fetchForumData(page: number): Promise<any> {
    try {
        return await axios.get(`http://localhost:8080/forums/all?page=${page}`)
    } catch (error) {
        console.error('Error fetching forum data: ', error)
        return
    }
}

/**
 * Fetches forum data in ascending order from the server.
 *
 * @param {number} page - The page number of the forum data to be fetched in ascending order.
 * @returns {Promise<any>} A promise that resolves to the ascending forum data for the specified page.
 * @throws {Error} Logs an error message if the request fails.
 */
export async function fetchForumDataASC(page: number): Promise<any> {
    try {
        return await axios.get(
            `http://localhost:8080/forums/all/asc?page=${page}`
        )
    } catch (error) {
        console.error('Error fetching ASCENDING forum data: ', error)
        return
    }
}

/**
 * Fetches forum data in descending order from the server.
 *
 * @param {number} page - The page number of the forum data to be fetched in descending order.
 * @returns {Promise<any>} A promise that resolves to the descending forum data for the specified page.
 * @throws {Error} Logs an error message if the request fails.
 */
export async function fetchForumDataDESC(page: number): Promise<any> {
    try {
        return await axios.get(
            `http://localhost:8080/forums/all/desc?page=${page}`
        )
    } catch (error) {
        console.error('Error fetching DESCENDING forum data: ', error)
        return
    }
}

export async function fetchForumDataById(id: number): Promise<any> {
    try {
        return await axios.get(`http://localhost:8080/forums/${id}`)
    } catch (error) {
        console.error('Error fetching forum data BY ID: ', error)
        return
    }
}

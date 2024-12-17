/**
 * Fetches user data from the browser's local storage.
 *
 * This function retrieves the value stored under the key 'userData' in localStorage.
 * If data is found, it parses and returns it as a JavaScript object.
 * If no data is found, it logs an error message to the console.
 *
 * @returns {Object|undefined} The parsed user data object if found, or undefined if no data exists.
 */
export function fetchUserFromLocalStorage() {
    const userData = localStorage.getItem('userData')
    if (userData) {
        return JSON.parse(userData)
    }
    console.error('No user data found!')
    return
}

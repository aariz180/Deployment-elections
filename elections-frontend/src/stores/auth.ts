import { defineStore } from 'pinia'
import axios from '../Axios' // Ensure you have a configured Axios instance
import { jwtDecode } from 'jwt-decode' // Import jwt-decode for decoding JWTs
import router from '@/router' // Adjust the path as necessary

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isSignedIn: !!localStorage.getItem('token'), // Initialize from local storage
        token: localStorage.getItem('token') || null, // Initialize token from local storage
        refreshToken: localStorage.getItem('refreshToken') || null, // Initialize refresh token from local storage
        userId: localStorage.getItem('userId') || null, // Store user ID
        email: '', // Track email for login
        name: '', // Track username
        gender: '', // Track gender
        voted: '', // Track voted for
        about: '', // Track about
        colour: '', // Track the colour of the user's profile
        initials: '', //Track the initials of the user
        successMessage: '', // For success messages
        errorMessage: '', // For error messages
        role: '',
        expiration: null, // Track token expiration
    }),

    actions: {
        // Load user data from localStorage when the app reloads
        loadUserFromStorage() {
            const userData = JSON.parse(
                <string>localStorage.getItem('userData')
            )
            if (userData) {
                this.userId = userData.userId
                this.email = userData.email
                this.name = userData.name
                this.gender = userData.gender
                this.voted = userData.voted
                this.about = userData.about
                this.colour = userData.colour
                this.initials = userData.initials
                this.isSignedIn = true
            }
        },

        // Login method that handles both credentials and token-based login
        async login(email: string, password: string) {
            try {
                if (this.token) {
                    // If there is a token in localStorage, use the token-based login
                    const decodedToken: any = jwtDecode(this.token)
                    this.userId = decodedToken.userId // Extract user ID from the token
                    this.expiration = decodedToken.exp // Token expiration
                    this.isSignedIn = true // Set logged-in state
                    console.log('Logged in using token')
                    await this.fetchUserDetails(<string>this.userId)
                    return {
                        token: this.token,
                        refreshToken: this.refreshToken,
                    }
                } else {
                    // If no token is present, use email and password to log in
                    const response = await axios.post('/auth/signin', {
                        email,
                        password,
                    })

                    // Store tokens in local storage
                    localStorage.setItem('token', response.data.token)
                    localStorage.setItem(
                        'refreshToken',
                        response.data.refreshToken
                    )

                    // Decode the token to get user ID and expiration
                    const decodedToken: any = jwtDecode(response.data.token)
                    this.userId = decodedToken.userId // Store user ID
                    this.expiration = decodedToken.exp // Store expiration timestamp

                    // Fetch user details after login
                    await this.fetchUserDetails(this.userId)

                    this.isSignedIn = true // Update the state
                    this.token = response.data.token // Set the token
                    this.successMessage = 'Login successful!' // Set success message

                    router.push('/') // Redirect to home or dashboard
                    this.errorMessage = '' // Clear any previous error messages
                    return {
                        token: response.data.token,
                        refreshToken: response.data.refreshToken,
                    }
                }
            } catch (error: any) {
                console.error('Login failed:', error)
                if (error.response) {
                    this.errorMessage =
                        error.response.data.message ||
                        'Login failed, please try again.'
                } else {
                    this.errorMessage = 'Error occurred. Please try again.'
                }
                this.successMessage = '' // Clear any previous success messages
                throw error
            }
        },

        // Fetch user details based on userIdMessageForm
        async fetchUserDetails(userId: string | null) {
            try {
                const response = await axios.get(`/users/details/${userId}`)
                if (response.status === 200) {
                    this.name = response.data.name
                    this.email = response.data.email
                    this.gender = response.data.gender
                    this.voted = response.data.voted
                    this.about = response.data.about
                    this.colour = response.data.colour
                    this.initials = response.data.initials
                    this.userId = response.data.id

                    // Store user data in local storage
                    localStorage.setItem(
                        'userData',
                        JSON.stringify({
                            userId: this.userId,
                            email: this.email,
                            name: this.name,
                            gender: this.gender,
                            voted: this.voted,
                            colour: this.colour,
                            initials: this.initials
                        })
                    )
                }
            } catch (error) {
                console.error('Error fetching user details:', error)
                this.errorMessage =
                    'Could not fetch user details. Please try again.'
            }
        },

        // Logout method to clear session
        logout() {
            this.isSignedIn = false
            this.token = null
            this.userId = null
            this.name = ''
            this.email = ''
            this.gender = ''
            this.voted = ''
            this.about = ''
            this.colour = ''
            this.initials = ''
            localStorage.removeItem('token')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('userData')
            router.push('/login') // Redirect to login page
        },

        // Additional method to refresh user data if needed
        async refreshUserData() {
            if (this.isSignedIn && this.refreshToken) {
                try {
                    const response = await axios.post('/auth/refresh', {
                        refreshToken: this.refreshToken,
                    })
                    localStorage.setItem('token', response.data.token)
                    this.token = response.data.token // Update token in store

                    // Optionally refresh user details
                    await this.fetchUserDetails(this.userId)
                } catch (error) {
                    console.error('Error refreshing user data:', error)
                    this.logout() // Logout on error
                }
            }
        },
    },
})

// axios.js
import axios from 'axios'

// Create an instance of axios
const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1', // Use environment variable or fallback
})

// Function to refresh the access token
const refreshAccessToken = async () => {
    const refreshToken = localStorage.getItem('refreshToken') // Adjust if you're using sessionStorage

    if (!refreshToken) {
        throw new Error('No refresh token found.')
    }

    // Call the refresh token endpoint
    const response = await axios.post('/auth/refresh', {
        refreshToken: refreshToken,
    })

    // Store new access token
    localStorage.setItem('token', response.data.token) // Update with the new token
    return response.data.token // Return the new access token
}

// Add a request interceptor
axiosInstance.interceptors.request.use(
    async (config) => {
        const token = localStorage.getItem('token')

        if (token) {
            config.headers['Authorization'] = `Bearer ${token}` // Set the token in headers
        }

        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// Add a response interceptor
axiosInstance.interceptors.response.use(
    (response) => {
        return response
    },
    async (error) => {
        const originalRequest = error.config

        // If access token is expired
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true // Prevent infinite loop

            try {
                const newToken = await refreshAccessToken() // Attempt to refresh token
                originalRequest.headers['Authorization'] = `Bearer ${newToken}`
                return axiosInstance(originalRequest) // Retry original request
            } catch (refreshError) {
                console.error('Failed to refresh token:', refreshError)
                // Optionally, redirect to login if refresh fails
                // const router = useRouter();
                // router.push('/login');
                return Promise.reject(refreshError)
            }
        }

        return Promise.reject(error)
    }
)

export default axiosInstance

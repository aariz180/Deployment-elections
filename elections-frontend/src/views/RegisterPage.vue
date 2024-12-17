<template>
    <div
        class="flex min-h-full flex-1 flex-col justify-center py-12 sm:px-6 lg:px-8 bg-gray-50 dark:bg-gray-900"
    >
        <!-- Add dark mode background -->
        <div class="sm:mx-auto sm:w-full sm:max-w-md">
            <img class="mx-auto h-10 w-auto" src="" alt="" />
            <h2
                class="mt-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900 dark:text-gray-200"
            >
                Create an account
            </h2>
            <!-- Add dark mode text color -->
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-md">
            <div
                class="bg-white dark:bg-gray-800 px-6 py-12 shadow sm:rounded-lg sm:px-12"
            >
                <!-- Add dark mode background -->
                <form @submit.prevent="handleRegister" class="space-y-6">
                    <div>
                        <label
                            for="email"
                            class="block text-sm font-medium leading-6 text-gray-900 dark:text-gray-200"
                            >Email address</label
                        >
                        <!-- Add dark mode text color -->
                        <div class="mt-2">
                            <input
                                id="email"
                                name="email"
                                type="email"
                                v-model="email"
                                required
                                class="block w-full rounded-md border-0 py-1.5 px-2 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-500 dark:bg-gray-700 dark:text-white"
                            />
                            <!-- Add dark mode styles -->
                        </div>
                    </div>

                    <div>
                        <label
                            for="password"
                            class="block text-sm font-medium leading-6 text-gray-900 dark:text-gray-200"
                            >Password</label
                        >
                        <!-- Add dark mode text color -->
                        <div class="mt-2 relative">
                            <input
                                id="password"
                                name="password"
                                :type="showPassword ? 'text' : 'password'"
                                v-model="password"
                                required
                                class="block w-full rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-500 dark:bg-gray-700 dark:text-white"
                            />
                            <!-- Add dark mode styles -->
                            <button
                                type="button"
                                @click="togglePasswordVisibility"
                                class="absolute right-2 top-1.5 text-gray-500 dark:text-gray-400"
                            >
                                {{ showPassword ? 'Hide' : 'Show' }}
                            </button>
                        </div>
                    </div>

                    <div>
                        <label
                            for="confirm-password"
                            class="block text-sm font-medium leading-6 text-gray-900 dark:text-gray-200"
                            >Confirm Password</label
                        >
                        <!-- Add dark mode text color -->
                        <div class="mt-2 relative">
                            <input
                                id="confirm-password"
                                name="confirm-password"
                                :type="showPassword ? 'text' : 'password'"
                                v-model="confirmPassword"
                                required
                                class="block w-full rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-500 dark:bg-gray-700 dark:text-white"
                            />
                            <!-- Add dark mode styles -->
                            <button
                                type="button"
                                @click="togglePasswordVisibility"
                                class="absolute right-2 top-1.5 text-gray-500 dark:text-gray-400"
                            >
                                {{ showPassword ? 'Hide' : 'Show' }}
                            </button>
                        </div>
                    </div>

                    <div>
                        <button
                            type="submit"
                            class="flex w-full justify-center rounded-md bg-sky-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-sky-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-sky-500"
                        >
                            Register
                        </button>
                    </div>
                </form>

                <div
                    v-if="errorMessage"
                    class="mt-4 text-red-500 text-sm text-center"
                >
                    {{ errorMessage }}
                </div>
                <div
                    v-if="successMessage"
                    class="mt-4 text-green-500 text-sm text-center"
                >
                    {{ successMessage }}
                </div>

                <div class="relative mt-10">
                    <div
                        class="absolute inset-0 flex items-center"
                        aria-hidden="true"
                    >
                        <div
                            class="w-full border-t border-gray-200 dark:border-gray-700"
                        />
                    </div>
                    <div
                        class="relative flex justify-center text-sm font-medium leading-6"
                    >
                        <span
                            class="bg-white px-6 text-gray-900 dark:bg-gray-800 dark:text-gray-200"
                            >Or continue with</span
                        >
                        <!-- Adjust text color for dark mode -->
                    </div>
                </div>

                <div class="mt-6 grid grid-cols-2 gap-4">
                    <a
                        href="#"
                        class="flex w-full items-center justify-center gap-3 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus-visible:ring-transparent dark:bg-gray-800 dark:text-gray-200 dark:ring-gray-700"
                    >
                        <!-- Add dark mode styles -->
                        <!-- Google Icon -->
                        <svg
                            class="h-5 w-5"
                            aria-hidden="true"
                            viewBox="0 0 24 24"
                        >
                            <path
                                d="M12.0003 4.75C13.7703 4.75 15.3553 5.36002 16.6053 6.54998L20.0303 3.125C17.9502 1.19 15.2353 0 12.0003 0C7.31028 0 3.25527 2.69 1.28027 6.60998L5.27028 9.70498C6.21525 6.86002 8.87028 4.75 12.0003 4.75Z"
                                fill="#EA4335"
                            />
                            <path
                                d="M23.49 12.275C23.49 11.49 23.415 10.73 23.3 10H12V14.51H18.47C18.18 15.99 17.34 17.25 16.08 18.1L19.945 21.1C22.2 19.01 23.49 15.92 23.49 12.275Z"
                                fill="#4285F4"
                            />
                            <path
                                d="M5.26498 14.2949C5.02498 13.5699 4.88501 12.7999 4.88501 11.9999C4.88501 11.1999 5.01998 10.4299 5.26498 9.7049L1.275 6.60986C0.46 8.22986 0 10.0599 0 11.9999C0 13.9399 0.46 15.7699 1.28 17.3899L5.26498 14.2949Z"
                                fill="#FBBC05"
                            />
                            <path
                                d="M12.0004 24.0001C15.2404 24.0001 17.9654 22.935 19.9454 21.095L16.0804 18.095C15.0054 18.82 13.6204 19.245 12.0004 19.245C8.8704 19.245 6.21537 17.135 5.2654 14.29L1.27539 17.385C3.25539 21.31 7.3104 24.0001 12.0004 24.0001Z"
                                fill="#34A853"
                            />
                        </svg>
                        <span class="text-sm font-semibold leading-6"
                            >Google</span
                        >
                    </a>

                    <a
                        href="#"
                        class="flex w-full items-center justify-center gap-3 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus-visible:ring-transparent dark:bg-gray-800 dark:text-gray-200 dark:ring-gray-700"
                    >
                        <!-- Add dark mode styles -->
                        <!-- GitHub Icon -->
                        <svg
                            class="h-5 w-5 fill-[#24292F]"
                            aria-hidden="true"
                            fill="currentColor"
                            viewBox="0 0 20 20"
                        >
                            <path
                                fill-rule="evenodd"
                                d="M10 0C4.477 0 0 4.484 0 10.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0110 4.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.203 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.942.359.31.678.921.678 1.856 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0020 10.017C20 4.484 15.522 0 10 0z"
                                clip-rule="evenodd"
                            />
                        </svg>
                        <span class="text-sm font-semibold leading-6"
                            >GitHub</span
                        >
                    </a>
                </div>
            </div>

            <p
                class="mt-10 text-center text-sm text-gray-500 dark:text-gray-400"
            >
                <!-- Add dark mode text color -->
                Already have an account?
                <router-link to="/login">
                    <span
                        class="font-semibold leading-6 text-sky-500 hover:text-sky-400"
                        >Log in</span
                    >
                </router-link>
            </p>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from '@/Axios' // Make sure you have axios installed: npm install axios
import { useAuthStore } from '@/stores/auth' // Import your auth store

// Reactive references for form inputs
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const showPassword = ref(false) // State for showing/hiding password
const errorMessage = ref('') // To handle error messages
const successMessage = ref('') // To handle success messages

// Initialize auth store
const authStore = useAuthStore()

// Simple handler function for the register button
const handleRegister = async () => {
    console.log('Register clicked', {
        email: email.value,
        password: password.value,
        confirmPassword: confirmPassword.value,
    })

    // Basic client-side validation
    if (password.value !== confirmPassword.value) {
        errorMessage.value = 'Passwords do not match.'
        return
    }

    try {
        const response = await axios.post(
            'http://localhost:8080/api/v1/auth/signup', // Adjust the URL as needed
            {
                email: email.value,
                password: password.value,
            }
        )

        // Handle the success response here
        console.log('Registration successful:', response.data)
        successMessage.value = 'Registration successful! You can now log in.' // Set success message

        // Log in the user automatically after successful registration
        await authStore.login(email.value, password.value)

        // Clear fields after successful registration
        email.value = ''
        password.value = ''
        confirmPassword.value = ''
        errorMessage.value = '' // Clear any previous error messages
    } catch (error: any) {
        // Handle the error response here
        if (error.response) {
            console.error('Error response:', error.response.data)
            errorMessage.value =
                error.response.data.message ||
                'Registration failed. Please try again.' // Update error message
        } else if (error.request) {
            console.error('Error request:', error.request)
            errorMessage.value =
                'No response from server. Please try again later.'
        } else {
            console.error('Error message:', error.message)
            errorMessage.value = 'Error occurred. Please try again.'
        }
    }
}

// Function to toggle password visibility
const togglePasswordVisibility = () => {
    showPassword.value = !showPassword.value
}
</script>

<style scoped>
/* Add your dark mode styles here */
.dark .bg-white {
    background-color: #1f2937; /* Dark background for the form */
}

.dark .text-gray-900 {
    color: #e5e7eb; /* Light text for dark mode */
}

.dark .text-gray-500 {
    color: #d1d5db; /* Light secondary text for dark mode */
}

/* Optional: Additional dark mode styles for hover states */
.dark .hover\:bg-gray-50:hover {
    background-color: #4b5563; /* Darker hover effect for dark mode */
}
</style>

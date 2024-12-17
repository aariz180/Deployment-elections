<template>
    <div
        class="flex min-h-full flex-1 flex-col justify-center py-12 sm:px-6 lg:px-8 bg-gray-50 dark:bg-gray-900"
    >
        <div class="sm:mx-auto sm:w-full sm:max-w-md">
            <h2
                class="mt-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900 dark:text-gray-200"
            >
                Sign in to your account
            </h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-md">
            <div
                class="bg-white dark:bg-gray-800 px-6 py-12 shadow sm:rounded-lg sm:px-12"
            >
                <form @submit.prevent="handleLogin" class="space-y-6">
                    <div>
                        <label
                            for="email"
                            class="block text-sm font-medium leading-6 text-gray-900 dark:text-gray-200"
                            >Email address</label
                        >
                        <div class="mt-2">
                            <input
                                id="email"
                                name="email"
                                type="email"
                                v-model="email"
                                required
                                class="block w-full rounded-md border-0 py-1.5 px-2 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-500 dark:bg-gray-700 dark:text-white"
                            />
                        </div>
                    </div>

                    <div>
                        <label
                            for="password"
                            class="block text-sm font-medium leading-6 text-gray-900 dark:text-gray-200"
                            >Password</label
                        >
                        <div class="mt-2">
                            <input
                                id="password"
                                name="password"
                                type="password"
                                v-model="password"
                                required
                                class="block w-full rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-500 dark:bg-gray-700 dark:text-white"
                            />
                        </div>
                    </div>

                    <div>
                        <button
                            type="submit"
                            class="flex w-full justify-center rounded-md bg-sky-500 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-sky-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-sky-500"
                        >
                            Sign in
                        </button>
                    </div>
                </form>

                <div
                    v-if="authStore.errorMessage"
                    class="mt-4 text-red-500 text-sm text-center"
                >
                    {{ authStore.errorMessage }}
                </div>
            </div>

            <p
                class="mt-10 text-center text-sm text-gray-500 dark:text-gray-400"
            >
                Don't have an account?
                <router-link to="/register"
                    ><a
                        href="#"
                        class="font-semibold leading-6 text-sky-500 hover:text-sky-500"
                        >Register here!</a
                    ></router-link
                >
            </p>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { jwtDecode } from 'jwt-decode' // Adjust the path as necessary

const email = ref('')
const password = ref('')
const authStore = useAuthStore()

const handleLogin = async () => {
    try {
        console.log('Email:', email.value) // Log email
        console.log('Password:', password.value) // Log password

        const { token, refreshToken } = await authStore.login(
            email.value,
            password.value
        )

        localStorage.setItem('token', token)
        localStorage.setItem('refreshToken', refreshToken)

        const decodedToken: any = jwtDecode(token)
        authStore.userId = decodedToken.userId
        authStore.role = decodedToken.role

        console.log('User logged in successfully')
        console.log('Current User ID:', authStore.userId)
        console.log('User Role:', authStore.role)

        // Fetch user details
        await fetchUserDetails(authStore.userId)
    } catch (error) {
        console.error('Login failed:', error)
        authStore.errorMessage = 'Login failed, please try again.'
    }
}

// Fetch user details function
const fetchUserDetails = async (userId: string | null) => {
    const token = localStorage.getItem('token')
    const response = await fetch(
        `http://localhost:8080/api/v1/users/details/${userId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }
    )

    if (response.ok) {
        const userDetails = await response.json()
        console.log('User Details:', userDetails)
    } else {
        console.error('Error fetching user details:', response.statusText)
    }
}
</script>

<style scoped>
/* Add any additional dark mode styles or overrides here */
</style>

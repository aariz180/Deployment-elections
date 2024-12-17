<template>
    <div
        class="flex flex-col gap-5 items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-800 px-24"
    >
        <div
            class="overflow-hidden bg-white dark:bg-gray-900 shadow sm:rounded-lg"
        >
            <div class="flex items-center px-4 py-6 sm:px-6">
                <!-- Account Info -->
                <div>
                    <h3
                        class="text-base font-semibold leading-7 text-gray-900 dark:text-sky-500"
                    >
                        Account
                    </h3>
                    <p
                        class="mt-1 max-w-2xl text-sm leading-6 text-gray-500 dark:text-gray-400"
                    >
                        Personal details
                    </p>
                </div>

                <!-- Profile Color Icon -->
                <p
                    class="h-11 w-11 rounded-full border border-black border-[1px] ml-auto"
                    :style="{ backgroundColor: authStore.colour || '#00ffff' }"
                    alt=""
                ></p>
            </div>

            <div class="border-t border-gray-100 dark:border-gray-700">
                <dl class="divide-y divide-gray-100 dark:divide-gray-700">
                    <div
                        class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6"
                    >
                        <dt
                            class="text-sm font-medium text-gray-900 dark:text-gray-300"
                        >
                            Full name
                        </dt>
                        <dd
                            class="mt-1 text-sm leading-6 text-gray-700 dark:text-gray-200 sm:col-span-2 sm:mt-0"
                        >
                            {{ user.fullName || 'Name not available' }}
                        </dd>
                    </div>
                    <div
                        class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6"
                    >
                        <dt
                            class="text-sm font-medium text-gray-900 dark:text-gray-300"
                        >
                            Gender
                        </dt>
                        <dd
                            class="mt-1 text-sm leading-6 text-gray-700 dark:text-gray-200 sm:col-span-2 sm:mt-0"
                        >
                            {{ user.gender || 'Gender not specified' }}
                        </dd>
                    </div>
                    <div
                        class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6"
                    >
                        <dt
                            class="text-sm font-medium text-gray-900 dark:text-gray-300"
                        >
                            Email address
                        </dt>
                        <dd
                            class="mt-1 text-sm leading-6 text-gray-700 dark:text-gray-200 sm:col-span-2 sm:mt-0"
                        >
                            {{ user.email }}
                        </dd>
                    </div>
                    <div
                        class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6"
                    >
                        <dt
                            class="text-sm font-medium text-gray-900 dark:text-gray-300"
                        >
                            Voted for:
                        </dt>
                      <dd
                          class="mt-1 text-sm leading-6 text-gray-700 dark:text-gray-200 sm:col-span-2 sm:mt-0 flex items-center gap-2"
                      >
                        <!-- Display the logo with a border if available -->
                        <img
                            v-if="user.voted !== 'Secret' && partyLogo !== 'No vote registered'"
                            :src="partyLogo"
                            alt="Party Logo"
                            class="h-14 p-1 border border-gray-300 rounded"
                        />
                        <!-- Show "Prefer not to tell" for Secret or "No vote registered" for default -->
                        <span v-else>
        {{ user.voted === 'Secret' ? 'Prefer not to tell' : 'No vote registered' }}
    </span>
                      </dd>

                    </div>

                    <div
                        class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6"
                    >
                        <dt
                            class="text-sm font-medium text-gray-900 dark:text-gray-300"
                        >
                            About
                        </dt>
                        <dd
                            class="mt-1 text-sm leading-6 text-gray-700 dark:text-gray-200 sm:col-span-2 sm:mt-0"
                        >
                            {{
                                user.about || 'About information not available'
                            }}
                        </dd>
                    </div>
                </dl>
            </div>
        </div>
        <!-- Move the router-link outside the main container -->
        <router-link to="/updateAccountPage">
            <a
                class="w-full py-2 px-4 bg-sky-400 text-white rounded-md shadow hover:bg-sky-500 focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-offset-2 mt-4"
            >
                Update account
            </a>
        </router-link>
      <router-link to="/login" v-if="authStore.isSignedIn">
        <button
            @click="logout"
            class="text-gray-400 hover:text-white"
            aria-label="Logout"
        >
          Logout
        </button>
      </router-link>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'

// Get the auth store
const authStore = useAuthStore()

// Ref to store the user data
const user = ref({
    fullName: '',
    gender: '',
    email: '',
    voted: '',
    about: '',
    colour: '',
    initials:''
})

// Fetch the user's data when the component mounts
onMounted(async () => {
    const userId = authStore.userId // Get the logged-in user's ID
    const token = localStorage.getItem('token') // Get the token from local storage

    if (userId && token) {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/v1/users/${userId}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`, // Add the token to the request headers
                    },
                }
            )

            // Map the available fields directly from the response
            user.value = {
                fullName: response.data.fullName || '',
                gender: response.data.gender || '',
                email: response.data.email || '',
                voted: response.data.voted || '',
                about: response.data.about || '',
                colour: response.data.colour || '',
                initials: response.data.initials || ''
            }
        } catch (error) {
            console.error('Error fetching user data:', error)
        }
    } else {
        console.error('User ID or Token not found')
    }
})
//test

// Computed property to get the logo path based on the user's vote
const partyLogo = computed(() => {
  const basePath = './src/assets/';
  const party = user.value.voted;

  switch (party) {
    case 'PVV':
      return `${basePath}PVV logo.jpg`;
    case 'GL-PvdA':
      return `${basePath}PvdA-GroenLinks logo.jpg`;
    case 'VVD':
      return `${basePath}VVD logo.png`;
    case 'NSC':
      return `${basePath}NSC logo.svg`;
    case 'D66':
      return `${basePath}D66 logo.png`;
    case 'BBB':
      return `${basePath}BBB logo.png`;
    case 'CDA':
      return `${basePath}CDA logo.png`;
    case 'SP':
      return `${basePath}SP logo.png`;
    case 'DENK':
      return `${basePath}DENK logo.png`;
    case 'PvdD':
      return `${basePath}PvdD logo.png`;
    case 'FvD':
      return `${basePath}FvD logo.png`;
    case 'SGP':
      return `${basePath}SGP logo.png`;
    case 'CU':
      return `${basePath}CU logo.png`;
    case 'Volt':
      return `${basePath}Volt logo.png`;
    case 'JA21':
      return `${basePath}JA21 logo.png`;
    case 'Secret':
      return "Prefer not to tell";
    default:
      return 'No vote registered';
  }
});

</script>

<style>
body {
    margin: 0;
    padding: 0;
    font-family: 'Arial', sans-serif;
}
</style>

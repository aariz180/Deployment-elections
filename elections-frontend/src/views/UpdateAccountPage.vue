<template>
    <div
        class="flex flex-col gap-5 items-center justify-center min-h-screen bg-gray-100 dark:bg-gray-800 px-6 sm:px-8 md:px-12 lg:px-24"
    >
        <!-- Update Account Section Container -->
        <div
            class="overflow-hidden bg-white dark:bg-gray-900 shadow sm:rounded-lg w-full sm:w-3/4 lg:w-1/2 max-w-3xl"
        >
            <!-- Header section with Account Info and Profile Color Icon -->
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
                <p
                    class="relative h-12 w-12 sm:h-14 sm:w-14 rounded-full border border-black ml-auto flex items-center justify-center overflow-hidden"
                    :style="{ backgroundColor: user.colour || '#00ffff' }"
                    alt=""
                >
                    <input
                        id="profileColor"
                        v-model="user.colour"
                        type="color"
                        class="absolute top-0 left-0 w-full h-full opacity-0 cursor-pointer"
                    />
                </p>
            </div>

            <!-- Form for updating user information -->
            <form
                @submit.prevent="updateUser"
                class="border-t border-gray-100 dark:border-gray-700 p-6 space-y-6"
            >
                <!-- Full Name Input -->
                <div
                    class="form-group grid grid-cols-1 sm:grid-cols-3 gap-4 sm:px-6"
                >
                    <label
                        for="fullName"
                        class="text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                        Full Name
                    </label>
                    <input
                        id="fullName"
                        v-model="user.fullName"
                        type="text"
                        placeholder="Full Name"
                        class="mt-1 sm:mt-0 sm:col-span-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 hover:border-sky-500 sm:text-sm"
                        required
                    />
                </div>

                <!-- Gender Input -->
                <div
                    class="form-group grid grid-cols-1 sm:grid-cols-3 gap-4 sm:px-6"
                >
                    <label
                        for="gender"
                        class="text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                        Gender
                    </label>
                    <select
                        id="gender"
                        v-model="user.gender"
                        class="mt-1 sm:mt-0 sm:col-span-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-sky-500 focus:border-sky-500 hover:border-sky-500 sm:text-sm"
                    >
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Else">Else</option>
                        <option value="No Tell">Prefer not to tell</option>
                    </select>
                </div>

                <!-- Email Input -->
                <div
                    class="form-group grid grid-cols-1 sm:grid-cols-3 gap-4 sm:px-6"
                >
                    <label
                        for="email"
                        class="text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                        Email
                    </label>
                    <input
                        id="email"
                        v-model="user.email"
                        type="email"
                        placeholder="Email"
                        class="mt-1 sm:mt-0 sm:col-span-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 hover:border-sky-500 sm:text-sm"
                        required
                    />
                </div>

                <!-- Voted Select -->
                <div
                    class="form-group grid grid-cols-1 sm:grid-cols-3 gap-4 sm:px-6"
                >
                    <label
                        for="voted"
                        class="text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                        Voted
                    </label>
                    <select
                        id="voted"
                        v-model="user.voted"
                        class="mt-1 sm:mt-0 sm:col-span-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 hover:border-sky-500 sm:text-sm"
                    >
                        <option value="PVV">
                            Partij voor de Vrijheid (PVV)
                        </option>
                        <option value="GL-PvdA">GroenLinks-PvdA</option>
                        <option value="VVD">
                            Volkspartij voor Vrijheid en Democratie (VVD)
                        </option>
                        <option value="NSC">
                            Nieuw Sociaal Contract (NSC)
                        </option>
                        <option value="D66">Democraten 66 (D66)</option>
                        <option value="BBB">BoerBurgerBeweging (BBB)</option>
                        <option value="CDA">
                            Christen-Democratisch Appèl (CDA)
                        </option>
                        <option value="SP">Socialistische Partij (SP)</option>
                        <option value="DENK">DENK</option>
                        <option value="PvdD">
                            Partij voor de Dieren (PvdD)
                        </option>
                        <option value="FvD">Forum voor Democratie (FvD)</option>
                        <option value="SGP">
                            Staatkundig Gereformeerde Partij (SGP)
                        </option>
                        <option value="CU">ChristenUnie (CU)</option>
                        <option value="Volt">Volt Nederland</option>
                        <option value="JA21">JA21</option>
                        <option value="Not Voted">Not Voted</option>
                        <option value="Secret">Prefer not to tell</option>
                    </select>
                </div>

                <!-- About Input -->
                <div
                    class="form-group grid grid-cols-1 sm:grid-cols-3 gap-4 sm:px-6"
                >
                    <label
                        for="about"
                        class="text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                        About
                    </label>
                    <textarea
                        id="about"
                        v-model="user.about"
                        placeholder="Tell us about yourself..."
                        class="mt-1 sm:mt-0 sm:col-span-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 hover:border-sky-500 sm:text-sm"
                    ></textarea>
                </div>
            </form>
        </div>

        <!-- Save Changes Button Outside of Form -->
        <button
            @click="updateUser"
            class="w-full sm:w-1/2 lg:w-1/4 max-w-3xl py-2 px-4 bg-sky-400 text-white rounded-md shadow hover:bg-sky-500 focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-offset-2 mt-4"
        >
            Save changes
        </button>
    </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';
import router from '@/router'; // Adjust the path as necessary

// Get the auth store
const authStore = useAuthStore();

// Ref to store the user data
const user = ref({
  fullName: '',
  gender: '',
  email: '',
  voted: '',
  about: '',
  colour: '#00ffff', // Default color (can be updated by the user)
  initials: '',
});

function getInitials(fullName) {
  if (!fullName || fullName.trim() === '') {
    return '';
  }
  const words = fullName.trim().split(/\s+/).filter(word => word.length > 0);
  if (words.length === 1) {
    return words[0].charAt(0).toUpperCase();
  }
  const firstInitial = words[0].charAt(0).toUpperCase() || '';
  const lastInitial = words[words.length - 1].charAt(0).toUpperCase() || '';
  return `${firstInitial}${lastInitial}`;
}



// Fetch the user's data when the component mounts
onMounted(async () => {
  const userId = authStore.userId; // Get the logged-in user's ID
  const token = localStorage.getItem('token'); // Get the token from local storage

  if (userId && token) {
    try {
      const response = await axios.get(
          `http://localhost:8080/api/v1/users/${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`, // Add the token to the request headers
            },
          }
      );

      user.value = {
        fullName: response.data.fullName || '',
        gender: response.data.gender || '',
        email: response.data.email || '',
        voted: response.data.voted || '',
        about: response.data.about || '',
        colour: response.data.colour || '#00ffff', // Default to #00ffff if none set
        initials: response.data.initials || '',
      };
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  } else {
    console.error('User ID or Token not found');
  }
});

// Function to update the user data
const updateUser = async () => {
  const userId = authStore.userId; // Get the logged-in user's ID
  const token = localStorage.getItem('token'); // Get the token from local storage

  if (userId && token) {
    try {
      // Compute initials from the user's full name
      const initials = getInitials(user.value.fullName);

      const updatedData = {
        fullName: user.value.fullName,
        gender: user.value.gender,
        email: user.value.email,
        voted: user.value.voted,
        about: user.value.about,
        colour: user.value.colour,
        initials: initials, // Use computed initials here
      };

      // Include password only if it's being changed
      if (user.value.password) {
        updatedData.password = user.value.password;
      }

      await axios.post(
          `http://localhost:8080/api/v1/users/update/${userId}`,
          updatedData,
          {
            headers: {
              Authorization: `Bearer ${token}`, // Add the token to the request headers
            },
          }
      );

      // Save updated user data back to localStorage
      localStorage.setItem(
          'userData',
          JSON.stringify({
            userId: userId,
            email: user.value.email,
            name: user.value.fullName,
            gender: user.value.gender,
            voted: user.value.voted,
            colour: user.value.colour,
            initials: initials, // Save computed initials as well
          })
      );

      console.log('User updated successfully');
      alert('User details updated successfully');
      router.push('/accountPage'); // Redirect to home or dashboard
    } catch (error) {
      console.error('Error updating user:', error);
      alert('Error occurred while updating your account');
    }
  } else {
    console.error('User ID or Token not found');
    alert('User not found or not authorized');
  }
};
</script>



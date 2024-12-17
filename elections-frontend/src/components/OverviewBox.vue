<script lang="ts" setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

// A way to pass data from a parent component to a child component.
const props = defineProps({
    partyName: {
        type: String,
        required: true,
    },
    partyOrigin: {
        type: String,
        required: true,
    },
    partyOrientation: {
        type: String,
        required: true,
    },
    partyDescription: {
        type: String,
        required: true,
    },
    partySummary: {
        type: String,
        required: true,
    },
    partyEcoscore: {
        type: Number,
        required: true,
    },
    imageUrl: {
        type: String,
        required: true,
    },
})

// Use router to navigate between pages
const router = useRouter()

// Function to redirect to party details page
async function selectParty(partyName: String): Promise<any> {
    try {
        const response = await axios.get(
            `http://localhost:8080/parties/${partyName}`
        )
        console.log('Minions have spawned in')
        console.log(response.data) // Log the retrieved data
        router.push({ path: `/parties/${partyName}` })
    } catch (error) {
        console.error('Error fetching single party data ', error)
    }
}
</script>

<template>
  <div
      class="flex flex-col md:flex-row max-w-4xl w-full h-auto rounded-lg shadow-md overflow-hidden"
      style="background-color: #4e6766"
  >
    <!-- Image Section (appears first on small screens) -->
    <div
        class="flex justify-center items-center w-full md:w-2/5 p-4 bg-white"
    >
      <img
          alt="Party Logo"
          :src="imageUrl"
          class="w-40 h-40 md:w-64 md:h-64 object-contain rounded-full border-2 border-gray-800"
      />
    </div>

    <!-- Party information -->
    <div
        class="flex flex-col w-full md:w-3/5 p-4 text-gray-50"
    >
      <h2 class="text-2xl md:text-3xl font-semibold mb-2">
        {{ partyName }}
      </h2>
      <p class="text-sm md:text-base mb-1">
        <strong>Founded:</strong> {{ partyOrigin }}
      </p>
      <p class="text-sm md:text-base mb-1">
        <strong>Orientation:</strong> {{ partyOrientation }}
      </p>
      <p class="text-sm md:text-base mb-1">
        <strong>Eco friendly score:</strong> {{ partyEcoscore }}
      </p>
      <br />
      <p class="text-sm md:text-base mb-1">
        <strong>Description:</strong> {{ partyDescription }}
      </p>
      <p class="text-sm md:text-base italic mb-1">
        <strong>History:</strong> {{ partySummary }}
      </p>
      <button
          @click="selectParty(partyName)"
          class="mt-4 bg-blue-600 text-white py-2 w-full md:w-40 rounded hover:bg-blue-700 transition"
      >
        Select Party
      </button>
    </div>
  </div>
</template>


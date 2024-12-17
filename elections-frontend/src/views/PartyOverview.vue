<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import OverviewBox from '@/components/OverviewBox.vue'
import axios from 'axios'

const parties = ref([]) // Create a reactive variable for storing the list of political parties
const selectedFilter = ref('Standard Layout') // Default filter option
const searchQuery = ref('') // Holds the search input value

onMounted(async () => {
    try {
        const response = await axios.get('http://localhost:8080/parties/all')
        parties.value = response.data
        console.log('Retrieved partyOverview data:', parties.value)
    } catch (error) {
        console.error('Error fetching partyOverview data:', error)
    }
})

// Function to get the image URL based on party name
const getImageUrl = (partyName: string) => {
    const images: any = {
        PVV: './src/assets/PVV logo.jpg',
        BBB: './src/assets/BBB logo.png',
        CDA: './src/assets/CDA logo.png',
        ChristenUnie: './src/assets/CU logo.png',
        D66: './src/assets/D66 logo.png',
        DENK: './src/assets/DENK logo.png',
        FvD: './src/assets/FvD logo.png',
        JA21: './src/assets/JA21 logo.png',
        NSC: './src/assets/NSC logo.svg',
        'GroenLinks-PvdA': './src/assets/PvdA-Groenlinks logo.jpg',
        PvdD: './src/assets/PvdD logo.png',
        SGP: './src/assets/SGP logo.png',
        SP: './src/assets/SP logo.png',
        Volt: './src/assets/VOLT logo.png',
        VVD: './src/assets/VVD logo.png',
    }
    return images[partyName] || null
}

// This function creates a computed property. It returns a reactive value that is recalculated automatically.
const filteredAndSortedParties: any = computed(() => {
    // Filter parties that match the search query
    let filtered = parties.value.filter((party: any) =>
        party.partyName.toUpperCase().includes(searchQuery.value.toUpperCase())
    )

    // Sort the filtered parties based on the selected filter option
    switch (selectedFilter.value) {
        case 'A-Z':
            return filtered.sort((a: any, b: any) =>
                a.partyName.localeCompare(b.partyName)
            )
        case 'Z-A':
            return filtered.sort((a: any, b: any) =>
                b.partyName.localeCompare(a.partyName)
            )
        case 'Oldest Party - Youngest Party':
            return filtered.sort((a: any, b: any) => {
                const dateA = new Date(a.partyOrigin)
                const dateB = new Date(b.partyOrigin)

                // Check if both dates are valid
                if (isNaN(dateA.getTime()) || isNaN(dateB.getTime())) {
                    return 0 // or handle the error case as needed
                }

                return dateA.getTime() - dateB.getTime()
            })

        case 'Youngest Party - Oldest Party':
            return filtered.sort((a: any, b: any) => {
                const dateA = new Date(a.partyOrigin)
                const dateB = new Date(b.partyOrigin)

                // Check if both dates are valid
                if (isNaN(dateA.getTime()) || isNaN(dateB.getTime())) {
                    return 0 // or handle the error case as needed
                }

                return dateB.getTime() - dateA.getTime()
            })

        case 'Top Eco Performer - Eco Underperformer':
            return filtered.sort(
                (a: any, b: any) => b.partyEcoscore - a.partyEcoscore
            )
        case 'Eco Underperformer - Top Eco Performer':
            return filtered.sort(
                (a: any, b: any) => a.partyEcoscore - b.partyEcoscore
            )
        default:
            return filtered
    }
})
</script>

<template>
  <div
      class="bg-gray-50 dark:bg-gray-800 py-24 sm:py-32 flex flex-col justify-center items-center"
  >
    <!-- Header -->
    <header class="text-center mb-10 px-4">
      <h1 class="text-2xl sm:text-3xl font-bold text-gray-900 dark:text-white">
        Select Your Preferred Political Party
      </h1>
    </header>

    <!-- Search Bar & Filter Section -->
    <div
        class="mb-4 flex flex-col sm:flex-row justify-between items-center w-full max-w-4xl px-4"
    >
      <!-- Search Bar -->
      <div class="flex flex-col sm:flex-row items-center w-full sm:w-1/2 mb-4 sm:mb-0">
        <label
            for="myInput"
            class="mr-2 text-gray-700 dark:text-white whitespace-nowrap mb-2 sm:mb-0"
        >Search By:</label
        >
        <input
            v-model="searchQuery"
            id="myInput"
            type="text"
            placeholder="Party Name..."
            class="border rounded p-2 w-full sm:w-auto"
        />
      </div>

      <!-- Filter Dropdown -->
      <div class="flex flex-col sm:flex-row items-center w-full sm:w-1/2">
        <label
            for="filter"
            class="mr-2 text-gray-700 dark:text-white whitespace-nowrap mb-2 sm:mb-0"
        >Sort By:</label
        >
        <select
            v-model="selectedFilter"
            id="filter"
            class="border rounded p-2 w-full sm:w-auto"
        >
          <option value="Standard Layout">Standard</option>
          <option value="A-Z">A-Z</option>
          <option value="Z-A">Z-A</option>
          <option value="Youngest Party - Oldest Party">
            Youngest Party - Oldest Party
          </option>
          <option value="Oldest Party - Youngest Party">
            Oldest Party - Youngest Party
          </option>
          <option value="Top Eco Performer - Eco Underperformer">
            Top Eco Performer - Eco Underperformer
          </option>
          <option value="Eco Underperformer - Top Eco Performer">
            Eco Underperformer - Top Eco Performer
          </option>
        </select>
      </div>
    </div>

  <!-- Party List -->
        <section class="flex flex-col gap-6">
            <OverviewBox
                v-for="partyOverview in filteredAndSortedParties"
                :key="partyOverview.partyId"
                :partyDescription="partyOverview.partyDescription"
                :partyName="partyOverview.partyName"
                :partyOrientation="partyOverview.partyOrientation"
                :partyOrigin="partyOverview.partyOrigin"
                :partySummary="partyOverview.partySummary"
                :partyEcoscore="partyOverview.partyEcoscore"
                :imageUrl="getImageUrl(partyOverview.partyName)"
            />
        </section>
    </div>
</template>

<style scoped>
/* Additional styles can go here if needed */
</style>

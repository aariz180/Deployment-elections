<script lang="ts" setup>
import PVVLogo from '@/assets/PVV logo.jpg'
import BBBLogo from '@/assets/BBB logo.png'
import CDALogo from '@/assets/CDA logo.png'
import CUlogo from '@/assets/CU logo.png'
import D66Logo from '@/assets/D66 logo.png'
import DENKLogo from '@/assets/DENK logo.png'
import FvDLogo from '@/assets/FvD logo.png'
import JA21Logo from '@/assets/JA21 logo.png'
import NSCLogo from '@/assets/NSC logo.svg'
import PvdAGroenLinksLogo from '@/assets/PvdA-Groenlinks logo.jpg'
import PvdDLogo from '@/assets/PvdD logo.png'
import SGPLogo from '@/assets/SGP logo.png'
import SPLogo from '@/assets/SP logo.png'
import VoltLogo from '@/assets/VOLT logo.png'
import VVDLogo from '@/assets/VVD logo.png'
import {ref, onMounted, computed} from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import ChartParties from '@/components/ChartParties.vue'

const route = useRoute()
const partyInfo = ref<any>(null) // Allows targeting HTML elements
const candidatesInfo = ref<Array<any>>([]) // Initially an empty array of candidates
const candidateSearchQuery = ref('') // Search query input
const candidateSelectedFilter = ref('Standard Layout') // Default filter value

// The onMounted lifecycle runs after the component is rendered to the DOM
onMounted(async () => {
    const partyName = route.params.partyName // Get the party name from the route parameters
    try {
        // Use Promise.all to fetch data from both URLs simultaneously
        const [partyResponse, candidatesResponse] = await Promise.all([
            //Promise allows to use 2 url links
            axios.get(`http://localhost:8080/parties/${partyName}`),
            axios.get(`http://localhost:8080/candidates/${partyName}`),
        ])

        // Store the fetched data
        partyInfo.value = partyResponse.data
        candidatesInfo.value = candidatesResponse.data

        console.log('Retrieved party information:', partyInfo.value)
        console.log('Retrieved candidates information:', candidatesInfo.value)
    } catch (error) {
        console.error('Error fetching party or candidates information:', error)
    }
})

const getImageInfo = (partyName: string) => {
    const images: any = {
        PVV: PVVLogo,
        BBB: BBBLogo,
        CDA: CDALogo,
        ChristenUnie: CUlogo,
        D66: D66Logo,
        DENK: DENKLogo,
        FvD: FvDLogo,
        JA21: JA21Logo,
        NSC: NSCLogo,
        'GroenLinks-PvdA': PvdAGroenLinksLogo,
        PvdD: PvdDLogo,
        SGP: SGPLogo,
        SP: SPLogo,
        Volt: VoltLogo,
        VVD: VVDLogo,
    }
    return images[partyName] || null // Default to null if no image found
}

// Filters and sorts candidates dynamically
const filteredAndSortedCandidates = computed(() => {
  // Return empty if candidatesInfo is null or not an array
  if (!candidatesInfo.value || !Array.isArray(candidatesInfo.value)) {
    return []
  }

  let filtered = candidatesInfo.value.filter((candidate: any) =>
      candidate.candidateName
          .toUpperCase()
          .includes(candidateSearchQuery.value.toUpperCase())
  )

  switch (candidateSelectedFilter.value) {
    case 'A-Z':
      return filtered.sort((a: any, b: any) =>
          a.candidateName.localeCompare(b.candidateName)
      )
    case 'Z-A':
      return filtered.sort((a: any, b: any) =>
          b.candidateName.localeCompare(a.candidateName)
      )
    case 'Highest to Lowest':
      return filtered.sort((a: any, b: any) =>
          parseInt(a.seatPosition) - parseInt(b.seatPosition)
      )
    case 'Lowest to Highest':
      return filtered.sort((a: any, b: any) =>
          parseInt(b.seatPosition) - parseInt(a.seatPosition)
      )
    default:
      return filtered
  }
})
</script>

<template>
  <div class="bg-gray-50 min-h-screen p-4 sm:p-8">
    <div class="max-w-5xl mx-auto bg-white rounded-lg shadow-xl overflow-hidden">
      <!-- Header Section with Logo -->
      <div
          class="flex flex-col items-center text-center"
          style="background-color: #4e6766; padding: 2rem"
      >
        <img
            v-if="partyInfo"
            :src="getImageInfo(partyInfo.partyName)"
            alt="Party Logo"
            class="w-32 h-32 sm:w-64 sm:h-64 object-contain rounded-full bg-white"
        />
        <h1
            v-if="partyInfo"
            class="text-3xl sm:text-5xl font-extrabold mb-2 text-white"
        >
          {{ partyInfo.partyName }}
        </h1>
        <p v-if="partyInfo" class="text-base sm:text-lg text-white mb-1">
          <strong>Date of Origin:</strong> {{ partyInfo.partyOrigin }}
        </p>
        <p v-if="partyInfo" class="text-base sm:text-lg text-white mb-4">
          <strong>Political Orientation:</strong>
          {{ partyInfo.partyOrientation }}
        </p>
        <p v-else class="text-base sm:text-lg text-white">
          Loading party information...
        </p>
      </div>

      <!-- Summary Section -->
      <div class="p-4 sm:p-8 bg-gray-100" v-if="partyInfo">
        <h2 class="text-2xl sm:text-3xl font-semibold mb-4 text-gray-800">
          In Short
        </h2>
        <p class="text-gray-700 mb-4 leading-relaxed text-sm sm:text-lg">
          {{ partyInfo.partySummary }}
        </p>
      </div>

      <!-- History Section -->
      <div class="p-4 sm:p-8 bg-gray-200" v-if="partyInfo">
        <h2 class="text-2xl sm:text-3xl font-semibold mb-4 text-gray-800">
          Historical Progress
        </h2>
        <ul class="text-gray-700 mb-4 leading-relaxed text-sm sm:text-lg list-disc pl-5">
          <li
              v-for="(sentence, index) in partyInfo.partyHistory.split('. ')"
              :key="index"
              class="mb-2"
          >
            <strong>{{ sentence.split(' ')[0] }}</strong>
            {{ sentence.substring(sentence.indexOf(' ') + 1).trim() }}
          </li>
        </ul>
      </div>

      <!-- Future Plans Section -->
      <div class="p-4 sm:p-8 bg-gray-100" v-if="partyInfo">
        <h2 class="text-2xl sm:text-3xl font-semibold mb-4 text-gray-800">
          Future Plans
        </h2>
        <ul class="list-disc list-inside mb-4 text-sm sm:text-lg text-gray-700">
          <li
              v-for="(plan, index) in partyInfo.partyFuture.split('. ')"
              :key="index"
              class="mb-2"
          >
            {{ plan.trim() }}
          </li>
        </ul>
      </div>

      <!-- Ecological Goals Section -->
      <div class="p-4 sm:p-8 bg-gray-200" v-if="partyInfo">
        <h2 class="text-2xl sm:text-3xl font-semibold mb-4 text-gray-800">
          Ecological Goals
        </h2>
        <ul class="list-disc list-inside mb-4 text-sm sm:text-lg text-gray-700">
          <li
              v-for="(goal, index) in partyInfo.partyEcological.split('. ')"
              :key="index"
              class="mb-2"
          >
            {{ goal.trim() }}
          </li>
        </ul>
      </div>

      <ChartParties></ChartParties>

      <!-- Candidates Section -->
      <div class="p-4 sm:p-8 bg-gray-200" v-if="filteredAndSortedCandidates.length > 0">
        <!-- Header Section with Search and Filter -->
        <div class="flex flex-col sm:flex-row items-center justify-between mb-6">
          <h2 class="text-xl sm:text-2xl lg:text-3xl font-semibold text-gray-800 mb-4 sm:mb-0">
            Candidates
          </h2>

          <!-- Filter Dropdown and Search Bar -->
          <div class="flex flex-col sm:flex-row items-center space-y-2 sm:space-y-0 sm:space-x-4 w-full sm:w-auto">
            <!-- Search Bar -->
            <input
                v-model="candidateSearchQuery"
                type="text"
                placeholder="Search candidates..."
                class="border rounded p-1 sm:p-2 w-full sm:w-48 text-sm"
            />

            <!-- Sort Dropdown -->
            <div class="flex flex-col sm:flex-row items-center w-full sm:w-auto">
              <label
                  for="candidateFilter"
                  class="text-gray-700 text-xs sm:text-base mr-2 whitespace-nowrap"
              >
                Sort By:
              </label>
              <select
                  v-model="candidateSelectedFilter"
                  id="candidateFilter"
                  class="border rounded p-1 sm:p-2 w-full sm:w-auto text-sm"
              >
                <option value="Standard Layout">Standard</option>
                <option value="A-Z">A-Z</option>
                <option value="Z-A">Z-A</option>
                <option value="Highest to Lowest">Highest Position</option>
                <option value="Lowest to Highest">Lowest Position</option>
              </select>
            </div>
          </div>
        </div>

        <!-- Candidate Grid -->
        <ul class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <!-- Candidate Card -->
          <li
              v-for="candidate in filteredAndSortedCandidates"
              :key="candidate.candidateId"
              class="p-3 sm:p-4 bg-white rounded-md border shadow-sm flex items-center space-x-3 sm:space-x-4 hover:shadow-md transition-shadow"
          >
            <!-- Candidate Info -->
            <div class="flex items-center w-full truncate">
              <!-- Hide Initial on Small Screens -->
              <div
                  class="hidden sm:flex w-12 h-12 bg-gray-400 rounded-full items-center justify-center text-white font-bold text-base"
              >
                {{ candidate.candidateName.charAt(0) }}
              </div>
              <div class="truncate ml-0 sm:ml-4">
                <h3 class="font-semibold text-sm sm:text-base text-gray-800 truncate">
                  {{ candidate.candidateName }}
                </h3>
                <p class="text-xs sm:text-sm text-gray-600 truncate">
                  {{ candidate.candidateTitle }}
                </p>
              </div>
            </div>
          </li>
        </ul>
      </div>

      <!-- No Candidates Found -->
      <div v-else class="p-4 sm:p-8 text-center bg-gray-200">
        <h2 class="text-2xl sm:text-3xl font-semibold text-gray-800 mb-4">
          Candidates
        </h2>
        <p>No candidates match your search or filter.</p>
      </div>

      <!-- Back to Party List Button -->
      <div class="p-4 sm:p-8 bg-gray-200 text-center">
        <router-link
            to="/partyOverview"
            class="block w-full sm:w-auto mx-auto bg-blue-500 text-white text-sm sm:text-base font-semibold py-2 px-3 sm:py-3 sm:px-6 rounded-lg shadow-md text-center hover:bg-blue-600 transition duration-200"
        >
          Back to Party List
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* You can add additional custom styles here */
</style>

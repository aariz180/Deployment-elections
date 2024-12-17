<script setup lang="js">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import Chart from 'chart.js/auto'

const votesCanvas = ref(null)
const voteData = ref([])
const route = useRoute()
const partyName = route.params.partyName

onMounted(async () => {
    try {
        console.log('Party name from route:', partyName) // Log partyName

        if (!partyName) {
            console.error('Party name is missing in the route')
            return
        }

        // Fetch data from the backend using the dynamic partyName
        const response = await axios.get(
            `http://localhost:8080/votes/${partyName}`
        )
        voteData.value = response.data
        console.log('Fetched vote data:', voteData.value)
    } catch (error) {
        console.error('Error fetching vote data:', error)
    }

    // Ensure that the canvas is available before trying to access getContext
    if (votesCanvas.value) {
        const ctx = votesCanvas.value.getContext('2d')

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: voteData.value.map((row) => row.provinceName),
                datasets: [
                    {
                        label: `Votes for ${partyName} by Province`,
                        data: voteData.value.map((row) => row.voteCount),
                        backgroundColor: '#3B5B93',
                        hoverBackgroundColor: '#3B82F6',
                    },
                ],
            },
            options: {
              responsive: true,
              maintainAspectRatio: false, // Allows the chart to resize properly
                scales: {
                    y: {
                        ticks: {
                            callback: function (value) {
                                return value + '%' // Add a % sign on the y-axis
                            },
                        },
                    },
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function (context) {
                                const value = context.raw
                                return `${value}%` // Display sign when hovering over the bars
                            },
                        },
                    },
                },
            },
        })
    } else {
        console.error('Canvas element not found.')
    }
})
</script>

<template>
  <div class="p-4 sm:p-8 bg-gray-100">
    <h2
        class="text-lg sm:text-2xl lg:text-3xl font-semibold mb-4 text-gray-800 text-center"
    >
      Vote Overview
    </h2>

    <!-- Responsive Chart Container -->
    <div class="flex justify-center items-center">
      <div
          class="w-full sm:w-3/4 lg:w-3/4 xl:w-2/3 bg-white p-2 sm:p-4 rounded shadow-md"
          style="min-height: 300px;"
      >
        <canvas ref="votesCanvas" id="votes" class="w-full h-auto"></canvas>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* You can add additional custom styles here */
</style>

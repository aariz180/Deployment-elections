<template>
    <div>
        <!-- Controls -->
        <div class="controls">
            <!-- Toggle Switch -->
            <div class="toggle-container">
                <label>
                    <input type="checkbox" v-model="heatMapEnabled" />
                    Toggle Heat Map
                </label>
            </div>

            <!-- Heatmap Data Selection -->
            <div class="heatmap-data-selection">
                <label for="heatmap-data-select">Select Heatmap Data:</label>
                <select
                    id="heatmap-data-select"
                    v-model="selectedHeatmapData"
                    @change="fetchVotes"
                >
                    <option value="totalVotes">Total Votes</option>
                    <option value="candidateVotes">Candidate Votes</option>
                </select>
            </div>

            <!-- Candidate Selection (shown if candidateVotes is selected) -->
            <div
                class="candidate-selection"
                v-if="selectedHeatmapData === 'candidateVotes'"
            >
                <label for="candidate-select">Select Candidate:</label>
                <select
                    id="candidate-select"
                    v-model="selectedCandidateId"
                    @change="fetchVotes"
                >
                    <option
                        v-for="candidate in candidates"
                        :key="candidate.candidateID"
                        :value="candidate.candidateID"
                    >
                        {{ candidate.fullName }}
                    </option>
                </select>
            </div>
        </div>

        <div class="map-container">
            <svg
                class="map-svg"
                :style="mapStyle"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 612.54211 723.61865"
            >
                <defs>
                    <filter
                        id="shadow"
                        x="-50%"
                        y="-50%"
                        width="200%"
                        height="200%"
                    >
                        <feOffset dx="4" dy="4" result="offsetblur" />
                        <feFlood flood-color="rgba(0, 0, 0, 0.4)" />
                        <feComposite in2="offsetblur" operator="in" />
                        <feMerge>
                            <feMergeNode />
                            <feMergeNode in="SourceGraphic" />
                        </feMerge>
                    </filter>
                </defs>
                <g>
                    <!-- Shadow for path23 without blur -->
                    <svg
                        class="path23-shadow"
                        style="fill: #000; filter: url(#shadow)"
                    >
                        <use :xlink:href="`${netherlandsSvg}#path23`" />
                    </svg>

                    <!-- Loop through regions for hover effects -->
                    <svg
                        v-for="regionId in regionIds"
                        :key="regionId"
                        class="region"
                        @mouseover="hoveredRegion = regionId"
                        @mouseleave="hoveredRegion = null"
                        @mousemove="movePopup"
                        :style="{ fill: getRegionColor(regionId) }"
                    >
                        <use :xlink:href="`${netherlandsSvg}#${regionId}`" />
                    </svg>

                    <!-- Original path23 without shadow -->
                    <svg class="path23" style="fill: none">
                        <use :xlink:href="`${netherlandsSvg}#path23`" />
                    </svg>
                </g>
            </svg>

            <!-- Pop-up -->
            <div
                v-if="hoveredRegion"
                class="popup"
                :style="popupStyle"
                @mousemove="movePopup"
            >
                <div class="popup-content">
                    <h3 class="font-semibold text-lg">
                        {{ regionNames[hoveredRegion] }}
                    </h3>
                    <p class="text-sm text-gray-600">
                        Region Info: {{ regionNames[hoveredRegion] }}
                    </p>
                    <div class="mt-2 space-y-1">
                        <p>
                            <strong>Area:</strong>
                            {{ regionInfo[hoveredRegion]?.area || 'N/A' }}
                        </p>
                        <p>
                            <strong>Population:</strong>
                            {{ regionInfo[hoveredRegion]?.population || 'N/A' }}
                        </p>
                        <!-- Display the appropriate Votes label -->
                        <p>
                            <strong>{{ votesLabel }}:</strong>
                            {{ regionInfo[hoveredRegion]?.votes || 'N/A' }}
                        </p>
                    </div>
                    <a class="text-blue-500 mt-2 inline-block" href="#"
                        >Learn More</a
                    >
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import netherlandsSvg from '@/assets/netherlands.svg'

export default {
    computed: {
        mapStyle() {
            // Check if the current route is the homepage ("/")
            if (this.$route.path === '/') {
                return { width: '100%', height: '100%' } // Apply 100% width on homepage
            } else {
                return { width: '40%', height: 'auto' } // Apply default width on other pages
            }
        },
    },
    data() {
        return {
            heatMapEnabled: false, // Toggle for heat map
            selectedHeatmapData: 'totalVotes', // 'totalVotes' or 'candidateVotes'
            selectedCandidateId: null,
            candidates: [],
            votesLabel: 'Votes',
            hoveredRegion: null,
            defaultColor: '#cccccc', // Default color for regions not hovered
            regions: {
                'NL-DR': '#FF5733', // Drenthe
                'NL-FL': '#33FF57', // Flevoland
                'NL-FR': '#3357FF', // Friesland
                'NL-GE': '#F1C40F', // Gelderland
                'NL-GR': '#9B59B6', // Groningen
                'NL-LI': '#E67E22', // Limburg
                'NL-NB': '#2ECC71', // Noord-Brabant
                'NL-NH': '#8E44AD', // Noord-Holland
                'NL-OV': '#3498DB', // Overijssel
                'NL-UT': '#1ABC9C', // Utrecht
                'NL-ZE': '#E74C3C', // Zeeland
                'NL-ZH': '#F39C12', // Zuid-Holland
            },
            regionIds: [
                'NL-DR', // Drenthe
                'NL-FL', // Flevoland
                'NL-FR', // Friesland
                'NL-GE', // Gelderland
                'NL-GR', // Groningen
                'NL-LI', //'Limburg',
                'NL-NB', //'Noord-Brabant',
                'NL-NH', //'Noord-Holland',
                'NL-OV', //'Overijssel',
                'NL-UT', //'Utrecht',
                'NL-ZE', //'Zeeland',
                'NL-ZH', //'Zuid-Holland',
            ],
            provinces: {
                'NL-DR': 'P22', // Drenthe
                'NL-FL': 'P24', // Flevoland
                'NL-FR': 'P21', // Friesland
                'NL-GE': 'P25', // Gelderland
                'NL-GR': 'P20', // Groningen
                'NL-LI': 'P31', // Limburg
                'NL-NB': 'P30', // Noord-Brabant
                'NL-NH': 'P27', // Noord-Holland
                'NL-OV': 'P23', // Overijssel
                'NL-UT': 'P26', // Utrecht
                'NL-ZE': 'P29', // Zeeland
                'NL-ZH': 'P28', // Zuid-Holland
            },
            regionNames: {
                'NL-DR': 'Drenthe',
                'NL-FL': 'Flevoland',
                'NL-FR': 'Friesland',
                'NL-GE': 'Gelderland',
                'NL-GR': 'Groningen',
                'NL-LI': 'Limburg',
                'NL-NB': 'Noord-Brabant',
                'NL-NH': 'Noord-Holland',
                'NL-OV': 'Overijssel',
                'NL-UT': 'Utrecht',
                'NL-ZE': 'Zeeland',
                'NL-ZH': 'Zuid-Holland',
            },
            regionInfo: {
                'NL-DR': { area: '2,680 km²', population: '502,051', votes: 0 },
                'NL-FL': { area: '2,412 km²', population: '444,701', votes: 0 },
                'NL-FR': { area: '5,753 km²', population: '659,551', votes: 0 },
                'NL-GE': {
                    area: '5,136 km²',
                    population: '2,133,708',
                    votes: 0,
                },
                'NL-GR': { area: '2,955 km²', population: '596,075', votes: 0 },
                'NL-LI': {
                    area: '2,210 km²',
                    population: '1,128,367',
                    votes: 0,
                },
                'NL-NB': {
                    area: '5,082 km²',
                    population: '2,626,210',
                    votes: 0,
                },
                'NL-NH': {
                    area: '4,092 km²',
                    population: '2,952,622',
                    votes: 0,
                },
                'NL-OV': {
                    area: '3,421 km²',
                    population: '1,184,333',
                    votes: 0,
                },
                'NL-UT': {
                    area: '1,560 km²',
                    population: '1,387,643',
                    votes: 0,
                },
                'NL-ZE': { area: '2,933 km²', population: '391,124', votes: 0 },
                'NL-ZH': {
                    area: '3,308 km²',
                    population: '3,804,906',
                    votes: 0,
                },
            },
            netherlandsSvg: netherlandsSvg,
            popupStyle: {
                top: '0px',
                left: '0px',
            },
            // For the heatmap
            maxVotes: 0,
            minVotes: 0,
            heatmapColors: [
                '#fff33b', // (255,243,59)
                '#fdc70c', // (253,199,12)
                '#f3903f', // (243,144,63)
                '#ed683c', // (237,104,60)
                '#e93e3a', // (233,62,58)
            ],
        }
    },
    methods: {
        async fetchCandidates() {
            try {
                const response = await axios.get(
                    'http://localhost:8080/api/v1/elections/candidate/all'
                ) // Use your endpoint
                this.candidates = response.data.map((candidate) => ({
                    candidateID: candidate.candidateID,
                    fullName: `${candidate.kandidaatVoornaam} ${
                        candidate.kandidaatTussenvoegsel || ''
                    } ${candidate.kandidaatAchternaam}`,
                }))
                // Set default selected candidate if none is selected
                if (!this.selectedCandidateId && this.candidates.length > 0) {
                    this.selectedCandidateId = this.candidates[0].candidateID
                }
            } catch (error) {
                console.error('Error fetching candidates:', error)
            }
        },
        async fetchVotes() {
            try {
                let response
                if (this.selectedHeatmapData === 'totalVotes') {
                    response = await axios.get(
                        'http://localhost:8080/api/v1/elections/vote/regional-summary'
                    )
                } else if (this.selectedHeatmapData === 'candidateVotes') {
                    if (!this.selectedCandidateId) {
                        console.warn('No candidate selected')
                        return
                    }
                    response = await axios.get(
                        `http://localhost:8080/api/v1/elections/vote/candidate-regional-summary/${this.selectedCandidateId}`
                    )
                }

                const voteData = response.data

                let maxVotes = 0
                let minVotes = Infinity

                // Initialize votes to zero
                for (const regionId of this.regionIds) {
                    this.regionInfo[regionId].votes = 0
                }

                // Map vote data to provinces and find max/min votes
                voteData.forEach((vote) => {
                    for (const [province, code] of Object.entries(
                        this.provinces
                    )) {
                        if (vote.regionCode === code) {
                            const regionId = province
                            this.regionInfo[regionId].votes = vote.totalVotes
                            if (vote.totalVotes > maxVotes) {
                                maxVotes = vote.totalVotes
                            }
                            if (vote.totalVotes < minVotes) {
                                minVotes = vote.totalVotes
                            }
                        }
                    }
                })

                this.maxVotes = maxVotes
                this.minVotes = minVotes

                if (this.selectedHeatmapData === 'totalVotes') {
                    this.votesLabel = 'Total Votes'
                } else {
                    const selectedCandidate = this.candidates.find(
                        (candidate) =>
                            candidate.candidateID === this.selectedCandidateId
                    )
                    this.votesLabel = `Votes for ${
                        selectedCandidate
                            ? selectedCandidate.fullName
                            : 'Candidate'
                    }`
                }
            } catch (error) {
                console.error('Error fetching vote data:', error)
            }
        },
        movePopup(event) {
            // Get the mouse position
            const mouseX = event.clientX
            const mouseY = event.clientY

            // Set the popup position with consistent offsets
            const offsetX = -335 // Adjust to fine-tune the position horizontally
            const offsetY = -380 // Adjust to fine-tune the position vertically

            // Ensure the popup stays within the viewport
            const popupWidth = 200 // Popup width
            const popupHeight = 200 // Popup height

            let top = Math.min(
                mouseY + offsetY,
                window.innerHeight - popupHeight
            )
            let left = Math.max(mouseX + offsetX, 0)

            this.popupStyle.top = `${top}px`
            this.popupStyle.left = `${left}px`
        },
        getRegionColor(regionId) {
            if (this.heatMapEnabled) {
                // Check if a candidate is selected and has votes in only one region
                if (
                    this.selectedHeatmapData === 'candidateVotes' &&
                    this.selectedCandidateId
                ) {
                    const candidateVotes = this.regionInfo[regionId]?.votes || 0
                    const onlyRegionWithVotes = Object.keys(
                        this.regionInfo
                    ).find(
                        (key) =>
                            this.regionInfo[key].votes > 0 && key !== regionId
                    )

                    if (candidateVotes > 0 && !onlyRegionWithVotes) {
                        // Only one region has votes, color it red
                        return '#FF5733'
                    }
                }

                // Otherwise, apply heat map colors
                const votes = this.regionInfo[regionId]?.votes || 0
                if (this.maxVotes === 0 || this.maxVotes === this.minVotes) {
                    return this.defaultColor
                }

                // Normalize the vote count between 0 and 1
                const normalizedVotes =
                    (votes - this.minVotes) / (this.maxVotes - this.minVotes)

                // Map the normalized value to the heatmap colors
                let color = this.getColorFromScale(normalizedVotes)

                // If the region is hovered, make the color slightly darker
                if (this.hoveredRegion === regionId) {
                    color = this.darkenColor(color, 0.2) // Darken by 20%
                }

                return color
            } else {
                // Heat map is disabled, use original styling
                // Regions are gray by default, and colored on hover
                if (this.hoveredRegion === regionId) {
                    return this.regions[regionId] || this.defaultColor
                } else {
                    return this.defaultColor
                }
            }
        },

        getColorFromScale(value) {
            // value is between 0 and 1
            const numColors = this.heatmapColors.length
            const scaledValue = value * (numColors - 1)
            const index = Math.floor(scaledValue)
            const remainder = scaledValue - index

            if (index >= numColors - 1) {
                return this.heatmapColors[numColors - 1]
            }

            const colorStart = this.hexToRgb(this.heatmapColors[index])
            const colorEnd = this.hexToRgb(this.heatmapColors[index + 1])

            const interpolatedColor = {
                r: Math.round(
                    colorStart.r + remainder * (colorEnd.r - colorStart.r)
                ),
                g: Math.round(
                    colorStart.g + remainder * (colorEnd.g - colorStart.g)
                ),
                b: Math.round(
                    colorStart.b + remainder * (colorEnd.b - colorStart.b)
                ),
            }

            return this.rgbToHex(interpolatedColor)
        },
        hexToRgb(hex) {
            if (typeof hex === 'string' && hex.startsWith('#')) {
                const bigint = parseInt(hex.slice(1), 16)
                return {
                    r: (bigint >> 16) & 255,
                    g: (bigint >> 8) & 255,
                    b: bigint & 255,
                }
            } else {
                // Handle invalid hex values or do something else
                // Return a default color or throw an error
                return { r: 255, g: 243, b: 59 } // Example default color (black)
            }
        },
        rgbToHex({ r, g, b }) {
            // Convert RGB object to hex color
            return (
                '#' +
                [r, g, b]
                    .map((x) => {
                        const hex = x.toString(16)
                        return hex.length === 1 ? '0' + hex : hex
                    })
                    .join('')
            )
        },
        darkenColor(hexColor, amount) {
            // Darken the color by the given amount (0 to 1)
            const { r, g, b } = this.hexToRgb(hexColor)
            const darkenedColor = {
                r: Math.max(0, Math.round(r * (1 - amount))),
                g: Math.max(0, Math.round(g * (1 - amount))),
                b: Math.max(0, Math.round(b * (1 - amount))),
            }
            return this.rgbToHex(darkenedColor)
        },
    },
    mounted() {
        this.fetchCandidates()
        this.fetchVotes()
    },
}
</script>

<style scoped>
/* Controls Styling */
.controls {
    margin-bottom: 10px;
    text-align: center;
    font-size: 16px;
}

.toggle-container {
    margin-bottom: 10px;
}

.heatmap-data-selection,
.candidate-selection {
    margin-bottom: 10px;
}

/* Map Styling */
.map-container {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

.map-svg {
    width: 40%;
    height: 40%;
}

.region {
    transition: fill 0.3s ease;
}

.path23-shadow {
    z-index: 1;
}

.path23 {
    z-index: 2;
}

/* Popup Styling */
.popup {
    position: absolute;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
    padding: 15px;
    box-sizing: border-box; /* Ensure padding doesn't affect dimensions */
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 10;
    width: 180px; /* Fixed width */
    height: 200px; /* Fixed height */
    font-size: 14px;
}

.popup-content {
    text-align: left;
}

.popup h3 {
    font-weight: bold;
}

.popup p {
    font-size: 12px;
    color: #555;
    margin: 5px 0; /* Consistent spacing */
}

a {
    font-size: 12px;
    color: #1e40af;
    text-decoration: none;
}
</style>

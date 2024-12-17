<template>
    <div
        :class="[
            'flex min-h-full flex-1 flex-col justify-center py-12 sm:px-6 lg:px-8',
            stopAnimations ? '' : 'bg-flashing',
        ]"
        class="hide-cursor"
    >
        <p
            :class="[
                'text-center text-4xl font-bold text-transparent bg-clip-text',
                stopAnimations ? '' : 'animate-flashing-text', // Use the new flashing text animation class
            ]"
        >
            {{ displayedText }}
        </p>

        <div class="flex flex-col space-y-4 w-full">
            <!-- Wide Box with border -->
            <div
                :class="[
                    'h-48 rounded-lg w-full relative p-4',
                    stopAnimations ? '' : 'animate-jump',
                ]"
            >
                <div class="grid grid-cols-6 gap-2 h-full w-full">
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                    <img
                        v-show="showImages"
                        :style="{ animationDelay: getRandomDelay() }"
                        src="../assets/zyzz-flex.gif"
                        alt="flex"
                        class="object-cover w-full h-full rounded-lg"
                    />
                </div>
            </div>

            <!-- Two Less Wide Boxes with border -->
            <div class="flex space-x-4">
                <div
                    :class="[
                        'h-48 rounded-lg w-1/2 relative p-4',
                        stopAnimations ? '' : 'animate-jump',
                    ]"
                >
                    <div class="grid grid-cols-3 gap-2 h-full w-full">
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                    </div>
                </div>
                <div
                    :class="[
                        'h-48 rounded-lg w-1/2 relative p-4',
                        stopAnimations ? '' : 'animate-jump',
                    ]"
                >
                    <div class="grid grid-cols-3 gap-2 h-full w-full">
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                    </div>
                </div>
            </div>

            <div class="flex space-x-4">
                <div
                    :class="[
                        'h-48 rounded-lg w-1/2 relative p-4',
                        stopAnimations ? '' : 'animate-jump',
                    ]"
                >
                    <div class="grid grid-cols-3 gap-2 h-full w-full">
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                    </div>
                </div>
                <div
                    :class="[
                        'h-48 rounded-lg w-1/2 relative p-4',
                        stopAnimations ? '' : 'animate-jump',
                    ]"
                >
                    <div class="grid grid-cols-3 gap-2 h-full w-full">
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                        <img
                            v-show="showImages"
                            :style="{ animationDelay: getRandomDelay() }"
                            src="../assets/wdcbvd0bgr461.gif"
                            alt="flex"
                            class="object-cover w-full h-full rounded-lg"
                        />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* Hide cursor when hovering over the whole page */
.hide-cursor {
    cursor: none;
}

/* Tailwind-compatible class for black, gray, and white text animation */
.animate-flashing-text {
    background-size: 200% 200%;
    background-image: linear-gradient(270deg, black, gray, white);
    animation: flashingTextAnimation 0.3s infinite;
    animation-delay: 2s;
}

@keyframes flashingTextAnimation {
    0% {
        background-position: 200% 0;
        color: black;
    }
    50% {
        background-position: 0 0;
        color: gray;
    }
    100% {
        background-position: 200% 0;
        color: white;
    }
}

@keyframes jumpAnimation {
    0% {
        transform: translateY(0);
    }
    50% {
        transform: translateY(+20px);
    }
    100% {
        transform: translateY(0);
    }
}

/* Jump animation for the boxes with 1s delay */
.animate-jump {
    animation: jumpAnimation 0.1s infinite;
    animation-delay: 2s; /* Add a 1-second delay before the animation starts */
}

/* Flashing background color animation */
@keyframes backgroundFlash {
    0%,
    100% {
        background-color: white;
    }
    25% {
        background-color: gray;
    }
    50% {
        background-color: black;
    }
    75% {
        background-color: gray;
    }
}

/* Apply flashing background color change */
.bg-flashing {
    animation: backgroundFlash 0.3s infinite;
    animation-delay: 2s;
}
</style>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import song from '../assets/PPWizardry.mp3'
import { useRouter } from 'vue-router'

const stopAnimations = ref(false)
const showImages = ref(false)
const router = useRouter()

// Function to generate a random delay between 0 and 3 seconds
function getRandomDelay(): string {
    const randomDelay = Math.floor(Math.random() * 3000) // 0-3000 ms
    return `${randomDelay}ms`
}

// Rainbow console log function following the white-grey-dark pattern
function rainbowConsoleLog(): number {
    let colorIndex = 0
    const colors = [
        { lightness: 100, color: 'white' }, // White
        { lightness: 50, color: 'grey' }, // Grey
        { lightness: 20, color: 'black' }, // Dark (Black)
    ]

    // Return the intervalId so it can be cleared later
    return setInterval(() => {
        // Cycle through the color pattern (white -> grey -> black)
        const color = colors[colorIndex]
        console.log(
            `%cYOUR OPINION DOESN'T MATTER`,
            `font-size: 40px; color: hsl(0, 0%, ${color.lightness}%)` // HSL with different lightness for white, grey, and black
        )

        // Move to the next color in the sequence
        colorIndex = (colorIndex + 1) % colors.length
    }, 100)
}

function playMySong(): void {
    const audio = document.createElement('audio')
    audio.style.display = 'none'
    audio.src = song
    audio.autoplay = true
    audio.loop = false
    document.body.appendChild(audio)

    // Set up event listener for when the song ends
    audio.addEventListener('ended', () => {
        stopAnimations.value = true
        // Clear the interval to stop the console log rainbow effect
        clearInterval(rainbowIntervalId)
        router.back()
    })

    // Set a timeout before showing the images
    setTimeout(() => {
        showImages.value = true
    }, 2000)

    // Start the rainbow console log and store the intervalId
    rainbowIntervalId = rainbowConsoleLog()
}

let rainbowIntervalId: number // Variable to store the interval ID

const displayedText = ref("YOUR OPINION DOESN'T MATTER")

onMounted(() => {
    playMySong()
})
</script>

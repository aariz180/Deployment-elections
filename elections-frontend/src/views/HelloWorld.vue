<template>
    <div class="relative bg-gray-50 py-24 sm:py-32 dark:bg-gray-800">
        <div class="mx-auto max-w-2xl px-6 lg:max-w-7xl lg:px-8 relative z-10">
            <h2
                class="text-center text-base font-semibold text-sky-500 dark:text-sky-400"
            >
                ELECTIONS 2024
            </h2>
            <p
                class="mx-auto mt-2 max-w-lg text-pretty text-center text-4xl font-medium tracking-tight text-gray-950 dark:text-gray-200 sm:text-5xl"
            >
                Your vote counts.
            </p>
            <div
                class="mt-10 grid gap-4 sm:mt-16 lg:grid-cols-3 lg:grid-rows-2"
            >
                <!-- Mobile Friendly Parties Card -->
                <router-link
                    to="/partyOverview"
                    class="relative lg:row-span-2 pt-4 card"
                >
                    <div
                        class="absolute inset-px rounded-lg bg-white dark:bg-gray-900 lg:rounded-l-[2rem]"
                    />
                    <div
                        class="relative flex h-full flex-col overflow-hidden rounded-[calc(theme(borderRadius.lg)+1px)] lg:rounded-l-[calc(2rem+1px)]"
                    >
                        <div
                            class="overflow-hidden bg-white dark:bg-gray-900 z-20"
                        >
                            <!-- Changed to a darker color -->
                            <div
                                class="px-6 pb-2 pt-4 sm:px-8 sm:pb-0 sm:pt-6 z-20 overflow-h"
                            >
                                <p
                                    class="mt-2 text-2xl font-medium tracking-tight text-gray-950 dark:text-gray-200 text-center"
                                >
                                    Parties
                                </p>
                                <p
                                    class="mt-2 max-w-lg text-sm text-gray-600 dark:text-gray-400 text-center"
                                >
                                    Anim aute id magna aliqua ad ad non deserunt
                                    sunt. Qui irure qui lorem cupidatat commodo.
                                </p>
                            </div>
                            <div
                                class="relative left-0 right-0 mt-2 bottom-0 h-1 bg-sky-500 shadow rounded-t-lg z-20"
                            ></div>
                            <!-- Underline -->
                        </div>

                        <!-- Animated Bubbles Inside the Parties Card -->
                        <div
                            class="animation-container relative z-0 overflow-hidden flex-grow"
                        >
                            <div
                                v-for="(bubble, index) in bubbles"
                                :key="index"
                                class="circle"
                                :style="{
                                    left: `${bubble.x}px` /* Set X position */,
                                    animationDelay: `${bubble.delay}s` /* Delay each bubble */,
                                    animationDuration:
                                        bubble.duration +
                                        's' /* Varying speed of bubble rise */,
                                    backgroundImage: `url(${bubble.image})` /* Set the background image */,
                                    backgroundSize:
                                        'cover' /* Cover the entire bubble */,
                                    backgroundPosition:
                                        'center' /* Center the image */,
                                    zIndex: 1 /* Set the z-index for bubbles to make them appear beneath */,
                                }"
                                @animationend="resetBubble(index)"
                            ></div>
                        </div>
                    </div>
                    <div
                        class="pointer-events-none absolute inset-px rounded-lg shadow ring-1 ring-black/5 lg:rounded-l-[2rem]"
                    />
                </router-link>

                <!-- Performance Card -->
                <router-link
                    to="/PPWizard"
                    class="relative max-lg:row-start-1 card"
                    @click="handleLinkClick"
                >
                    <div
                        class="absolute inset-px rounded-lg bg-white dark:bg-gray-900 max-lg:rounded-t-[2rem]"
                    />
                    <div
                        class="relative flex h-full flex-col overflow-hidden rounded-[calc(theme(borderRadius.lg)+1px)] max-lg:rounded-t-[calc(2rem+1px)]"
                    >
                        <div class="px-8 pt-8 sm:px-10 sm:pt-10">
                            <p
                                class="mt-2 text-2xl font-medium tracking-tight text-gray-950 dark:text-gray-200 max-lg:text-center"
                            >
                                Political Party Wizard
                            </p>
                            <p
                                class="mt-2 max-w-lg text-sm text-gray-600 dark:text-gray-400 max-lg:text-center"
                            >
                                Unsure which party to vote for in the upcoming
                                election? We invite you to use the Political
                                Party Wizard.
                            </p>
                        </div>
                        <div
                            class="relative left-0 right-0 mt-2 bottom-0 h-1 bg-sky-500 shadow rounded-t-lg z-20"
                        ></div>
                        <!-- Underline -->
                    </div>
                    <div
                        class="pointer-events-none absolute inset-px rounded-lg shadow ring-1 ring-black/5 max-lg:rounded-t-[2rem]"
                    />
                </router-link>

                <!-- Security Card -->
                <router-link
                    to="/forumOverview"
                    class="relative max-lg:row-start-3 lg:col-start-2 lg:row-start-2 card"
                >
                    <div
                        class="absolute inset-px rounded-lg bg-white dark:bg-gray-900"
                    />
                    <div
                        class="relative flex h-full flex-col overflow-hidden rounded-[calc(theme(borderRadius.lg)+1px)]"
                    >
                        <div class="px-8 pt-8 sm:px-10 sm:pt-10">
                            <p
                                class="mt-2 text-2xl font-medium tracking-tight text-gray-950 dark:text-gray-200 max-lg:text-center"
                            >
                                Forum
                            </p>
                            <p
                                class="mt-2 max-w-lg text-sm text-gray-600 dark:text-gray-400 max-lg:text-center"
                            >
                                Morbi viverra dui mi arcu sed. Tellus semper
                                adipiscing suspendisse semper morbi.
                            </p>
                        </div>
                        <div
                            class="relative left-0 right-0 mt-2 bottom-0 h-1 bg-sky-500 shadow rounded-t-lg z-20"
                        ></div>
                        <!-- Underline -->
                    </div>
                    <div
                        class="pointer-events-none absolute inset-px rounded-lg shadow ring-1 ring-black/5"
                    />
                </router-link>

                <!-- Map Card -->
                <router-link to="/MapPage" class="relative lg:row-span-2 card">
                    <div
                        class="absolute inset-px rounded-lg bg-white dark:bg-gray-900 max-lg:rounded-b-[2rem] lg:rounded-r-[2rem]"
                    />
                    <div
                        class="relative flex h-full flex-col overflow-hidden rounded-[calc(theme(borderRadius.lg)+1px)] max-lg:rounded-b-[calc(2rem+1px)] lg:rounded-r-[calc(2rem+1px)]"
                    >
                        <div class="px-8 pb-3 pt-8 sm:px-10 sm:pb-0 sm:pt-10">
                            <p
                                class="mt-2 text-2xl font-medium tracking-tight text-gray-950 dark:text-gray-200 max-lg:text-center"
                            >
                                Map
                            </p>
                            <p
                                class="mt-2 max-w-lg text-sm text-gray-600 dark:text-gray-400 max-lg:text-center"
                            >
                                Explore the regions of the Netherlands
                                interactively.
                            </p>
                        </div>
                        <div
                            class="relative left-0 right-0 mt-2 bottom-0 h-1 bg-sky-500 shadow rounded-t-lg z-20"
                        ></div>
                        <!-- Underline -->
                        <div
                            class="relative min-h-[30rem] w-full grow flex justify-center items-center"
                        >
                            <Map />
                            <!-- Importing the Map component here -->
                        </div>
                    </div>
                    <div
                        class="pointer-events-none absolute inset-px rounded-lg shadow ring-1 ring-black/5 max-lg:rounded-b-[2rem] lg:rounded-r-[2rem]"
                    />
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import Map from '@/components/icons/Map.vue' // Import the Map component
import BBBLogo from '@/assets/BBB logo.png'
import CDALogo from '@/assets/CDA logo.png'
import CULogo from '@/assets/CU logo.png'
import D66Logo from '@/assets/D66 logo.png'
import DENKLogo from '@/assets/DENK logo.png'
import FvDLogo from '@/assets/FvD logo.png'
import JA21Logo from '@/assets/JA21 logo.png'
import NSCLogo from '@/assets/NSC logo.svg'
import PvdAGroenlinksLogo from '@/assets/PvdA-Groenlinks logo.jpg'
import PvdDLogo from '@/assets/PvdD logo.png'
import PVVLogo from '@/assets/PVV logo.jpg'
import SGPLogo from '@/assets/SGP logo.png'
import SPLogo from '@/assets/SP logo.png'
import VOLTLogo from '@/assets/VOLT logo.png'
import VVDLogo from '@/assets/VVD logo.png'

export default {
    components: {
        Map, // Register the Map component
    },
    data() {
        return {
            numberOfBubbles: 60, // Increased number of bubbles
            bubbles: [], // Array to hold bubbles
            images: [
                // List of images to choose from
                BBBLogo,
                CDALogo,
                CULogo,
                D66Logo,
                DENKLogo,
                FvDLogo,
                JA21Logo,
                NSCLogo,
                PvdAGroenlinksLogo,
                PvdDLogo,
                PVVLogo,
                SGPLogo,
                SPLogo,
                VOLTLogo,
                VVDLogo,
            ],
        }
    },
    created() {
        this.createBubbles() // Create initial bubbles on component creation
    },
    methods: {
        createBubbles() {
            for (let i = 0; i < this.numberOfBubbles; i++) {
                this.addBubble(i)
            }
        },
        addBubble(index) {
            // Generate a new bubble with a random speed and staggered delay
            this.bubbles.push({
                x: Math.random() * (window.innerWidth - 30), // Random X position within the viewport
                delay: index * 0.5, // Staggered delay for animation start
                duration: Math.random() * (8 - 4) + 4, // Random duration between 4s and 8s
                image: this.getRandomImage(), // Random image from the list
            })
        },
        resetBubble(index) {
            // Reset the bubble to the starting position
            this.bubbles[index] = {
                x: Math.random() * (window.innerWidth - 30), // New random X position
                delay: 0, // Reset delay
                duration: Math.random() * (8 - 4) + 4, // New random duration
                image: this.getRandomImage(), // New random image
            }
            // Force the animation to restart
            this.$nextTick(() => {
                const bubbleElement =
                    this.$el.querySelectorAll('.circle')[index]
                bubbleElement.style.animation = 'none' // Reset animation
                bubbleElement.offsetHeight // Trigger reflow
                bubbleElement.style.animation = '' // Reapply animation
            })
        },
        getRandomImage() {
            // Return a random image from the list
            const randomIndex = Math.floor(Math.random() * this.images.length)
            return this.images[randomIndex]
        },
        handleLinkClick(event) {
            // Prevent the default behavior of the link first
            event.preventDefault()

            if (confirm('EPILEPSY ALERT! PROCEED AT YOUR OWN RISK')) {
                // Show second alert
                alert('Are you sure? No going back now')
                // Proceed to navigate if user confirms
                this.$router.push('/PPWizard')
            }
        },
    },
}
</script>

<style scoped>
.animation-container {
    position: absolute;
    bottom: 0; /* Start position for bubbles */
    left: 0;
    right: 0;
    height: 100%; /* Fill the height of the container */
    overflow: hidden; /* Prevent overflow */
    pointer-events: none; /* Ignore pointer events */
}

.circle {
    position: absolute;
    bottom: -60px; /* Start 30px lower */
    width: 50px; /* Circle width */
    height: 50px; /* Circle height */
    border-radius: 50%; /* Make the div circular */
    animation: rise 6s ease-in forwards; /* Adjust speed of animation */
    background-size: cover; /* Cover the entire bubble with the image */
    border: 2px solid white; /* Add a white border */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Optional: add a slight shadow for depth */
    z-index: 1; /* Set the z-index for bubbles to make them appear beneath */
}

@keyframes rise {
    0% {
        transform: translateY(0); /* Start at the bottom */
    }
    100% {
        transform: translateY(
            -730px
        ); /* Move to the top of the container (30px higher) */
    }
}

.card {
    transition:
        transform 0.2s ease,
        box-shadow 0.2s ease; /* Smooth transition */
}

.card:hover {
    transform: translateY(-5px); /* Slight upward movement */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Enhanced shadow on hover */
}

/* Add any specific styles for your component here */
.image-container {
    border: 2px solid #e2e8f0; /* Light border */
    border-radius: 0.5rem; /* Rounded corners */
    overflow: hidden; /* Ensure the image doesn't overflow */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
}

/* Dark mode specific styles */
.dark .image-container {
    border-color: #4b5563; /* Darker border for dark mode */
    background-color: #1f2937; /* Dark background for cards */
}

.dark .card {
    background-color: #2d3748; /* Darker card background */
    color: #edf2f7; /* Light text color for better contrast */
}
</style>

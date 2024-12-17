<script setup lang="ts">
import Map from '@/components/icons/Map.vue' // Import the Map component
</script>

<template>
    <div class="center-container">
        <Map class="map-size" />
        <!-- Apply class to the Map component -->
    </div>
</template>

<style scoped>
/* Ensure the HTML and body take up the full height and prevent scrolling */
html,
body {
    height: 100%;
    margin: 0;
    overflow: hidden; /* Disable scrolling */
}

.center-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: inherit;
    height: 100vh; /* Make sure the container fills the full viewport height */
    width: 100%; /* Full width of the screen */
}
.map-size {
    width: 100%;
}
</style>

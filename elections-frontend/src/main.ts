// main.js

import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'
import { createPinia } from 'pinia' // Import Pinia

const app = createApp(App)

const pinia = createPinia() // Create a Pinia instance
app.use(pinia) // Use Pinia
app.use(router) // Use Vue Router

app.mount('#app') // Mount the app to the DOM

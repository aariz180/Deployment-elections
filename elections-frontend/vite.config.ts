import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

/**
 * Vite configuration for the Vue application.
 *
 * @module ViteConfig
 * @see https://vitejs.dev/config/
 */
export default defineConfig({
    /**
     * Plugins to use in the Vite build.
     * Here we use the Vue plugin for Vite.
     *
     * @type {import('vite').Plugin[]}
     */
    plugins: [vue()],

    /**
     * Resolve options for module imports.
     * Sets up aliases for cleaner import paths.
     *
     * @type {Object}
     * @property {Object} alias - Aliases for module resolution.
     * @property {string} alias.@ - Alias for the src directory.
     */
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url)),
        },
    },

    /**
     * Server options for Vite development server.
     *
     * @type {Object}
     * @property {number} port - The port on which the server will run.
     */
    server: {
        port: 3000,
    },
})

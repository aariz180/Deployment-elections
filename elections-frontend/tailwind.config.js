/**
 * @type {import('tailwindcss').Config}
 * Tailwind CSS configuration file.
 *
 * This configuration specifies the settings for Tailwind CSS,
 * including dark mode preferences and the paths to scan for content.
 */
export default {
    /**
     * Specifies the dark mode strategy.
     * Options include 'media' (prefers-color-scheme) or 'class' (using a class to toggle dark mode).
     * Here, 'selector' is used to define dark mode via a specific CSS selector.
     *
     * @type {string}
     */
    darkMode: 'selector',

    /**
     * An array of paths to the files that Tailwind should scan for class names.
     * This includes HTML files and all Vue, JavaScript, TypeScript, JSX, and TSX files
     * within the src directory.
     *
     * @type {string[]}
     */
    content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],

    /**
     * Theme configuration options for customizing Tailwind's default styles.
     * The `extend` object allows you to add or override default theme values.
     *
     * @type {Object}
     */
    theme: {
        extend: {},
    },

    /**
     * An array of plugins to add additional functionality to Tailwind CSS.
     * Currently empty, but can be populated with Tailwind plugins as needed.
     *
     * @type {Array}
     */
    plugins: [],
}

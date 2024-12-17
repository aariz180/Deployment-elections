/**
 * PostCSS configuration file.
 *
 * This configuration exports an object that specifies the plugins to use with PostCSS,
 * including Tailwind CSS and Autoprefixer.
 *
 * @type {import('postcss').ProcessOptions}
 */
export default {
    /**
     * An object containing the PostCSS plugins to be used.
     *
     * @type {Object}
     * @property {Object} tailwindcss - Configuration options for Tailwind CSS.
     * @property {Object} autoprefixer - Configuration options for Autoprefixer.
     */
    plugins: {
        /**
         * Tailwind CSS plugin configuration.
         * This allows for utility-first CSS styling in your application.
         *
         * @type {Object}
         */
        tailwindcss: {},

        /**
         * Autoprefixer plugin configuration.
         * This automatically adds vendor prefixes to CSS rules, ensuring better cross-browser compatibility.
         *
         * @type {Object}
         */
        autoprefixer: {},
    },
}

<template>
    <div
        class="mx-auto mt-5 w-screen max-w-screen-md py-5 leading-6"
        ref="dropdownContainer"
    >
        <div class="flex w-full">
            <!-- Filter Form (Search Bar) -->
            <div
                class="relative flex w-full items-center rounded-md border shadow-lg border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800"
            >
                <svg
                    class="absolute left-2 block h-5 w-5 text-gray-400 dark:text-gray-400"
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                >
                    <circle cx="11" cy="11" r="8" />
                    <line x1="21" y1="21" x2="16.65" y2="16.65" />
                </svg>
                <input
                    type="text"
                    name="filter"
                    @keydown.enter="
                        $emit(
                            'filter-keywords',
                            ($event.target as HTMLInputElement).value
                        )
                    "
                    class="h-14 w-full rounded-md py-4 pr-40 pl-12 border border-gray-300 dark:border-gray-600 outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-600 bg-transparent dark:bg-gray-800 dark:text-gray-200"
                    placeholder="Search Forum..."
                />
            </div>

            <!-- Container for Filter Dropdown and Add Button -->
            <div class="flex w-auto ml-4 gap-2">
                <!-- Filter Options Dropdown -->
                <div class="relative w-20 group">
                    <button
                        type="button"
                        class="inline-flex items-center justify-center w-full h-14 rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-sm font-semibold text-gray-900 dark:text-gray-200 shadow-sm ring-1 ring-inset ring-gray-300 dark:ring-gray-600 hover:bg-gray-50 dark:hover:bg-gray-700"
                        @click="toggleFilterDropdown"
                        ref="filterButton"
                    >
                        <svg
                            class="h-5 w-5 text-gray-400 dark:text-gray-400"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="currentColor"
                            stroke-width="2"
                            stroke-linecap="round"
                            stroke-linejoin="round"
                        >
                            <path d="M4 6h16M4 12h16m-7 6h7" />
                        </svg>
                    </button>
                    <!-- Tooltip for Filter Button -->
                    <span
                        class="absolute bottom-full mb-2 hidden w-full rounded-md bg-gray-800 p-2 text-xs text-white opacity-0 group-hover:block group-hover:opacity-100"
                    >
                        Filter Options
                    </span>

                    <!-- Filter dropdown menu -->
                    <div
                        class="absolute right-0 z-10 mt-1 w-20 origin-top-right rounded-md bg-white dark:bg-gray-800 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
                        v-show="isFilterDropdownOpen"
                    >
                        <div class="py-1">
                            <a
                                href="#"
                                class="block px-4 py-2 text-sm text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700"
                                @click="$emit('select-filter', 'new')"
                            >
                                New
                            </a>
                            <a
                                href="#"
                                class="block px-4 py-2 text-sm text-gray-700 dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-700"
                                @click="$emit('select-filter', 'old')"
                            >
                                Old
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Display Selected Tags Below Input -->
        <div class="mt-2 flex flex-wrap space-x-1">
            <div
                v-for="tag in selectedTags"
                :key="tag"
                class="px-4 py-2 text-sm font-bold text-white bg-blue-600 rounded cursor-pointer hover:bg-blue-700"
                @click.prevent="removeTag(tag)"
            >
                {{ tag }}
                <button @click.prevent="removeTag(tag)" class="ml-1 text-white">
                    &times;
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import {
    ref,
    computed,
    defineProps,
    defineEmits,
    onMounted,
    onBeforeUnmount,
} from 'vue'

// Dropdown state management
const isFilterDropdownOpen = ref(false)

// Handle clicks outside of dropdown
const dropdownContainer = ref<HTMLElement | null>(null)
const filterButton = ref<HTMLElement | null>(null)

// Define the props for the component
const receivedTagProps = defineProps<{
    tag: Array<string>
}>()

// Computed property to get the selected tags in lower case
const selectedTags = computed(() => {
    return receivedTagProps.tag.map((tag) => tag.toLowerCase())
})

/**
 * Define the events emitted from this component.
 * @returns {Object} Emits object containing filter events.
 */
const emit = defineEmits([
    'filter-keywords',
    'select-filter',
    'remove-tag',
    'is-logged-in',
])

/**
 * Toggles the visibility of the filter options dropdown menu.
 * Closes the dropdown if it is opened.
 *
 * @returns {void}
 */
const toggleFilterDropdown = (): void => {
    isFilterDropdownOpen.value = !isFilterDropdownOpen.value
}

/**
 * Removes a tag from the selected tags and emit the updated list.
 *
 * @param {string} tag - The tag to be removed from the selected tags.
 * @returns {void}
 */
const removeTag = (tag: string): void => {
    console.log('Tag removed:', tag) // Log the removed tag for debugging
    emit('remove-tag', tag) // Emit event to remove the tag
}

/**
 * Handles clicks outside the dropdown to close it.
 *
 * @param {MouseEvent} event - The click event object.
 * @returns {void}
 */
const handleClickOutside = (event: MouseEvent) => {
    const target = event.target as HTMLElement
    if (
        isFilterDropdownOpen.value &&
        dropdownContainer.value &&
        filterButton.value &&
        !dropdownContainer.value.contains(target) &&
        !filterButton.value.contains(target)
    ) {
        isFilterDropdownOpen.value = false // Close the dropdown
    }
}

// Register event listeners
onMounted(() => {
    document.addEventListener('click', handleClickOutside)
})

// Clean up event listeners
onBeforeUnmount(() => {
    document.removeEventListener('click', handleClickOutside)
})
</script>

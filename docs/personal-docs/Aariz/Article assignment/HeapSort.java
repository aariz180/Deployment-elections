public class HeapSort {

    public static void main(String[] args) {
        HeapSort sorter = new HeapSort(); // Create an instance of the HeapSort class
        int[] array = {2, 8, 5, 3, 9, 1}; // Define the array to be sorted

        // Print the original array
        System.out.println("Original array:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();

        sorter.heapSort(array); // Call the heapSort method to sort the array

        // Print the sorted array
        System.out.println("Sorted array:");
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println(); // Add a new line after the array
    }

    // This method sorts the array using heapsort
    public void heapSort(int[] array) {
        buildMaxHeap(array); // Step 1: Build a max heap from the array
        int n = array.length; // Get the size of the array

        // Step 2: Move the largest element (root of the heap) to the end of the array
        for (int i = n - 1; i >= 1; i--) {
            swap(array, 0, i); // Swap the root (largest) with the last element
            n = n - 1; // Reduce the size of the heap
            heapify(array, 0, n); // Restore the heap property for the reduced heap
        }
    }

    // This method builds a max heap from the array
    private void buildMaxHeap(int[] array) {
        int n = array.length; // Get the size of the array

        // Start from the last non-leaf node (last node in a tree that has at least one child)
        // And call heapify for each node
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, i, n); // Fix the heap property for node i
        }
    }

    // This method ensures the heap property is maintained
    private void heapify(int[] array, int i, int n) {
        int left = 2 * i + 1; // Left child of node i
        int right = 2 * i + 2; // Right child of node i
        int max = i; // Assume the current node (i) is the largest

        // Check if the left child exists and is larger than the current node
        if (left < n && array[left] > array[max]) {
            max = left; // Update max to the left child
        }

        // Check if the right child exists and is larger than the largest so far
        if (right < n && array[right] > array[max]) {
            max = right; // Update max to the right child
        }

        // If the largest is not the current node, swap and continue heapifying
        if (max != i) {
            swap(array, i, max); // Swap the current node with the largest
            heapify(array, max, n); // Recursively fix the heap property
        }
    }

    // This method swaps two elements in the array
    private void swap(int[] array, int i, int j) {
        int temp = array[i]; // Store the value at index i in a temporary variable
        array[i] = array[j]; // Assign the value at index j to index i
        array[j] = temp; // Assign the temporary value to index j
    }
}

public class SelectionSortSimplified {

    // Method to perform selection sort
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        // Traverse through the entire array
        for (int i = 0; i < n - 1; i++) {
            // Find the index of the smallest element in the unsorted portion and set it as the minIndex
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the minIndex with the first element of the unsorted portion
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Method to print the array
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Initialize an array of numbers
        int[] numbers = {5, 9, 7, 2, 8, 4, 6};

        System.out.println("\nArray before sorting:");
        printArray(numbers);

        // Sort the array using selection sort function
        selectionSort(numbers);

        System.out.println("\nArray after sorting:");
        printArray(numbers);
    }
}


import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Quyen Tran
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array and comparator "
                    + "cant be null");
        }
        int i = 0;
        boolean swap = true;
        while (i < arr.length - 1 && swap) {
            swap = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swap = true;
                }
            }
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array and comparator "
                    + "cant be null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j = j - 1;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array or comparator or rand"
                    + " cant be null");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Helper method for the quickSort() method
     *
     * @param <T> data type to sort
     * @param arr is the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param left the index starts from the left side
     * @param right the index starts from the right side
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                Random rand, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivotIndex = rand.nextInt(right + 1 - left) + left;
        T pivot = arr[pivotIndex];
        T temp = arr[left];
        arr[left] = arr[pivotIndex];
        arr[pivotIndex] = temp;
        int leftIndex = left + 1;
        int rightIndex = right;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex
                    && comparator.compare(arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }

            while (leftIndex < rightIndex
                    && comparator.compare(arr[rightIndex], pivot) >= 0) {
                rightIndex--;
            }

            if (leftIndex == rightIndex) {
                rightIndex--;
            }

            if (leftIndex < rightIndex) {
                T temp1 = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp1;
                leftIndex++;
                rightIndex--;
            }
        }

        T temp2 = arr[left];
        arr[left] = arr[rightIndex];
        arr[rightIndex] = temp2;
        quickSortHelper(arr, comparator, rand, left, rightIndex - 1);
        quickSortHelper(arr, comparator, rand, leftIndex, right);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator "
                    + "cant be null");
        }

        if (arr.length < 2) {
            return;
        }

        int midIndex = arr.length / 2;
        T[] leftArray = newArray(arr, 0, midIndex);
        T[] rightArray = newArray(arr, midIndex, arr.length);

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;

        while (leftIndex < midIndex && rightIndex < arr.length - midIndex) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex])
                    <= 0) {
                arr[currentIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                arr[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }

        while (leftIndex < midIndex) {
            arr[currentIndex] = leftArray[leftIndex];
            leftIndex++;
            currentIndex++;
        }

        while (rightIndex < arr.length - midIndex) {
            arr[currentIndex] = rightArray[rightIndex];
            rightIndex++;
            currentIndex++;
        }
    }

    /**
     * Copy from the old array to make a new array
     *
     * @param <T> data type to sort
     * @param arr is the old array we already have
     * @param first the begining index in the array
     * @param last the last index in the array
     *
     * @return the new array that is copied
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] newArray(T[] arr, int first, int last) {
        int newLength = last - first;
        T[] newArray = (T[]) new Object[newLength];
        int index = 0;
        for (int i = first; i < last; i++) {
            newArray[index] = arr[i];
            index++;
        }
        return newArray;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    @SuppressWarnings("unchecked")
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cant be null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int h = 0; h < 19; h++) {
            buckets[h] = new LinkedList<Integer>();
        }
        int mostDigit = mostDigit(arr);
        int length = arr.length;
        for (int i = 0; i <= mostDigit; i++) {
            for (int j = 0; j <= length - 1; j++) {
                int num = arr[j];
                int b = (num / pow(10, i)) % 10;
                b = b + 9;
                buckets[b].add(num);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index] = bucket.remove();
                    index++;
                }
            }
        }
        return arr;
    }

    /**
     * Count how many digit of each number in array has
     *
     * @param num the number in the array
     *
     * @return the number digits of a number has
     */
    private static int countDigit(int num) {
        if (num == 0) {
            return 1;
        }
        int digit = 1;
        while ((num / 10) != 0) {
            num = num / 10;
            digit++;
        }
        return digit;
    }

    /**
     * Looking for the number has most digits in the array
     *
     * @param arr the array of numbers
     *
     * @return the number has most digits in the array
     */
    private static int mostDigit(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int mostDigit = 1;
        for (int num : arr) {
            if (Math.abs(num) > mostDigit) {
                mostDigit = Math.abs(num);
            }
        }
        return countDigit(mostDigit);
    }

    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cant be null");
        }
        int mostDigit = mostDigit(arr);
        return msdRadixSortHelper(arr, mostDigit);
    }

    /**
     * Helper method for the msdRadixSort
     *
     * @param arr the array to be sorted
     * @param i how many digit of a number
     *
     * @return the sorted array
     */
    @SuppressWarnings("unchecked")
    private static int[] msdRadixSortHelper(int[] arr, int i) {
        if (arr.length == 0) {
            return arr;
        }
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int h = 0; h < 19; h++) {
            buckets[h] = new ArrayList<Integer>();
        }
        int length = arr.length;
        for (int j = 0; j <= length - 1; j++) {
            int num = arr[j];
            int b = (num / pow(10, i)) % 10;
            b = b + 9;
            buckets[b].add(num);
        }
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            int[] newArray = new int[bucket.size()];
            for (int k = 0; k < newArray.length; k++) {
                newArray[k] = bucket.get(k);
            }
            if (i > 0) {
                newArray = msdRadixSortHelper(newArray, i - 1);
            }
            for (int h = 0; h < newArray.length; h++) {
                arr[index] = newArray[h];
                index++;
            }
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}

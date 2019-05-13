/**
 * Your implementation of a max heap.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    @SuppressWarnings("unchecked")
    public MaxHeap() {
        this.backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then increase its length by 2n + 1 (or twice the
     * current capacity plus one). No duplicates will be added.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The item can not be null");
        }
        if ((size + 1) == backingArray.length) {
            int newLength = 2 * backingArray.length + 1;
            T[] newArray = (T[]) (new Comparable[newLength]);
            for (int i = 1; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        int newItemIndex = size + 1;
        int parentIndex = newItemIndex / 2;
        while (backingArray[parentIndex] != null
                && backingArray[parentIndex].compareTo(item) < 0) {
            backingArray[newItemIndex] = backingArray[parentIndex];
            newItemIndex = parentIndex;
            parentIndex = newItemIndex / 2;
        }
        backingArray[newItemIndex] = item;
        size++;
    }

    /**
     * Removes and returns the first item of the heap. Do not resize the backing
     * array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the item removed
     */
    @Override
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The heap is empty");
        }
        int rootIndex = 1;
        int left = 2;
        int lastItemIndex = size;
        T lastItem = backingArray[size];
        T removed = backingArray[rootIndex];
        backingArray[rootIndex] = lastItem;
        backingArray[lastItemIndex] = null;
        while (left + 1 <= size
                && backingArray[rootIndex]
                    .compareTo(max(backingArray[left],
                        backingArray[left + 1])) < 0) {
            if (backingArray[left + 1] != null && backingArray[left]
                    .compareTo(backingArray[left + 1]) < 0) {
                backingArray[rootIndex] = backingArray[left + 1];
                backingArray[left + 1] = lastItem;
                rootIndex = left + 1;
                left = rootIndex * 2;
            } else {
                backingArray[rootIndex] = backingArray[left];
                backingArray[left] = lastItem;
                rootIndex = left;
                left = rootIndex * 2;
            }
        }
        size--;
        return removed;
    }

    /**
     * Helper method to compare the left and right item
     *
     *@param left The left item
     *@param right The right item
     * 
     * @return the larger item
     */
    private T max(T left, T right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.compareTo(right) < 0) {
            return right;
        } else {
            return left;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        backingArray = (T[]) (new Comparable[INITIAL_CAPACITY]);
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}

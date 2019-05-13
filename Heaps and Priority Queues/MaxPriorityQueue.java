/**
 * Your implementation of a max priority queue.
 * 
 * @author QUYEN TRAN
 * @version 1.0
 */
public class MaxPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueueInterface<T> {

    private HeapInterface<T> backingHeap;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a priority queue.
     */
    public MaxPriorityQueue() {
        backingHeap = new MaxHeap<T>();
    }

    /**
     * Adds an item to the priority queue.
     *
     * @param item the item to be added
     * @throws IllegalArgumentException if the item is null
     */
    @Override
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cant be null");
        }
        backingHeap.add(item);
    }

    /**
     * Removes and returns the first item in the priority queue.
     *
     * @throws java.util.NoSuchElementException if the priority queue is empty
     * @return the item to be dequeued
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The priority queue "
                + "is empty");
        }
        return backingHeap.remove();
    }

    @Override
    public boolean isEmpty() {
        return backingHeap.isEmpty();
    }

    @Override
    public int size() {
        return backingHeap.size();
    }

    @Override
    public void clear() {
        backingHeap.clear();
    }

    @Override
    public HeapInterface<T> getBackingHeap() {
        // DO NOT CHANGE THIS METHOD!
        return backingHeap;
    }

}

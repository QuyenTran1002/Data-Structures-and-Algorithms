/**
 * Your implementation of an array-backed queue.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        this.backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    /**
     * Dequeue from the front of the queue.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you must not
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The queue is empty");
        }
        T data = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % this.backingArray.length;
        size--;
        return data;
    }

    /**
     * Add the given data to the queue.
     *
     * This method should be implemented in (if array-backed, amortized) O(1)
     * time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity. If a regrow is necessary, you should copy
     * elements to the front of the new array and reset front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can not be null");
        }
        if (size == this.backingArray.length) {
            T[] newArray = (T[])
                    (new Object[this.backingArray.length * 2 + 1]);
            for (int i = 0; i < this.backingArray.length; i++) {
                newArray[i] = backingArray[front % backingArray.length];
                front++;
            }
            this.backingArray = newArray;
            backingArray[size] = data;
            front = 0;
            back = size;
            size++;
        } else {
            back = (front + size) % backingArray.length;
            backingArray[back] = data;
            size++;
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY!
        return backingArray;
    }
}
/**
 * Your implementation of an array-backed stack.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Pop from the stack.
     *
     * Removes and returns the top-most element on the stack.
     * This method should be implemented in O(1) time.
     *
     * @return the data from the front of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    /**
     * Pop from the stack.
     *
     * Do not shrink the backing array.
     *
     * @see StackInterface#pop()
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The stack is empty");
        }
        T data = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return data;
    }

    /**
     * Push the given data onto the stack.
     *
     * The given element becomes the top-most element of the stack.
     * This method should be implemented in (if array-backed, amortized) O(1)
     * time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    /**
     * Push the given data onto the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity.
     *
     * @see StackInterface#push(T)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can not be null");
        }
        if (size == backingArray.length) {
            int newLength = backingArray.length * 2 + 1;
            T[] newArray = (T[]) (new Object[newLength]);
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            newArray[size] = data;
            backingArray = newArray;
            size++;
        } else {
            backingArray[size] = data;
            size++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this stack.
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

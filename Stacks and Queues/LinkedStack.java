/**
 * Your implementation of a linked stack.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Return true if this stack contains no elements, false otherwise.
     *
     * This method should be implemented in O(1) time.
     *
     * @return true if the stack is empty; false otherwise
     */
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
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The stack is empty");
        }
        T data = head.getData();
        head = head.getNext();
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
    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (head == null) {
            head = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    /**
     * Return the size of the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @return number of items in the stack
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}
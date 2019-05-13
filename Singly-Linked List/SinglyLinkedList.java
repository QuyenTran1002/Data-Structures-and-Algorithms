/**
 * Your implementation of a SinglyLinkedList
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
import java.util.NoSuchElementException;
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index The requested index for the new element.
     * @param data The data for the new element.
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index can not be "
                    + "negative");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The index can not be "
                    + "greater than size");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size()) {
            addToBack(data);
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            LinkedListNode<T> newNode = new LinkedListNode<T>(data);
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data The data for the new element.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        LinkedListNode<T> curr = new LinkedListNode<T>(data);
        if (head == null) {
            head = curr;
            tail = curr;
        } else {
            curr.setNext(head);
            head = curr;
        }
        size++;        
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data The data for the new element.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes the node and returns the data from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index The requested index to be removed.
     * @return The object formerly located at index.
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size.
     */
    @Override
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index can not be "
                    + "greater than or equal the size");
        }
        T toReturn = null;
        if (isEmpty()) {
            return null;
        } else if (head == tail) {
            toReturn =  head.getData();
            head = null;
            tail = null;
            size = 0;
            return toReturn;
        } else if (index == 0) {
            return removeFromFront();
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            toReturn = current.getNext().getData();
            if (current.getNext() == tail) {
                current.setNext(null);
                tail = current;
                size--;
            } else {
                current.setNext(current.getNext().getNext());
                size--;
            }
            return toReturn;
        } 
    }

    /**
     * Removes the node and returns the data at the front of the list. If the
     * list is empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return The object formerly located at the front.
     */
    @Override
    public T removeFromFront() {
        T toReturn = null;
        if (isEmpty()) {
            return null;
        } else if (head == tail) {
            toReturn =  head.getData();
            head = null;
            tail = null;
            size = 0;
            return toReturn;
        } else {
            toReturn = head.getData();
            head = head.getNext();
            size--;
            return toReturn;
        }
    }

    /**
     * Removes the node and returns the data at the back of the list. If the
     * list is empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return The object formerly located at the back.
     */
    @Override
    public T removeFromBack() {
        return size == 0 ? null : removeAtIndex(size - 1);
    }


    /**
     * Removes the node and returns the first copy of the given data, with
     * 'first' meaning the copy with the smallest index. In other words, the
     * first copy encountered when traversing over the 'next' pointers (starting
     * at the head).
     *
     * Must be O(n) for all cases.
     *
     * @param data The data to be removed from the list.
     * @return The first occurrence of data in the list.
     * @throws java.lang.IllegalArgumentException if data is null.
     * @throws java.util.NoSuchElementException if data is not found.
     */
    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        if (isEmpty()) {
            throw new IllegalArgumentException("The list is empty");
        }
        LinkedListNode<T> current = head;
        if (head.getData().equals(data)) {
            return removeFromFront();
        }
        for (int i = 0; i < size - 1; i++) {
            if (current.getNext().getData().equals(data)) {
                if (current.getNext() == tail && tail.getData().equals(data)) {
                    T removed = current.getNext().getData();
                    current.setNext(null);
                    tail = current;
                    size--;
                    return removed;
                } else {
                    LinkedListNode<T> removedNode = current.getNext();
                    current.setNext(current.getNext().getNext());
                    size--;
                    return removedNode.getData();
                }
            }
            current = current.getNext();
        }
        throw new NoSuchElementException("Data is not found in the list");
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting from indices 0 and {@code size} should be O(1), all other cases
     * are O(n).
     *
     * @param index The index of the requested element.
     * @return The object stored at index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size.
     */
    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index can not be "
                    + "negative");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index can not be "
                + "greater than or equal the size");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        }
        LinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return An array of length {@code size} holding all of the objects in
     * this list in the same order.
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        if (isEmpty()) {
            return array;
        }
        int tempArrayIndex = 0;
        LinkedListNode<T> temp = head;
        while (temp != null) {
            array[tempArrayIndex] = temp.getData();
            temp = temp.getNext();
            tempArrayIndex++;
        }
        return array;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Must be O(1) for all cases.
     *
     * @return The size of the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}

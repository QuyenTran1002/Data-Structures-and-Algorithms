/**
 * Your implementation of an ArrayList.
 *
 * @author QUYEN TRAN
 * @version 1
 */

public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.backingArray = (T[]) (new Object
                [INITIAL_CAPACITY]);
        this.size = 0;
    }

    /**
     * 
     *regrowing the size of the array
     * 
     */
    @SuppressWarnings("unchecked")
    private void regrowing() {
        T[] newArray = (T[]) new Object[backingArray.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The input of data "
                    + "can not be null");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index "
                    + "can not be negative");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("The index "
                    + "can not be larger than the size");
        }
        if (size >= backingArray.length) {
            regrowing();
        }
        for (int i = size; i > index; i--) {
            backingArray[i] = backingArray[i - 1];
        }
        backingArray[index] = data;
        size++;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    @Override
    public T removeAtIndex(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("You can not have "
                    + "negative index");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Your index can not "
                    + "greater than or "
                    + "equal the size of the array");
        }
        T removedElement = backingArray[index];
        if (index != size) {
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
        }
        backingArray[size - 1] = null;
        size--; 
        return removedElement;
    }

    @Override
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index "
                    + "can not be negative");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index can not be "
                    + "greater than or "
                    + "equal the size of the array");
        }
        return backingArray[index]; 
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of an AVL Tree.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cant be null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Element cant be null");
            }
            this.add(element);
        }
    }

    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cant be null");
        }
        AVLNode<T> newNode = new AVLNode<T>(data);
        if (root == null) {
            root = newNode;
            size++;
        } else {
            root = addHelper(root, data);
        }
    }

    /**
    * Helper method for the add() method
    *
    * @param data The data to be added
    * @param current The current node for the new leaf node
    *
    * @return return the current node that we add in the tree
    */
    private AVLNode<T> addHelper(AVLNode<T> current, T data) {
        if (current == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (current.getData().compareTo(data) > 0) {
            current.setLeft(addHelper(current.getLeft(), data));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(addHelper(current.getRight(), data));
        }
        update(current);
        current = rebalance(current);
        return current;
    }

    /**
    * Helper method to update the height and the balance factor of the node
    *
    * @param current The current node that we want to update
    */
    private void update(AVLNode<T> current) {
        int left = heightHelper(current.getLeft());
        int right = heightHelper(current.getRight());
        current.setHeight(1 + Math.max(left, right));
        current.setBalanceFactor(left - right);
    }

    /**
    * Helper method to do the right rotation
    *
    * @param current The current node that we want to rotate
    *
    * @return the node that on the left side of the current
    */
    private AVLNode<T> rightRotation(AVLNode<T> current) {
        AVLNode<T> node = current.getLeft();
        current.setLeft(node.getRight());
        node.setRight(current);
        update(current);
        update(node);
        return node;
    }

    /**
    * Helper method to do the left rotation
    *
    * @param current The current node that we want to rotate
    *
    * @return the node that on the right side of the current
    */
    private AVLNode<T> leftRotation(AVLNode<T> current) {
        AVLNode<T> node = current.getRight();
        current.setRight(node.getLeft());
        node.setLeft(current);
        update(current);
        update(node);
        return node;
    }

    /**
    * Helper method to rebalance the tree 
    *
    * @param current The current node that we want to rebalance
    *
    * @return the node that on the left side of the current
    */
    private AVLNode<T> rebalance(AVLNode<T> current) {
        if (current.getBalanceFactor() > 1) {
            if (current.getLeft().getBalanceFactor() >= 0) {
                return rightRotation(current);
            } else {
                current.setLeft(leftRotation(current.getLeft()));
                return rightRotation(current);
            }
        }
        if (current.getBalanceFactor() < -1) {
            if (current.getRight().getBalanceFactor() <= 0) {
                return leftRotation(current);
            } else {
                current.setRight(rightRotation(current.getRight()));
                return leftRotation(current);
            }
        }
        return current;
    }


    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }

    /**
    *Helper method for remove() to remove the node in different situations
    *if that removed node has no children, one child, or two children
    *
    *@param current The current node of the tree
    *@param data The data of node we are looking for to remove
    *@param dummy The node that help to hold the data of current node
    *
    *@throws java.util.NoSuchElementException if the current node is null
    *@return the node that we want to remove
    */
    private AVLNode<T> removeHelper(AVLNode<T> current,
                T data, AVLNode<T> dummy) {
        if (current == null) {
            throw new java.util.NoSuchElementException("Data is "
                    + "not in tree");
        }
        if (current.getData().compareTo(data) > 0) {
            current.setLeft(removeHelper(current.getLeft(), data, dummy));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removeHelper(current.getRight(), data, dummy));
        } else {
            //node with no children
            dummy.setData(current.getData());
            if (current.getLeft() == null
                    && current.getRight() == null) {
                size--;
                return null;
            } else if (current.getLeft() == null) { //node with one child
                size--;
                return current.getRight();
            } else if (current.getRight() == null) {
                size--;
                return current.getLeft();
            } else { //node with two children
                AVLNode<T> successor = new AVLNode<T>(null);
                current.setRight(successorNode(current.getRight(), successor));
                current.setData(successor.getData());
                size--;
                return current;
            }
        }
        update(current);
        current = rebalance(current);
        return current;
    }

    /**
    *Helper method for removeHelper() to find the successor node
    *
    *@param current The current node of the tree
    *@param dummy The helper node
    *
    *@return the successor node that we are looking for
    */
    private AVLNode<T> successorNode(AVLNode<T> current, AVLNode<T> dummy) {
        if (current.getLeft() != null) {
            current.setLeft(successorNode(current.getLeft(), dummy));
        } else {
            dummy.setData(current.getData());
            return current.getRight();
        }
        update(current);
        current = rebalance(current);
        return current;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        T matchedData = findNode(root, data);
        if (matchedData == null) {
            throw new java.util.NoSuchElementException("Data is not found");
        } else {
            return matchedData;
        }
    }

    /**
    *Recursive method that seaches the node with the data pass in
    *
    *@param current current the currentcurrent node is to check the child for
    *correct data
    *@param data the data is what we are looking for
    *@return the data match with the data is passed in
    */
    private T findNode(AVLNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (current.getData().compareTo(data) > 0) {
            return findNode(current.getLeft(), data);
        } else if (current.getData().compareTo(data) < 0) {
            return findNode(current.getRight(), data);
        } else {
            return current.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        return contains(root, data);
    }

    /**
    *Recursive contains method is to find the if data is
    *contained within the tree
    *
    *@param current the current that roots the subtree
    *@param data the data to search for in the tree
    *@return whether or not the parameter is contained within the tree
    */
    private boolean contains(AVLNode<T> current, T data) {
        if (current == null) {
            return false;
        }
        if (current.getData().compareTo(data) > 0) {
            return contains(current.getLeft(), data);
        } else if (current.getData().compareTo(data) < 0) {
            return contains(current.getRight(), data);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> newList = new LinkedList<T>();
        if (root == null) {
            return newList;
        } else {
            return preorderHelper(root, newList);
        }
    }

    /**
    *Helper method for preoder()
    *
    *@param current The current node of the tree
    *@param newList The list of data in preorder traversal
    *
    *@return the list of data in preorder traversal
    */
    private List<T> preorderHelper(AVLNode<T> current, List<T> newList) {
        if (current != null) {
            newList.add(current.getData());
            preorderHelper(current.getLeft(), newList);
            preorderHelper(current.getRight(), newList);
        }
        return newList;
    }

    @Override
    public List<T> postorder() {
        List<T> newList = new LinkedList<T>();
        if (root == null) {
            return newList;
        } else {
            return postorderHelper(root, newList);
        }
    }

    /**
    *Helper method for postorder()
    *
    *@param current The current node of the tree
    *@param newList The list of data in postorder traversal
    *
    *@return the list of data in postorder traversal
    */
    private List<T> postorderHelper(AVLNode<T> current, List<T> newList) {
        if (current != null) {
            postorderHelper(current.getLeft(), newList);
            postorderHelper(current.getRight(), newList);
            newList.add(current.getData());
        }
        return newList;
    }

    @Override
    public List<T> inorder() {
        List<T> newList = new LinkedList<T>();
        if (root == null) {
            return newList;
        } else {
            return inorderHelper(root, newList);
        }
    }

    /**
    *Helper method for inorder()
    *
    *@param current The current node of the tree
    *@param newList The list of data in inorder traversal
    *
    *@return the list of data in inorder traversal
    */
    private List<T> inorderHelper(AVLNode<T> current, List<T> newList) {
        if (current != null) {
            inorderHelper(current.getLeft(), newList);
            newList.add(current.getData());
            inorderHelper(current.getRight(), newList);
        }
        return newList;
    }

    @Override
    public List<T> levelorder() {
        List<T> newList = new LinkedList<T>();
        Queue<AVLNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> removedNode = queue.remove();
            if (removedNode != null) {
                newList.add(removedNode.getData());
                if (removedNode.getLeft() != null) {
                    queue.add(removedNode.getLeft());
                }
                if (removedNode.getRight() != null) {
                    queue.add(removedNode.getRight());
                }
            }
        }
        return newList;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
    * Helper method for the height() method
    *
    * @param current The current node that we want to get the height
    *
    * @return the height of the node you look at
    */
    private int heightHelper(AVLNode<T> current) {
        if (current == null) {
            return -1;
        }
        return current.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}

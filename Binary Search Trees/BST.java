import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author QUYEN TRAN
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Element cant be null");
            }
            this.add(element);
        }
    }

    /**
     * Add the data as a leaf in the BST.  Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        BSTNode<T> newNode = new BSTNode<T>(data);
        if (root == null) {
            root = newNode;
            size++;
        } else {
            add(root, data);
        }
    }

    /**
    *Recursive method to add data to BST as a leaf node
    *into the correct location.
    *
    *@param current The current node for the new leaf node
    *@param data The data to be added
    */
    private void add(BSTNode<T> current, T data) {
        if (current.getData().compareTo(data) > 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getLeft(), data);
            }
        } else if (current.getData().compareTo(data) < 0) {
            if (current.getRight() == null) {
                current.setRight(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getRight(), data);
            }
        }
    }

    /**
     * Removes the data from the tree.  There are 3 cases to consider:
     *
     * 1: the data is a leaf.  In this case, simply remove it.
     * 2: the data has one child.  In this case, simply replace it with its
     * child.
     * 3: the data has 2 children.  There are generally two approaches:
     * replacing the data with either the largest element that is smaller than
     * the element being removed (commonly called the predecessor), or replacing
     * it with the smallest element that is larger than the element being
     * removed (commonly called the successor). For this assignment, use the
     * successor. You may use iteration to find the successor AFTER you have
     * found the node to be removed recursively (but don't start back at the
     * root to do so).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
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
    private BSTNode<T> removeHelper(BSTNode<T> current,
                T data, BSTNode<T> dummy) {
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
                BSTNode<T> successor = new BSTNode<T>(null);
                current.setRight(successorNode(current.getRight(), successor));
                current.setData(successor.getData());
                size--;
                return current;
            }
        }
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
    private BSTNode<T> successorNode(BSTNode<T> current, BSTNode<T> dummy) {
        if (current.getLeft() != null) {
            current.setLeft(successorNode(current.getLeft(), dummy));
        } else {
            dummy.setData(current.getData());
            return current.getRight();
        }
        return current;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use .equals or == ?).
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
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
    private T findNode(BSTNode<T> current, T data) {
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

    /**
     * Returns whether or not the parameter is contained within the tree.
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
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
    private boolean contains(BSTNode<T> current, T data) {
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

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
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
    private List<T> preorderHelper(BSTNode<T> current, List<T> newList) {
        if (current != null) {
            newList.add(current.getData());
            preorderHelper(current.getLeft(), newList);
            preorderHelper(current.getRight(), newList);
        }
        return newList;
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
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
    private List<T> postorderHelper(BSTNode<T> current, List<T> newList) {
        if (current != null) {
            postorderHelper(current.getLeft(), newList);
            postorderHelper(current.getRight(), newList);
            newList.add(current.getData());
        }
        return newList;
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
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
    private List<T> inorderHelper(BSTNode<T> current, List<T> newList) {
        if (current != null) {
            inorderHelper(current.getLeft(), newList);
            newList.add(current.getData());
            inorderHelper(current.getRight(), newList);
        }
        return newList;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    @Override
    public List<T> levelorder() {
        List<T> newList = new LinkedList<T>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> removedNode = queue.remove();
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
    *Helper method for height()
    *
    *@param current The current node of the tree
    *
    *@return the hieght of the current height
    */
    private int heightHelper(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            int left = heightHelper(current.getLeft());
            int right = heightHelper(current.getRight());
            int currentHeight = 1 + Math.max(left, right);
            return currentHeight;
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 * <p>
 * This class implements a height-balanced binary search tree,
 * using the AVL algorithm. Beyond the constructor, only the insert()
 * and remove() methods need to be implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

    /**
     * Creates an empty AVL tree as a BST organized according to the
     * lessThan predicate.
     */
    public AVLTree(BiPredicate<K, K> lessThan) {
        super(lessThan);
    }

    public boolean isAVL() {
        if (root == null)
            return true;
        else
            return root.isAVL();
    }

    /**
     * TODO
     * Inserts the given key into this AVL tree such that the ordering
     * property for a BST and the balancing property for an AVL tree are
     * maintained.
     */
    public Node insert(K key) {
        Node location = super.insert(key);

        // If after insert it is AVL, then don't worry.
        //if(!isAVL()) balanceTree(location);

        return location;
    }

    // Balance the tree, from upwards. Assume everything below the given node is balanced.
    // Check if there are children.
    protected Node<K> balanceTree(Node<K> curr) {
        if (curr.left.height <= curr.right.height) {
            if (curr.right.left.height <= curr.right.right.height) {

            }
            if (curr.right.left.height > curr.right.right.height) {

            }

        }
        return null;
    }

    protected Node<K> rightRotate(Node<K> n) {
        Node<K> newRoot = new Node<K>(n.get(),n.left.left,n);
        newRoot.parent = n.parent;
        newRoot.left.parent = newRoot;
        newRoot.right.parent = newRoot;

        n.left = n.left.right;
        n.left.parent = n.parent;

        newRoot.left.updateHeight();
        newRoot.right.left.updateHeight();
        newRoot.right.right.updateHeight();

        n = null;
        return newRoot;
    }

    protected Node<K> leftRotate(Node<K> x) {
        Node<K> newRoot = new Node<K>(x.get(),x,x.right.right);
        newRoot.parent = x.parent;
        newRoot.left.parent = newRoot;
        newRoot.right.parent = newRoot;

        newRoot.left.left.parent = newRoot.left;
        newRoot.left.left = x.parent;

        newRoot.left.updateHeight();
        newRoot.right.left.updateHeight();
        newRoot.right.right.updateHeight();

        x = null;
        return newRoot;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        super.remove(key);
    }

}

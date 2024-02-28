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
        Node<K> location = super.insert(key);

        propagateBalance(location);

        return location;
    }

    // If it needed to be balanced, return true. It will balance if needed.
    protected boolean propagateBalance(Node<K> n) {
        if (n == null) return false;
        if (!n.isAVL()) {
            balanceTree(n);
            propagateBalance(n.parent);
            return (true);
        }
        return propagateBalance(n.parent);

    }

    // Balance the tree, from upwards. Assume everything below the given node is balanced.
    // Check if there are children.
    protected Node<K> balanceTree(Node<K> curr) {
        if (curr == null) return curr;
        if (get_height(curr.left) - get_height(curr.right) > 1) {
            if (curr.right == null) {
                curr = leftRotate(curr);
            }
            if (curr.left == null) {
                curr = doubleLeftRotate(curr);
            }
            if(get_height(curr.left.left) >= get_height(curr.right.left)) {
                curr = leftRotate(curr);
            }
            else curr = doubleLeftRotate(curr);
        }
        if (get_height(curr.right) - get_height(curr.left) > 1) {
            if (get_height(curr.right.right) >= get_height(curr.right.left)) {
                curr = rightRotate(curr);
            }
            else curr = doubleRightRotate(curr);
        }
        return curr;
    }

    protected Node<K> rightRotate(Node<K> x) {
        Node<K> k1 = x;
        Node<K> k2 = x.right.left;
        Node<K> k3 = x.right;

        //Shuffle B
        if(x.right != null && x.left != null) k1.left = x.right.left.right;
        else k1 = null;
        if(k1 != null && k1.left != null) k1.left.parent = k1;

        //Shuffle C
        if(x.right != null && x.right.left != null) k3.right = x.right.left.left;
        if(k3.right != null) k3.right.parent = k3;

        // Shuffle k2
        if (k2 != null) k2.left = k1;
        if(k2 != null && k1 != null) k1.parent = k2;
        if(k2 != null) k2.right = k3;
        if(k2 != null && k3 != null) k3.parent = k2;


        if(k1 != null && k1.right != null) k1.right.updateHeight();
        if(k1 != null && k1.left != null) k1.left.updateHeight();
        if(k3.left != null) k3.left.updateHeight();
        if(k3.right != null) k3.right.updateHeight();

        k1.updateHeight();
        k3.updateHeight();

        updateHeightUp(k2);
        return k2;
    }

    protected Node<K> doubleLeftRotate(Node<K> x) {
        x.left = rightRotate(x.left);
        return leftRotate(x);
    }

    protected Node<K> doubleRightRotate(Node<K> x) {
        x.right = leftRotate(x.right);
        return rightRotate(x);
    }

    protected Node<K> leftRotate(Node<K> x) {
        Node<K> k1 = x;
        Node<K> k2 = x.left.right;
        Node<K> k3 = x.left;

        //Shuffle B
        if(x.left != null && x.left.right != null) k1.right = x.left.right.left;
        if(k1.right != null) k1.right.parent = k1;

        //Shuffle C
        if (x.left != null && x.left.right != null) k3.left = x.left.right.right;
        else k3.left = null;
        if (k3.left != null) k3.left.parent = k3;

        // Shuffle k2
        if (k2 != null) k2.left = k1;
        if(k2 != null && k1 != null) k1.parent = k2;
        if(k2 != null) k2.right = k3;
        if(k2 != null && k3 != null) k3.parent = k2;

        if(k1.right != null) k1.right.updateHeight();
        if(k1.left != null) k1.left.updateHeight();
        if(k3.left != null) k3.left.updateHeight();
        if(k3.right != null) k3.right.updateHeight();

        k1.updateHeight();
        k3.updateHeight();

        updateHeightUp(k2);
        return k2;
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

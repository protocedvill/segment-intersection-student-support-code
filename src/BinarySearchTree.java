import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * This is your first major task.
 * <p>
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements OrderedSet<K> {

    /**
     * A Node<K> is a Location (defined in OrderedSet.java), which
     * means that it can be the return value of a search on the tree.
     */

    static class Node<K> implements Location<K> {

        protected K data;
        protected Node<K> left, right;
        protected Node<K> parent;
        protected int height;

        /**
         * Constructs a leaf Node<K> with the given key.
         */
        public Node(K key) {
            this(key, null, null);
        }

        /**
         *
         * <p>
         * Constructs a new Node<K> with the given values for fields.
         */
        public Node(K data, Node<K> left, Node<K> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        /*
         * Provide the get() method required by the Location interface.
         */
        @Override
        public K get() {
            return data;
        }

        /**
         * Return true iff this Node<K> is a leaf in the tree.
         */
        protected boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         *
         * <p>
         * Performs a local update on the height of this Node<K>. Assumes that the
         * heights in the child Node<K>s are correct. Returns true iff the height
         * actually changed. This function *must* run in O(1) time.
         */
        protected boolean updateHeight() {
            int l = -1,r = -1;
            if (left != null) l = left.height;
            if (right != null) r = right.height;
            int nh = Math.max(l, r) + 1;
            boolean updated = (true);//todo
            height = nh;
            return (updated);
        }

        /**
         *
         * <p>
         * Returns the location of the Node<K> containing the inorder predecessor
         * of this Node<K>.
         */
        @Override
        public Location<K> previous() {
            if(left != null) return left.last();
            return prevAncestor();
        }

        private Location<K> last() {
            if (right != null) return right.last();
            return this;
        }

        private Location<K> prevAncestor() {
            if (parent != null && parent.left == this) return parent.prevAncestor();
            return parent;
        }

        /**
         *
         * <p>
         * Returns the location of the Node<K> containing the inorder successor
         * of this Node<K>.
         */
        @Override
        public Location<K> next() {
            if (right != null) return right.first();
            return nextAncestor();
        }

        private Location<K> first() {
            if (left != null) return left.first();
            return this;
        }

        public Location<K> nextAncestor() {
            if (parent != null && parent.right == this) return parent.nextAncestor();
            return parent;
        }

        public boolean isAVL() {
            int h1, h2;
            h1 = get_height(left);
            h2 = get_height(right);
            return Math.abs(h2 - h1) < 2;
        }

        public String toString() {
            return toStringPreorder(this);
        }

    }

    protected Node<K> root;
    protected int numNodes;
    protected BiPredicate<K, K> lessThan;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the lessThan relation.
     */
    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    /**
     *
     * <p>
     * Looks up the key in this tree and, if found, returns the
     * location containing the key.
     *
     *
     */
    public Node<K> search(K key) {
        Node<K> n = find(key, root, null);
        if (n == null) return null;
        if(n.get() != key) return null;
        return n;
    }

    /**
     * Looks up the location of where an item SHOULD be and returns it. If it can't find the item it will return the parent of it's intended location.
     * @param key
     * @param curr
     * @param parent
     * @return
     */
    protected Node<K> find(K key, Node<K> curr, Node<K> parent) {
        if (curr == null && parent != null) return parent;
        if (curr == null) return null;
        if (curr.get() == key) return curr;
        if (lessThan.test(key, curr.get())) return find(key, curr.left, curr);
        if (lessThan.test(curr.get(), key)) return find(key, curr.right, curr);
        return curr;
    }

    /**
     *
     * <p>
     * Returns the height of this tree. Runs in O(1) time!
     */
    public int height() {
        return get_height(root);
    }

    /**
     *
     * <p>
     * Clears all the keys from this tree. Runs in O(1) time!
     */
    public void clear() {
        root = null;
    }

    /**
     * Returns the number of keys in this tree.
     */
    public int size() {
        return numNodes;
    }

    /**
     * <p>
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time. The parent pointer of the new Node<K> and
     * the heights in all Node<K> along the path to the root are adjusted
     * accordingly.
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * Node<K> containing the key), or null if the key is already present.
     */
    public Node<K> insert(K key) {
        Node<K> n = find(key, root, null);
        if (n == null){
            root = new Node<K>(key);
            numNodes++;
            updateHeightUp(root);
            return root;
        }
        if (n.get() == key) return null;
        if (lessThan.test(key, n.data)) {
            Node<K> x = new Node<K>(key);
            n.left = x;
            x.parent = n;
            numNodes++;

            updateHeightUp(x);
            return x;
        }
        if (lessThan.test(n.data, key)) {
            Node<K> x = new Node<K>(key);
            n.right = x;
            x.parent = n;
            numNodes++;

            updateHeightUp(x);
            return x;
        }
        return null;
    }

    private void updateHeightUp(Node<K> x) {
        if(x.updateHeight() && x.parent != null) updateHeightUp(x.parent);
    }

    /**
     * Returns a textual representation of this BST.
     */
    public String toString() {
        return toStringPreorder(root);
    }

    /**
     * Returns true iff the given key is in this BST.
     */
    public boolean contains(K key) {
        Node<K> p = search(key);
        return (p != null);
    }

    /**
     *
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        root = remove_helper(root, key);

    }

    private Node<K> remove_helper(Node<K> curr, K key) {
        if (curr == null) return null;
        if (lessThan.test(key, curr.data)) { // remove in left subtree
            curr.left = remove_helper(curr.left, key);
            return curr;
        }
        if (lessThan.test(curr.data, key)) { // remove in right subtree
            curr.right = remove_helper(curr.right, key);
            return curr;
        }
        // remove this node
        if (curr.get() == key) {
            numNodes--;
            // if node to be deleted has an empty child, overwrite current node to the other child.
            if (curr.left == null && curr.right == null) return null;
            if (curr.left == null) {
                if (curr.parent != null) {curr.right.parent = curr.parent;}
                updateHeightUp(curr.right);
                return curr.right;
            }
            if (curr.right == null) {
                if (curr.parent != null) {curr.left.parent = curr.parent;}
                updateHeightUp(curr.left);
                return curr.left;
            }
            numNodes++;
            // Otherwise two children
            Location<K> min = curr.right.first();
            //Node<K> min = curr.right.first();
            curr.data = min.get();
            curr.right = remove_helper(curr.right, min.get());
            return curr;
        }
        return curr;
    }
    /**
     *  * <p> * Returns a sorted list of all the keys in this tree.
     */
    public List<K> keys() {
        List<K> keys = new ArrayList<>();
        inOrderTraversal(root, keys);
        return keys;
    }

    private void inOrderTraversal(Node<K> x, List<K> keys) {
        if (x == null) return;
        inOrderTraversal(x.left, keys);
        keys.add(x.data);
        inOrderTraversal(x.right, keys);
    }



    static private <K> String toStringPreorder(Node<K> p) {
        if (p == null) return ".";
        String left = toStringPreorder(p.left);
        if (left.length() != 0) left = " " + left;
        String right = toStringPreorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        return "(" + data + "[" + p.height + "]" + left + right + ")";
    }

    /**
     * The get_height method returns the height of the Node<K> n, which may be null.
     */
    static protected <K> int get_height(Node<K> n) {
        if (n == null) return -1;
        else return n.height;
    }
}

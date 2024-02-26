import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.Random;


public class StudentTest {

    @Test
    public void insertSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        /*
         *       4
         *     /  \
         *    /    \
         *   0      8
         *    \    / \
         *     2  6   10
         */
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void insertSmallBSTDemo() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{12, 3, 10, 6, 7, 1, 0, 9};


        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }
        for (int i = 0; i != 11; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }
    }

    @Test
    public void deleteSmallBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int[] a = new int[]{4, 8, 0, 2, 6, 10};
        for (Integer key : a) {
            bst.insert(key);
            map.put(key, key);
        }

        // remove 3 valid items
        int[] b = new int[]{5,4,6,19,0};
        for (Integer key : b) {
            bst.remove(key);
            map.remove(key, key);
        }

        for (int i = 0; i != 20; ++i) {
            assertEquals(bst.contains(i), map.containsKey(i));
        }

        assertEquals(bst.numNodes, 3);
        //System.out.println(bst.keys());

    }

    @Test
    public void largeRandomBST() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();

        Random r = new Random();
        int[] a = new int[10000];
        for (int x : a) {
            x = r.nextInt();
            bst.insert(x);
            map.put(x, x);
        }

        for (int x : a) {
            assertEquals(bst.contains(x), map.containsKey(x));
        }

        assertTrue(validateSorted(bst));
    }

    protected boolean validateSorted(BinarySearchTree<Integer> bst) {
        Integer prevKey = bst.keys().get(0) - 1;
        for (int key : bst.keys()) {
            if(key <= prevKey) return false;
            prevKey = key;
        }
        return true;
    }

    @Test
    public void AVLBalanced() {
        AVLTree<Integer> bst = new AVLTree<>((Integer x, Integer y) -> x < y);
        TreeMap<Integer, Integer> map = new TreeMap<>();

        Random r = new Random();
        int[] a = new int[1000];
        for (int x : a) {
            x = r.nextInt();
            bst.insert(x);
            map.put(x, x);
        }

        for (int x : a) {
            assertTrue(bst.isAVL());
            assertTrue(validateSorted(bst));
            assertEquals(bst.contains(x), map.containsKey(x));
        }
    }

    @Test
    public void test() {
        insertSmallBST();
        deleteSmallBST();
        largeRandomBST();
        AVLBalanced();
        // your tests go here
    }

}

package ch.jobtrek.datastructures.tree;

import java.util.List;

/**
 * <h1>Module 5 — Hierarchical Relationships and the Binary Search Tree</h1>
 *
 * <p>A Binary Search Tree (BST) is a hierarchical data structure where each node
 * contains a value and up to two child references (left and right). The BST enforces
 * a strict <strong>structural invariant</strong>:</p>
 * <ul>
 *   <li>All values in a node's <strong>left</strong> subtree are strictly
 *       <strong>less than</strong> the node's value.</li>
 *   <li>All values in a node's <strong>right</strong> subtree are strictly
 *       <strong>greater than</strong> the node's value.</li>
 * </ul>
 *
 * <p>This invariant enables O(log n) search, insertion, and retrieval — provided
 * the tree remains reasonably <strong>balanced</strong>.</p>
 *
 * <h2>The degenerate tree problem</h2>
 * <p>If elements are inserted in strictly ascending or descending order (e.g.,
 * 1, 2, 3, 4, 5), the BST degenerates into a linked list, and all operations
 * degrade from O(log n) to O(n). Production systems use self-balancing variants
 * (AVL trees, Red-Black trees) to prevent this. This exercise uses a naive BST
 * to illustrate the problem.</p>
 *
 * <h2>Recursive traversals</h2>
 * <ul>
 *   <li><strong>In-Order (Left → Node → Right):</strong> Visits nodes in ascending
 *       sorted order. This is the key traversal that leverages the BST invariant.</li>
 *   <li><strong>Pre-Order (Node → Left → Right):</strong> Used for creating exact
 *       structural copies of the tree.</li>
 *   <li><strong>Post-Order (Left → Right → Node):</strong> Used for safe deletion
 *       (children processed before parent).</li>
 * </ul>
 *
 * @param <T> the type of elements stored in the tree, must implement {@link Comparable}
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root;

    /**
     * Creates an empty Binary Search Tree.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Returns the root node of the tree. Used internally by tests.
     *
     * @return the root node, or null if the tree is empty
     */
    Node<T> getRoot() {
        return root;
    }

    /**
     * <h3>Exercise 1 — Insert a value into the BST</h3>
     *
     * <p>Insert the given value into the tree while maintaining the BST invariant.
     * If the value already exists in the tree, do nothing (no duplicates).</p>
     *
     * <p><strong>Algorithm (recursive):</strong></p>
     * <ol>
     *   <li>If the current node is {@code null}, create a new {@link Node} with the
     *       value and return it — this is the base case.</li>
     *   <li>Compare the value to the current node's value using {@code compareTo()}:
     *       <ul>
     *         <li>If {@code value < node.value}: recurse into the left subtree.</li>
     *         <li>If {@code value > node.value}: recurse into the right subtree.</li>
     *         <li>If equal: do nothing (no duplicates).</li>
     *       </ul>
     *   </li>
     *   <li>Return the (possibly updated) current node.</li>
     * </ol>
     *
     * <p><strong>Implementation hint:</strong> Write a private recursive helper method
     * {@code insertRec(Node<T> node, T value)} that returns the updated node. In this
     * public method, call: {@code this.root = insertRec(this.root, value);}</p>
     *
     * @param value the value to insert
     */
    public void insert(T value) {
        this.root = insertRec(this.root, value);
    }

    private Node<T> insertRec(Node<T> node, T value) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 2 — In-Order Traversal</h3>
     *
     * <p>Traverse the tree in <strong>in-order</strong> (Left → Node → Right) and
     * append each node's value to the provided list.</p>
     *
     * <p>Because of the BST invariant, an in-order traversal produces values in
     * <strong>ascending sorted order</strong>. This is the primary way to extract
     * sorted data from a BST.</p>
     *
     * <p><strong>Algorithm (recursive):</strong></p>
     * <ol>
     *   <li>If the current node is {@code null}, return (base case).</li>
     *   <li>Recurse into the <strong>left</strong> subtree.</li>
     *   <li>Append the current node's value to the result list.</li>
     *   <li>Recurse into the <strong>right</strong> subtree.</li>
     * </ol>
     *
     * <p><strong>Implementation hint:</strong> Write a private recursive helper method
     * {@code inOrderRec(Node<T> node, List<T> result)} and call it from here with
     * {@code this.root}.</p>
     *
     * @param result the list to populate with values in ascending order
     */
    public void inOrderTraversal(List<T> result) {
        inOrderRec(this.root, result);
    }

    private void inOrderRec(Node<T> node, List<T> result) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 3 — Count the number of nodes (size)</h3>
     *
     * <p>Return the total number of nodes in the tree.</p>
     *
     * <p><strong>Algorithm (recursive):</strong></p>
     * <ol>
     *   <li>If the current node is {@code null}, return 0 (base case).</li>
     *   <li>Return {@code 1 + size(left) + size(right)}.</li>
     * </ol>
     *
     * <p>This exercises <strong>bottom-up recursion</strong>: each call returns an
     * integer that gets aggregated by its parent call.</p>
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return sizeRec(this.root);
    }

    private int sizeRec(Node<T> node) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 4 — Compute the height (maximum depth)</h3>
     *
     * <p>Return the height of the tree, defined as the number of edges on the
     * longest path from the root to a leaf. An empty tree has height {@code -1},
     * a single-node tree has height {@code 0}.</p>
     *
     * <p><strong>Algorithm (recursive):</strong></p>
     * <ol>
     *   <li>If the current node is {@code null}, return {@code -1} (base case).</li>
     *   <li>Compute the height of the left subtree and the right subtree.</li>
     *   <li>Return {@code 1 + Math.max(leftHeight, rightHeight)}.</li>
     * </ol>
     *
     * <p><strong>Why this matters:</strong> A balanced BST with n nodes has height
     * ~log₂(n). A degenerate BST has height n-1. Comparing size() and height()
     * reveals how balanced the tree is.</p>
     *
     * @return the height of the tree (-1 if empty)
     */
    public int height() {
        return heightRec(this.root);
    }

    private int heightRec(Node<T> node) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 5 — Find the maximum value</h3>
     *
     * <p>Return the largest value stored in the tree. In a BST, the maximum value
     * is always the <strong>rightmost</strong> node — just keep following the right
     * child until you hit {@code null}.</p>
     *
     * <p><strong>Algorithm:</strong></p>
     * <ol>
     *   <li>Start at the root.</li>
     *   <li>While the current node has a right child, move to the right child.</li>
     *   <li>Return the current node's value.</li>
     * </ol>
     *
     * <p><strong>Complexity:</strong> O(h) where h is the height of the tree —
     * O(log n) for balanced, O(n) for degenerate.</p>
     *
     * @return the maximum value, or {@code null} if the tree is empty
     */
    public T findMax() {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 6 — Search for a value (contains)</h3>
     *
     * <p>Return {@code true} if the tree contains the given value, {@code false}
     * otherwise.</p>
     *
     * <p><strong>Algorithm (recursive):</strong></p>
     * <ol>
     *   <li>If the current node is {@code null}, return {@code false} (not found).</li>
     *   <li>Compare the value to the current node's value:
     *       <ul>
     *         <li>If equal: return {@code true}.</li>
     *         <li>If less: recurse into the left subtree.</li>
     *         <li>If greater: recurse into the right subtree.</li>
     *       </ul>
     *   </li>
     * </ol>
     *
     * <p><strong>Complexity:</strong> O(log n) for balanced, O(n) for degenerate.</p>
     *
     * @param value the value to search for
     * @return true if found, false otherwise
     */
    public boolean contains(T value) {
        return containsRec(this.root, value);
    }

    private boolean containsRec(Node<T> node, T value) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * A single node in the binary search tree.
     *
     * <p>Each node holds a value and references to its left and right children.
     * A {@code null} child reference means there is no child on that side.</p>
     *
     * <p><strong>Students:</strong> This inner class is provided for you. Do not modify it.</p>
     */
    static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}
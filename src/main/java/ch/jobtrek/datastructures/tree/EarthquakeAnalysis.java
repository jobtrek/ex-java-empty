package ch.jobtrek.datastructures.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Earthquake Scenario — Practical BST Data Modeling</h1>
 *
 * <p>This exercise applies the {@link BinarySearchTree} to a real-world scenario:
 * storing and querying earthquake data. Earthquakes are sorted by seismic
 * {@code magnitude}, which serves as the natural comparison key via
 * {@link Earthquake#compareTo(Earthquake)}.</p>
 *
 * <p>By using the BST, you can efficiently find the largest earthquake or
 * retrieve all events in sorted order without explicitly sorting.</p>
 */
public class EarthquakeAnalysis {

    /**
     * <h3>Exercise 1 — Build an Earthquake BST</h3>
     *
     * <p>Populate a new {@link BinarySearchTree} with all the earthquakes from
     * the provided list.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new {@code BinarySearchTree<Earthquake>}.</li>
     *   <li>Iterate over the list and call {@code tree.insert(earthquake)} for each.</li>
     *   <li>Return the populated tree.</li>
     * </ol>
     *
     * <p><strong>Note:</strong> Since {@link Earthquake} implements {@code Comparable}
     * by magnitude, the BST will organize earthquakes from weakest (leftmost) to
     * strongest (rightmost).</p>
     *
     * @param quakes the list of earthquakes to insert
     * @return a populated BinarySearchTree
     */
    public static BinarySearchTree<Earthquake> buildTree(List<Earthquake> quakes) {
        // TODO: complete this method
        // --sw-wipe--
        var tree = new BinarySearchTree<Earthquake>();
        for (Earthquake quake : quakes) {
            tree.insert(quake);
        }
        return tree;
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 2 — Find the Largest Magnitude Earthquake</h3>
     *
     * <p>Use the BST's {@link BinarySearchTree#findMax()} method to retrieve
     * the earthquake with the highest magnitude <strong>without traversing
     * the entire tree</strong>.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code tree.findMax()} and return the result.</li>
     * </ol>
     *
     * <p><strong>Why this is efficient:</strong> In a BST, the maximum value is
     * always at the rightmost node. Finding it requires following right pointers
     * until null — O(log n) for a balanced tree, O(n) for a degenerate one.
     * No need to visit every node.</p>
     *
     * @param tree the populated earthquake BST
     * @return the earthquake with the highest magnitude, or null if empty
     */
    public static Earthquake findLargestMagnitude(BinarySearchTree<Earthquake> tree) {
        // TODO: complete this method
        // --sw-wipe--
        return tree.findMax();
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 3 — Get All Earthquakes Sorted by Magnitude</h3>
     *
     * <p>Use the BST's {@link BinarySearchTree#inOrderTraversal(List)} method to
     * retrieve all earthquakes in <strong>ascending order of magnitude</strong>.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new empty {@code ArrayList<Earthquake>}.</li>
     *   <li>Call {@code tree.inOrderTraversal(list)} to populate it.</li>
     *   <li>Return the list.</li>
     * </ol>
     *
     * <p><strong>Key insight:</strong> The in-order traversal of a BST automatically
     * produces sorted output — no explicit sorting algorithm needed. The BST
     * invariant guarantees left &lt; node &lt; right at every level.</p>
     *
     * @param tree the populated earthquake BST
     * @return a list of earthquakes sorted by magnitude ascending
     */
    public static List<Earthquake> getSortedByMagnitude(BinarySearchTree<Earthquake> tree) {
        // TODO: complete this method
        // --sw-wipe--
        var result = new ArrayList<Earthquake>();
        tree.inOrderTraversal(result);
        return result;
        // --sw-wipe--
    }
}

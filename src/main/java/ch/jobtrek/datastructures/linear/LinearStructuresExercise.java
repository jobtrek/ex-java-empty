package ch.jobtrek.datastructures.linear;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>Module 1 — Linear Data Structures: ArrayList vs LinkedList</h1>
 *
 * <p>This exercise explores the fundamental differences between contiguous-memory
 * lists ({@link ArrayList}) and node-based lists ({@link LinkedList}). You will
 * implement methods that populate, insert into, and optimize these structures,
 * and observe how hardware realities (CPU cache locality, memory layout) affect
 * real-world performance beyond what Big-O notation predicts.</p>
 *
 * <h2>Key concepts</h2>
 * <ul>
 *   <li><strong>Cache locality:</strong> ArrayList stores elements in a contiguous
 *       memory block. When the CPU accesses one element, neighboring elements are
 *       prefetched into L1/L2 caches, making sequential access extremely fast.</li>
 *   <li><strong>Pointer chasing:</strong> LinkedList scatters nodes across the heap.
 *       Traversal requires following pointers to random memory locations, causing
 *       continuous cache misses.</li>
 *   <li><strong>Memory overhead:</strong> Each LinkedList node carries extra overhead
 *       for prev/next pointers and an object header, while ArrayList only has a
 *       backing array with some spare capacity.</li>
 * </ul>
 */
public class LinearStructuresExercise {

    /**
     * <h3>Exercise 1 — List Population</h3>
     *
     * <p>Create both an {@link ArrayList} and a {@link LinkedList}, then populate
     * each with sequential integers from {@code 0} (inclusive) to {@code size}
     * (exclusive).</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Instantiate a new {@code ArrayList<Integer>}. <em>Performance tip:</em>
     *       if you know the final size up front, pass it to the constructor
     *       ({@code new ArrayList<>(size)}) to avoid repeated internal array
     *       resizing.</li>
     *   <li>Instantiate a new {@code LinkedList<Integer>}.</li>
     *   <li>Using a loop, add integers {@code 0, 1, 2, ..., size-1} to <em>both</em> lists.</li>
     *   <li>Return them wrapped in a {@link ListPair}.</li>
     * </ol>
     *
     * <p><strong>Expected complexity:</strong> O(n) for both lists — appending to
     * the end is amortized O(1) for ArrayList and O(1) for LinkedList.</p>
     *
     * @param size the number of elements to insert (must be &gt; 0)
     * @return a {@link ListPair} containing both populated lists
     */
    public static ListPair populateLists(int size) {
    }

    /**
     * <h3>Exercise 2 — Middle Insertion</h3>
     *
     * <p>Insert the given {@code value} at the <em>middle index</em> of the provided list.
     * The middle index is defined as {@code list.size() / 2}.</p>
     *
     * <p><strong>Observation to make after running the tests:</strong> Despite both
     * operations being theoretically O(n), the ArrayList will be significantly faster
     * for middle insertions on large lists. This is because:</p>
     * <ul>
     *   <li>ArrayList shifts elements via a fast {@code System.arraycopy()} — a single
     *       contiguous memory move that benefits from CPU cache prefetching.</li>
     *   <li>LinkedList must traverse n/2 nodes by following pointers scattered across
     *       the heap (pointer chasing), causing constant cache misses.</li>
     * </ul>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Compute the middle index: {@code list.size() / 2}.</li>
     *   <li>Use {@code list.add(index, value)} to insert at that position.</li>
     * </ol>
     *
     * @param list  the list to insert into (either ArrayList or LinkedList)
     * @param value the integer value to insert
     */
    public static void insertAtMiddle(List<Integer> list, int value) {
    }

    /**
     * <h3>Exercise 3 — Head Insertion</h3>
     *
     * <p>Insert the given {@code value} at index {@code 0} (the head) of the provided list.</p>
     *
     * <p><strong>This is the one scenario where LinkedList truly shines:</strong></p>
     * <ul>
     *   <li>LinkedList maintains a direct pointer to the head node. Inserting at
     *       index 0 is a genuine O(1) operation — just update two pointers.</li>
     *   <li>ArrayList must shift <em>every</em> existing element one position to the
     *       right via {@code System.arraycopy()}, making it O(n).</li>
     * </ul>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Use {@code list.addFirst(value)} to insert at the head. This method
     *       was introduced by the {@link java.util.SequencedCollection} interface
     *       in Java 21 and is semantically equivalent to {@code list.add(0, value)},
     *       but more expressive and intention-revealing.</li>
     * </ol>
     *
     * @param list  the list to insert into (either ArrayList or LinkedList)
     * @param value the integer value to insert at position 0
     */
    public static void insertAtHead(List<Integer> list, int value) {
    }

    /**
     * <h3>Exercise 4 — Memory Optimization with trimToSize()</h3>
     *
     * <p>When an {@link ArrayList} grows, it dynamically expands its internal backing
     * array by a factor of approximately 1.5x. This means the array often has more
     * capacity than elements. For example, after adding 1000 elements, the internal
     * array might have capacity for 1500.</p>
     *
     * <p>The {@link ArrayList#trimToSize()} method reduces the internal array capacity
     * to match the actual number of elements, freeing unused memory. This is useful
     * when you know no more elements will be added.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code list.trimToSize()} on the provided ArrayList.</li>
     *   <li>Return the same list (the trim is done in-place).</li>
     * </ol>
     *
     * <p><strong>Note:</strong> LinkedList has no equivalent method because each node
     * is individually allocated — there is no "spare capacity" concept.</p>
     *
     * @param list the ArrayList to optimize
     * @return the same ArrayList after trimming
     */
    public static ArrayList<Integer> optimizeMemory(ArrayList<Integer> list) {
    }
}
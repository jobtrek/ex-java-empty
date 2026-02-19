package ch.jobtrek.datastructures.benchmarks;

import java.util.Collection;
import java.util.List;

/**
 * <h1>Module 6 — Performance Benchmarks: Theory vs Reality</h1>
 *
 * <p>Big-O notation tells you how an algorithm <em>scales</em>, but it says nothing
 * about the constant factors that dominate real-world performance: CPU cache misses,
 * memory allocation overhead, branch prediction, and garbage-collection pressure.
 * This module makes those hidden costs <strong>visible</strong> by timing identical
 * operations on different data structures.</p>
 *
 * <h2>Methodology</h2>
 * <p>Each exercise asks you to implement a <strong>small, focused operation</strong>
 * (2–4 lines of code). The accompanying tests then call your method on several
 * data-structure implementations, measure elapsed time with
 * {@link System#nanoTime()}, and print a comparison table.</p>
 *
 * <p><strong>Important caveats:</strong></p>
 * <ul>
 *   <li>These are <em>not</em> production-grade benchmarks. A proper benchmark tool
 *       (such as JMH) handles JVM warm-up, dead-code elimination, and statistical
 *       analysis. Our measurements are good enough to reveal order-of-magnitude
 *       differences, which is the learning objective.</li>
 *   <li>Absolute numbers vary across machines and JVM versions. Focus on the
 *       <strong>ratios</strong> between data structures, not the raw nanoseconds.</li>
 *   <li>The JVM's Just-In-Time (JIT) compiler optimises hot code paths after
 *       several thousand iterations, so the first few calls may be slower than
 *       subsequent ones.</li>
 * </ul>
 *
 * <h2>What you will observe</h2>
 * <ol>
 *   <li><strong>Exercise 1 — Sequential append:</strong> ArrayList vs LinkedList.
 *       Both are amortized O(1), yet ArrayList is significantly faster because it
 *       writes into a contiguous array (cache-friendly) while LinkedList allocates
 *       a new {@code Node} object per element (GC pressure, scattered memory).</li>
 *   <li><strong>Exercise 2 — Indexed access ({@code get(i)}):</strong> ArrayList
 *       vs LinkedList. O(1) vs O(n) per call — the most dramatic difference,
 *       easily 1 000× or more on moderately sized lists.</li>
 *   <li><strong>Exercise 3 — Membership test ({@code contains()}):</strong>
 *       HashSet vs TreeSet vs ArrayList. O(1) vs O(log n) vs O(n) — demonstrates
 *       why choosing the right collection type is critical for search-heavy
 *       workloads.</li>
 * </ol>
 */
public class PerformanceBenchmarkExercise {

    /**
     * <h3>Exercise 1 — Sequential Append</h3>
     *
     * <p>Append integers {@code 0, 1, 2, ..., count-1} to the provided list.</p>
     *
     * <p>The benchmark test will call this method twice — once with an
     * {@link java.util.ArrayList} and once with a {@link java.util.LinkedList} —
     * and compare the elapsed time.</p>
     *
     * <p><strong>What to implement:</strong></p>
     * <ol>
     *   <li>Write a {@code for} loop from {@code 0} to {@code count} (exclusive).</li>
     *   <li>Inside the loop, call {@code list.add(i)}.</li>
     * </ol>
     *
     * <p><strong>What to observe after running the test:</strong></p>
     * <ul>
     *   <li>Both structures are amortized O(1) for tail-append, yet ArrayList is
     *       typically <strong>2–5× faster</strong>.</li>
     *   <li><em>Why?</em> ArrayList writes into a pre-allocated contiguous array.
     *       When the array is full, it grows by ~50% via a single
     *       {@code Arrays.copyOf()} — an optimised bulk memory copy. LinkedList,
     *       by contrast, allocates a new {@code Node} object (24+ bytes of overhead)
     *       for <em>every single element</em>, scattering data across the heap.</li>
     *   <li>The extra object allocations also create garbage-collection pressure:
     *       more objects to track, more work for the GC, and more cache pollution.</li>
     * </ul>
     *
     * @param list  an empty list (ArrayList or LinkedList) to populate
     * @param count the number of elements to append (e.g. 1 000 000)
     */
    public static void appendElements(List<Integer> list, int count) {
        // TODO: complete this method
        // --sw-wipe--
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 2 — Indexed Access ({@code get(i)})</h3>
     *
     * <p>Compute the sum of all elements in the list by accessing each element
     * via its index: {@code list.get(0)}, {@code list.get(1)}, ...,
     * {@code list.get(size - 1)}.</p>
     *
     * <p>The benchmark test will call this method with both an ArrayList and a
     * LinkedList containing the same data, and compare the elapsed time.</p>
     *
     * <p><strong>What to implement:</strong></p>
     * <ol>
     *   <li>Initialise a {@code long sum = 0}.</li>
     *   <li>Write a {@code for} loop from {@code 0} to {@code list.size()}.</li>
     *   <li>Inside the loop, add {@code list.get(i)} to {@code sum}.</li>
     *   <li>Return {@code sum}.</li>
     * </ol>
     *
     * <p><strong>What to observe after running the test:</strong></p>
     * <ul>
     *   <li>ArrayList completes almost instantly (O(1) per access — it is a simple
     *       array offset calculation).</li>
     *   <li>LinkedList is <strong>catastrophically slow</strong> — easily
     *       <strong>1 000× or more</strong> — because every {@code get(i)} call
     *       must traverse the linked chain from the head (or tail) node, following
     *       {@code i} pointers scattered across the heap. This results in O(n)
     *       per call, making the overall loop O(n²).</li>
     *   <li><em>Lesson:</em> Never use index-based iteration on a LinkedList.
     *       If you need sequential access, use an {@link java.util.Iterator} or
     *       an enhanced {@code for} loop instead, which follow the internal
     *       pointers in O(1) per step.</li>
     * </ul>
     *
     * @param list a list of integers to sum (may be ArrayList or LinkedList)
     * @return the sum of all elements
     */
    public static long sumByIndex(List<Integer> list) {
        // TODO: complete this method
        // --sw-wipe--
        long sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        return sum;
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 3 — Membership Test ({@code contains()})</h3>
     *
     * <p>Given a collection and a list of query values, count how many queries
     * are found in the collection using {@link Collection#contains(Object)}.</p>
     *
     * <p>The benchmark test will call this method with the <em>same data</em>
     * stored in an {@link java.util.ArrayList}, a {@link java.util.HashSet}, and
     * a {@link java.util.TreeSet}, and compare the elapsed time.</p>
     *
     * <p><strong>What to implement:</strong></p>
     * <ol>
     *   <li>Initialise an {@code int hits = 0}.</li>
     *   <li>Iterate over the {@code queries} list.</li>
     *   <li>For each query, call {@code collection.contains(query)}. If it
     *       returns {@code true}, increment {@code hits}.</li>
     *   <li>Return {@code hits}.</li>
     * </ol>
     *
     * <p><strong>What to observe after running the test:</strong></p>
     * <ul>
     *   <li><strong>HashSet</strong> — O(1) per lookup thanks to hashing.
     *       Completes almost instantly regardless of collection size.</li>
     *   <li><strong>TreeSet</strong> — O(log n) per lookup via Red-Black Tree
     *       traversal. Fast, but measurably slower than HashSet.</li>
     *   <li><strong>ArrayList</strong> — O(n) per lookup: scans the entire array
     *       sequentially until a match is found (or not). With 10 000 queries
     *       against 100 000 elements, this results in up to 10⁹ comparisons —
     *       dramatically slower than the other two.</li>
     *   <li><em>Lesson:</em> If your workload involves frequent membership
     *       checks, always prefer a Set over a List. The choice between HashSet
     *       and TreeSet depends on whether you also need sorted iteration.</li>
     * </ul>
     *
     * @param collection the collection to search in (ArrayList, HashSet, or TreeSet)
     * @param queries    the list of values to look up
     * @return the number of queries found in the collection
     */
    public static int countHits(Collection<Integer> collection, List<Integer> queries) {
        // TODO: complete this method
        // --sw-wipe--
        int hits = 0;
        for (int query : queries) {
            if (collection.contains(query)) {
                hits++;
            }
        }
        return hits;
        // --sw-wipe--
    }
}

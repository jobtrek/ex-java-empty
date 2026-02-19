package ch.jobtrek.datastructures.maps;

import java.util.*;

/**
 * <h1>Module 3 — Key-Value Associations and Hash Mechanics</h1>
 *
 * <p>The {@link Map} interface maps unique keys to values. This module dissects the
 * three primary Map implementations and the modern {@link SequencedMap} interface:</p>
 *
 * <ul>
 *   <li><strong>{@link HashMap}:</strong> Amortized O(1) put/get via hash functions.
 *       No ordering guarantees. Internally uses buckets that "treeify" into Red-Black
 *       Trees when collision chains exceed 8 elements, restoring worst-case to O(log n)
 *       instead of O(n).</li>
 *   <li><strong>{@link TreeMap}:</strong> Backed by a Red-Black Tree. Guarantees
 *       O(log n) for all operations. Keys are iterated in <em>natural sorted order</em>
 *       (or by a provided Comparator). More CPU overhead than HashMap due to constant
 *       tree rebalancing.</li>
 *   <li><strong>{@link LinkedHashMap}:</strong> A hybrid: HashMap speed with a
 *       doubly-linked list threading through all entries, preserving <em>insertion
 *       order</em>. Slightly higher memory footprint due to the pointer network.
 *       In Java 21+, it natively implements {@link SequencedMap}.</li>
 * </ul>
 *
 * <h2>The equals() / hashCode() contract</h2>
 * <p>Hash-based structures (HashMap, LinkedHashMap) depend entirely on a correct
 * {@code equals()} and {@code hashCode()} implementation. If two objects are
 * {@code equals()}, they <strong>must</strong> return the same {@code hashCode()}.
 * Breaking this contract causes silent data loss: elements become permanently
 * unretrievable from the map.</p>
 */
public class MapStructuresExercise {

    /**
     * <h3>Exercise 1 — Populate a HashMap</h3>
     *
     * <p>Insert all key-value pairs from the provided parallel lists into a new
     * {@link HashMap} and return it.</p>
     *
     * <p><strong>Key observation:</strong> After insertion, the iteration order of
     * the HashMap's {@code keySet()} will <strong>not</strong> match the insertion
     * order. The order is determined by the internal hash bucket layout and is
     * effectively pseudorandom.</p>
     *
     * <p><em>Note to students:</em> Exercises 1, 2, and 3 share identical
     * implementation bodies on purpose. The learning objective is <strong>not</strong>
     * the insertion code — it is observing (via the tests) how each Map type
     * produces a different iteration order from the <em>same</em> input data.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new {@code HashMap<String, String>}.</li>
     *   <li>Iterate over both lists simultaneously (they have the same size).</li>
     *   <li>For each index {@code i}, call {@code map.put(keys.get(i), values.get(i))}.</li>
     *   <li>Return the populated map.</li>
     * </ol>
     *
     * @param keys   the list of keys
     * @param values the list of values (same size as keys)
     * @return a HashMap populated with the key-value pairs
     */
    public static HashMap<String, String> populateHashMap(List<String> keys, List<String> values) {
        // TODO: complete this method
        // --sw-wipe--
        var map = new HashMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 2 — Populate a TreeMap</h3>
     *
     * <p>Insert all key-value pairs into a new {@link TreeMap} and return it.</p>
     *
     * <p><strong>Key observation:</strong> The TreeMap's {@code keySet()} will always
     * iterate in <strong>natural alphabetical order</strong>, regardless of insertion
     * order. This is because the underlying Red-Black Tree maintains sorted structure
     * through rotational rebalancing after every insertion.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new {@code TreeMap<String, String>}.</li>
     *   <li>Insert all key-value pairs exactly as in Exercise 1.</li>
     *   <li>Return the populated map.</li>
     * </ol>
     *
     * <p><strong>Complexity:</strong> O(n log n) total — each of the n insertions
     * costs O(log n) for tree placement and rebalancing.</p>
     *
     * @param keys   the list of keys
     * @param values the list of values (same size as keys)
     * @return a TreeMap populated with the key-value pairs
     */
    public static TreeMap<String, String> populateTreeMap(List<String> keys, List<String> values) {
        // TODO: complete this method
        // --sw-wipe--
        var map = new TreeMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 3 — Populate a LinkedHashMap</h3>
     *
     * <p>Insert all key-value pairs into a new {@link LinkedHashMap} and return it.</p>
     *
     * <p><strong>Key observation:</strong> The LinkedHashMap's {@code keySet()} will
     * iterate in <strong>exact insertion order</strong>. It achieves this by maintaining
     * a doubly-linked list that threads through all entries, while still providing
     * O(1) hash-based lookups.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new {@code LinkedHashMap<String, String>}.</li>
     *   <li>Insert all key-value pairs exactly as in Exercise 1.</li>
     *   <li>Return the populated map.</li>
     * </ol>
     *
     * @param keys   the list of keys
     * @param values the list of values (same size as keys)
     * @return a LinkedHashMap populated with the key-value pairs
     */
    public static LinkedHashMap<String, String> populateLinkedHashMap(List<String> keys, List<String> values) {
        // TODO: complete this method
        // --sw-wipe--
        var map = new LinkedHashMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 4 — FIFO Queue with SequencedMap (Java 21+)</h3>
     *
     * <p>Use the {@link SequencedMap} API to simulate a First-In-First-Out (FIFO)
     * processing pipeline. You will add entries to the <em>front</em> of the map
     * and remove entries from the <em>back</em>.</p>
     *
     * <p>The {@code SequencedMap} interface (introduced in Java 21) provides methods
     * that were previously impossible without workarounds:</p>
     * <ul>
     *   <li>{@code putFirst(key, value)} — inserts at the beginning of the encounter order</li>
     *   <li>{@code putLast(key, value)} — inserts at the end</li>
     *   <li>{@code pollFirstEntry()} — removes and returns the first entry</li>
     *   <li>{@code pollLastEntry()} — removes and returns the last entry</li>
     * </ul>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Iterate over the keys and values lists simultaneously.</li>
     *   <li>For each pair, call {@code map.putFirst(key, value)} — this places the
     *       newest entry at the front.</li>
     *   <li>After all insertions, call {@code map.pollLastEntry()} to remove and
     *       return the <em>oldest</em> entry (the one inserted first, now at the back).</li>
     * </ol>
     *
     * <p><strong>Why this simulates FIFO:</strong> Since each new entry is placed at
     * the front ({@code putFirst}), the first entry inserted ends up at the back.
     * Polling from the back ({@code pollLastEntry}) retrieves the oldest entry first.</p>
     *
     * @param map    an <strong>empty</strong> {@link SequencedMap} (typically a LinkedHashMap)
     * @param keys   the list of keys to insert
     * @param values the list of integer values to insert
     * @return the entry that was removed via {@code pollLastEntry()}, or null if the map is empty
     */
    public static Map.Entry<String, Integer> buildFifoQueue(
            SequencedMap<String, Integer> map, List<String> keys, List<Integer> values) {
        // TODO: complete this method
        // --sw-wipe--
        for (int i = 0; i < keys.size(); i++) {
            map.putFirst(keys.get(i), values.get(i));
        }
        return map.pollLastEntry();
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 5 — Reversed Map View (Java 21+)</h3>
     *
     * <p>Return a <strong>reversed view</strong> of the provided {@link SequencedMap}
     * using the {@link SequencedMap#reversed()} method.</p>
     *
     * <p>The returned map is a <em>view</em> — it does not copy the data. Iterating
     * over the reversed map traverses the entries in the opposite order of the
     * original. This avoids the expensive anti-pattern of dumping keys into an
     * intermediary list just to iterate backwards.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code map.reversed()} and return the result.</li>
     * </ol>
     *
     * @param map the sequenced map to reverse
     * @return a reversed view of the map
     */
    public static SequencedMap<String, Integer> reverseMapView(SequencedMap<String, Integer> map) {
        // TODO: complete this method
        // --sw-wipe--
        return map.reversed();
        // --sw-wipe--
    }

    /**
     * <h3>Exercise 6 — Word Frequency Counter with Map.merge()</h3>
     *
     * <p>Count the frequency of each word in the given list using
     * {@link Map#merge(Object, Object, java.util.function.BiFunction)}.
     * Return a {@link HashMap} where each key is a <strong>lower-case</strong>
     * word and the value is how many times it appeared.</p>
     *
     * <p><strong>Why {@code merge()} is the idiomatic approach:</strong></p>
     * <ul>
     *   <li>Before Java 8, the classic pattern required a verbose
     *       {@code if (map.containsKey(word))} check followed by {@code get + put}.</li>
     *   <li>{@code merge(key, 1, Integer::sum)} does it in a single atomic call:
     *       <em>"put 1 if absent, otherwise add 1 to the existing value"</em>.</li>
     *   <li>Another common alternative is {@code computeIfAbsent} combined with
     *       an {@link java.util.concurrent.atomic.AtomicInteger}, but {@code merge}
     *       is simpler for primitive counting.</li>
     * </ul>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Create a new {@code HashMap<String, Integer>}.</li>
     *   <li>Iterate over the words list.</li>
     *   <li>For each word, call
     *       {@code map.merge(word.toLowerCase(), 1, Integer::sum)}.</li>
     *   <li>Return the populated map.</li>
     * </ol>
     *
     * <p><strong>Complexity:</strong> O(n) — one hash lookup per word.</p>
     *
     * @param words a list of words (may contain mixed case and duplicates)
     * @return a map of lower-case words to their occurrence count
     */
    public static HashMap<String, Integer> countWordFrequencies(List<String> words) {
        // TODO: complete this method
        // --sw-wipe--
        var map = new HashMap<String, Integer>();
        for (String word : words) {
            map.merge(word.toLowerCase(), 1, Integer::sum);
        }
        return map;
        // --sw-wipe--
    }
}

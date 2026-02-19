package ch.jobtrek.datastructures.maps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 3 — Key-Value Associations and Hash Mechanics.
 *
 * <p>These tests verify encounter order differences between HashMap, TreeMap,
 * and LinkedHashMap, as well as the SequencedMap API for FIFO and reverse operations.</p>
 */
@DisplayName("Module 3 — Maps")
class MapStructuresTest {

    // Keys deliberately NOT in alphabetical order to expose ordering differences
    private static final List<String> KEYS = List.of(
            "Mango", "Apple", "Cherry", "Banana", "Elderberry", "Date", "Fig"
    );
    private static final List<String> VALUES = List.of(
            "yellow", "green", "red", "yellow", "purple", "brown", "purple"
    );

    // ---------------------------------------------------------------
    // Exercise 1 — HashMap (no order guarantee)
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: populateHashMap()")
    class PopulateHashMapTests {

        @Test
        @DisplayName("Should return a non-null HashMap")
        void shouldReturnNonNull() {
            HashMap<String, String> map = MapStructuresExercise.populateHashMap(KEYS, VALUES);

            assertThat(map).isNotNull();
        }

        @Test
        @DisplayName("Should contain all key-value pairs")
        void shouldContainAllPairs() {
            HashMap<String, String> map = MapStructuresExercise.populateHashMap(KEYS, VALUES);

            assertThat(map).hasSize(KEYS.size());
            for (int i = 0; i < KEYS.size(); i++) {
                assertThat(map).containsEntry(KEYS.get(i), VALUES.get(i));
            }
        }

        @Test
        @DisplayName("HashMap keySet order is NOT guaranteed to match insertion order")
        void keySetOrderIsUnpredictable() {
            HashMap<String, String> map = MapStructuresExercise.populateHashMap(KEYS, VALUES);

            List<String> keySetOrder = new ArrayList<>(map.keySet());

            // HashMap uses hash-based bucketing — the iteration order depends on
            // hash codes and internal table size. With these specific keys, the
            // order will almost certainly differ from the insertion order.
            // We verify the keys are all present but do NOT expect insertion order.
            assertThat(keySetOrder).containsExactlyInAnyOrderElementsOf(KEYS);

            // Informational output for the student
            System.out.println("=== HashMap Key Order ===");
            System.out.println("  Insertion order: " + KEYS);
            System.out.println("  HashMap order:   " + keySetOrder);
            System.out.println("  → Order depends on hash codes and internal bucket layout.");
            System.out.println();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — TreeMap (sorted order)
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: populateTreeMap()")
    class PopulateTreeMapTests {

        @Test
        @DisplayName("Should return a non-null TreeMap")
        void shouldReturnNonNull() {
            TreeMap<String, String> map = MapStructuresExercise.populateTreeMap(KEYS, VALUES);

            assertThat(map).isNotNull();
        }

        @Test
        @DisplayName("Should contain all key-value pairs")
        void shouldContainAllPairs() {
            TreeMap<String, String> map = MapStructuresExercise.populateTreeMap(KEYS, VALUES);

            assertThat(map).hasSize(KEYS.size());
            for (int i = 0; i < KEYS.size(); i++) {
                assertThat(map).containsEntry(KEYS.get(i), VALUES.get(i));
            }
        }

        @Test
        @DisplayName("TreeMap keySet should be in natural alphabetical order")
        void keySetShouldBeAlphabetical() {
            TreeMap<String, String> map = MapStructuresExercise.populateTreeMap(KEYS, VALUES);

            List<String> keySetOrder = new ArrayList<>(map.keySet());
            List<String> expected = List.of(
                    "Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Mango"
            );

            assertThat(keySetOrder).containsExactlyElementsOf(expected);

            System.out.println("=== TreeMap Key Order ===");
            System.out.println("  Insertion order:  " + KEYS);
            System.out.println("  TreeMap order:    " + keySetOrder);
            System.out.println("  → Always sorted alphabetically via Red-Black Tree.");
            System.out.println();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — LinkedHashMap (insertion order)
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: populateLinkedHashMap()")
    class PopulateLinkedHashMapTests {

        @Test
        @DisplayName("Should return a non-null LinkedHashMap")
        void shouldReturnNonNull() {
            LinkedHashMap<String, String> map = MapStructuresExercise.populateLinkedHashMap(KEYS, VALUES);

            assertThat(map).isNotNull();
        }

        @Test
        @DisplayName("Should contain all key-value pairs")
        void shouldContainAllPairs() {
            LinkedHashMap<String, String> map = MapStructuresExercise.populateLinkedHashMap(KEYS, VALUES);

            assertThat(map).hasSize(KEYS.size());
            for (int i = 0; i < KEYS.size(); i++) {
                assertThat(map).containsEntry(KEYS.get(i), VALUES.get(i));
            }
        }

        @Test
        @DisplayName("LinkedHashMap keySet should exactly match insertion order")
        void keySetShouldMatchInsertionOrder() {
            LinkedHashMap<String, String> map = MapStructuresExercise.populateLinkedHashMap(KEYS, VALUES);

            List<String> keySetOrder = new ArrayList<>(map.keySet());

            assertThat(keySetOrder).containsExactlyElementsOf(KEYS);

            System.out.println("=== LinkedHashMap Key Order ===");
            System.out.println("  Insertion order:      " + KEYS);
            System.out.println("  LinkedHashMap order:  " + keySetOrder);
            System.out.println("  → Matches insertion order via internal doubly-linked list.");
            System.out.println();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — FIFO Queue with SequencedMap
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: buildFifoQueue()")
    class BuildFifoQueueTests {

        @Test
        @DisplayName("Should return the oldest entry (first inserted) via pollLastEntry")
        void shouldReturnOldestEntry() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            List<String> keys = List.of("task-A", "task-B", "task-C");
            List<Integer> values = List.of(1, 2, 3);

            Map.Entry<String, Integer> polled = MapStructuresExercise.buildFifoQueue(map, keys, values);

            // "task-A" was inserted first, so putFirst pushes it to the back
            // as subsequent putFirst calls push newer entries to the front.
            // pollLastEntry removes the entry at the back = the oldest = "task-A"
            assertThat(polled).isNotNull();
            assertThat(polled.getKey()).isEqualTo("task-A");
            assertThat(polled.getValue()).isEqualTo(1);
        }

        @Test
        @DisplayName("After polling, the map should contain remaining entries")
        void mapShouldContainRemainingEntries() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            List<String> keys = List.of("first", "second", "third");
            List<Integer> values = List.of(10, 20, 30);

            MapStructuresExercise.buildFifoQueue(map, keys, values);

            // "first" was polled, so only "second" and "third" remain
            assertThat(map).hasSize(2);
            assertThat(map).containsKey("second");
            assertThat(map).containsKey("third");
        }

        @Test
        @DisplayName("With a single entry, polling should empty the map")
        void singleEntryShouldEmptyMap() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();

            Map.Entry<String, Integer> polled = MapStructuresExercise.buildFifoQueue(
                    map, List.of("only"), List.of(42));

            assertThat(polled).isNotNull();
            assertThat(polled.getKey()).isEqualTo("only");
            assertThat(polled.getValue()).isEqualTo(42);
            assertThat(map).isEmpty();
        }

        @Test
        @DisplayName("Remaining entries should be in reverse insertion order (newest first)")
        void remainingEntriesShouldBeReverseInsertionOrder() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            List<String> keys = List.of("A", "B", "C", "D");
            List<Integer> values = List.of(1, 2, 3, 4);

            MapStructuresExercise.buildFifoQueue(map, keys, values);

            // After putFirst for A, B, C, D: order is D, C, B, A
            // After pollLastEntry removes A: order is D, C, B
            List<String> remainingKeys = new ArrayList<>(map.keySet());
            assertThat(remainingKeys).containsExactly("D", "C", "B");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 5 — Reversed Map View
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 5: reverseMapView()")
    class ReverseMapViewTests {

        @Test
        @DisplayName("Should return a non-null reversed view")
        void shouldReturnNonNull() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            map.put("A", 1);
            map.put("B", 2);

            SequencedMap<String, Integer> reversed = MapStructuresExercise.reverseMapView(map);

            assertThat(reversed).isNotNull();
        }

        @Test
        @DisplayName("Reversed view should iterate in opposite order")
        void shouldIterateInReverseOrder() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            map.put("first", 1);
            map.put("second", 2);
            map.put("third", 3);
            map.put("fourth", 4);

            SequencedMap<String, Integer> reversed = MapStructuresExercise.reverseMapView(map);

            List<String> reversedKeys = new ArrayList<>(reversed.keySet());
            assertThat(reversedKeys).containsExactly("fourth", "third", "second", "first");
        }

        @Test
        @DisplayName("Reversed view should contain the same entries")
        void shouldContainSameEntries() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            map.put("X", 10);
            map.put("Y", 20);
            map.put("Z", 30);

            SequencedMap<String, Integer> reversed = MapStructuresExercise.reverseMapView(map);

            assertThat(reversed).hasSize(3);
            assertThat(reversed).containsEntry("X", 10);
            assertThat(reversed).containsEntry("Y", 20);
            assertThat(reversed).containsEntry("Z", 30);
        }

        @Test
        @DisplayName("Reversed view is a live view — changes to original reflect in reversed")
        void shouldBeALiveView() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();
            map.put("A", 1);
            map.put("B", 2);

            SequencedMap<String, Integer> reversed = MapStructuresExercise.reverseMapView(map);

            // Modify the original map
            map.put("C", 3);

            // The reversed view should reflect the change
            assertThat(reversed).hasSize(3);
            List<String> reversedKeys = new ArrayList<>(reversed.keySet());
            assertThat(reversedKeys).containsExactly("C", "B", "A");
        }

        @Test
        @DisplayName("Reversed view of an empty map should be empty")
        void shouldHandleEmptyMap() {
            SequencedMap<String, Integer> map = new LinkedHashMap<>();

            SequencedMap<String, Integer> reversed = MapStructuresExercise.reverseMapView(map);

            assertThat(reversed).isNotNull();
            assertThat(reversed).isEmpty();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 6 — Word Frequency Counter
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 6: countWordFrequencies()")
    class CountWordFrequenciesTests {

        @Test
        @DisplayName("Should return a non-null map")
        void shouldReturnNonNull() {
            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(
                    List.of("hello"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should count word occurrences correctly")
        void shouldCountWordOccurrences() {
            List<String> words = List.of(
                    "apple", "banana", "apple", "cherry", "banana", "apple"
            );

            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(words);

            assertThat(result).hasSize(3);
            assertThat(result).containsEntry("apple", 3);
            assertThat(result).containsEntry("banana", 2);
            assertThat(result).containsEntry("cherry", 1);
        }

        @Test
        @DisplayName("Should normalize words to lower case")
        void shouldNormalizeToLowerCase() {
            List<String> words = List.of("Java", "JAVA", "java", "Python", "python");

            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(words);

            assertThat(result).hasSize(2);
            assertThat(result).containsEntry("java", 3);
            assertThat(result).containsEntry("python", 2);
        }

        @Test
        @DisplayName("Should handle a single word")
        void shouldHandleSingleWord() {
            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(
                    List.of("unique"));

            assertThat(result).hasSize(1);
            assertThat(result).containsEntry("unique", 1);
        }

        @Test
        @DisplayName("Should handle all identical words")
        void shouldHandleAllIdentical() {
            List<String> words = List.of("same", "same", "same", "same");

            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(words);

            assertThat(result).hasSize(1);
            assertThat(result).containsEntry("same", 4);
        }

        @Test
        @DisplayName("Should handle an empty list")
        void shouldHandleEmptyList() {
            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(
                    List.of());

            assertThat(result).isNotNull();
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should handle words with no duplicates")
        void shouldHandleNoDuplicates() {
            List<String> words = List.of("alpha", "beta", "gamma", "delta");

            HashMap<String, Integer> result = MapStructuresExercise.countWordFrequencies(words);

            assertThat(result).hasSize(4);
            result.values().forEach(count -> assertThat(count).isEqualTo(1));
        }
    }
}

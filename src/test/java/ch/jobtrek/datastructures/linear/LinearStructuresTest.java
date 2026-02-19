package ch.jobtrek.datastructures.linear;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 1 — Linear Data Structures.
 *
 * <p>These tests verify both correctness and — where relevant — display
 * timing information so students can observe the performance differences
 * between ArrayList and LinkedList in practice.</p>
 */
@DisplayName("Module 1 — Linear Data Structures")
class LinearStructuresTest {

    // ---------------------------------------------------------------
    // Exercise 1 — List Population
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: populateLists()")
    class PopulateListsTests {

        @Test
        @DisplayName("Both lists should contain the correct number of elements")
        void shouldContainCorrectSize() {
            ListPair pair = LinearStructuresExercise.populateLists(1_000);

            assertThat(pair).isNotNull();
            assertThat(pair.arrayList()).hasSize(1_000);
            assertThat(pair.linkedList()).hasSize(1_000);
        }

        @Test
        @DisplayName("Both lists should contain sequential integers from 0 to size-1")
        void shouldContainSequentialIntegers() {
            ListPair pair = LinearStructuresExercise.populateLists(100);

            for (int i = 0; i < 100; i++) {
                assertThat(pair.arrayList().get(i)).isEqualTo(i);
                assertThat(pair.linkedList().get(i)).isEqualTo(i);
            }
        }

        @Test
        @DisplayName("Both lists should contain the same elements in the same order")
        void bothListsShouldBeIdentical() {
            ListPair pair = LinearStructuresExercise.populateLists(500);

            assertThat(pair.arrayList()).containsExactlyElementsOf(pair.linkedList());
        }

        @Test
        @DisplayName("populateLists(1) should create lists with a single element 0")
        void shouldHandleSingleElement() {
            ListPair pair = LinearStructuresExercise.populateLists(1);

            assertThat(pair.arrayList()).containsExactly(0);
            assertThat(pair.linkedList()).containsExactly(0);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Middle Insertion
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: insertAtMiddle()")
    class InsertAtMiddleTests {

        @Test
        @DisplayName("Should insert the value at the middle index of an ArrayList")
        void shouldInsertAtMiddleOfArrayList() {
            ArrayList<Integer> list = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));

            LinearStructuresExercise.insertAtMiddle(list, 99);

            assertThat(list).hasSize(7);
            assertThat(list.get(3)).isEqualTo(99);
            // Original elements shifted: [0, 1, 2, 99, 3, 4, 5]
            assertThat(list).containsExactly(0, 1, 2, 99, 3, 4, 5);
        }

        @Test
        @DisplayName("Should insert the value at the middle index of a LinkedList")
        void shouldInsertAtMiddleOfLinkedList() {
            LinkedList<Integer> list = new LinkedList<>(List.of(0, 1, 2, 3, 4, 5));

            LinearStructuresExercise.insertAtMiddle(list, 99);

            assertThat(list).hasSize(7);
            assertThat(list.get(3)).isEqualTo(99);
            assertThat(list).containsExactly(0, 1, 2, 99, 3, 4, 5);
        }

        @Test
        @DisplayName("Should insert at index 0 when list has a single element")
        void shouldHandleSingleElementList() {
            ArrayList<Integer> list = new ArrayList<>(List.of(42));

            LinearStructuresExercise.insertAtMiddle(list, 99);

            // size/2 = 0, so 99 goes at index 0
            assertThat(list).containsExactly(99, 42);
        }

        @Test
        @DisplayName("Performance comparison: middle insertion on large lists (informational)")
        void performanceComparisonMiddleInsertion() {
            int size = 100_000;
            int insertions = 1_000;

            // Populate lists
            ListPair pair = LinearStructuresExercise.populateLists(size);
            ArrayList<Integer> arrayList = pair.arrayList();
            LinkedList<Integer> linkedList = pair.linkedList();

            // Time ArrayList middle insertions
            long startAL = System.nanoTime();
            for (int i = 0; i < insertions; i++) {
                LinearStructuresExercise.insertAtMiddle(arrayList, i);
            }
            long durationAL = System.nanoTime() - startAL;

            // Time LinkedList middle insertions
            long startLL = System.nanoTime();
            for (int i = 0; i < insertions; i++) {
                LinearStructuresExercise.insertAtMiddle(linkedList, i);
            }
            long durationLL = System.nanoTime() - startLL;

            System.out.println("=== Middle Insertion Performance (" + insertions + " insertions on " + size + " elements) ===");
            System.out.printf("  ArrayList:  %,d ns  (%.2f ms)%n", durationAL, durationAL / 1_000_000.0);
            System.out.printf("  LinkedList: %,d ns  (%.2f ms)%n", durationLL, durationLL / 1_000_000.0);
            System.out.printf("  Ratio (LinkedList / ArrayList): %.1fx%n", (double) durationLL / durationAL);
            System.out.println("  → Despite O(n) for both, ArrayList benefits from cache-friendly System.arraycopy()");
            System.out.println("    while LinkedList suffers from pointer chasing and cache misses.");
            System.out.println();

            // Both lists should have grown by the number of insertions
            assertThat(arrayList).hasSize(size + insertions);
            assertThat(linkedList).hasSize(size + insertions);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Head Insertion
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: insertAtHead()")
    class InsertAtHeadTests {

        @Test
        @DisplayName("Should insert the value at index 0 of an ArrayList")
        void shouldInsertAtHeadOfArrayList() {
            ArrayList<Integer> list = new ArrayList<>(List.of(1, 2, 3));

            LinearStructuresExercise.insertAtHead(list, 99);

            assertThat(list).containsExactly(99, 1, 2, 3);
        }

        @Test
        @DisplayName("Should insert the value at index 0 of a LinkedList")
        void shouldInsertAtHeadOfLinkedList() {
            LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3));

            LinearStructuresExercise.insertAtHead(list, 99);

            assertThat(list).containsExactly(99, 1, 2, 3);
        }

        @Test
        @DisplayName("Multiple head insertions should prepend in reverse order")
        void multipleHeadInsertions() {
            ArrayList<Integer> list = new ArrayList<>();

            LinearStructuresExercise.insertAtHead(list, 1);
            LinearStructuresExercise.insertAtHead(list, 2);
            LinearStructuresExercise.insertAtHead(list, 3);

            assertThat(list).containsExactly(3, 2, 1);
        }

        @Test
        @DisplayName("Performance comparison: head insertion — LinkedList advantage (informational)")
        void performanceComparisonHeadInsertion() {
            int size = 100_000;
            int insertions = 10_000;

            ListPair pair = LinearStructuresExercise.populateLists(size);
            ArrayList<Integer> arrayList = pair.arrayList();
            LinkedList<Integer> linkedList = pair.linkedList();

            // Time ArrayList head insertions
            long startAL = System.nanoTime();
            for (int i = 0; i < insertions; i++) {
                LinearStructuresExercise.insertAtHead(arrayList, i);
            }
            long durationAL = System.nanoTime() - startAL;

            // Time LinkedList head insertions
            long startLL = System.nanoTime();
            for (int i = 0; i < insertions; i++) {
                LinearStructuresExercise.insertAtHead(linkedList, i);
            }
            long durationLL = System.nanoTime() - startLL;

            System.out.println("=== Head Insertion Performance (" + insertions + " insertions on " + size + " elements) ===");
            System.out.printf("  ArrayList:  %,d ns  (%.2f ms)%n", durationAL, durationAL / 1_000_000.0);
            System.out.printf("  LinkedList: %,d ns  (%.2f ms)%n", durationLL, durationLL / 1_000_000.0);
            System.out.printf("  Ratio (ArrayList / LinkedList): %.1fx%n", (double) durationAL / durationLL);
            System.out.println("  → This is the ONE scenario where LinkedList wins: O(1) head insertion");
            System.out.println("    vs O(n) array shift for ArrayList.");
            System.out.println();

            assertThat(arrayList).hasSize(size + insertions);
            assertThat(linkedList).hasSize(size + insertions);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Memory Optimization
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: optimizeMemory()")
    class OptimizeMemoryTests {

        @Test
        @DisplayName("Should return the same list instance after trimming")
        void shouldReturnSameInstance() {
            ArrayList<Integer> list = new ArrayList<>(1000);
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }

            ArrayList<Integer> result = LinearStructuresExercise.optimizeMemory(list);

            assertThat(result).isSameAs(list);
        }

        @Test
        @DisplayName("Should preserve all elements after trimming")
        void shouldPreserveAllElements() {
            ArrayList<Integer> list = new ArrayList<>(500);
            for (int i = 0; i < 50; i++) {
                list.add(i);
            }

            ArrayList<Integer> result = LinearStructuresExercise.optimizeMemory(list);

            assertThat(result).hasSize(50);
            for (int i = 0; i < 50; i++) {
                assertThat(result.get(i)).isEqualTo(i);
            }
        }

        @Test
        @DisplayName("Should work on an empty list without error")
        void shouldHandleEmptyList() {
            ArrayList<Integer> list = new ArrayList<>(100);

            ArrayList<Integer> result = LinearStructuresExercise.optimizeMemory(list);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("trimToSize does not affect logical content — only internal capacity")
        void trimDoesNotAffectContent() {
            // Build a list with excess capacity
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 1_000; i++) {
                list.add(i);
            }
            // Remove half the elements — the backing array retains its large capacity
            list.subList(500, 1_000).clear();

            ArrayList<Integer> result = LinearStructuresExercise.optimizeMemory(list);

            assertThat(result).hasSize(500);
            assertThat(result.get(0)).isEqualTo(0);
            assertThat(result.get(499)).isEqualTo(499);
        }
    }
}

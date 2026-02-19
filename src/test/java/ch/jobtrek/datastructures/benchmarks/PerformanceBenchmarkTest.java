package ch.jobtrek.datastructures.benchmarks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 6 — Performance Benchmarks.
 *
 * <p>Each nested class contains <strong>correctness tests</strong> (to verify
 * the student's implementation) and one <strong>benchmark test</strong> that
 * measures and prints the performance difference between data structures.</p>
 *
 * <p>Read the console output after running the tests — the comparison tables
 * are the core learning material of this module.</p>
 */
@DisplayName("Module 6 — Performance Benchmarks")
class PerformanceBenchmarkTest {

    // ---------------------------------------------------------------
    // Exercise 1 — Sequential Append
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: appendElements()")
    class AppendElementsTests {

        @Test
        @DisplayName("Should append the correct number of elements to an ArrayList")
        void shouldAppendToArrayList() {
            var list = new ArrayList<Integer>();

            PerformanceBenchmarkExercise.appendElements(list, 1_000);

            assertThat(list).hasSize(1_000);
            assertThat(list.getFirst()).isEqualTo(0);
            assertThat(list.getLast()).isEqualTo(999);
        }

        @Test
        @DisplayName("Should append the correct number of elements to a LinkedList")
        void shouldAppendToLinkedList() {
            var list = new LinkedList<Integer>();

            PerformanceBenchmarkExercise.appendElements(list, 1_000);

            assertThat(list).hasSize(1_000);
            assertThat(list.getFirst()).isEqualTo(0);
            assertThat(list.getLast()).isEqualTo(999);
        }

        @Test
        @DisplayName("Should handle count = 0 without error")
        void shouldHandleZeroCount() {
            var list = new ArrayList<Integer>();

            PerformanceBenchmarkExercise.appendElements(list, 0);

            assertThat(list).isEmpty();
        }

        @Test
        @DisplayName("Benchmark: ArrayList vs LinkedList — 10,000,000 appends")
        void benchmarkAppend() {
            int count = 10_000_000;

            // -- ArrayList --
            var arrayList = new ArrayList<Integer>();
            long startAL = System.nanoTime();
            PerformanceBenchmarkExercise.appendElements(arrayList, count);
            long durationAL = System.nanoTime() - startAL;

            // -- LinkedList --
            var linkedList = new LinkedList<Integer>();
            long startLL = System.nanoTime();
            PerformanceBenchmarkExercise.appendElements(linkedList, count);
            long durationLL = System.nanoTime() - startLL;

            // Print results
            System.out.println("=== Benchmark 1: Sequential Append (" + String.format("%,d", count) + " elements) ===");
            System.out.printf("  ArrayList:  %,15d ns  (%6.2f ms)%n", durationAL, durationAL / 1_000_000.0);
            System.out.printf("  LinkedList: %,15d ns  (%6.2f ms)%n", durationLL, durationLL / 1_000_000.0);
            System.out.printf("  Ratio (LinkedList / ArrayList): %.1fx%n", (double) durationLL / durationAL);
            System.out.println("  → Both are amortized O(1) per append, yet ArrayList wins because it");
            System.out.println("    writes into a contiguous array (cache-friendly, no per-element object");
            System.out.println("    allocation), while LinkedList creates a new Node object for every element.");
            System.out.println();

            // Correctness assertions
            assertThat(arrayList).hasSize(count);
            assertThat(linkedList).hasSize(count);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Indexed Access (get(i))
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: sumByIndex()")
    class SumByIndexTests {

        @Test
        @DisplayName("Should compute the correct sum for a small ArrayList")
        void shouldComputeSumForArrayList() {
            var list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

            long sum = PerformanceBenchmarkExercise.sumByIndex(list);

            assertThat(sum).isEqualTo(15);
        }

        @Test
        @DisplayName("Should compute the correct sum for a small LinkedList")
        void shouldComputeSumForLinkedList() {
            var list = new LinkedList<>(List.of(10, 20, 30));

            long sum = PerformanceBenchmarkExercise.sumByIndex(list);

            assertThat(sum).isEqualTo(60);
        }

        @Test
        @DisplayName("Should return 0 for an empty list")
        void shouldReturnZeroForEmpty() {
            long sum = PerformanceBenchmarkExercise.sumByIndex(new ArrayList<>());

            assertThat(sum).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return the element value for a single-element list")
        void shouldHandleSingleElement() {
            long sum = PerformanceBenchmarkExercise.sumByIndex(List.of(42));

            assertThat(sum).isEqualTo(42);
        }

        @Test
        @DisplayName("Benchmark: ArrayList vs LinkedList — indexed access on 50,000 elements")
        void benchmarkIndexedAccess() {
            int size = 50_000;

            // Build both lists with identical data
            var arrayList = new ArrayList<Integer>(size);
            var linkedList = new LinkedList<Integer>();
            for (int i = 0; i < size; i++) {
                arrayList.add(i);
                linkedList.add(i);
            }

            // -- ArrayList --
            long startAL = System.nanoTime();
            long sumAL = PerformanceBenchmarkExercise.sumByIndex(arrayList);
            long durationAL = System.nanoTime() - startAL;

            // -- LinkedList --
            long startLL = System.nanoTime();
            long sumLL = PerformanceBenchmarkExercise.sumByIndex(linkedList);
            long durationLL = System.nanoTime() - startLL;

            // Print results
            System.out.println("=== Benchmark 2: Indexed Access — get(i) (" + String.format("%,d", size) + " elements) ===");
            System.out.printf("  ArrayList:  %,15d ns  (%6.2f ms)%n", durationAL, durationAL / 1_000_000.0);
            System.out.printf("  LinkedList: %,15d ns  (%6.2f ms)%n", durationLL, durationLL / 1_000_000.0);
            System.out.printf("  Ratio (LinkedList / ArrayList): %.0fx%n", (double) durationLL / durationAL);
            System.out.println("  → ArrayList.get(i) is O(1) — a direct array offset calculation.");
            System.out.println("    LinkedList.get(i) is O(n) — it traverses i nodes from the head/tail.");
            System.out.println("    With " + String.format("%,d", size) + " elements, the LinkedList loop is O(n²) total.");
            System.out.println("    NEVER use index-based iteration on a LinkedList — use an Iterator instead.");
            System.out.println();

            // Both sums must be identical
            long expectedSum = (long) size * (size - 1) / 2;
            assertThat(sumAL).isEqualTo(expectedSum);
            assertThat(sumLL).isEqualTo(expectedSum);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Membership Test (contains())
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: countHits()")
    class CountHitsTests {

        @Test
        @DisplayName("Should count all hits when every query matches")
        void shouldCountAllHitsWhenAllMatch() {
            var data = new HashSet<>(List.of(1, 2, 3, 4, 5));
            var queries = List.of(1, 2, 3, 4, 5);

            int hits = PerformanceBenchmarkExercise.countHits(data, queries);

            assertThat(hits).isEqualTo(5);
        }

        @Test
        @DisplayName("Should return 0 when no query matches")
        void shouldReturnZeroWhenNoMatch() {
            var data = new HashSet<>(List.of(1, 2, 3));
            var queries = List.of(10, 20, 30);

            int hits = PerformanceBenchmarkExercise.countHits(data, queries);

            assertThat(hits).isEqualTo(0);
        }

        @Test
        @DisplayName("Should count partial matches correctly")
        void shouldCountPartialMatches() {
            var data = new TreeSet<>(List.of(10, 20, 30, 40, 50));
            var queries = List.of(10, 15, 20, 25, 30);

            int hits = PerformanceBenchmarkExercise.countHits(data, queries);

            assertThat(hits).isEqualTo(3); // 10, 20, 30
        }

        @Test
        @DisplayName("Should work with an ArrayList as the collection")
        void shouldWorkWithArrayList() {
            var data = new ArrayList<>(List.of(5, 10, 15));
            var queries = List.of(5, 7, 10, 12, 15);

            int hits = PerformanceBenchmarkExercise.countHits(data, queries);

            assertThat(hits).isEqualTo(3);
        }

        @Test
        @DisplayName("Should handle empty queries list")
        void shouldHandleEmptyQueries() {
            var data = new HashSet<>(List.of(1, 2, 3));

            int hits = PerformanceBenchmarkExercise.countHits(data, List.of());

            assertThat(hits).isEqualTo(0);
        }

        @Test
        @DisplayName("Benchmark: ArrayList vs HashSet vs TreeSet — 10,000 lookups in 100,000 elements")
        void benchmarkContains() {
            int dataSize = 100_000;
            int queryCount = 10_000;

            // Build the data set: integers 0..dataSize-1
            var arrayList = new ArrayList<Integer>(dataSize);
            for (int i = 0; i < dataSize; i++) {
                arrayList.add(i);
            }
            var hashSet = new HashSet<>(arrayList);
            var treeSet = new TreeSet<>(arrayList);

            // Build queries: mix of hits and misses
            // Even numbers 0..19998 → half will be in range, half may exceed dataSize
            var queries = new ArrayList<Integer>(queryCount);
            for (int i = 0; i < queryCount; i++) {
                // Query values spread across 0..2*dataSize to get ~50% hit rate
                queries.add(i * 2);
            }

            // -- ArrayList --
            long startAL = System.nanoTime();
            int hitsAL = PerformanceBenchmarkExercise.countHits(arrayList, queries);
            long durationAL = System.nanoTime() - startAL;

            // -- HashSet --
            long startHS = System.nanoTime();
            int hitsHS = PerformanceBenchmarkExercise.countHits(hashSet, queries);
            long durationHS = System.nanoTime() - startHS;

            // -- TreeSet --
            long startTS = System.nanoTime();
            int hitsTS = PerformanceBenchmarkExercise.countHits(treeSet, queries);
            long durationTS = System.nanoTime() - startTS;

            // Print results
            System.out.println("=== Benchmark 3: Membership Test — contains() ===");
            System.out.println("  Data size: " + String.format("%,d", dataSize)
                    + " elements | Queries: " + String.format("%,d", queryCount));
            System.out.printf("  ArrayList:  %,15d ns  (%6.2f ms)  — O(n) per lookup%n",
                    durationAL, durationAL / 1_000_000.0);
            System.out.printf("  HashSet:    %,15d ns  (%6.2f ms)  — O(1) per lookup%n",
                    durationHS, durationHS / 1_000_000.0);
            System.out.printf("  TreeSet:    %,15d ns  (%6.2f ms)  — O(log n) per lookup%n",
                    durationTS, durationTS / 1_000_000.0);
            System.out.printf("  Ratio (ArrayList / HashSet):  %,.0fx%n", (double) durationAL / durationHS);
            System.out.printf("  Ratio (ArrayList / TreeSet):  %,.0fx%n", (double) durationAL / durationTS);
            System.out.printf("  Ratio (TreeSet   / HashSet):  %.1fx%n", (double) durationTS / durationHS);
            System.out.println("  → For search-heavy workloads, always prefer a Set over a List.");
            System.out.println("    HashSet is fastest (O(1) hash lookup), TreeSet is fast (O(log n)),");
            System.out.println("    ArrayList is disastrously slow (O(n) linear scan per query).");
            System.out.println();

            // All three must find the same number of hits
            assertThat(hitsAL).isEqualTo(hitsHS);
            assertThat(hitsAL).isEqualTo(hitsTS);

            // Sanity check: we should have found some hits
            assertThat(hitsAL).isGreaterThan(0);
        }
    }
}

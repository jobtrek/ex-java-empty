package ch.jobtrek.datastructures.sets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 4 — Set Semantics and Uniqueness Guarantees.
 *
 * <p>These tests verify deduplication, mathematical set operations (intersection,
 * union), and O(log n) proximity search via NavigableSet.</p>
 */
@DisplayName("Module 4 — Sets")
class SetStructuresTest {

    // ---------------------------------------------------------------
    // Exercise 1 — Mass Deduplication
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: deduplicateWithHashSet()")
    class DeduplicateTests {

        @Test
        @DisplayName("Should return a non-null HashSet")
        void shouldReturnNonNull() {
            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(
                    List.of("a", "b", "a"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should remove all duplicates and keep only unique elements")
        void shouldDeduplicateStrings() {
            List<String> input = List.of(
                    "apple", "banana", "apple", "cherry", "banana",
                    "apple", "date", "cherry", "banana", "apple"
            );

            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(input);

            assertThat(result).hasSize(4);
            assertThat(result).containsExactlyInAnyOrder("apple", "banana", "cherry", "date");
        }

        @Test
        @DisplayName("Should handle a list with no duplicates")
        void shouldHandleNoDuplicates() {
            List<String> input = List.of("x", "y", "z");

            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(input);

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder("x", "y", "z");
        }

        @Test
        @DisplayName("Should handle a list where all elements are identical")
        void shouldHandleAllDuplicates() {
            List<String> input = List.of("same", "same", "same", "same", "same");

            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(input);

            assertThat(result).hasSize(1);
            assertThat(result).containsExactly("same");
        }

        @Test
        @DisplayName("Should handle large-scale deduplication efficiently")
        void shouldHandleLargeScale() {
            // Build a list with 100,000 elements but only 100 unique values
            List<String> input = new ArrayList<>();
            for (int i = 0; i < 100_000; i++) {
                input.add("item-" + (i % 100));
            }

            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(input);

            assertThat(result).hasSize(100);
        }

        @Test
        @DisplayName("Should handle a single-element list")
        void shouldHandleSingleElement() {
            HashSet<String> result = SetStructuresExercise.deduplicateWithHashSet(
                    List.of("only"));

            assertThat(result).hasSize(1);
            assertThat(result).containsExactly("only");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Set Intersection
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: computeIntersection()")
    class IntersectionTests {

        @Test
        @DisplayName("Should return a non-null set")
        void shouldReturnNonNull() {
            Set<String> result = SetStructuresExercise.computeIntersection(
                    Set.of("a"), Set.of("a"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should return only elements present in both sets")
        void shouldReturnCommonElements() {
            Set<String> setA = Set.of("Alice", "Bob", "Charlie", "Diana");
            Set<String> setB = Set.of("Bob", "Diana", "Eve", "Frank");

            Set<String> result = SetStructuresExercise.computeIntersection(setA, setB);

            assertThat(result).containsExactlyInAnyOrder("Bob", "Diana");
        }

        @Test
        @DisplayName("Should return an empty set when there is no overlap")
        void shouldReturnEmptyForDisjointSets() {
            Set<String> setA = Set.of("A", "B", "C");
            Set<String> setB = Set.of("X", "Y", "Z");

            Set<String> result = SetStructuresExercise.computeIntersection(setA, setB);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Intersection of a set with itself should return a copy of that set")
        void intersectionWithSelfShouldReturnCopy() {
            Set<String> setA = Set.of("one", "two", "three");

            Set<String> result = SetStructuresExercise.computeIntersection(setA, setA);

            assertThat(result).containsExactlyInAnyOrderElementsOf(setA);
        }

        @Test
        @DisplayName("Should not modify the original input sets")
        void shouldNotModifyInputSets() {
            Set<String> setA = new HashSet<>(Set.of("A", "B", "C"));
            Set<String> setB = new HashSet<>(Set.of("B", "C", "D"));
            int sizeA = setA.size();
            int sizeB = setB.size();

            SetStructuresExercise.computeIntersection(setA, setB);

            assertThat(setA).hasSize(sizeA);
            assertThat(setB).hasSize(sizeB);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Set Union
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: computeUnion()")
    class UnionTests {

        @Test
        @DisplayName("Should return a non-null set")
        void shouldReturnNonNull() {
            Set<String> result = SetStructuresExercise.computeUnion(
                    Set.of("a"), Set.of("b"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should return all elements from both sets without duplicates")
        void shouldReturnAllUniqueElements() {
            Set<String> setA = Set.of("Alice", "Bob", "Charlie");
            Set<String> setB = Set.of("Bob", "Diana", "Eve");

            Set<String> result = SetStructuresExercise.computeUnion(setA, setB);

            assertThat(result).containsExactlyInAnyOrder(
                    "Alice", "Bob", "Charlie", "Diana", "Eve");
        }

        @Test
        @DisplayName("Union of disjoint sets should contain all elements from both")
        void unionOfDisjointSets() {
            Set<String> setA = Set.of("A", "B");
            Set<String> setB = Set.of("X", "Y");

            Set<String> result = SetStructuresExercise.computeUnion(setA, setB);

            assertThat(result).hasSize(4);
            assertThat(result).containsExactlyInAnyOrder("A", "B", "X", "Y");
        }

        @Test
        @DisplayName("Union of identical sets should have the same size")
        void unionOfIdenticalSets() {
            Set<String> setA = Set.of("one", "two", "three");

            Set<String> result = SetStructuresExercise.computeUnion(setA, setA);

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrderElementsOf(setA);
        }

        @Test
        @DisplayName("Should not modify the original input sets")
        void shouldNotModifyInputSets() {
            Set<String> setA = new HashSet<>(Set.of("A", "B"));
            Set<String> setB = new HashSet<>(Set.of("C", "D"));
            int sizeA = setA.size();
            int sizeB = setB.size();

            SetStructuresExercise.computeUnion(setA, setB);

            assertThat(setA).hasSize(sizeA);
            assertThat(setB).hasSize(sizeB);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3b — Set Difference
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3b: computeDifference()")
    class DifferenceTests {

        @Test
        @DisplayName("Should return a non-null set")
        void shouldReturnNonNull() {
            Set<String> result = SetStructuresExercise.computeDifference(
                    Set.of("a", "b"), Set.of("b"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should return elements in setA that are not in setB")
        void shouldReturnDifference() {
            Set<String> setA = Set.of("Alice", "Bob", "Charlie", "Diana");
            Set<String> setB = Set.of("Bob", "Diana", "Eve", "Frank");

            Set<String> result = SetStructuresExercise.computeDifference(setA, setB);

            assertThat(result).containsExactlyInAnyOrder("Alice", "Charlie");
        }

        @Test
        @DisplayName("Difference is not commutative: A\\B != B\\A")
        void shouldNotBeCommutative() {
            Set<String> setA = Set.of("A", "B", "C");
            Set<String> setB = Set.of("B", "C", "D");

            Set<String> aMinusB = SetStructuresExercise.computeDifference(setA, setB);
            Set<String> bMinusA = SetStructuresExercise.computeDifference(setB, setA);

            assertThat(aMinusB).containsExactly("A");
            assertThat(bMinusA).containsExactly("D");
            assertThat(aMinusB).isNotEqualTo(bMinusA);
        }

        @Test
        @DisplayName("Difference of disjoint sets should return a copy of setA")
        void differenceOfDisjointSets() {
            Set<String> setA = Set.of("A", "B");
            Set<String> setB = Set.of("X", "Y");

            Set<String> result = SetStructuresExercise.computeDifference(setA, setB);

            assertThat(result).containsExactlyInAnyOrderElementsOf(setA);
        }

        @Test
        @DisplayName("Difference of a set with itself should be empty")
        void differenceWithSelfShouldBeEmpty() {
            Set<String> setA = Set.of("one", "two", "three");

            Set<String> result = SetStructuresExercise.computeDifference(setA, setA);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Difference when setB is a superset should be empty")
        void differenceWhenSubsetShouldBeEmpty() {
            Set<String> setA = Set.of("A", "B");
            Set<String> setB = Set.of("A", "B", "C", "D");

            Set<String> result = SetStructuresExercise.computeDifference(setA, setB);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should not modify the original input sets")
        void shouldNotModifyInputSets() {
            Set<String> setA = new HashSet<>(Set.of("A", "B", "C"));
            Set<String> setB = new HashSet<>(Set.of("B", "C", "D"));
            int sizeA = setA.size();
            int sizeB = setB.size();

            SetStructuresExercise.computeDifference(setA, setB);

            assertThat(setA).hasSize(sizeA);
            assertThat(setB).hasSize(sizeB);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Proximity Search with TreeSet
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: findClosest()")
    class FindClosestTests {

        private TreeSet<Double> sampleCoordinates() {
            TreeSet<Double> coords = new TreeSet<>();
            coords.add(10.0);
            coords.add(25.5);
            coords.add(42.0);
            coords.add(67.3);
            coords.add(89.9);
            return coords;
        }

        @Test
        @DisplayName("Should return a non-null result")
        void shouldReturnNonNull() {
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 40.0);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should find exact match when target is in the set")
        void shouldFindExactMatch() {
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 42.0);

            assertThat(result).isEqualTo(42.0);
        }

        @Test
        @DisplayName("Should find closest when target is between two values (closer to upper)")
        void shouldFindClosestToUpper() {
            // Target 41.0: floor=25.5 (diff=15.5), ceiling=42.0 (diff=1.0) → 42.0
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 41.0);

            assertThat(result).isEqualTo(42.0);
        }

        @Test
        @DisplayName("Should find closest when target is between two values (closer to lower)")
        void shouldFindClosestToLower() {
            // Target 27.0: floor=25.5 (diff=1.5), ceiling=42.0 (diff=15.0) → 25.5
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 27.0);

            assertThat(result).isEqualTo(25.5);
        }

        @Test
        @DisplayName("Should return lowest value when target is below the set range")
        void shouldHandleTargetBelowRange() {
            // Target 1.0: floor=null, ceiling=10.0 → 10.0
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 1.0);

            assertThat(result).isEqualTo(10.0);
        }

        @Test
        @DisplayName("Should return highest value when target is above the set range")
        void shouldHandleTargetAboveRange() {
            // Target 100.0: floor=89.9, ceiling=null → 89.9
            Double result = SetStructuresExercise.findClosest(sampleCoordinates(), 100.0);

            assertThat(result).isEqualTo(89.9);
        }

        @Test
        @DisplayName("When equidistant, should return the floor (lower value)")
        void shouldReturnFloorWhenEquidistant() {
            TreeSet<Double> coords = new TreeSet<>();
            coords.add(10.0);
            coords.add(20.0);
            // Target 15.0: floor=10.0 (diff=5.0), ceiling=20.0 (diff=5.0) → floor wins
            Double result = SetStructuresExercise.findClosest(coords, 15.0);

            assertThat(result).isEqualTo(10.0);
        }

        @Test
        @DisplayName("Should work with a single-element set")
        void shouldHandleSingleElement() {
            TreeSet<Double> coords = new TreeSet<>();
            coords.add(50.0);

            Double result = SetStructuresExercise.findClosest(coords, 99.9);

            assertThat(result).isEqualTo(50.0);
        }

        @Test
        @DisplayName("Should handle negative coordinates")
        void shouldHandleNegativeCoordinates() {
            TreeSet<Double> coords = new TreeSet<>();
            coords.add(-20.0);
            coords.add(-5.0);
            coords.add(10.0);
            coords.add(30.0);

            // Target -3.0: floor=-5.0 (diff=2.0), ceiling=10.0 (diff=13.0) → -5.0
            Double result = SetStructuresExercise.findClosest(coords, -3.0);

            assertThat(result).isEqualTo(-5.0);
        }
    }
}
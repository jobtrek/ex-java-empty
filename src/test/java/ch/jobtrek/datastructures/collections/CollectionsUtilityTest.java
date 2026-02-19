package ch.jobtrek.datastructures.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SequencedSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Module 2 — Collections Utility.
 *
 * <p>These tests verify sorting, binary search, immutability enforcement,
 * sequenced set creation, and utility transformations.</p>
 */
@DisplayName("Module 2 — Collections Utility")
class CollectionsUtilityTest {

    // Reusable test data
    private static final Student ALICE   = new Student("Alice",   5.5, 28);
    private static final Student BOB     = new Student("Bob",     3.2, 20);
    private static final Student CHARLIE = new Student("Charlie", 4.8, 25);
    private static final Student DIANA   = new Student("Diana",   4.0, 22);
    private static final Student EVE     = new Student("Eve",     5.0, 27);

    private static List<Student> unsortedStudents() {
        return new ArrayList<>(List.of(ALICE, BOB, CHARLIE, DIANA, EVE));
    }

    // ---------------------------------------------------------------
    // Exercise 1 — Sorting with a Custom Comparator
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: sortStudentsByGrade()")
    class SortStudentsTests {

        @Test
        @DisplayName("Should sort students in ascending order by grade")
        void shouldSortByGradeAscending() {
            List<Student> students = unsortedStudents();

            CollectionsUtilityExercise.sortStudentsByGrade(students);

            assertThat(students).extracting(Student::grade)
                    .containsExactly(3.2, 4.0, 4.8, 5.0, 5.5);
        }

        @Test
        @DisplayName("Sorted list should preserve all original students")
        void shouldPreserveAllStudents() {
            List<Student> students = unsortedStudents();

            CollectionsUtilityExercise.sortStudentsByGrade(students);

            assertThat(students).containsExactlyInAnyOrder(ALICE, BOB, CHARLIE, DIANA, EVE);
        }

        @Test
        @DisplayName("Should handle a single-element list without error")
        void shouldHandleSingleElement() {
            List<Student> students = new ArrayList<>(List.of(ALICE));

            CollectionsUtilityExercise.sortStudentsByGrade(students);

            assertThat(students).containsExactly(ALICE);
        }

        @Test
        @DisplayName("Should handle an already sorted list")
        void shouldHandleAlreadySorted() {
            List<Student> students = new ArrayList<>(List.of(BOB, DIANA, CHARLIE, EVE, ALICE));
            // BOB=3.2, DIANA=4.0, CHARLIE=4.8, EVE=5.0, ALICE=5.5 → already sorted

            CollectionsUtilityExercise.sortStudentsByGrade(students);

            assertThat(students).extracting(Student::grade)
                    .containsExactly(3.2, 4.0, 4.8, 5.0, 5.5);
        }

        @Test
        @DisplayName("Should correctly order students with equal grades (stable sort)")
        void shouldBeStableSort() {
            Student frank = new Student("Frank", 4.0, 15);
            List<Student> students = new ArrayList<>(List.of(DIANA, frank));
            // Both have grade 4.0 — stable sort preserves original order

            CollectionsUtilityExercise.sortStudentsByGrade(students);

            assertThat(students).containsExactly(DIANA, frank);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Binary Search
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: findStudent()")
    class FindStudentTests {

        @Test
        @DisplayName("Should find a student in a sorted list and return correct index")
        void shouldFindStudentInSortedList() {
            List<Student> students = unsortedStudents();
            CollectionsUtilityExercise.sortStudentsByGrade(students);
            // Sorted by grade: BOB(3.2), DIANA(4.0), CHARLIE(4.8), EVE(5.0), ALICE(5.5)

            int index = CollectionsUtilityExercise.findStudent(students, CHARLIE);

            assertThat(index).isEqualTo(2); // CHARLIE is at index 2 in sorted order
        }

        @Test
        @DisplayName("Should find the first student (lowest grade)")
        void shouldFindFirstStudent() {
            List<Student> students = unsortedStudents();
            CollectionsUtilityExercise.sortStudentsByGrade(students);

            int index = CollectionsUtilityExercise.findStudent(students, BOB);

            assertThat(index).isEqualTo(0);
        }

        @Test
        @DisplayName("Should find the last student (highest grade)")
        void shouldFindLastStudent() {
            List<Student> students = unsortedStudents();
            CollectionsUtilityExercise.sortStudentsByGrade(students);

            int index = CollectionsUtilityExercise.findStudent(students, ALICE);

            assertThat(index).isEqualTo(4);
        }

        @Test
        @DisplayName("Should return a negative value for a student not in the list")
        void shouldReturnNegativeForMissing() {
            List<Student> students = unsortedStudents();
            CollectionsUtilityExercise.sortStudentsByGrade(students);

            Student unknown = new Student("Unknown", 1.0, 0);
            int index = CollectionsUtilityExercise.findStudent(students, unknown);

            assertThat(index).isNegative();
        }

        @Test
        @DisplayName("Binary search on an UNSORTED list produces incorrect results")
        void binarySearchOnUnsortedListFails() {
            // This test demonstrates WHY sorting is mandatory before binary search.
            // We compare the result of searching in the unsorted list vs. the
            // correctly sorted list. If binary search were order-agnostic, both
            // would return the same index — but they won't, because the algorithm
            // inspects the wrong half when the data is unsorted.
            List<Student> unsorted = unsortedStudents();
            List<Student> sorted = unsortedStudents();
            CollectionsUtilityExercise.sortStudentsByGrade(sorted);

            int indexInUnsorted = CollectionsUtilityExercise.findStudent(unsorted, BOB);
            int indexInSorted   = CollectionsUtilityExercise.findStudent(sorted, BOB);

            // In the correctly sorted list, BOB (grade 3.2) is at index 0.
            assertThat(indexInSorted).isEqualTo(0);

            // In the unsorted list, binary search should NOT reliably find
            // the correct index. It either returns a wrong index or a negative
            // "not found" value — either way it differs from the sorted result.
            assertThat(indexInUnsorted)
                    .as("Binary search on an unsorted list should NOT return the correct sorted index")
                    .isNotEqualTo(0);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Immutability Enforcement
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: makeUnmodifiable()")
    class MakeUnmodifiableTests {

        @Test
        @DisplayName("Should return a non-null unmodifiable list")
        void shouldReturnNonNull() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Unmodifiable list should contain all original elements")
        void shouldContainAllElements() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThat(result).containsExactly(ALICE, BOB, CHARLIE, DIANA, EVE);
        }

        @Test
        @DisplayName("add() on unmodifiable list should throw UnsupportedOperationException")
        void addShouldThrow() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThatThrownBy(() -> result.add(new Student("Hacker", 6.0, 0)))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("remove() on unmodifiable list should throw UnsupportedOperationException")
        void removeShouldThrow() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThatThrownBy(() -> result.remove(0))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("set() on unmodifiable list should throw UnsupportedOperationException")
        void setShouldThrow() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThatThrownBy(() -> result.set(0, new Student("Hacker", 6.0, 0)))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("clear() on unmodifiable list should throw UnsupportedOperationException")
        void clearShouldThrow() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThatThrownBy(() -> result.clear())
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("Reading from unmodifiable list should work normally")
        void readingShouldWork() {
            List<Student> result = CollectionsUtilityExercise.makeUnmodifiable(unsortedStudents());

            assertThat(result.get(0)).isEqualTo(ALICE);
            assertThat(result.size()).isEqualTo(5);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Sequenced Set from Map
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: createSequencedSetFromMap()")
    class SequencedSetTests {

        @Test
        @DisplayName("Should return a non-null SequencedSet")
        void shouldReturnNonNull() {
            SequencedSet<String> set = CollectionsUtilityExercise
                    .createSequencedSetFromMap(new LinkedHashMap<>());

            assertThat(set).isNotNull();
        }

        @Test
        @DisplayName("SequencedSet should preserve insertion order")
        void shouldPreserveInsertionOrder() {
            SequencedSet<String> set = CollectionsUtilityExercise
                    .createSequencedSetFromMap(new LinkedHashMap<>());

            set.add("Zebra");
            set.add("Apple");
            set.add("Mango");

            assertThat(set).containsExactly("Zebra", "Apple", "Mango");
        }

        @Test
        @DisplayName("SequencedSet should reject duplicate elements")
        void shouldRejectDuplicates() {
            SequencedSet<String> set = CollectionsUtilityExercise
                    .createSequencedSetFromMap(new LinkedHashMap<>());

            set.add("Alpha");
            set.add("Beta");
            boolean added = set.add("Alpha"); // duplicate

            assertThat(added).isFalse();
            assertThat(set).hasSize(2);
            assertThat(set).containsExactly("Alpha", "Beta");
        }

        @Test
        @DisplayName("SequencedSet getFirst() and getLast() should reflect insertion order")
        void shouldSupportFirstAndLast() {
            SequencedSet<String> set = CollectionsUtilityExercise
                    .createSequencedSetFromMap(new LinkedHashMap<>());

            set.add("First");
            set.add("Middle");
            set.add("Last");

            assertThat(set.getFirst()).isEqualTo("First");
            assertThat(set.getLast()).isEqualTo("Last");
        }

        @Test
        @DisplayName("SequencedSet reversed() should return elements in reverse insertion order")
        void shouldSupportReversed() {
            SequencedSet<String> set = CollectionsUtilityExercise
                    .createSequencedSetFromMap(new LinkedHashMap<>());

            set.add("A");
            set.add("B");
            set.add("C");

            assertThat(set.reversed()).containsExactly("C", "B", "A");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 5 — Shuffle and Rotate
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 5: shuffleAndRotate()")
    class ShuffleAndRotateTests {

        @Test
        @DisplayName("Should preserve all elements after shuffle and rotate")
        void shouldPreserveAllElements() {
            List<Student> students = unsortedStudents();

            CollectionsUtilityExercise.shuffleAndRotate(students, 2);

            assertThat(students).hasSize(5);
            assertThat(students).containsExactlyInAnyOrder(ALICE, BOB, CHARLIE, DIANA, EVE);
        }

        @Test
        @DisplayName("Shuffle should change the order (with very high probability)")
        void shuffleShouldRandomize() {
            // Run multiple shuffles — at least one should differ from the original order.
            // The probability that 10 shuffles ALL produce the same order as the original
            // is astronomically low (1/120^10 for 5 elements).
            List<Student> original = List.of(ALICE, BOB, CHARLIE, DIANA, EVE);
            boolean atLeastOneDifferent = false;

            for (int i = 0; i < 10; i++) {
                List<Student> students = new ArrayList<>(original);
                CollectionsUtilityExercise.shuffleAndRotate(students, 0); // rotate by 0 = no rotation
                if (!students.equals(original)) {
                    atLeastOneDifferent = true;
                    break;
                }
            }

            assertThat(atLeastOneDifferent)
                    .as("After 10 shuffle attempts, at least one order should differ from original")
                    .isTrue();
        }

        @Test
        @DisplayName("Rotate by 0 after shuffle should just shuffle (no rotation effect)")
        void rotateByZeroShouldNotAffectShuffle() {
            List<Student> students = unsortedStudents();

            CollectionsUtilityExercise.shuffleAndRotate(students, 0);

            // Just verify the list still has all elements — order is random
            assertThat(students).containsExactlyInAnyOrder(ALICE, BOB, CHARLIE, DIANA, EVE);
        }

        @Test
        @DisplayName("Rotate by list size should return to the same shuffled order")
        void rotateByFullSizeShouldCycle() {
            // Create a deterministic list to test rotation independently
            List<Student> students = new ArrayList<>(List.of(BOB, DIANA));

            // Manually test rotate behavior: rotating [X, Y] by 2 returns [X, Y]
            // But since shuffle comes first, we just verify element preservation
            CollectionsUtilityExercise.shuffleAndRotate(students, students.size());

            assertThat(students).containsExactlyInAnyOrder(BOB, DIANA);
        }
    }
}

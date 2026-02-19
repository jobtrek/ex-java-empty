package ch.jobtrek.datastructures.streams;

import ch.jobtrek.datastructures.collections.Student;
import ch.jobtrek.datastructures.tree.Earthquake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for Module 6 — Streams and Stream Gatherers.
 *
 * <p>Group A (Exercises 1–5) verifies the fundamental Stream operations:
 * filtering, mapping, numeric reduction, partitioning, and frequency analysis.</p>
 *
 * <p>Group B (Exercises 6–9) verifies the Gatherer-based operations:
 * fixed-window batching, running balance, sliding moving average, and
 * consecutive deduplication.</p>
 */
@DisplayName("Module 6 — Streams and Stream Gatherers")
class StreamGatherersTest {

    // -----------------------------------------------------------------------
    // Shared fixtures
    // -----------------------------------------------------------------------

    private static final List<Student> STUDENTS = List.of(
            new Student("Alice",   5.5, 20),
            new Student("Bob",     3.2, 15),
            new Student("Charlie", 4.0, 18),
            new Student("Diana",   5.0, 22),
            new Student("Eve",     2.8, 10),
            new Student("Frank",   4.8, 19)
    );

    // -----------------------------------------------------------------------
    // Exercise 1 — filterByGrade
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: filterByGrade()")
    class FilterByGradeTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.filterByGrade(STUDENTS, 4.0);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should keep only students with grade >= minGrade")
        void shouldRetainPassingStudents() {
            var result = StreamGatherersExercise.filterByGrade(STUDENTS, 4.0);

            assertThat(result)
                    .extracting(Student::name)
                    .containsExactlyInAnyOrder("Alice", "Charlie", "Diana", "Frank");
        }

        @Test
        @DisplayName("Should exclude students below minGrade")
        void shouldExcludeFailingStudents() {
            var result = StreamGatherersExercise.filterByGrade(STUDENTS, 4.0);

            assertThat(result)
                    .extracting(Student::name)
                    .doesNotContain("Bob", "Eve");
        }

        @Test
        @DisplayName("Should return all students when minGrade is 0.0")
        void shouldReturnAllWhenMinGradeIsZero() {
            var result = StreamGatherersExercise.filterByGrade(STUDENTS, 0.0);

            assertThat(result).hasSize(STUDENTS.size());
        }

        @Test
        @DisplayName("Should return an empty list when no student meets the threshold")
        void shouldReturnEmptyWhenNoneQualify() {
            var result = StreamGatherersExercise.filterByGrade(STUDENTS, 6.1);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.filterByGrade(List.of(), 4.0);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should not modify the original list")
        void shouldNotModifyOriginalList() {
            var input = List.of(new Student("X", 3.0, 5));
            StreamGatherersExercise.filterByGrade(input, 4.0);

            assertThat(input).hasSize(1);
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 2 — extractNames
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: extractNames()")
    class ExtractNamesTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.extractNames(STUDENTS);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should extract the name of every student in encounter order")
        void shouldExtractAllNames() {
            var result = StreamGatherersExercise.extractNames(STUDENTS);

            assertThat(result)
                    .containsExactly("Alice", "Bob", "Charlie", "Diana", "Eve", "Frank");
        }

        @Test
        @DisplayName("Should preserve encounter order")
        void shouldPreserveOrder() {
            var students = List.of(
                    new Student("Z", 4.0, 10),
                    new Student("A", 4.0, 10),
                    new Student("M", 4.0, 10)
            );

            var result = StreamGatherersExercise.extractNames(students);

            assertThat(result).containsExactly("Z", "A", "M");
        }

        @Test
        @DisplayName("Should return a list of the same size as the input")
        void resultSizeShouldMatchInput() {
            var result = StreamGatherersExercise.extractNames(STUDENTS);

            assertThat(result).hasSize(STUDENTS.size());
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.extractNames(List.of());

            assertThat(result).isEmpty();
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 3 — computeAverageGrade
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: computeAverageGrade()")
    class ComputeAverageGradeTests {

        @Test
        @DisplayName("Should compute the correct average for a normal list")
        void shouldComputeAverage() {
            // (5.5 + 3.2 + 4.0 + 5.0 + 2.8 + 4.8) / 6 = 25.3 / 6 ≈ 4.2167
            double result = StreamGatherersExercise.computeAverageGrade(STUDENTS);

            assertThat(result).isCloseTo(4.2167, within(0.001));
        }

        @Test
        @DisplayName("Should return 0.0 for an empty list")
        void shouldReturnZeroForEmptyList() {
            double result = StreamGatherersExercise.computeAverageGrade(List.of());

            assertThat(result).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should return the single grade for a one-element list")
        void shouldReturnSingleGradeForSingletonList() {
            var students = List.of(new Student("Solo", 4.5, 10));

            double result = StreamGatherersExercise.computeAverageGrade(students);

            assertThat(result).isEqualTo(4.5);
        }

        @Test
        @DisplayName("Should return the exact average when all grades are equal")
        void shouldHandleUniformGrades() {
            var students = List.of(
                    new Student("A", 3.0, 10),
                    new Student("B", 3.0, 10),
                    new Student("C", 3.0, 10)
            );

            double result = StreamGatherersExercise.computeAverageGrade(students);

            assertThat(result).isEqualTo(3.0);
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 4 — groupStudentsByPassFail
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: groupStudentsByPassFail()")
    class GroupStudentsByPassFailTests {

        @Test
        @DisplayName("Should return a non-null map with both keys present")
        void shouldReturnMapWithBothKeys() {
            var result = StreamGatherersExercise.groupStudentsByPassFail(STUDENTS, 4.0);

            assertThat(result).containsKeys(true, false);
        }

        @Test
        @DisplayName("Should correctly separate passing from failing students")
        void shouldSeparatePassAndFail() {
            var result = StreamGatherersExercise.groupStudentsByPassFail(STUDENTS, 4.0);

            assertThat(result.get(true))
                    .extracting(Student::name)
                    .containsExactlyInAnyOrder("Alice", "Charlie", "Diana", "Frank");

            assertThat(result.get(false))
                    .extracting(Student::name)
                    .containsExactlyInAnyOrder("Bob", "Eve");
        }

        @Test
        @DisplayName("Passing threshold should be inclusive")
        void shouldTreatThresholdAsInclusive() {
            // Charlie has exactly 4.0 — should be in the passing group
            var result = StreamGatherersExercise.groupStudentsByPassFail(STUDENTS, 4.0);

            assertThat(result.get(true))
                    .extracting(Student::name)
                    .contains("Charlie");
        }

        @Test
        @DisplayName("Should place all students in the passing group when threshold is 0.0")
        void allPassWhenThresholdIsZero() {
            var result = StreamGatherersExercise.groupStudentsByPassFail(STUDENTS, 0.0);

            assertThat(result.get(true)).hasSize(STUDENTS.size());
            assertThat(result.get(false)).isEmpty();
        }

        @Test
        @DisplayName("Should place all students in the failing group when threshold is impossibly high")
        void allFailWhenThresholdIsImpossiblyHigh() {
            var result = StreamGatherersExercise.groupStudentsByPassFail(STUDENTS, 7.0);

            assertThat(result.get(true)).isEmpty();
            assertThat(result.get(false)).hasSize(STUDENTS.size());
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 5 — countWordFrequencies
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 5: countWordFrequencies()")
    class CountWordFrequenciesTests {

        @Test
        @DisplayName("Should return a non-null map")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.countWordFrequencies(List.of("a", "b"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should count each word occurrence correctly")
        void shouldCountFrequencies() {
            var words = List.of("apple", "banana", "apple", "cherry", "banana", "apple");

            var result = StreamGatherersExercise.countWordFrequencies(words);

            assertThat(result)
                    .containsEntry("apple",  3L)
                    .containsEntry("banana", 2L)
                    .containsEntry("cherry", 1L);
        }

        @Test
        @DisplayName("Should return a map with one entry per unique word")
        void shouldHaveOneEntryPerUniqueWord() {
            var words = List.of("x", "y", "z", "x", "y", "x");

            var result = StreamGatherersExercise.countWordFrequencies(words);

            assertThat(result).hasSize(3);
        }

        @Test
        @DisplayName("Each word appearing once should have count 1")
        void singleOccurrenceShouldCountAsOne() {
            var words = List.of("alpha", "beta", "gamma");

            var result = StreamGatherersExercise.countWordFrequencies(words);

            result.values().forEach(count -> assertThat(count).isEqualTo(1L));
        }

        @Test
        @DisplayName("Should return an empty map for an empty input")
        void shouldReturnEmptyMapForEmptyInput() {
            var result = StreamGatherersExercise.countWordFrequencies(List.of());

            assertThat(result).isEmpty();
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 6 — batchEmailRecords
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 6: batchEmailRecords()")
    class BatchEmailRecordsTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.batchEmailRecords(List.of("a@b.com"), 5);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should produce the correct number of full batches")
        void shouldProduceCorrectBatchCount() {
            // 9 emails / batch size 3 = 3 full batches
            var emails = List.of("e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 3);

            assertThat(result).hasSize(3);
        }

        @Test
        @DisplayName("Each full batch should have exactly batchSize elements")
        void fullBatchesShouldHaveCorrectSize() {
            var emails = List.of("e1", "e2", "e3", "e4", "e5", "e6");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 2);

            result.forEach(batch -> assertThat(batch).hasSize(2));
        }

        @Test
        @DisplayName("The final batch may be smaller when total count is not divisible by batchSize")
        void lastBatchMayBeSmallerThanBatchSize() {
            // 8 emails / batch size 3 → [3, 3, 2]
            var emails = List.of("e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 3);

            assertThat(result).hasSize(3);
            assertThat(result.get(0)).hasSize(3);
            assertThat(result.get(1)).hasSize(3);
            assertThat(result.get(2)).hasSize(2);
        }

        @Test
        @DisplayName("Batches should preserve the original encounter order")
        void shouldPreserveOrder() {
            var emails = List.of("a@x.com", "b@x.com", "c@x.com", "d@x.com");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 2);

            assertThat(result.get(0)).containsExactly("a@x.com", "b@x.com");
            assertThat(result.get(1)).containsExactly("c@x.com", "d@x.com");
        }

        @Test
        @DisplayName("All original elements should be present across all batches")
        void allElementsShouldBePresent() {
            var emails = List.of("e1", "e2", "e3", "e4", "e5");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 2);
            var flat = result.stream().flatMap(List::stream).toList();

            assertThat(flat).containsExactly("e1", "e2", "e3", "e4", "e5");
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.batchEmailRecords(List.of(), 10);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should produce a single batch when batchSize >= list size")
        void singleBatchWhenBatchSizeCoversAll() {
            var emails = List.of("a@x.com", "b@x.com", "c@x.com");

            var result = StreamGatherersExercise.batchEmailRecords(emails, 100);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst()).hasSize(3);
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 7 — computeRunningBalance
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 7: computeRunningBalance()")
    class ComputeRunningBalanceTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.computeRunningBalance(List.of(10));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should produce one output value per input transaction")
        void outputSizeShouldMatchInput() {
            var transactions = List.of(100, -30, 50, -20, 200);

            var result = StreamGatherersExercise.computeRunningBalance(transactions);

            assertThat(result).hasSize(transactions.size());
        }

        @Test
        @DisplayName("Should compute cumulative running balance correctly")
        void shouldComputeCorrectBalance() {
            // seed=0: 0+100=100, 100-30=70, 70+50=120, 120-20=100, 100+200=300
            var transactions = List.of(100, -30, 50, -20, 200);

            var result = StreamGatherersExercise.computeRunningBalance(transactions);

            assertThat(result).containsExactly(100, 70, 120, 100, 300);
        }

        @Test
        @DisplayName("Should handle a single positive transaction")
        void shouldHandleSingleTransaction() {
            var result = StreamGatherersExercise.computeRunningBalance(List.of(42));

            assertThat(result).containsExactly(42);
        }

        @Test
        @DisplayName("Should handle all-negative transactions")
        void shouldHandleAllNegativeTransactions() {
            var result = StreamGatherersExercise.computeRunningBalance(List.of(-10, -20, -30));

            assertThat(result).containsExactly(-10, -30, -60);
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.computeRunningBalance(List.of());

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Running balance with all zeros should remain zero")
        void allZeroTransactionsShouldRemainZero() {
            var result = StreamGatherersExercise.computeRunningBalance(List.of(0, 0, 0));

            assertThat(result).containsExactly(0, 0, 0);
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 8 — computeMovingAverage
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 8: computeMovingAverage()")
    class ComputeMovingAverageTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.computeMovingAverage(
                    List.of(1.0, 2.0, 3.0), 2);

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Output should have n - windowSize + 1 elements")
        void outputSizeShouldBeCorrect() {
            // 6 elements, window 3 → 4 averages
            var temps = List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);

            var result = StreamGatherersExercise.computeMovingAverage(temps, 3);

            assertThat(result).hasSize(4);
        }

        @Test
        @DisplayName("Should compute correct moving averages for window size 3")
        void shouldComputeCorrectAverages() {
            // Windows: [1,2,3]→2.0, [2,3,4]→3.0, [3,4,5]→4.0, [4,5,6]→5.0
            var temps = List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0);

            var result = StreamGatherersExercise.computeMovingAverage(temps, 3);

            assertThat(result).hasSize(4);
            assertThat(result.get(0)).isCloseTo(2.0, within(0.001));
            assertThat(result.get(1)).isCloseTo(3.0, within(0.001));
            assertThat(result.get(2)).isCloseTo(4.0, within(0.001));
            assertThat(result.get(3)).isCloseTo(5.0, within(0.001));
        }

        @Test
        @DisplayName("Window size 1 should return a copy of the input values")
        void windowSizeOneShouldReturnInputCopy() {
            var temps = List.of(10.0, 20.0, 30.0);

            var result = StreamGatherersExercise.computeMovingAverage(temps, 1);

            assertThat(result).hasSize(3);
            assertThat(result.get(0)).isEqualTo(10.0);
            assertThat(result.get(1)).isEqualTo(20.0);
            assertThat(result.get(2)).isEqualTo(30.0);
        }

        @Test
        @DisplayName("Window size equal to list size should return a single overall average")
        void windowSizeEqualToListSizeShouldReturnSingleAverage() {
            var temps = List.of(10.0, 20.0, 30.0);

            var result = StreamGatherersExercise.computeMovingAverage(temps, 3);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst()).isCloseTo(20.0, within(0.001));
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.computeMovingAverage(List.of(), 3);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should compute correct averages for non-uniform temperature data")
        void shouldHandleNonUniformData() {
            // Models three-day rolling average: [22,25,19] → 22.0, [25,19,30] → 24.67
            var temps = List.of(22.0, 25.0, 19.0, 30.0);

            var result = StreamGatherersExercise.computeMovingAverage(temps, 3);

            assertThat(result).hasSize(2);
            assertThat(result.get(0)).isCloseTo(22.0,  within(0.01));
            assertThat(result.get(1)).isCloseTo(24.67, within(0.01));
        }
    }

    // -----------------------------------------------------------------------
    // Exercise 9 — deduplicateConsecutive
    // -----------------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 9: deduplicateConsecutive()")
    class DeduplicateConsecutiveTests {

        @Test
        @DisplayName("Should return a non-null list")
        void shouldReturnNonNull() {
            var result = StreamGatherersExercise.deduplicateConsecutive(List.of("a"));

            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Should remove consecutive duplicate strings")
        void shouldRemoveConsecutiveDuplicates() {
            // A A B C C C A B B → A B C A B
            var input = List.of("A", "A", "B", "C", "C", "C", "A", "B", "B");

            var result = StreamGatherersExercise.deduplicateConsecutive(input);

            assertThat(result).containsExactly("A", "B", "C", "A", "B");
        }

        @Test
        @DisplayName("Should preserve non-consecutive repetitions (unlike distinct())")
        void shouldPreserveNonConsecutiveRepetitions() {
            // A B A — the second A is NOT consecutive, so it must be kept
            var input = List.of("A", "B", "A");

            var result = StreamGatherersExercise.deduplicateConsecutive(input);

            assertThat(result).containsExactly("A", "B", "A");
        }

        @Test
        @DisplayName("Should not modify a list with no consecutive duplicates")
        void shouldLeaveNonDuplicateListUnchanged() {
            var input = List.of("X", "Y", "Z");

            var result = StreamGatherersExercise.deduplicateConsecutive(input);

            assertThat(result).containsExactly("X", "Y", "Z");
        }

        @Test
        @DisplayName("Should return a single element when all elements are equal")
        void allEqualShouldReturnSingleElement() {
            var input = List.of("same", "same", "same", "same");

            var result = StreamGatherersExercise.deduplicateConsecutive(input);

            assertThat(result).containsExactly("same");
        }

        @Test
        @DisplayName("Should handle a single-element input")
        void shouldHandleSingleElement() {
            var result = StreamGatherersExercise.deduplicateConsecutive(List.of("only"));

            assertThat(result).containsExactly("only");
        }

        @Test
        @DisplayName("Should return an empty list for an empty input")
        void shouldHandleEmptyInput() {
            var result = StreamGatherersExercise.deduplicateConsecutive(List.of());

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Should work correctly with Integer elements")
        void shouldWorkWithIntegers() {
            // 1 1 2 3 3 1 → 1 2 3 1
            var input = List.of(1, 1, 2, 3, 3, 1);

            var result = StreamGatherersExercise.deduplicateConsecutive(input);

            assertThat(result).containsExactly(1, 2, 3, 1);
        }

        @Test
        @DisplayName("Should prove it is not equivalent to distinct(): non-consecutive duplicates survive")
        void distinctWouldGiveDifferentResultThanDeduplicateConsecutive() {
            // Input where distinct() → [A, B] but deduplicateConsecutive → [A, B, A]
            var input = List.of("A", "B", "A");

            var consecutiveResult = StreamGatherersExercise.deduplicateConsecutive(input);
            var distinctResult    = input.stream().distinct().toList();

            assertThat(consecutiveResult).hasSize(3);
            assertThat(distinctResult).hasSize(2);
            assertThat(consecutiveResult).isNotEqualTo(distinctResult);
        }
    }
}

package ch.jobtrek.basics.types;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 2 — Java's Type System.
 */
@DisplayName("Module 2 — Java's Type System")
class TypeSystemTest {

    // ---------------------------------------------------------------
    // Exercise 1 — Integer Division and Casting
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: divide()")
    class DivideTests {

        @Test
        @DisplayName("Should return the exact decimal result, not a truncated integer")
        void shouldNotTruncate() {
            // The classic trap: int / int = int in Java → 7/2 would give 3.0, not 3.5
            assertThat(TypeSystemExercise.divide(7, 2)).isEqualTo(3.5);
        }

        @Test
        @DisplayName("Should return a repeating decimal for 10 / 3")
        void shouldReturnDecimalForThirds() {
            assertThat(TypeSystemExercise.divide(10, 3))
                    .isCloseTo(3.333, Offset.offset(0.001));
        }

        @Test
        @DisplayName("Should return an exact result when evenly divisible")
        void shouldReturnExactWhenEven() {
            assertThat(TypeSystemExercise.divide(6, 3)).isEqualTo(2.0);
        }

        @Test
        @DisplayName("Should work with negative operands")
        void shouldWorkWithNegatives() {
            assertThat(TypeSystemExercise.divide(-7, 2)).isEqualTo(-3.5);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Arrays and Null
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: firstOrNull()")
    class FirstOrNullTests {

        @Test
        @DisplayName("Should return the first element of a non-empty array")
        void shouldReturnFirstElement() {
            assertThat(TypeSystemExercise.firstOrNull(new String[]{"apple", "banana"}))
                    .isEqualTo("apple");
        }

        @Test
        @DisplayName("Should return null for an empty array")
        void shouldReturnNullForEmpty() {
            assertThat(TypeSystemExercise.firstOrNull(new String[]{})).isNull();
        }

        @Test
        @DisplayName("Should work for a single-element array")
        void shouldWorkForSingleElement() {
            assertThat(TypeSystemExercise.firstOrNull(new String[]{"only"}))
                    .isEqualTo("only");
        }

        @Test
        @DisplayName("Should return null for a null array")
        void shouldReturnNullForNullArray() {
            assertThat(TypeSystemExercise.firstOrNull(null)).isNull();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — The char Primitive Type
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: countChar()")
    class CountCharTests {

        @Test
        @DisplayName("Should count character occurrences correctly")
        void shouldCountOccurrences() {
            assertThat(TypeSystemExercise.countChar("hello world", 'l')).isEqualTo(3);
        }

        @Test
        @DisplayName("Should return 0 when the character is not present")
        void shouldReturnZeroWhenAbsent() {
            assertThat(TypeSystemExercise.countChar("hello", 'z')).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 for an empty string")
        void shouldReturnZeroForEmptyString() {
            assertThat(TypeSystemExercise.countChar("", 'a')).isEqualTo(0);
        }

        @Test
        @DisplayName("Should be case-sensitive")
        void shouldBeCaseSensitive() {
            assertThat(TypeSystemExercise.countChar("Hello Hello", 'H')).isEqualTo(2);
            assertThat(TypeSystemExercise.countChar("Hello Hello", 'h')).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 for a null string")
        void shouldReturnZeroForNullText() {
            assertThat(TypeSystemExercise.countChar(null, 'a')).isEqualTo(0);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Null Safety
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: safeLength()")
    class SafeLengthTests {

        @Test
        @DisplayName("Should return the length for a non-null string")
        void shouldReturnLength() {
            assertThat(TypeSystemExercise.safeLength("hello")).isEqualTo(5);
        }

        @Test
        @DisplayName("Should return 0 for null — without throwing NullPointerException")
        void shouldReturnZeroForNull() {
            assertThat(TypeSystemExercise.safeLength(null)).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 for an empty string")
        void shouldReturnZeroForEmptyString() {
            assertThat(TypeSystemExercise.safeLength("")).isEqualTo(0);
        }
    }
}
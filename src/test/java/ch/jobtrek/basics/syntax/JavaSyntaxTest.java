package ch.jobtrek.basics.syntax;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 1 — Java Syntax for Web Developers.
 */
@DisplayName("Module 1 — Java Syntax for Web Developers")
class JavaSyntaxTest {

    // ---------------------------------------------------------------
    // Exercise 1 — Variables and String Formatting
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: formatGreeting()")
    class FormatGreetingTests {

        @Test
        @DisplayName("Should return the correctly formatted greeting")
        void shouldFormatGreeting() {
            assertThat(JavaSyntaxExercise.formatGreeting("Alice", 30))
                    .isEqualTo("Hello, Alice! You are 30 years old.");
        }

        @Test
        @DisplayName("Should work with different names and ages")
        void shouldWorkWithDifferentInputs() {
            assertThat(JavaSyntaxExercise.formatGreeting("Bob", 25))
                    .isEqualTo("Hello, Bob! You are 25 years old.");
        }

        @Test
        @DisplayName("Should handle single-character names")
        void shouldHandleSingleCharName() {
            assertThat(JavaSyntaxExercise.formatGreeting("X", 1))
                    .isEqualTo("Hello, X! You are 1 years old.");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Control Flow
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: classifyScore()")
    class ClassifyScoreTests {

        @Test
        @DisplayName("Score >= 90 should return 'Excellent'")
        void shouldReturnExcellentForHighScores() {
            assertThat(JavaSyntaxExercise.classifyScore(100)).isEqualTo("Excellent");
            assertThat(JavaSyntaxExercise.classifyScore(90)).isEqualTo("Excellent");
        }

        @Test
        @DisplayName("Score >= 75 and < 90 should return 'Good'")
        void shouldReturnGoodForMidHighScores() {
            assertThat(JavaSyntaxExercise.classifyScore(89)).isEqualTo("Good");
            assertThat(JavaSyntaxExercise.classifyScore(75)).isEqualTo("Good");
        }

        @Test
        @DisplayName("Score >= 60 and < 75 should return 'Average'")
        void shouldReturnAverageForMidScores() {
            assertThat(JavaSyntaxExercise.classifyScore(74)).isEqualTo("Average");
            assertThat(JavaSyntaxExercise.classifyScore(60)).isEqualTo("Average");
        }

        @Test
        @DisplayName("Score < 60 should return 'Failing'")
        void shouldReturnFailingForLowScores() {
            assertThat(JavaSyntaxExercise.classifyScore(59)).isEqualTo("Failing");
            assertThat(JavaSyntaxExercise.classifyScore(0)).isEqualTo("Failing");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Enhanced For-Each
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: sumEvens()")
    class SumEvensTests {

        @Test
        @DisplayName("Should return the sum of all even numbers")
        void shouldSumEvenNumbers() {
            assertThat(JavaSyntaxExercise.sumEvens(new int[]{1, 2, 3, 4, 5, 6})).isEqualTo(12);
        }

        @Test
        @DisplayName("Should return 0 for an empty array")
        void shouldReturnZeroForEmpty() {
            assertThat(JavaSyntaxExercise.sumEvens(new int[]{})).isEqualTo(0);
        }

        @Test
        @DisplayName("Should return 0 when no even numbers are present")
        void shouldReturnZeroForAllOdds() {
            assertThat(JavaSyntaxExercise.sumEvens(new int[]{1, 3, 5, 7})).isEqualTo(0);
        }

        @Test
        @DisplayName("Should handle negative even numbers")
        void shouldHandleNegativeEvens() {
            assertThat(JavaSyntaxExercise.sumEvens(new int[]{-4, -3, 2})).isEqualTo(-2);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Classic For Loop
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: buildRepeat()")
    class BuildRepeatTests {

        @Test
        @DisplayName("Should repeat the word the given number of times, joined by spaces")
        void shouldRepeatWithSpaces() {
            assertThat(JavaSyntaxExercise.buildRepeat("ha", 3)).isEqualTo("ha ha ha");
        }

        @Test
        @DisplayName("Should return just the word when times is 1")
        void shouldReturnSingleWord() {
            assertThat(JavaSyntaxExercise.buildRepeat("hello", 1)).isEqualTo("hello");
        }

        @Test
        @DisplayName("Should return empty string when times is 0")
        void shouldReturnEmptyForZero() {
            assertThat(JavaSyntaxExercise.buildRepeat("abc", 0)).isEqualTo("");
        }

        @Test
        @DisplayName("Should return empty string when times is negative")
        void shouldReturnEmptyForNegative() {
            assertThat(JavaSyntaxExercise.buildRepeat("abc", -5)).isEqualTo("");
        }

        @Test
        @DisplayName("Should not have a trailing space")
        void shouldNotHaveTrailingSpace() {
            String result = JavaSyntaxExercise.buildRepeat("x", 3);
            assertThat(result).doesNotEndWith(" ");
            assertThat(result).isEqualTo("x x x");
        }
    }
}
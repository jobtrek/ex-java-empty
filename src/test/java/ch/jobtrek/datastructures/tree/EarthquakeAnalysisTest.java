package ch.jobtrek.datastructures.tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the Earthquake Scenario — practical BST data modeling.
 *
 * <p>These tests verify that students can use their {@link BinarySearchTree}
 * implementation to store and query real-world earthquake data.</p>
 */
@DisplayName("Module 5 — Earthquake Analysis")
class EarthquakeAnalysisTest {

    // Sample earthquake data (magnitudes are deliberately unordered)
    private static final List<Earthquake> SAMPLE_QUAKES = List.of(
            new Earthquake("San Francisco, USA", 7.9, 1906),
            new Earthquake("Valdivia, Chile", 9.5, 1960),
            new Earthquake("Sumatra, Indonesia", 9.1, 2004),
            new Earthquake("Haiti", 7.0, 2010),
            new Earthquake("Tohoku, Japan", 9.0, 2011),
            new Earthquake("Nepal", 7.8, 2015),
            new Earthquake("Mexico City, Mexico", 8.0, 1985),
            new Earthquake("Lisbon, Portugal", 8.7, 1755)
    );

    // ---------------------------------------------------------------
    // Exercise 1 — Build Tree
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: buildTree()")
    class BuildTreeTests {

        @Test
        @DisplayName("buildTree() should return a non-null tree")
        void buildTreeShouldReturnNonNull() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(SAMPLE_QUAKES);

            assertThat(tree).isNotNull();
        }

        @Test
        @DisplayName("buildTree() should contain all earthquakes")
        void buildTreeShouldContainAll() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(SAMPLE_QUAKES);

            assertThat(tree.size()).isEqualTo(SAMPLE_QUAKES.size());
        }

        @Test
        @DisplayName("buildTree() with empty list should produce an empty tree")
        void buildTreeWithEmptyList() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(List.of());

            assertThat(tree).isNotNull();
            assertThat(tree.size()).isEqualTo(0);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Find Largest Magnitude
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: findLargestMagnitude()")
    class FindLargestMagnitudeTests {

        @Test
        @DisplayName("findLargestMagnitude() should return the 1960 Valdivia earthquake (9.5)")
        void shouldFindLargestMagnitude() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(SAMPLE_QUAKES);

            Earthquake largest = EarthquakeAnalysis.findLargestMagnitude(tree);

            assertThat(largest).isNotNull();
            assertThat(largest.magnitude()).isEqualTo(9.5);
            assertThat(largest.location()).isEqualTo("Valdivia, Chile");
            assertThat(largest.year()).isEqualTo(1960);
        }

        @Test
        @DisplayName("findLargestMagnitude() should return null for an empty tree")
        void shouldReturnNullForEmptyTree() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(List.of());

            Earthquake largest = EarthquakeAnalysis.findLargestMagnitude(tree);

            assertThat(largest).isNull();
        }

        @Test
        @DisplayName("findLargestMagnitude() with single earthquake should return that earthquake")
        void shouldHandleSingleEarthquake() {
            Earthquake only = new Earthquake("Test", 5.0, 2020);
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(List.of(only));

            Earthquake largest = EarthquakeAnalysis.findLargestMagnitude(tree);

            assertThat(largest).isEqualTo(only);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Sorted by Magnitude
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: getSortedByMagnitude()")
    class GetSortedByMagnitudeTests {

        @Test
        @DisplayName("getSortedByMagnitude() should return earthquakes in ascending magnitude order")
        void shouldReturnSortedByMagnitude() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(SAMPLE_QUAKES);

            List<Earthquake> sorted = EarthquakeAnalysis.getSortedByMagnitude(tree);

            assertThat(sorted).isNotNull();
            assertThat(sorted).hasSize(SAMPLE_QUAKES.size());

            // Verify ascending magnitude order
            assertThat(sorted).extracting(Earthquake::magnitude)
                    .containsExactly(7.0, 7.8, 7.9, 8.0, 8.7, 9.0, 9.1, 9.5);
        }

        @Test
        @DisplayName("getSortedByMagnitude() should return empty list for empty tree")
        void shouldReturnEmptyListForEmptyTree() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(List.of());

            List<Earthquake> sorted = EarthquakeAnalysis.getSortedByMagnitude(tree);

            assertThat(sorted).isNotNull();
            assertThat(sorted).isEmpty();
        }

        @Test
        @DisplayName("First sorted earthquake should be Haiti (7.0), last should be Valdivia (9.5)")
        void shouldHaveCorrectFirstAndLast() {
            BinarySearchTree<Earthquake> tree = EarthquakeAnalysis.buildTree(SAMPLE_QUAKES);

            List<Earthquake> sorted = EarthquakeAnalysis.getSortedByMagnitude(tree);

            assertThat(sorted.getFirst().location()).isEqualTo("Haiti");
            assertThat(sorted.getFirst().magnitude()).isEqualTo(7.0);

            assertThat(sorted.getLast().location()).isEqualTo("Valdivia, Chile");
            assertThat(sorted.getLast().magnitude()).isEqualTo(9.5);
        }
    }
}
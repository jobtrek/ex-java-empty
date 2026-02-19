package ch.jobtrek.datastructures.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Module 5 — Binary Search Tree.
 *
 * <p>These tests verify insertion, in-order traversal, size, height,
 * findMax, and contains operations on the custom BST implementation.</p>
 */
@DisplayName("Module 5 — Binary Search Tree")
class BinarySearchTreeTest {

    private BinarySearchTree<Integer> tree;

    @BeforeEach
    void setUp() {
        tree = new BinarySearchTree<>();
    }

    // ---------------------------------------------------------------
    // Exercise 1 — Insert
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: insert()")
    class InsertTests {

        @Test
        @DisplayName("Inserting into an empty tree should set the root")
        void shouldSetRoot() {
            tree.insert(10);

            assertThat(tree.getRoot()).isNotNull();
            assertThat(tree.getRoot().value).isEqualTo(10);
        }

        @Test
        @DisplayName("Smaller values should go to the left subtree")
        void smallerValuesGoLeft() {
            tree.insert(10);
            tree.insert(5);

            assertThat(tree.getRoot().left).isNotNull();
            assertThat(tree.getRoot().left.value).isEqualTo(5);
        }

        @Test
        @DisplayName("Larger values should go to the right subtree")
        void largerValuesGoRight() {
            tree.insert(10);
            tree.insert(15);

            assertThat(tree.getRoot().right).isNotNull();
            assertThat(tree.getRoot().right.value).isEqualTo(15);
        }

        @Test
        @DisplayName("Duplicate values should not be inserted")
        void shouldIgnoreDuplicates() {
            tree.insert(10);
            tree.insert(10);
            tree.insert(10);

            assertThat(tree.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Multiple inserts should build a correct BST structure")
        void shouldBuildCorrectStructure() {
            //       50
            //      /  \
            //    30    70
            //   /  \     \
            //  20  40    80
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(40);
            tree.insert(80);

            assertThat(tree.getRoot().value).isEqualTo(50);
            assertThat(tree.getRoot().left.value).isEqualTo(30);
            assertThat(tree.getRoot().right.value).isEqualTo(70);
            assertThat(tree.getRoot().left.left.value).isEqualTo(20);
            assertThat(tree.getRoot().left.right.value).isEqualTo(40);
            assertThat(tree.getRoot().right.right.value).isEqualTo(80);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — In-Order Traversal
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: inOrderTraversal()")
    class InOrderTraversalTests {

        @Test
        @DisplayName("In-order traversal should produce sorted output")
        void shouldProduceSortedOutput() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(40);
            tree.insert(80);
            tree.insert(10);

            List<Integer> result = new ArrayList<>();
            tree.inOrderTraversal(result);

            assertThat(result).containsExactly(10, 20, 30, 40, 50, 70, 80);
        }

        @Test
        @DisplayName("In-order traversal of empty tree should produce empty list")
        void shouldProduceEmptyListForEmptyTree() {
            List<Integer> result = new ArrayList<>();
            tree.inOrderTraversal(result);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("In-order traversal of single-node tree should produce single element")
        void shouldHandleSingleNode() {
            tree.insert(42);

            List<Integer> result = new ArrayList<>();
            tree.inOrderTraversal(result);

            assertThat(result).containsExactly(42);
        }

        @Test
        @DisplayName("In-order traversal should sort regardless of insertion order")
        void shouldSortRegardlessOfInsertionOrder() {
            // Insert in random order
            tree.insert(5);
            tree.insert(3);
            tree.insert(8);
            tree.insert(1);
            tree.insert(4);
            tree.insert(7);
            tree.insert(9);
            tree.insert(2);
            tree.insert(6);

            List<Integer> result = new ArrayList<>();
            tree.inOrderTraversal(result);

            assertThat(result).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Size
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: size()")
    class SizeTests {

        @Test
        @DisplayName("Empty tree should have size 0")
        void emptyTreeSizeIsZero() {
            assertThat(tree.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("Single-node tree should have size 1")
        void singleNodeSizeIsOne() {
            tree.insert(10);

            assertThat(tree.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Size should reflect the number of inserted unique values")
        void shouldCountAllNodes() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(40);

            assertThat(tree.size()).isEqualTo(5);
        }

        @Test
        @DisplayName("Size should not count duplicates")
        void shouldNotCountDuplicates() {
            tree.insert(10);
            tree.insert(20);
            tree.insert(10); // duplicate
            tree.insert(20); // duplicate

            assertThat(tree.size()).isEqualTo(2);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — Height
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: height()")
    class HeightTests {

        @Test
        @DisplayName("Empty tree should have height -1")
        void emptyTreeHeightIsMinusOne() {
            assertThat(tree.height()).isEqualTo(-1);
        }

        @Test
        @DisplayName("Single-node tree should have height 0")
        void singleNodeHeightIsZero() {
            tree.insert(10);

            assertThat(tree.height()).isEqualTo(0);
        }

        @Test
        @DisplayName("Balanced tree should have expected height")
        void balancedTreeHeight() {
            //       50
            //      /  \
            //    30    70
            //   /  \
            //  20  40
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(40);

            assertThat(tree.height()).isEqualTo(2);
        }

        @Test
        @DisplayName("Degenerate tree (ascending order) has height n-1 — demonstrates worst case")
        void degenerateTreeHeight() {
            // Inserting in ascending order creates a right-skewed linked list:
            // 1 → 2 → 3 → 4 → 5
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);

            assertThat(tree.height()).isEqualTo(4); // height = n - 1

            System.out.println("=== Degenerate BST Demonstration ===");
            System.out.println("  Inserted in order: 1, 2, 3, 4, 5");
            System.out.println("  Size:   " + tree.size());
            System.out.println("  Height: " + tree.height() + "  (optimal would be ~2 for 5 nodes)");
            System.out.println("  → The tree has degenerated into a linked list!");
            System.out.println("    All operations are now O(n) instead of O(log n).");
            System.out.println("    This is why production systems use Red-Black or AVL trees.");
            System.out.println();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 5 — Find Max
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 5: findMax()")
    class FindMaxTests {

        @Test
        @DisplayName("Should return null for an empty tree")
        void shouldReturnNullForEmptyTree() {
            assertThat(tree.findMax()).isNull();
        }

        @Test
        @DisplayName("Should return the only value in a single-node tree")
        void shouldReturnSingleValue() {
            tree.insert(42);

            assertThat(tree.findMax()).isEqualTo(42);
        }

        @Test
        @DisplayName("Should return the largest value in a multi-node tree")
        void shouldReturnLargestValue() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(80);
            tree.insert(60);

            assertThat(tree.findMax()).isEqualTo(80);
        }

        @Test
        @DisplayName("Should find max in a left-only tree (max is root)")
        void shouldFindMaxInLeftOnlyTree() {
            tree.insert(50);
            tree.insert(40);
            tree.insert(30);
            tree.insert(20);

            assertThat(tree.findMax()).isEqualTo(50);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 6 — Contains
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 6: contains()")
    class ContainsTests {

        @Test
        @DisplayName("Should return false for an empty tree")
        void shouldReturnFalseForEmptyTree() {
            assertThat(tree.contains(10)).isFalse();
        }

        @Test
        @DisplayName("Should find a value that exists in the tree")
        void shouldFindExistingValue() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);

            assertThat(tree.contains(30)).isTrue();
            assertThat(tree.contains(50)).isTrue();
            assertThat(tree.contains(70)).isTrue();
        }

        @Test
        @DisplayName("Should return false for a value not in the tree")
        void shouldNotFindMissingValue() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);

            assertThat(tree.contains(10)).isFalse();
            assertThat(tree.contains(40)).isFalse();
            assertThat(tree.contains(100)).isFalse();
        }

        @Test
        @DisplayName("Should find values at various depths")
        void shouldFindAtVariousDepths() {
            tree.insert(50);
            tree.insert(30);
            tree.insert(70);
            tree.insert(20);
            tree.insert(40);
            tree.insert(60);
            tree.insert(80);

            // Root level
            assertThat(tree.contains(50)).isTrue();
            // Level 1
            assertThat(tree.contains(30)).isTrue();
            assertThat(tree.contains(70)).isTrue();
            // Level 2 (leaves)
            assertThat(tree.contains(20)).isTrue();
            assertThat(tree.contains(40)).isTrue();
            assertThat(tree.contains(60)).isTrue();
            assertThat(tree.contains(80)).isTrue();
        }
    }
}
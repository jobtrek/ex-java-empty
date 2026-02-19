package ch.jobtrek.datastructures.streams;

import ch.jobtrek.datastructures.collections.Student;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 * <h1>Module 6 — Streams and Stream Gatherers</h1>
 *
 * <p>This module introduces two complementary halves of modern Java data processing:</p>
 *
 * <h2>Group A — Stream Fundamentals (Exercises 1–5)</h2>
 * <p>The Stream API, introduced in Java 8, provides a declarative, pipeline-based
 * model for transforming collections of data. A stream pipeline has three stages:</p>
 * <ol>
 *   <li><strong>Source:</strong> {@code collection.stream()} or {@code Stream.of(...)}</li>
 *   <li><strong>Intermediate operations:</strong> Lazy transformations such as
 *       {@code filter()}, {@code map()}, and {@code sorted()}. These are not
 *       executed until a terminal operation is called.</li>
 *   <li><strong>Terminal operation:</strong> Triggers evaluation: {@code toList()},
 *       {@code collect()}, {@code forEach()}, {@code reduce()}, etc.</li>
 * </ol>
 *
 * <h2>Group B — Stream Gatherers (Exercises 6–9)</h2>
 * <p>The standard intermediate operations are <em>stateless</em> — each element is
 * processed independently. This makes stateful transformations (running totals,
 * windowing, consecutive deduplication) impossible to express cleanly.</p>
 *
 * <p>Java 22+ introduced <strong>Stream Gatherers</strong> ({@link Gatherer}) as
 * a new kind of intermediate operation that can safely maintain state across elements.
 * The {@link Gatherers} utility class provides built-in implementations:</p>
 * <ul>
 *   <li>{@link Gatherers#windowFixed(int)} — non-overlapping fixed-size batches</li>
 *   <li>{@link Gatherers#windowSliding(int)} — overlapping sliding-window batches</li>
 *   <li>{@link Gatherers#scan(java.util.function.Supplier, java.util.function.BiFunction) scan()} — running prefix accumulation</li>
 *   <li>{@link Gatherers#fold(java.util.function.Supplier, java.util.function.BiFunction) fold()} — many-to-one sequential reduction</li>
 * </ul>
 *
 * <p>A custom gatherer is defined by up to four components:</p>
 * <ol>
 *   <li><strong>Initializer:</strong> Creates a private mutable state object.</li>
 *   <li><strong>Integrator:</strong> Processes each element, reads/updates state,
 *       and optionally pushes results downstream. Returning {@code false} short-circuits
 *       the pipeline.</li>
 *   <li><strong>Combiner:</strong> Merges two states when running in parallel.</li>
 *   <li><strong>Finisher:</strong> Flushes any remaining buffered state after the
 *       input stream is exhausted.</li>
 * </ol>
 */
public class StreamGatherersExercise {

    // =======================================================================
    // Group A — Stream Fundamentals
    // =======================================================================

    /**
     * <h3>Exercise 1 — Filtering with a Predicate</h3>
     *
     * <p>Return only the students whose grade is greater than or equal to
     * {@code minGrade}. This is the most fundamental stream operation: selecting
     * a subset of elements based on a boolean condition.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code students.stream()} to open a sequential pipeline.</li>
     *   <li>Chain {@code .filter(s -> s.grade() >= minGrade)} to keep only
     *       matching students. The lambda is a {@link java.util.function.Predicate}:
     *       it returns {@code true} for elements that should pass through.</li>
     *   <li>Call {@code .toList()} to materialise the filtered elements into
     *       an unmodifiable {@link List}.</li>
     * </ol>
     *
     * <p><strong>Laziness:</strong> Nothing executes until {@code toList()} is
     * called. Intermediate operations merely <em>describe</em> the transformation.</p>
     *
     * <p><strong>Complexity:</strong> O(n) — every element is visited exactly once.</p>
     *
     * @param students the input list of students
     * @param minGrade the minimum grade (inclusive) a student must have
     * @return a new list containing only students with {@code grade >= minGrade}
     */
    public static List<Student> filterByGrade(List<Student> students, double minGrade) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 2 — One-to-One Transformation with map()</h3>
     *
     * <p>Transform a list of {@link Student} objects into a list of their names.
     * {@code map()} is the archetypal one-to-one transformation: for every input
     * element it emits exactly one output element of a (potentially different) type.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code students.stream()}.</li>
     *   <li>Chain {@code .map(Student::name)} — a method reference that is
     *       shorthand for the lambda {@code s -> s.name()}. This maps each
     *       {@code Student} to a {@code String}.</li>
     *   <li>Call {@code .toList()} to collect the names.</li>
     * </ol>
     *
     * <p><strong>Method references vs lambdas:</strong> {@code Student::name} is
     * equivalent to {@code s -> s.name()} but more concise. Prefer method references
     * whenever the lambda only delegates to a single method without extra logic.</p>
     *
     * <p><strong>Complexity:</strong> O(n).</p>
     *
     * @param students the input list of students
     * @return a new list containing the name of each student, in encounter order
     */
    public static List<String> extractNames(List<Student> students) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 3 — Numeric Reduction with mapToDouble() and average()</h3>
     *
     * <p>Compute the arithmetic mean of all student grades. This exercise introduces
     * <em>primitive specialised streams</em> ({@link java.util.stream.DoubleStream})
     * and terminal reductions.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code students.stream()}.</li>
     *   <li>Chain {@code .mapToDouble(Student::grade)} to convert the
     *       {@code Stream<Student>} into a {@link java.util.stream.DoubleStream}.
     *       This avoids boxing each {@code double} into a {@code Double} object.</li>
     *   <li>Call {@code .average()} which returns an {@link java.util.OptionalDouble}
     *       — it is empty when the stream has no elements.</li>
     *   <li>Call {@code .orElse(0.0)} to unwrap the optional, returning {@code 0.0}
     *       if the student list is empty.</li>
     * </ol>
     *
     * <p><strong>Why OptionalDouble?</strong> The average of zero elements is
     * mathematically undefined. The API forces callers to handle this case explicitly
     * rather than returning a magic sentinel value like {@code -1}.</p>
     *
     * <p><strong>Complexity:</strong> O(n).</p>
     *
     * @param students the input list of students (may be empty)
     * @return the average grade, or {@code 0.0} if the list is empty
     */
    public static double computeAverageGrade(List<Student> students) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 4 — Partitioning with Collectors.partitioningBy()</h3>
     *
     * <p>Split students into two groups: those who passed (grade ≥ {@code passingGrade})
     * and those who failed. {@link Collectors#partitioningBy(java.util.function.Predicate) partitioningBy}
     * is a special collector that always produces a {@code Map<Boolean, List<T>>}
     * with exactly two keys: {@code true} (passing) and {@code false} (failing).</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code students.stream()}.</li>
     *   <li>Call {@code .collect(Collectors.partitioningBy(s -> s.grade() >= passingGrade))}.
     *       The predicate determines which partition each student belongs to.</li>
     *   <li>Return the resulting map directly.</li>
     * </ol>
     *
     * <p><strong>partitioningBy vs groupingBy:</strong> Use {@code partitioningBy}
     * when the grouping criterion is boolean (two buckets). Use
     * {@link Collectors#groupingBy(java.util.function.Function) groupingBy} when
     * there are more than two possible categories (e.g., grouping by first letter
     * of name, or by grade range).</p>
     *
     * <p><strong>Complexity:</strong> O(n).</p>
     *
     * @param students     the input list of students
     * @param passingGrade the minimum grade (inclusive) required to pass
     * @return a map with key {@code true} → passing students, {@code false} → failing students
     */
    public static Map<Boolean, List<Student>> groupStudentsByPassFail(
            List<Student> students, double passingGrade) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 5 — Frequency Analysis with groupingBy() and counting()</h3>
     *
     * <p>Count how many times each word appears in the input list.
     * This exercise demonstrates how a <em>downstream collector</em> can be composed
     * inside {@link Collectors#groupingBy(java.util.function.Function, java.util.stream.Collector) groupingBy}
     * to perform aggregation within each group.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code words.stream()}.</li>
     *   <li>Call {@code .collect(Collectors.groupingBy(word -> word, Collectors.counting()))}.
     *       <ul>
     *         <li>The first argument ({@code word -> word}, or equivalently
     *             {@code Function.identity()}) is the <em>classifier</em>: it defines
     *             the key for each group.</li>
     *         <li>The second argument ({@code Collectors.counting()}) is the
     *             <em>downstream collector</em>: instead of collecting matching
     *             elements into a list, it counts them.</li>
     *       </ul>
     *   </li>
     *   <li>Return the resulting {@code Map<String, Long>}.</li>
     * </ol>
     *
     * <p><strong>Complexity:</strong> O(n) amortized — each element is hashed
     * and counted in a single pass.</p>
     *
     * @param words a list of words, potentially with duplicates
     * @return a map from each unique word to the number of times it appears
     */
    public static Map<String, Long> countWordFrequencies(List<String> words) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    // =======================================================================
    // Group B — Stream Gatherers
    // =======================================================================

    /**
     * <h3>Exercise 6 — Fixed-Window Batching with Gatherers.windowFixed()</h3>
     *
     * <p>Split a flat list of email strings into fixed-size sub-lists (batches).
     * This simulates preparing data for external API calls that accept a maximum
     * of {@code batchSize} records per request.</p>
     *
     * <p>{@link Gatherers#windowFixed(int)} groups the stream into
     * <strong>non-overlapping</strong> windows of exactly {@code batchSize} elements.
     * The final window may be smaller if the total element count is not divisible
     * by {@code batchSize}.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code emails.stream()}.</li>
     *   <li>Chain {@code .gather(Gatherers.windowFixed(batchSize))}. This transforms
     *       the {@code Stream<String>} into a {@code Stream<List<String>>}, where
     *       each inner list is one batch.</li>
     *   <li>Call {@code .toList()} to materialise all batches.</li>
     * </ol>
     *
     * <p><strong>Example:</strong> Given 8 emails with {@code batchSize = 3},
     * the result is {@code [[e1,e2,e3], [e4,e5,e6], [e7,e8]]}.</p>
     *
     * <p><strong>Why can't filter/map do this?</strong> Those operations are
     * stateless — they process elements independently and cannot buffer a group
     * of elements before emitting them downstream.</p>
     *
     * @param emails    the flat list of email address strings
     * @param batchSize the maximum number of emails per batch (must be &gt; 0)
     * @return a list of batches, where each batch is a sub-list of emails
     */
    public static List<List<String>> batchEmailRecords(List<String> emails, int batchSize) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 7 — Running Balance with Gatherers.scan()</h3>
     *
     * <p>Transform a list of daily transaction amounts (positive for credits,
     * negative for debits) into a running cumulative balance. Unlike a terminal
     * {@code reduce()} which produces a single final value, {@code scan()} emits
     * an <em>intermediate result after every element</em>, preserving the full
     * history of the accumulation.</p>
     *
     * <p>{@link Gatherers#scan(java.util.function.Supplier, java.util.function.BiFunction) scan()}
     * is a one-to-one gatherer: for each input element it emits exactly one output
     * element — the updated running state.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code transactions.stream()}.</li>
     *   <li>Chain {@code .gather(Gatherers.scan(() -> 0, Integer::sum))}:
     *       <ul>
     *         <li>The first argument ({@code () -> 0}) is the <em>initial seed</em>:
     *             the starting balance.</li>
     *         <li>The second argument ({@code Integer::sum}) is the <em>accumulator</em>:
     *             it receives the current running balance and the next transaction
     *             amount, and returns the new balance.</li>
     *       </ul>
     *   </li>
     *   <li>Call {@code .toList()}.</li>
     * </ol>
     *
     * <p><strong>Example:</strong> Input {@code [100, -30, 50]} with seed {@code 0}
     * yields {@code [100, 70, 120]}.</p>
     *
     * @param transactions a chronological list of transaction amounts (may be negative)
     * @return a list of running balances with one entry per input transaction
     */
    public static List<Integer> computeRunningBalance(List<Integer> transactions) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 8 — Sliding-Window Moving Average with Gatherers.windowSliding()</h3>
     *
     * <p>Compute a moving average over a stream of temperature readings using a
     * sliding window of {@code windowSize} consecutive observations. Sliding-window
     * averages smooth out short-term noise and reveal longer-term trends — a
     * staple of time-series analysis.</p>
     *
     * <p>{@link Gatherers#windowSliding(int)} groups elements into
     * <strong>overlapping</strong> windows. Each subsequent window advances by one
     * element: it drops the oldest observation and adds the newest.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code temperatures.stream()}.</li>
     *   <li>Chain {@code .gather(Gatherers.windowSliding(windowSize))} to produce
     *       a {@code Stream<List<Double>>} of overlapping windows.</li>
     *   <li>Chain {@code .map(window -> window.stream().mapToDouble(Double::doubleValue).average().orElse(0.0))}
     *       to convert each window into its arithmetic mean.</li>
     *   <li>Call {@code .toList()}.</li>
     * </ol>
     *
     * <p><strong>Example:</strong> Input {@code [1.0, 2.0, 3.0, 4.0]} with
     * {@code windowSize = 3} yields windows {@code [[1,2,3], [2,3,4]]},
     * which map to averages {@code [2.0, 3.0]}.</p>
     *
     * <p><strong>Window count:</strong> For a list of {@code n} elements and
     * window size {@code w}, the output contains exactly {@code n - w + 1} averages
     * (when {@code n >= w}).</p>
     *
     * @param temperatures a chronological list of temperature readings
     * @param windowSize   the number of readings to include in each window (must be &gt; 0)
     * @return a list of moving averages, one per sliding window
     */
    public static List<Double> computeMovingAverage(List<Double> temperatures, int windowSize) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 9 — Custom Gatherer: Consecutive Deduplication</h3>
     *
     * <p>Remove elements that are <strong>identical to the immediately preceding
     * element</strong>, while preserving elements that are identical to some
     * earlier element (as long as they are separated by a different value.</p>
     *
     * <p><strong>Why filter() and distinct() cannot solve this:</strong></p>
     * <ul>
     *   <li>{@code filter()} is stateless — it cannot compare an element to the
     *       previous one because it has no memory of what came before.</li>
     *   <li>{@code distinct()} removes <em>all</em> duplicates globally, not just
     *       consecutive ones. {@code [A, B, A]} would become {@code [A, B]},
     *       which is wrong — the second {@code A} is not consecutive.</li>
     * </ul>
     *
     * <p><strong>Steps — build the gatherer with
     * {@link Gatherer#ofSequential(java.util.function.Supplier, Gatherer.Integrator) Gatherer.ofSequential()}:</strong></p>
     * <ol>
     *   <li><strong>Initializer:</strong> Create an {@link AtomicReference}{@code <T>}
     *       initialised to {@code null}. This holds the last element seen.</li>
     *   <li><strong>Integrator:</strong> Use {@link Gatherer.Integrator#ofGreedy(Gatherer.Integrator.Greedy) Integrator.ofGreedy}
     *       (because this gatherer always processes all elements — no short-circuit).
     *       In the lambda:
     *       <ol>
     *         <li>Read the previous element from the {@code AtomicReference}.</li>
     *         <li>If the current element is <em>not equal</em> to the previous one,
     *             push it downstream with {@code downstream.push(element)}.</li>
     *         <li>Always update the {@code AtomicReference} with the current element.</li>
     *       </ol>
     *   </li>
     * </ol>
     *
     * <p><strong>Example:</strong>
     * Input {@code [A, A, B, C, C, C, A, B, B]} →
     * Output {@code [A, B, C, A, B]} (consecutive duplicates removed,
     * but non-consecutive repeats like the second {@code A} and {@code B} are kept).</p>
     *
     * <p><strong>Complexity:</strong> O(n) — every element is visited exactly once.</p>
     *
     * @param <T>   the type of elements
     * @param input the source list, possibly containing consecutive duplicates
     * @return a new list with consecutive duplicate elements removed
     */
    public static <T> List<T> deduplicateConsecutive(List<T> input) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }
}
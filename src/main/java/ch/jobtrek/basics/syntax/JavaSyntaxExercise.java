package ch.jobtrek.basics.syntax;

/**
 * <h1>Module 1 — Java Syntax for Web Developers</h1>
 *
 * <p>If you already know JavaScript, TypeScript, Python, or PHP, Java will look
 * surprisingly familiar. The syntax shares the same C-style roots: curly braces,
 * semicolons, for loops, if/else. The main differences are:</p>
 *
 * <ul>
 *   <li><strong>Every variable has a declared type</strong> — like TypeScript, but
 *       mandatory and enforced at compile time, not just by a type checker.</li>
 *   <li><strong>No dynamic typing</strong> — unlike Python, you cannot reassign a
 *       variable to a value of a different type.</li>
 *   <li><strong>No dollar signs</strong> — unlike PHP, variables are just names.</li>
 *   <li><strong>Everything lives in a class</strong> — even standalone functions must
 *       be declared inside a class. We use {@code static} methods to get something
 *       close to free functions.</li>
 * </ul>
 *
 * <h2>Function / method declaration across languages</h2>
 * <pre>
 *   Python:      def add(a, b):
 *                    return a + b
 *
 *   PHP:         function add($a, $b) { return $a + $b; }
 *
 *   JavaScript:  function add(a, b)   { return a + b; }
 *
 *   TypeScript:  function add(a: number, b: number): number { return a + b; }
 *
 *   Java:        public static int add(int a, int b) { return a + b; }
 * </pre>
 *
 * <p>Java is the most explicit: every parameter and the return type must be declared.
 * The compiler uses this information to catch mistakes before the program even runs.</p>
 */
public class JavaSyntaxExercise {

    /**
     * <h3>Exercise 1 — Variables and String Formatting</h3>
     *
     * <p>Return a greeting in the form: {@code "Hello, Alice! You are 30 years old."}</p>
     *
     * <h4>Variable declaration</h4>
     * <p>In Java, every variable must have a declared type. You can write it explicitly,
     * or use {@code var} to let the compiler infer it (just like TypeScript's type
     * inference):</p>
     * <pre>
     *   // Explicit type (always safe, always readable)
     *   String message = "Hello!";
     *   int    count   = 42;
     *
     *   // Inferred with var (Java 10+, equivalent to TS's inference)
     *   var message = "Hello!";
     *   var count   = 42;
     * </pre>
     *
     * <p>Comparison across languages:</p>
     * <pre>
     *   Python:     message = "Hello!"           # no type, dynamic
     *   PHP:        $message = "Hello!";          # $ prefix, dynamic
     *   TypeScript: const message: string = "Hello!";
     *   Java:       String message = "Hello!";    // or: var message = "Hello!";
     * </pre>
     *
     * <h4>Constants</h4>
     * <p>Use the {@code final} keyword to declare a variable that cannot be reassigned
     * — the equivalent of {@code const} in JS/TS:</p>
     * <pre>
     *   final String PREFIX = "Hello, ";   // reassigning PREFIX later → compile error
     * </pre>
     *
     * <h4>String formatting</h4>
     * <p>Java does not have template literals. Use {@link String#format} instead:</p>
     * <pre>
     *   Python:     f"Hello, {name}! You are {age} years old."
     *   PHP:        "Hello, $name! You are $age years old."
     *   JS/TS:      `Hello, ${name}! You are ${age} years old.`
     *   Java:       String.format("Hello, %s! You are %d years old.", name, age)
     * </pre>
     * <p>{@code %s} is the placeholder for a {@code String}; {@code %d} for an
     * integer ({@code int} or {@code long}).</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Declare a {@code final String} variable {@code prefix} with value
     *       {@code "Hello, "}.</li>
     *   <li>Use {@code String.format()} to build and return the greeting.</li>
     * </ol>
     *
     * @param name the person's name
     * @param age  the person's age
     * @return a formatted greeting string
     */
    public static String formatGreeting(String name, int age) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 2 — Control Flow: if / else if / else</h3>
     *
     * <p>Return a label describing the score:</p>
     * <ul>
     *   <li>{@code score >= 90} → {@code "Excellent"}</li>
     *   <li>{@code score >= 75} → {@code "Good"}</li>
     *   <li>{@code score >= 60} → {@code "Average"}</li>
     *   <li>otherwise           → {@code "Failing"}</li>
     * </ul>
     *
     * <p>The if/else syntax is identical in Java, JavaScript, and PHP.
     * Python uses {@code elif} where the others use {@code else if}:</p>
     * <pre>
     *   Python:          if score >= 90:      ...
     *                    elif score >= 75:    ...
     *                    else:               ...
     *
     *   Java / JS / PHP: if (score >= 90)       { ... }
     *                    else if (score >= 75)  { ... }
     *                    else                   { ... }
     * </pre>
     *
     * @param score an integer score (0 to 100)
     * @return a string label for the score range
     */
    public static String classifyScore(int score) {
        // TODO: complete this method using if / else if / else
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 3 — Loops: Enhanced For-Each</h3>
     *
     * <p>Given an array of integers, return the sum of all <em>even</em> numbers.</p>
     *
     * <p>Java has two common loop forms. The enhanced for-each is idiomatic when you
     * only need the elements (not their index):</p>
     * <pre>
     *   Python:  for n in numbers:          total += n
     *   PHP:     foreach ($numbers as $n) { $total += $n; }
     *   Java:    for (int n : numbers)    { total += n; }
     * </pre>
     *
     * <p>When you need the index, use the classic C-style for loop (identical to JS):</p>
     * <pre>
     *   Java / JS:  for (int i = 0; i &lt; numbers.length; i++) { ... }
     * </pre>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Declare an {@code int} variable {@code sum} initialized to {@code 0}.</li>
     *   <li>Loop over {@code numbers} with an enhanced for-each loop.</li>
     *   <li>Inside the loop, add even numbers to {@code sum}
     *       (hint: {@code n % 2 == 0}).</li>
     *   <li>Return {@code sum}.</li>
     * </ol>
     *
     * @param numbers an array of integers (may be empty)
     * @return the sum of all even numbers in the array
     */
    public static int sumEvens(int[] numbers) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 4 — Loops: Classic For with String Building</h3>
     *
     * <p>Return a string that repeats {@code word} exactly {@code times} times,
     * separated by a single space. For example:</p>
     * <ul>
     *   <li>{@code buildRepeat("ha", 3)} → {@code "ha ha ha"}</li>
     *   <li>{@code buildRepeat("hello", 1)} → {@code "hello"}</li>
     * </ul>
     * <p>If {@code times} is 0 or negative, return an empty string {@code ""}.</p>
     *
     * <p>The classic for loop is syntactically identical across Java, JavaScript, and C:</p>
     * <pre>
     *   for (int i = 0; i &lt; times; i++) { ... }
     * </pre>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Guard against {@code times <= 0}: return {@code ""} immediately.</li>
     *   <li>Create a {@link StringBuilder} — it is more efficient than concatenating
     *       strings in a loop with {@code +}, which creates a new object every
     *       iteration.</li>
     *   <li>Loop from {@code 0} to {@code times - 1} (inclusive).</li>
     *   <li>Append {@code word} each iteration. Append a space {@code " "} between
     *       words but <em>not</em> after the last one (check {@code i < times - 1}).</li>
     *   <li>Return {@code builder.toString()}.</li>
     * </ol>
     *
     * @param word  the word to repeat
     * @param times the number of repetitions
     * @return the words joined by spaces
     */
    public static String buildRepeat(String word, int times) {
        // TODO: complete this method
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }
}

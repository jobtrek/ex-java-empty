package ch.jobtrek.basics.types;

/**
 * <h1>Module 2 — Java's Type System</h1>
 *
 * <p>Coming from JavaScript or Python, the biggest mental shift in Java is moving from
 * <em>dynamic</em> to <em>static</em> typing. In JavaScript, the same variable can hold
 * a number today and a string tomorrow. In Java, every variable has a fixed type that
 * is checked at <em>compile time</em> — before the program ever runs.</p>
 *
 * <h2>Primitive types</h2>
 * <p>Java separates <em>primitive</em> types (stored directly in memory as raw values)
 * from <em>reference</em> types (objects stored on the heap, accessed via pointer).</p>
 *
 * <table>
 *   <caption>Common primitive types and their JS/TS equivalents</caption>
 *   <tr><th>Java</th>      <th>JS/TS equivalent</th>           <th>Size</th></tr>
 *   <tr><td>int</td>       <td>number (integers up to ~2B)</td> <td>32-bit</td></tr>
 *   <tr><td>long</td>      <td>number or BigInt</td>            <td>64-bit</td></tr>
 *   <tr><td>double</td>    <td>number (floating-point)</td>     <td>64-bit</td></tr>
 *   <tr><td>boolean</td>   <td>boolean</td>                     <td>1-bit</td></tr>
 *   <tr><td>char</td>      <td>string of length 1</td>          <td>16-bit</td></tr>
 * </table>
 *
 * <p>{@code String} is <em>not</em> a primitive — it is a reference type (an object),
 * but Java gives it special syntax ({@code "double quotes"}) and some primitive-like
 * conveniences.</p>
 */
public class TypeSystemExercise {

    /**
     * <h3>Exercise 1 — Integer Division and Casting</h3>
     *
     * <p>Divide {@code a} by {@code b} and return the result as a {@code double}
     * (a decimal number, not truncated).</p>
     *
     * <h4>The integer division trap</h4>
     * <p>In JavaScript, {@code 7 / 2} gives {@code 3.5} because JS uses floating-point
     * for all numbers. In Java, when <em>both</em> operands are {@code int}, the result
     * is also an {@code int} — the decimal part is silently discarded:</p>
     * <pre>
     *   int result = 7 / 2;          // result is 3, not 3.5 !
     * </pre>
     *
     * <p>To get a floating-point result, explicitly <em>cast</em> one operand to
     * {@code double} before the division. A cast is written as {@code (targetType)}
     * just before the value:</p>
     * <pre>
     *   double result = (double) a / b;   // ✓  7 / 2 → 3.5
     *   double result = (double) (a / b); // ✗  cast happens after int division → 3.0
     * </pre>
     *
     * <p>The position of the cast matters: cast {@code a} <em>before</em> the {@code /}
     * operator so Java promotes the whole expression to floating-point arithmetic.</p>
     *
     * @param a the dividend
     * @param b the divisor (non-zero)
     * @return the exact division result as a double
     */
    public static double divide(int a, int b) {
        // TODO: return a / b as a double (not truncated — watch out for integer division!)
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 2 — Arrays and Null</h3>
     *
     * <p>Return the first element of the array, or {@code null} if the array is empty.</p>
     *
     * <h4>Java arrays vs JavaScript arrays</h4>
     * <p>Java arrays are fundamentally different from JavaScript arrays:</p>
     * <pre>
     *   // JavaScript: dynamic size, mixed types
     *   const arr = [];
     *   arr.push("hello");
     *   arr.push(42);          // OK — JS arrays accept any type
     *
     *   // Java: fixed size, single declared type
     *   String[] arr = new String[3];   // must declare size upfront
     *   arr[0] = "hello";
     *   // arr[1] = 42;        // compile error: int is not a String
     *   // arr[3] = "x";       // runtime error: ArrayIndexOutOfBoundsException
     * </pre>
     *
     * <p>Accessing an out-of-bounds index in Java throws an
     * {@code ArrayIndexOutOfBoundsException} — unlike JavaScript which returns
     * {@code undefined}. You must always guard with a length check.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>If {@code items} is {@code null} <em>or</em> {@code items.length == 0},
     *       return {@code null}.</li>
     *   <li>Otherwise return {@code items[0]}.</li>
     * </ol>
     *
     * @param items a possibly {@code null} or empty array of strings
     * @return the first element, or {@code null} if the array is null or empty
     */
    public static String firstOrNull(String[] items) {
        // TODO: return the first element, or null if the array is null or empty
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 3 — The char Primitive Type</h3>
     *
     * <p>Count how many times the character {@code target} appears in {@code text}.</p>
     *
     * <h4>char vs String</h4>
     * <p>Java has a {@code char} primitive representing a single Unicode character.
     * It is distinct from a {@code String} (a sequence of chars) and must always be
     * written with <em>single quotes</em>:</p>
     * <pre>
     *   char  letter = 'A';       // single character — single quotes
     *   String word  = "Alice";   // sequence of chars — double quotes
     *   // char wrong = "A";      // compile error: "A" is a String, not a char
     * </pre>
     *
     * <p>To iterate over the characters of a {@code String}, use
     * {@link String#charAt(int)} in a for loop. Compare chars with {@code ==}
     * (they are primitives, so this is a value comparison — not a reference check
     * like with objects):</p>
     * <pre>
     *   for (int i = 0; i &lt; text.length(); i++) {
     *       char c = text.charAt(i);
     *       if (c == target) { ... }
     *   }
     * </pre>
     *
     * @param text   the string to search in (may be {@code null}, treated as empty)
     * @param target the character to count
     * @return the number of times {@code target} appears in {@code text}, or
     *         {@code 0} if {@code text} is {@code null}
     */
    public static int countChar(String text, char target) {
        // TODO: count and return how many times target appears in text
        // If text is null, return 0 (treat it the same as an empty string)
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 4 — Null Safety</h3>
     *
     * <p>Return the length of {@code text}, or {@code 0} if {@code text} is
     * {@code null}.</p>
     *
     * <h4>null in Java</h4>
     * <p>{@code null} means "no object" — the variable holds no reference.
     * Calling any method on a {@code null} reference throws a
     * {@link NullPointerException} (NPE), the most common runtime error in Java:</p>
     * <pre>
     *   String s = null;
     *   s.length();   // throws NullPointerException!
     * </pre>
     *
     * <p>The fix is always to check for {@code null} before calling methods on the
     * value. This is explicit in Java — there is no automatic optional chaining:</p>
     * <pre>
     *   // JavaScript optional chaining (automatic short-circuit):
     *   text?.length ?? 0
     *
     *   // Python None check:
     *   len(text) if text is not None else 0
     *
     *   // Java — explicit null check with if/else:
     *   if (text == null) { return 0; }
     *   return text.length();
     *
     *   // Java — same thing as a ternary (more concise, common in real code):
     *   return text == null ? 0 : text.length();
     * </pre>
     *
     * <p>Both forms are correct. Use whichever you find most readable.</p>
     *
     * @param text a string that may be {@code null}
     * @return the length of {@code text}, or {@code 0} if it is null
     */
    public static int safeLength(String text) {
        // TODO: return text.length(), or 0 if text is null
        // Hint: you can use an if/else, or the ternary: text == null ? 0 : text.length()
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }
}
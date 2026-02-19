package ch.jobtrek.datastructures.collections;

/**
 * A student domain record used throughout the Collections Utility exercises.
 *
 * <p>This is a Java {@code record} — a compact, immutable data carrier introduced
 * in Java 16. The compiler automatically generates the constructor, getters
 * ({@code name()}, {@code grade()}, {@code attendance()}), {@code equals()},
 * {@code hashCode()}, and {@code toString()} methods.</p>
 *
 * @param name       the student's full name
 * @param grade      the student's grade (0.0 to 6.0 scale)
 * @param attendance the number of classes attended
 */
public record Student(String name, double grade, int attendance) {
}
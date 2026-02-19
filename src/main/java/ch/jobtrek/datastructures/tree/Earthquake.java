package ch.jobtrek.datastructures.tree;

/**
 * An earthquake event record used in the BST practical data modeling exercise.
 *
 * <p>Earthquakes are compared by their {@code magnitude} value, making
 * magnitude the natural sorting key when stored in a
 * {@link BinarySearchTree BinarySearchTree&lt;Earthquake&gt;}.</p>
 *
 * <p><strong>Important — equals/hashCode/compareTo consistency:</strong>
 * The default record-generated {@code equals()} compares <em>all</em> fields
 * (location, magnitude, year), but {@code compareTo()} only compares by
 * magnitude. This violates the strongly recommended contract that
 * {@code (a.compareTo(b) == 0) == a.equals(b)}. Sorted collections such as
 * {@link java.util.TreeSet} and {@link java.util.TreeMap} rely on
 * {@code compareTo} for equality checks, so two earthquakes with the same
 * magnitude would be considered duplicates by a BST — even if they have
 * different locations or years.</p>
 *
 * <p>To honour the contract, this record overrides {@code equals()} and
 * {@code hashCode()} to consider <strong>magnitude only</strong>, matching
 * the {@code compareTo} semantics.</p>
 *
 * @param location  the geographical location of the earthquake
 * @param magnitude the seismic magnitude (Richter scale)
 * @param year      the year the earthquake occurred
 */
public record Earthquake(String location, double magnitude, int year) implements Comparable<Earthquake> {

    @Override
    public int compareTo(Earthquake other) {
        return Double.compare(this.magnitude, other.magnitude);
    }

    /**
     * Two earthquakes are equal if and only if they have the same magnitude,
     * consistent with the {@link #compareTo(Earthquake)} ordering.
     */
    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof Earthquake other
                && Double.compare(this.magnitude, other.magnitude) == 0);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(magnitude);
    }
}
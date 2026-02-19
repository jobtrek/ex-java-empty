package ch.jobtrek.datastructures.linear;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple container holding both an {@link ArrayList} and a {@link LinkedList}
 * populated with the same data. This allows side-by-side comparison of the two
 * list implementations.
 *
 * @param arrayList  the ArrayList instance
 * @param linkedList the LinkedList instance
 */
public record ListPair(ArrayList<Integer> arrayList, LinkedList<Integer> linkedList) {
}

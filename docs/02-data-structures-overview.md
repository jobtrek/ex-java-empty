# Data Structures Overview

A **Data Structure** is a specialized format for organizing, processing, retrieving, and storing data. Under the hood, a
data structure manages the way data will be stored and accessed from the computer memory. There is no
"perfect" data structure; each one is designed to be efficient for specific tasks while accepting trade-offs in others.

Choosing the right data structure is often more important than the algorithm itself.

## 1. Why Use Data Structures?

* **Efficiency:** Finding an item in a sorted `TreeSet` ($O(\log n)$) is vastly faster than scanning an
  `ArrayList` ($O(n)$) for large datasets.
* **Abstraction:** They handle complex memory management (like resizing arrays or linking nodes) so you can focus on
  logic.
* **Organization:** They model real-world relationships (e.g., hierarchies with Trees, queues with Queues).

## 2. Common Java Data Structures

### Lists (Ordered Collections)

Used when order matters and you need to access elements by index or iterate sequentially.

* **`ArrayList`**:
    * **Under the hood:** A resizing array.
    * **Best for:** Fast random access (`get(i)` is $O(1)$) and iteration.
    * **Worst for:** Inserting/deleting from the *start* or *middle* ($O(n)$), as all subsequent elements must shift.
    * **Use case:** Storing a list of items where you mostly add to the end and read often.

* **`LinkedList`**:
    * **Under the hood:** A chain of nodes, where each node points to the next (and previous).
    * **Best for:** fast insertion/deletion at the *start* or *end* ($O(1)$) if you have a reference to the node.
    * **Worst for:** Random access (`get(i)` is $O(n)$), as you must traverse from the start.
    * **Use case:** Implementing Queues or Stacks.

### Maps (Key-Value Pairs)

Used when you need to look up data based on a unique identifier (Key).

* **`HashMap`**:
    * **Under the hood:** An array of buckets (hash table).
    * **Best for:** Extremely fast lookups, insertions, and deletions ($O(1)$ average).
    * **Worst for:** Ordering (keys are not sorted).
    * **Use case:** Caching, looking up user profiles by ID, counting word frequencies.

* **`TreeMap`**:
    * **Under the hood:** A Red-Black Tree (balanced binary search tree).
    * **Best for:** Keeping keys **sorted** naturally. Operations are $O(\log n)$.
    * **Worst for:** Slower than `HashMap` for simple lookups.
    * **Use case:** Storing events by timestamp, leaderboard rankings.

### Sets (Unique Elements)

Used when you need to ensure no duplicates exist in your collection.

* **`HashSet`**: Backed by a `HashMap`. Fast ($O(1)$), unordered, unique elements.
* **`TreeSet`**: Backed by a `TreeMap`. Sorted ($O(\log n)$), unique elements.

### Stacks & Queues (Restricted Access)

Used for specific processing orders.

* **`Stack` (LIFO - Last In, First Out):** Like a stack of plates. You can only take the top one.
    * **Use case:** Undo features, backtracking algorithms (e.g., maze solving).
* **`Queue` (FIFO - First In, First Out):** Like a line at a store. First person in is the first served.
    * **Use case:** Task scheduling, breadth-first search.

## 3. Comparison Cheat Sheet

| Data Structure | Access (get) | Search (contains) | Insertion (add)  | Deletion (remove)  | Order?    |
|:---------------|:-------------|:------------------|:-----------------|:-------------------|:----------|
| **ArrayList**  | $O(1)$       | $O(n)$            | $O(1)$ (at end)  | $O(n)$ (start/mid) | Insertion |
| **LinkedList** | $O(n)$       | $O(n)$            | $O(1)$ (at ends) | $O(1)$ (at ends)   | Insertion |
| **HashMap**    | $N/A$        | $O(1)$            | $O(1)$           | $O(1)$             | Random    |
| **TreeMap**    | $N/A$        | $O(\log n)$       | $O(\log n)$      | $O(\log n)$        | Sorted    |
| **HashSet**    | $N/A$        | $O(1)$            | $O(1)$           | $O(1)$             | Random    |
| **TreeSet**    | $N/A$        | $O(\log n)$       | $O(\log n)$      | $O(\log n)$        | Sorted    |

*Note: Big O values are for average cases. Worst cases may vary but are rare with good hash functions.*

## Other languages

Note that data structures are not a "Java thing", each language has its own set of data structures with varying
implementations and performance characteristics.

Some examples of available data structures in other languages:

- JavaScript: `Array`, `Set`, `Map`, `WeakMap`, `WeakSet`
- Python: `list`, `set`, `dict`
- Rust: `Vec`, `HashSet`, `HashMap`

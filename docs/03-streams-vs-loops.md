# Stream Processing vs. Traditional Loops

Java Streams (introduced in Java 8) revolutionized how developers handle collections of data. Instead of focusing on
*how* to iterate (loops), Streams allow you to declare *what* you want to achieve (filtering, mapping, collecting).

This approach is similar to functional programming concepts found in JavaScript, Elixir, Rust... (`map`, `filter`, `reduce`) or SQL
queries.

## 1. Why Use Streams?

* **Readability:** Express complex data transformations in a clear, linear pipeline.
* **Immutability:** Original data structures are not modified; new results are produced.
* **Parallelization:** easily process large datasets across multiple CPU cores with `.parallelStream()`.
* **Less Boilerplate:** Eliminates manual loop counters and temporary storage variables.

## 2. Core Stream Operations

A Stream pipeline consists of three parts:

1. **Source:** A collection, array, or generator (from where your data comes).
2. **Intermediate Operations:** Transform the stream (e.g., `filter`, `map`, `sorted`). These are *lazy* and don't
   execute until a terminal operation is called.
3. **Terminal Operation:** Produce a result (e.g., `collect`, `forEach`, `count`).

### Common Methods

* **`filter(Predicate)`**: Keep only elements that match a condition.
* **`map(Function)`**: Transform each element into something else (e.g., Object -> String).
* **`sorted(Comparator)`**: Sort the stream.
* **`distinct()`**: Remove duplicates.
* **`collect(Collector)`**: Gather results into a List, Set, or Map.

## 3. Comparison: The "Old Way" vs. The "Stream Way"

**Scenario:** We have a list of `Student` objects. We want to find the names of all students who passed (grade >= 50),
convert their names to uppercase, and sort them alphabetically.

### The Traditional Loop Approach (Imperative)

This style focuses on the step-by-step mechanics of iteration.

```java
List<String> passedStudents = new ArrayList<>();

for(Student student :allStudents){
    if(student.getGrade() >=50){
        String name = student.getName().toUpperCase();
        passedStudents.add(name);
    }
}

Collections.sort(passedStudents);

// Result: ["ALICE", "BOB", "CHARLIE"]
```

**Drawbacks:**

* Requires a temporary list (`passedStudents`).
* Logic is mixed with iteration details (the `for` loop).
* Sorting is a separate step at the end.

### The Stream Approach (Declarative)

This style focuses on the flow of data transformation.

```java
List<String> passedStudents = allStudents
    .stream()
    .filter(student -> student.getGrade() >= 50)      // 1. Filter passing grades
    .map(student -> student.getName().toUpperCase()) // 2. Extract name and transform to uppercase names
    .sorted()                                       // 3. Sort alphabetically
    .collect(Collectors.toList());                  // 4. Collect into a result list
```

**Benefits:**

* Reads like a sentence: "Filter, then map, then sort, then collect."
* No temporary variables or manual iteration.
* Modifying the logic (e.g., adding `distinct()`) is as simple as inserting one line.

## 4. When to Use Which?

| Feature              | Streams                                  | Loops                                           |
|:---------------------|:-----------------------------------------|:------------------------------------------------|
| **Simple Iteration** | Overkill for simple tasks.               | **Better** for basic `for (int i=0; i<n; i++)`. |
| **Complex Logic**    | **Excellent** for chaining filters/maps. | Becomes messy with nested `if`s.                |
| **Performance**      | Slight overhead for small lists.         | Slightly faster for raw primitives.             |
| **Parallelism**      | Easy: `.parallelStream()`.               | Hard: Requires manual threading code.           |
| **Debugging**        | Can be tricky to debug inside lambdas.   | Easy to step through with a debugger.           |

**Guideline:** Use Streams for data processing pipelines (filtering, transforming collections). Use loops for low-level
control flow or when performance is absolutely critical in tight inner loops.

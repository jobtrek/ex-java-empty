# Java Exercises

**v1.1.3 :** [Read changelog](./CHANGELOG.md) <!-- x-release-please-version -->

This repo contains guided exercises to learn Java from the ground up. It starts with Java syntax and OOP basics, then
moves on to data structures and performance trade-offs. The concepts covered are broadly applicable — data structures
exist in every language, and the principles are the same even when syntax or performance differs.

## Prerequisites

- Java 25+ (installed on your system or from your IDE)
- Maven 3.6+ (installed on your system or from your IDE)
- Some experience with at least one programming language (JavaScript, TypeScript, Python, or PHP)

## Getting Started

- Clone this repository
- Open it with your favorite IDE (IntelliJ, Eclipse, NetBeans...)
- Run the tests (`mvn test`), they should all fail at the moment — don't worry, you will resolve them.
- Solve the exercises in order, starting from Module 1.

> The exercise comments are written with Javadoc syntax. Depending on your IDE you can display them in a pretty format
> (HTML rendered). In IntelliJ, use the button in the margin to toggle the rendered view.

## Theory

Before starting the exercises, read the relevant background articles:

- [Java for Web Developers](docs/00-java-introduction.md), read this first if you are new to Java
- [What is big O notation](docs/01-big-o-notation.md), read when starting data structures exercises
- [Data Structures](docs/02-data-structures-overview.md)
- [Streams vs loops (bonus only)](docs/03-streams-vs-loops.md)

## Exercises

### Java Basics (Modules 1–3)

Start here if you are new to Java. These modules introduce the language syntax and OOP fundamentals,
with comparisons to JavaScript, TypeScript, Python, and PHP.

- **Module 1**, [Java Syntax for Web Developers](./src/main/java/ch/jobtrek/basics/syntax): variables, control flow, loops, string formatting
- **Module 2**, [Java's Type System](./src/main/java/ch/jobtrek/basics/types): primitives, arrays, casting, null safety
- **Module 3**, [OOP Basics](./src/main/java/ch/jobtrek/basics/oop): classes, encapsulation, constructors, `toString()`

### Data Structures (Modules 4–9)

> **Important:** Read the test output carefully — it includes timing results and explanations about
> real-world performance differences between data structures.

- **Module 4**, [Lists](./src/main/java/ch/jobtrek/datastructures/linear): ArrayList vs LinkedList, cache locality
- **Module 5**, [Collections](./src/main/java/ch/jobtrek/datastructures/collections): sorting, searching, unmodifiable views
- **Module 6**, [Maps](./src/main/java/ch/jobtrek/datastructures/maps): HashMap, LinkedHashMap, TreeMap
- **Module 7**, [Sets](./src/main/java/ch/jobtrek/datastructures/sets): uniqueness, union, intersection, difference
- **Module 8**, [Trees](./src/main/java/ch/jobtrek/datastructures/tree): binary search tree, real-world analysis
- **Module 9**, [Performance Benchmarks](./src/main/java/ch/jobtrek/datastructures/benchmarks): measuring theory vs reality

Run the tests with `mvn test`, or directly from your IDE.

## Bonus

More advanced topics, not mandatory.

- **Module 10**, [Streams and Gatherers](./src/main/java/ch/jobtrek/datastructures/streams): Stream API, custom gatherers (Java 24+)

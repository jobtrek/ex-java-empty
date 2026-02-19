# Java Data Structures Exercises

**v1.0.1 :** [Read changelog](./CHANGELOG.md) <!-- x-release-please-version -->

This repo contains some exercises to help you learn how data structures work. The code is written in Java, but remember
that Data Structures are not language dependent, these concepts
are shared by all languages. Sometimes the implementation and performance can differ, but the underlying principles are
the same.

## Prerequisites

- Java 25+ (installed on your system or from your IDE)
- Maven 3.6+ (installed on your system or from your IDE)
- Java syntax basics knowledge
- Basic understanding of algorithmic complexity https://en.wikipedia.org/wiki/Big_O_notation
- Basic understanding of data structures https://en.wikipedia.org/wiki/Data_structure

## Getting Started

- Clone this repository
- Open it with your favorite IDE (IntelliJ, Eclipse, NetBeans...)
- Run the tests (`mvn test`), they should all fail at the moment, don't worry you will resolve them.
- Solve the exercises.

> The exercises comments are written with Javadoc syntax, depending on your IDE you can display them in pretty format
> (HTML rendered). Example, in IntelliJ, use the button in the margin to toggle pretty print.

## Theory

Before starting the exercises, I encourage you to reat the introduction of the concepts covered in the exercises.

- [What is big O notation](docs/01-big-o-notation.md)
- [Data Structures](docs/02-data-structures-overview.md)
- [Streams vs loops (only if you make the bonus exercises)](docs/03-streams-vs-loops.md)

## Exercises

> **Important:** Carefully read the test outputs, as they contain explanations about the performance of these data
> structures.

- [Lists](./src/main/java/ch/jobtrek/datastructures/linear)
- [Collections](./src/main/java/ch/jobtrek/datastructures/collections)
- [Maps](./src/main/java/ch/jobtrek/datastructures/maps)
- [Sets](./src/main/java/ch/jobtrek/datastructures/sets)
- [Trees](./src/main/java/ch/jobtrek/datastructures/tree)
- [Inspecting performance](./src/main/java/ch/jobtrek/datastructures/benchmarks)

Run the tests with `mvn test`, or directly from your IDE.

## Bonus

More advanced topics, not mandatory.

- [Streams and Gatherers](./src/main/java/ch/jobtrek/datastructures/streams)

# Java for Web Developers

If you have been writing JavaScript, TypeScript, Python, or PHP, you already know how to program. This document
explains what Java looks like compared to those languages, what is different, and why.

## 1. The Same Roots

Java, JavaScript, PHP, and TypeScript all share a **C-style syntax**: curly braces for blocks, semicolons at the
end of statements, `if / else`, `for`, `while`. The structure looks immediately recognizable:

```java
// Java
if (score >= 90) {
    System.out.println("Excellent");
} else if (score >= 75) {
    System.out.println("Good");
} else {
    System.out.println("Keep going");
}
```

Compare with JavaScript:

```javascript
// JavaScript — almost identical
if (score >= 90) {
    console.log("Excellent");
} else if (score >= 75) {
    console.log("Good");
} else {
    console.log("Keep going");
}
```

The main structural differences you will notice are:

- **Semicolons are mandatory**: unlike Python and optional in some JS styles.
- **Parentheses around conditions are mandatory**: unlike Python's `if score >= 90:`.
- **`System.out.println`** is Java's equivalent of `console.log` / `print`.

## 2. Static Typing: The Biggest Shift

The most important difference between Java and the languages you know is that Java is **statically typed**. Every
variable must have a declared type, and that type cannot change.

| Language   | Typing   | Example                               |
|:-----------|:---------|:--------------------------------------|
| Python     | Dynamic  | `name = "Alice"`                      |
| PHP        | Dynamic  | `$name = "Alice";`                    |
| JavaScript | Dynamic  | `let name = "Alice";`                 |
| TypeScript | Static   | `const name: string = "Alice";`       |
| Java       | Static   | `String name = "Alice";`              |

TypeScript is the closest analogy: you annotate types and the compiler catches mistakes before the code runs. Java
works the same way. Types are not optional, they must be declared explicitly or inferred.

Java also offers **type inference** with the `var` keyword (since Java 10), which works like TypeScript's inferred
types:

```java
var name    = "Alice";   // inferred as String
var score   = 95;        // inferred as int
var active  = true;      // inferred as boolean
```

`var` is a convenience, the type is still fixed at compile time. You cannot reassign a `var` variable to a
different type. `var` will work only if there is absolutely no doubt about the type (this is why we call that inference and not deduction).

### Constants

Use `final` to declare a variable that cannot be reassigned. It is the equivalent of `const` in JavaScript/TypeScript:

```java
final int MAX_SIZE = 100;   // Java
// MAX_SIZE = 200;          // compile error
```

```javascript
const MAX_SIZE = 100;       // JavaScript
// MAX_SIZE = 200;          // TypeError at runtime
```

## 3. Primitive Types

Java separates **primitive types** (raw values stored directly in memory) from **reference types** (objects). This
distinction does not exist (in that form) in JavaScript, where all numbers are the same type, and there is no difference between a
character and a string.

| Java type   | JS/TS equivalent              | Notes                           |
|:------------|:------------------------------|:--------------------------------|
| `int`       | `number` (integers up to ~2B) | 32-bit signed integer           |
| `long`      | `number` or `BigInt`          | 64-bit signed integer           |
| `double`    | `number` (floating-point)     | 64-bit float — the default for decimals |
| `boolean`   | `boolean`                     | `true` or `false`               |
| `char`      | A `string` of length 1        | Written with **single quotes**: `'A'` |
| `String`    | `string`                      | An object, but with special syntax |

A common beginner mistake is **integer division**. In JavaScript, `7 / 2` gives `3.5`. In Java, when both operands
are `int`, the result is also an `int` (as int cannot store fractional parts), so the decimal part is discarded silently:

```java
int result = 7 / 2;            // 3 — decimal part is discarded silently!
double result = (double) 7 / 2; // 3.5 — explicit cast to double first
```

## 4. Functions Are Called Methods

In Java, there are no standalone functions. All code lives inside a **class**. When a function belongs to a class and
does not need an instance to be called, it is declared `static`:

```python
# Python
def add(a, b):
    return a + b
```

```php
// PHP
function add($a, $b) {
    return $a + $b;
}
```

```javascript
// JavaScript
function add(a, b) {
    return a + b;
}
```

```java
// Java — lives inside a class
public static int add(int a, int b) {
    return a + b;
}
```

The `public` keyword means the method can be called from anywhere. `static` means it belongs to the class itself, not
to a specific instance. Both the parameter types and the return type (`int`) must be declared explicitly in Java.

## 5. String Formatting

Java does not have template literals. Use **`String.format()`** instead, which works like `printf` in C or PHP:

| Language   | String interpolation                                      |
|:-----------|:----------------------------------------------------------|
| Python     | `f"Hello, {name}! You are {age} years old."`              |
| PHP        | `"Hello, $name! You are $age years old."`                 |
| JavaScript | `` `Hello, ${name}! You are ${age} years old.` ``         |
| Java       | `String.format("Hello, %s! You are %d years old.", name, age)` |

The most common format specifiers are `%s` for strings and `%d` for integers. Use `%.2f` to format a decimal with
two places (e.g. `42.50`).

## 6. Object-Oriented Programming

Java is a **purely object-oriented** language: everything is organized around classes. A class bundles data (fields)
and the methods that operate on that data.

### Encapsulation

In JavaScript and Python, object fields are public by default. In Java, the convention is to make fields **`private`**
and expose them only through **public methods**. This is called **encapsulation**.

```javascript
// JavaScript — field is public, anyone can write to it
class BankAccount {
    constructor(owner, balance) {
        this.balance = balance; // anyone can set account.balance = -999
    }
}
```

```java
// Java — field is private, only the class controls it
class BankAccount {
    private double balance;   // cannot be accessed from outside

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {    // read-only access from outside
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;      // class validates before modifying
        }
    }
}
```

The benefit: the `BankAccount` class guarantees that no one can set the balance to a negative value directly.
External code must go through `deposit()` and `withdraw()`, which enforce the rules.

### The `this` Keyword

`this` works exactly as in JavaScript or PHP, it refers to the current object instance:

```java
public BankAccount(String owner, double initialBalance) {
    this.owner   = owner;          // parameter 'owner' → field 'this.owner'
    this.balance = initialBalance;
}
```

### toString()

Every Java class inherits a `toString()` method from `Object`. By default it returns something like
`BankAccount@7ef88735` — not useful. Override it to control how your object prints:

```java
@Override
public String toString() {
    return String.format("BankAccount[owner=%s, balance=%.2f]", owner, balance);
}
```

The `@Override` annotation tells the compiler you are intentionally replacing the inherited method (it will warn you
if you make a typo in the method name).

## 7. null and NullPointerException

`null` in Java means "no object". Calling any method on a `null` reference throws a **`NullPointerException`** (NPE),
which is the most common runtime error in Java programs:

```java
String s = null;
s.length();   // NullPointerException at runtime!
```

Unlike JavaScript (which returns `undefined` for missing values) or Python (which raises `AttributeError`), Java gives
you no automatic safety net. You must guard against `null` explicitly:

```javascript
// JavaScript — optional chaining short-circuits automatically
const length = text?.length ?? 0;
```

```java
// Java — explicit null check required
int length = (text == null) ? 0 : text.length();
```

**Key Takeaway:** Any time you receive a value that could be `null`, from a method return, a collection lookup, or a
parameter — check it before using it.

## 8. Summary

| Concept          | JavaScript / Python / PHP         | Java                                        |
|:-----------------|:----------------------------------|:--------------------------------------------|
| Variable         | `let x = 5` / `x = 5` / `$x = 5` | `int x = 5;` or `var x = 5;`               |
| Constant         | `const MAX = 5`                   | `final int MAX = 5;`                        |
| Function         | `function add(a, b) { ... }`      | `public static int add(int a, int b) { ... }` |
| String format    | Template literal / f-string       | `String.format("%s %d", name, age)`         |
| Class field      | Public by default                 | `private` by convention                     |
| Missing value    | `undefined` / `None` / `null`     | `null` — throws NPE if not checked          |
| Type checking    | At runtime (JS/Py/PHP)            | At compile time                             |

package ch.jobtrek.basics.oop;

import java.util.Objects;

/**
 * <h1>Module 3 — Object-Oriented Programming Basics</h1>
 *
 * <p>This module introduces the core ideas behind Object-Oriented Programming (OOP)
 * as Java practices it. Even if you have used objects in JavaScript or Python before,
 * Java's approach is more rigid and explicit — and that rigidity is intentional.</p>
 *
 * <h2>Your exercises</h2>
 * <p>This module has two parts:</p>
 * <ol>
 *   <li><strong>Build the class</strong> — complete the {@code BankAccount} class defined
 *       below: fields, constructor, getters, operations, and {@code toString()}.</li>
 *   <li><strong>Use the class</strong> — implement the static methods in
 *       {@code OopExercise} to practise instantiating objects and making them interact.</li>
 * </ol>
 */
public class OopExercise {

    /**
     * <h3>Exercise 5 — Instantiation</h3>
     *
     * <p>Create and return a new {@link BankAccount} using the {@code new} keyword.</p>
     *
     * <p>Instantiating an object in Java works just like in JavaScript and PHP:</p>
     * <pre>
     *   // JavaScript:  new BankAccount(owner, initialBalance)
     *   // PHP:         new BankAccount($owner, $initialBalance)
     *   // Python:      BankAccount(owner, initial_balance)   ← no 'new'
     *   // Java:        new BankAccount(owner, initialBalance)
     * </pre>
     *
     * <p>Each call to {@code new} creates a completely independent object with its own
     * state. Modifying one instance has no effect on any other.</p>
     *
     * @param owner          the account owner's name
     * @param initialBalance the starting balance
     * @return a new {@link BankAccount} instance
     */
    public static BankAccount createAccount(String owner, double initialBalance) {
        // TODO: return a new BankAccount using the 'new' keyword
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 6 — Reading State Through Getters</h3>
     *
     * <p>Return {@code true} if {@code account} has enough balance to cover
     * {@code price}, {@code false} otherwise.</p>
     *
     * <p>This exercise forces you to confront encapsulation directly. You might
     * instinctively try to write {@code account.balance >= price} — but that is a
     * <strong>compile error</strong> because {@code balance} is {@code private}.
     * From outside the class, the only way to read the balance is through the public
     * getter:</p>
     * <pre>
     *   account.getBalance() >= price   // ✓ uses the public API
     *   account.balance >= price        // ✗ compile error: balance has private access
     * </pre>
     *
     * <p>This is encapsulation in practice: external code describes <em>what</em> it
     * wants (the balance value) without touching <em>how</em> it is stored.</p>
     *
     * @param account the account to check
     * @param price   the price to compare against
     * @return {@code true} if the balance covers the price
     */
    public static boolean canAfford(BankAccount account, double price) {
        // TODO: return true if account's balance is >= price
        // Hint: you cannot write account.balance — use the getter instead
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 7 — Comparing Objects Through Their Public API</h3>
     *
     * <p>Given two accounts, return the one with the higher balance. If both balances
     * are equal, return {@code a}.</p>
     *
     * <p>To compare the two accounts, you must read their balances through
     * {@code getBalance()} — neither field is directly accessible from here.
     * This mirrors how you would compare any two objects in Java: through their
     * public interface, not their internal state.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Read both balances with {@code getBalance()}.</li>
     *   <li>Return the account whose balance is greater, or {@code a} if tied.</li>
     * </ol>
     *
     * @param a the first account
     * @param b the second account
     * @return the account with the higher balance (or {@code a} if equal)
     */
    public static BankAccount findRicher(BankAccount a, BankAccount b) {
        // TODO: return the account with the higher balance, or a if they are equal
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * <h3>Exercise 8 — Object Interaction: Transfer</h3>
     *
     * <p>Transfer {@code amount} from {@code source} to {@code target}. Return
     * {@code true} if the transfer succeeded, {@code false} if it did not (e.g.
     * insufficient funds in {@code source}).</p>
     *
     * <p>This exercise shows how objects interact: one method call on {@code source}
     * produces a result that decides whether to call a method on {@code target}.
     * Neither account knows about the other — the coordination happens here.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Call {@code source.withdraw(amount)}. It returns {@code true} on success.</li>
     *   <li>If it succeeded, call {@code target.deposit(amount)}.</li>
     *   <li>Return whether the withdrawal (and therefore the transfer) succeeded.</li>
     * </ol>
     *
     * @param source the account to debit
     * @param target the account to credit
     * @param amount the amount to transfer
     * @return {@code true} if the transfer was completed; {@code false} otherwise
     */
    public static boolean transfer(BankAccount source, BankAccount target, double amount) {
        // TODO: withdraw from source, deposit into target if successful, return the outcome
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }
}

/**
 * A simple bank account demonstrating OOP fundamentals.
 *
 * <h2>Class anatomy across languages</h2>
 * <pre>
 *   // Python
 *   class BankAccount:
 *       def __init__(self, owner, balance):
 *           self.owner   = owner    # public by default in Python
 *           self.balance = balance
 *
 *   // JavaScript
 *   class BankAccount {
 *       constructor(owner, balance) {
 *           this.owner   = owner;   // public by default in JS
 *           this.balance = balance;
 *       }
 *   }
 *
 *   // Java
 *   class BankAccount {
 *       private String owner;       // explicitly private — nothing outside can touch it
 *       private double balance;
 *
 *       public BankAccount(String owner, double balance) {
 *           this.owner   = owner;
 *           this.balance = balance;
 *       }
 *   }
 * </pre>
 *
 * <p>In Java, fields are {@code private} by convention. External code accesses
 * data only through public methods (getters). This is called
 * <strong>encapsulation</strong>: the object controls how its own state is
 * read and modified, preventing invalid states (e.g. a negative balance set
 * directly from outside).</p>
 */
class BankAccount {

    // ---------------------------------------------------------------
    // Exercise 1 — Fields and Constructor
    // ---------------------------------------------------------------

    /**
     * Declare two {@code private} fields:
     * <ul>
     *   <li>{@code owner}   — a {@code String} holding the account owner's name.</li>
     *   <li>{@code balance} — a {@code double} holding the current account balance.</li>
     * </ul>
     *
     * <p>Making them {@code private} means only the code inside this class can read or
     * write them directly. Other classes must go through the public methods below.</p>
     */
    // TODO: declare private String owner
    // TODO: declare private double balance

    /**
     * Constructs a new {@code BankAccount} with the given owner and initial balance.
     *
     * <p>Use the {@code this} keyword to distinguish between the parameter and the
     * field when they share the same name — exactly like in JavaScript:</p>
     * <pre>
     *   // JavaScript:  this.owner = owner;
     *   // Java:        this.owner = owner;   ← identical!
     * </pre>
     *
     * <p>Use {@link java.util.Objects#requireNonNull} to validate that {@code owner}
     * is not {@code null}. This is the standard Java idiom for rejecting invalid
     * arguments at the earliest possible point:</p>
     * <pre>
     *   this.owner = Objects.requireNonNull(owner, "owner must not be null");
     * </pre>
     * <p>If {@code owner} is {@code null}, it throws a {@link NullPointerException}
     * with your message — much clearer than a silent NPE later in unrelated code.</p>
     *
     * @param owner          the name of the account owner (must not be {@code null})
     * @param initialBalance the starting balance (must be &gt;= 0)
     * @throws NullPointerException     if {@code owner} is {@code null}
     * @throws IllegalArgumentException if {@code initialBalance} is negative
     */
    public BankAccount(String owner, double initialBalance) {
        // TODO: validate and initialize this.owner and this.balance
        // - use Objects.requireNonNull(owner, "owner must not be null")
        // - throw IllegalArgumentException if initialBalance < 0
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Encapsulation: Getters
    // ---------------------------------------------------------------

    /**
     * Returns the account owner's name.
     *
     * <p>Since {@code owner} is {@code private}, the only way for external code to
     * read it is through this public method. The field is intentionally read-only:
     * there is no {@code setOwner()} because an account owner should not change.</p>
     *
     * @return the account owner's name
     */
    public String getOwner() {
        // TODO: return the owner field
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * Returns the current balance.
     *
     * @return the account balance
     */
    public double getBalance() {
        // TODO: return the balance field
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Operations: deposit and withdraw
    // ---------------------------------------------------------------

    /**
     * Deposits money into the account.
     *
     * <p><strong>Rule:</strong> the amount must be strictly positive. If it is not,
     * throw an {@link IllegalArgumentException} to signal invalid input:</p>
     * <pre>
     *   if (amount &lt;= 0) {
     *       throw new IllegalArgumentException("Deposit amount must be positive");
     *   }
     * </pre>
     * <p>This is how Java signals that a method received an invalid argument —
     * similar to {@code throw new Error("...")} in JavaScript.</p>
     *
     * <p><strong>Steps:</strong></p>
     * <ol>
     *   <li>Guard: throw {@link IllegalArgumentException} if {@code amount <= 0}.</li>
     *   <li>Add {@code amount} to {@code balance}.</li>
     * </ol>
     *
     * @param amount the amount to deposit (must be &gt; 0)
     * @throws IllegalArgumentException if {@code amount} is zero or negative
     */
    public void deposit(double amount) {
        // TODO: validate amount > 0, then add it to balance
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    /**
     * Withdraws money from the account.
     *
     * <p>Returns {@code true} if the withdrawal succeeded, {@code false} otherwise.
     * This boolean return pattern is a clean alternative to throwing exceptions for
     * expected business conditions (insufficient funds is not a programming error).</p>
     *
     * <p><strong>Rules:</strong></p>
     * <ul>
     *   <li>If {@code amount <= 0}: return {@code false}.</li>
     *   <li>If {@code amount > balance}: return {@code false} (insufficient funds).</li>
     *   <li>Otherwise: subtract {@code amount} from {@code balance} and return
     *       {@code true}.</li>
     * </ul>
     *
     * @param amount the amount to withdraw
     * @return {@code true} if the withdrawal succeeded; {@code false} otherwise
     */
    public boolean withdraw(double amount) {
        // TODO: implement the withdrawal logic described above
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }

    // ---------------------------------------------------------------
    // Exercise 4 — toString()
    // ---------------------------------------------------------------

    /**
     * Returns a human-readable string representation of this account.
     *
     * <p>Every Java class inherits a {@code toString()} method from {@link Object}.
     * The default output looks like {@code BankAccount@7ef88735} (class name + memory
     * address) — not useful. Override it with {@code @Override} to return something
     * meaningful.</p>
     *
     * <p>Expected format:</p>
     * <pre>
     *   BankAccount[owner=Alice, balance=1000.00]
     * </pre>
     *
     * <p>Use {@link String#format} with {@code %.2f} to format the balance to exactly
     * two decimal places.</p>
     *
     * @return a formatted string describing this account
     */
    @Override
    public String toString() {
        // TODO: return "BankAccount[owner=<owner>, balance=<balance with 2 decimals>]"
        throw new UnsupportedOperationException("TODO: replace me with your solution !");
    }
}

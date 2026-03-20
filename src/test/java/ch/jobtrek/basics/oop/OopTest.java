package ch.jobtrek.basics.oop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Module 3 — Object-Oriented Programming Basics.
 */
@DisplayName("Module 3 — Object-Oriented Programming Basics")
class OopTest {

    // ---------------------------------------------------------------
    // Exercise 1 — Fields and Constructor
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 1: Constructor and Fields")
    class ConstructorTests {

        @Test
        @DisplayName("A newly created account should store the owner and initial balance")
        void shouldStoreOwnerAndBalance() {
            BankAccount account = new BankAccount("Alice", 500.0);

            assertThat(account.getOwner()).isEqualTo("Alice");
            assertThat(account.getBalance()).isEqualTo(500.0);
        }

        @Test
        @DisplayName("Two accounts should have independent state")
        void accountsShouldBeIndependent() {
            BankAccount a = new BankAccount("Alice", 100.0);
            BankAccount b = new BankAccount("Bob", 200.0);

            assertThat(a.getOwner()).isEqualTo("Alice");
            assertThat(b.getOwner()).isEqualTo("Bob");
            assertThat(a.getBalance()).isNotEqualTo(b.getBalance());
        }

        @Test
        @DisplayName("Should allow an initial balance of zero")
        void shouldAllowZeroBalance() {
            BankAccount account = new BankAccount("Charlie", 0.0);

            assertThat(account.getBalance()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should throw NullPointerException when owner is null")
        void shouldRejectNullOwner() {
            assertThatThrownBy(() -> new BankAccount(null, 100.0))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when initial balance is negative")
        void shouldRejectNegativeInitialBalance() {
            assertThatThrownBy(() -> new BankAccount("Alice", -50.0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 2 — Encapsulation: Getters
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 2: getOwner() and getBalance()")
    class GetterTests {

        @Test
        @DisplayName("getOwner() should return the owner's name")
        void shouldReturnOwner() {
            BankAccount account = new BankAccount("Diana", 0.0);

            assertThat(account.getOwner()).isEqualTo("Diana");
        }

        @Test
        @DisplayName("getBalance() should return the exact initial balance")
        void shouldReturnBalance() {
            BankAccount account = new BankAccount("Eve", 1250.75);

            assertThat(account.getBalance()).isEqualTo(1250.75);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 3 — Operations: deposit and withdraw
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 3: deposit() and withdraw()")
    class OperationsTests {

        @Test
        @DisplayName("deposit() should increase the balance by the given amount")
        void shouldIncreaseBalanceOnDeposit() {
            BankAccount account = new BankAccount("Alice", 100.0);

            account.deposit(50.0);

            assertThat(account.getBalance()).isEqualTo(150.0);
        }

        @Test
        @DisplayName("deposit() should throw IllegalArgumentException for a zero amount")
        void shouldRejectZeroDeposit() {
            BankAccount account = new BankAccount("Alice", 100.0);

            assertThatThrownBy(() -> account.deposit(0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("deposit() should throw IllegalArgumentException for a negative amount")
        void shouldRejectNegativeDeposit() {
            BankAccount account = new BankAccount("Alice", 100.0);

            assertThatThrownBy(() -> account.deposit(-10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("withdraw() should decrease the balance and return true on success")
        void shouldDecreaseBalanceOnWithdraw() {
            BankAccount account = new BankAccount("Alice", 100.0);

            boolean result = account.withdraw(40.0);

            assertThat(result).isTrue();
            assertThat(account.getBalance()).isEqualTo(60.0);
        }

        @Test
        @DisplayName("withdraw() should return false and not change the balance for insufficient funds")
        void shouldReturnFalseForInsufficientFunds() {
            BankAccount account = new BankAccount("Alice", 100.0);

            boolean result = account.withdraw(200.0);

            assertThat(result).isFalse();
            assertThat(account.getBalance()).isEqualTo(100.0);
        }

        @Test
        @DisplayName("withdraw() should return false for a zero or negative amount")
        void shouldReturnFalseForNonPositiveWithdrawal() {
            BankAccount account = new BankAccount("Alice", 100.0);

            assertThat(account.withdraw(0)).isFalse();
            assertThat(account.withdraw(-10)).isFalse();
            assertThat(account.getBalance()).isEqualTo(100.0);
        }

        @Test
        @DisplayName("withdraw() should allow withdrawing the full balance")
        void shouldAllowFullWithdrawal() {
            BankAccount account = new BankAccount("Alice", 100.0);

            boolean result = account.withdraw(100.0);

            assertThat(result).isTrue();
            assertThat(account.getBalance()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Multiple operations should compose correctly")
        void shouldChainOperations() {
            BankAccount account = new BankAccount("Alice", 100.0);

            account.deposit(50.0);           // 150
            account.withdraw(30.0);          // 120
            account.deposit(10.0);           // 130

            assertThat(account.getBalance()).isEqualTo(130.0);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 4 — toString()
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 4: toString()")
    class ToStringTests {

        @Test
        @DisplayName("toString() should return the expected format")
        void shouldReturnFormattedString() {
            BankAccount account = new BankAccount("Alice", 1000.0);

            assertThat(account.toString())
                    .isEqualTo("BankAccount[owner=Alice, balance=1000.00]");
        }

        @Test
        @DisplayName("toString() should format the balance to two decimal places")
        void shouldFormatDecimalBalance() {
            BankAccount account = new BankAccount("Bob", 42.5);

            assertThat(account.toString())
                    .isEqualTo("BankAccount[owner=Bob, balance=42.50]");
        }

        @Test
        @DisplayName("toString() should reflect updated balance after operations")
        void shouldReflectCurrentBalance() {
            BankAccount account = new BankAccount("Carol", 200.0);
            account.deposit(50.0);

            assertThat(account.toString())
                    .isEqualTo("BankAccount[owner=Carol, balance=250.00]");
        }
    }

    // ---------------------------------------------------------------
    // Exercise 5 — Instantiation
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 5: createAccount()")
    class CreateAccountTests {

        @Test
        @DisplayName("Should create an account with the correct owner and balance")
        void shouldCreateAccountWithCorrectState() {
            BankAccount account = OopExercise.createAccount("Alice", 500.0);

            assertThat(account.getOwner()).isEqualTo("Alice");
            assertThat(account.getBalance()).isEqualTo(500.0);
        }

        @Test
        @DisplayName("Each call should return a new independent instance")
        void eachCallShouldReturnANewInstance() {
            BankAccount first  = OopExercise.createAccount("Alice", 100.0);
            BankAccount second = OopExercise.createAccount("Bob",   200.0);

            // Modifying one account must not affect the other
            first.deposit(50.0);

            assertThat(first.getBalance()).isEqualTo(150.0);
            assertThat(second.getBalance()).isEqualTo(200.0);
        }

        @Test
        @DisplayName("Two accounts created with the same arguments should be distinct objects")
        void shouldNotReturnTheSameInstance() {
            BankAccount a = OopExercise.createAccount("Alice", 100.0);
            BankAccount b = OopExercise.createAccount("Alice", 100.0);

            assertThat(a).isNotSameAs(b);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 7 — Reading State Through Getters
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 7: canAfford()")
    class CanAffordTests {

        @Test
        @DisplayName("Should return true when balance exactly covers the price")
        void shouldReturnTrueWhenBalanceCoversExactly() {
            BankAccount account = OopExercise.createAccount("Alice", 100.0);

            assertThat(OopExercise.canAfford(account, 100.0)).isTrue();
        }

        @Test
        @DisplayName("Should return true when balance exceeds the price")
        void shouldReturnTrueWhenBalanceExceedsPrice() {
            BankAccount account = OopExercise.createAccount("Alice", 200.0);

            assertThat(OopExercise.canAfford(account, 50.0)).isTrue();
        }

        @Test
        @DisplayName("Should return false when balance is insufficient")
        void shouldReturnFalseWhenBalanceIsInsufficient() {
            BankAccount account = OopExercise.createAccount("Alice", 30.0);

            assertThat(OopExercise.canAfford(account, 100.0)).isFalse();
        }

        @Test
        @DisplayName("Should reflect updated balance after a deposit")
        void shouldReflectUpdatedBalance() {
            BankAccount account = OopExercise.createAccount("Alice", 50.0);
            assertThat(OopExercise.canAfford(account, 100.0)).isFalse();

            account.deposit(60.0);

            assertThat(OopExercise.canAfford(account, 100.0)).isTrue();
        }
    }

    // ---------------------------------------------------------------
    // Exercise 8 — Comparing Objects Through Their Public API
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 8: findRicher()")
    class FindRicherTests {

        @Test
        @DisplayName("Should return the account with the higher balance")
        void shouldReturnTheRicherAccount() {
            BankAccount alice = OopExercise.createAccount("Alice", 500.0);
            BankAccount bob   = OopExercise.createAccount("Bob",   200.0);

            assertThat(OopExercise.findRicher(alice, bob)).isSameAs(alice);
            assertThat(OopExercise.findRicher(bob, alice)).isSameAs(alice);
        }

        @Test
        @DisplayName("Should return 'a' when both balances are equal")
        void shouldReturnFirstAccountWhenEqual() {
            BankAccount alice = OopExercise.createAccount("Alice", 100.0);
            BankAccount bob   = OopExercise.createAccount("Bob",   100.0);

            assertThat(OopExercise.findRicher(alice, bob)).isSameAs(alice);
        }

        @Test
        @DisplayName("Should return the correct account after balances change")
        void shouldReflectCurrentBalances() {
            BankAccount alice = OopExercise.createAccount("Alice", 100.0);
            BankAccount bob   = OopExercise.createAccount("Bob",   300.0);

            assertThat(OopExercise.findRicher(alice, bob)).isSameAs(bob);

            alice.deposit(500.0);   // Alice is now richer

            assertThat(OopExercise.findRicher(alice, bob)).isSameAs(alice);
        }
    }

    // ---------------------------------------------------------------
    // Exercise 6 — Object Interaction: Transfer
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Exercise 6: transfer()")
    class TransferTests {

        @Test
        @DisplayName("Should debit the source and credit the target on success")
        void shouldMoveMoneyBetweenAccounts() {
            BankAccount alice = OopExercise.createAccount("Alice", 200.0);
            BankAccount bob   = OopExercise.createAccount("Bob",     0.0);

            boolean result = OopExercise.transfer(alice, bob, 75.0);

            assertThat(result).isTrue();
            assertThat(alice.getBalance()).isEqualTo(125.0);
            assertThat(bob.getBalance()).isEqualTo(75.0);
        }

        @Test
        @DisplayName("Should return false and leave both balances unchanged when funds are insufficient")
        void shouldFailWithoutAffectingEitherAccount() {
            BankAccount alice = OopExercise.createAccount("Alice",  50.0);
            BankAccount bob   = OopExercise.createAccount("Bob",   100.0);

            boolean result = OopExercise.transfer(alice, bob, 200.0);

            assertThat(result).isFalse();
            assertThat(alice.getBalance()).isEqualTo(50.0);   // unchanged
            assertThat(bob.getBalance()).isEqualTo(100.0);    // unchanged
        }

        @Test
        @DisplayName("Should allow transferring the full balance")
        void shouldAllowTransferringFullBalance() {
            BankAccount alice = OopExercise.createAccount("Alice", 100.0);
            BankAccount bob   = OopExercise.createAccount("Bob",     0.0);

            boolean result = OopExercise.transfer(alice, bob, 100.0);

            assertThat(result).isTrue();
            assertThat(alice.getBalance()).isEqualTo(0.0);
            assertThat(bob.getBalance()).isEqualTo(100.0);
        }

        @Test
        @DisplayName("Should support chained transfers across multiple accounts")
        void shouldSupportChainedTransfers() {
            BankAccount alice   = OopExercise.createAccount("Alice",   300.0);
            BankAccount bob     = OopExercise.createAccount("Bob",       0.0);
            BankAccount charlie = OopExercise.createAccount("Charlie",   0.0);

            OopExercise.transfer(alice, bob,     100.0);   // Alice→Bob
            OopExercise.transfer(bob,   charlie,  60.0);   // Bob→Charlie

            assertThat(alice.getBalance()).isEqualTo(200.0);
            assertThat(bob.getBalance()).isEqualTo(40.0);
            assertThat(charlie.getBalance()).isEqualTo(60.0);
        }
    }
}

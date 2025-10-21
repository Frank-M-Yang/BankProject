/**
 * Account Class Hierarchy
 *
 * Abstract base class for different bank account types with specific withdrawal rules:
 * - GreenStandardAccount: No overdraft allowed (balance must remain >= 0)
 * - BlackPremiumAccount: Limited overdraft up to -4000
 * - GoldUnlimitedAccount: Unlimited overdraft allowed
 *
 * Each account type implements its own withdrawal permission logic while sharing
 * common deposit/withdrawal functionality from the base class.
 */

public abstract class Account {
    protected String accountNumber; //protected can be accessed by child classes
    protected double balance;
    protected String accountType;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    abstract boolean withdrawPermission(double amount);
    abstract String getAccountType();

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && withdrawPermission(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("Account: %s | Type: %s | Balance: €%.2f",
                accountNumber, accountType, balance);
    }
}

class GreenStandardAccount extends Account {
    public GreenStandardAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
        this.accountType = "Green Standard";
    }

    @Override
    public boolean withdrawPermission(double amount) {
        return balance >= amount;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }
}

class BlackPremiumAccount extends Account {
    private static final double MAX_NEGATIVE_BALANCE = -4000.0;

    public BlackPremiumAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
        this.accountType = "Black Premium";
    }

    @Override
    public boolean withdrawPermission(double amount) {
        return amount > 0 && (balance - amount) >= MAX_NEGATIVE_BALANCE;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }
}

class GoldUnlimitedAccount extends Account {
    public GoldUnlimitedAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
        this.accountType = "Gold Unlimited";
    }

    @Override
    public boolean withdrawPermission(double amount) {
        return amount > 0;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }
}
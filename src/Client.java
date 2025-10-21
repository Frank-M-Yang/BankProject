/**
 * Client Class
 *
 * Represents a bank client with authentication and account management.
 * Each client has a unique ID, secure PIN, and can hold multiple accounts.
 *
 * Features:
 * - PIN validation (exactly 4 digits)
 * - Secure PIN verification
 * - Multiple account management
 * - Account lookup by account number
 *
 * Security:
 * - PIN is validated during client creation
 * - PIN verification for authentication
 * - Returns copy of accounts list to prevent external modification
 */

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String clientID;
    private String pin;
    private String name;
    private List<Account> accounts;

    public Client (String clientID, String pin, String name) {
        if (!isValidPin(pin)) {
            throw new IllegalArgumentException("PIN must be 4 digits");
        }
        this.clientID = clientID;
        this.pin = pin;
        this.name = name;
        this.accounts = new ArrayList<>();
    }


    private static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    // Getters
    public String getClientID() { return clientID; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Client: %s (ID: %s) - %d account(s)",
                name, clientID, accounts.size());
    }
}


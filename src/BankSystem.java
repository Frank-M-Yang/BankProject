/**
 * BankSystem Class
 *
 * Core banking system that manages client authentication and financial operations.
 * Handles client login/logout, account management, and transaction processing.
 *
 * Key Features:
 * - Secure PIN-based client authentication
 * - Account overview and balance display
 * - Funds transfer between accounts
 * - Deposit and withdrawal operations
 * - Automatic logout management
 *
 * Security:
 * - Validates PIN format (exactly 4 digits)
 * - Ensures client authorization before transactions
 * - Auto-logout when new client logs in
 */

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class BankSystem {
    private Map<String, Client> clients;
    private Client loggedInClient;

    public BankSystem() {
        this.clients = new HashMap<String, Client>();
        this.loggedInClient = null;
    }

    public void addClient(Client client) {
        clients.put(client.getClientID(), client);
    }

    public boolean login(String clientId, String pin) {
        // Check if clientId is empty
        if (clientId == null || clientId.trim().isEmpty()) {
            System.out.println("Login failed! Client ID cannot be empty.");
            return false;
        }

        // Check if clientId exists
        if (!clients.containsKey(clientId)) {
            System.out.println("Login failed! Client ID does not exist.");
            return false;
        }

        // Check PIN format
        if (pin == null || pin.length() != 4) {
            System.out.println("Login failed! PIN must be 4 digits.");
            return false;
        }
        for (int i = 0; i < pin.length(); i++) {
            if (!Character.isDigit(pin.charAt(i))) {
                System.out.println("Login failed! PIN must be 4 digits.");
                return false;
            }
        }

        // Verify PIN
        Client client = clients.get(clientId);
        if (client.verifyPin(pin)) {

            if (loggedInClient != null) {
                System.out.println("Auto-logging out: " + loggedInClient.getName());
            }
            loggedInClient = client;
            System.out.println("Login successful! Welcome " + client.getName());
            return true;
        } else {
            System.out.println("Login failed! Invalid PIN.");
            return false;
        }
    }

    public void logout() {
        if (loggedInClient != null) {
            System.out.println("Logging out: " + loggedInClient.getName());
            loggedInClient = null;
        } else {
            System.out.println("No client is currently logged in.");
        }
    }

    public void showAccountOverview() {
        if (!isClientLoggedIn()) {
            return;
        }

        System.out.println("\n=== Account Overview for " + loggedInClient.getName() + " ===");
        List<Account> accounts = loggedInClient.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println((i + 1) + ". " + accounts.get(i));
            }
        }
        System.out.println("=================================");
    }

    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (!isClientLoggedIn()) {
            return false;
        }

        Account fromAccount = loggedInClient.getAccount(fromAccountNumber);
        if (fromAccount == null) {
            System.out.println("Source account not found!");
            return false;
        }

        Account toAccount = findAccount(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Destination account not found!");
            return false;
        }

        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println("Transfer successful! €" + amount + " transferred from " + fromAccountNumber + " to " + toAccountNumber);
            return true;
        } else {
            System.out.println("Transfer failed! Insufficient funds or invalid amount.");
            return false;
        }
    }

    public boolean deposit(String accountNumber, double amount) {
        if (!isClientLoggedIn()) {
            return false;
        }

        Account account = loggedInClient.getAccount(accountNumber);
        if (account != null && account.deposit(amount)) {
            System.out.println("Deposit successful! New balance: €" + account.getBalance());
            return true;
        }
        System.out.println("Deposit failed!");
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        if (!isClientLoggedIn()) {
            return false;
        }

        Account account = loggedInClient.getAccount(accountNumber);
        if (account != null && account.withdraw(amount)) {
            System.out.println("Withdrawal successful! New balance: €" + account.getBalance());
            return true;
        }
        System.out.println("Withdrawal failed! Insufficient funds or invalid amount.");
        return false;
    }

    private Account findAccount(String accountNumber) {
        for (Client client : clients.values()) {
            Account account = client.getAccount(accountNumber);
            if (account != null) {
                return account;
            }
        }
        return null;
    }

    private boolean isClientLoggedIn() {
        if (loggedInClient == null) {
            System.out.println("No client is logged in. Please login first.");
            return false;
        }
        return true;
    }

    public boolean clientExists(String clientId) {
        return clients.containsKey(clientId);
    }

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public boolean isLoggedIn() {
        return loggedInClient != null;
    }
}
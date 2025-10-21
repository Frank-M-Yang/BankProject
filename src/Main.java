/**
 * Bank System Demo
 *
 * Features:
 * - Client authentication with PIN
 * - Multiple account types with different limits
 * - Deposit/withdrawal/transfer operations
 * - Account balance management
 */

public class Main {
    public static void main(String[] args) {
        // Initialize bank system
        BankSystem bank = new BankSystem();

        // Create clients with PIN validation
        Client client1 = new Client("C001", "1234", "Tommy Shelby");
        Client client2 = new Client("C002", "5678", "Grace Burgess");
        Client client3 = new Client("C003", "9999", "Polly Gray");

        // Create accounts for clients
        client1.addAccount(new GreenStandardAccount("BE0001", 1000.0));
        client1.addAccount(new BlackPremiumAccount("BE0002", 500.0));

        client2.addAccount(new GoldUnlimitedAccount("BE0003", 2000.0));
        client2.addAccount(new GreenStandardAccount("BE0004", 300.0));

        client3.addAccount(new BlackPremiumAccount("BE0005", 1500.0));

        // Add clients to bank
        bank.addClient(client1);
        bank.addClient(client2);
        bank.addClient(client3);

        // Demo scenarios
        System.out.println("=== Bank System Demo ===\n");

        // Scenario 1: Testing PIN Authentication - Valid Login
        System.out.println("1. Testing valid PIN login:");
        bank.login("C001", "1234");
        bank.showAccountOverview();

        // Scenario 2: Testing PIN Verification - Invalid PIN
        System.out.println("\n2. Testing invalid PIN:");
        bank.login("C001", "9999"); // Wrong PIN
        bank.login("C001", "123");  // Too short
        bank.login("C001", "12345"); // Too long
        bank.login("C001", "12a4");  // Contains letters

        // Scenario 3: Normal Operations
        System.out.println("\n3. Normal Operations:");
        bank.login("C002", "5678"); // Login with client2
        bank.showAccountOverview();
        bank.deposit("BE0003", 500.0);
        bank.withdraw("BE0003", 100.0);
        bank.showAccountOverview(); // Show updated balances

        // Scenario 4: Testing Account Type Restrictions
        System.out.println("\n4. Testing account type limits:");
        bank.login("C001", "1234"); // Login with client1
        System.out.println("--- Testing Green Standard Account ---");
        bank.withdraw("BE0001", 1500.0); // Should fail (cannot go negative)
        System.out.println("--- Testing Black Premium Account ---");
        bank.withdraw("BEO002", 3000.0); // Should succeed (within -4000 limit)
        bank.showAccountOverview();

        // Scenario 5: Transfer Test
        System.out.println("\n5. Transfer test:");
        bank.transfer("BE0002", "BE0003", 500.0); // Transfer from client1 to client2
        bank.showAccountOverview();

        // Scenario 6: Final demonstration
        System.out.println("\n6. Testing Gold Unlimited Account:");
        bank.login("C002", "5678");
        bank.withdraw("BE0003", 10000.0); // Should succeed (unlimited negative)
        bank.showAccountOverview();

        // Final logout
        System.out.println("\n7. Final logout:");
        bank.logout();

        System.out.println("\n=== Demo Completed ===");
    }
}

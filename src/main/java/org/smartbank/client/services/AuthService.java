package org.smartbank.client.services;

import org.smartbank.client.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthService {

    private final Map<String, String> userPasswords; // Map for TCKN -> Password
    private final Map<String, User> userDatabase; // Map for TCKN -> User object

    public AuthService() {
        userPasswords = new HashMap<>();
        userDatabase = new HashMap<>();

        // Pre-populate with fake data
        userDatabase.put("12345678901", new User("ACC123456", 5000.00, "user1"));
        userPasswords.put("12345678901", "password123");

        userDatabase.put("98765432101", new User("ACC987654", 2000.00, "user2"));
        userPasswords.put("98765432101", "password987");
    }

    private void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted!");
        }
    }

    public User login(String tckn, String password) {
        simulateDelay();
        if (userPasswords.containsKey(tckn) && userPasswords.get(tckn).equals(password)) {
            System.out.println("Login successful for TCKN: " + tckn + " (Simulated)");
            return userDatabase.get(tckn);
        }
        System.out.println("Login failed: Invalid credentials (Simulated)");
        return null;
    }
}

package org.smartbank.client.services;

import org.smartbank.client.model.User;
import java.util.concurrent.TimeUnit;

public class UserService {

    private void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted!");
        }
    }

    public User getUserDetails(String userId) {
        simulateDelay();
        // Simulating fetching user details from a database
        return new User("123456789", 5000.00, userId);
    }

    public User deposit(String userId, double amount) {
        simulateDelay();
        // Simulating deposit operation
        double newBalance = 5000.00 + amount; // Fake balance update
        return new User("123456789", newBalance, userId);
    }

    public User withdraw(String userId, double amount) {
        simulateDelay();
        // Simulating withdrawal operation
        double newBalance = 5000.00 - amount; // Fake balance update
        if (newBalance < 0) {
            System.out.println("Simulated: Insufficient funds.");
            return null; // Indicate the failure
        }
        return new User("123456789", newBalance, userId);
    }
}

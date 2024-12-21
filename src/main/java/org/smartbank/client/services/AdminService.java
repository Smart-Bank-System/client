package org.smartbank.client.services;

import org.smartbank.client.model.User;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminService {

    private void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted!");
        }
    }

    public List<User> viewAllUsers() {
        simulateDelay();
        // Returning fake user list
        List<User> users = new ArrayList<>();
        users.add(new User("111111111", 1000.00, "U1001"));
        users.add(new User("222222222", 2000.00, "U1002"));
        users.add(new User("333333333", 3000.00, "U1003"));
        return users;
    }
}

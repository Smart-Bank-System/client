package org.smartbank.client.controller;

import org.smartbank.client.model.User;

public class CustomerHomeController {
    private User currentUser;

    public void initializeUser(User user) {
        this.currentUser = user;
        System.out.println("Logged in user: " + user);
        // Update the UI with user-specific information
    }
}
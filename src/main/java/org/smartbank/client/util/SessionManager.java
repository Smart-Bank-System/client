package org.smartbank.client.util;

import org.smartbank.client.model.Transaction;
import org.smartbank.client.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private Transaction recentTransaction;

    private SessionManager() {
        // Private constructor for Singleton
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Transaction getRecentTransaction() {
        return recentTransaction;
    }

    public void setRecentTransaction(Transaction transaction) {
        this.recentTransaction = transaction;
    }

    public void clearSession() {
        this.currentUser = null;
        this.recentTransaction = null;
    }
}

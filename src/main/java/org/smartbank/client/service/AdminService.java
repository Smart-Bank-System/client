package org.smartbank.client.service;

import org.smartbank.client.model.Admin;
import org.smartbank.client.model.Transaction;
import org.smartbank.client.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private static AdminService instance;

    private AdminService() {
        // Private constructor for singleton
    }

    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    // Fetch new account requests for the admin's preferred bank
    public List<User> getNewAccountRequests(Admin admin) {
        List<User> pendingUsers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE preferred_bank = ? AND status = 'PENDING'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, admin.getPreferredBank());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("tckn"),
                            resultSet.getString("account_number"),
                            resultSet.getString("password"),
                            resultSet.getDouble("balance"),
                            resultSet.getString("preferred_bank")
                    );
                    pendingUsers.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingUsers;
    }

    // Approve or deny a new account request
    public void updateUserStatus(int userId, String status) {
        String query = "UPDATE users SET status = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch transaction history for the admin's preferred bank
    public List<Transaction> getTransactionHistory(Admin admin) {
        List<Transaction> transactions = new ArrayList<>();
        String query = """
                SELECT * FROM transactions 
                WHERE from_user_id IN (SELECT user_id FROM users WHERE preferred_bank = ?)
                   OR to_user_id IN (SELECT user_id FROM users WHERE preferred_bank = ?)
                ORDER BY date DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, admin.getPreferredBank());
            statement.setString(2, admin.getPreferredBank());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(
                            resultSet.getInt("transaction_id"),
                            resultSet.getString("transaction_type"),
                            resultSet.getDouble("amount"),
                            resultSet.getInt("from_user_id"),
                            resultSet.getInt("to_user_id"),
                            resultSet.getTimestamp("date").toLocalDateTime()
                    );
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

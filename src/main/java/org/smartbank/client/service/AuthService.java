package org.smartbank.client.service;

import org.smartbank.client.model.Admin;
import org.smartbank.client.model.User;

import java.sql.*;
import java.util.Random;

public class AuthService {

    // Register a new user with TCKN and password
    public boolean register(String tckn, String password) {
        String query = "INSERT INTO users (tckn, account_number, password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Generate a unique account number
            String accountNumber = generateUniqueAccountNumber(connection);

            statement.setString(1, tckn);
            statement.setString(2, accountNumber);
            statement.setString(3, password);  // Store password directly (no hashing)

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;  // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if an error occurred
        }
    }

    // Authenticate a user by TCKN and password
    public User login(String tckn, String password) {
        String query = "SELECT user_id, tckn, account_number, balance, password FROM users WHERE tckn = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tckn);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    // Verify the entered password matches the stored password
                    if (password.equals(storedPassword)) {
                        int userId = resultSet.getInt("user_id");
                        String accountNumber = resultSet.getString("account_number");
                        double balance = resultSet.getDouble("balance");

                        return new User(userId, tckn, accountNumber, balance);  // Return authenticated user
                    } else {
                        System.out.println("Invalid password.");
                    }
                } else {
                    System.out.println("User not found.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if authentication fails
    }

    public Admin loginAdmin(String tckn, String password) {
        String query = "SELECT admin_id, tckn, password FROM admins WHERE tckn = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tckn);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    // Verify the entered password matches the stored password
                    if (password.equals(storedPassword)) {
                        int adminId = resultSet.getInt("admin_id");
                        return new Admin(adminId, tckn);  // Return authenticated admin
                    } else {
                        System.out.println("Invalid password.");
                    }
                } else {
                    System.out.println("Admin not found.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if authentication fails
    }


    // Generate a random account number
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("TR");
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));  // Append a random digit
        }
        return accountNumber.toString();
    }

    // Ensure the account number is unique in the database
    private String generateUniqueAccountNumber(Connection connection) throws SQLException {
        String accountNumber;
        String query = "SELECT COUNT(*) FROM users WHERE account_number = ?";
        do {
            accountNumber = generateAccountNumber();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, accountNumber);
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    if (resultSet.getInt(1) == 0) {
                        break;  // Account number is unique
                    }
                }
            }
        } while (true);
        return accountNumber;
    }
}

package org.smartbank.client.service;

import org.smartbank.client.model.Admin;
import org.smartbank.client.model.User;

import java.sql.*;
import java.util.Random;

public class AuthService {

    public boolean register(String fullname, String tckn, String password, String preferredBank) {
        System.out.println("Register with fullname");
        String query = "INSERT INTO users (fullname, tckn, account_number, password, status, preferred_bank) " +
                "VALUES (?, ?, ?, ?, 'PENDING', ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Generate a unique account number
            String accountNumber = generateUniqueAccountNumber(connection);

            // Set parameters in order
            statement.setString(1, fullname);
            statement.setString(2, tckn);
            statement.setString(3, accountNumber);
            statement.setString(4, password);
            statement.setString(5, preferredBank);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User login(String tckn, String password) {
        String query = "SELECT user_id, fullname, tckn, account_number, balance, status, preferred_bank " +
                "FROM users WHERE tckn = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tckn);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    if ("CONFIRMED".equalsIgnoreCase(status)) {
                        int userId = resultSet.getInt("user_id");
                        String fullname = resultSet.getString("fullname");
                        String accountNumber = resultSet.getString("account_number");
                        double balance = resultSet.getDouble("balance");
                        String preferredBank = resultSet.getString("preferred_bank");

                        return new User(userId, fullname, tckn, accountNumber, balance, preferredBank);
                    } else {
                        System.out.println("Account is not confirmed.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Admin loginAdmin(String tckn, String password) {
        String query = "SELECT admin_id, tckn, password, preferred_bank FROM admins WHERE tckn = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tckn);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    if (password.equals(storedPassword)) {
                        int adminId = resultSet.getInt("admin_id");
                        String preferredBank = resultSet.getString("preferred_bank");

                        return new Admin(adminId, tckn, preferredBank);
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
        return null;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("TR");
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

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
                        break;
                    }
                }
            }
        } while (true);
        return accountNumber;
    }
}

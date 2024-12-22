package org.smartbank.client.service;

import org.smartbank.client.model.Transaction;
import org.smartbank.client.model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class UserService {

    // Singleton instance
    private static UserService instance;
    private final TransactionService transactionService = TransactionService.getInstance();

    // Private constructor to enforce singleton
    private UserService() {
    }

    // Get the singleton instance
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    // Deposit money
    public boolean deposit(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return false;
        }

        User user = getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }

        double newBalance = user.getBalance() + amount;

        if (updateUserBalance(userId, newBalance)) {
            Transaction transaction = new Transaction(
                    "DEPOSIT",
                    amount,
                    null,
                    userId,
                    LocalDateTime.now()
            );
            return transactionService.addTransaction(transaction);
        }
        return false;
    }

    // Withdraw money
    public boolean withdraw(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        User user = getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }

        if (user.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return false;
        }

        double newBalance = user.getBalance() - amount;

        if (updateUserBalance(userId, newBalance)) {
            Transaction transaction = new Transaction(
                    "WITHDRAWAL",
                    amount,
                    userId,
                    null,
                    LocalDateTime.now()
            );
            return transactionService.addTransaction(transaction);
        }
        return false;
    }

    // Transfer money
    public boolean transfer(int fromUserId, int toUserId, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return false;
        }

        User fromUser = getUserById(fromUserId);
        User toUser = getUserById(toUserId);

        if (fromUser == null || toUser == null) {
            System.out.println("User(s) not found.");
            return false;
        }

        if (fromUser.getBalance() < amount) {
            System.out.println("Insufficient balance.");
            return false;
        }

        double newFromBalance = fromUser.getBalance() - amount;
        double newToBalance = toUser.getBalance() + amount;

        if (updateUserBalance(fromUserId, newFromBalance) && updateUserBalance(toUserId, newToBalance)) {
            Transaction transaction = new Transaction(
                    "TRANSFER",
                    amount,
                    fromUserId,
                    toUserId,
                    LocalDateTime.now()
            );
            return transactionService.addTransaction(transaction);
        }
        return false;
    }

    // Authenticate a user by TCKN and password
    public User loginUser(String tckn, String password) {
        String query = "SELECT user_id, fullname, tckn, account_number, balance, preferred_bank, password " +
                "FROM users WHERE tckn = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tckn);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    if (password.equals(storedPassword)) {
                        int userId = resultSet.getInt("user_id");
                        String fullname = resultSet.getString("fullname");
                        String accountNumber = resultSet.getString("account_number");
                        double balance = resultSet.getDouble("balance");
                        String preferredBank = resultSet.getString("preferred_bank");

                        return new User(userId, fullname, tckn, accountNumber, balance, preferredBank);
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
        return null;
    }
    public User getUserByAccountNumber(String accountNumber) {
        String query = "SELECT user_id, fullname, tckn, account_number, balance, preferred_bank FROM users WHERE account_number = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Kullanıcı bilgilerini haritala
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("fullname"),
                            resultSet.getString("tckn"),
                            resultSet.getString("account_number"),
                            resultSet.getDouble("balance"),
                            resultSet.getString("preferred_bank")
                    );
                } else {
                    System.out.println("No user found with the given account number: " + accountNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error occurred while fetching user by account number.");
        }
        return null; // Kullanıcı bulunamazsa null döndür
    }


    // Retrieve user details by User ID
    public User getUserById(int userId) {
        String query = "SELECT user_id, fullname, tckn, account_number, balance, preferred_bank " +
                "FROM users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String fullname = resultSet.getString("fullname");
                    String tckn = resultSet.getString("tckn");
                    String accountNumber = resultSet.getString("account_number");
                    double balance = resultSet.getDouble("balance");
                    String preferredBank = resultSet.getString("preferred_bank");

                    return new User(userId, fullname, tckn, accountNumber, balance, preferredBank);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user's balance
    public boolean updateUserBalance(int userId, double newBalance) {
        String query = "UPDATE users SET balance = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Generate a unique account number
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

    // Generate a random account number
    private String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder("TR");
        for (int i = 0; i < 10; i++) {
            accountNumber.append((int) (Math.random() * 10));
        }
        return accountNumber.toString();
    }
}

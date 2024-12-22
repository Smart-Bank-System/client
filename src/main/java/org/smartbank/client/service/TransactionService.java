package org.smartbank.client.service;

import org.smartbank.client.model.Transaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    // Singleton instance
    private static TransactionService instance;

    // Private constructor to enforce singleton
    private TransactionService() {
    }

    // Get the singleton instance
    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }

    public boolean addTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_type, amount, from_user_id, to_user_id, date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, transaction.getTransactionType());
            statement.setDouble(2, transaction.getAmount());
            statement.setObject(3, transaction.getFromUserId(), Types.INTEGER);
            statement.setObject(4, transaction.getToUserId(), Types.INTEGER);
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getDate()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch all transactions for a user
    public List<Transaction> getTransactionsByUserId(int userId) {
        String query = "SELECT * FROM transactions WHERE from_user_id = ? OR to_user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(
                            resultSet.getString("transaction_type"),
                            resultSet.getDouble("amount"),
                            resultSet.getObject("from_user_id", Integer.class),
                            resultSet.getObject("to_user_id", Integer.class),
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

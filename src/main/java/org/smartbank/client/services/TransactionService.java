package org.smartbank.client.services;

import org.smartbank.client.model.Transaction;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TransactionService {

    private void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted!");
        }
    }

    public List<Transaction> viewTransactionHistory(String accountNumber) {
        simulateDelay();
        // Returning fake transaction history
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("T1001", "deposit", 1000.00, accountNumber));
        transactions.add(new Transaction("T1002", "withdrawal", 500.00, accountNumber));
        return transactions;
    }
}

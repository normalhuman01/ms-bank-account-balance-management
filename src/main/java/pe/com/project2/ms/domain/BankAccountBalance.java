package pe.com.project2.ms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class BankAccountBalance {
    private String id;
    private String bankAccountId;
    private BigDecimal balance;
    private Integer transactionCountLimit;
    private List<Transaction> transactions;

    public BankAccountBalance() {
        transactions = new LinkedList<>();
    }

    public void creditMoney(Transaction transaction) {
        transaction.setOccurredAt(LocalDateTime.now());
        transaction.setBankingTransactionType(BankingTransactionType.CREDIT);
        balance = balance.add(transaction.getAmount());
        transactions.add(transaction);
        this.updateTransactionCountLimit();
    }

    public void debitMoney(Transaction transaction) {
        transaction.setOccurredAt(LocalDateTime.now());
        transaction.setBankingTransactionType(BankingTransactionType.DEBIT);
        balance = balance.subtract(transaction.getAmount());
        transactions.add(transaction);
        this.updateTransactionCountLimit();
    }

    public void updateTransactionCountLimit() {
        transactionCountLimit--;
    }

    public void applyFee(Transaction transaction) {
        transaction.setOccurredAt(LocalDateTime.now());
        transaction.setBankingTransactionType(BankingTransactionType.FEE);
        balance = balance.subtract(transaction.getAmount());
        transactions.add(transaction);
    }
}

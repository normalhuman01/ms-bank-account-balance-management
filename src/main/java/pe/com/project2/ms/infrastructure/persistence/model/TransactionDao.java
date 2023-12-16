package pe.com.project2.ms.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.com.project2.ms.domain.BankingTransactionType;
import pe.com.project2.ms.domain.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("transactions")
public class TransactionDao {

    protected String id;
    protected String bankAccountId;
    protected BigDecimal amount;
    protected LocalDateTime occurredAt;
    protected BankingTransactionType bankingTransactionType;

    public TransactionDao(Transaction transaction) {
        id = transaction.getId();
        bankAccountId = transaction.getBankAccountId();
        amount = transaction.getAmount();
        occurredAt = transaction.getOccurredAt();
        bankingTransactionType = transaction.getBankingTransactionType();
    }

    public Transaction toTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setBankAccountId(bankAccountId);
        transaction.setAmount(amount);
        transaction.setOccurredAt(occurredAt);
        transaction.setBankingTransactionType(bankingTransactionType);
        return transaction;
    }
}

package pe.com.project2.ms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fee {
    private BigDecimal amount;

    public Transaction toTransaction(String bankAccountId) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setOccurredAt(LocalDateTime.now());
        transaction.setBankAccountId(bankAccountId);
        transaction.setBankingTransactionType(BankingTransactionType.FEE);
        return transaction;
    }
}

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
public class Transaction {
    protected String id;
    protected String bankAccountId;
    protected BigDecimal amount;
    protected LocalDateTime occurredAt;
    protected BankingTransactionType bankingTransactionType;
}

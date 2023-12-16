package pe.com.project2.ms.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.com.project2.ms.domain.BankAccountBalance;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("bankAccountBalances")
public class BankAccountBalanceDao {

    @Id
    private String id;
    private String bankAccountId;
    private BigDecimal balance;
    private Integer transactionCountLimit;

    public BankAccountBalanceDao(BankAccountBalance bankAccountBalance) {
        id = bankAccountBalance.getId();
        bankAccountId = bankAccountBalance.getBankAccountId();
        balance = bankAccountBalance.getBalance();
        transactionCountLimit = bankAccountBalance.getTransactionCountLimit();
    }

    public BankAccountBalance toBankAccountBalance() {
        BankAccountBalance bankAccountBalance = new BankAccountBalance();
        bankAccountBalance.setId(id);
        bankAccountBalance.setBankAccountId(bankAccountId);
        bankAccountBalance.setBalance(balance);
        bankAccountBalance.setTransactionCountLimit(transactionCountLimit);
        return bankAccountBalance;
    }
}

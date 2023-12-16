package pe.com.project2.ms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends Transaction {
    private String accountSenderId;
    private String accountReceiverId;

    public Transaction toTransactionForSender() {
        return new Transaction(id, accountSenderId, amount, occurredAt, BankingTransactionType.DEBIT);
    }

    public Transaction toTransactionsForRecipient() {
        return new Transaction(id, accountReceiverId, amount, occurredAt, BankingTransactionType.CREDIT);
    }
}

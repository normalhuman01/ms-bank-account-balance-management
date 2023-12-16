package pe.com.project2.ms.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.project2.ms.application.WithdrawalUseCase;
import pe.com.project2.ms.application.persistence.BankAccountBalanceRepository;
import pe.com.project2.ms.application.persistence.TransactionRepository;
import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.domain.Fee;
import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.domain.exception.NotFoundException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements WithdrawalUseCase {

    private final TransactionRepository transactionRepository;
    private final BankAccountBalanceRepository bankAccountBalanceRepository;

    @Override
    public Mono<BankAccountBalance> execute(Transaction transaction) {
        return bankAccountBalanceRepository.findByBankAccountId(transaction.getBankAccountId())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta bancaria no existe")))
                .flatMap(bankAccountBalance -> {
                    if (bankAccountBalance.getTransactionCountLimit() <= 0) {
                        return this.applyFee(bankAccountBalance, transaction)
                                .flatMap(bankAccountBalanceRepository::save);
                    } else {
                        if (bankAccountBalance.getBalance().compareTo(transaction.getAmount()) < 0) {
                            return Mono.error(new RuntimeException("Saldo insufiente para realizar esta operacion"));
                        }
                        bankAccountBalance.debitMoney(transaction);
                        return bankAccountBalanceRepository.save(bankAccountBalance);
                    }
                });
    }


    private Mono<BankAccountBalance> applyFee(BankAccountBalance bankAccountBalance, Transaction transaction) {
        Mono<Transaction> feeTransactionMono = Mono.just(new Fee(BigDecimal.valueOf(5)))
                .map(fee -> fee.toTransaction(bankAccountBalance.getBankAccountId()));

        return feeTransactionMono
                .flatMap(feeTransaction -> this.assertThatCanMakeTransactionWithFee(bankAccountBalance, feeTransaction, transaction))
                .map(feeTransaction -> {
                    bankAccountBalance.applyFee(feeTransaction);
                    bankAccountBalance.debitMoney(transaction);
                    return bankAccountBalance;
                });
    }

    private Mono<Transaction> assertThatCanMakeTransactionWithFee(BankAccountBalance bankAccountBalance, Transaction feeTransaction, Transaction transaction) {
        BigDecimal amount = Stream.of(transaction, feeTransaction).map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (bankAccountBalance.getBalance().compareTo(amount) < 0) {
            return Mono.error(new RuntimeException("Saldo insufiente para realizar esta operacion"));
        }
        return Mono.just(feeTransaction);
    }

    private Mono<BankAccountBalance> asserThatCanMakeTransaction(BankAccountBalance bankAccountBalance, BigDecimal amount) {
        if (bankAccountBalance.getBalance().compareTo(amount) < 0) {
            return Mono.error(new RuntimeException("Saldo insufiente para realizar esta operacion"));
        }
        return Mono.just(bankAccountBalance);
    }

}

package pe.com.project2.ms.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.project2.ms.application.DepositUseCase;
import pe.com.project2.ms.application.persistence.BankAccountBalanceRepository;
import pe.com.project2.ms.application.persistence.TransactionRepository;
import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.domain.Fee;
import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.domain.exception.NotFoundException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DepositService implements DepositUseCase {

    private final TransactionRepository transactionRepository;
    private final BankAccountBalanceRepository bankAccountBalanceRepository;

    @Override
    public Mono<BankAccountBalance> execute(Transaction transaction) {

        Mono<BankAccountBalance> bankAccountBalanceMono = bankAccountBalanceRepository.findByBankAccountId(transaction.getBankAccountId())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta bancaria no existe")))
                .map(bankAccountBalance -> {
                    bankAccountBalance.creditMoney(transaction);
                    return bankAccountBalance;
                });

        return bankAccountBalanceMono.flatMap(bankAccountBalance -> {
            if (bankAccountBalance.getTransactionCountLimit() < 0) {
                return this.applyFee(bankAccountBalance)
                        .flatMap(bankAccountBalanceRepository::save);
            }
            return bankAccountBalanceRepository.save(bankAccountBalance);
        });

    }


    private Mono<BankAccountBalance> applyFee(BankAccountBalance bankAccountBalance) {
        return Mono.just(new Fee(BigDecimal.valueOf(5)))
                .map(fee -> fee.toTransaction(bankAccountBalance.getBankAccountId()))
                .map(feeTransaction -> {
                    bankAccountBalance.applyFee(feeTransaction);
                    return bankAccountBalance;
                });
    }

}

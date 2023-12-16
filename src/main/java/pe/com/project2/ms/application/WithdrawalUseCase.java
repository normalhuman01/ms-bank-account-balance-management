package pe.com.project2.ms.application;

import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.domain.Transaction;
import reactor.core.publisher.Mono;

public interface WithdrawalUseCase {
    Mono<BankAccountBalance> execute(Transaction transaction);
}

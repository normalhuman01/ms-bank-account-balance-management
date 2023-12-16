package pe.com.project2.ms.application;

import pe.com.project2.ms.domain.BankAccountBalance;
import reactor.core.publisher.Mono;

public interface BankAccountBalanceService {
    Mono<BankAccountBalance> save(BankAccountBalance bankAccountBalance);

    Mono<BankAccountBalance> findByBankAccountId(String bankAccountId);
}

package pe.com.project2.ms.application.persistence;

import pe.com.project2.ms.domain.BankAccountBalance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountBalanceRepository {
    Mono<BankAccountBalance> save(BankAccountBalance bankAccountBalance);

    Mono<BankAccountBalance> findByBankAccountId(String bankAccountId);

    Flux<BankAccountBalance> saveAll(Flux<BankAccountBalance> bankAccountBalances);
}

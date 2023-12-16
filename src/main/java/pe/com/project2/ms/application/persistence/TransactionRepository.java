package pe.com.project2.ms.application.persistence;

import pe.com.project2.ms.domain.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Mono<Transaction> save(Transaction transaction);

    Flux<Transaction> findByBankAccountId(String bankAccountId);

    Flux<Transaction> saveAll(Flux<Transaction> transactions);

}

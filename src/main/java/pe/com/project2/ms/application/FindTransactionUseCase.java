package pe.com.project2.ms.application;

import pe.com.project2.ms.domain.Transaction;
import reactor.core.publisher.Mono;

public interface FindTransactionUseCase {
    Mono<Transaction> findByAccountId();
}

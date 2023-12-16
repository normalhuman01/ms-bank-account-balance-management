package pe.com.project2.ms.application;

import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.domain.Transfer;
import reactor.core.publisher.Mono;

public interface TransferUseCase {
    Mono<Transaction> execute(Transfer transfer);
}

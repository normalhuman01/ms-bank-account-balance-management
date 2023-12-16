package pe.com.project2.ms.infrastructure.persistence.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pe.com.project2.ms.application.persistence.TransactionRepository;
import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.infrastructure.persistence.model.TransactionDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TransactionReactiveMongoRepository implements TransactionRepository {

    private final ITransactionReactiveMongoRepository transactionReactiveMongoRepository;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return transactionReactiveMongoRepository.save(new TransactionDao(transaction))
                .map(TransactionDao::toTransaction);
    }

    @Override
    public Flux<Transaction> findByBankAccountId(String bankAccountId) {
        return transactionReactiveMongoRepository.findByBankAccountId(bankAccountId)
                .map(TransactionDao::toTransaction);
    }

    @Override
    public Flux<Transaction> saveAll(Flux<Transaction> transactions) {
        Flux<TransactionDao> transactionDaoFlux = transactions.map(TransactionDao::new);
        return transactionReactiveMongoRepository.saveAll(transactionDaoFlux)
                .map(TransactionDao::toTransaction);
    }

}

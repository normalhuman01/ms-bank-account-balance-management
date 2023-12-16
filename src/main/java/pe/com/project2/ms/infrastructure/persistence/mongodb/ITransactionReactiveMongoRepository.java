package pe.com.project2.ms.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.project2.ms.infrastructure.persistence.model.TransactionDao;
import reactor.core.publisher.Flux;

public interface ITransactionReactiveMongoRepository extends ReactiveMongoRepository<TransactionDao, String> {
    Flux<TransactionDao> findByBankAccountId(String bankAccountId);
}

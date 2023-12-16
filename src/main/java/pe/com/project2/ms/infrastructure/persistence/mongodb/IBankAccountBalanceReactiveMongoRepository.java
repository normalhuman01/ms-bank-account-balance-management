package pe.com.project2.ms.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.project2.ms.infrastructure.persistence.model.BankAccountBalanceDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountBalanceReactiveMongoRepository extends ReactiveMongoRepository<BankAccountBalanceDao, String> {
    Mono<BankAccountBalanceDao> findByBankAccountId(String bankAccountId);

    Flux<BankAccountBalanceDao> saveAll(Flux<BankAccountBalanceDao> bankAccountBalances);
}

package pe.com.project2.ms.infrastructure.persistence.mongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pe.com.project2.ms.application.persistence.BankAccountBalanceRepository;
import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.infrastructure.persistence.model.BankAccountBalanceDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BankAccountBalanceReactiveMongoRepository implements BankAccountBalanceRepository {

    private final IBankAccountBalanceReactiveMongoRepository bankAccountBalanceReactiveMongoRepository;

    @Override
    public Mono<BankAccountBalance> save(BankAccountBalance bankAccountBalance) {
        log.info("BankAccountBalance to save {}", bankAccountBalance);
        return bankAccountBalanceReactiveMongoRepository.save(new BankAccountBalanceDao(bankAccountBalance))
                .map(BankAccountBalanceDao::toBankAccountBalance);
    }

    @Override
    public Mono<BankAccountBalance> findByBankAccountId(String bankAccountId) {
        return bankAccountBalanceReactiveMongoRepository.findByBankAccountId(bankAccountId)
                .map(BankAccountBalanceDao::toBankAccountBalance);
    }

    @Override
    public Flux<BankAccountBalance> saveAll(Flux<BankAccountBalance> bankAccountBalances) {
        Flux<BankAccountBalanceDao> bankAccountBalanceDaoFlux = bankAccountBalances.map(BankAccountBalanceDao::new);
        return bankAccountBalanceReactiveMongoRepository.saveAll(bankAccountBalanceDaoFlux)
                .map(BankAccountBalanceDao::toBankAccountBalance);
    }
}

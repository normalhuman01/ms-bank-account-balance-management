package pe.com.project2.ms.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.project2.ms.application.BankAccountBalanceService;
import pe.com.project2.ms.application.persistence.BankAccountBalanceRepository;
import pe.com.project2.ms.domain.BankAccountBalance;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {

    private final BankAccountBalanceRepository bankAccountBalanceRepository;

    @Override
    public Mono<BankAccountBalance> save(BankAccountBalance bankAccountBalance) {
        return bankAccountBalanceRepository.save(bankAccountBalance);
    }

    @Override
    public Mono<BankAccountBalance> findByBankAccountId(String bankAccountId) {
        return bankAccountBalanceRepository.findByBankAccountId(bankAccountId);
    }
}

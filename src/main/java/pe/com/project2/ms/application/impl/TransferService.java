package pe.com.project2.ms.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.project2.ms.application.TransferUseCase;
import pe.com.project2.ms.application.WithdrawalUseCase;
import pe.com.project2.ms.application.persistence.BankAccountBalanceRepository;
import pe.com.project2.ms.application.persistence.TransactionRepository;
import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.domain.Transfer;
import pe.com.project2.ms.domain.exception.NotFoundException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransferService implements TransferUseCase {

    private final TransactionRepository transactionRepository;
    private final BankAccountBalanceRepository bankAccountBalanceRepository;
    private final WithdrawalUseCase withdrawalUseCase;

    @Override
    public Mono<Transaction> execute(Transfer transfer) {
        Mono<BankAccountBalance> debitTransactionMono = withdrawalUseCase.execute(transfer.toTransactionForSender());
        Mono<BankAccountBalance> creditTransactionMono = bankAccountBalanceRepository.findByBankAccountId(transfer.getAccountReceiverId())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta bancaria de destino no existe")))
                .map(bankAccountBalance -> {
                    bankAccountBalance.creditMoney(transfer.toTransactionsForRecipient());
                    return bankAccountBalance;
                })
                .flatMap(bankAccountBalanceRepository::save);
        return Mono.when(debitTransactionMono, creditTransactionMono).then(Mono.just(transfer));
    }

}

package pe.com.project2.ms.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.project2.ms.application.BankAccountBalanceService;
import pe.com.project2.ms.application.DepositUseCase;
import pe.com.project2.ms.application.TransferUseCase;
import pe.com.project2.ms.application.WithdrawalUseCase;
import pe.com.project2.ms.domain.BankAccountBalance;
import pe.com.project2.ms.domain.Transaction;
import pe.com.project2.ms.domain.Transfer;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankAccountBalanceManagementHandler {

    private final DepositUseCase depositUseCase;
    private final TransferUseCase transferUseCase;
    private final WithdrawalUseCase withdrawalUseCase;
    private final BankAccountBalanceService bankAccountBalanceService;

    public Mono<ServerResponse> getAllTransactions(ServerRequest serverRequest) {
        return Mono.empty();
    }

    public Mono<ServerResponse> transfer(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Transfer.class)
                .map(transferUseCase::execute)
                .flatMap(transfer -> this.toTransactionServerResponse(transfer, HttpStatus.CREATED));
    }

    public Mono<ServerResponse> withdrawal(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Transaction.class)
                .map(withdrawalUseCase::execute)
                .flatMap(bankAccountBalance -> this.toBankAccountBalanceServerResponse(bankAccountBalance, HttpStatus.CREATED));
    }

    public Mono<ServerResponse> deposit(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Transaction.class)
                .map(depositUseCase::execute)
                .flatMap(bankAccountBalance -> this.toBankAccountBalanceServerResponse(bankAccountBalance, HttpStatus.CREATED));
    }

    public Mono<ServerResponse> postBankAccountBalance(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BankAccountBalance.class)
                .doOnNext(bankAccountBalance -> log.info("Desde el handler, {}", bankAccountBalance))
                .map(bankAccountBalanceService::save)
                .flatMap(bankAccountBalance -> this.toBankAccountBalanceServerResponse(bankAccountBalance, HttpStatus.CREATED));
    }

    public Mono<ServerResponse> getAllBankAccountBalance(ServerRequest serverRequest) {
        Mono<BankAccountBalance> bankAccountBalanceMono = serverRequest.queryParam("bankAccountId")
                .map(bankAccountBalanceService::findByBankAccountId)
                .orElseGet(Mono::empty);
        return this.toBankAccountBalanceServerResponse(bankAccountBalanceMono, HttpStatus.OK);
    }

    private Mono<ServerResponse> toTransactionServerResponse(CorePublisher<Transaction> transaction, HttpStatus status) {
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(transaction, Transaction.class);
    }

    private Mono<ServerResponse> toBankAccountBalanceServerResponse(CorePublisher<BankAccountBalance> bankAccountBalance, HttpStatus status) {
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bankAccountBalance, BankAccountBalance.class);
    }

}

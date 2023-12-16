package pe.com.project2.ms.infrastructure.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BankAccountBalanceManagementRouter {

    private static final String BANK_ACCOUNT_TRANSACTIONS = "/bank-account-balance";
    private static final String DEPOSITS = BANK_ACCOUNT_TRANSACTIONS + "/deposit";
    private static final String WITHDRAWALS = BANK_ACCOUNT_TRANSACTIONS + "/withdrawal";
    private static final String TRANSFERS = BANK_ACCOUNT_TRANSACTIONS + "/transfer";

    @Bean
    public RouterFunction<ServerResponse> routes(BankAccountBalanceManagementHandler bankAccountBalanceManagementHandler) {
        return route(POST(DEPOSITS).and(accept(APPLICATION_JSON)), bankAccountBalanceManagementHandler::deposit)
                .andRoute(POST(WITHDRAWALS).and(accept(APPLICATION_JSON)), bankAccountBalanceManagementHandler::withdrawal)
                .andRoute(POST(TRANSFERS).and(accept(APPLICATION_JSON)), bankAccountBalanceManagementHandler::transfer)
                .andRoute(POST(BANK_ACCOUNT_TRANSACTIONS), bankAccountBalanceManagementHandler::postBankAccountBalance)
                .andRoute(GET(BANK_ACCOUNT_TRANSACTIONS), bankAccountBalanceManagementHandler::getAllBankAccountBalance);
    }

}

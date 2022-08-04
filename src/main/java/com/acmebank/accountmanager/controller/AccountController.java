package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.request.CreateTransactionDto;
import com.acmebank.accountmanager.dto.response.BalanceDto;
import com.acmebank.accountmanager.dto.response.TransactionDto;
import com.acmebank.accountmanager.exception.ResourceNotFoundException;
import com.acmebank.accountmanager.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Get account balance by account No.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the account with balance",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDto.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)})
    @GetMapping(value = "/{accountNo}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> getAccountBalance(@PathVariable("accountNo") String accountNo) {
        return accountService.getAccountBalanceByAccountNo(accountNo)
                .map(ResponseEntity::ok)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Operation(summary = "Create a transaction for transferring money from one account to another")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created, money transfer succeeded",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Transaction failure, see the error message for more detail",
                    content = @Content)})
    @PostMapping(value = "/{accountNo}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable("accountNo") String accountNo, @Valid @RequestBody CreateTransactionDto createTransactionDto) {
        return new ResponseEntity<>(accountService.createTransaction(accountNo, createTransactionDto), HttpStatus.CREATED);
    }
}

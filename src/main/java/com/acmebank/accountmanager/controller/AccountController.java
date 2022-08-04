package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.request.CreateTransactionDto;
import com.acmebank.accountmanager.dto.response.BalanceDto;
import com.acmebank.accountmanager.dto.response.TransactionDto;
import com.acmebank.accountmanager.exception.ResourceNotFoundException;
import com.acmebank.accountmanager.service.AccountService;
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

    @GetMapping(value = "/{accountNo}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDto> getAccountBalance(@PathVariable("accountNo") String accountNo) {
        return accountService.getAccountBalanceByAccountNo(accountNo)
                .map(ResponseEntity::ok)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping(value = "/{accountNo}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable("accountNo") String accountNo, @Valid @RequestBody CreateTransactionDto createTransactionDto) {
        return new ResponseEntity<>(accountService.createTransaction(accountNo, createTransactionDto), HttpStatus.CREATED);
    }
}

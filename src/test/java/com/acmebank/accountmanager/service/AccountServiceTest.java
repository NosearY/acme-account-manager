package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.dto.request.CreateTransactionDto;
import com.acmebank.accountmanager.dto.response.TransactionDto;
import com.acmebank.accountmanager.entity.Account;
import com.acmebank.accountmanager.entity.Balance;
import com.acmebank.accountmanager.exception.InvalidOperationException;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {

    private static final String FROM_ACCOUNT_NO = "12345678";
    private static final String TO_ACCOUNT_NO = "88888888";
    public static final double INITIAL_AMOUNT = 1_000_000d;
    public static final String CURRENCY_HKD = "HKD";

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @Autowired
    AccountService accountService;

    @BeforeEach
    void setup() {

        var mockFromBalance = new Balance();
        mockFromBalance.setAmount(INITIAL_AMOUNT);
        mockFromBalance.setCurrency(CURRENCY_HKD);
        mockFromBalance.setAccountId(1);
        mockFromBalance.setId(1);
        var mockFromAccount = new Account();
        mockFromAccount.setAccountNo(FROM_ACCOUNT_NO);
        mockFromAccount.setId(1);
        mockFromAccount.setBalance(mockFromBalance);

        var mockToBalance = new Balance();
        mockToBalance.setAmount(INITIAL_AMOUNT);
        mockToBalance.setCurrency(CURRENCY_HKD);
        mockToBalance.setAccountId(1);
        mockToBalance.setId(1);
        var mockToAccount = new Account();
        mockToAccount.setAccountNo(FROM_ACCOUNT_NO);
        mockToAccount.setId(1);
        mockToAccount.setBalance(mockToBalance);

        when(accountRepository.findByAccountNo(FROM_ACCOUNT_NO)).thenReturn(Optional.of(mockFromAccount));
        when(accountRepository.findByAccountNo(TO_ACCOUNT_NO)).thenReturn(Optional.of(mockToAccount));
    }

    @Test
    public void givenSameAccountNo_whenCreateTransaction_shouldFail() {
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setToAccountNo(FROM_ACCOUNT_NO);
        Assertions.assertThrows(InvalidOperationException.class, () -> accountService.createTransaction(FROM_ACCOUNT_NO, createTransactionDto));
    }

    @Test
    public void givenInsufficientBalance_whenCreateTransaction_shouldFail() {
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setToAccountNo(TO_ACCOUNT_NO);
        createTransactionDto.setAmount(INITIAL_AMOUNT + 1);
        Assertions.assertThrows(InvalidOperationException.class, () -> accountService.createTransaction(FROM_ACCOUNT_NO, createTransactionDto));
    }

    @Test
    public void givenUnmatchedCurrency_whenCreateTransaction_shouldFail() {
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setToAccountNo(TO_ACCOUNT_NO);
        createTransactionDto.setAmount(100d);
        createTransactionDto.setCurrency("USD");
        Assertions.assertThrows(InvalidOperationException.class, () -> accountService.createTransaction(FROM_ACCOUNT_NO, createTransactionDto));
    }

    @Test
    public void givenInvalidToAccount_whenCreateTransaction_shouldFail() {
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setToAccountNo(TO_ACCOUNT_NO + Math.random());
        createTransactionDto.setAmount(100d);
        createTransactionDto.setCurrency(CURRENCY_HKD);
        Assertions.assertThrows(InvalidOperationException.class, () -> accountService.createTransaction(FROM_ACCOUNT_NO, createTransactionDto));
    }

    @Test
    public void whenCreateTransaction_shouldReturn() {
        double amount = 100d;
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setToAccountNo(TO_ACCOUNT_NO);
        createTransactionDto.setAmount(100d);
        createTransactionDto.setCurrency(CURRENCY_HKD);

        TransactionDto transactionDto = accountService.createTransaction(FROM_ACCOUNT_NO, createTransactionDto);
        Assertions.assertEquals(amount, transactionDto.getAmount());
        Assertions.assertEquals(FROM_ACCOUNT_NO, transactionDto.getFromAccount());
        Assertions.assertEquals(TO_ACCOUNT_NO, transactionDto.getToAccount());
        Assertions.assertEquals(amount, transactionDto.getAmount());
        Assertions.assertEquals(CURRENCY_HKD, transactionDto.getCurrency());
        Assertions.assertTrue(transactionDto.getReferenceId() != null && !transactionDto.getReferenceId().isEmpty());
    }

}
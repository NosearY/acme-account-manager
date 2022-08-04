package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.dto.request.CreateTransactionDto;
import com.acmebank.accountmanager.dto.response.AccountDto;
import com.acmebank.accountmanager.dto.response.BalanceDto;
import com.acmebank.accountmanager.dto.response.TransactionDto;
import com.acmebank.accountmanager.entity.Transaction;
import com.acmebank.accountmanager.exception.InvalidOperationException;
import com.acmebank.accountmanager.exception.ResourceNotFoundException;
import com.acmebank.accountmanager.repository.AccountRepository;
import com.acmebank.accountmanager.repository.TransactionRepository;
import com.acmebank.accountmanager.utils.MappingUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Optional<AccountDto> getAccountByAccountNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo).map(MappingUtils::accountDto);
    }

    public Optional<BalanceDto> getAccountBalanceByAccountNo(String accountNo) {
        return getAccountByAccountNo(accountNo).map(AccountDto::getBalance);
    }

    public TransactionDto createTransaction(String fromAccountNo, CreateTransactionDto createTransactionDto) {

        var fromAccount = accountRepository.findByAccountNo(fromAccountNo).orElseThrow(ResourceNotFoundException::new);
        var fromAccountBalance = fromAccount.getBalance();

        if (fromAccountNo.equals(createTransactionDto.getToAccountNo())) {
            throw new InvalidOperationException("Cannot transfer money between two same account");
        }

        if (!fromAccountBalance.getCurrency().equals(createTransactionDto.getCurrency())) {
            throw new InvalidOperationException("Currency between two account must match");
        }

        if (fromAccountBalance.getAmount() < createTransactionDto.getAmount()) {
            throw new InvalidOperationException("Insufficient balance");
        }

        var toAccount = accountRepository.findByAccountNo(createTransactionDto.getToAccountNo()).orElseThrow(() -> new InvalidOperationException("Invalid account no. to transfer balance to"));
        var toAccountBalance = toAccount.getBalance();

        fromAccountBalance.setAmount(fromAccountBalance.getAmount() - createTransactionDto.getAmount());
        toAccountBalance.setAmount(toAccountBalance.getAmount() + createTransactionDto.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        var transaction = new Transaction();
        transaction.setFromAccount(fromAccountNo);
        transaction.setToAccount(createTransactionDto.getToAccountNo());
        transaction.setAmount(createTransactionDto.getAmount());
        transaction.setCurrency(createTransactionDto.getCurrency());
        transaction.setReferenceId(UUID.randomUUID().toString());
        transactionRepository.save(transaction);

        return MappingUtils.transactionDto(transaction);
    }

}

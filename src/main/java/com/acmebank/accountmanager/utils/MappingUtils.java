package com.acmebank.accountmanager.utils;

import com.acmebank.accountmanager.dto.response.AccountDto;
import com.acmebank.accountmanager.dto.response.BalanceDto;
import com.acmebank.accountmanager.dto.response.TransactionDto;
import com.acmebank.accountmanager.entity.Account;
import com.acmebank.accountmanager.entity.Balance;
import com.acmebank.accountmanager.entity.Transaction;

public class MappingUtils {

    public static AccountDto accountDto(Account account) {
        return new AccountDto(account.getAccountNo(), balanceDto(account.getBalance()));
    }

    public static BalanceDto balanceDto(Balance balance) {
        return new BalanceDto(balance.getAmount(), balance.getCurrency());
    }

    public static TransactionDto transactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getReferenceId(), transaction.getFromAccount(), transaction.getToAccount(), transaction.getCurrency(), transaction.getAmount());
    }

}

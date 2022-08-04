package com.acmebank.accountmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

    private String accountNo;
    private BalanceDto balance;

}

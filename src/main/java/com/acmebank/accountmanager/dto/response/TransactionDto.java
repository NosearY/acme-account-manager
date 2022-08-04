package com.acmebank.accountmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

    private String referenceId;
    private String fromAccount;
    private String toAccount;
    private String currency;
    private double amount;
}

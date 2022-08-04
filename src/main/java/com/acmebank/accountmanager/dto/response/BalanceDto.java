package com.acmebank.accountmanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceDto {

    private double amount;
    private String currency;
}

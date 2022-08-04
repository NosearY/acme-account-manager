package com.acmebank.accountmanager.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateTransactionDto {

    @NotEmpty(message = "toAccountNo is required")
    private String toAccountNo;

    @Pattern(regexp = "^HKD$", message = "currency format is not recognized")
    @NotNull
    private String currency;

    @Min(value = 1, message = "amount must be greater than 1")
    private double amount;
}

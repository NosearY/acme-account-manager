package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.dto.request.CreateTransactionDto;
import com.acmebank.accountmanager.dto.response.BalanceDto;
import com.acmebank.accountmanager.service.AccountService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Test
    void givenIncorrectAccount_whenGetBalance_thenReturnWithError() throws Exception {
        mockMvc.perform(get("/account/{accountNo}/balance", -1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetAccountBalance_thenReturn() throws Exception {

        var accountNo = "1";
        var balanceDto = new BalanceDto(1.0d, "HKD");

        when(accountService.getAccountBalanceByAccountNo(anyString())).thenReturn(Optional.of(balanceDto));
        mockMvc.perform(get("/account/{id}/balance", accountNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("HKD"))
                .andExpect(jsonPath("$.amount").value(1.0d));
    }

    @Test
    void givenInvalidCreateTransactionDto_whenCreateTransaction_thenReturnWithError() throws Exception {
        var fromAccount = "1";
        var createTransactionDto = new CreateTransactionDto();
        mockMvc.perform(post("/account/{id}/transaction", fromAccount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createTransactionDto)))
                .andExpect(status().isBadRequest());

        createTransactionDto.setToAccountNo("2");
        mockMvc.perform(post("/account/{id}/transaction", fromAccount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createTransactionDto)))
                .andExpect(status().isBadRequest());

        createTransactionDto.setAmount(1d);
        createTransactionDto.setToAccountNo("2");
        mockMvc.perform(post("/account/{id}/transaction", fromAccount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createTransactionDto)))
                .andExpect(status().isBadRequest());

        createTransactionDto.setCurrency("CNY");
        createTransactionDto.setAmount(1d);
        createTransactionDto.setToAccountNo("2");
        mockMvc.perform(post("/account/{id}/transaction", fromAccount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createTransactionDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateTransaction_thenReturn() throws Exception {
        var fromAccount = "1";
        var createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setCurrency("HKD");
        createTransactionDto.setAmount(1d);
        createTransactionDto.setToAccountNo("2");
        mockMvc.perform(post("/account/{id}/transaction", fromAccount)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createTransactionDto)))
                .andExpect(status().isCreated());

    }
}
package com.limadevCod3r.balance.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.limadevCod3r.balance.dtos.UpdateBalanceRequest;
import com.limadevCod3r.balance.mapper.BalanceMapper;
import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.UpdateBalanceService;
import com.limadevCod3r.shared.exceptions.GlobalExceptionHandler;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
public class UpdateBalanceControllerTest {

    @InjectMocks
    UpdateBalanceController controller;

    @Mock
    private UpdateBalanceService service;

    @Mock
    private BalanceMapper mapper;

    MockMvc mockMvc;

    private Balance balance;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .alwaysDo(print())
                .build();

        balance = new Balance();
        balance.setId("123e4567-e89b-12d3-a456-426614174000");
        balance.setDescription("Exemplo de balanço");
        balance.setAmount(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("It should successfully update a balance")
    void shouldUpdateBalance() throws Exception {
        String balanceId = "123e4567-e89b-12d3-a456-426614174000";
        UpdateBalanceRequest request = new UpdateBalanceRequest("Balanço atualizado", new BigDecimal("200.00"));

        Balance updateEntity = new Balance();
        updateEntity.setDescription("Balanço atualizado");
        updateEntity.setAmount(new BigDecimal("200.00"));

        Balance updatedBalance = new Balance();
        updatedBalance.setId(balanceId);
        updatedBalance.setDescription("Balanço atualizado");
        updatedBalance.setAmount(new BigDecimal("200.00"));

        when(mapper.ConvertToEntity(any(UpdateBalanceRequest.class))).thenReturn(updateEntity);

        when(service.update(eq(balanceId), any(Balance.class))).thenReturn(updatedBalance);

        mockMvc.perform(patch("/api/balance/{id}", balanceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(balanceId))
                .andExpect(jsonPath("$.description").value("Balanço atualizado"))
                .andExpect(jsonPath("$.amount").value(200.00));
    }

    @Test
    @DisplayName("It should return status 404 when the balance is not found")
    void shouldReturnNotFoundWhenBalanceDoesNotExist() throws Exception {
        String nonExistentId = "non-existent-id";
        UpdateBalanceRequest request = new UpdateBalanceRequest("Balanço atualizado", new BigDecimal("200.00"));

        Balance updateEntity = new Balance();
        updateEntity.setDescription("Balanço atualizado");
        updateEntity.setAmount(new BigDecimal("200.00"));

        when(mapper.ConvertToEntity(any(UpdateBalanceRequest.class))).thenReturn(updateEntity);

        when(service.update(eq(nonExistentId), any(Balance.class)))
                .thenThrow(new ResourceNotFoundException("Balance not found with ID: " + nonExistentId));

        mockMvc.perform(patch("/api/balance/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

package com.limadevCod3r.balance.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.GetBalanceService;
import com.limadevCod3r.shared.exceptions.GlobalExceptionHandler;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GetBalanceControllerTest {

    @InjectMocks
    GetBalanceController controller;

    @Mock
    private GetBalanceService service;

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    private Balance balance;
    private String balanceId;

    @BeforeEach
    void setUp() {
        // Configuração do MockMvc com o handler de exceções
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .alwaysDo(print())
                .build();

        // Inicialização do ObjectMapper para verificar o corpo da resposta
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Para suportar LocalDateTime

        // Inicialização do objeto balance para os testes
        balanceId = "123e4567-e89b-12d3-a456-426614174000";
        balance = new Balance();
        balance.setId(UUID.fromString(balanceId).toString());
        balance.setDescription("Teste de balanço");
        balance.setAmount(new BigDecimal("100.00"));
        balance.setCreatedAt(LocalDateTime.now());
        balance.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("It should return a balance by ID")
    void shouldReturnBalanceWhenIdExists() throws Exception {

        when(service.getById(balanceId)).thenReturn(balance);

        mockMvc.perform(get("/api/balance/{id}", balanceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(balanceId))
                .andExpect(jsonPath("$.description").value("Teste de balanço"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    @DisplayName("It should return 404 when balance does not exist")
    void shouldReturnNotFoundWhenBalanceDoesNotExist() throws Exception {
        // Arrange - Preparação dos dados e mocks
        String nonExistentId = "123e4567-e89b-12d3-a456-426614174999";
        when(service.getById(nonExistentId)).thenThrow(
                new ResourceNotFoundException("Balance not found with ID: " + nonExistentId));


        mockMvc.perform(get("/api/balance/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

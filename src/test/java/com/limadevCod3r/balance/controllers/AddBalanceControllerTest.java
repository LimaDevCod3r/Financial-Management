package com.limadevCod3r.balance.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limadevCod3r.balance.dtos.CreateBalanceRequest;
import com.limadevCod3r.balance.mapper.BalanceMapper;
import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.AddBalanceService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AddBalanceControllerTest {

    @InjectMocks
    AddBalanceController controller;

    @Mock
    private AddBalanceService service;
    
    @Mock
    private BalanceMapper mapper;

    MockMvc mockMvc;

    private Balance balance;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .alwaysDo(print())
                .build();
                

                balance = new Balance();
                balance.setDescription("Exemple balance");
                balance.setAmount(new BigDecimal("100.00"));
    }


    @Test
    @DisplayName("It should successfully add a balance")
    void shouldAddBalanceSuccessfully() throws Exception {

        CreateBalanceRequest request = new CreateBalanceRequest("Exemple balance", new BigDecimal("100.00"));


        when(mapper.ConvertToEntity(any(CreateBalanceRequest.class))).thenReturn(balance);


        Balance savedBalance = new Balance();
        savedBalance.setId("123e4567-e89b-12d3-a456-426614174000");
        savedBalance.setDescription(balance.getDescription());
        savedBalance.setAmount(balance.getAmount());
        when(service.Add(any(Balance.class))).thenReturn(savedBalance);


        mockMvc.perform(post("/api/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print()) // Imprime detalhes da requisição/resposta no console
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}
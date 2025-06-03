package com.limadevCod3r.balance.controllers;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.services.DeleteBalanceService;
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


import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DeleteBalanceControllerTest {

    @InjectMocks
    DeleteBalanceController controller;

    @Mock
    DeleteBalanceService service;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionHandler)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("It should delete a balance by ID successfully.")
    void shouldDeleteBalanceSuccessfully() throws Exception {
        String balanceId = "123e4567-e89b-12d3-a456-426614174000";

        doNothing().when(service).delete(balanceId);

        mockMvc.perform(delete("/api/balance/{id}", balanceId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("It should return status 404 when the balance is not found.")
    void shouldReturnNotFoundWhenBalanceDoesNotExist() throws Exception {
        String nonExistentId = "non-existent-id";

        doThrow(new ResourceNotFoundException("Balance not found with ID: " + nonExistentId))
                .when(service).delete(nonExistentId);

        mockMvc.perform(delete("/api/balance/{id}",nonExistentId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

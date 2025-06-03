package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import com.limadevCod3r.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetBalanceServiceTest {

    @Mock
    private BalanceRepository repository;

    @InjectMocks
    private GetBalanceService service;

    private Balance balance;

    @BeforeEach
    void setUp() {
        balance = new Balance();
        balance.setId("123e4567-e89b-12d3-a456-426614174000");
        balance.setDescription("Exemplo of balance");
        balance.setAmount(new BigDecimal("200.00"));
    }

    @Test
    @DisplayName("It should return a balance by ID")
    void shouldReturnBalanceWhenIdExists() {
        String id = "123e4567-e89b-12d3-a456-426614174000";

        when(repository.findById(id)).thenReturn(Optional.of(balance));

        Balance result = service.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Exemplo of balance", result.getDescription());
        assertEquals(new BigDecimal("200.00"), result.getAmount());
    }

    @Test
    @DisplayName("It should throw ResourceNotFoundException when balance not found")
    void shouldThrowExceptionWhenBalanceNotFound() {
        String nonExistentId = "non-existent-id";

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getById(nonExistentId);
        });

        assertEquals("Balance not found with ID: " + nonExistentId, exception.getMessage());
    }
}

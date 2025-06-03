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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteBalanceServiceTest {

    @Mock
    private BalanceRepository repository;

    @InjectMocks
    private DeleteBalanceService service;

    private Balance balance;

    @BeforeEach
    void setUp() {
        balance = new Balance();
    }

    @Test
    @DisplayName("It should delete a balance by ID successfully.")
    void shouldDeleteBalanceWhenGivenValidId() {
        String balanceId = "123e4567-e89b-12d3-a456-426614174000";
        balance.setId(balanceId);

        when(repository.findById(balanceId)).thenReturn(Optional.of(balance));

        service.delete(balanceId);

        verify(repository, times(1)).delete(balance);

        verify(repository, times(1)).findById(balanceId);
    }

    @Test
    @DisplayName("It should throw ResourceNotFoundException when balance not found.")
    void shouldThrowExceptionWhenBalanceNotFound() {

        String nonExistentId = "non-existent-id";

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistentId);
        });

        verify(repository, never()).delete(any());
    }
}

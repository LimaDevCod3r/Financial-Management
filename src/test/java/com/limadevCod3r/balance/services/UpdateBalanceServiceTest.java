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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UpdateBalanceServiceTest {

    @Mock
    private BalanceRepository repository;

    @InjectMocks
    private UpdateBalanceService service;

    private Balance balance;

    @BeforeEach
    void setUp() {
        // Cria uma instância de Balance simulando um dado já existente no banco.
        balance = new Balance();
        balance.setId("123e4567-e89b-12d3-a456-426614174000");
        balance.setDescription("Exemplo of balance");
        balance.setAmount(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("It should successfully update a balance")
    void shouldUpdateBalance() {
        String id = "123e4567-e89b-12d3-a456-426614174000";

        // Cria um objeto Balance com as alterações que queremos aplicar.
        Balance updateData = new Balance();
        updateData.setDescription("Updated description");
        updateData.setAmount(new BigDecimal("200.00"));

        // Configura o mock repository para retornar o balance existente quando findById for chamado.
        when(repository.findById(id)).thenReturn(Optional.of(balance));

        // Cria um objeto Balance atualizado simulando o resultado esperado após o save.
        Balance updatedBalance = new Balance();
        updatedBalance.setId(id);
        updatedBalance.setDescription(updateData.getDescription());
        updatedBalance.setAmount(updateData.getAmount());

        // Configura o mock repository para retornar o balance atualizado quando save for chamado.
        when(repository.save(any(Balance.class))).thenReturn(updatedBalance);

        // Executa o método update na service.
        Balance result = service.update(id, updateData);

        // Realiza as asserções para garantir que o resultado é o esperado.
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated description", result.getDescription());
        assertEquals(new BigDecimal("200.00"), result.getAmount());
    }

    @Test
    @DisplayName("It should throw ResourceNotFoundException when the balance is not found")
    void shouldThrowExceptionWhenBalanceNotFound() {
        // Arrange
        String nonExistentId = "non-existent-id";
        Balance updateData = new Balance();
        updateData.setDescription("Updated description");

        // Configura o mock para retornar Optional.empty() quando findById for chamado com ID inexistente
        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistentId, updateData);
        });
    }
}

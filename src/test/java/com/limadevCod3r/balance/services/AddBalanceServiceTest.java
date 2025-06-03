package com.limadevCod3r.balance.services;

import com.limadevCod3r.balance.model.Balance;
import com.limadevCod3r.balance.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AddBalanceServiceTest {

    @Mock
    private BalanceRepository repository;

    @InjectMocks
    private AddBalanceService service;

    private Balance balance;

    @BeforeEach
    void setUp() {

        balance = new Balance();
        balance.setDescription("Exemplo of balance");
        balance.setAmount(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("It should successfully add a balance")
    void shouldAddBalanceSuccessfully(){

        Balance savedBalance = new Balance();

        savedBalance.setId("123e4567-e89b-12d3-a456-426614174000");

        savedBalance.setDescription(balance.getDescription());
        savedBalance.setAmount(balance.getAmount());


        when(repository.save(any(Balance.class))).thenReturn(savedBalance);


        Balance result = service.Add(balance);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", result.getId());

        assertEquals("Exemplo of balance", result.getDescription());
        assertEquals(new BigDecimal("100.00"), result.getAmount());
    }
}

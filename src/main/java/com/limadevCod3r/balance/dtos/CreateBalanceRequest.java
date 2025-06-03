package com.limadevCod3r.balance.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateBalanceRequest(
        @NotBlank(message = "The description is required and cannot be blank.")
        @Size(min = 3, max = 150, message = "The description must be between 3 and 150 characters long.")
        String description,

        @NotNull(message = "The amount is required.")
        @DecimalMin(value = "1.0", inclusive = true, message = "The amount must be greater than or equal to 1.")
        @Digits(integer = 10, fraction = 2, message = "The amount must have up to 10 digits and up to 2 decimal places.")
        BigDecimal amount
) {}

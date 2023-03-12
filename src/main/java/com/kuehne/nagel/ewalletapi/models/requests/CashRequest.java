package com.kuehne.nagel.ewalletapi.models.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRequest {

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal amount;
}

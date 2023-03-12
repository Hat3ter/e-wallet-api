package com.kuehne.nagel.ewalletapi.models.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest extends CashRequest {

    @NotNull
    private UUID walletId;

    public TransferMoneyRequest(UUID walletId, @NotNull @DecimalMin(value = "0.00") BigDecimal amount) {

        super(amount);
        this.walletId = walletId;
    }
}

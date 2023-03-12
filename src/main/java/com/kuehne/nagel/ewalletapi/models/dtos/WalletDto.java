package com.kuehne.nagel.ewalletapi.models.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kuehne.nagel.ewalletapi.utils.BalanceSerialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private UUID id;

    private String name;

    @JsonSerialize(using = BalanceSerialization.class)
    private BigDecimal balance;

    private String currencyType;
}

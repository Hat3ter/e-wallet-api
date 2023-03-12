package com.kuehne.nagel.ewalletapi.models.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kuehne.nagel.ewalletapi.utils.BalanceSerialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Wallet Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    /**
     * ID
     */
    private UUID id;

    /**
     * name
     */
    private String name;

    /**
     * balance
     */
    @JsonSerialize(using = BalanceSerialization.class)
    private BigDecimal balance;

    /**
     * currency type
     */
    private String currencyType;
}

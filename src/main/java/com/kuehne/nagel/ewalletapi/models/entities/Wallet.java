package com.kuehne.nagel.ewalletapi.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "wallets")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    /**
     * Id
     */
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;

    /**
     * Wallet name
     */
    @Column(name = "name")
    private String name;

    /**
     * Balance
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * Type of wallet currency
     */
    @Column(name = "currency_type")
    private String currencyType;
}

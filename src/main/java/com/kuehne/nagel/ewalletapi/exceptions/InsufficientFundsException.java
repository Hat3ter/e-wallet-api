package com.kuehne.nagel.ewalletapi.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Exception thrown when the requested operation cannot be completed due to insufficient funds in the account.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InsufficientFundsException extends RuntimeException {

    /**
     * The ID of the account that does not have enough funds.
     */
    private final UUID id;
}

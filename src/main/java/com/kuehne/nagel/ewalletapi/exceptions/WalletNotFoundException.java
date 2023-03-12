package com.kuehne.nagel.ewalletapi.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Exception thrown when a wallet with a given ID is not found in the system.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WalletNotFoundException extends RuntimeException {

    /**
     * The ID of the wallet that was not found.
     */
    private final UUID id;
}

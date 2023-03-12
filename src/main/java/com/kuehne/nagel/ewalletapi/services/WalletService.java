package com.kuehne.nagel.ewalletapi.services;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interface for managing wallet operations, such as retrieving wallet information, creating a new wallet, and
 * <p>
 * performing transactions such as cash in, cash out, and transfer.
 */
public interface WalletService {

    /**
     * Retrieves wallet information by its unique identifier.
     *
     * @param walletId the unique identifier of the wallet
     * @return the wallet DTO containing wallet information
     */
    WalletDto getWalletById(UUID walletId);

    /**
     * Creates a new wallet based on the provided wallet request.
     *
     * @param walletRequest the request for creating a new wallet
     * @return the wallet DTO containing wallet information for the newly created wallet
     */
    WalletDto createWallet(CreateWalletRequest walletRequest);

    /**
     * Retrieves the balance of a wallet by its unique identifier.
     *
     * @param walletId the unique identifier of the wallet
     * @return the balance of the wallet
     */
    BigDecimal getBalance(UUID walletId);

    /**
     * Performs a cash in operation on a wallet.
     *
     * @param walletId      the unique identifier of the wallet
     * @param cashInRequest the request for performing a cash in operation
     * @return the wallet DTO containing wallet information after the cash in operation
     */
    WalletDto cashIn(UUID walletId, WalletCashInRequest cashInRequest);

    /**
     * Performs a cash out operation on a wallet.
     *
     * @param walletId       the unique identifier of the wallet
     * @param cashOutRequest the request for performing a cash out operation
     * @return the wallet DTO containing wallet information after the cash out operation
     */
    WalletDto cashOut(UUID walletId, WalletCashOutRequest cashOutRequest);

    /**
     * Performs a transfer of funds between two wallets.
     *
     * @param walletId             the unique identifier of the source wallet
     * @param transferMoneyRequest the request for performing a transfer operation
     */
    void transferMoney(UUID walletId, TransferMoneyRequest transferMoneyRequest);

    /**
     * Retrieves a list of wallets belonging to the current user.
     *
     * @return a list of wallet DTOs containing wallet information for each wallet belonging to the user
     */
    List<WalletDto> getWalletsByUser();
}

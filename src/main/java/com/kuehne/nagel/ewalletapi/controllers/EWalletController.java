package com.kuehne.nagel.ewalletapi.controllers;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;
import com.kuehne.nagel.ewalletapi.models.response.ResponseApi;
import com.kuehne.nagel.ewalletapi.services.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a REST controller for managing wallet operations.
 */
@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class EWalletController {

    /**
     * The wallet service used by this controller.
     */
    private final WalletService walletService;

    /**
     * Retrieves a wallet by its ID.
     *
     * @param walletId The ID of the wallet to retrieve.
     * @return A ResponseApi object containing a WalletDto.
     */
    @GetMapping("/wallets/{walletId}")
    public ResponseApi<WalletDto> getWallet(@PathVariable UUID walletId) {

        log.info("#POST getWallet walletId={}", walletId);
        return new ResponseApi<>(walletService.getWalletById(walletId));
    }

    /**
     * Retrieves all wallets belonging to the currently authenticated user.
     *
     * @return A ResponseApi object containing a list of WalletDtos.
     */
    @GetMapping("/wallets")
    public ResponseApi<List<WalletDto>> getWalletsByUser() {

        log.info("#POST getWalletsByUser");
        return new ResponseApi<>(walletService.getWalletsByUser());
    }

    /**
     * Creates a new wallet for the currently authenticated user.
     *
     * @param walletRequest A CreateWalletRequest object containing the details of the new wallet to create.
     * @return A ResponseApi object containing a WalletDto representing the newly created wallet.
     */
    @PostMapping("/wallets")
    public ResponseApi<WalletDto> createWallet(@Valid @RequestBody CreateWalletRequest walletRequest) {

        log.info("#POST createWallet walletRequest={}", walletRequest);

        WalletDto wallet = walletService.createWallet(walletRequest);
        return new ResponseApi<>(wallet);
    }

    /**
     * Retrieves the balance of a wallet by its ID.
     *
     * @param walletId The ID of the wallet to retrieve the balance for.
     * @return A ResponseApi object containing a BigDecimal representing the balance of the specified wallet.
     */
    @GetMapping("/wallets/{walletId}/balance")
    public ResponseApi<?> getBalance(@PathVariable UUID walletId) {

        log.info("#GET getBalance walletId={}", walletId);

        BigDecimal balance = walletService.getBalance(walletId);
        return new ResponseApi<>(balance);
    }

    /**
     * Adds funds to a wallet by its ID.
     *
     * @param walletId      The ID of the wallet to add funds to.
     * @param cashInRequest A WalletCashInRequest object containing the details of the funds to add.
     * @return A ResponseApi object containing a WalletDto representing the updated wallet.
     */
    @PatchMapping("/wallets/{walletId}/cash-in")
    public ResponseApi<WalletDto> cashIn(@PathVariable UUID walletId,
                                         @Valid @RequestBody WalletCashInRequest cashInRequest) {

        log.info("#PATCH cashIn walletId={}, cashInRequest {}", walletId, cashInRequest);

        WalletDto wallet = walletService.cashIn(walletId, cashInRequest);
        return new ResponseApi<>(wallet);
    }

    /**
     * Withdraws funds from a wallet by its ID.
     *
     * @param walletId       The ID of the wallet to withdraw funds from.
     * @param cashOutRequest A WalletCashOutRequest object containing the details of the funds to withdraw.
     * @return A ResponseApi object containing a WalletDto representing the updated wallet.
     */
    @PatchMapping("/wallets/{walletId}/cash-out")
    public ResponseApi<WalletDto> cashOut(@PathVariable UUID walletId,
                                          @Valid @RequestBody WalletCashOutRequest cashOutRequest) {

        log.info("#PATCH cashOut walletId={}, cashInRequest {}", walletId, cashOutRequest);

        WalletDto wallet = walletService.cashOut(walletId, cashOutRequest);
        return new ResponseApi<>(wallet);
    }

    /**
     * Transfers money from one wallet to another.
     *
     * @param walletId             the ID of the wallet from which to transfer funds
     * @param transferMoneyRequest a TransferMoneyRequest object containing the transfer details
     * @return a ResponseApi containing a success message
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/wallets/{walletId}/transfer")
    public ResponseApi<String> transferMoney(@PathVariable UUID walletId,
                                             @Valid @RequestBody TransferMoneyRequest transferMoneyRequest) {

        log.info("#PATCH transferMoney walletId={}, transferMoneyRequest {}", walletId, transferMoneyRequest);

        walletService.transferMoney(walletId, transferMoneyRequest);
        return new ResponseApi<>("Success");
    }


}

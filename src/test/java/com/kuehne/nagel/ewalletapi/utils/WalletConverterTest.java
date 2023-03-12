package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletConverterTest {

    @Test
    public void convertToWallet_shouldReturnValidWallet() {

        CreateWalletRequest request = new CreateWalletRequest();
        request.setWalletName("Test Wallet");
        request.setCurrencyType("USD");

        Wallet wallet = WalletConverter.convertToWallet(request);

        assertEquals("Test Wallet", wallet.getName());
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertEquals("USD", wallet.getCurrencyType());
    }

    @Test
    public void convertToWalletDto_shouldConvertWalletToWalletDto() {

        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setName("My wallet");
        wallet.setBalance(new BigDecimal("100.00"));
        wallet.setCurrencyType("USD");

        WalletDto walletDto = WalletConverter.convertToWalletDto(wallet);

        assertEquals(wallet.getId(), walletDto.getId());
        assertEquals(wallet.getName(), walletDto.getName());
        assertEquals(wallet.getCurrencyType(), walletDto.getCurrencyType());
        assertEquals(wallet.getBalance(), walletDto.getBalance());

    }
}
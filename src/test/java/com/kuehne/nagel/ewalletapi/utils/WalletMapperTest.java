package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WalletMapperTest {

    @Test
    public void testConvertCreateWalletRequestToWallet() {

        CreateWalletRequest request = new CreateWalletRequest();
        request.setWalletName("request");
        request.setCurrencyType("usd");

        Wallet wallet = WalletMapper.INSTANCE.convert(request);

        assertEquals(request.getWalletName(), wallet.getName());
        assertEquals(request.getCurrencyType(), wallet.getCurrencyType());
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertNull(wallet.getId());
    }

    @Test
    public void testConvertWalletTOWalletDto() {

        Wallet wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setName("Wallet");
        wallet.setBalance(BigDecimal.TEN);
        wallet.setCurrencyType("USD");

        WalletDto walletDto = WalletMapper.INSTANCE.convert(wallet);

        assertEquals(wallet.getId(), walletDto.getId());
        assertEquals(wallet.getName(), walletDto.getName());
        assertEquals(wallet.getCurrencyType(), walletDto.getCurrencyType());
        assertEquals(wallet.getBalance(), walletDto.getBalance());


    }
}
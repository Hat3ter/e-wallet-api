package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletConverter {

    public static Wallet convertToWallet(CreateWalletRequest walletRequest) {

        Wallet wallet = new Wallet();
        wallet.setName(walletRequest.getWalletName());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrencyType(walletRequest.getCurrencyType());
        return wallet;
    }

    public static WalletDto convertToWalletDto(Wallet wallet) {

        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setName(wallet.getName());
        walletDto.setCurrencyType(wallet.getCurrencyType());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }
}

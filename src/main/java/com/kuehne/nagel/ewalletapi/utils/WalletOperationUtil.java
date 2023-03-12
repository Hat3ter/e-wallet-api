package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.exceptions.InsufficientFundsException;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletOperationUtil {

    public static BigDecimal getPotentialCashOutBalance(Wallet wallet, BigDecimal cashOutSum) {

        BigDecimal potentialBalance = wallet.getBalance().subtract(cashOutSum);
        if (potentialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException(wallet.getId());
        }
        return potentialBalance;
    }
}

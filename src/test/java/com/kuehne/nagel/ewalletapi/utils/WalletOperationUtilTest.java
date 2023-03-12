package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.exceptions.InsufficientFundsException;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletOperationUtilTest {

    @Test
    public void testGetPotentialCashOutBalance_SufficientFunds() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));
        BigDecimal cashOutSum = BigDecimal.valueOf(50);
        BigDecimal expectedBalance = BigDecimal.valueOf(50);

        BigDecimal actualBalance = WalletOperationUtil.getPotentialCashOutBalance(wallet, cashOutSum);

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void testGetPotentialCashOutBalance_InsufficientFunds() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));
        BigDecimal cashOutSum = BigDecimal.valueOf(150);

        assertThrows(InsufficientFundsException.class, () -> {
            WalletOperationUtil.getPotentialCashOutBalance(wallet, cashOutSum);
        });
    }

    @Test
    public void testGetPotentialCashOutBalance_ZeroBalance() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        BigDecimal cashOutSum = BigDecimal.valueOf(50);

        assertThrows(InsufficientFundsException.class, () -> {
            WalletOperationUtil.getPotentialCashOutBalance(wallet, cashOutSum);
        });
    }
}
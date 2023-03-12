package com.kuehne.nagel.ewalletapi.services;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;
import com.kuehne.nagel.ewalletapi.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WalletServiceImplTest {

    private WalletRepository walletRepository;

    private WalletService walletService;

    @BeforeEach
    public void setup() {

        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletServiceImpl(walletRepository);
    }

    @Test
    public void testGetWalletById() {

        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setName("My Wallet");
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setCurrencyType("USD");
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        WalletDto walletDto = walletService.getWalletById(walletId);

        assertEquals(walletDto.getId(), walletId);
        assertEquals(walletDto.getName(), "My Wallet");
        assertEquals(walletDto.getBalance(), BigDecimal.valueOf(100));
        assertEquals(walletDto.getCurrencyType(), "USD");
    }

    @Test
    public void testGetWalletsByUser() {

        Wallet wallet1 = new Wallet();
        wallet1.setId(UUID.randomUUID());
        wallet1.setName("My Wallet 1");
        wallet1.setBalance(BigDecimal.valueOf(100));
        wallet1.setCurrencyType("USD");
        Wallet wallet2 = new Wallet();
        wallet2.setId(UUID.randomUUID());
        wallet2.setName("My Wallet 2");
        wallet2.setBalance(BigDecimal.valueOf(200));
        wallet2.setCurrencyType("USD");
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
        when(walletRepository.findAll()).thenReturn(wallets);

        List<WalletDto> walletDtos = walletService.getWalletsByUser();

        assertEquals(walletDtos.size(), 2);
        assertEquals(walletDtos.get(0).getId(), wallet1.getId());
        assertEquals(walletDtos.get(0).getName(), wallet1.getName());
        assertEquals(walletDtos.get(0).getBalance(), wallet1.getBalance());
        assertEquals(walletDtos.get(0).getCurrencyType(), wallet1.getCurrencyType());
        assertEquals(walletDtos.get(1).getId(), wallet2.getId());
        assertEquals(walletDtos.get(1).getName(), wallet2.getName());
        assertEquals(walletDtos.get(1).getBalance(), wallet2.getBalance());
        assertEquals(walletDtos.get(1).getCurrencyType(), wallet2.getCurrencyType());
    }

    @Test
    public void testCreateWallet() {

        CreateWalletRequest request = new CreateWalletRequest();
        request.setWalletName("My Wallet");
        request.setCurrencyType("USD");
        when(walletRepository.save(Mockito.any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);

        WalletDto walletDto = walletService.createWallet(request);

        assertEquals(walletDto.getName(), "My Wallet");
        assertEquals(walletDto.getBalance(), BigDecimal.ZERO);
        assertEquals(walletDto.getCurrencyType(), "USD");
    }

    @Test
    public void testGetBalance() {

        UUID walletId = UUID.randomUUID();
        BigDecimal balance = BigDecimal.valueOf(100);
        when(walletRepository.getBalanceById(walletId)).thenReturn(balance);

        BigDecimal result = walletService.getBalance(walletId);

        assertEquals(result, balance);
    }

    @Test
    public void testCashIn() {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(50.0);
        WalletCashInRequest cashInRequest = new WalletCashInRequest();
        cashInRequest.setAmount(amount);

        Wallet wallet = new Wallet(walletId, "wallet", BigDecimal.ZERO, "USD");
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletDto walletDto = walletService.cashIn(walletId, cashInRequest);

        Mockito.verify(walletRepository).findById(walletId);
        Mockito.verify(walletRepository).save(wallet);

        assertEquals(amount, wallet.getBalance());
        assertEquals(walletDto.getId(), wallet.getId());
        assertEquals(walletDto.getBalance(), wallet.getBalance());
        assertEquals(walletDto.getName(), wallet.getName());
        assertEquals(walletDto.getCurrencyType(), wallet.getCurrencyType());
    }

    @Test
    public void testCashOut() {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(10.0);
        WalletCashOutRequest cashOutRequest = new WalletCashOutRequest();
        cashOutRequest.setAmount(amount);

        Wallet wallet = new Wallet(walletId, "wallet", BigDecimal.TEN, "USD");
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletDto walletDto = walletService.cashOut(walletId, cashOutRequest);

        Mockito.verify(walletRepository).findById(walletId);
        Mockito.verify(walletRepository).save(wallet);

        BigDecimal expectedBalance = BigDecimal.valueOf(0.0);
        assertEquals(expectedBalance, wallet.getBalance());
        assertEquals(walletDto.getId(), wallet.getId());
        assertEquals(walletDto.getBalance(), wallet.getBalance());
        assertEquals(walletDto.getName(), wallet.getName());
        assertEquals(walletDto.getCurrencyType(), wallet.getCurrencyType());
    }


    @Test
    public void testTransferMoney() {

        UUID fromWalletId = UUID.randomUUID();
        UUID toWalletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.TEN;

        Wallet fromWallet = new Wallet(fromWalletId, "wallet", BigDecimal.TEN, "USD");
        Wallet toWallet = new Wallet(toWalletId, "wallet", BigDecimal.ZERO, "USD");

        when(walletRepository.findById(fromWalletId)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(toWalletId)).thenReturn(Optional.of(toWallet));
        when(walletRepository.saveAll(Arrays.asList(fromWallet, toWallet))).thenReturn(Arrays.asList(fromWallet, toWallet));

        walletService.transferMoney(fromWalletId, new TransferMoneyRequest(toWalletId, amount));

        assertEquals(BigDecimal.ZERO, fromWallet.getBalance());
        assertEquals(BigDecimal.TEN, toWallet.getBalance());
        Mockito.verify(walletRepository).saveAll(Arrays.asList(fromWallet, toWallet));
    }
}
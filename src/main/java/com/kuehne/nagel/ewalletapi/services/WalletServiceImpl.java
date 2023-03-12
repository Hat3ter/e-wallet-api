package com.kuehne.nagel.ewalletapi.services;

import com.kuehne.nagel.ewalletapi.exceptions.WalletNotFoundException;
import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;
import com.kuehne.nagel.ewalletapi.repositories.WalletRepository;
import com.kuehne.nagel.ewalletapi.utils.WalletConverter;
import com.kuehne.nagel.ewalletapi.utils.WalletOperationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletDto getWalletById(UUID walletId) {

        return WalletConverter.convertToWalletDto(getWalletByIdOrThrowException(walletId));
    }

    @Override
    public List<WalletDto> getWalletsByUser() {

        return walletRepository.findAll().stream()
                .map(WalletConverter::convertToWalletDto)
                .toList();
    }


    @Override
    public WalletDto createWallet(CreateWalletRequest walletRequest) {

        Wallet wallet = WalletConverter.convertToWallet(walletRequest);

        wallet = walletRepository.save(wallet);

        return WalletConverter.convertToWalletDto(wallet);
    }

    @Override
    public BigDecimal getBalance(UUID walletId) {

        return walletRepository.getBalanceByWalletId(walletId);
    }

    @Override
    public WalletDto cashIn(UUID walletId, WalletCashInRequest cashInRequest) {

        Wallet wallet = getWalletByIdOrThrowException(walletId);
        wallet.setBalance(wallet.getBalance().add(cashInRequest.getAmount()));

        return WalletConverter.convertToWalletDto(walletRepository.save(wallet));
    }

    @Override
    public WalletDto cashOut(UUID walletId, WalletCashOutRequest cashOutRequest) {

        Wallet wallet = getWalletByIdOrThrowException(walletId);
        BigDecimal balance = WalletOperationUtil.getPotentialCashOutBalance(wallet, cashOutRequest.getAmount());
        wallet.setBalance(balance);

        return WalletConverter.convertToWalletDto(walletRepository.save(wallet));
    }

    @Override
    public void transferMoney(UUID walletId, TransferMoneyRequest transferRequest) {

        Wallet fromWallet = getWalletByIdOrThrowException(walletId);

        BigDecimal balanceFromWallet = WalletOperationUtil
                .getPotentialCashOutBalance(fromWallet, transferRequest.getAmount());
        fromWallet.setBalance(balanceFromWallet);

        Wallet toWallet = getWalletByIdOrThrowException(transferRequest.getWalletId());
        toWallet.setBalance(toWallet.getBalance().add(transferRequest.getAmount()));

        walletRepository.saveAll(Arrays.asList(fromWallet, toWallet));
    }


    public Wallet getWalletByIdOrThrowException(UUID walletId) {

        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }


}

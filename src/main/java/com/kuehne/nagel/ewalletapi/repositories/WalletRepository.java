package com.kuehne.nagel.ewalletapi.repositories;

import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Query(nativeQuery = true, value = "select balance from wallets where id = :walletId")
    BigDecimal getBalanceByWalletId(@Param("walletId") UUID walletId);
}

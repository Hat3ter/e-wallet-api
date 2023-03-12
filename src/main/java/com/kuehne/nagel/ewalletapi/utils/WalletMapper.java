package com.kuehne.nagel.ewalletapi.utils;

import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.entities.Wallet;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mappings({
            @Mapping(source = "walletName", target = "name"),
            @Mapping(source = "currencyType", target = "currencyType"),
            @Mapping(target = "balance", constant = "0"),
            @Mapping(target = "id", ignore = true)
    })
    Wallet convert(CreateWalletRequest request);

    WalletDto convert(Wallet wallet);
}

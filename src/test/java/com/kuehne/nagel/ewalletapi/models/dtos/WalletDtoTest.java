package com.kuehne.nagel.ewalletapi.models.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuehne.nagel.ewalletapi.AbstractSerializationTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletDtoTest extends AbstractSerializationTest {

    @Test
    public void shouldSerializeAndDeserializeCorrectly() throws JsonProcessingException {

        WalletDto walletDto = new WalletDto();
        walletDto.setId(UUID.randomUUID());
        walletDto.setName("Test Wallet");
        walletDto.setBalance(new BigDecimal("100.50"));
        walletDto.setCurrencyType("USD");

        String json = mapper.writeValueAsString(walletDto);
        WalletDto deserializedWalletDto = mapper.readValue(json, WalletDto.class);

        assertEquals(walletDto.getId(), deserializedWalletDto.getId());
        assertEquals(walletDto.getName(), deserializedWalletDto.getName());
        assertEquals(walletDto.getBalance().toString(), "100.50");
        assertEquals(walletDto.getCurrencyType(), deserializedWalletDto.getCurrencyType());
    }

}
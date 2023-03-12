package com.kuehne.nagel.ewalletapi.controllers;

import com.kuehne.nagel.ewalletapi.AbstractMvcTest;
import com.kuehne.nagel.ewalletapi.models.dtos.WalletDto;
import com.kuehne.nagel.ewalletapi.models.requests.CreateWalletRequest;
import com.kuehne.nagel.ewalletapi.models.requests.TransferMoneyRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashInRequest;
import com.kuehne.nagel.ewalletapi.models.requests.WalletCashOutRequest;
import com.kuehne.nagel.ewalletapi.services.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EWalletControllerTest extends AbstractMvcTest {

    @MockBean
    private WalletService walletService;

    @Test
    public void testGetWallet_shouldReturnValidResponse() throws Exception {

        UUID walletId = UUID.randomUUID();
        WalletDto wallet = new WalletDto(walletId, "wallet", BigDecimal.TEN, "USD");
        given(walletService.getWalletById(walletId)).willReturn(wallet);

        mockMvc.perform(get("/wallets/{walletId}", walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(walletId.toString())))
                .andExpect(jsonPath("$.data.balance", is("10.00")))
                .andExpect(jsonPath("$.data.currencyType", is("USD")))
                .andExpect(jsonPath("$.data.name", is("wallet")));

        verify(walletService, times(1)).getWalletById(walletId);
    }

    @Test
    public void testGetWalletsByUser_shouldReturnValidResponse() throws Exception {

        WalletDto wallet1 = new WalletDto(UUID.randomUUID(), "wallet", new BigDecimal("10.00"), "USD");
        WalletDto wallet2 = new WalletDto(UUID.randomUUID(), "wallet2", new BigDecimal("0.00"), "USD");
        List<WalletDto> wallets = Arrays.asList(wallet1, wallet2);
        given(walletService.getWalletsByUser()).willReturn(wallets);

        mockMvc.perform(get("/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(wallet1.getId().toString()))
                .andExpect(jsonPath("$.data[0].balance").value(wallet1.getBalance()))
                .andExpect(jsonPath("$.data[0].currencyType").value(wallet1.getCurrencyType()))
                .andExpect(jsonPath("$.data[0].name").value(wallet1.getName()))
                .andExpect(jsonPath("$.data[1].id").value(wallet2.getId().toString()))
                .andExpect(jsonPath("$.data[1].balance").value(wallet2.getBalance()))
                .andExpect(jsonPath("$.data[0].currencyType").value(wallet1.getCurrencyType()))
                .andExpect(jsonPath("$.data[0].name").value(wallet1.getName()));

        verify(walletService, times(1)).getWalletsByUser();
    }

    @Test
    public void testCreateWallet_shouldReturnValidResponse() throws Exception {

        UUID walletId = UUID.randomUUID();
        BigDecimal balance = BigDecimal.valueOf(100);
        String name = "My Wallet";
        String currencyType = "USD";
        WalletDto wallet = new WalletDto(walletId, name, balance, currencyType);
        CreateWalletRequest walletRequest = new CreateWalletRequest(name, currencyType);
        given(walletService.createWallet(any())).willReturn(wallet);

        mockMvc.perform(post("/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(walletId.toString()))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.balance").value("100.00"))
                .andExpect(jsonPath("$.data.currencyType").value(currencyType));

        verify(walletService, times(1)).createWallet(walletRequest);
    }

    @Test
    public void testGetBalance_shouldReturnValidResponse() throws Exception {

        UUID walletId = UUID.randomUUID();
        BigDecimal balance = BigDecimal.valueOf(100);
        given(walletService.getBalance(walletId)).willReturn(balance);

        mockMvc.perform(get("/wallets/" + walletId + "/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(balance));
    }

    @Test
    public void testCashIn_shouldReturnValidResponse() throws Exception {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(50);
        WalletCashInRequest cashInRequest = new WalletCashInRequest();
        cashInRequest.setAmount(amount);
        WalletDto wallet = new WalletDto(walletId, "Test Wallet", BigDecimal.valueOf(100), "USD");

        given(walletService.cashIn(walletId, cashInRequest)).willReturn(wallet);

        mockMvc.perform(patch("/wallets/" + walletId + "/cash-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cashInRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(walletId.toString()))
                .andExpect(jsonPath("$.data.name").value(wallet.getName()))
                .andExpect(jsonPath("$.data.balance").value("100.00"))
                .andExpect(jsonPath("$.data.currencyType").value(wallet.getCurrencyType()));
    }


    @Test
    public void testCashOut_shouldReturnValidResponse() throws Exception {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(50);
        WalletCashOutRequest cashOutRequest = new WalletCashOutRequest();
        cashOutRequest.setAmount(amount);
        WalletDto wallet = new WalletDto(walletId, "Test Wallet", BigDecimal.valueOf(100), "USD");

        given(walletService.cashOut(walletId, cashOutRequest)).willReturn(wallet);

        mockMvc.perform(patch("/wallets/" + walletId + "/cash-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cashOutRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(walletId.toString()))
                .andExpect(jsonPath("$.data.name").value(wallet.getName()))
                .andExpect(jsonPath("$.data.balance").value("100.00"))
                .andExpect(jsonPath("$.data.currencyType").value(wallet.getCurrencyType()));
    }

    @Test
    public void testTransferMoney_shouldReturnAcceptedStatus() throws Exception {

        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(50);
        UUID recipientWalletId = UUID.randomUUID();
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.setWalletId(recipientWalletId);
        transferMoneyRequest.setAmount(amount);


        mockMvc.perform(patch("/wallets/" + walletId + "/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(transferMoneyRequest)))
                .andExpect(status().isAccepted());
        verify(walletService, times(1)).transferMoney(walletId, transferMoneyRequest);
    }
}
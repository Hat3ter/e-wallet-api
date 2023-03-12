package com.kuehne.nagel.ewalletapi.controllers;

import com.kuehne.nagel.ewalletapi.exceptions.InsufficientFundsException;
import com.kuehne.nagel.ewalletapi.exceptions.WalletNotFoundException;
import com.kuehne.nagel.ewalletapi.models.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Exception handler for wallet-related exceptions.
 */
@Slf4j
@RestControllerAdvice
public class WalletExceptionHandler {

    /**
     * Handles WalletNotFoundException.
     *
     * @param e the exception to handle
     * @return an ErrorResponse containing the ID of the wallet that was not found and a message indicating that the wallet was not found
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WalletNotFoundException.class)
    public ErrorResponse<UUID> handleWalletNotFoundException(WalletNotFoundException e) {

        log.warn("Fetched handleWalletNotFoundException {}", e.getMessage());
        return new ErrorResponse<>(e.getId(), "wallet not found");
    }

    /**
     * Handles InsufficientFundsException.
     *
     * @param e the exception to handle
     * @return an ErrorResponse containing the ID of the wallet that has insufficient funds and a message indicating that there is not enough money in the account for the operation
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientFundsException.class)
    public ErrorResponse<UUID> handleInsufficientFundsException(InsufficientFundsException e) {

        log.warn("Fetched handleInsufficientFundsException {}", e.getMessage());
        return new ErrorResponse<>(e.getId(), "not enough money in the account for the operation");
    }

    /**
     * Handles MethodArgumentNotValidException.
     *
     * @param e the exception to handle
     * @return an ErrorResponse containing a map of field names and error messages, and a concatenated error message string
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {

        log.warn("Fetched handleValidationExceptions {}", e.getDetailMessageArguments());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String errorAsString = errors.entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .reduce((s, s2) -> s + " " + s2)
                .orElse(Strings.EMPTY);
        log.warn("Fetched handleValidationExceptions errors {}", errors);
        return new ErrorResponse<>(errors, errorAsString);
    }
}

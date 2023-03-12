package com.kuehne.nagel.ewalletapi.models.response;

public record ErrorResponse<T>(T data, String errorMessage) {

}

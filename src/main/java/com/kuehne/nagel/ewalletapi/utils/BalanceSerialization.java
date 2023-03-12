package com.kuehne.nagel.ewalletapi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Custom Jackson serializer for BigDecimal values representing balances. It serializes a BigDecimal
 * <p>
 * to a string with two decimal places, rounded using the HALF_UP rounding mode.
 */
public class BalanceSerialization extends JsonSerializer<BigDecimal> {

    /**
     * Serializes a BigDecimal value to a string with two decimal places, rounded using the HALF_UP rounding mode.
     *
     * @param value    the BigDecimal value to serialize
     * @param jgen     the JsonGenerator to use for outputting the serialized value
     * @param provider the SerializerProvider to use for accessing serializers and serializers
     * @throws IOException if there is an error writing to the JsonGenerator
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeString(value.setScale(2, RoundingMode.HALF_UP).toString());
    }
}
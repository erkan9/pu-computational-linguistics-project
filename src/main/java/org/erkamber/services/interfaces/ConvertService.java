package org.erkamber.services.interfaces;

import org.erkamber.dtos.TextDto;

import java.io.IOException;

public interface ConvertService {

    /**
     * Converts the currencies found in the input text to the desired currency
     * and saves the converted text to the database. Returns the converted text as a TextDto object.
     *
     * @param inputText         the text to convert currencies in
     * @param convertToCurrency the currency to convert the currencies to
     * @return object containing the converted text
     */
    TextDto convertCurrencies(String inputText, String convertToCurrency) throws IOException;

}
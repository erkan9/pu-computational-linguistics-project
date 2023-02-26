package org.erkamber.services.implementations;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.erkamber.configurations.EuropeanCentralBankApiKeyConfiguration;
import org.erkamber.configurations.PatternConfiguration;
import org.erkamber.entities.Currency;
import org.erkamber.entities.Text;
import org.erkamber.dtos.TextDto;
import org.erkamber.mappers.TextMapper;
import org.erkamber.repositories.TextRepository;
import org.erkamber.services.interfaces.ConvertService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConvertServiceImpl implements ConvertService {

    private final EuropeanCentralBankApiKeyConfiguration apiKeyConfiguration;

    private final TextRepository textRepository;

    private final TextMapper textMapper;

    private final PatternConfiguration patternConfiguration;

    public ConvertServiceImpl(EuropeanCentralBankApiKeyConfiguration apiKeyConfiguration, TextRepository textRepository,
                              TextMapper textMapper, PatternConfiguration patternConfiguration) {

        this.apiKeyConfiguration = apiKeyConfiguration;
        this.textRepository = textRepository;
        this.textMapper = textMapper;
        this.patternConfiguration = patternConfiguration;
    }

    /**
     * Converts the currencies found in the given input text to the desired currency specified by the user.
     * First, it extracts the currencies from the input text using a regular expression pattern.
     * Then, it converts each extracted currency to the desired currency using an external API call.
     * Finally, it replaces the original currencies in the input text with their converted values and returns a DTO object
     * containing the converted text.
     *
     * @param inputText         inputText the input text to be converted
     * @param convertToCurrency the desired currency to which the extracted currencies should be converted
     * @return a TextDto object containing the converted text
     */
    @Override
    public TextDto convertCurrencies(String inputText, String convertToCurrency) throws IOException {

        ArrayList<Currency> listOfNotConvertedCurrencies = getCurrencyPatternsFromInputText(inputText);

        ArrayList<Currency> listOfConvertedCurrencies = convertListOfCurrenciesToDesiredCurrency(listOfNotConvertedCurrencies, convertToCurrency);

        String inputTextConverted = replaceCurrencies(inputText, listOfConvertedCurrencies);

        Text text = new Text(inputTextConverted);

        textRepository.save(text);

        return textMapper.mapTextToTextDTO(text);
    }

    /**
     * Replaces currencies in the input text with their converted values in the desired currency.
     *
     * @param inputText                 the input text to replace the currencies in
     * @param listOfConvertedCurrencies the list of converted currencies to replace the currencies in the input text with
     * @return the input text with the currencies replaced with their converted values
     */
    private String replaceCurrencies(String inputText, ArrayList<Currency> listOfConvertedCurrencies) {

        StringBuilder sb = new StringBuilder();

        int lastIndex = 0;
        int currencyIndex = 0;

        Pattern pattern = patternConfiguration.currencyPatternMatch();

        Matcher matcher = pattern.matcher(inputText);

        while (matcher.find()) {

            sb.append(inputText, lastIndex, matcher.start());

            if (currencyIndex < listOfConvertedCurrencies.size()) {

                sb.append(listOfConvertedCurrencies.get(currencyIndex).toString());

                currencyIndex++;

            } else {

                sb.append(matcher.group());
            }

            lastIndex = matcher.end();
        }

        sb.append(inputText.substring(lastIndex));

        return sb.toString();
    }

    /**
     * This method extracts all currency patterns from the input text and returns them as an ArrayList of Currency objects.
     * <p>
     * A currency pattern is defined as a string in the format of three uppercase letters representing a currency code,
     * <p>
     * followed by a hyphen, followed by a decimal number with up to two decimal places representing a currency value.
     *
     * @param inputText the input text to extract currency patterns from
     * @return an ArrayList of Currency objects representing the currency patterns found in the input text
     */
    private ArrayList<Currency> getCurrencyPatternsFromInputText(String inputText) {

        ArrayList<Currency> listOfNotConvertedCurrencies = new ArrayList<>();

        Pattern CURRENCY_PATTERN = patternConfiguration.currencyPatternMatch();

        Matcher matcher = CURRENCY_PATTERN.matcher(inputText);

        while (matcher.find()) {

            String currencyCode = matcher.group(1);

            double currencyValue = Double.parseDouble(matcher.group(2));

            Currency currency = new Currency(currencyCode, currencyValue);

            listOfNotConvertedCurrencies.add(currency);
        }

        return listOfNotConvertedCurrencies;
    }

    /**
     * Convert a list of currencies to a desired currency.
     *
     * @param notConvertedCurrencies the list of currencies to be converted.
     * @param convertTo              the desired currency to convert the currencies to.
     * @return an ArrayList of Currency objects, with their values converted to the desired
     */
    private ArrayList<Currency> convertListOfCurrenciesToDesiredCurrency(ArrayList<Currency> notConvertedCurrencies, String convertTo) throws IOException {

        ArrayList<Currency> listOfConvertedCurrencies = new ArrayList<>();

        for (Currency notConvertedCurrency : notConvertedCurrencies) {

            String convertFrom = notConvertedCurrency.getCurrency();

            double amountToConvert = notConvertedCurrency.getCurrencyValue();

            double convertedAmount = getApiConvertedAmount(convertFrom, convertTo, amountToConvert);

            Currency currency = new Currency(convertTo, convertedAmount);

            listOfConvertedCurrencies.add(currency);
        }

        return listOfConvertedCurrencies;
    }

    /**
     * Calls an external API to convert a given amount from one currency to another.
     *
     * @param convertFrom The currency to convert from
     * @param convertTo   The currency to convert to
     * @param amount      The amount to be converted
     * @return The converted amount
     * @throws IOException If an I/O error occurs while communicating with the external API
     */
    private double getApiConvertedAmount(String convertFrom, String convertTo, double amount) throws IOException {

        JSONObject resultJson;

        HttpGet request = new HttpGet("https://api.apilayer.com/exchangerates_data/convert?to=" + convertTo + "&from=" + convertFrom + "&amount=" + amount);
        request.addHeader("apikey", apiKeyConfiguration.getApiKey());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();

             CloseableHttpResponse response = httpClient.execute(request)) {

            String responseBody = EntityUtils.toString(response.getEntity());

            resultJson = new JSONObject(responseBody);
        }
        return resultJson.getDouble("result");
    }
}
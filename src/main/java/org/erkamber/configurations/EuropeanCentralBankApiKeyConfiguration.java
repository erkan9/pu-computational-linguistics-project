package org.erkamber.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EuropeanCentralBankApiKeyConfiguration {

    @Value("${app.european.central.bank.api.key}")
    String apiKey;

    /**
     * Returns the API key for the European Central Bank.
     * <p>
     * The API key is read from the application configuration using the
     * property "app.european.central.bank.api.key".
     *
     * @return the API key for the European Central Bank
     */
    public String getApiKey() {

        return apiKey;
    }
}
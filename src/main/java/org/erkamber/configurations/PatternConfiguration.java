package org.erkamber.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class PatternConfiguration {

    /**
     * Returns a regular expression pattern for matching currency values.
     * <p>
     * The pattern matches currency values in the format "CCC-NNN.NN", where
     * CCC is a 3 to 5 character currency code and NNN.NN is a decimal value.
     * The regular expression pattern is compiled and returned as a Pattern object.
     *
     * @return a Pattern object for matching currency values
     */
    @Bean
    public Pattern currencyPatternMatch() {

        return Pattern.compile("\\b([A-Z]{3,5})-(\\d+(?:\\.\\d{1,2})?)\\b");
    }
}
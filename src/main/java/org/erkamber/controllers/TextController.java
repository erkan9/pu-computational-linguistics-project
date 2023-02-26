package org.erkamber.controllers;

import org.erkamber.services.implementations.ConvertServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@Validated
public class TextController {

    private final ConvertServiceImpl convertServiceImpl;

    public TextController(ConvertServiceImpl convertServiceImpl) {

        this.convertServiceImpl = convertServiceImpl;
    }

    /**
     * Converts the currencies in the given text to the specified currency and returns the converted text.
     * <p>
     * This method takes in the text to be converted and the currency to convert to. It then calls the
     * `convertCurrencies()` method of the `convertServiceImpl` object with these arguments to perform the
     * conversion. The converted text is then retrieved from the resulting `TextConversionResult` object
     * and returned by the method.
     *
     * @param text the text to be converted
     * @param convertToCurrency the currency to convert to
     * @return the converted text
     */
    @PostMapping(value = "/convert", params = {"convertToCurrency"})
    public String getText( @RequestBody String text, @RequestParam String convertToCurrency) throws IOException {

        return convertServiceImpl.convertCurrencies(text, convertToCurrency).getText();
    }
}
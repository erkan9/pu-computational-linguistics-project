package org.erkamber.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class TextDto {

    @PositiveOrZero
    private int textID;

    @NotBlank(message = "Text cannot be Blank")
    @NotEmpty(message = "Text cannot be Empty")
    @NotNull(message = "Text cannot be null")
    @Size(min = 8, max = 255, message = "Text size should be at least 8 letters long")
    private String text;

    public TextDto() {
    }

    public TextDto(String text) {
        this.text = text;
    }

    public TextDto(int textID, String text) {
        this.textID = textID;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
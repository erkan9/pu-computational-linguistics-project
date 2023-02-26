package org.erkamber.mappers;

import org.erkamber.entities.Text;
import org.erkamber.dtos.TextDto;
import org.springframework.stereotype.Component;

@Component
public class TextMapper {

    /**
     * Maps a Text object to a TextDto object.
     * @param text the Text object to map
     * @return the mapped TextDto object
     */
    public TextDto mapTextToTextDTO(Text text) {

        return new TextDto(text.getTextID(), text.getText());
    }
}
package org.erkamber.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "texts")
@Getter
@Setter
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int textID;

    @Column(name = "text_content", length = 255, unique = false, updatable = true, insertable = true, nullable = false)
    private String text;

    public Text() {
    }

    public Text(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
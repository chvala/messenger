package hu.progmatic.modell;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class Message {
    @NotNull
    @Size(min = 2, max = 30, message = "Not between {2} and {1} characters!")
    @Pattern(regexp = "^[A-Z].*", message = "Doesn't start with capital letter")
    private String author;


    @NotNull
    @Size(min = 2, max = 30)
    @NotBlank
    private String text;
    private boolean isHidden = false;

    public boolean isHidden() {
        return isHidden;
    }

    public void isHidden(boolean answer) {
        isHidden = answer;
    }

    @Past
    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm")
    private LocalDateTime creationDate;
    private Integer ID;

    public Message() {

    }

    public Message(Integer ID, String author, String text, LocalDateTime creationDate) {
        this.ID = ID;
        this.author = author;
        this.creationDate = creationDate;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

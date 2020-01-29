package hu.progmatic.dto;

import hu.progmatic.modell.Topic;

public class MessageServiceDTO {
    private int ID;
    private String author;
    private String text;
    private String topic;

    public int getID() {
        return ID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return author;
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


}

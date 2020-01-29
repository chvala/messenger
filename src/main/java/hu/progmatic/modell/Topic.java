package hu.progmatic.modell;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

@Entity
public class Topic {

    @Column(unique = true)
    @NotNull
    private String title;

    @OneToMany(cascade = REMOVE, mappedBy = "topic")
    @JsonIgnore
    private List<Message> messages;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    public Topic(String title, List<Message> message) {
        this.title = title;
        this.messages = message;
    }

    public Topic() {

    }

    public Topic(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }
}

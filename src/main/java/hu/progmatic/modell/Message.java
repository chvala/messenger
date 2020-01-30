package hu.progmatic.modell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Message")
public class Message {


    @Column(name = "author")
    // @NotNull
    // @Size(min = 2, max = 30, message = "Not between {2} and {1} characters!")
    // @Pattern(regexp = "^[A-Z].*", message = "Doesn't start with capital letter")
    private String author;

    @Column(name = "text", length = 1000)
    @NotNull
    @NotBlank
    private String text;

    private boolean isHidden = false;


    public void isHidden(boolean answer) {
        isHidden = answer;
    }

    @Column(name = "creationDate")
    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm")
    private LocalDateTime creationDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @ManyToOne
    private myUser myuser;
    

    @OneToMany(mappedBy = "messageForComment")
    @JsonIgnore
    private List<Message> comments;

    @ManyToOne
    private Message messageForComment;

    public List<Message> getComments() {
        return comments;
    }

    public void setComments(List<Message> comments) {
        this.comments = comments;
    }

    @ManyToOne
    private Topic topic;

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Message() {

    }

    public Message getMessageForComment() {
        return messageForComment;
    }

    public void setMessageForComment(Message messageForComment) {
        this.messageForComment = messageForComment;
    }

    public Message(String author, String text, LocalDateTime creationDate) {
        this.author = author;
        this.creationDate = creationDate;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public Integer getID() {
        return ID;
    }

    //public void setID(Integer ID) {
    //    this.ID = ID;
    //}

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

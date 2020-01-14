package hu.progmatic.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashMap;

@Component
@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)

public class UserSessionDetails {
    private String name;
    private int sentMessagesCounter;
    private String email;
    private LocalDate registrationTime; // = LocalDate.now();
    private HashMap<String, Integer> stats = new HashMap<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDate registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getSentMessagesCounter() {
        return sentMessagesCounter;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }


    public void setStats(HashMap<String, Integer> stats) {
        this.stats = stats;
    }

    public void setSentMessagesCounter(int sentMessagesCounter) {
        this.sentMessagesCounter = sentMessagesCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

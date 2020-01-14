package hu.progmatic.services;

import hu.progmatic.modell.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {
    public static int counter = 1;
    public List<Message> messages = new ArrayList<>();

    {
        messages.add(new Message(counter++, "Aladár", "Mz/x jelentkezz jelentkezz", LocalDateTime.now()));
        messages.add(new Message(counter++, "Kriszta", "Hanem Tiszta vidd vissza majd a Kriszta megissza", LocalDateTime.now()));
        messages.add(new Message(counter++, "Béla", "Ez a Béla üzenete mindenkienki részére", LocalDateTime.now()));
        messages.add(new Message(counter++, "Tódor", "Miau, okos tódór", LocalDateTime.now()));
        messages.add(new Message(counter++, "Valaki", "Valaki aki nem Te vagy én.", LocalDateTime.now()));
        messages.add(new Message(counter++, "Te", "Vau Vau Kutya vagy", LocalDateTime.now()));
        messages.add(new Message(counter++, "Béla2", "Béla 2. üzenete mindenkienkinek.", LocalDateTime.now()));
        messages.add(new Message(counter++, "Zsófi", "Sziasztok", LocalDateTime.now()));
        messages.add(new Message(counter++, "User", "Ez a szövege egy usernak", LocalDateTime.now()));
        messages.add(new Message(counter++, "Béla5", "Béla 3. üzenete.", LocalDateTime.now()));
    }

    public List<Message> filterMessages(String nameOrder, Integer max, Integer ID, String text) {
        List<Message> filteredMessages = messages;
        // System.out.println(userSessionDetails.getName());
        Stream<Message> filteredMessagesStream = filteredMessages.stream();


        if (nameOrder != null) {
            if (nameOrder.equals("abc")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getAuthor));
            } else if (nameOrder.equals("cba")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getAuthor).reversed());
            } else if (nameOrder.equals("123")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getID));
            }

        }

        filteredMessages = filteredMessagesStream
                .filter(m -> text == null ||
                        m.getText().contains(text) ||
                        m.getAuthor().contains(text) ||
                        m.getCreationDate().toString().contains(text))
                .collect(Collectors.toList());


        return filteredMessages;
    }

    public Message getMessage(Integer ID) {
        return messages.get(ID);
    }

    public int getSize() {
        return messages.size();
    }

    public void add(Message message) {
        messages.add(message);
    }

    public void delete(Integer ID) {
        messages.removeIf(message -> message.getID().equals(ID));
    }

}

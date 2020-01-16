package hu.progmatic.services;

import hu.progmatic.modell.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {


    @PersistenceContext
    EntityManager em;


    private static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    public static int counter = 1;

    //  public List<Message> messages = new ArrayList<>();
    //  {
    //      messages.add(new Message(counter++, "Aladár", "Mz/x jelentkezz jelentkezz", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Kriszta", "Hanem Tiszta vidd vissza majd a Kriszta megissza", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Béla", "Ez a Béla üzenete mindenkienki részére", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Tódor", "Miau, okos tódór", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Valaki", "Valaki aki nem Te vagy én.", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Te", "Vau Vau Kutya vagy", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Béla2", "Béla 2. üzenete mindenkienkinek.", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Zsófi", "Sziasztok", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "User", "Ez a szövege egy usernak", LocalDateTime.now()));
    //      messages.add(new Message(counter++, "Béla5", "Béla 3. üzenete.", LocalDateTime.now()));
    //  }


    public List<Message> filterMessages(String nameOrder, Integer max, Integer ID, String text, boolean isHidden) {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        List<Message> filteredMessages = messages;
        LOGGER.info("filteredMessages method started");
        LOGGER.debug("id: {}, nameOrder: {}, text: {}", ID, nameOrder, text);
        Stream<Message> filteredMessagesStream = filteredMessages.stream();


        if (isHidden) {
            filteredMessagesStream = filteredMessagesStream.filter(m -> !m.isHidden());
        }

        if (nameOrder != null) {
            if (nameOrder.equals("abc")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getAuthor));
            } else if (nameOrder.equals("cba")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getAuthor).reversed());
            } else if (nameOrder.equals("123")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(Message::getID));
            } else if (nameOrder.equals("isDeleted")) {
                filteredMessagesStream = filteredMessagesStream.filter(Message::isHidden);
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

    @Transactional
    public Message getMessage(Integer ID) {
        Message message = em.createQuery(
                "SELECT m FROM Message m where m.ID=:ID", Message.class)
                .setParameter("ID", ID)
                .getSingleResult();

        return message;
    }

    public int getSize() {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();

        return messages.size();
    }

    public void add(Message message) {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        em.persist(message);

        messages.add(message);
    }

    public void delete(Integer ID) {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();

        messages.removeIf(message -> message.getID().equals(ID));
    }

}

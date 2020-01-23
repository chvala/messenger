package hu.progmatic.services;

import hu.progmatic.modell.Message;
import hu.progmatic.modell.Topic;
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

    public List<Message> filterMessages(String nameOrder, Integer max, Integer ID, String text, boolean isHidden, Integer topicID) {
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
            } else if (nameOrder.equals("topic")) {
                filteredMessagesStream = filteredMessagesStream.sorted(Comparator.comparing(message -> message.getTopic().getTitle()));
            }
        }
        if (topicID != null) {
            filteredMessagesStream = filteredMessagesStream.filter(message -> message.getTopic().getID()==topicID);
        }
        filteredMessages = filteredMessagesStream
                .filter(m -> text == null ||
                        m.getText().contains(text) ||
                        m.getAuthor().contains(text) ||
                        m.getCreationDate().toString().contains(text) ||
                        m.getTopic().getTitle().contains(text))
                .collect(Collectors.toList());
        return filteredMessages;
    }

    @Transactional
    public Message getMessage(Integer ID) {
        Message message = em.find(Message.class, ID);
        return message;
    }

    public int getSize() {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();

        return messages.size();
    }

    @Transactional
    public void add(Message message) {
        Topic topic = em.createQuery("Select t FROM Topic t where t.title=:name", Topic.class)
                .setParameter("name", message.getTopic().getTitle()).getSingleResult();
        message.setTopic(topic);
        em.persist(message);
    }

    public void hide(Integer ID) {
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        messages.removeIf(message -> message.getID().equals(ID));
    }

    @Transactional
    public void delete(Integer ID) {
        Message message = em.createQuery(
                "SELECT m FROM Message m where m.ID=:ID", Message.class)
                .setParameter("ID", ID)
                .getSingleResult();
        em.remove(message);
    }
}

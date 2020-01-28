package hu.progmatic.services;

import hu.progmatic.dto.MessageServiceDTO;
import hu.progmatic.modell.Message;
import hu.progmatic.modell.Message_;
import hu.progmatic.modell.Topic;
import hu.progmatic.modell.Topic_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;


    private static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public List<Message> filterMessages(String nameOrder, Integer max, Integer ID, String text, LocalDate time, boolean isHidden, Integer topicID) {
        LOGGER.info("filteredMessages method started");
        LOGGER.debug("id: {}, nameOrder: {}, text: {}", ID, nameOrder, text);

        /////////--------
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cQuery = cb.createQuery(Message.class);
        Root<Message> messageRoot = cQuery.from(Message.class);
        /////////--------
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        Stream<Message> filteredMessagesStream = messages.stream();
        /////////--------


        if (isHidden) {
            filteredMessagesStream = filteredMessagesStream.filter(m -> !m.isHidden());
        }


        if (nameOrder != null) {
            switch (nameOrder) {
                case "abc":
                    cQuery = cQuery.orderBy(cb.asc(messageRoot.get(Message_.author)));
                    break;
                case "cba":
                    cQuery = cQuery.orderBy(cb.desc(messageRoot.get(Message_.author)));
                    break;
                case "123":
                    cQuery = cQuery.orderBy(cb.asc(messageRoot.get(Message_.I_D)));
                    break;
                case "isDeleted":
                    cQuery = cQuery.orderBy(cb.asc(messageRoot.get(Message_.isHidden)));
                    break;
                case "topic":
                    cQuery = cQuery.orderBy(cb.asc(messageRoot.get(Message_.topic.getName())));
                    break;
            }
            filteredMessagesStream = em.createQuery(cQuery).getResultList().stream();
        }
        if (topicID != null) {
            filteredMessagesStream = filteredMessagesStream.filter(message -> message.getTopic().getID() == topicID);
        }
        if (time != null) {
            cQuery.select(messageRoot).where(cb.between(messageRoot.get(Message_.creationDate), time.atStartOfDay(), time.atTime(23, 59)));
            // time.plusDays(1).atStartOfDay())
            filteredMessagesStream = em.createQuery(cQuery).getResultList().stream();
        }
        if (text.length() > 2) {
            LOGGER.debug("id: {}, text: {}", ID, text);

            cQuery.select(messageRoot).where(cb.or(

                    (cb.like(messageRoot.get(Message_.author), "%" + text + "%")),
                    (cb.like(messageRoot.get(Message_.text), "%" + text + "%")),
                    (cb.equal(messageRoot.get(Message_.topic).get(Topic_.title), text))));
            LOGGER.debug("Author: " + Message_.author);
            LOGGER.debug("MessageText: " + Message_.text);

            filteredMessagesStream = em.createQuery(cQuery).getResultList().stream();
        }
        return filteredMessagesStream.collect(Collectors.toList());
    }

    @Transactional
    public Message getMessage(Integer ID, Integer sleep) throws InterruptedException {
        Message message = em.find(Message.class, ID);
        Thread.sleep(sleep * 1000);
        Message message2 = em.find(Message.class, ID);

        return message;
    }

    @Transactional
    public Message getMessage(Integer ID) {
        Message message = em.find(Message.class, ID);
        return message;
    }

    public int getSize() {
        return em.createQuery("SELECT m FROM Message m", Message.class).getResultList().size();
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

    public List<MessageServiceDTO> findAllMessages() {
        List<Message> msgs = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        return msgs.stream().map(message -> {
            MessageServiceDTO dto = new MessageServiceDTO();
            dto.setAuthor(message.getAuthor());
            dto.setHidden(message.isHidden());
            dto.setID(message.getID());
            dto.setText(message.getText());
            dto.setTopic(message.getTopic());
            return dto;
        }).collect(Collectors.toList());
    }


    @Transactional
    public boolean delete(Integer ID) {
        if (getMessage(ID) != null) {
            Message message = em.createQuery(
                    "SELECT m FROM Message m where m.ID=:ID", Message.class)
                    .setParameter("ID", ID)
                    .getSingleResult();
            em.remove(getMessage(ID));
            return true;
        }
        return false;
    }
}

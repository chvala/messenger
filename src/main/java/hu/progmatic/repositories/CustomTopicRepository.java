package hu.progmatic.repositories;

import hu.progmatic.modell.Topic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomTopicRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(Topic topic) {
        em.persist(topic);
    }

    public List<Topic> findAll() {
        return em.createQuery("select t from Topic t", Topic.class).getResultList();
    }

    public void delete(Integer ID) {
        Topic topic = em.find(Topic.class, ID);
        em.remove(topic);
    }
    public Topic getTopic(String title) {
        Topic topic = em.createQuery(
                "SELECT m FROM Message m where m.topic.title=:topic", Topic.class)
                .getSingleResult();
        return topic;
    }

    public Topic getTopic(Integer ID) {
        return em.find(Topic.class, ID);
    }

    public boolean isExist(Topic topic) {
        List<Topic> topics = em.createQuery("SELECT t FROM Topic t", Topic.class).getResultList();
        for (Topic oneTopic : topics) {
            if (oneTopic.getTitle().equals(topic.getTitle())) {
                return true;
            }
        }
        return false;
    }

}


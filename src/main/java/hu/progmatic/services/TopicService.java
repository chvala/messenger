package hu.progmatic.services;

import hu.progmatic.modell.Topic;
import hu.progmatic.repositories.CustomTopicRepository;
import hu.progmatic.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional
    public Topic getTopic(Integer ID) {
        return topicRepository.getOne(ID);
    }

    @Transactional
    public Topic getTopic(String title) {
        return topicRepository.findByTitle(title);
    }

    @Transactional
    public boolean topicExist(Topic topic) {
        return topicRepository.existsById(topic.getID());
    }

    @Transactional
    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public List<Topic> loadTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public void deleteTopic(Integer ID) {
        topicRepository.deleteById(ID);
    }


}

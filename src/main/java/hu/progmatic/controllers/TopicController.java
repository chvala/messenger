package hu.progmatic.controllers;

import hu.progmatic.modell.Topic;
import hu.progmatic.services.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;


@Controller
public class TopicController {

    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
    @PersistenceContext
    EntityManager em;
    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping(value = "/newTopic")
    public String newTopic(@ModelAttribute("Topic") Topic topic) {
        return "newTopic";
    }

    @Transactional
    @PostMapping(value = "/deleteTopic")
    public String deleteTopic(@ModelAttribute("Topic") Topic topic) {
        logger.info("Ez a title: " + topic.getTitle());
        topicService.deleteTopic(topic.getID());
        return "redirect:/topicTable";
    }

    @Transactional
    @PostMapping(path = "/createTopic")
    public String createTopic(@Valid @ModelAttribute("Topic") Topic topic, BindingResult bindingResult) {
        if (topicService.topicExist(topic)) {
            bindingResult.rejectValue("title", "title", "Title already exist");
            return "newTopic";
        } else if (bindingResult.hasErrors()) {
            return "newTopic";
        } else {
            em.persist(topic);
            return "redirect:/newMessage";
        }
    }

    @GetMapping(value = "/topicTable/{ID}")
    public String deleteTopic(@PathVariable("ID") int ID, Model model) {
            topicService.deleteTopic(ID);
        return "redirect:/topicTable";
    }


    @GetMapping(value = {"/topicTable"})
    public String loadTopics(Model model, Topic topic) {
        List<Topic> topics = topicService.loadTopics();
        model.addAttribute("topicList", topics);
        return "topicTable";
    }
}
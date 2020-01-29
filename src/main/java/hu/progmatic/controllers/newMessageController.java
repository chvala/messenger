package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.modell.Topic;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.MessageService;
import hu.progmatic.session.UserSessionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Controller
public class newMessageController {

    private static final Logger logger = LoggerFactory.getLogger(newMessageController.class);
    @PersistenceContext
    EntityManager em;

    private MessageService messageService;
    private UserSessionDetails userSessionDetails;


    @Autowired
    public newMessageController(MessageService messageService, UserSessionDetails userSessionDetails) {
        this.messageService = messageService;
        this.userSessionDetails = userSessionDetails;
    }

    @GetMapping(value = {"/newMessage"})
    public String newMessage(Model model, Topic topic) {
        Message m = new Message();
        myUser user = (myUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        m.setAuthor(user.getUsername());

        model.addAttribute("Message", m);
        model.addAttribute(topic);

        List<Topic> topics = em.createQuery("SELECT t FROM Topic t", Topic.class).getResultList();
        model.addAttribute("topicList", topics);

        return "newMessage";
    }

    @Transactional
    @PostMapping(path = "/createMessage")
    public String createMessage(@Valid @ModelAttribute("Message") Message m, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "newMessage";
        }
        myUser user = (myUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        HashMap<String, Integer> stats = userSessionDetails.getStats();
        stats.putIfAbsent(name, 0);
        stats.put(name, stats.get(name) + 1);

        m.setAuthor(name);
        userSessionDetails.setName(name);
        userSessionDetails.setSentMessagesCounter(userSessionDetails.getSentMessagesCounter() + 1);
        m.setCreationDate(LocalDateTime.now());

        messageService.add(m);

        logger.info("Üzenet feladója (userSessionDetails): "+userSessionDetails.getName());
        logger.info("Üzenet szövege m.getText(): "+m.getText());
        logger.info("User neve SecurityContextHolder: "+name);

        return "redirect:/messagetable";
    }


}

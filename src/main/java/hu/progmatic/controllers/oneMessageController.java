package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.MessageService;
import hu.progmatic.session.UserSessionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class oneMessageController {

    @PersistenceContext
    EntityManager em;


    @Autowired
    public oneMessageController(MessageService messageService, UserSessionDetails userSessionDetails) {
        this.messageService = messageService;
        this.userSessionDetails = userSessionDetails;
    }

    private MessageService messageService;
    private UserSessionDetails userSessionDetails;


    @Transactional
    @RequestMapping(value = {"/createComment/{ID}"}, method = POST)
    public String createComment(@RequestParam(value = "text") String text,
                                @PathVariable(value = "ID") Integer ID, Model model) {

        myUser user = (myUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();


        String author = name;
        String commentText = text;
        LocalDateTime creationDate = LocalDateTime.now();
        Message comment = new Message(author, commentText, creationDate);
        model.addAttribute("oneMessage", comment);
        Message originalMessage = em.createQuery("select m from Message m where m.ID=:ID", Message.class).setParameter("ID", ID).getSingleResult();
        comment.setMessageForComment(originalMessage);
        comment.setTopic(originalMessage.getTopic());
        em.persist(comment);
        int commentID = comment.getID();


        return "redirect:/messagetable/" + originalMessage.getID();
    }
}

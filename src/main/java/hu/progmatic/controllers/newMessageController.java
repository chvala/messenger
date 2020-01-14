package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.MessageService;
import hu.progmatic.session.UserSessionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
public class newMessageController {
    private MessageService messageService;
    private UserSessionDetails userSessionDetails;
    private myUser myuser;


    @Autowired
    public newMessageController(MessageService messageService, UserSessionDetails userSessionDetails, myUser myuser) {
        this.messageService = messageService;
        this.myuser = myuser;
        this.userSessionDetails = userSessionDetails;
    }


    @RequestMapping(value = {"/newMessage"}, method = GET)
    public String newMessage(Model model) {
        Message m = new Message();
        model.addAttribute("Message", m);
        m.setAuthor(userSessionDetails.getName());
        return "newMessage";
    }


    @PostMapping(path = "/createMessage")
    public String createMessage(@Valid @ModelAttribute("Message") Message m, BindingResult bindingResult, myUser myuser) {

        if (bindingResult.hasErrors()) {
            return "newMessage";
        }
        String name = m.getAuthor();

        HashMap<String, Integer> stats = userSessionDetails.getStats();
        stats.putIfAbsent(name, 0);
        stats.put(name, stats.get(name) + 1);


        myUser user = (myUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userSessionDetails.setName(user.getUsername());
        userSessionDetails.setSentMessagesCounter(userSessionDetails.getSentMessagesCounter() + 1);
        m.setCreationDate(LocalDateTime.now());
        m.setID(messageService.getSize() + 1);

        messageService.add(m);
        return "redirect:/messagetable";
    }

}

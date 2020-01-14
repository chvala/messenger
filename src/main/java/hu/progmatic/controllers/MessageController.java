package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MessageController {



    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping(value = "/messagetable/{userId}")
    public String showUserData(
            @PathVariable("userId") int userId, Model model) {
        model.addAttribute("messageList", messageService.getMessage(userId));
        return "Message";
    }


    @RequestMapping(value = {"/messagetable"}, method = GET)
    public String loadMessages(@RequestParam(value = "max", required = false, defaultValue = "15") Integer max,
                               @RequestParam(value = "ID", required = false, defaultValue = "0") Integer ID,
                               @RequestParam(value = "nameOrder", required = false, defaultValue = "123") String nameOrder,
                               @RequestParam(value = "text", defaultValue = "", required = false) String text,
                               @RequestParam(value = "hide", defaultValue = "true", required = false) boolean isHidden,
                               Model model) {
        List<Message> filteredMessages = messageService.filterMessages(nameOrder, max, ID, text, isHidden);
        model.addAttribute("messageList", filteredMessages);
        return "Message";
    }


}


package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MessageController {


    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messagetable/{userId}")
    public String showOneMessage(@PathVariable("userId") int userId, Model model) {
        Message message = messageService.getMessage(userId);
        Message newMessage = new Message();
        model.addAttribute("Message", message);
        model.addAttribute("newMessage", newMessage);
        List<Message> comments = message.getComments();
        logger.info(String.valueOf(comments.size()));
        model.addAttribute("comments", comments);
        return "oneMessage";
    }

    @RequestMapping(value = {"/messagetable"}, method = GET)
    public String loadMessages(@RequestParam(value = "max", required = false, defaultValue = "15") Integer max,
                               @RequestParam(value = "ID", required = false, defaultValue = "0") Integer ID,
                               @RequestParam(value = "nameOrder", required = false, defaultValue = "123") String nameOrder,
                               @RequestParam(value = "text", defaultValue = "", required = false) String text,
                               @RequestParam(value = "hide", defaultValue = "true", required = false) boolean isHidden,
                               @RequestParam(value = "topicID", required = false) Integer topicID,
                               Model model) {
        List<Message> filteredMessages = messageService.filterMessages(nameOrder, max, ID, text, isHidden, topicID);
        model.addAttribute("messageList", filteredMessages);
        return "Message";
    }


}


package hu.progmatic.controllers;

import hu.progmatic.dto.MessageServiceDTO;
import hu.progmatic.modell.Message;
import hu.progmatic.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public String showOneMessage(@PathVariable("userId") int userId,
                                 @RequestParam(value = "sleep", required = false, defaultValue = "0") Integer sleep,
                                 Model model) throws InterruptedException {
        Message message = messageService.getMessage(userId, sleep);
        Message newMessage = new Message();
        model.addAttribute("Message", message);
        model.addAttribute("newMessage", newMessage);
        List<Message> comments = message.getComments();
        model.addAttribute("comments", comments);
        return "oneMessage";
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @RequestMapping(value = {"/modifymessage"}, method = GET)
    public String loadModifiedMessages(
            @RequestParam(value = "ID") Integer ID,
            @RequestParam(value = "text") String text,
            @RequestParam(value = "sleep") Integer secondsToSleep, Model model
    ) throws InterruptedException {
        Message msg = messageService.getMessage(ID, secondsToSleep);
        msg.setText(text);
        List<Message> msgs = new ArrayList<>();
        msgs.add(msg);
        model.addAttribute("messageList", msgs);
        Thread.sleep(1000 * secondsToSleep);
        return "Message";
    }

    @DeleteMapping(path = "messagetable/delete/{ID}")
    public @ResponseBody boolean restDelete(@PathVariable Integer ID) {
        return messageService.delete(ID);
    }


    @RequestMapping(value = {"/messagetable"}, method = GET)
    public String loadMessages(@RequestParam(value = "max", required = false) Integer max,
                               @RequestParam(value = "ID", required = false) Integer ID,
                               @RequestParam(value = "nameOrder", required = false) String nameOrder,
                               @RequestParam(value = "text",  required = false, defaultValue = "")String text,
                               @RequestParam(value = "time",  required = false, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate time,
                               @RequestParam(value = "hide", required = false) boolean isHidden,
                               @RequestParam(value = "topicID", required = false) Integer topicID,
                               Model model) {
        List<Message> filteredMessages = messageService.filterMessages(nameOrder, max, ID, text,time, isHidden, topicID);
        model.addAttribute("messageList", filteredMessages);
        return "Message";
    }


}


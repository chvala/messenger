package hu.progmatic.controllers;

import hu.progmatic.dto.MessageServiceDTO;
import hu.progmatic.modell.Message;
import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RestMessageController {

    private MessageService messageService;

    @Autowired
    public RestMessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/rest/messagetable/{userId}")
    public Message showOneMessage(@PathVariable("userId") int userId) {
        return messageService.getMessage(userId);
    }

    @RequestMapping(path = "/rest/messagetable/jason", method = RequestMethod.GET)
    public List<MessageServiceDTO> messageTableJson() {
        List<MessageServiceDTO> allMessages = messageService.findAllMessages();
        return allMessages;
    }

    @DeleteMapping(path = "/rest/messagetable/delete/{ID}")
    public boolean restDelete(@PathVariable Integer ID) {
        return messageService.delete(ID);
    }

    @GetMapping(value = {"/rest/messagetable"})
    public List<Message> loadMessages(@RequestParam(value = "max", required = false) Integer max,
                                      @RequestParam(value = "ID", required = false) Integer ID,
                                      @RequestParam(value = "nameOrder", required = false) String nameOrder,
                                      @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                      @RequestParam(value = "time", required = false, defaultValue = "")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate time,
                                      @RequestParam(value = "hide", required = false) boolean isHidden,
                                      @RequestParam(value = "topicID", required = false) Integer topicID,
                                      Model model) {
        return messageService.filterMessages(nameOrder, max, ID, text, time, isHidden, topicID);
    }


}

package hu.progmatic.controllers;

import hu.progmatic.dto.MessageServiceDTO;
import hu.progmatic.modell.Message;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    //  @GetMapping(path = "/rest/messagetable/jason")
    //  public List<MessageServiceDTO> messageTableJson() {
    //      List<MessageServiceDTO> allMessages = messageService.findAllMessages();
    //      return allMessages;
    //  }

    @Transactional
    @DeleteMapping(path = "/rest/messagetable/delete/{ID}")
    public boolean restDelete(@PathVariable Integer ID) {
        return messageService.delete(ID);
    }

    @GetMapping(value = {"/rest/messagetable"})
    public List<MessageServiceDTO> loadMessages(@RequestParam(value = "max", required = false) Integer max,
                                                @RequestParam(value = "ID", required = false) Integer ID,
                                                @RequestParam(value = "nameOrder", required = false) String nameOrder,
                                                @RequestParam(value = "text", required = false, defaultValue = "") String text,
                                                @RequestParam(value = "time", required = false, defaultValue = "")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate time,
                                                @RequestParam(value = "hide", required = false) boolean isHidden,
                                                @RequestParam(value = "topicID", required = false) Integer topicID,
                                                Model model) {
        List<Message> allMessagesList = messageService.filterMessages(nameOrder, max, ID, text, time, isHidden, topicID);


        return messageService.convertMessageListToDTOList(allMessagesList);
    }

    @Transactional
    @PostMapping(path = "/rest/createMessage")
    public Message createMessage(@Valid @RequestBody Message m) {

        myUser user = (myUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        m.setAuthor(name);
        m.setCreationDate(LocalDateTime.now());
        messageService.add(m);

        return m;
    }


}

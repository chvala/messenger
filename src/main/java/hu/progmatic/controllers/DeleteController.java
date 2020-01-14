package hu.progmatic.controllers;

import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeleteController {


    private MessageService messageService;
    @Autowired
    public DeleteController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = {"/delete/{ID}"})
    public String delete(@PathVariable Integer ID) {
        messageService.delete(ID);
        return "redirect:/messagetable";
    }

}

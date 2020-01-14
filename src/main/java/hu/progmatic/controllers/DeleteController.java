package hu.progmatic.controllers;

import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/delete/{ID}"})
    public String delete(@PathVariable Integer ID) {
        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            // messageService.delete(ID);
            messageService.getMessage(ID - 1).isHidden(true);
        }
        return "redirect:/messagetable";
    }

}

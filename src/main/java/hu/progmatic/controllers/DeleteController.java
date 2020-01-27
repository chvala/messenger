package hu.progmatic.controllers;

import hu.progmatic.modell.Topic;
import hu.progmatic.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Controller
public class DeleteController {

    @PersistenceContext
    EntityManager em;

    private MessageService messageService;

    @Autowired
    public DeleteController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/hide/{ID}"})
    public String hide(@PathVariable Integer ID) {
        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            messageService.getMessage(ID).isHidden(true);
            messageService.hide(ID);
        }
        return "redirect:/messagetable";
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/delete/{ID}"})
    public String delete(@PathVariable Integer ID) {
            messageService.delete(messageService.getMessage(ID).getID());
        return "redirect:/messagetable";
    }

}

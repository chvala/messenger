package hu.progmatic.controllers;

import hu.progmatic.modell.Authorities;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegisterController {
    @PersistenceContext
    EntityManager em;

    private static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping(value = {"/Register"})
    public String Register(@ModelAttribute("user") myUser user) {
        LOGGER.info("Register method started");
        return "Register";
    }

    @PostMapping(value = {"/Register"})
    @Transactional
    public String registration(@Valid @ModelAttribute("user") myUser user, BindingResult bindingResult, Model model) {

        if (userService.userExists(user.getUsername())) {
            bindingResult.rejectValue("username", "username", "Username already exist");
            return "Register";
        } else if (bindingResult.hasErrors()) {
            return "Register";
        } else {

            Set<Authorities> authoritiesSet = new HashSet<>();
            authoritiesSet.add((Authorities) em.createQuery("select a from Authorities a where a.authoritie like 'ROLE_USER'").getSingleResult());
            user.setAuthorities(authoritiesSet);
        }


        userService.createUser(user);

        LOGGER.debug("username: {}, password: {}, Authorities: {}, birthDate: {}", user.getUsername(), user.getPassword(), user.getAuthorities(), user.getBirthDate());


        return "redirect:/Login";
    }

}





package hu.progmatic.controllers;

import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class RegisterController {

    private static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @RequestMapping(value = {"/Register"}, method = GET)
    public String Register(@ModelAttribute("user") myUser user) {

        LOGGER.info("Register method started");

        return "Register";
    }

    @PostMapping(value = {"/Register"})

    public String registration(@Valid @ModelAttribute("user") myUser user, BindingResult bindingResult, Model model) {

        if (userService.userExists(user.getUsername())) {
            bindingResult.rejectValue("username", "username", "Username already exist");
            return "Register";
        } else if (bindingResult.hasErrors()) {
            return "Register";
        } else {
            userService.createUser(user);
            LOGGER.debug("username: {}, password: {}, Authorities: {}, birthDate: {}", user.getUsername(), user.getPassword(), user.getAuthorities(), user.getBirthDate());


            HashMap<String, myUser> alluserMap = userService.getUsers();
            alluserMap.put(user.getUsername(), user);


            return "redirect:/Login";
        }

    }
}




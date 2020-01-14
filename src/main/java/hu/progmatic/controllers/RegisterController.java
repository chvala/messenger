package hu.progmatic.controllers;

import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class RegisterController {


    @Autowired
    public RegisterController(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = (InMemoryUserDetailsManager) userDetailsService;
        this.userService = userService;
    }

    private UserService userService;
    private InMemoryUserDetailsManager userDetailsService;

    @RequestMapping(value = {"/Register"}, method = GET)
    public String Register(@ModelAttribute("user") myUser user) {


        return "Register";
    }

    @PostMapping(value = {"/Register"})

    public String registration(@Valid @ModelAttribute("user") myUser user, BindingResult bindingResult, Model model) {


        if (userDetailsService.userExists(user.getUsername())) {
            bindingResult.rejectValue("username", "username", "User already exist");
            return "Register";

        } else {
            userDetailsService.createUser(user);
            System.out.println(user.getBirthDate());
            System.out.println(user.getEmail());

            HashMap<String, myUser> alluserMap = userService.getUsers();
            List<myUser> allUserList = new ArrayList<>();
            for (myUser oneUser : alluserMap.values()) {
                allUserList.add(oneUser);
            }
            model.addAttribute("allUserList", allUserList);
            return "redirect:/Login";
        }

    }
}


// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
// String hashedPassword = passwordEncoder.encode(password);

package hu.progmatic.controllers;

import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class UsersController {


    private UserService userService;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/usersTable/{username}")
    public String delete(@PathVariable("username") String username) {
        myUser oneUser = userService.getUsers().get(username);
        userService.getUsers().remove(oneUser.getUsername());

        return "redirect:/usersTable";
    }

    @PostMapping(value = "/usersTable/{username}")
    public String changeRole(@PathVariable("username") String username,
                             @RequestParam("changeRole") String role) {


        myUser changeUser = (myUser) userService.loadUserByUsername(username);
        changeUser.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(role)));
        return "redirect:/usersTable";
    }

    @RequestMapping(value = {"/usersTable"}, method = GET)
    public String loadUsers(Model model) {

        List<myUser> usersTable = new ArrayList<>();
        usersTable.addAll(userService.getUsers().values());

        model.addAttribute("usersTable", usersTable);
        return "usersTable";
    }

}

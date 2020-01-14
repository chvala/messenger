package hu.progmatic.controllers;

import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class UsersController {


    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/usersTable")
    public String showUserData(Model model, myUser myuser) {
        model.addAttribute("messageList", myuser);
        return "usersTable";
    }


    @RequestMapping(value = {"/usersTable"}, method=POST)
    public String loadUsers(Model model) {

        List<myUser> usersTable = new ArrayList<>();
        usersTable.addAll(userService.getUsers().values());
        model.addAttribute("usersTable", usersTable);
        return "usersTable";
    }

}

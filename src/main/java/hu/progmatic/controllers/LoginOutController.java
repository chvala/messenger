package hu.progmatic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class LoginOutController {


    public LoginOutController() {
    }

    @RequestMapping(value = {"/Login"}, method = GET)
    public String login() {
        return "Login";
    }



}

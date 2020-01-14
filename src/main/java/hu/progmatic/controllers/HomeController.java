package hu.progmatic.controllers;

import hu.progmatic.config.WebSecConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {
    static HashMap<String, String> lang = new HashMap<>();

    static {
        lang.put("hun", "Szia");
        lang.put("eng", "Welcome");
        lang.put("jp", "Koniciuwa");
    }

    @RequestMapping(value = {"/", "/home"}, method = GET)
    public String home(@RequestParam(value = "name", required = false) String userName,
                       @RequestParam(value = "lang", required = false, defaultValue = "eng") String language, Model model) {
        if (!lang.containsKey(language)) {
        } else {
            if (userName == null) {
                userName = "valaki";
            }
            model.addAttribute("message", lang.get(language) +" " + userName);
        }
        return "home";
    }

}

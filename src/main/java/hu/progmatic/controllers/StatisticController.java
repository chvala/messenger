package hu.progmatic.controllers;

import hu.progmatic.session.UserSessionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class StatisticController {
    private UserSessionDetails userSessionDetails;

    @Autowired
    public StatisticController(UserSessionDetails userSessionDetails) {
        this.userSessionDetails = userSessionDetails;
    }

    static HashMap<String, Integer> nameAndMessagesCounter = new HashMap<>();


    @RequestMapping(value = {"/Statistic"}, method = GET)
    public String Statistic(Model model) {
        Set<Map.Entry<String, Integer>> statSet = userSessionDetails.getStats().entrySet();
        model.addAttribute("entrySet", statSet);

        return "Statistic";
    }
}

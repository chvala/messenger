package hu.progmatic.controllers;

import hu.progmatic.modell.Authorities;
import hu.progmatic.modell.myUser;
import hu.progmatic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class UsersController {

    @PersistenceContext
    EntityManager em;

    private UserService userService;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/usersTable/{username}")
    public String delete(@PathVariable("username") String username) {
        myUser oneUser = em.createQuery("select myUser from myUser where myUser.username=:username", myUser.class).getSingleResult();
        userService.delete(oneUser);
        return "redirect:/usersTable";
    }

    @Transactional
    @PostMapping(value = "/usersTable/{username}")
    public String changeRole(@PathVariable("username") String username,
                             @RequestParam("changeRole") String role) {

        myUser changeUser = (myUser) userService.loadUserByUsername(username);
        Set<Authorities> authoritiesSet = new HashSet<>();
        if (role.equals("ROLE_USER")) {
            authoritiesSet.add((Authorities) em.createQuery("select a from Authorities a where a.authoritie like 'ROLE_USER'").getSingleResult());
        } else if (role.equals("ROLE_ADMIN")) {
            authoritiesSet.add((Authorities) em.createQuery("select a from Authorities a where a.authoritie like 'ROLE_ADMIN'").getSingleResult());
        }
        changeUser.setAuthorities(authoritiesSet);

        return "redirect:/usersTable";
    }

    @RequestMapping(value = {"/usersTable"}, method = GET)
    public String loadUsers(Model model) {
        List<myUser> usersTable = new ArrayList<>(em.createQuery("select u from myUser u", myUser.class).getResultList());
        model.addAttribute("usersTable", usersTable);
        return "usersTable";
    }

}

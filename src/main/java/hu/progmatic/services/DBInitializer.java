package hu.progmatic.services;


import hu.progmatic.modell.Authorities;
import hu.progmatic.modell.myUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class DBInitializer {

    private UserService userService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public DBInitializer(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onAppStartup(ContextRefreshedEvent ev) throws ServletException {
        DBInitializer me = ev.getApplicationContext().getBean(DBInitializer.class);
        me.settings();
    }

    @Transactional
    public void settings() {
        if (em.createQuery("select u from myUser u", myUser.class).getResultList().isEmpty()) {
            myUser admin = new myUser("admin", "admin", "$2a$10$ckQ7zwnJDV/j14/Rib2F6uYtmDwGN/e32exSUQg6KhHmU/TrgSoFa", "admin@admin.com", LocalDate.now());
            myUser user = new myUser("user", "password", "$2a$10$oCRGXXFIaR85U.9PqcxXTeo1quoAphJNFbZriNQVdv7CrQTW.GS32", "user@user.com", LocalDate.now());
            Authorities myadmin = new Authorities("ROLE_ADMIN");
            Authorities myuser = new Authorities("ROLE_USER");
            em.persist(myadmin);
            em.persist(myuser);
            admin.setAuthorities(Collections.singleton(myadmin));
            user.setAuthorities(Collections.singleton(myuser));

            userService.createUser(admin);
            userService.createUser(user);
        }
    }
}

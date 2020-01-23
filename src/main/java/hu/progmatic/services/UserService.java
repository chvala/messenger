package hu.progmatic.services;

import hu.progmatic.modell.Authorities;
import hu.progmatic.modell.myUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;

// CREATE SCHEMA `messengerapp` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci ;
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(@Valid myUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    public void delete(myUser user) {
        em.remove(user);
    }

    public myUser getUser(myUser user) {
        return em.createQuery("select u from myUser u where u=:user", myUser.class).setParameter("user", user).getSingleResult();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            myUser user = em.createQuery("select u from myUser u where u.username=:username", myUser.class).setParameter("username", username).getSingleResult();
            return user;
        } catch (NoResultException e) {
            logger.debug("User was not found: {}", username);
            throw new UsernameNotFoundException("User was not found: " + username);
        }
    }

    public boolean userExists(String user) {
        try {
            em.createQuery("select u from myUser u where u=:user", myUser.class).setParameter("user", user).getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

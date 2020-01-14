package hu.progmatic.services;

import hu.progmatic.modell.myUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;

@Service
public class UserService implements UserDetailsService {

    private HashMap<String, myUser> users = new HashMap<>();

    public UserService() {
        creatUser();
    }

    public void createUser(myUser user) {
        users.put(user.getUsername(), user);
    }

    public void creatUser() {
        myUser admin = new myUser("admin", "admin", "admin@admin.com", LocalDate.now());
        myUser user = new myUser("user", "password", "user@user.com", LocalDate.now());
        createUser(admin);
        admin.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        createUser(user);
        user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public void delete(myUser user) {
        users.remove(user.getUsername());
    }

    public myUser getUser(myUser user) {
        return users.get(user.getUsername());
    }

    public HashMap<String, myUser> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, myUser> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            throw new UsernameNotFoundException("User was not found: " + username);
        }
    }

    public boolean userExists(String user) {
        return users.containsKey(user);
    }


}

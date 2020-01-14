package hu.progmatic.services;

import hu.progmatic.modell.myUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    private HashMap<String, myUser> users = new HashMap<>();

    public void upLoad(myUser user) {
        users.put(user.getUsername(), user);
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
}

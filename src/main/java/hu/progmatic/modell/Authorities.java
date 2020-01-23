package hu.progmatic.modell;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Transactional
@Entity
@Table(name = "Authorities")
public class Authorities implements GrantedAuthority {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String authoritie;


    @ManyToMany(mappedBy = "authorities")
    private List<myUser> userAuthorities;

    public Integer getID() {
        return ID;
    }

    public Authorities(String authoritie) {
        this.authoritie = authoritie;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

        // userAuthorities.addAuthority("ROLE_USER");


    public List<myUser> getUsersAuthorities() {
        return userAuthorities;
    }

    public void setUsersAuthorities(List<myUser> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }



    public Authorities() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String getAuthority() {
        return authoritie;
    }
}

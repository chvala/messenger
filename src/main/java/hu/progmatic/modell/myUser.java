package hu.progmatic.modell;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.REMOVE;
@Entity
public class myUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "username")
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;

    public String getRePassword() {
        return rePassword;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "birthDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "authorities")
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authorities> authorities = new HashSet<>();

    @OneToMany(cascade = REMOVE, mappedBy = "myuser")
    private List<Message> message;

    public myUser(String username, String password, String rePassword, String email, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.birthDate = birthDate;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public myUser() {
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void addAuthority(String auth) {
        authorities.add(new Authorities(auth));
    }

}
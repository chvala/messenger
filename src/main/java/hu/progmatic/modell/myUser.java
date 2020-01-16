package hu.progmatic.modell;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Controller
public class myUser implements UserDetails {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @NotBlank
    private String email;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public myUser(String username, String password, String email, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }
    public myUser() {

    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

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
        authorities.add(new SimpleGrantedAuthority(auth));
    }
}
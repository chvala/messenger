package hu.progmatic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/Login").permitAll()
                .defaultSuccessUrl("/messagetable", true)
                .loginProcessingUrl("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/loginpage")
                .and()
                .authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/Register").permitAll()
                .antMatchers("/delete/*").hasRole("ADMIN")
                .antMatchers("/webjars/bootstrap/**", "/webjars/jquery/**", "/webjars/popper.js/**").permitAll()

                .antMatchers("/Statistic").hasRole("ADMIN")

                .anyRequest().authenticated();
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/registerusers")
    // @PreAuthorize("hasAuthority('GLOBAL_ADMINISTRATOR')")
    // public String registerUsers(Model model) {
    //     model.addAttribute("user", new User());
    //     return "userRegistration";
    // }

   /* @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").
                password("password").roles("USER").build());
        manager.createUser(User.withUsername("admin").
                password("admin").roles("ADMIN").build());
        return manager;
    }*/


    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
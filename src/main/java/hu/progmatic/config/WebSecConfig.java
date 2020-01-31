package hu.progmatic.config;

import hu.progmatic.services.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
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
                .antMatchers("/oauth_login").permitAll()
                .antMatchers("/style/*", "/images/*").permitAll()
                .antMatchers("/delete/*", "/usersTable").hasRole("ADMIN")
                .antMatchers("/webjars/bootstrap/**", "/webjars/jquery/**", "/webjars/popper.js/**").permitAll()
                .antMatchers("/Statistic").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/Login").permitAll()
                .defaultSuccessUrl("/messagetable", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
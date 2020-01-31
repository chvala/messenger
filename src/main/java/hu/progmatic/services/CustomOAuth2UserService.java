package hu.progmatic.services;

import hu.progmatic.exceptions.OAuth2AuthenticationProcessingException;
import hu.progmatic.modell.Authorities;
import hu.progmatic.modell.GoogleOAuth2UserInfo;
import hu.progmatic.modell.OAuth2UserInfo;
import hu.progmatic.modell.myUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        try {
            UserDetails userDetails = userService.loadUserByUsername(oAuth2UserInfo.getEmail());
            return (OAuth2User) userDetails;
        } catch (UsernameNotFoundException ex) {
            myUser myUser = new myUser();
            Set<Authorities> authoritiesSet = new HashSet<>();
            authoritiesSet.add((Authorities) em.createQuery("select a from Authorities a where a.authoritie like 'ROLE_USER'").getSingleResult());
            myUser.setAuthorities(authoritiesSet);
            myUser.setUsername(oAuth2UserInfo.getEmail());
            myUser.setEmail(oAuth2UserInfo.getEmail());
            myUser.setPassword("qwer");
            myUser.setRePassword("qwer");
            myUser.setBirthDate(LocalDate.now().minusDays(1));
            userService.createUser(myUser);
            em.persist(myUser);
            return myUser;
        }
    }
}
package hu.progmatic.controllers;

import hu.progmatic.services.MessageService;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.*;

public class LoginOutControllerTest {

    @Test
    public void login() throws Exception {


        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver("", ".html");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LoginOutController())
                .setViewResolvers(viewResolver)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/Login"))
                .andExpect(MockMvcResultMatchers.view().name("Login"));

    }
}
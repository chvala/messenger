package hu.progmatic.controllers;

import hu.progmatic.modell.Message;
import hu.progmatic.services.MessageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void showUserData() throws Exception {
        MessageService ms = Mockito.mock(MessageService.class);
        List<Message> msgList = new ArrayList<>();
        msgList.add(new Message("asd", "asd", LocalDateTime.now()));
        Mockito.when(ms.filterMessages(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.any(),
                Mockito.anyBoolean(),
                Mockito.anyInt()))
                .thenReturn(msgList);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new MessageController(ms))
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/messagetable"))
                .andExpect(MockMvcResultMatchers.view().name("Message"));

    }

    @Test
    public void loadMessages() throws Exception {
        MessageService ms = Mockito.mock(MessageService.class);
        Mockito.when(ms.filterMessages(
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.any(),
                Mockito.anyBoolean(),
                Mockito.anyInt()))
                .thenReturn(null);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(ms)).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/messagetable"))
                .andExpect(MockMvcResultMatchers.view().name("Message"));
    }
}
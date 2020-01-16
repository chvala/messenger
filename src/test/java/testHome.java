import hu.progmatic.controllers.HomeController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class testHome {
    @Test
    public void testHome() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new HomeController())
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

}

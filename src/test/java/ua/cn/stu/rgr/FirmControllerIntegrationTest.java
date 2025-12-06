package ua.cn.stu.rgr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FirmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFirmsListPage() throws Exception {
        mockMvc.perform(get("/firms"))
                .andExpect(status().isOk())
                .andExpect(view().name("firms/firms"))
                .andExpect(model().attributeExists("firms"));
    }

    @Test
    void shouldReturnNewFirmForm() throws Exception {
        mockMvc.perform(get("/firms/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("firms/form"))
                .andExpect(model().attributeExists("firm"));
    }

    @Test
    void shouldCreateNewFirm() throws Exception {
        mockMvc.perform(post("/firms")
                        .param("name", "Test Firm")
                        .param("address", "Test Address"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/firms"));
    }

    @Test
    void shouldNotCreateFirmWithInvalidData() throws Exception {
        mockMvc.perform(post("/firms")
                        .param("name", "")
                        .param("address", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("firms/form"));
    }
}
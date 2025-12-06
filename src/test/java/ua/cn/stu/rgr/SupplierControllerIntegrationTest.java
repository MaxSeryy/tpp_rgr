package ua.cn.stu.rgr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import ua.cn.stu.rgr.entity.Firm;
import ua.cn.stu.rgr.repository.FirmRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class SupplierControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirmRepository firmRepository;

    private Long firmId;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        Firm firm = new Firm();
        firm.setName("Test Firm");
        firm.setAddress("Test Address");
        Firm savedFirm = firmRepository.save(firm);
        firmId = savedFirm.getId();
    }

    @Test
    void shouldReturnSuppliersListPage() throws Exception {
        mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andExpect(view().name("suppliers/suppliers"))
                .andExpect(model().attributeExists("suppliers"));
    }

    @Test
    void shouldReturnNewSupplierForm() throws Exception {
        mockMvc.perform(get("/suppliers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("suppliers/form"))
                .andExpect(model().attributeExists("supplier"))
                .andExpect(model().attributeExists("firms"));
    }

    @Test
    void shouldCreateNewSupplier() throws Exception {
        mockMvc.perform(post("/suppliers")
                        .param("name", "Test Supplier")
                        .param("contactInfo", "Valid Contact Info")
                        .param("firm.id", firmId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/suppliers"));
    }
}
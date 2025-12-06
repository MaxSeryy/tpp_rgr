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
import ua.cn.stu.rgr.entity.Supplier;
import ua.cn.stu.rgr.repository.FirmRepository;
import ua.cn.stu.rgr.repository.SupplierRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private Long supplierId;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        Firm firm = new Firm();
        firm.setName("Test Firm");
        firm.setAddress("Test Address");
        Firm savedFirm = firmRepository.save(firm);

        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setContactInfo("Contact Info 123");
        supplier.setFirm(savedFirm);
        Supplier savedSupplier = supplierRepository.save(supplier);
        supplierId = savedSupplier.getId();
    }

    @Test
    void shouldReturnProductsListPage() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/products"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void shouldReturnNewProductForm() throws Exception {
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/form"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("suppliers"));
    }

    @Test
    void shouldCreateNewProduct() throws Exception {
        mockMvc.perform(post("/products")
                        .param("name", "Test Product")
                        .param("sku", "12345")
                        .param("price", "100")
                        .param("supplier.id", supplierId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }
}
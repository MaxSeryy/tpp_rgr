package ua.cn.stu.rgr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import ua.cn.stu.rgr.entity.Firm;
import ua.cn.stu.rgr.repository.FirmRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FirmRepositoryTest {

    @Autowired
    private FirmRepository firmRepository;

    @Test
    void shouldSaveAndRetrieveFirm() {

        Firm firm = new Firm();
        firm.setName("Test Firm");
        firm.setAddress("Test Address");


        Firm savedFirm = firmRepository.save(firm);
        Firm retrievedFirm = firmRepository.findById(savedFirm.getId()).orElse(null);


        assertThat(retrievedFirm).isNotNull();
        assertThat(retrievedFirm.getName()).isEqualTo("Test Firm");
        assertThat(retrievedFirm.getAddress()).isEqualTo("Test Address");
    }

    @Test
    void shouldDeleteFirm() {

        Firm firm = new Firm();
        firm.setName("Firm to Delete");
        firm.setAddress("Some Address");
        Firm savedFirm = firmRepository.save(firm);


        firmRepository.deleteById(savedFirm.getId());


        assertThat(firmRepository.findById(savedFirm.getId())).isEmpty();
    }
}
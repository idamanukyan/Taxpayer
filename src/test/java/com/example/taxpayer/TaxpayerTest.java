package com.example.taxpayer;

import com.example.taxpayer.entity.Taxpayer;
import com.example.taxpayer.service.TaxpayerService;
import com.example.taxpayer.service.exceptions.ApplicationException;
import com.example.taxpayer.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.properties"})
public class TaxpayerTest {

    @Autowired
    private TaxpayerService taxpayerService;

    @Test
    @Transactional
    void add() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        Taxpayer savedTaxpayer = taxpayerService.addTaxpayer(dummyTaxpayer);

        Assertions.assertEquals(1, taxpayerService.getSize());

        Assertions.assertEquals(savedTaxpayer.getId(), dummyTaxpayer.getId());
        Assertions.assertEquals(savedTaxpayer.getName(), dummyTaxpayer.getName());
        Assertions.assertEquals(savedTaxpayer.getSurname(), dummyTaxpayer.getSurname());
        Assertions.assertEquals(savedTaxpayer.getEmail(), dummyTaxpayer.getEmail());


    }

    @Test
    @Transactional
    void addSame() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        taxpayerService.addTaxpayer(dummyTaxpayer);

        try {
            taxpayerService.addTaxpayer(dummyTaxpayer);
            fail("Expected an ApplicationException to be thrown");
        } catch (ApplicationException ex) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, ex.status());
            Assertions.assertEquals("Taxpayer already exists.", ex.getMessage());
        }

        Assertions.assertEquals(1, taxpayerService.getSize());
    }

    @Test
    @Transactional
    void deactivate() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        taxpayerService.addTaxpayer(dummyTaxpayer);
        taxpayerService.deactivate(dummyTaxpayer.getId());
        Taxpayer deactivatedTaxpayer = taxpayerService.get(dummyTaxpayer.getId());
        Assertions.assertNotNull(deactivatedTaxpayer.getDeactivated());
    }

    @Test
    @Transactional
    void deactivateSame() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        taxpayerService.addTaxpayer(dummyTaxpayer);
        taxpayerService.deactivate(dummyTaxpayer.getId());

        try {
            taxpayerService.deactivate(dummyTaxpayer.getId());
        } catch (ApplicationException ex) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, ex.status());
            Assertions.assertEquals("Taxpayer is already deactivated.", ex.getMessage());
        }
    }


    @Test
    @Transactional
    void getValid() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        Taxpayer taxpayer = taxpayerService.addTaxpayer(dummyTaxpayer);
        Assertions.assertNotNull(taxpayer);
        Assertions.assertEquals(dummyTaxpayer.getName(), taxpayer.getName());
    }


    @Test
    @Transactional
    void getInvalid() {
        try {
            taxpayerService.get(1);
        } catch (ResourceNotFoundException ex) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, ex.status());
            Assertions.assertEquals("Resource with id : {1} not found", ex.getMessage());
        }
    }


    @Test
    @Transactional
    void update() {
        Taxpayer dummyTaxpayer = createDummyTaxpayer();
        taxpayerService.addTaxpayer(dummyTaxpayer);
        dummyTaxpayer.setName("UPDATED");
        Taxpayer update = taxpayerService.update(dummyTaxpayer.getId(), dummyTaxpayer);
        Assertions.assertEquals("UPDATED", update.getName());

    }

    private Taxpayer createDummyTaxpayer() {
        return Taxpayer.builder()
                .id(1)
                .name("NAME")
                .surname("SURNAME")
                .email("EMAIL")
                .created(LocalDateTime.now())
                .build();
    }
}

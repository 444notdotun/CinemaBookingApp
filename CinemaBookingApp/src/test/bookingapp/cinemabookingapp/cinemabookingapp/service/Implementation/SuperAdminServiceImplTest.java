package bookingapp.cinemabookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)


class SuperAdminServiceImplTest{
    @Autowired
    SuperAdminService superAdminService;
    @Autowired
    AdminRepo  adminRepo;
    @Autowired
    MongoTemplate mongoTemplate;

    CreateAdminRequest createAdminRequest;

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();

        createAdminRequest = new CreateAdminRequest();
        createAdminRequest.setPassword("password");
        createAdminRequest.setCity("ekos");
    }

    @Test
    void testThatSuperAdminCanCreateAnAdmin(){
        assertEquals("ekosTheater Admin created successfully",superAdminService.createTheaterAdmin(createAdminRequest).getMessage());
        assertTrue(adminRepo.existsAdminById("ekosTheater Admin"));
    }

    @Test
    void testThatMultipleAdminCanNotBeCreatedForALocation(){
        testThatSuperAdminCanCreateAnAdmin();
        assertThrows(RuntimeException.class,() -> superAdminService.createTheaterAdmin(createAdminRequest));
    }

}
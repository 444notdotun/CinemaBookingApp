package bookingapp.cinemabookingapp;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.Role;
import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class CinemaBookingAppApplication implements CommandLineRunner {
    @Autowired
    AdminRepo  adminRepo;
    @Autowired
    PasswordEncoder  passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CinemaBookingAppApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args){
        if(!adminRepo.existsAdminByRole(Role.SUPER_ADMIN)){
            Admin admin = new SuperAdmin("Superadmin1955","Superadminpassword", Role.SUPER_ADMIN);
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminRepo.save(admin);
            log.info("Admin has been saved successfully");
        }else {
            log.info("Admin exists already");
        }
    }
}

package bookingapp.cinemabookingapp;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.Role;
import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
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
        Admin admin = new SuperAdmin("Superadmin1955","Superadminpassword", Role.SUPER_ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        if(adminRepo.existsAdminByRole(admin.getRole())){
            throw  new RuntimeException("Admin with id "+admin.getId()+" already exists");
        }
        adminRepo.save(admin);
    }
}

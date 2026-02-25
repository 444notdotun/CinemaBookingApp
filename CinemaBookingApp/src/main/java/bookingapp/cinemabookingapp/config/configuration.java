package bookingapp.cinemabookingapp.config;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.Role;
import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class configuration implements CommandLineRunner {
    @Autowired
    AdminRepo adminRepo;

    @Override
    public void run(String @NonNull ... args) throws Exception {
       createSuperAdmin();
    }

    private void createSuperAdmin(){
        Admin admin = new SuperAdmin("Superadmin1955","Superadminpassword", Role.SUPER_ADMIN);
        adminRepo.save(admin);
    }

}

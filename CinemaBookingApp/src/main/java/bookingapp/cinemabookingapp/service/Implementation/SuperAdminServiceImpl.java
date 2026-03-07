package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.TheaterAdmin;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.dtos.response.CreateAdminResponse;
import bookingapp.cinemabookingapp.exceptions.AdminAlreadyExistException;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
@Slf4j
@Service
@NullMarked
public class SuperAdminServiceImpl implements SuperAdminService {
    @Autowired
    AdminRepo adminRepo;

    PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();




    @Override
    public CreateAdminResponse createTheaterAdmin(CreateAdminRequest createAdminRequest) {
        if(adminRepo.existsAdminById(createAdminRequest.getCity()+"Theater Admin")){
            log.info("admin already exists");
            throw new AdminAlreadyExistException("The Admin already exists");
        }
        createAdminRequest.setPassword(passwordEncoder.encode(createAdminRequest.getPassword()));
        Admin admin = Mapper.mapAdminRequestToTheaterAdmin(createAdminRequest);
        saveAdminToDataBase(admin);
        log.info("admin created successfully");
        return Mapper.MapAdminToResponse(admin);
    }

    private void saveAdminToDataBase(Admin admin){
        adminRepo.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return adminRepo.findAdminById(username)
                .orElseThrow(()->new UsernameNotFoundException("Admin not found"));
    }
}

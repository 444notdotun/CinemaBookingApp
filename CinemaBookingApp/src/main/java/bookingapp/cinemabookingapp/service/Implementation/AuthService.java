package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.dtos.request.LoginRequest;
import bookingapp.cinemabookingapp.dtos.response.LoginResponse;
import bookingapp.cinemabookingapp.service.interfaces.Auth;
import bookingapp.cinemabookingapp.service.interfaces.JwtService;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthService implements Auth {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SuperAdminService superAdminService;
    @Autowired
    JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getId(),
                       loginRequest.getPassword()
                )
        );
        UserDetails user = superAdminService.loadUserByUsername(loginRequest.getId());

        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .id(loginRequest.getId())
                .accessToken(accessToken)
                .refreshToken("coming_in_chapter_6")
                .build();
    }


}

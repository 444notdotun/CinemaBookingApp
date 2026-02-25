package bookingapp.cinemabookingapp.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String,Object> extraClaims,UserDetails userDetails);
    boolean IsTokenValid(String token, UserDetails userDetails);
}

package bookingapp.cinemabookingapp.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean IsTokenValid(String token, UserDetails userDetails);

    <T> T extractClaims(String token, Function<Claims, T> claimsResolver);

    String extractUsername(String token);
}

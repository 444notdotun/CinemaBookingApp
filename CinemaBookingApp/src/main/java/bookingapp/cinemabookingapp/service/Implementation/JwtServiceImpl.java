package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.service.interfaces.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt.secret}")
    private String SecretKey;
    @Value("${app.jwt.expiration}")
    private String jwtExpiration;  @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSecretKey())
                .compact();

    }

    @Override
    public boolean IsTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SecretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}

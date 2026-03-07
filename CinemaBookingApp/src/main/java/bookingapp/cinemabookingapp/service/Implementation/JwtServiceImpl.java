package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.service.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${app.jwt.secret}")
    private String SecretKey;
    @Value("${app.jwt.expiration}")
    private long jwtExpiration;
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generating JWT Token");
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
        String username = extractUsername(token);
        log.info("Checking if token is valid");
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        log.info("Checking if token is expired");
        return extractClaims(token,claims -> claims.getExpiration().before(new Date(System.currentTimeMillis())));
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final  Claims claims = extractAllClaims(token);
        log.info("Claims extracted from JWT Token");
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        log.info("Extracting all claims from JWT Token");
       return Jwts.parser()
                .verifyWith(getSecretKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }


    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SecretKey);
        log.info("Decoded SecretKey from JWT Token");
        return Keys.hmacShaKeyFor(decodedKey);
    }
}

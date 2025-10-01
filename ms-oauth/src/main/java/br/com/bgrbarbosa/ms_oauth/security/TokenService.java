package br.com.bgrbarbosa.ms_oauth.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${spring.secret.jwt.secret}")
    private String secret;

    public String generateToken(User user){
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setIssuer(secret)
                    .setSubject(user.getUsername())
                    .claim("roles", user.getAuthorities())
                    .setExpiration(Date.from(this.generateExpirationDate()))
                    .signWith(key)
                    .compact();
            return token;
        } catch (Exception exception){
            throw new RuntimeException("Error while authenticating: " + exception.getMessage(), exception);
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

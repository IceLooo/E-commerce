package kz.zhanayev.ecommerce.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 часов

    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Извлечение даты истечения токена
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Универсальное извлечение claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Извлечение всех claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Проверка истечения токена
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Генерация токена для пользователя
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userPrincipal.getAuthorities());

        return createToken(claims, userPrincipal.getUsername());
    }

    // Создание токена
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Валидация токена
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}

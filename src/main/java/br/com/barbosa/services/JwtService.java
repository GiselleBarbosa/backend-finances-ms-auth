package br.com.barbosa.services;

import br.com.barbosa.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final static Logger logger = LoggerFactory.getLogger(JwtService.class);

    private SecretKey getSignInKey() {

        try {
            logger.info("Chave Secreta: {}", secretKey);
            return Keys.hmacShaKeyFor(secretKey.getBytes());
        } catch (Exception e) {
            logger.error("Erro ao gerar a chave de assinatura: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar a chave de assinatura.", e);
        }
    }

    public String generateToken(User user) {
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : null;

        return Jwts.builder()
                .setSubject(user.getId())
                .claim("role", roleName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {

        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public String getRolesFromToken(String token) {
        return parseToken(token).get("role", String.class);
    }
}
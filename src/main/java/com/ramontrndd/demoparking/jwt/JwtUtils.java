package com.ramontrndd.demoparking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "f9efdeca79730818f1ee8237dde960bba6937cf3bfc662128f0d3ca8c5f4996240c0e0088294989d63ab6c40279d6ad2";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 2;


    private JwtUtils() {
    }

    private static SecretKey generateKey() {
        return  Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);

        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken (String name, String role) {
        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);
        String token = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(name)
                .issuedAt(issueAt)
                .expiration(limit)
                .claim("role", role)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
        return new JwtToken(token);

    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token))
                    .getPayload();

        } catch (JwtException ex) {
            log.error(String.format("Invalid Token %s", ex.getMessage()));
        }
        return null;
    }
    public static String getNameFromToken(String token ) {
        Claims claims = getClaimsFromToken(token);
        assert claims != null;
        return claims.getSubject();
    }
    public static Boolean isTokebValid (String token) {

        try {
             Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token));
             return true;

        } catch (JwtException ex) {
            log.error(String.format("Invalid Token %s", ex.getMessage()));
        }
        return false;
    }
    private static String refactorToken (String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

}

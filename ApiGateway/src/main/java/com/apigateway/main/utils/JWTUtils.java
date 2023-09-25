package com.apigateway.main.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {
    private static final String ALPHABET = "MySecretMySecretMySecretMySecretMySecretMySecretMySecretMySecret";

    public Claims getClaims(final String token) {
        return Jwts.parser().setSigningKey(ALPHABET).parseClaimsJws(token).getBody();
    }
    public static String validateToken(String token) {
        return Jwts.parser().setSigningKey(ALPHABET).parseClaimsJws(token).getBody().getSubject();
    }

}

package com.example.app.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;


@Service
public class JWTService  {

    private final String secretKey;

    public JWTService() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username, String type, Collection<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", type);
//        claims.put("roles", roles.stream().map(role -> role.replace("ROLE_", "")).toList());
        List<String> rolesWithoutPrefix = roles.stream()
                .map(role -> role.replace("ROLE_", ""))
                .toList();


        claims.put("roles", rolesWithoutPrefix);

        // **Add this line**
        System.out.println("Roles stored in token: " + rolesWithoutPrefix);


        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 1000 * 5))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsernameFromToken(String token) {
        // extract the username from jwt token
        System.out.println("extracted username: " + extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractClaimFromToken(String token, String claimKey) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build().parseSignedClaims(token).getPayload()
                .get(claimKey, String.class);
    }

    public List<String> extractRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class); // Extract roles as a List
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsernameFromToken(token);
        List<String> roles = extractRolesFromToken(token);

        System.out.println(userDetails.getUsername());
        System.out.println(userName);
        System.out.println("Extracted roles from JWT: " + roles);

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

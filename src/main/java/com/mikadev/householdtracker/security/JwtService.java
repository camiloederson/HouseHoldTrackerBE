package com.mikadev.householdtracker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mikadev.householdtracker.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    public String generateToken(UserEntity userEntity) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(userEntity.getEmail())
                .withClaim("userId", userEntity.getId())
                .withClaim("firstName", userEntity.getFirstName())
                .withClaim("lastName", userEntity.getLastName())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plusMillis(expirationTime)))
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWT.require(algorithm)
                    .build()
                    .verify(token);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
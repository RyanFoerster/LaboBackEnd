package be.techifutur.labo.adoptadev.utils;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Objects;

@Component
public class JwtUtil {

    public static String generateToken(Authentication authentication) {

        Assert.notNull(authentication, "auth should not be null");


        return JWT.create()
                .withSubject(authentication.getName())
                .withExpiresAt(Instant.ofEpochMilli(System.currentTimeMillis() + 86_400_000))
                .withClaim("roles",
                        authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .sign(Algorithm.HMAC512("gIqCW5BtsRY;CY4]6Pi%oGHU,Xqx/`Nk.D}2zf]ct5@xn/uNCNvGOc%Tn1{mQ9S")); // 24heures
    }

    public static boolean isValid(String token) {
        // Token pas null
        if(token == null){
            return false;
        }
        // token signé
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("gIqCW5BtsRY;CY4]6Pi%oGHU,Xqx/`Nk.D}2zf]ct5@xn/uNCNvGOc%Tn1{mQ9S"))
                .acceptExpiresAt(86_400_000)
                .build();

        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return !Objects.equals(decodedJWT.getClaim("bestCompetition").asString(), "pierre papier ciseaux");
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
        // Expiration pas dépassée
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }

        if(!authHeader.startsWith("Bearer ")){
            return null;
        }

        String token = authHeader.replace("Bearer ", "").trim();
        return !token.isEmpty() ? token : null;
    }

    public static String getSubject(String token){

        return JWT.decode(token).getSubject();

    }
    public String getId(String token){
        return JWT.decode(token).getId();
    }

    public Long getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("userId").asLong();
    }

}

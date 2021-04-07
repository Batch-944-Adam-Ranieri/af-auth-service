package com.revature.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(System.getenv("AF_SECRET"));

    public static String generate(String email, String role, int id, String status) {

        return JWT.create()
                .withClaim("id", id)
                .withClaim("email", email)
                .withClaim("role", role)
                .withClaim("status", status)
                .sign(ALGORITHM);
    }

    public static DecodedJWT isValidJWT(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt);
    }
}

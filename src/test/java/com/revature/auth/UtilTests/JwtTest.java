package com.revature.auth.UtilTests;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.auth.utils.JwtUtil;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtTest {
    static String testTrainerJwt;
    static String testAdminJwt;


    @Test
    @Order(1)
    void create_jwt_trainer(){
        String jwt = JwtUtil.generate("trainer@email.com","trainer", 1, "created");
        Assertions.assertNotNull(jwt);
        Assertions.assertTrue(jwt.matches("^(.*\\..*){2}$"));
        testTrainerJwt = jwt;
    }

    @Test
    @Order(2)
    void create_jwt_admin(){
        String jwt = JwtUtil.generate("admin@email.com","admin", 2, "created");
        Assertions.assertNotNull(jwt);
        testAdminJwt = jwt;
    }

    @Test
    @Order(3)
    void decode_trainer_jwt(){
        DecodedJWT jwt = JwtUtil.isValidJWT(testTrainerJwt);
        String email = jwt.getClaim("email").asString();
        Assertions.assertEquals("trainer@email.com", email);

        String role = jwt.getClaim("role").asString();
        Assertions.assertEquals("trainer", role);
    }

    @Test
    @Order(4)
    void decode_admin_jwt(){
        DecodedJWT jwt = JwtUtil.isValidJWT(testAdminJwt);
        String email = jwt.getClaim("email").asString();
        Assertions.assertEquals("admin@email.com", email);

        String role = jwt.getClaim("role").asString();
        Assertions.assertEquals("admin", role);
    }

}
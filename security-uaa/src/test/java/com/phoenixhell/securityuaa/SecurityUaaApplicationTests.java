package com.phoenixhell.securityuaa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

//@SpringBootTest
class SecurityUaaApplicationTests {

    @Test
    void contextLoads() {
        String secret = BCrypt.hashpw("secret", BCrypt.gensalt());
        System.out.println(secret);
    }

}

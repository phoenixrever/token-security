package com.phoenixhell.securityuaa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
class SecurityUaaApplicationTests {

    @Test
    void contextLoads() {
        //String secret = BCrypt.hashpw("secret", BCrypt.gensalt());
        //System.out.println(secret);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String secret = passwordEncoder.encode("secret");
        String password = passwordEncoder.encode("123");
        System.out.println(secret);
        System.out.println(password);
        System.out.println(passwordEncoder.matches("123", secret));
    }

}

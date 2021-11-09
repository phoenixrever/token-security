package com.phoenixhell.securityuaa;

import com.phoenixhell.securityuaa.utils.ApplicationContextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SecurityUaaApplicationTests {

    @Test
    void contextLoads() {
        //String secret = BCrypt.hashpw("secret", BCrypt.gensalt());
        //System.out.println(secret);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("a12345678");
        System.out.println(password);
//        System.out.println(passwordEncoder.matches("123", secret));

//        RedisTemplate<String,String> redisTemplate = ApplicationContextUtils.getBean("redisTemplate");
//        System.out.println(redisTemplate);
    }

}

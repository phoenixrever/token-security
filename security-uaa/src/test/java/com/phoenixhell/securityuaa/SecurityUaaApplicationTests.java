package com.phoenixhell.securityuaa;

import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SecurityUaaApplicationTests {

    @Autowired
    UserService userService;

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

    @Test
    void  IDTypeTest(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("lalalal111");
        userService.save(userEntity);
    }
}

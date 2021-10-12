package com.phoenixhell.securityorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)  //此注解不能少
@EnableDiscoveryClient
@SpringBootApplication
public class SecurityOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOrderApplication.class, args);
    }

}

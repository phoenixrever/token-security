package com.phoenixhell.securityuaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SecurityUaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityUaaApplication.class, args);
    }

}

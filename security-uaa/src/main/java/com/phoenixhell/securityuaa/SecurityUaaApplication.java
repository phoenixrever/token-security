package com.phoenixhell.securityuaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.phoenixhell.securityuaa.mapper")
public class SecurityUaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityUaaApplication.class, args);
    }

}

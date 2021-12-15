package com.phoenixhell.summoresource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)  //此注解不能少
//@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.phoenixhell.summoresource.mapper")
public class SummoResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SummoResourceApplication.class, args);
    }

}

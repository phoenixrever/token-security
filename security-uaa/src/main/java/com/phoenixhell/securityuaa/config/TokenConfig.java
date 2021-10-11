package com.phoenixhell.securityuaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @author phoenixhell
 * @since 2021/10/11 0011-下午 3:13
 */
@Configuration
public class TokenConfig {
    //内存令牌存储策咯
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }
}
